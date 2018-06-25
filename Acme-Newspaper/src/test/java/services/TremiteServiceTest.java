package services;

import java.sql.Date;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Administrator;
import domain.Tremite;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TremiteServiceTest extends AbstractTest {

	// The SUT -------------------------------------

	@Autowired
	private TremiteService tremiteService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private NewspaperService newspaperService;
	
	@Test
	public void sampleDriver() {
		final Object testingData[][] = {
				
				{ "admin1", null, "positiveTest" }, //An admin creates a new tremite, saves it in draft mode and then edits it and saves it in final mode
	
				{ "admin1", IllegalArgumentException.class, "negativeTest" } //An admin creates a new tremite, saves it as final mode and associates a newspaper to it; then tries to edit the newspaper
	
				};

		for (int i = 0; i < testingData.length; i++)
			try {
				super.startTransaction();
				this.template((String) testingData[i][0],
						(Class<?>) testingData[i][1], (String) testingData[i][2]);
				} catch (final Throwable oops) {
					throw new RuntimeException(oops);
				} finally {
					super.rollbackTransaction();
				}
	}

	// Ancillary methods ------------------------------------------------------

	protected void template(final String username, final Class<?> expected, String operation) {
		Class<?> caught;
		Tremite tremite;

		caught = null;
		try {
			super.authenticate(username);
			
			if (operation.equals("positiveTest")) {
				Tremite saved;
				
				tremite = tremiteService.create();
				
				tremite.setTitle("Test");
				tremite.setDescription("Test");
				tremite.setGauge(1);
				tremite.setIsFinal(false);
				tremite.setAdministrator((Administrator) actorService.findByUserAccountUsername(username));
				tremite.setTicker(tremiteService.getTicker());
				
				saved = tremiteService.save(tremite);
				
				saved.setIsFinal(true);
				
				saved = tremiteService.save(saved);
			}else if(operation.equals("negativeTest")) {
				Tremite saved, nextSaved;
				
				tremite = tremiteService.create();
				
				tremite.setTitle("Test");
				tremite.setDescription("Test");
				tremite.setGauge(1);
				tremite.setIsFinal(false);
				tremite.setAdministrator((Administrator) actorService.findByUserAccountUsername(username));
				tremite.setTicker(tremiteService.getTicker());
				tremite.setNewspaper(newspaperService.findOne(super.getEntityId("newspaper1")));
				saved = tremiteService.save(tremite);
				
				saved.setIsFinal(true);
				
				saved = tremiteService.save(saved);
				
				nextSaved = tremiteService.findOneToEdit(saved.getId());
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
