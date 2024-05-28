package io.dotsehyde.schoolsystem.Records;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Optional;

public record TeacherRecord() {
    @Builder
    public record TeacherCreateParam(@NotNull String firstName, @NotNull String lastName,
                                     Optional<String> photoUrl,Optional<String> address,Optional<Long> classId){}

   @Builder
    public record TeacherItem(Long id, String firstName,String lastName,String photoUrl, String address){}
}
