package com.literalura.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer birthYear;
    private Integer deathYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Livro> books = new ArrayList<>();

    public Autor() { }

    public Autor(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }
    public Integer getDeathYear() { return deathYear; }
    public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }
    public List<Livro> getBooks() { return books; }

    public String toPrettyString() {
        String vida = (birthYear == null ? "??" : birthYear.toString()) + " - " + (deathYear == null ? "?" : deathYear.toString());
        return "Autor{id=%d, nome='%s', vida=%s, livros=%d}".formatted(id, name, vida, books == null ? 0 : books.size());
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor author = (Autor) o;
        return Objects.equals(name, author.name) &&
               Objects.equals(birthYear, author.birthYear) &&
               Objects.equals(deathYear, author.deathYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthYear, deathYear);
    }

    public void setNome(String name) {
    }

    public void setAnoNascimento(Integer integer) {
    }

    public void setAnoFalecimento(Integer integer) {
    }
}
