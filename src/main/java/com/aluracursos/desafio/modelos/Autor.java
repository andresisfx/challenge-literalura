package com.aluracursos.desafio.modelos;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String nombre;
    private int fechaDeNacimiento;
    private int muerte;
    @OneToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(DatosAutor a) {
        this.nombre = a.nombre().replace(",","");
        this.fechaDeNacimiento = a.fechaDeNacimiento();
        this.muerte = a.muerte();
    }
    public String getnombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public int getMuerte() {
        return muerte;
    }

public void setLibros(List<Libro> libros){
        this.libros = libros;
    }
  public List<String> getLibros() {
        return (List<String>) libros.stream().map(Libro::getTitulo).toList();
  }

    @Override
    public String toString() {
        return String.format(
                """
                ~~~~~~~~~AUTOR~~~~~~~~
                Nombre: %s
                Fecha de nacimiento: %d
                Fecha de fallecimiento: %d
                Libros: %s
                ~~~~~~~~~~~~~~~~~~~~~~
                """,
                getnombre(), getFechaDeNacimiento(), getMuerte(),getLibros()

        );
    }
}



