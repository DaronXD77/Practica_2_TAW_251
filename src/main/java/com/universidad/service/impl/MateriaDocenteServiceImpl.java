package com.universidad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.model.Materia;
import com.universidad.repository.DocenteRepository;
import com.universidad.repository.MateriaDocenteRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IMateriaDocenteService;

import jakarta.transaction.Transactional;

@Service
public class MateriaDocenteServiceImpl implements IMateriaDocenteService {

    @Autowired
    private MateriaDocenteRepository materiaDocenteRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    @Transactional
    public void asignarMateria(Long idDocente, Long idMateria) {
        if (materiaDocenteRepository.existsAsignacion(idDocente, idMateria) > 0) {
            throw new IllegalArgumentException("Esta asignación ya existe.");
        }

        docenteRepository.findById(idDocente)
            .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado."));
        materiaRepository.findById(idMateria)
            .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada."));

        materiaDocenteRepository.insertarAsignacion(idDocente, idMateria);
    }

    @Override
    public List<Materia> listarMateriasPorDocente(Long idDocente) {
        return materiaDocenteRepository.findMateriasByDocente(idDocente);
    }

    @Override
    @Transactional
    public void eliminarAsignacion(Long idDocente, Long idMateria) {
        docenteRepository.findById(idDocente)
            .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado."));

        materiaDocenteRepository.eliminarAsignacion(idDocente, idMateria);
    }
}
