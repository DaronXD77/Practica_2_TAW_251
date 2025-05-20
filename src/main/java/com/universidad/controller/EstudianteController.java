package com.universidad.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.dto.EstudianteDTO;
import com.universidad.model.Estudiante;
import com.universidad.model.Materia;
import com.universidad.service.IEstudianteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estudiantes")
@Validated
@Tag(name = "Estudiantes", description = "API para gestionar los estudiantes en el sistema")
public class EstudianteController {

    private final IEstudianteService estudianteService;
    private static final Logger logger = LoggerFactory.getLogger(EstudianteController.class);

    @Autowired
    public EstudianteController(IEstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los estudiantes", description = "Obtiene una lista de todos los estudiantes registrados")
    public ResponseEntity<List<EstudianteDTO>> obtenerTodosLosEstudiantes() {
        long inicio = System.currentTimeMillis();
        logger.info("[ESTUDIANTE] Inicio obtenerTodosLosEstudiantes: {}", inicio);
        List<EstudianteDTO> estudiantes = estudianteService.obtenerTodosLosEstudiantes();
        long fin = System.currentTimeMillis();
        logger.info("[ESTUDIANTE] Fin obtenerTodosLosEstudiantes: {} (Duracion: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/inscripcion/{numeroInscripcion}")
    @Operation(summary = "Obtener estudiante por número de inscripción", description = "Obtiene un estudiante por su número de inscripción")
    public ResponseEntity<EstudianteDTO> obtenerEstudiantePorNumeroInscripcion(@PathVariable String numeroInscripcion) {
        long inicio = System.currentTimeMillis();
        logger.info("[ESTUDIANTE] Inicio obtenerEstudiantePorNumeroInscripcion: {}", inicio);
        EstudianteDTO estudiante = estudianteService.obtenerEstudiantePorNumeroInscripcion(numeroInscripcion);
        long fin = System.currentTimeMillis();
        logger.info("[ESTUDIANTE] Fin obtenerEstudiantePorNumeroInscripcion: {} (Duracion: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(estudiante);
    }

    @GetMapping("/{id}/materias")
    @Operation(summary = "Obtener materias de un estudiante", description = "Obtiene la lista de materias asociadas a un estudiante")
    public ResponseEntity<List<Materia>> obtenerMateriasDeEstudiante(@PathVariable("id") Long estudianteId) {
        List<Materia> materias = estudianteService.obtenerMateriasDeEstudiante(estudianteId);
        return ResponseEntity.ok(materias);
    }

    @GetMapping("/{id}/lock")
    @Operation(summary = "Obtener estudiante con bloqueo", description = "Obtiene los datos del estudiante con la información de bloqueo, si existe")
    public ResponseEntity<Estudiante> getEstudianteConBloqueo(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.obtenerEstudianteConBloqueo(id);
        return ResponseEntity.ok(estudiante);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo estudiante", description = "Crea un nuevo estudiante en el sistema")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EstudianteDTO> crearEstudiante(@Valid @RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO nuevoEstudiante = estudianteService.crearEstudiante(estudianteDTO);
        return ResponseEntity.status(201).body(nuevoEstudiante);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar estudiante", description = "Actualiza los datos de un estudiante existente")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EstudianteDTO> actualizarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO estudianteActualizado = estudianteService.actualizarEstudiante(id, estudianteDTO);
        return ResponseEntity.ok(estudianteActualizado);
    }

    @PutMapping("/{id}/baja")
    @Operation(summary = "Eliminar estudiante", description = "Elimina a un estudiante del sistema")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EstudianteDTO> eliminarEstudiante(@PathVariable Long id, @RequestBody EstudianteDTO estudianteDTO) {
        EstudianteDTO estudianteEliminado = estudianteService.eliminarEstudiante(id, estudianteDTO);
        return ResponseEntity.ok(estudianteEliminado);
    }

    @GetMapping("/activos")
    @Operation(summary = "Obtener estudiantes activos", description = "Obtiene una lista de todos los estudiantes activos")
    public ResponseEntity<List<EstudianteDTO>> obtenerEstudianteActivo() {
        List<EstudianteDTO> estudiantesActivos = estudianteService.obtenerEstudianteActivo();
        return ResponseEntity.ok(estudiantesActivos);
    }
}
