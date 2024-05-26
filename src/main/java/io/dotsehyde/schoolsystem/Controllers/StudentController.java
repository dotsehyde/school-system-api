package io.dotsehyde.schoolsystem.Controllers;

import io.dotsehyde.schoolsystem.Models.StudentModel;
import io.dotsehyde.schoolsystem.Services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private final StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
//    Create Student
    @PostMapping("/create")
    public ResponseEntity<StudentModel> createStudent(@RequestBody @Valid StudentModel student){
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            StudentModel student = mapper.readValue(data, StudentModel.class);
            return ResponseEntity.ok(studentService.createStudent(student));
//        }  catch (Exception e) {
//            throw new AppException(500,e.getMessage());
//        }
    }
    //Upload Student Photo
    @PutMapping("/uploadPhoto/{id}")
    public ResponseEntity<StudentModel> uploadStudentPhoto(@RequestParam("photo") MultipartFile photo,@PathVariable Long id){
            return ResponseEntity.ok(studentService.uploadStudentPhoto(photo,id));
    }
//    Get Student By ID
    @GetMapping("/{id}")
    public ResponseEntity<StudentModel> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }
//    Get All Students
    @GetMapping("/list")
    public ResponseEntity<Page<StudentModel>> getAllStudents(@RequestParam Optional<Integer> page,
                                                             @RequestParam Optional<Integer> limit,
                                                             @RequestParam Optional<String> sort){
        return ResponseEntity.ok(studentService.getAllStudents(page,limit,sort));
    }
//    Update Student
    @PutMapping("/update/{id}")
    public ResponseEntity<StudentModel> updateStudent(@RequestBody StudentModel student, @PathVariable Long id){
            return ResponseEntity.ok(studentService.updateStudent(student,id));
    }
//    Delete Student
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<String,String>> deleteStudent(@PathVariable Long id){
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }
}
