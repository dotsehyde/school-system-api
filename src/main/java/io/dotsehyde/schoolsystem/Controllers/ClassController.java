package io.dotsehyde.schoolsystem.Controllers;

import io.dotsehyde.schoolsystem.Models.ClassModel;
import io.dotsehyde.schoolsystem.Records.ClassRecord;
import io.dotsehyde.schoolsystem.Records.GenericResponse;
import io.dotsehyde.schoolsystem.Records.PagedResponse;
import io.dotsehyde.schoolsystem.Services.ClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    public ClassController(ClassService classService){
        this.classService=classService;
    }

//    Create Class
    @PostMapping("/create")
    public ResponseEntity<ClassModel> createClass(@Valid @RequestBody ClassModel classModel){
        return ResponseEntity.ok(classService.createClass(classModel));
    }
//    Get class by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClassModel> getClassById(@PathVariable Long id){
        return ResponseEntity.ok(classService.getClassById(id));
    }
//    Get all classes
    @GetMapping("/list")
    public ResponseEntity<PagedResponse<ClassRecord.ClassListItem>> getAllClasses(@RequestParam Optional<Integer> page,
                                                                    @RequestParam Optional<Integer> limit,
                                                                    @RequestParam Optional<String> sort, @RequestParam Optional<String> search){
        return ResponseEntity.ok(classService.getAllClasses(page,limit,sort,search));

    }
//    update class
    @PutMapping("/update/{id}")
    public ResponseEntity<ClassModel> updateClass(@RequestBody ClassModel classModel,@PathVariable Long id){
        return ResponseEntity.ok(classService.updateClass(classModel,id));
    }
//    delete class
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteClass(@PathVariable Long id){
        return ResponseEntity.ok(classService.deleteClass(id));
    }
//    assign teacher
    @PutMapping("/assignTeacher")
    public ResponseEntity<ClassModel> assignTeacher(@Valid @RequestBody ClassRecord.ClassTeacher classTeacher){
        return ResponseEntity.ok(classService.assignTeacher(classTeacher));
    }

    @PutMapping("/unAssignTeacher/{id}")
    public ResponseEntity<ClassModel> unAssignTeacher(@PathVariable Long id){
        return ResponseEntity.ok(classService.unAssignTeacher(id));
    }
}
