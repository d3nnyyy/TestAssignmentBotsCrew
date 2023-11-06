package ua.dtsebulia.testassignmentbotscrew.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Entity class for lectors.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Lector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private Integer salary;

    @ManyToOne
    private Degree degree;

    @OneToMany(mappedBy = "headOfDepartment")
    private Set<Department> departments;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
