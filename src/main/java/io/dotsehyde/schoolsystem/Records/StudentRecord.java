package io.dotsehyde.schoolsystem.Records;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public record StudentRecord() {
    public record StudentClassParam(
            @NotNull
            Long studentId,
            @NotNull
            Long classId
    ) {}
    public record StudentListItem(Long id, String firstName, String lastName, String address, String photoUrl) {
    }
    public record StudentCreateParam(@NotNull String firstName, @NotNull String lastName, Optional<String> address,
                                     Optional<String> photoUrl, Optional<Long> classId) {
    }
}
