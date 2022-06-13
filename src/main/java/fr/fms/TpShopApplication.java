package fr.fms;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import fr.fms.business.IBShopImpl;
import fr.fms.entities.Article;
import fr.fms.entities.Category;

@SpringBootApplication
public class TpShopApplication implements CommandLineRunner {
	private static Scanner scan = new Scanner(System.in);

	@Autowired
	private IBShopImpl ibShopImpl;

	public static void main(String[] args) {
		SpringApplication.run(TpShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		populateDB();

		int choice = 0;
		while (choice != 6) {
			displayMenu();
			switch (choice = input()) {
			case 1:
				displayAllArticles();
				break;
			case 2:
				getArticlesByPages();
				break;
			case 3:
				displayCatogories();
				System.out
						.println("Quelle catégorie d'articles souhaitez-vous consulter ? [Saisir l'ID correspondant]");
				List<Article> articles = ibShopImpl.getArticlesByCategory((long) input());
				displayArticles(articles);
				break;
			case 4:
				gestionArticle(0);
				break;
			case 5:
				gestionCategory(0);
				break;
			case 6:
				System.out.println("Bye, à bientôt");
				break;
			default:
				System.out.println("Mauvaise saisie, recommencez !");
			}
		}
		scan.close();
	}

	/**
	 * Display list of all articles
	 */
	public void displayAllArticles() {
		try {
			List<Article> articles = ibShopImpl.getAllArticles();
			if (articles.size() > 0) {
				displayArticles(articles);
			} else {
				System.out.println("VOUS N'AVEZ PAS D'ARTICLE");
			}
		} catch (Exception e) {
			System.out.println("Erreur de communication avec la base : " + e.getMessage());
		}
	}

	/**
	 * Display all categories
	 */
	public void displayCatogories() {
		try {
			List<Category> categories = ibShopImpl.getAllCategories();
			if (categories.size() > 0) {
				System.out.format(lineCategory);
				System.out.format(headerCategory);
				System.out.format(lineCategory);
				categories.forEach(category -> {
					System.out.format(formatCategory, category.getId(), category.getName());
				});
				System.out.format(lineCategory);
				System.out.println();
			} else {
				System.out.println("VOUS N'AVEZ PAS DE CATEGORIES");
			}

		} catch (Exception e) {
			System.out.println("Erreur de communication avec la base : " + e.getMessage());
		}
	}

	/**
	 * Sub menu CRUD Article
	 * 
	 * @param choice
	 */
	public void gestionArticle(int choice) {
		while (choice != 4) {
			displaySousMenu("articles");
			switch (choice = input()) {
			case 1:
				addArticle();
				break;
			case 2:
				updateArticle();
				break;
			case 3:
				deleteArticle();
				break;
			case 4:
				System.out.println("Retour au menu principal");
				break;
			default:
				System.out.println("Mauvaise saisie, recommencez !");
			}
		}
	}

	/**
	 * Add an article
	 */
	public void addArticle() {
		System.out.println("Ajouter un article");
		System.out.println("------------------");
		System.out.println("Veillez saisir la marque :");
		String brand = inputStr();
		System.out.println("Veillez saisir la description :");
		String description = inputStr();
		System.out.println("Veillez saisir le prix :");
		double price = inputDouble();
		System.out.println("Veillez choisir la catégorie :");
		displayCatogories();
		Category category = ibShopImpl.getCategory((long) input());
		ibShopImpl.addAndUpdateArticle(new Article(description, brand, price, category));
		displayAllArticles();
	}

	/**
	 * Update an article
	 */
	public void updateArticle() {
		System.out.println("Modifier un article");
		System.out.println("-------------------");
		System.out.println("Quel article souhaitez-vous modifier ? [Saisir l'ID correspondant]");
		displayAllArticles();
		Article article = ibShopImpl.getArticle((long) input());
		System.out.println("Veillez saisir la marque :");
		String brandToUpdate = inputStr();
		article.setBrand(brandToUpdate);
		System.out.println("Veillez saisir la description :");
		String descriptionToUpdate = inputStr();
		article.setDescription(descriptionToUpdate);
		System.out.println("Veillez saisir le prix :");
		double priceToUpdate = inputDouble();
		article.setPrice(priceToUpdate);
		System.out.println("Veillez choisir la catégorie :");
		displayCatogories();
		Category categoryToUpdate = ibShopImpl.getCategory((long) input());
		article.setCategory(categoryToUpdate);
		ibShopImpl.addAndUpdateArticle(article);
		displayAllArticles();
	}

	/**
	 * Delete an article
	 */
	public void deleteArticle() {
		System.out.println("Supprimer un article");
		System.out.println("--------------------");
		System.out.println("Quel article souhaitez-vous supprimer ? [Saisir l'ID correspondant]");
		displayAllArticles();
		ibShopImpl.deleteArticle((long) input());
		displayAllArticles();
	}

	/**
	 * Sub menu CRUD Category
	 * 
	 * @param choice
	 */
	public void gestionCategory(int choice) {
		while (choice != 4) {
			displaySousMenu("catégories");
			choice = input();
			switch (choice) {
			case 1:
				addCateogry();
				break;
			case 2:
				updateCategory();
				break;
			case 3:
				deleteCategory();
				break;
			case 4:
				System.out.println("Retour au menu principal");
				break;
			default:
				System.out.println("Mauvaise saisie, recommencez !");
			}
		}
	}

	/**
	 * Add a category
	 */
	public void addCateogry() {
		System.out.println("Ajouter une catégorie");
		System.out.println("---------------------");
		System.out.println("Veillez saisir le nom :");
		String name = inputStr();
		ibShopImpl.addAndUpdateCategory(new Category(name));
		displayCatogories();
	}

	/**
	 * Update a category
	 */
	public void updateCategory() {
		System.out.println("Modifier une catégorie");
		System.out.println("----------------------");
		System.out.println("Quelle catégorie souhaitez-vous modifier ? [Saisir l'ID correspondant]");
		displayCatogories();
		Category category = ibShopImpl.getCategory((long) input());
		System.out.println("Veillez saisir le nom : ");
		String nameToChange = inputStr();
		category.setName(nameToChange);
		ibShopImpl.addAndUpdateCategory(category);
		displayCatogories();
	}

	/**
	 * Delete a category
	 */
	public void deleteCategory() {
		// TODO: demander à l'user s'il veut mettre article à null ou supprimer tous les
		// articles
		System.out.println("Supprimer une catégorie");
		System.out.println("-----------------------");
		System.out.println("Quelle catégorie souhaitez-vous supprimer ? [Saisir l'ID correspondant]");
		displayCatogories();
		ibShopImpl.deleteCategory((long) input());
		displayCatogories();
	}

	/**
	 * Display main menu
	 */
	public static void displayMenu() {
		System.out.println("+------------------------------------------------------------+");
		System.out.println("|                     Menu principal                         |");
		System.out.println("+------------------------------------------------------------+");
		System.out.println("Que souhaitez-vous faire ? [Saisir le chiffre correspondant]");
		System.out.println("[1] - Afficher tous les articles");
		System.out.println("[2] - Afficher tous les articles avec pagination");
		System.out.println("[3] - Afficher tous les articles d'une catégorie");
		System.out.println("[4] - Gérer un article");
		System.out.println("[5] - Gérer une catégorie");
		System.out.println("[6] - Quitter l'application");
	}

	/**
	 * Display sub menu according to the type (article or category)
	 * 
	 * @param str
	 */
	public void displaySousMenu(String str) {
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("Gestion des " + str + " : Que souhaitez-vous faire ? [Saisir le chiffre correspondant]");
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.println("[1] - Ajouter");
		System.out.println("[2] - Modifier");
		System.out.println("[3] - Supprimer");
		System.out.println("[4] - Retour");
	}

	/**
	 * Display and format articles
	 * 
	 * @param articles
	 */
	private void displayArticles(List<Article> articles) {
		System.out.format(lineArticle);
		System.out.format(headerArticle);
		System.out.format(lineArticle);
		;
		articles.forEach(article -> {
			System.out.format(formatArticle, article.getId(), article.getBrand(), article.getDescription(),
					article.getCategory() != null ? article.getCategory().getName() : "N/A", article.getPrice());
		});
		System.out.format(lineArticle);
	}

	/**
	 * Display articles by page of 5 each
	 */
	public void getArticlesByPages() {
		int currentPage = 0;
		int itemPerPage = 5;
		boolean flag = true;

		while (flag) {
			Page<Article> articles = ibShopImpl.getAllByPages(PageRequest.of(currentPage, itemPerPage));
			if (articles.getTotalPages() != 0) {
				displaySousMenuPagination();

				displayArticles(articles.getContent());

				System.out.print("\n PREV [");

				for (int i = 0; i < articles.getTotalPages(); i++) {
					if (i == currentPage) {
						System.out.print(" {" + i + "} ");
					} else {
						System.out.print(" " + i + " ");
					}
				}
				System.out.println("] NEXT");

				String action = inputStr();
				if (action.equalsIgnoreCase("PREV")) {
					if (currentPage > 0) {
						currentPage--;
					}
				} else if (action.equalsIgnoreCase("NEXT")) {
					if (currentPage < articles.getTotalPages() - 1) {
						currentPage++;
					}
				} else if (action.equalsIgnoreCase("EXIT")) {
					flag = false;
				} else if (action.equalsIgnoreCase("PAGE")) {
					System.out.println("Saisissez le nombre d'articles par page :");
					itemPerPage = input();
					currentPage = 0;
				} else if (action.equalsIgnoreCase("0")) {
					displaySousMenuPagination();
				}
			} else {
				System.out.println("TABLE VIDE EN BDD !");
				flag = false;
			}
		}
	}

	/**
	 * Display sub meny pagintation
	 */
	private void displaySousMenuPagination() {
		System.out.println("PREV    pour afficher la page précédente");
		System.out.println("NEXT    pour afficher la page suivante");
		System.out.println("EXIT    pour sortir de la pagination");
		System.out.println("PAGE    pour afficher 7 articles par page (par défaut c'est 5)");
	}

	/**
	 * Get input int from scan
	 * 
	 * @return
	 */
	private static int input() {
		int choice;
		while (scan.hasNextInt() == false)
			scan.next();
		choice = scan.nextInt();
		return choice;
	}

	/**
	 * Get input double from scan
	 * 
	 * @return
	 */
	private static double inputDouble() {
		double price;
		while (scan.hasNextDouble() == false)
			scan.next();
		price = scan.nextDouble();
		return price;
	}

	/**
	 * Get input string from scan
	 * 
	 * @return
	 */
	private static String inputStr() {
		String str;
		while (scan.hasNextLine() == false)
			scan.next();
		str = scan.next();
		return str;
	}

	public void populateDB() {
//		Category smartphone = ibShopImpl.addAndUpdateCategory(new Category("Smartphone"));
//		Category tablet = ibShopImpl.addAndUpdateCategory(new Category("Tablet"));
//		Category pc = ibShopImpl.addAndUpdateCategory(new Category("PC"));
//
//		ibShopImpl.addAndUpdateArticle(new Article("S11", "Samsung", 750, smartphone));
//		ibShopImpl.addAndUpdateArticle(new Article("MI10", "Xiaomi", 350, smartphone));
//		ibShopImpl.addAndUpdateArticle(new Article("Iphone", "Apple", 950, smartphone));
//		ibShopImpl.addAndUpdateArticle(new Article("MI11", "Xiaomi", 850, smartphone));
//		ibShopImpl.addAndUpdateArticle(new Article("S10", "Samsung", 450, smartphone));
//		ibShopImpl.addAndUpdateArticle(new Article("MI9", "Xiaomi", 150, smartphone));
//
//		ibShopImpl.addAndUpdateArticle(new Article("ipad", "Apple", 1250, tablet));
//		ibShopImpl.addAndUpdateArticle(new Article("Galaxy A8", "Samsung", 250, tablet));
//		ibShopImpl.addAndUpdateArticle(new Article("Galaxy S8", "Samsung", 1450, tablet));
//		ibShopImpl.addAndUpdateArticle(new Article("mac", "Apple", 1250, pc));
	}

	private static String formatArticle = "| %-4d | %-25s | %-25s| %-25s | %-8s   | %n";
	private static String lineArticle = "+------+---------------------------+--------------------------+---------------------------+------------+%n";
	private static String headerArticle = "| ID   | BRAND                     | DESCRIPTION              | CATEGORY                  | PRICE      |%n";

	private static String formatCategory = "| %-4d | %-25s | %n";
	private static String lineCategory = "+------+---------------------------+%n";
	private static String headerCategory = "| ID   | NAME                      |%n";
}
