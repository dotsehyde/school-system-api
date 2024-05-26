package io.dotsehyde.schoolsystem.Models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ClassModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public int size;
    @OneToOne
    public TeacherModel teacher;
    @OneToMany(targetEntity = StudentModel.class)
    public List<StudentModel> students;

    public ClassModel() {
    }

    public ClassModel(Long id, String name, int size, TeacherModel teacher, List<StudentModel> students) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.teacher = teacher;
        this.students = students;
    }
}
