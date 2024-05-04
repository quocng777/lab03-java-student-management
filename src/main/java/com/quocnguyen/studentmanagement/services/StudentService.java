package com.quocnguyen.studentmanagement.services;

import com.quocnguyen.studentmanagement.entities.CourseStudent;
import com.quocnguyen.studentmanagement.entities.Student;
import com.quocnguyen.studentmanagement.entities.StudentDTO;
import com.quocnguyen.studentmanagement.exceptions.ResourceNotFoundException;
import com.quocnguyen.studentmanagement.repositories.CourseStudentRepository;
import com.quocnguyen.studentmanagement.repositories.StudentRepository;
import jakarta.transaction.Transactional;
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
    private final CourseStudentRepository courseStudentRepository;

    public Page<StudentDTO> getStudents(Pageable pageable, String keyword) {

        if(keyword.matches("^[1-9][0-9]*$")) {
            return  repository.findAllByIdEquals(Integer.parseInt(keyword), pageable)
                    .map(StudentDTO::new);
        }

        return repository
                .findByNameContainingIgnoreCase(keyword, pageable)
                .map(StudentDTO::new);
    }

    public StudentDTO getStudentById(int id) {

        final Student student = repository.findById(id).orElseThrow(ResourceNotFoundException::new);
        log.debug(student.getCourses().toString());
        return  new StudentDTO(student);
    }

    @Transactional
    public StudentDTO create(StudentDTO student) {

        Student entity = Student
                .builder()
                .name(student.getName())
                .birthday(student.getBirthday())
                .address(student.getAddress())
                .name(student.getName())
                .build();

        repository.save(entity);
        //logging
        log.debug(entity.getId().toString());

        return new StudentDTO(entity);
    }

    @Transactional
    public StudentDTO update(int id, StudentDTO student) {

        Student storedStudent = repository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        storedStudent.setName(student.getName());
        storedStudent.setBirthday(student.getBirthday());
        storedStudent.setNotes(student.getNotes());
        storedStudent.setAddress(student.getAddress());

        repository.save(storedStudent);

        return student;
    }

    @Transactional
    public void delete(int id) {
        Student storedStudent = repository.findById(id).orElseThrow(ResourceNotFoundException::new);

        repository.delete(storedStudent);
    }

    @Transactional
    public void deleteStudentFromCourse(int courseId, int studentId) {
        CourseStudent courseStudent = courseStudentRepository.findFirstByCourse_IdAndStudent_Id(courseId, studentId);

        if(courseStudent == null) {
            throw new ResourceNotFoundException();
        }

        courseStudentRepository.delete(courseStudent);
    }

}
