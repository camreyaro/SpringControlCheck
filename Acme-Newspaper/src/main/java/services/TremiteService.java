
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TremiteRepository;
import domain.Actor;
import domain.Administrator;
import domain.Tremite;

@Service
@Transactional
public class TremiteService {


	// Managed repository
	@Autowired
	private TremiteRepository	tremiteRepository;
	
	//Supporting services
	@Autowired
	private ActorService	actorService;
	
	//Validator
	@Autowired
	private Validator			validator;


	// Constructor
	public TremiteService() {
		super();
	}

	// Simple CRUD methods
	public Tremite create() {
		Tremite tremite;
		
		tremite = new Tremite();
		
		tremite.setIsFinal(false);
		
		return tremite;
	}

	public Collection<Tremite> findAll() {
		final Collection<Tremite> result = this.tremiteRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Tremite findOne(final int tremiteId) {
		final Tremite result = this.tremiteRepository.findOne(tremiteId);
		return result;
	}
	
	public Tremite findOneToEdit(final int tremiteId) {
		checkPrincipal(tremiteId);
		
		Tremite result;
		
		result = this.tremiteRepository.findOne(tremiteId);
		Assert.isTrue(result.getIsFinal() == false || result.getNewspaper() == null);
		
		return result;
	}

	public Tremite save(final Tremite tremite) {			
		Assert.notNull(tremite);

		final Tremite result;
		
		if(tremite.getNewspaper() != null && tremite.getIsFinal() && tremite.getMoment() == null){
			tremite.setMoment(new Date(System.currentTimeMillis()+1000));
		}
		
		result = this.tremiteRepository.save(tremite);
		
		return result;
	}

	public void delete(final Tremite tremite) {
		Assert.notNull(tremite);
		
		this.tremiteRepository.delete(tremite);
	}
	
	public void flush(){
		tremiteRepository.flush();
	}

	//Other business methods
	
	public void checkPrincipal(int tremiteId){
		Administrator admin;
		Tremite tremite;
		
		admin = (Administrator) this.actorService.findByPrincipal();
		tremite = this.findOne(tremiteId);
		
		Assert.isTrue(tremite.getAdministrator().equals(admin));
	}
	
	public Tremite reconstruct(final Tremite tremite, final BindingResult binding) {

		if (tremite.getId() == 0) {
			Actor actor;

			actor = this.actorService.findByPrincipal();
			tremite.setAdministrator((Administrator) actor);
			tremite.setTicker(this.getTicker());

		} else {
			final Tremite original = this.findOne(tremite.getId());

			tremite.setTicker(original.getTicker());
			tremite.setAdministrator(original.getAdministrator());
		}

		this.validator.validate(tremite, binding);

		return tremite;
	}
	
	public Collection<Tremite> tremitesByAdministratorId(Integer managerId){
		return this.tremiteRepository.tremitesByAdministratorId(managerId);
	}


	public String getTicker() {
		String res = "";
		final Calendar c = Calendar.getInstance();

		String day = Integer.toString(c.get(Calendar.DATE));
		if (day.length() == 1)
			day = "0" + day;
		String month = Integer.toString(c.get(Calendar.MONTH) + 1); // +1 ya que
																	// Enero es
																	// 0
		if (month.length() == 1)
			month = "0" + month;
		String year = Integer.toString(c.get(Calendar.YEAR));
		// Por el patron solo necesitamos las ultimas 2 cifras del año
		year = year.substring(2, 4);
		// Aqui sacamos las 2 letras mayusculas
		final Random r = new Random();
		final String alphabetUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String alphabetLower = alphabetUpper.toLowerCase();
		final String digits = "0123456789";
		
		final String alphanumerics = alphabetLower + alphabetUpper + digits + "_";
		final int index1 = r.nextInt(alphanumerics.length());
		final int index2 = r.nextInt(alphanumerics.length());
		final int digit = 10000 + r.nextInt(20000);

		res = year + ":" + alphanumerics.charAt(index1) + alphanumerics.charAt(index2) + ":" + month
				+ ":" + digit + ":" + day;

		// Comprobamos que el ticker sea único
		// Si existe algún ticker igual, volvemos a calcularlo

		for (final Tremite t : this.tremiteRepository.findAll())
			if (t.getTicker().equals(res))
				res = this.getTicker();
		return res;
	}
	
	public Collection<Tremite> tremitesByNewspaperId(Integer newspaperId){
		return this.tremiteRepository.tremitesByNewspaperId(newspaperId);
	}
	
	public Collection<Tremite> allTremitesByNewspaperId(Integer newspaperId){
		return this.tremiteRepository.allTremitesByNewspaperId(newspaperId);
	}
	
	//RATIO
	public Double getTremitesRatioPerNewspaper(){
		return this.tremiteRepository.getTremitesRatioPerNewspaper();
	}
	
	//MAX BY ADMIN
//	public Administrator getMaxTremitesAdmin(){
//		return this.tremiteRepository.getMaxTremitesAdmin();
//	}
	
}
