package fr.fms;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
		int choice = 0;

		while (choice != 8) {
			try {
				displayMenu();
				switch (choice = input()) {
				case 1:
					displayArticles();
					break;
				case 2:
					System.out.println("Afficher tous les articles avec pagination");
					break;
				case 3:
					int articleChoice = 0;
					gestionArticle(articleChoice);
					break;
				case 4:
					displayCatogories();
					break;
				case 5:
					int categoryChoice = 0;
					gestionCategory(categoryChoice);
					break;
				case 6:
					System.out.println("Bye, à bientôt");
					break;
				default:
					System.out.println("Mauvaise saisie, recommencez !");
				}
			} catch (Exception e) {
				System.out.println();
				System.out.println("-------------------------------------------------");
				System.out.println(e.getMessage());
				System.out.println("-------------------------------------------------");
				System.out.println();
			}
		}
	}

	public static void displayMenu() {
		System.out.println("Menu principal");
		System.out.println("Que souhaitez-vous faire ? [Saisir le chiffre correspondant]");
		System.out.println("[1] - Afficher tous les articles");
		System.out.println("[2] - Afficher tous les articles avec pagination");
		System.out.println("[3] - Gérer un article");
		System.out.println("[4] - Afficher toutes les catégories");
		System.out.println("[5] - Gérer une catégorie");
		System.out.println("[6] - Quitter l'application");
	}

	public void displayArticles() {
		List<Article> articles = ibShopImpl.getAllArticles();
		System.out.format(lineArticle);
		System.out.format(headerArticle);
		System.out.format(lineArticle);

		for (Article article : articles) {
			System.out.format(formatArticle, article.getId(), article.getBrand(), article.getDescription(),
					article.getCategory().getName(), article.getPrice());
		}
		System.out.format(lineArticle);
		System.out.println();
	}

	public void displayCatogories() {
		List<Category> categories = ibShopImpl.getAllCategories();
		System.out.format(lineCategory);
		System.out.format(headerCategory);
		System.out.format(lineCategory);

		for (Category category : categories) {
			System.out.format(formatCategory, category.getId(), category.getName());
		}
		System.out.format(lineCategory);
		System.out.println();
	}

	public void displaySousMenu(String menuName) {
		System.out
				.println("Gestion des " + menuName + " : Que souhaitez-vous faire ? [Saisir le chiffre correspondant]");
		System.out.println("[1] - Ajouter");
		System.out.println("[2] - Modifier");
		System.out.println("[3] - Supprimer");
		System.out.println("[4] - Retour");
	}

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
				break;
			default:
				System.out.println("Mauvaise saisie, recommencez !");
			}
		}
	}

	public void addArticle() {
		System.out.println("Ajouter un article");
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
		displayArticles();
	}

	public void updateArticle() {
		System.out.println("Modifier un article - Quel article souhaitez-vous modifier ? [Saisir l'ID correspondant]");
		displayArticles();
		Article article = ibShopImpl.getArticle((long) input());
		System.out.println(article);
		System.out.println("Veillez saisir la marque :");
		String brand = inputStr();
		article.setBrand(brand);
		System.out.println("Veillez saisir la description :");
		String description = inputStr();
		article.setDescription(description);
		System.out.println("Veillez saisir le prix :");
		double price = inputDouble();
		article.setPrice(price);
		System.out.println("Veillez choisir la catégorie :");
		displayCatogories();
		Category category = ibShopImpl.getCategory((long) input());
		article.setCategory(category);
		ibShopImpl.addAndUpdateArticle(article);
		displayArticles();
	}

	public void deleteArticle() {
		System.out.println("Quel article souhaitez-vous supprimer ? [Saisir l'ID correspondant]");
		displayArticles();
		int id = input();
		ibShopImpl.deleteArticle((long) id);
		displayArticles();
	}

	public void gestionCategory(int choice) {
		while (choice != 4) {
			displaySousMenu("catégories");
			choice = input();
			switch (choice) {
			case 1:
				addCategory();
				break;
			case 2:
				updateCategory();
				break;
			case 3:
				deleteCategory();
				break;
			case 4:
				break;
			default:
				System.out.println("Mauvaise saisie, recommencez !");
			}
		}
	}

	public void deleteCategory() {
		System.out.println("Quelle catégorie souhaitez-vous supprimer ? [Saisir l'ID correspondant]");
		displayCatogories();
		int id = input();
		ibShopImpl.deleteCategory((long) id);
		displayCatogories();
	}

	public void addCategory() {
		System.out.println("Ajouter une catégorie");
		System.out.println("Veillez saisir le nom :");
		String name = inputStr();
		ibShopImpl.addAnsUpdateCategory(new Category(name));
		displayCatogories();
	}

	public void updateCategory() {
		System.out.println("Quelle catégorie souhaitez-vous modifier ? [Saisir l'ID correspondant]");
		displayCatogories();
		int id = input();
		Category category = ibShopImpl.getCategory((long) id);
		System.out.println("Veillez saisir le nom : ");
		String name = inputStr();
		category.setName(name);
		ibShopImpl.addAnsUpdateCategory(category);
		displayCatogories();
	}

	public static int input() {
		int choice;
		while (scan.hasNextInt() == false)
			scan.next();
		choice = scan.nextInt();
		return choice;
	}

	public static double inputDouble() {
		double price;
		while (scan.hasNextDouble() == false)
			scan.next();
		price = scan.nextDouble();
		return price;
	}

	public static String inputStr() {
		String str;
		while (scan.hasNextLine() == false)
			scan.next();
		str = scan.next();
		return str;
	}

	public static String formatArticle = "| %-4d | %-25s | %-25s| %-15s | %-8s   | %n";
	public static String lineArticle = "+------+---------------------------+--------------------------+-----------------+------------+%n";
	public static String headerArticle = "| ID   | BRAND                     | DESCRIPTION              | CATEGORY        | PRICE      |%n";

	public static String formatCategory = "| %-4d | %-17s | %n";
	public static String lineCategory = "+------+-------------------+%n";
	public static String headerCategory = "| ID   | NAME              |%n";

}
