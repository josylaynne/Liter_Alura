package com.literalura.runner;

import com.literalura.domain.Livro;
import com.literalura.domain.Autor;
import com.literalura.repository.LivroRepository;
import com.literalura.repository.AutorRepository;
import com.literalura.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class AppRunner implements CommandLineRunner {

    private final LivroRepository livroRepo;
    private final AutorRepository autorRepo;
    private final GutendexService gutendexService;

    public AppRunner(LivroRepository livroRepo, AutorRepository autorRepo, GutendexService gutendexService) {
        this.livroRepo = livroRepo;
        this.autorRepo = autorRepo;
        this.gutendexService = gutendexService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Se veio argumento, usa ele (modo simulação)
        if (args.length > 0) {
            executarComArgs(args);
        } else {
            executarComScanner();
        }
    }

    private void executarComArgs(String... args) {
        try {
            int opcao = Integer.parseInt(args[0]);
            switch (opcao) {
                case 1 -> {
                    if (args.length < 2) {
                        System.out.println("⚠ Informe o título do livro após a opção 1.");
                        return;
                    }
                    String titulo = args[1];
                    Livro livro = gutendexService.buscarLivroPorTitulo(titulo);
                    autorRepo.save(livro.getAutor());
                    livroRepo.save(livro);
                    System.out.println("Livro salvo:\n" + livro);
                }
                case 2 -> livroRepo.findAll().forEach(System.out::println);
                case 3 -> {
                    if (args.length < 2) {
                        System.out.println("⚠ Informe o ano após a opção 3.");
                        return;
                    }
                    int ano = Integer.parseInt(args[1]);
                    List<Autor> autores = autorRepo
                            .findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqualOrAnoFalecimentoIsNull(ano, ano);
                    autores.forEach(System.out::println);
                }
                case 4 -> {
                    List<Livro> livrosEn = livroRepo.findByIdioma("en");
                    List<Livro> livrosFr = livroRepo.findByIdioma("fr");
                    System.out.println("Inglês (en): " + livrosEn.size());
                    System.out.println("Francês (fr): " + livrosFr.size());
                }
                default -> System.out.println("Opção inválida.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao executar com args: " + e.getMessage());
        }
    }

    private void executarComScanner() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Buscar livro por título");
            System.out.println("2 - Listar todos os livros");
            System.out.println("3 - Listar autores vivos em determinado ano");
            System.out.println("4 - Estatísticas: livros por idioma");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o título do livro: ");
                    String titulo = scanner.nextLine();
                    try {
                        Livro livro = gutendexService.buscarLivroPorTitulo(titulo);
                        autorRepo.save(livro.getAutor());
                        livroRepo.save(livro);
                        System.out.println("Livro salvo:\n" + livro);
                    } catch (Exception e) {
                        System.out.println("Erro ao buscar livro: " + e.getMessage());
                    }
                }
                case 2 -> livroRepo.findAll().forEach(System.out::println);
                case 3 -> {
                    System.out.print("Digite o ano: ");
                    int ano = scanner.nextInt();
                    List<Autor> autores = autorRepo
                            .findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqualOrAnoFalecimentoIsNull(ano, ano);
                    autores.forEach(System.out::println);
                }
                case 4 -> {
                    List<Livro> livrosEn = livroRepo.findByIdioma("en");
                    List<Livro> livrosFr = livroRepo.findByIdioma("fr");
                    System.out.println("Inglês (en): " + livrosEn.size());
                    System.out.println("Francês (fr): " + livrosFr.size());
                }
            }
        } while (opcao != 0);

        System.out.println("Encerrando aplicação.");
    }
}
