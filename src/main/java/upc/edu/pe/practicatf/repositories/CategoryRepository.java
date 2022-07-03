package upc.edu.pe.practicatf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import upc.edu.pe.practicatf.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
