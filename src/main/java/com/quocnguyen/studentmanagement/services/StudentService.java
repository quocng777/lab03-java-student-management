package com.quocnguyen.studentmanagement.services;

import com.quocnguyen.studentmanagement.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;


}
