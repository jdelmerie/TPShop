package fr.fms.business;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import fr.fms.entities.Article;
import fr.fms.entities.Category;

public interface IBShop {

	/**
	 * List of all articles
	 * 
	 * @return
	 */
	public List<Article> getAllArticles();

	/**
	 * List of all Categories
	 * 
	 * @return
	 */
	public List<Category> getAllCategories();

	/**
	 * Get article by ID
	 * 
	 * @param id
	 * @return
	 */
	public Article getArticle(long id);

	/**
	 * Get category by ID
	 * 
	 * @param id
	 * @return
	 */
	public Category getCategory(long id);

	/**
	 * Add and update article (using save)
	 * 
	 * @param article
	 * @return
	 */
	public Article addAndUpdateArticle(Article article);

	/**
	 * Delete article by ID
	 * 
	 * @param id
	 */
	public void deleteArticle(long id);

	/**
	 * And and update Category
	 * 
	 * @param category
	 * @return
	 */
	public Category addAndUpdateCategory(Category category);

	/**
	 * Delete category by ID
	 * 
	 * @param id
	 */
	public void deleteCategory(long id);

	/**
	 * List of article by pages
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<Article> getAllByPages(Pageable pageable);

	/**
	 * List of articles by category
	 * 
	 * @param id
	 * @return
	 */
	public List<Article> getArticlesByCategory(long id);
}
