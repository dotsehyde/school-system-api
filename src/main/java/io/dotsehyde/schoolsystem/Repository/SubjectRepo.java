package io.dotsehyde.schoolsystem.Repository;

import io.dotsehyde.schoolsystem.Models.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectModel,Long> {
}
