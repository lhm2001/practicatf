package upc.edu.pe.practicatf.services;

import upc.edu.pe.practicatf.entities.Category;
import upc.edu.pe.practicatf.entities.Course;

public interface CategoryService extends CrudService<Category,Long> {

    Category createCategory(Category category);

}
