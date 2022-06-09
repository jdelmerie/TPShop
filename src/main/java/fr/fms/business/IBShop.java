package fr.fms.business;


import java.util.List;

import fr.fms.entities.Article;
import fr.fms.entities.Category;

public interface IBShop {
	public List<Article> getAllArticles(); 
	public List<Category> getAllCategories();
	public Article getArticle(long id);
	public Category getCategory(long id);
	public Article addAndUpdateArticle(Article article);
	public void deleteArticle(long id);
	public Category addAnsUpdateCategory(Category category);
	public void deleteCategory(long id); 
}
