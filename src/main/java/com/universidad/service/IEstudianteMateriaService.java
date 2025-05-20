package com.universidad.service;

import java.util.List;

import com.universidad.model.Materia;

public interface IEstudianteMateriaService {

    void inscribirMateria(Long idEstudiante, Long idMateria);

    void eliminarInscripcion(Long idEstudiante, Long idMateria);

    List<Materia> listarMateriasPorEstudiante(Long idEstudiante);
}
