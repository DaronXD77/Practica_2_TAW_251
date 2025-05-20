package com.universidad.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "materia")
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_materia")
    private Long id;

    @NotBlank(message = "El nombre de la materia es obligatorio.")
    @Size(max = 100, message = "El nombre de la materia no puede superar los 100 caracteres.")
    @Column(name = "nombre_materia", nullable = false, length = 100)
    private String nombreMateria;

    @NotBlank(message = "El código único de la materia es obligatorio.")
    @Size(max = 50, message = "El código único no puede superar los 50 caracteres.")
    @Column(name = "codigo_unico", nullable = false, unique = true)
    private String codigoUnico;

    @NotNull(message = "Los créditos son obligatorios.")
    @Column(name = "creditos", nullable = false)
    private Integer creditos;

    @Version
    @Column(nullable = false)
    private Long version = 1L;

    @ManyToMany
    @JoinTable(
        name = "materia_prerequisito",
        joinColumns = @JoinColumn(name = "id_materia"),
        inverseJoinColumns = @JoinColumn(name = "id_prerequisito")
    )
    @JsonIgnoreProperties("esPrerequisitoDe")
    private List<Materia> prerequisitos;

    @ManyToMany(mappedBy = "prerequisitos")
    @JsonIgnoreProperties("prerequisitos")
    private List<Materia> esPrerequisitoDe;

    @ManyToMany(mappedBy = "materias", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Docente> docentes;

    public Materia(Long id, String nombreMateria, String codigoUnico) {
        this.id = id;
        this.nombreMateria = nombreMateria;
        this.codigoUnico = codigoUnico;
    }

    public boolean formariaCirculo(Long prerequisitoId) {
        return verificarCiclo(this.id, prerequisitoId, new HashSet<>());
    }

    private boolean verificarCiclo(Long objetivoId, Long actualId, Set<Long> visitados) {
        if (objetivoId == null || actualId == null) return false;
        if (objetivoId.equals(actualId)) return true;
        if (!visitados.add(actualId)) return false;

        if (prerequisitos != null) {
            for (Materia prereq : prerequisitos) {
                if (prereq != null && prereq.getId() != null) {
                    if (prereq.getId().equals(actualId) || 
                        prereq.verificarCiclo(objetivoId, prereq.getId(), visitados)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
