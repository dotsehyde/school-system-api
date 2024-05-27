package io.dotsehyde.schoolsystem.Repository;

import io.dotsehyde.schoolsystem.Models.ClassModel;
import io.dotsehyde.schoolsystem.Models.StudentModel;
import io.dotsehyde.schoolsystem.Models.SubjectModel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Transactional
    @Modifying
    @Query("UPDATE StudentModel s SET s.classRoom=?1 WHERE s.id=?2")
    int assignClass(ClassModel classRoom,Long id);

    @Query("SELECT s from StudentModel s WHERE lower(s.firstName) LIKE lower(concat('%',:searchTerm,'%')) OR " +
            "lower(s.lastName) like lower(concat('%',:searchTerm,'%')) or" +
            " lower(s.address) like lower(concat('%',:searchTerm,'%'))")
    Page<StudentModel> searchStudent(@Param("searchTerm") String search, Pageable pageable);

}
