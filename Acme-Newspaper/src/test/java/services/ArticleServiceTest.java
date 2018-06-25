
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Article;
import domain.Newspaper;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ArticleServiceTest extends AbstractTest {

	// --- The SUT --- 
	@Autowired
	private ArticleService		articleService;

	@Autowired
	private FollowUpService		followUpService;

	@Autowired
	private NewspaperService	newspaperService;


	@Test
	public void sampleDriver() {
		final Object testingData[][] = {

			// --- CREATE & SAVE
//			{
//				"user3", "title", "body", null, null, "summary", "false", null, "newspaper1", null, "createAndSave"// Crearlo normal y correcto.
//			// Art�culo que no es m�o.
//			}, {
//				"user3", null, "body", null, null, "summary", "false", null, "newspaper1", IllegalArgumentException.class, "createAndSave" // Sin t�tulo
//			}, {
//				"user3", "title", null, null, null, "summary", "false", null, "newspaper1", IllegalArgumentException.class, "createAndSave" //Sin body
//			}, {
//				null, "title", "body", null, null, "summary", "false", null, "newspaper1", IllegalArgumentException.class, "createAndSaveNonAuth" // Siendo an�nimo
//			}, {
//				"admin", "title", "body", null, null, "summary", "false", null, "newspaper1", ClassCastException.class, "createAndSave" //Siendo administrador
//			}, {
//				"customer1", "title", "body", null, null, "summary", "false", null, "newspaper1", ClassCastException.class, "createAndSave" //Siendo Customer
//			}, {
//				"user3", "title", "body", null, null, "summary", "false", null, null, AssertionError.class, "createAndSave" //Sin peri�dico.
//
//			// --- EDIT ---
//			}, {
//				"user2", "title", "body", null, null, "summary", "false", "article2", "newspaper1", null, "re-save" //Edit Correcto
//			}, {
//				"user3", "title", "body", null, null, "summary", "false", "article4", "newspaper2", IllegalArgumentException.class, "re-save"// Peri�dico Pasado
			 {
				"user3", "title", "body", null, null, "summary", "false", "article2", "newspaper1", IllegalArgumentException.class, "re-save" // Art�culo que no es m�o.
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7],
				(String) testingData[i][8], (Class<?>) testingData[i][9], (String) testingData[i][10]);

	}
	// M�todo base para realizar test

	protected void template(final String username, final String title, final String body, final String date, final String pictureURL, final String summary, final String saved, final String articleName, final String newspaperName, final Class<?> expected,
		final String operation) {
		Class<?> caught;
		Article article;

		caught = null;
		try {
			super.unauthenticate();
			if (operation.equals("re-save")) {
				super.authenticate(username);
				article = this.articleService.findOne(this.getEntityId(articleName));
				this.articleService.save(article);
			} else if (operation.equals("createAndSave")) {
				super.authenticate(username);
				final Newspaper n = this.newspaperService.findOne(this.getEntityId(newspaperName));

				article = this.articleService.create(n);
				article.setTitle(title);
				article.setBody(body);
				article.setSummary(summary);

				final Boolean savedd = saved == "true";
				article.setSaved(savedd);
				this.articleService.save(article);

			} else if (operation.equals("createAndSaveNonAuth")) {
				final Newspaper n = this.newspaperService.findOne(this.getEntityId(newspaperName));

				article = this.articleService.create(n);
				article.setTitle(title);
				article.setBody(body);
				article.setSummary(summary);

				final Boolean savedd = saved == "true";
				article.setSaved(savedd);
				this.articleService.save(article);
				System.out.println(article.getCreator());

			}

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
	
	
	// ADMINISTRATOR
	
	@Test
	public void adminDriver() {
		final Object testingData[][] = {

			{
				"admin", "article1", null, "delete", // Borrado correcto
				
				"user1", "article1", IllegalArgumentException.class, "delete", // Un user no puede borrar un art�culo
				
				"admin", "article1", null, "spamArticles", // Listar art�culos con spam
			}

		};

		for (int i = 0; i < testingData.length; i++)
			this.templateAdmin((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2], (String) testingData[i][3]);

	}
	// M�todo base para realizar test

	protected void templateAdmin(final String username, final String articleName, final Class<?> expected, String operation) {
		Class<?> caught;
		Article article;

		caught = null;
		try {
			super.authenticate(username);
			article = articleService.findOne(super.getEntityId(articleName));
			
			if(operation.equals("delete")){
				articleService.delete(article);
			}else if(operation.equals("spamArticles")){
				article.setBody("viagra");
				articleService.save(article);
				Assert.isTrue(articleService.getArticlesWithSpamWords().contains(article));
			}
			
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
