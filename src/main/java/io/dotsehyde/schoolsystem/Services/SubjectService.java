package io.dotsehyde.schoolsystem.Services;


import io.dotsehyde.schoolsystem.Config.Error.AppException;
import io.dotsehyde.schoolsystem.Models.SubjectModel;
import io.dotsehyde.schoolsystem.Records.SubjectRecord;
import io.dotsehyde.schoolsystem.Repository.SubjectRepo;
import io.dotsehyde.schoolsystem.Repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    private final SubjectRepo subjectRepo;
    private final TeacherRepo teacherRepo;
    @Autowired
    public SubjectService(SubjectRepo subjectRepo,TeacherRepo teacherRepo){
        this.subjectRepo = subjectRepo;
        this.teacherRepo = teacherRepo;
    }

    public SubjectModel createSubject(SubjectRecord.CreateSubjectParam subject){
        if(subject.teacherId().isPresent()){
            var teacher = teacherRepo.findById(subject.teacherId().get()).orElseThrow(()-> new AppException(404,"Teacher with ID: "+subject.teacherId().get()+" does"));
       return  subjectRepo.save(
                    SubjectModel.builder()
                            .name(subject.name())
                            .teacher(teacher)
                            .build()
            );
        }else{
            return subjectRepo.save(
                    SubjectModel.builder()
                            .name(subject.name())
                            .build()
            );

        }
    }
}
