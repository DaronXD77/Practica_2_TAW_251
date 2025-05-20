package com.universidad.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.universidad.model.Materia;

@Repository
public interface EstudianteMateriaRepository extends JpaRepository<Materia, Long> {

    @Query(value = "SELECT COUNT(*) FROM estudiante_materia WHERE id_estudiante = :idEstudiante AND id_materia = :idMateria", nativeQuery = true)
    int existsInscripcion(@Param("idEstudiante") Long idEstudiante, @Param("idMateria") Long idMateria);

    @Modifying
    @Query(value = "INSERT INTO estudiante_materia (id_estudiante, id_materia) VALUES (:idEstudiante, :idMateria)", nativeQuery = true)
    void insertarInscripcion(@Param("idEstudiante") Long idEstudiante, @Param("idMateria") Long idMateria);

    @Modifying
    @Query(value = "DELETE FROM estudiante_materia WHERE id_estudiante = :idEstudiante AND id_materia = :idMateria", nativeQuery = true)
    void eliminarInscripcion(@Param("idEstudiante") Long idEstudiante, @Param("idMateria") Long idMateria);

    @Query(value = "SELECT m.* FROM materia m JOIN estudiante_materia em ON m.id_materia = em.id_materia WHERE em.id_estudiante = :idEstudiante", nativeQuery = true)
    List<Materia> findMateriasByEstudiante(@Param("idEstudiante") Long idEstudiante);
    
}
