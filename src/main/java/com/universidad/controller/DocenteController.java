package com.universidad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.dto.DocenteDTO;
import com.universidad.service.IDocenteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/docentes")
@Tag(name = "Docentes", description = "Operaciones CRUD para gestión de docentes")
public class DocenteController {

    private final IDocenteService docenteService;

    @Autowired
    public DocenteController(IDocenteService docenteService) {
        this.docenteService = docenteService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los docentes", description = "Retorna una lista completa de docentes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de docentes encontrada",
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = DocenteDTO.class)))
    public ResponseEntity<List<DocenteDTO>> obtenerTodosLosDocentes() {
        List<DocenteDTO> docentes = docenteService.obtenerTodosLosDocentes();
        return ResponseEntity.ok(docentes);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar docente por email", description = "Retorna un docente específico según su dirección de email")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Docente encontrado",
                    content = @Content(schema = @Schema(implementation = DocenteDTO.class))),
        @ApiResponse(responseCode = "404", description = "Docente no encontrado")
    })
    public ResponseEntity<DocenteDTO> obtenerDocentePorEmail(
            @PathVariable @Schema(description = "Email del docente", example = "docente@universidad.edu") String email) {
        DocenteDTO docente = docenteService.obtenerDocentePorEmail(email);
        return ResponseEntity.ok(docente);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar docente por ID", description = "Retorna un docente específico según su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Docente encontrado"),
        @ApiResponse(responseCode = "404", description = "Docente no encontrado")
    })
    public ResponseEntity<DocenteDTO> obtenerDocentePorId(
            @PathVariable @Schema(description = "ID del docente", example = "1") Long id) {
        DocenteDTO docente = docenteService.obtenerDocentePorId(id);
        return ResponseEntity.ok(docente);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo docente", description = "Registra un nuevo docente en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Docente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<DocenteDTO> crearDocente(
            @RequestBody @Valid @Schema(description = "Datos del docente a crear") DocenteDTO docenteDTO) {
        DocenteDTO nuevoDocente = docenteService.crearDocente(docenteDTO);
        return ResponseEntity.status(201).body(nuevoDocente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar docente", description = "Actualiza los datos de un docente existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Docente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Docente no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    public ResponseEntity<DocenteDTO> actualizarDocente(
            @PathVariable @Schema(description = "ID del docente a actualizar", example = "1") Long id,
            @RequestBody @Valid @Schema(description = "Datos actualizados del docente") DocenteDTO docenteDTO) {
        DocenteDTO docenteActualizado = docenteService.actualizarDocente(id, docenteDTO);
        return ResponseEntity.ok(docenteActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar docente", description = "Elimina un docente del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Docente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Docente no encontrado")
    })
    public ResponseEntity<Void> eliminarDocente(
            @PathVariable @Schema(description = "ID del docente a eliminar", example = "1") Long id) {
        docenteService.eliminarDocente(id);
        return ResponseEntity.noContent().build();
    }
}