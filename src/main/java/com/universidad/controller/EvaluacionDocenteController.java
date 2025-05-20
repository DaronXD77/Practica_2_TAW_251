package com.universidad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.model.EvaluacionDocente;
import com.universidad.service.IEvaluacionDocenteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/evaluaciones-docente")
@Tag(name = "Evaluaciones Docente", description = "API para gestionar las evaluaciones de los docentes")
public class EvaluacionDocenteController {

    @Autowired
    private IEvaluacionDocenteService evaluacionDocenteService;

    @PostMapping
    @Operation(summary = "Crear nueva evaluación docente", description = "Permite crear una nueva evaluación para un docente")
    public ResponseEntity<EvaluacionDocente> crearEvaluacion(@RequestBody EvaluacionDocente evaluacion) {
        EvaluacionDocente nueva = evaluacionDocenteService.crearEvaluacion(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @GetMapping("/docente/{docenteId}")
    @Operation(summary = "Obtener evaluaciones por docente", description = "Obtiene todas las evaluaciones realizadas a un docente en específico")
    public ResponseEntity<List<EvaluacionDocente>> obtenerEvaluacionesPorDocente(@PathVariable Long docenteId) {
        List<EvaluacionDocente> evaluaciones = evaluacionDocenteService.obtenerEvaluacionesPorDocente(docenteId);
        return ResponseEntity.ok(evaluaciones);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener evaluación por ID", description = "Obtiene una evaluación específica por su ID")
    public ResponseEntity<EvaluacionDocente> obtenerEvaluacionPorId(@PathVariable Long id) {
        EvaluacionDocente evaluacion = evaluacionDocenteService.obtenerEvaluacionPorId(id);
        if (evaluacion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(evaluacion);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar evaluación", description = "Elimina una evaluación docente a partir de su ID")
    public ResponseEntity<Void> eliminarEvaluacion(@PathVariable Long id) {
        evaluacionDocenteService.eliminarEvaluacion(id);
        return ResponseEntity.noContent().build();
    }
}
