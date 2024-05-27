package io.dotsehyde.schoolsystem.Repository;

import io.dotsehyde.schoolsystem.Models.ClassModel;
import io.dotsehyde.schoolsystem.Models.TeacherModel;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepo extends JpaRepository<ClassModel,Long> {
    @Transactional
    @Modifying
    @Query("update ClassModel c set c.size=?1 where c.id=?2")
    void updateClassSize(int size,Long id);

    @Query("SELECT c FROM ClassModel c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR CAST(c.size AS string) = :searchTerm")
    Page<ClassModel> findAllByNameContainsOrSizeEquals( Pageable pageable,@Param("searchTerm") String searchTerm);

    @Transactional
    @Modifying
    @Query("update ClassModel c set c.name=?1, c.size=?2 where c.id=?3")
    int updateClass(String name,int size,Long id);

    @Transactional
    @Modifying
    @Query("update ClassModel c set c.teacher=?1 where c.id=?2")
    int assignTeacher(TeacherModel teacherModel,Long id);
}
