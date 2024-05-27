package io.dotsehyde.schoolsystem.Records;

import jakarta.validation.constraints.NotNull;

public record ClassRecord() {
    public record ClassListItem(Long id, String name,int size) {
    }

    public record ClassTeacher(@NotNull Long classId,@NotNull Long teacherId){}

}
