package com.quocnguyen.studentmanagement.services;


import com.quocnguyen.studentmanagement.entities.*;
import com.quocnguyen.studentmanagement.exceptions.ResourceNotFoundException;
import com.quocnguyen.studentmanagement.repositories.CourseRepository;
import com.quocnguyen.studentmanagement.repositories.CourseStudentRepository;
import com.quocnguyen.studentmanagement.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository repository;
    private final StudentRepository studentRepository;
    private final CourseStudentRepository courseStudentRepository;


    public Page<CourseDTO> getCourses(Pageable pageable, String keyword) {
        Page<Course> courses;
        if(keyword.matches("^(19|20)\\d{2}$")) { // check if the keyword is year formatted
            int year = Integer.parseInt(keyword);
            courses = repository.findByYearEquals(year, pageable);
        } else {
            courses = repository.findByNameContainingIgnoreCase(keyword, pageable);
        }

        return courses.map(CourseDTO::new);
    }

    @Transactional
    public CourseDTO create(CourseDTO course) {
        Course newCourse = Course
                .builder()
                .name(course.getName())
                .lecture(course.getLecture())
                .year(course.getYear())
                .notes(course.getNotes())
                .students(new ArrayList<>())
                .build();


        repository.save(newCourse);

        log.debug(newCourse.getStudents().toString());

        return new CourseDTO(newCourse);
    }

    @Transactional
    public void delete(int id) {
        Course course = repository.findById(id).orElseThrow(ResourceNotFoundException::new);

        repository.delete(course);
    }

    public CourseDTO getById(int id) {
        Course course = repository.findById(id).orElseThrow(ResourceNotFoundException::new);

        return new CourseDTO(course);
    }

    public List<StudentDTO> getAvailableStudent(int courseId, String keyword) {

        if(keyword.matches("^[1-9][0-9]*$")) {
            return  studentRepository.findStudentNotJoinedCourseById(courseId, Integer.parseInt(keyword))
                    .stream().map(StudentDTO::new).toList();
        }

        return studentRepository
                .findStudentNotJoinedCourseByName(courseId, keyword)
                .stream()
                .map(StudentDTO::new).toList();
    }

    @Transactional
    public CourseStudentDTO addStudentToCourse(CourseStudentDTO courseStudent) {
        final Student student = studentRepository.findById(courseStudent.getStudentId()).orElseThrow(ResourceNotFoundException::new);
        final Course course = repository.findById(courseStudent.getCourseId()).orElseThrow(ResourceNotFoundException::new);

        CourseStudent entity = CourseStudent
                .builder()
                .student(student)
                .course(course)
                .grade(courseStudent.getGrade())
                .build();

        courseStudentRepository.save(entity);

        return new CourseStudentDTO(entity);
    }

    @Transactional
    public CourseDTO update(int id, CourseDTO course) {

        Course storedCourse = repository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        storedCourse.setName(course.getName());
        storedCourse.setLecture(course.getLecture());
        storedCourse.setNotes(course.getNotes());
        storedCourse.setYear(course.getYear());

        repository.save(storedCourse);

        return new CourseDTO(storedCourse);
    }

    @Transactional
    public void deleteStudentFromCourse(int courseId, int studentId) {
        CourseStudent enrollment = courseStudentRepository.findFirstByCourse_IdAndStudent_Id(courseId, studentId);

        if(enrollment == null) {
            throw new ResourceNotFoundException();
        }

        courseStudentRepository.delete(enrollment);
    }

    @Transactional
    public CourseStudentDTO updateStudentInCourse(CourseStudentDTO courseStudent) {
        CourseStudent enrollment = courseStudentRepository.findFirstByCourse_IdAndStudent_Id(
                courseStudent.getCourseId(),
                courseStudent.getStudentId());

        if(enrollment == null) {
            throw new ResourceNotFoundException();
        };

        enrollment.setGrade(courseStudent.getGrade());

        courseStudentRepository.save(enrollment);

        return new CourseStudentDTO(enrollment);
    }
}
