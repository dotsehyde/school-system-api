package io.dotsehyde.schoolsystem.Controllers;

import io.dotsehyde.schoolsystem.Models.SubjectModel;
import io.dotsehyde.schoolsystem.Records.SubjectRecord;
import io.dotsehyde.schoolsystem.Services.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subject")
public class SubjectController {
    private final SubjectService subjectService;

public SubjectController(SubjectService subjectService){
    this.subjectService=subjectService;
}
    //Create Subject
    @PostMapping("/create")
    public ResponseEntity<SubjectModel> createSubject(@RequestBody SubjectRecord.CreateSubjectParam subject){
return ResponseEntity.ok(subjectService.createSubject(subject));
    }

    //Get By ID

    //Get All Subjects

    //Get Class Subjects

    //Assign Teacher to Subject

    //UnAssign Teacher to Subject
}
