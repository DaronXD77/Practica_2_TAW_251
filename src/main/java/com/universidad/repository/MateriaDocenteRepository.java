package com.universidad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.universidad.model.Materia;

@Repository
public interface MateriaDocenteRepository extends JpaRepository<Materia, Long> {

    @Query(value = "SELECT COUNT(*) FROM materia_docente WHERE id_docente = :idDocente AND id_materia = :idMateria", nativeQuery = true)
    int existsAsignacion(@Param("idDocente") Long idDocente, @Param("idMateria") Long idMateria);

    @Modifying
    @Query(value = "INSERT INTO materia_docente (id_docente, id_materia) VALUES (:idDocente, :idMateria)", nativeQuery = true)
    void insertarAsignacion(@Param("idDocente") Long idDocente, @Param("idMateria") Long idMateria);

    @Modifying
    @Query(value = "DELETE FROM materia_docente WHERE id_docente = :idDocente AND id_materia = :idMateria", nativeQuery = true)
    void eliminarAsignacion(@Param("idDocente") Long idDocente, @Param("idMateria") Long idMateria);

    @Query(value = "SELECT m.* FROM materia m JOIN materia_docente md ON m.id_materia = md.id_materia WHERE md.id_docente = :idDocente", nativeQuery = true)
    List<Materia> findMateriasByDocente(@Param("idDocente") Long idDocente);
}
