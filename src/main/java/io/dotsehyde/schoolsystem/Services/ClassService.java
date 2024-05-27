package io.dotsehyde.schoolsystem.Services;

import io.dotsehyde.schoolsystem.Config.Error.AppException;
import io.dotsehyde.schoolsystem.Models.ClassModel;
import io.dotsehyde.schoolsystem.Records.ClassRecord;
import io.dotsehyde.schoolsystem.Records.GenericResponse;
import io.dotsehyde.schoolsystem.Records.PagedResponse;
import io.dotsehyde.schoolsystem.Repository.ClassRepo;
import io.dotsehyde.schoolsystem.Repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassService {
    @Autowired
    private ClassRepo classRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    public ClassService(ClassRepo classRepo,TeacherRepo teacherRepo){
        this.classRepo =classRepo;
        this.teacherRepo= teacherRepo;
    }

    public ClassModel createClass(ClassModel classModel){
       return classRepo.save(classModel);
    }

    public ClassModel getClassById(Long id){
        var optClass = classRepo.findById(id);
        if (optClass.isEmpty()) throw  new AppException(404,"Class with ID "+ id.toString()+" does not exist");
        return optClass.get();
    }

    public PagedResponse<ClassRecord.ClassListItem> getAllClasses(Optional<Integer> page, Optional<Integer> limit, Optional<String> sort,Optional<String> search){
        var sortParams = sort.orElse("desc,id").split(",");
        Pageable pageable = PageRequest.of(
                page.orElse(0),
                limit.orElse(10),
                Sort.by(sortParams[0].equals("desc")? Sort.Direction.DESC: Sort.Direction.ASC,sortParams[1])
        );
        Page<ClassModel> classPage =  null;
        if(search.isPresent()&&!search.get().isEmpty()){
            classPage = classRepo.findAllByNameContainsOrSizeEquals(pageable,search.get());
        }else {
            classPage = classRepo.findAll(pageable);
        }
       //convert classPage.getContent() to ClassShort
        return new PagedResponse<ClassRecord.ClassListItem>(
                classPage.getSize(),
                classPage.getTotalElements(),
                classPage.getTotalPages(),
                classPage.getNumberOfElements(),
                classPage.getSort(),
                classPage.getContent().stream().map(c->
                  new ClassRecord.ClassListItem(
                          c.getId(),
                          c.getName(),
                          c.getSize())
              ).collect(Collectors.toList())
              );
    }

    public ClassModel updateClass(ClassModel classModel,Long id){
        var res= classRepo.updateClass(classModel.getName(),classModel.getSize(),id);
        if(res>0){
            return classRepo.findById(id).get();
        }else{
            throw new AppException(500, "Class could not update");
        }
    }

    public GenericResponse deleteClass(Long id) {
        classRepo.deleteById(id);
        return new GenericResponse("Class deleted successfully");
    }

    public ClassModel assignTeacher(ClassRecord.ClassTeacher classTeacher){
        var classRoom = classRepo.findById(classTeacher.classId())
                .orElseThrow(()-> new AppException(404,"Class with ID "+ classTeacher.classId().toString()+" does not exist"));
        //get teacher
        var teacher = teacherRepo.findById(classTeacher.teacherId())
                .orElseThrow(()-> new AppException(404,"Teacher with ID "+ classTeacher.teacherId().toString()+" does not exist"));
        var res = classRepo.assignTeacher(teacher,classTeacher.classId());
        if(res>0){
            classRoom.setTeacher(teacher);
            return classRoom;
        }else{
            throw new AppException(500,"Could not assign teacher to class");
        }
    }

    public ClassModel unAssignTeacher(Long classId) {
        var classRoom = classRepo.findById(classId)
                .orElseThrow(()-> new AppException(404,"Class with ID "+ classId.toString()+" does not exist"));
        var res = classRepo.assignTeacher(null,classId);
        if(res>0){
            classRoom.setTeacher(null);
            return classRoom;
        }else{
            throw new AppException(500,"Could not un-assign teacher to class");
        }
    }
}
