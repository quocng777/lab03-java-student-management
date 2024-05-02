package com.quocnguyen.studentmanagement.repositories;

import com.quocnguyen.studentmanagement.entities.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Student> findAllByIdEquals(Integer id, Pageable pageable);

    @Query("select distinct s " +
            "from Student s " +
            "where s not in ( " +
                "select s1 " +
                "from Student  s1 " +
                "join CourseStudent c " +
                "on(s1.id = c.student.id)" +
                "where c.course.id = ?1) " +
            "and s.id = ?2 " +
            "order by s.id asc ")
    List<Student> findStudentNotJoinedCourseById(Integer courseId, Integer studentId);

    @Query("select distinct s " +
            "from Student s " +
            "where s not in ( " +
                "select s1 " +
                "from Student  s1 " +
                "join CourseStudent c " +
                "on(s1.id = c.student.id)" +
                "where c.course.id = ?1) " +
            "and lower(s.name) like lower(concat('%',?2,'%')) " +
            "order by s.id asc ")
    List<Student> findStudentNotJoinedCourseByName(Integer courseId, String name);

}
