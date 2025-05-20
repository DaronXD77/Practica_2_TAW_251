package com.universidad.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.model.Materia;
import com.universidad.repository.EstudianteMateriaRepository;
import com.universidad.repository.EstudianteRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IEstudianteMateriaService;

import jakarta.transaction.Transactional;

@Service
public class EstudianteMateriaServiceImpl implements IEstudianteMateriaService {

    @Autowired
    private EstudianteMateriaRepository estudianteMateriaRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    @Transactional
    public void inscribirMateria(Long idEstudiante, Long idMateria) {
        if (estudianteMateriaRepository.existsInscripcion(idEstudiante, idMateria) > 0) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en esta materia.");
        }

        estudianteRepository.findById(idEstudiante)
            .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado."));
        materiaRepository.findById(idMateria)
            .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada."));

        estudianteMateriaRepository.insertarInscripcion(idEstudiante, idMateria);
    }

    @Override
    public List<Materia> listarMateriasPorEstudiante(Long idEstudiante) {
        return estudianteMateriaRepository.findMateriasByEstudiante(idEstudiante);
    }

    @Override
    @Transactional
    public void eliminarInscripcion(Long idEstudiante, Long idMateria) {
        estudianteRepository.findById(idEstudiante)
            .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado."));

        estudianteMateriaRepository.eliminarInscripcion(idEstudiante, idMateria);
    }
}
