package io.dotsehyde.schoolsystem.Repository;

import io.dotsehyde.schoolsystem.Models.TeacherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo extends JpaRepository<TeacherModel,Long> {
}
