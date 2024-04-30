package com.quocnguyen.studentmanagement.services;

import com.quocnguyen.studentmanagement.entities.Student;
import com.quocnguyen.studentmanagement.entities.StudentDTO;
import com.quocnguyen.studentmanagement.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    private final StudentRepository repository;

    public Page<StudentDTO> getStudents(Pageable pageable) {

        var result = repository.findAll(pageable);
//        log.debug(result.getPageable().toString());
//        log.debug(result.getSort().toString());
        System.out.println(result.getSort());
        return repository
                .findAll(pageable)
                .map(StudentDTO::new);
    }

    public StudentDTO getStudentById(int id) {

        final Student student = repository.findById(id).orElseThrow(null);

        return  new StudentDTO(student);
    }

}
