package upc.edu.pe.practicatf.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upc.edu.pe.practicatf.entities.Category;
import upc.edu.pe.practicatf.entities.Course;
import upc.edu.pe.practicatf.exception.ResourceNotFoundException;
import upc.edu.pe.practicatf.repositories.CategoryRepository;
import upc.edu.pe.practicatf.services.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() throws Exception {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long aLong) throws Exception {
        return categoryRepository.findById(aLong).orElseThrow(()->new ResourceNotFoundException(""));
    }

    @Override
    public Category update(Category entity, Long aLong) throws Exception {
        Category category=categoryRepository.findById(aLong).orElseThrow(()->new ResourceNotFoundException(""));
        category.setName(entity.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Category category=categoryRepository.findById(aLong).orElseThrow(()->new ResourceNotFoundException(""));
        categoryRepository.deleteById(aLong);
    }
}
