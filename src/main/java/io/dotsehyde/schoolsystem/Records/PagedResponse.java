package io.dotsehyde.schoolsystem.Records;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public record PagedResponse<T>(
        int size,
        Long totalElements,
        int totalPages,
        int numberOfElements,
        Sort sort,
        List<T> content

) {
}
