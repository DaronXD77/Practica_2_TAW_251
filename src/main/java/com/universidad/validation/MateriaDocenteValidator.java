package com.universidad.validation;

import org.springframework.stereotype.Component;

import com.universidad.repository.MateriaDocenteRepository;

@Component
public class MateriaDocenteValidator {

    private final MateriaDocenteRepository materiaDocenteRepository;

    public MateriaDocenteValidator(MateriaDocenteRepository materiaDocenteRepository) {
        this.materiaDocenteRepository = materiaDocenteRepository;
    }

    public void validaMateriaExistente(Long idMateria) {
        // Verifica si la materia existe (aquí puedes realizar la verificación según tu necesidad)
        if (idMateria == null || idMateria <= 0) {
            throw new IllegalArgumentException("La materia no existe.");
        }
    }

    public void validaDocenteExistente(Long idDocente) {
        // Verifica si el docente existe (aquí puedes realizar la verificación según tu necesidad)
        if (idDocente == null || idDocente <= 0) {
            throw new IllegalArgumentException("El docente no existe.");
        }
    }

    public void validaAsignacionExistente(Long idDocente, Long idMateria) {
        // Verifica si la asignación existe para poder eliminarla
        if (materiaDocenteRepository.existsAsignacion(idDocente, idMateria) == 0) {
            throw new IllegalArgumentException("La materia no está asignada a este docente.");
        }
    }

    public void validacionCompleta(Long idDocente, Long idMateria) {
        validaMateriaExistente(idMateria);
        validaDocenteExistente(idDocente);
        validaAsignacionExistente(idDocente, idMateria); // Validación para asegurarnos de que la asignación exista
    }
}
