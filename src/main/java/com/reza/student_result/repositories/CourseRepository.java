package com.reza.student_result.repositories;

import com.reza.student_result.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    @Query("SELECT c FROM Course c WHERE " +
            "(LOWER(c.courseCode) = LOWER(:courseCode)) AND (c.recordStatus <> 'DELETED')"
    )
    Optional<Course> findByCourseCode(String courseCode);
    @Query("SELECT c FROM Course c WHERE " +
            "(c.id = :id) AND (c.recordStatus <> 'DELETED')"
    )
    Optional <Course> findCourseById(Long id);
    Optional <Course> findById(Long id);

    @Query("SELECT c FROM Course c WHERE " +
            "(LOWER(c.courseTitle) = LOWER(:courseTitle)) AND (c.recordStatus <> 'DELETED')"
    )
    Optional<Course> findByCourseTitle(String courseTitle);
}