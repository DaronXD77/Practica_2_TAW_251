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
import com.universidad.service.IEstudianteMateriaService;
import com.universidad.validation.EstudianteMateriaValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inscripciones")
@Tag(name = "Inscripciones", description = "API para gestionar las inscripciones de estudiantes a materias")
public class EstudianteMateriaController {

    @Autowired
    private IEstudianteMateriaService estudianteMateriaService;

    @Autowired
    private EstudianteMateriaValidator estudianteMateriaValidator; // Inyección del validador

    @PostMapping("/inscribir")
    @Operation(summary = "Inscribir estudiante a materia", description = "Permite inscribir a un estudiante a una materia")
    public ResponseEntity<String> inscribirMateria(@RequestParam Long idEstudiante, @RequestParam Long idMateria) {
        // Validación previa antes de inscribir al estudiante
        estudianteMateriaValidator.validacionCompleta(idEstudiante, idMateria);
        
        estudianteMateriaService.inscribirMateria(idEstudiante, idMateria);
        return ResponseEntity.ok("Estudiante inscrito correctamente.");
    }

    @GetMapping("/estudiante/{idEstudiante}")
    @Operation(summary = "Listar materias por estudiante", description = "Obtiene las materias en las que está inscrito un estudiante")
    public ResponseEntity<List<Materia>> listarMateriasPorEstudiante(@PathVariable Long idEstudiante) {
        List<Materia> materias = estudianteMateriaService.listarMateriasPorEstudiante(idEstudiante);
        return ResponseEntity.ok(materias);
    }

    @DeleteMapping("/eliminar")
    @Operation(summary = "Eliminar inscripción", description = "Permite eliminar una inscripción de un estudiante a una materia")
    public ResponseEntity<String> eliminarInscripcion(@RequestParam Long idEstudiante, @RequestParam Long idMateria) {
        // Validación antes de proceder con la eliminación de la inscripción
        estudianteMateriaValidator.validacionCompleta(idEstudiante, idMateria);

        // Realiza la eliminación de la inscripción
        estudianteMateriaService.eliminarInscripcion(idEstudiante, idMateria);
        return ResponseEntity.ok("Inscripción eliminada correctamente.");
    }
}
