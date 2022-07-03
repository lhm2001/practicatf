package upc.edu.pe.practicatf.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import upc.edu.pe.practicatf.entities.Category;
import upc.edu.pe.practicatf.entities.Course;
import upc.edu.pe.practicatf.services.CategoryService;
import upc.edu.pe.practicatf.services.CourseService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CourseService courseService;

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Course>> fetchAll(){
        try{
            List<Course> courses=courseService.findAll();
            return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/categories/{categoryId}/courses")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course, @PathVariable("categoryId") Long categoryId,BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Course courseDB= courseService.createCourse(categoryId,course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDB);
    }

    @GetMapping(value = "/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Long courseId){
        try {
            Course course = courseService.findById(courseId);
            if (course!=null){
                return new ResponseEntity<Course>(course, HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable("courseId") Long courseId, @Valid @RequestBody Course course) throws Exception {
        log.info("Actualizando Course con Id {}", courseId);
        Course currentCourse = courseService.update(course, courseId);
        return ResponseEntity.ok(currentCourse);
    }

    @DeleteMapping(path = "/{courseId}")
    public void deleteCourse(@PathVariable("courseId") Long courseId) throws Exception {
        log.info("Eliminando Course con Id {}", courseId);
        courseService.deleteById(courseId);
    }

    private String formatMessage( BindingResult result){

        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
