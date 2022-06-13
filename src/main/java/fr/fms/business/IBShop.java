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
	public List<Article> getAllArticles() throws Exception;

	/**
	 * List of all Categories
	 * 
	 * @return
	 */
	public List<Category> getAllCategories() throws Exception;

	/**
	 * Get article by ID
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Article getArticle(long id) throws Exception;

	/**
	 * Get category by ID
	 * 
	 * @param id
	 * @return
	 */
	public Category getCategory(long id) throws Exception;

	/**
	 * Add and update article (using save)
	 * 
	 * @param article
	 * @return
	 */
	public Article addAndUpdateArticle(Article article) throws Exception;

	/**
	 * Delete article by ID
	 * 
	 * @param id
	 */
	public void deleteArticle(long id) throws Exception;

	/**
	 * And and update Category
	 * 
	 * @param category
	 * @return
	 */
	public Category addAndUpdateCategory(Category category) throws Exception;

	/**
	 * Delete category by ID
	 * 
	 * @param id
	 */
	public void deleteCategory(long id) throws Exception;

	/**
	 * List of article by pages
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<Article> getAllByPages(Pageable pageable) throws Exception;

	/**
	 * List of articles by category
	 * 
	 * @param id
	 * @return
	 */
	public List<Article> getArticlesByCategory(long id) throws Exception;
}
