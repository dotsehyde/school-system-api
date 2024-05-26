package io.dotsehyde.schoolsystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class SubjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    @OneToOne
    public TeacherModel teacher;

    public SubjectModel(Long id, String name, TeacherModel teacher) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
    }

    public SubjectModel() {
    }
}
