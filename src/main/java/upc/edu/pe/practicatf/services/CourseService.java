package upc.edu.pe.practicatf.services;

import upc.edu.pe.practicatf.entities.Course;

import java.util.List;

public interface CourseService extends CrudService<Course,Long>{

    Course createCourse(Long categoryId,Course course);

    List<Course> getAllCoursesByCategoryId(Long categoryId);
}
