package upc.edu.pe.practicatf.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.practicatf.entities.Category;
import upc.edu.pe.practicatf.entities.Course;
import upc.edu.pe.practicatf.exception.ResourceNotFoundException;
import upc.edu.pe.practicatf.repositories.CategoryRepository;
import upc.edu.pe.practicatf.repositories.CourseRepository;
import upc.edu.pe.practicatf.services.CourseService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Course createCourse(Long categoryId, Course course) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));

        return courseRepository.save(course.setCategory(category));
    }

    @Override
    public List<Course> getAllCoursesByCategoryId(Long categoryId) {
        return courseRepository.findCoursesByCategoryId(categoryId);
    }

    @Override
    public List<Course> findAll() throws Exception {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long aLong) throws Exception {

        return courseRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
    }

    @Override
    public Course update(Course entity, Long aLong) throws Exception {
        Course course=courseRepository.findById(aLong).orElseThrow(()->new ResourceNotFoundException("Course","Id",aLong));
        course.setPrice(entity.getPrice());
        course.setName(entity.getName());

        return courseRepository.save(course);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Course course=courseRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Course","Id",aLong));
        courseRepository.deleteById(aLong);
    }
}
