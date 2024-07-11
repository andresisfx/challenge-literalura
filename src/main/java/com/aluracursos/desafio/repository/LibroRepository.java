package com.aluracursos.desafio.repository;

import com.aluracursos.desafio.modelos.Lenguaje;
import com.aluracursos.desafio.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitleContainsIgnoreCase(String titulo);
    List<Libro> findByLanguage(Lenguaje lenguaje);
    @Query("SELECT d FROM Book d ORDER BY d.descargas DESC LIMIT 10")
    List<Libro> top10Books();
    @Query(value = "SELECT d FROM Libro d INNER JOIN Autor a ON d.autores = a WHERE a.nombre = :autores")
    List<Libro> autorLibro(@Param("autor") String autor);
}
