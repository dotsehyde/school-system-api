package io.dotsehyde.schoolsystem.Repository;

import io.dotsehyde.schoolsystem.Models.StudentModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<StudentModel,Long> {

    @Transactional
    @Modifying
    @Query("UPDATE StudentModel s SET s.firstName=?1,s.lastName=?2,s.address=?3 WHERE s.id=?4")
    int updateStudent(String firstName,String lastName,String address,Long id);

    @Transactional
    @Modifying
    @Query("UPDATE StudentModel s SET s.photoUrl=?1 where s.id=?2")
    int updateStudentPhoto(String photoUrl,Long id);

}
