package com.universidad.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.universidad.dto.DocenteDTO;
import com.universidad.model.Docente;
import com.universidad.repository.DocenteRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IDocenteService;

@Service
public class DocenteServiceImpl implements IDocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    // Métodos de conversión
    private DocenteDTO convertToDTO(Docente docente) {
        return DocenteDTO.builder()
                .id(docente.getId())
                .nombre(docente.getNombre())
                .apellido(docente.getApellido())
                .email(docente.getEmail())
                .fechaNacimiento(docente.getFechaNacimiento())
                .nroEmpleado(docente.getNroEmpleado())
                .departamento(docente.getDepartamento())
                .build();
    }

    private Docente convertToEntity(DocenteDTO dto) {
        return Docente.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .fechaNacimiento(dto.getFechaNacimiento())
                .nroEmpleado(dto.getNroEmpleado())
                .departamento(dto.getDepartamento())
                .build();
    }

    @Override
    public List<DocenteDTO> obtenerTodosLosDocentes() {
        return docenteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DocenteDTO obtenerDocentePorId(Long id) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        return convertToDTO(docente);
    }

    @Override
    public DocenteDTO crearDocente(DocenteDTO docenteDTO) {
        Docente docente = convertToEntity(docenteDTO);
        Docente saved = docenteRepository.save(docente);
        return convertToDTO(saved);
    }

    @Override
    public DocenteDTO actualizarDocente(Long id, DocenteDTO docenteDTO) {
        Docente existente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        existente.setNombre(docenteDTO.getNombre());
        existente.setApellido(docenteDTO.getApellido());
        existente.setEmail(docenteDTO.getEmail());
        existente.setFechaNacimiento(docenteDTO.getFechaNacimiento());
        existente.setNroEmpleado(docenteDTO.getNroEmpleado());
        existente.setDepartamento(docenteDTO.getDepartamento());
        return convertToDTO(docenteRepository.save(existente));
    }

    @Override
    public void eliminarDocente(Long id) {
        docenteRepository.deleteById(id);
    }

    @Override
    public DocenteDTO obtenerDocentePorEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerDocentePorEmail'");
    }

}
