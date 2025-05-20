package com.universidad.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;
import com.universidad.service.IMateriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/materias")
@Tag(name = "Materias", description = "API para gestionar las materias en el sistema")
public class MateriaController {

    private final IMateriaService materiaService;
    private static final Logger logger = LoggerFactory.getLogger(MateriaController.class);

    @Autowired
    public MateriaController(IMateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping
    @Operation(summary = "Obtener todas las materias", description = "Obtiene una lista de todas las materias disponibles")
    public ResponseEntity<List<MateriaDTO>> obtenerTodasLasMaterias() {
        long inicio = System.currentTimeMillis();
        logger.info("[MATERIA] Inicio obtenerTodasLasMaterias: {}", inicio);
        List<MateriaDTO> result = materiaService.obtenerTodasLasMaterias();
        long fin = System.currentTimeMillis();
        logger.info("[MATERIA] Fin obtenerTodasLasMaterias: {} (Duracion: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener materia por ID", description = "Obtiene una materia a partir de su ID")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorId(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[MATERIA] Inicio obtenerMateriaPorId: {}", inicio);
        MateriaDTO materia = materiaService.obtenerMateriaPorId(id);
        long fin = System.currentTimeMillis();
        logger.info("[MATERIA] Fin obtenerMateriaPorId: {} (Duracion: {} ms)", fin, (fin - inicio));
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    @GetMapping("/codigo/{codigoUnico}")
    @Operation(summary = "Obtener materia por código único", description = "Obtiene una materia usando su código único")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorCodigoUnico(@PathVariable String codigoUnico) {
        MateriaDTO materia = materiaService.obtenerMateriaPorCodigoUnico(codigoUnico);
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    @PostMapping
    @Operation(summary = "Crear nueva materia", description = "Permite crear una nueva materia")
    public ResponseEntity<MateriaDTO> crearMateria(@RequestBody MateriaDTO materia) {
        MateriaDTO nueva = materiaService.crearMateria(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar materia", description = "Permite actualizar los detalles de una materia existente")
    public ResponseEntity<MateriaDTO> actualizarMateria(@PathVariable Long id, @RequestBody MateriaDTO materia) {
        MateriaDTO actualizadaDTO = materiaService.actualizarMateria(id, materia);
        return ResponseEntity.ok(actualizadaDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar materia", description = "Permite eliminar una materia del sistema")
    public ResponseEntity<Void> eliminarMateria(@PathVariable Long id) {
        materiaService.eliminarMateria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/formaria-circulo/{materiaId}/{prerequisitoId}")
    @Transactional
    @Operation(summary = "Verificar círculo de prerequisitos", description = "Verifica si agregar un prerequisito formaría un círculo entre materias")
    public ResponseEntity<Boolean> formariaCirculo(@PathVariable Long materiaId, @PathVariable Long prerequisitoId) {
        MateriaDTO materiaDTO = materiaService.obtenerMateriaPorId(materiaId);
        if (materiaDTO == null) {
            return ResponseEntity.notFound().build();
        }
        Materia materia = new Materia(materiaDTO.getId(), materiaDTO.getNombreMateria(), materiaDTO.getCodigoUnico());
        boolean circulo = materia.formariaCirculo(prerequisitoId);
        if (circulo) {
            return ResponseEntity.badRequest().body(circulo);
        }
        return ResponseEntity.ok(circulo);
    }
}
