package com.universidad.service;

import java.util.List;

import com.universidad.model.Materia;

public interface IMateriaDocenteService {

    void asignarMateria(Long idDocente, Long idMateria);

    List<Materia> listarMateriasPorDocente(Long idDocente);

    void eliminarAsignacion(Long idDocente, Long idMateria);
}
