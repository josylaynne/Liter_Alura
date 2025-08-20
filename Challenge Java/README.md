# LiterAlura — Spring Boot 3.2.3 • Java 17 • Gutendex • PostgreSQL

Projeto de linha de comando que busca livros na **API Gutendex**, mapeia o JSON com **Jackson 2.16**, persiste **Livro** e **Autor** com **Spring Data JPA** em **PostgreSQL** e oferece um menu via `CommandLineRunner`.

## Requisitos

- **Java JDK**: 17+
- **Maven**: 4+
- **Spring Boot**: 3.2.3
- **PostgreSQL**: 16+
- **IDE** (opcional): IntelliJ IDEA

## Como criar o projeto via Spring Initializr (referência)
- Project: Maven • Language: Java • Packaging: JAR
- Java: 17 • Spring Boot: 3.2.3
- Dependencies: **Spring Data JPA**, **PostgreSQL Driver**
- Depois adicione Jackson 2.16 ao `pom.xml` (já incluso aqui).

## Configuração

Edite `src/main/resources/application.properties` conforme seu ambiente:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
```

Também é possível configurar por variáveis de ambiente:
`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`.

A URL base da Gutendex já está configurada:
```properties
app.gutendex.base-url=https://gutendex.com/books/
```

## Executando

```bash
mvn spring-boot:run
```
ou
```bash
mvn package
java -jar target/literalura-0.0.1-SNAPSHOT.jar
```

## Menu e funcionalidades

1. **Buscar livro por título** (via Gutendex) e salvar (mantém apenas **1º autor** e **1º idioma**).
2. **Listar todos os livros**.
3. **Listar livros por idioma** (`en`, `pt`, `es`, `fr`, etc.).
4. **Listar autores**.
5. **Listar autores vivos em determinado ano** (derived query/JPQL).
6. **Estatística: quantidade de livros por idioma** (exibe contagem).

## Estrutura principal

- `com.literalura.LiteraluraApplication` — `CommandLineRunner` com o menu.
- `domain/Author`, `domain/Book` — entidades JPA e relacionamento (**Book n:1 Author**).
- `dto/*` — mapeamento dos campos da Gutendex com `@JsonAlias` e `@JsonIgnoreProperties`.
- `service/GutendexClient` — `HttpClient`/`HttpRequest`/`HttpResponse` + `ObjectMapper` para consumir a API.
- `service/BookService` — regras para salvar/consultar e converter DTO → Entidades.
- `repository/*` — repositórios Spring Data JPA (`JpaRepository`).

## Observações de modelagem

- **Único idioma por livro**: para simplificar consultas, somente o **primeiro** idioma do array é salvo (campo `language`).
- **Único autor por livro**: somente o **primeiro** autor do array da API é usado e relacionado.
- Constraint de unicidade: (`title`, `author_id`) evita duplicatas simples do mesmo título/autor.

## Exemplos de uso

- Buscar: escolha opção `1`, informe "Pride and Prejudice". O primeiro resultado da Gutendex será salvo.
- Contagem por idioma: escolha `6` e informe, por exemplo, `en` ou `pt`.

## Links úteis

- API Gutendex: https://gutendex.com/
- Documentação do Spring: https://spring.io/projects/spring-boot
- Postgres: https://www.postgresql.org/download/

---

Feito com ♥ para o desafio **LiterAlura**.