package io.dotsehyde.schoolsystem.Services;

import io.dotsehyde.schoolsystem.Config.Error.AppException;
import io.dotsehyde.schoolsystem.Models.StudentModel;
import io.dotsehyde.schoolsystem.Models.SubjectModel;
import io.dotsehyde.schoolsystem.Records.PagedResponse;
import io.dotsehyde.schoolsystem.Records.StudentRecord;
import io.dotsehyde.schoolsystem.Repository.ClassRepo;
import io.dotsehyde.schoolsystem.Repository.StudentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentService {
    @Autowired
    private final StudentRepo studentRepo;
    @Autowired
    private final ClassRepo classRepo;

    public StudentService(StudentRepo studentRepo,ClassRepo classRepo){
        this.studentRepo= studentRepo;
        this.classRepo = classRepo;
    }

    @Value("${sms.media.path}")
    private String mediaDir;
    @Value("${sms.base.url}")
    private String baseUrl;

    public StudentModel createStudent(StudentRecord.StudentCreateParam student)  {
        if(student.classId().isPresent()){
            var classRoom = classRepo.findById(student.classId().get())
                    .orElseThrow(()-> new AppException(404,"Class with ID "+student.classId().toString()+" doesn't exist"));
            //update class size
            classRepo.updateClassSize(classRoom.getSize()-1,classRoom.getId());
            return studentRepo.save(StudentModel.builder()
                    .firstName(student.firstName())
                    .lastName(student.lastName())
                    .photoUrl(student.photoUrl().orElse(null))
                    .address(student.address().orElse(null))
                    .classRoom(classRoom)
                    .build()
            );
        }
            return studentRepo.save(StudentModel.builder()
                    .firstName(student.firstName())
                    .lastName(student.lastName())
                    .photoUrl(student.photoUrl().orElse(null))
                    .address(student.address().orElse(null))
                    .classRoom(null)
                    .build());
    }

    public StudentModel getStudentById(Long id){
    return studentRepo.findById(id)
            .orElseThrow(()->new AppException(400,"Student with ID " + id.toString() + " doesn't exist"));
    }

    public PagedResponse<StudentRecord.StudentListItem> getAllStudents(Optional<Integer> page, Optional<Integer> limit, Optional<String> sort, Optional<String> search){
        var sortParam = sort.orElse("desc,id").split(",");
        Page<StudentModel> studentPage = null;
        Pageable pageable = PageRequest.of(page.orElse(0),
                limit.orElse(10), Sort.by(
                        sortParam[0].equals("desc")?Sort.Direction.DESC: Sort.Direction.ASC,sortParam[1]
                ));
        if(search.isPresent()&& !search.get().isEmpty()){
            studentPage = studentRepo.searchStudent(search.get(),pageable);
        }else{
            studentPage = studentRepo.findAll(pageable);
        }
      var studentList=  studentPage.getContent().stream().map(s->new StudentRecord.StudentListItem(
                s.getId(),
                s.getFirstName(),
                s.getLastName(),
                s.getAddress(),
                s.getPhotoUrl()
                )).collect(Collectors.toList());
        return new PagedResponse<StudentRecord.StudentListItem>(
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages(),
                studentPage.getNumberOfElements(),
                studentPage.getSort(),
                studentList
        );

    }

    public StudentModel updateStudent(StudentModel student,Long id){
        if(!studentRepo.existsById(id)) throw  new AppException(400, "Student with Id"+id.toString()+" does not exist");
        var res=studentRepo.updateStudent(student.getFirstName(),student.getLastName(),student.getAddress(),id);
        if(res>0){
          return  studentRepo.findById(id).get();
        }else{
            throw new AppException(500,"Student could not be updated");
        }

    }

    public HashMap<String,String> deleteStudent(Long id){
        var stud=studentRepo.findById(id).orElseThrow(()-> new AppException(400, "Student with Id "+id.toString()+" does not exist"));
       classRepo.updateClassSize(stud.getClassRoom().getSize()+1,stud.getClassRoom().getId());
        studentRepo.deleteById(id);
        return new HashMap<String,String>()
                {{put("data","Student deleted successfully");}};
    }

    public StudentModel uploadStudentPhoto(MultipartFile photo,Long id) {
        try {
            if(!studentRepo.existsById(id)) throw new AppException(404,"Student with ID "+id.toString()+" does not exist");
            var student = studentRepo.findById(id);
            if (!photo.getContentType().contains("image"))
                throw new AppException(400, "The file is not a picture format: Please use .png, .jpeg");
            if (photo.getSize() > 2_000_000) throw new AppException(400, "Your is photo size is too large");

            String fileExtension = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf("."));
            // Generate a unique file name with the same extension
            String generatedFileName = "stud_"+id.toString() + fileExtension;
            // Ensure the media directory exists
            Path mediaPath = Paths.get(mediaDir);
            if (!Files.exists(mediaPath)) {
                Files.createDirectories(mediaPath);
            }
            if (student.isPresent() && student.get().getPhotoUrl() != null){
              log.error("Called here!!!!");
                Path oldFilePath = Paths.get(mediaDir).resolve(student.get().getPhotoUrl().split("media")[1].substring(1));
                Files.deleteIfExists(oldFilePath);
            }

            Path filePath = mediaPath.resolve(generatedFileName);
            photo.transferTo(filePath.toFile());
            // Generate the new photo URL
            String newPhotoUrl = baseUrl + "/media/" + generatedFileName;
            int res = studentRepo.updateStudentPhoto(newPhotoUrl,id);
            if(res >0){
                student.get().setPhotoUrl(newPhotoUrl);
                return student.get();
            }else{
                throw new AppException(500,"Could not update student photo");
            }
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }

    public StudentModel assignClass(StudentRecord.StudentClassParam studentClass){
      var classRoom=  classRepo.findById(studentClass.classId());
      if (classRoom.isEmpty()) throw new AppException(404,"Class with ID "+studentClass.classId()+" does not exist");
      if(!studentRepo.existsById(studentClass.studentId()))throw new AppException(404,"Student with ID "+studentClass.studentId()+" does not exist");
      studentRepo.assignClass(classRoom.get(),studentClass.studentId());
      //update class size
        classRepo.updateClassSize(classRoom.get().getSize()-1,classRoom.get().getId());
      return studentRepo.findById(studentClass.studentId()).get();
    }

    public StudentModel unAssignClass(Long id){
        var stud = studentRepo.findById(id)
                .orElseThrow(()-> new AppException(404,"Student with ID "+id.toString()+" does not exist"));
        classRepo.updateClassSize(stud.getClassRoom().getSize()+1,stud.getClassRoom().getId());
        studentRepo.assignClass(null,id);
        stud.setClassRoom(null);
        //update class size
        return stud;
    }

    public List<SubjectModel> getStudentSubject(Long studentId){
        //get student class
      var student=  studentRepo.findById(studentId)
              .orElseThrow(()-> new AppException(404,"Student with ID "+studentId.toString()+" does not exist"));
       var classRoom= student.getClassRoom();
       if(classRoom == null) throw  new AppException(400,"Student has not been assigned to a class yet");
       return classRoom.getSubjects();
    }
}
