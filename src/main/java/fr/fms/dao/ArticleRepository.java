package fr.fms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.fms.entities.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	public List<Article> findByBrand(String brand);

	public List<Article> findByCategoryId(Long categoryId);

	public List<Article> findByDescriptionContainsAndBrandContains(String description, String brand);

	public void deleteById(Long articleId); 
}
