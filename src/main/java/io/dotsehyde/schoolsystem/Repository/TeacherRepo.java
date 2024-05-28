package io.dotsehyde.schoolsystem.Repository;

import io.dotsehyde.schoolsystem.Models.TeacherModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo extends JpaRepository<TeacherModel,Long> {
    @Query("SELECT TeacherModel from TeacherModel t where t.firstName like lower(concat('%',:search,'%') ) or" +
            " t.lastName like lower(concat('%',:search,'%') ) or t.address like lower(concat('%',:search,'%') )")
    Page<TeacherModel> searchTeacher(@Param("search") String search, Pageable pageable);
}
