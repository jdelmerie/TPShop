package fr.fms.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.fms.dao.ArticleRepository;
import fr.fms.dao.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@Service
public class IBShopImpl implements IBShop {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public List<Article> getAllArticles() {
		return articleRepository.findAll();
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Article getArticle(long id) {
		return articleRepository.getReferenceById(id)  ;
	}

	@Override
	public Category getCategory(long id) {
		return categoryRepository.getReferenceById(id);
	}

	@Override
	public Article addAndUpdateArticle(Article article) {
		return articleRepository.save(article);
	}

	@Override
	public void deleteArticle(long id) {
		articleRepository.deleteById(id);
	}

	@Override
	public Category addAnsUpdateCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(long id) {
		categoryRepository.deleteById(id);
	}



}
