package com.quocnguyen.studentmanagement.repositories;

import com.quocnguyen.studentmanagement.entities.CourseStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Integer> {
    CourseStudent findFirstByCourse_IdAndStudent_Id(int courseId, int studentId);
}
