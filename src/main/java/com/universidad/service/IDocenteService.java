package com.universidad.service;

import java.util.List;

import com.universidad.dto.DocenteDTO;

public interface IDocenteService {
    List<DocenteDTO> obtenerTodosLosDocentes();
    DocenteDTO obtenerDocentePorId(Long id);
    DocenteDTO obtenerDocentePorEmail(String email);  // Método agregado
    DocenteDTO crearDocente(DocenteDTO docente);
    DocenteDTO actualizarDocente(Long id, DocenteDTO docente);
    void eliminarDocente(Long id);
}
