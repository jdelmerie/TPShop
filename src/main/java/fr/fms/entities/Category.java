package fr.fms.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;

@Entity
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	@OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST) // une catégorie est liée à plusieurs articles
	private Collection<Article> articles;

	@PreRemove
	private void preRemove() {
		articles.forEach(article -> article.setCategory(null));
	}

	public Category(String name) {
		this.name = name;
	}

	public Category() {
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
