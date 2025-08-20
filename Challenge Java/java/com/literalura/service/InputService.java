package com.literalura.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class InputService {

    private final Scanner scanner;

    public InputService() {
        this.scanner = new Scanner(System.in);
    }

    public int lerInteiro(String mensagem) {
        System.out.print(mensagem);
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um número válido: ");
            scanner.next(); // descarta entrada inválida
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // limpa buffer
        return valor;
    }

    public String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }
}
