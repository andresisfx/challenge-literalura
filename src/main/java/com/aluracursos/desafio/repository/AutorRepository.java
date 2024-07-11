package com.aluracursos.desafio.repository;

import com.aluracursos.desafio.modelos.Autor;
import com.aluracursos.desafio.modelos.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    @Query("SELECT a FROM Author a WHERE a.fechaDeNacimiento <= :year AND (a.muerte > :year OR a.muerte IS NULL)")
     List<Autor> autoresVivos(@Param("año") int año);

}
