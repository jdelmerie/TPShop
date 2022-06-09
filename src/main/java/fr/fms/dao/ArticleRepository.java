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

	public void deleteById(Long articleId); //

//	@Query("update article SET brand = :brand, description = :description, price = :price, category_id = :cat_id WHERE id = :id")
//	public void updateById(@Param("brand") String brand, @Param("description") String description,
//			@Param("price") float price, @Param("cat_id") Long categoryId, @Param("id") Long id);

//	@Modifying
//	@Query("update User u set u.firstname = ?1 where u.lastname = ?2")
//	int setFixedFirstnameFor(String firstname, String lastname);
}
