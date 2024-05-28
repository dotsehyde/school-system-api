package io.dotsehyde.schoolsystem.Services;

import io.dotsehyde.schoolsystem.Config.Error.AppException;
import io.dotsehyde.schoolsystem.Models.ClassModel;
import io.dotsehyde.schoolsystem.Models.TeacherModel;
import io.dotsehyde.schoolsystem.Records.GenericResponse;
import io.dotsehyde.schoolsystem.Records.PagedResponse;
import io.dotsehyde.schoolsystem.Records.TeacherRecord;
import io.dotsehyde.schoolsystem.Repository.ClassRepo;
import io.dotsehyde.schoolsystem.Repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private ClassRepo classRepo;
    public TeacherService(ClassRepo classRepo,TeacherRepo teacherRepo){
        this.teacherRepo=teacherRepo;
        this.classRepo=classRepo;
    }
    
    @Value("${sms.media.path}")
    private String mediaPath;

    //create teacher
    public TeacherModel createTeacher(TeacherRecord.TeacherCreateParam teacher){
        ClassModel classRoom = null;

        if (teacher.classId().isPresent()){
            classRoom = classRepo.findById(teacher.classId().get()).orElseThrow(()-> new AppException(404,"Class with ID"+teacher.classId().get()+"does not exist"));
        }
        return teacherRepo.save(TeacherModel.builder()
                .firstName(teacher.firstName())
                .lastName(teacher.lastName())
                .address(teacher.address().orElse(null))
                .photoUrl(teacher.photoUrl().orElse(null))
                .mainClassRoom(classRoom).build()

        );
    }

    public TeacherModel getTeacherById(Long id) {
        return teacherRepo.findById(id).orElseThrow(()-> new AppException(404,"Teacher with ID "+id+" does not exist"));
    }

    public PagedResponse<TeacherRecord.TeacherItem> getAllTeachers(Optional<Integer> page, Optional<Integer> limit,
                                                                    Optional<String> sort, Optional<String> search) {
        String[] sortParam = sort.orElse("desc,id").split(",");
        Pageable pageable = PageRequest.of(
                page.orElse(0),
                limit.orElse(10),
                Sort.by(sortParam[0].equals("desc")? Sort.Direction.DESC: Sort.Direction.ASC,sortParam[1])
        );
        Page<TeacherModel> teacherPage = teacherRepo.searchTeacher(search.orElse(""), pageable);

        return new PagedResponse<TeacherRecord.TeacherItem>(
                teacherPage.getSize(),
                teacherPage.getTotalElements(),
                teacherPage.getTotalPages(),
                teacherPage.getNumberOfElements(),
                teacherPage.getSort(),
                teacherPage.getContent().stream().map((t)->  TeacherRecord.TeacherItem.builder()
                        .photoUrl(t.getPhotoUrl())
                        .address(t.getAddress())
                        .lastName(t.getLastName())
                        .firstName(t.getFirstName())
                        .id(t.getId()).build()
                ).collect(Collectors.toList())
                );

    }

    public GenericResponse deleteTeacher(Long id) {
        teacherRepo.deleteById(id);
        return GenericResponse.builder().message("Teacher deleted successfully").build();
    }
}
