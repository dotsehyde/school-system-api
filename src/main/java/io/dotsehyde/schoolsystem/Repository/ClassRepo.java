package io.dotsehyde.schoolsystem.Repository;

import io.dotsehyde.schoolsystem.Models.ClassModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepo extends JpaRepository<ClassModel,Long> {
}
