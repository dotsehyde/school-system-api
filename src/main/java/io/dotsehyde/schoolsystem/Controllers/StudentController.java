package io.dotsehyde.schoolsystem.Controllers;

import io.dotsehyde.schoolsystem.Models.StudentModel;
import io.dotsehyde.schoolsystem.Models.SubjectModel;
import io.dotsehyde.schoolsystem.Records.PagedResponse;
import io.dotsehyde.schoolsystem.Records.StudentRecord;
import io.dotsehyde.schoolsystem.Services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
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
    public ResponseEntity<StudentModel> createStudent(@RequestBody @Valid StudentRecord.StudentCreateParam student){
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
    public ResponseEntity<PagedResponse<StudentRecord.StudentListItem>> getAllStudents(@RequestParam Optional<Integer> page,
                                                                                       @RequestParam Optional<Integer> limit,
                                                                                       @RequestParam Optional<String> sort,
                                                                                       @RequestParam Optional<String> search){
        return ResponseEntity.ok(studentService.getAllStudents(page,limit,sort,search));
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

    //Assign student to class
    @PutMapping("/classAssign")
    public ResponseEntity<StudentModel> assignClass(@RequestBody @Valid StudentRecord.StudentClassParam studentClass){
      return ResponseEntity.ok(studentService.assignClass(studentClass));
    }

    //UnAssign student to class
    @PutMapping("/{id}/classUnAssign")
    public ResponseEntity<StudentModel> unAssignClass(@PathVariable Long id){
        return ResponseEntity.ok(studentService.unAssignClass(id));
    }

    //Get Student Subjects
    @GetMapping("/{id}/subjects")
    public ResponseEntity<List<SubjectModel>> getStudentSubject(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentSubject(id));
    }

}
