package com.universidad.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "docente")
public class Docente extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "El número de empleado es obligatorio.")
    @Size(min = 3, max = 20, message = "El número de empleado debe tener entre 3 y 20 caracteres.")
    @Column(name = "nro_empleado", nullable = false, unique = true)
    private String nroEmpleado;

    @NotBlank(message = "El departamento es obligatorio.")
    @Size(min = 3, max = 100, message = "El departamento debe tener entre 3 y 100 caracteres.")
    @Column(name = "departamento", nullable = false)
    private String departamento;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "materia_docente",
        joinColumns = @JoinColumn(name = "id_docente"),
        inverseJoinColumns = @JoinColumn(name = "id_materia")
    )
    private List<Materia> materias;
}
