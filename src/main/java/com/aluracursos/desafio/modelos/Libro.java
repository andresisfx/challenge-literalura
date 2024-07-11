package com.aluracursos.desafio.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    private long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    @ManyToOne()
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Lenguaje lenguaje;
    private long descargas;

    public Libro(){


    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lenguaje getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(Lenguaje lenguaje) {
        this.lenguaje = lenguaje;
    }

    public long getDescargas() {
        return descargas;
    }

    public void setDescargas(long descargas) {
        this.descargas = descargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo= titulo;
    }



    @Override
    public String toString() {
        return String.format(
                """
               ¬¬¬¬¬¬¬¬¬¬¬¬¬¬LIBRO¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬
                Título      : %s
                Autor       : %s
                Idioma      : %s
                Descargas   : %d
                ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬
                """,
                getTitulo(),
                getAutor().getnombre(),
                getLenguaje(),
                getDescargas()
        );

    }
}