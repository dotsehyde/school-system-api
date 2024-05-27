package io.dotsehyde.schoolsystem.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Entity
@Builder
public class StudentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3, max = 100, message = "length must be between 3 and 100 characters")
    @NotNull(message = "can not be empty")
    private String firstName;
    @Size(min = 3, max = 100, message = "length must be between 3 and 100 characters")
    @NotNull(message = "can not be empty")
    private String lastName;
    private String address;
    private String photoUrl;
    @OneToOne
    private ClassModel classRoom;

    public StudentModel() {
    }

    public StudentModel(Long id, String firstName, String lastName, String address, String photoUrl, ClassModel classRoom) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.photoUrl = photoUrl;
        this.classRoom = classRoom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public ClassModel getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassModel classRoom) {
        this.classRoom = classRoom;
    }


}
