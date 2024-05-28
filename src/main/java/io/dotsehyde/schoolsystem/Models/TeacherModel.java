package io.dotsehyde.schoolsystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TeacherModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "firstName is required")
    @Size(min = 3, max = 100)
    private String firstName;
    @NotNull(message = "lastName is required")
    @Size(min=3, max=100)
    private String lastName;
    private String photoUrl;
    private String address;
    @OneToOne
    private ClassModel mainClassRoom;
    @OneToMany(targetEntity = ClassModel.class)
    private List<ClassModel> classRooms;
}
