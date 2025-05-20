package com.universidad.repository;

import java.util.Optional;  // Necesitamos usar Optional para manejar el valor retornado

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.universidad.model.Docente;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

    boolean existsByEmail(String email);  // Verificar existencia de email
    boolean existsByNroEmpleado(String nroEmpleado);  // Verificar existencia de número de empleado

    // Método para buscar un docente por su email
    Optional<Docente> findByEmail(String email);  // Devuelve un Optional para manejar el caso en el que no se encuentre el docente
}
