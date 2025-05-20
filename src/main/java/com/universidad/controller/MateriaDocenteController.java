package com.universidad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.model.Materia;
import com.universidad.service.IMateriaDocenteService;
import com.universidad.validation.MateriaDocenteValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/asignaciones")
@Tag(name = "Asignaciones Docente", description = "API para gestionar las asignaciones de materias a docentes")
public class MateriaDocenteController {

    @Autowired
    private IMateriaDocenteService materiaDocenteService;

    @Autowired
    private MateriaDocenteValidator materiaDocenteValidator; // Inyección del validador

    @PostMapping("/asignar")
    @Operation(summary = "Asignar materia a docente", description = "Permite asignar una materia a un docente")
    public ResponseEntity<String> asignarMateria(@RequestParam Long idDocente, @RequestParam Long idMateria) {
        // Validación previa antes de asignar la materia
        materiaDocenteValidator.validacionCompleta(idDocente, idMateria);

        materiaDocenteService.asignarMateria(idDocente, idMateria);
        return ResponseEntity.ok("Materia asignada correctamente.");
    }

    @GetMapping("/docente/{idDocente}")
    @Operation(summary = "Listar materias por docente", description = "Obtiene las materias asignadas a un docente")
    public ResponseEntity<List<Materia>> listarMateriasPorDocente(@PathVariable Long idDocente) {
        List<Materia> materias = materiaDocenteService.listarMateriasPorDocente(idDocente);
        return ResponseEntity.ok(materias);
    }

    @DeleteMapping("/eliminar")
    @Operation(summary = "Eliminar asignación de materia", description = "Elimina la asignación de una materia a un docente")
    public ResponseEntity<String> eliminarAsignacion(@RequestParam Long idDocente, @RequestParam Long idMateria) {
        // Validación antes de proceder con la eliminación de la asignación
        materiaDocenteValidator.validacionCompleta(idDocente, idMateria);

        // Realiza la eliminación de la asignación
        materiaDocenteService.eliminarAsignacion(idDocente, idMateria);
        return ResponseEntity.ok("Asignación eliminada correctamente.");
    }
}
