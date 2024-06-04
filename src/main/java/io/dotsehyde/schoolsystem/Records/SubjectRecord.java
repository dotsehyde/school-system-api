package io.dotsehyde.schoolsystem.Records;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.util.Optional;

public record SubjectRecord() {
    public  record CreateSubjectParam(
            @NotNull String name,
            Optional<Long> teacherId
    ){}
}
