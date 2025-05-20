package com.universidad.validation;

import org.springframework.stereotype.Component;

import com.universidad.repository.EstudianteMateriaRepository;

@Component
public class EstudianteMateriaValidator {

    private final EstudianteMateriaRepository estudianteMateriaRepository;

    public EstudianteMateriaValidator(EstudianteMateriaRepository estudianteMateriaRepository) {
        this.estudianteMateriaRepository = estudianteMateriaRepository;
    }

    public void validaEstudianteExistente(Long idEstudiante) {
        // Verifica si el estudiante existe
        if (idEstudiante == null || idEstudiante <= 0) {
            throw new IllegalArgumentException("El estudiante no existe.");
        }
    }

    public void validaMateriaExistente(Long idMateria) {
        // Verifica si la materia existe
        if (idMateria == null || idMateria <= 0) {
            throw new IllegalArgumentException("La materia no existe.");
        }
    }

    public void validaInscripcionExistente(Long idEstudiante, Long idMateria) {
        // Verifica si la inscripción existe para poder eliminarla
        if (estudianteMateriaRepository.existsInscripcion(idEstudiante, idMateria) == 0) {
            throw new IllegalArgumentException("El estudiante no está inscrito en esta materia.");
        }
    }

    public void validacionCompleta(Long idEstudiante, Long idMateria) {
        validaEstudianteExistente(idEstudiante);
        validaMateriaExistente(idMateria);
        validaInscripcionExistente(idEstudiante, idMateria); // Validación para asegurarnos de que la inscripción exista
    }
}
