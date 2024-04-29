package com.quocnguyen.studentmanagement.services;

import com.quocnguyen.studentmanagement.dto.StudentDTO;
import com.quocnguyen.studentmanagement.entities.Student;
import com.quocnguyen.studentmanagement.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    private static final int NUMBER_STUDENT_PER_PAGE = 12;

    public List<StudentDTO> getStudents(int page, String sortBy, String sortDir) {

        Sort sort = sortDir.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable paging = PageRequest.of(page, NUMBER_STUDENT_PER_PAGE, sort);

        return repository.findAll(paging)
                .getContent()
                .stream()
                .map(StudentDTO::new)
                .toList();
    }

}
