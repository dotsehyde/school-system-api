package io.dotsehyde.schoolsystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class TeacherModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NotNull(message = "firstName is required")
    @Size(min = 3, max = 100)
    public String firstName;
    @NotNull(message = "lastName is required")
    @Size(min=3, max=100)
    public String lastName;
    public String photoUrl;
    public String address;
    @OneToOne
    public ClassModel mainClassRoom;
    @OneToMany(targetEntity = ClassModel.class)
    public List<ClassModel> classRooms;

    public TeacherModel() {

    }

    public TeacherModel(Long id, String firstName, String lastName, String photoUrl, String address, ClassModel mainClassRoom, List<ClassModel> classRooms) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
        this.address = address;
        this.mainClassRoom = mainClassRoom;
        this.classRooms = classRooms;
    }
}
