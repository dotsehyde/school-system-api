package io.dotsehyde.schoolsystem.Services;

import io.dotsehyde.schoolsystem.Config.Error.AppException;
import io.dotsehyde.schoolsystem.Models.StudentModel;
import io.dotsehyde.schoolsystem.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private final StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo){
        this.studentRepo= studentRepo;
    }

    @Value("${sms.media.path}")
    private String mediaDir;
    @Value("${sms.base.url}")
    private String baseUrl;

    public StudentModel createStudent(StudentModel student)  {
            return studentRepo.save(student);
    }

    public StudentModel getStudentById(Long id){
            Optional<StudentModel> optStud = studentRepo.findById(id);
            if(optStud.isPresent()){
                return optStud.get();
            }else {
                throw new AppException(400,"Student with" + id.toString() + " not found");
            }
    }

    public Page<StudentModel> getAllStudents(Optional<Integer> page,Optional<Integer> limit,Optional<String> sort){
       String dir = sort.orElse("id,desc").split(",")[0];
        String prop = sort.orElse("id,desc").split(",")[1];
        return studentRepo.findAll(
                PageRequest.of(page.orElse(0), limit.orElse(10), Sort.by(
                        dir.equals("desc")?Sort.Direction.DESC: Sort.Direction.ASC,prop
                ))
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
        if(!studentRepo.existsById(id)) throw  new AppException(400, "Student with Id"+id.toString()+" does not exist");
        studentRepo.deleteById(id);
        return new HashMap<String,String>()
                {{put("data","Student deleted successfully");}};
    }

    public StudentModel uploadStudentPhoto(MultipartFile photo,Long id) {
        try {
            if(!studentRepo.existsById(id)) throw new AppException(404,"Student with ID "+id.toString()+" does not exist");
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
            // Create the file path
            Path filePath = mediaPath.resolve(generatedFileName);
            if(filePath.toFile().exists()){
                filePath.toFile().delete();
;            }
            photo.transferTo(filePath.toFile());
            // Generate the new photo URL
            String newPhotoUrl = baseUrl + "/media/" + generatedFileName;
            int res = studentRepo.updateStudentPhoto(newPhotoUrl,id);
            if(res >0){
                return studentRepo.findById(id).get();
            }else{
                throw new AppException(500,"Could not update student photo");
            }
        } catch (Exception e) {
            throw new AppException(500, e.getMessage());
        }
    }
}
