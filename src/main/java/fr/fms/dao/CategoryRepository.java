package fr.fms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.fms.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Category findByName(String name);

	public List<Category> findAllByOrderByNameAsc();

	public List<Category> findAllByOrderByNameDesc();
}
