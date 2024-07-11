package com.aluracursos.desafio.principal;

import com.aluracursos.desafio.modelos.*;
import com.aluracursos.desafio.modelos.DatosLibros;
import com.aluracursos.desafio.modelos.Libro;
import com.aluracursos.desafio.repository.AutorRepository;
import com.aluracursos.desafio.repository.LibroRepository;
import com.aluracursos.desafio.service.ConsumoAPI;
import com.aluracursos.desafio.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private LibroRepository libroRepositorio;
    private AutorRepository repositorio;
    private List<Autor> autores;


    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepositorio = libroRepository;
        this.repositorio = autorRepository;
    }

    //MENU DE INTERACCIÓN CON EL USUARIO
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬
                    1. Muestra Listado de todos los libros
                    2. Buscar libro por titulo
                    3. Lista de autores
                    4. Listar autores vivos en determinado año
                    5. Buscar Libros por idiomas
                    6. Top 10 libros más descargados
                    0. Salir
                    ¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬¬
                                        """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    mostrarListadoLibros();
                    break;
                case 2:
                    buscarLibroPorTitulo();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 6:
                    top10();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación");
                    break;
                default:
                    System.out.println("Opción invalida");
            }
        }
    }


    private void setTodosLosAutores(List<Autor> autores) {
        autores.forEach(autor -> autor.setLibros(libroRepositorio.autorLibro(autor.getnombre())));

    }

    private Libro getDatosLibros(String titulo) {
        Optional<Libro> libroOptional = libroRepositorio.findByTitleContainsIgnoreCase(titulo);
        return libroOptional.orElse(null);
    }

    private Libro getConsumoAPI(String titulo) {
        String URL_BASE = "https://gutendex.com/books/?search=";
        var buscar = (URL_BASE + titulo.replace(" ", "%20"));
        var json = consumoAPI.obtenerDatos(buscar);
        DatosGlobales res = conversor.obtenerDatos(json, DatosGlobales.class);
        Optional<DatosLibros> libroDatosOptional = res.libros().stream()
                .filter(d -> d.titulo().equalsIgnoreCase(titulo))
                .findFirst();
        /*if (libroDatosOptional.isPresent()) {
            DatosLibros datosLibros = libroDatosOptional.get();
            DatosAutor datosAutor = datosLibros.autor().getFirst();
            Libro libro = new Libro(datosLibros, new Autor(datosAutor));
            Autor autor = libro.getAutor();
            Optional<Autor> autorExiste = repositorio.findByName(autor.getnombre());
            if (autorExiste.isEmpty()) {
                repositorio.save(autor);
            } else {
                libro.setAutor(autorExiste.get());
            }
            libroRepositorio.save(libro);
            return new Libro(datosLibros, new Autor(datosAutor));
        }*/
        return null;
    }

    private Libro getLibro() {
        try {
            System.out.println("Escribe el titulo del libro que deseas buscar");
            String titulo = teclado.nextLine();
            Libro Libro = getDatosLibros(titulo);
            if (Libro == null) {
                Libro = getConsumoAPI(titulo);
            }
            return Libro;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    private void mostrarListadoLibros() {
        List<Libro> libros = libroRepositorio.findAll();
        libros.forEach(System.out::println);
    }

    private void buscarLibroPorTitulo() {
        Libro libro = getLibro();
        if (libro != null) {
            System.out.println("Libro encontrado");
            System.out.println(libro);
        } else {
            System.out.println("No se encontró el libro");
        }

    }

    private void listarAutores() {
        List<Autor> autores = repositorio.findAll();
        setTodosLosAutores(autores);
        autores.forEach(System.out::println);
    }


    private void listarAutoresVivos() {
        try {
            System.out.println("¿De que año deseas ver los actores vivos?");
            int año = teclado.nextInt();
            List<Autor> autor = repositorio.autoresVivos(año);
            setTodosLosAutores(autores);
            autores.forEach(System.out::println);
            if (autores.isEmpty()) {
                System.out.println("Ningún autor vivo en este año: " + año);
            } else {
                System.out.println("Los autores vivos en este año:" + año);
                autores.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    private void librosPorIdioma() {
        try {
            System.out.println("Lista de idiomas disponibles");
            for (Lenguaje lenguaje : Lenguaje.values()) {
                System.out.println("" + lenguaje.getAbreviatura() + ":" + lenguaje.getIdioma());
            }
            System.out.println("Escribe la abreviatura del idioma");
            var i = teclado.nextLine();
            var lenguaje = Lenguaje.fromString(i);
            List<Libro> libros = libroRepositorio.findByLanguage(lenguaje);
            if (libros.isEmpty()) {
                System.out.println("No se encuentran libros en este idioma:" + lenguaje.getIdioma());
            } else {
                System.out.println("Libros en " + lenguaje.getIdioma() + "");
                libros.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void top10() {
        List<Libro> top = libroRepositorio.top10Books();
        System.out.println("El TOP10 de libros más descargados es:");
        top.forEach(System.out::println);
    }
}



