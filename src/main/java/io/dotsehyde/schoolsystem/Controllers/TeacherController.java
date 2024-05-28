package io.dotsehyde.schoolsystem.Controllers;

import io.dotsehyde.schoolsystem.Models.TeacherModel;
import io.dotsehyde.schoolsystem.Records.GenericResponse;
import io.dotsehyde.schoolsystem.Records.PagedResponse;
import io.dotsehyde.schoolsystem.Records.TeacherRecord;
import io.dotsehyde.schoolsystem.Repository.TeacherRepo;
import io.dotsehyde.schoolsystem.Services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService){
        this.teacherService = teacherService;
    }
    //create teacher
    @PostMapping("/create")
    public ResponseEntity<TeacherModel> createTeacher(@Valid @RequestBody TeacherRecord.TeacherCreateParam teacher){
        return ResponseEntity.ok(teacherService.createTeacher(teacher));
    }

    //get teacher by Id
    @GetMapping("/{id}")
    public ResponseEntity<TeacherModel> getTeacherById(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    //Upload teacher photo


    //get all teachers
    @GetMapping("/list")
    public ResponseEntity<PagedResponse<TeacherRecord.TeacherItem>> getAllTeachers(
            @RequestParam Optional<Integer> page,@RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort,@RequestParam Optional<String> search
            ){
        return ResponseEntity.ok(teacherService.getAllTeachers(page,limit,sort,search));
    }



    //delete teacher
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteTeacher(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.deleteTeacher(id));
    }


}
