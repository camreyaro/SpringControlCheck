/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.NewspaperService;
import services.TremiteService;
import domain.Newspaper;
import domain.Tremite;

@Controller
@RequestMapping("/tremite")
public class TremiteController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TremiteService	tremiteService;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private NewspaperService		newspaperService;


	// Constructors -----------------------------------------------------------

	public TremiteController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Tremite tremite;
		final Collection<Newspaper> newspapers = this.newspaperService.findAllPublished();

		tremite = this.tremiteService.create();
		result = this.createEditModelAndView(tremite);
		result.addObject("newspapers", newspapers);

		return result;
	}
	
	// Display ---------------------------------------------------------------

	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int tremiteId) {
		ModelAndView result;
		Tremite tremite;
		
		tremite = this.tremiteService.findOne(tremiteId);
		Assert.isTrue((tremite.getMoment() == null || tremite.getMoment().before(new Date(System.currentTimeMillis()))) 
				|| tremite.getAdministrator().equals(actorService.findByPrincipal()));
		
		result = new ModelAndView("tremite/display");
		result.addObject("tremite", tremite);

		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "administrator/list", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Collection<Tremite> tremites;
		Integer administratorId;
		
		administratorId = this.actorService.findByPrincipal().getId();
		tremites = this.tremiteService.tremitesByAdministratorId(administratorId);

		result = new ModelAndView("tremite/list");
		result.addObject("tremites", tremites);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "administrator/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tremiteId) {
		ModelAndView result;
		Tremite tremite;
		final Collection<Newspaper> newspapers;
		
		newspapers = this.newspaperService.findAllPublished();
		tremite = this.tremiteService.findOneToEdit(tremiteId);

		result = this.createEditModelAndView(tremite);
		result.addObject("newspapers", newspapers);

		return result;
	}

	@RequestMapping(value = "administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Tremite tremite, final BindingResult binding) {
		ModelAndView result;
		final Collection<Newspaper> newspapers;
		
		this.tremiteService.reconstruct(tremite, binding);
		newspapers = this.newspaperService.findAllPublished();

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(tremite);
			result.addObject("newspapers", newspapers);
		} else
			try {
					this.tremiteService.save(tremite);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				String errorMessage = "error.commit";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(tremite, errorMessage);
			}

		return result;
	}
	@RequestMapping(value = "administrator/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Tremite tremite, final BindingResult binding) {
		ModelAndView result;
		final Collection<Newspaper> newspapers;
		
		newspapers = this.newspaperService.findAllPublished();

		try {
			this.tremiteService.delete(tremite);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			String errorMessage = "category.commit.error";

			if (oops.getMessage().contains("message.error"))
				errorMessage = oops.getMessage();
			result = this.createEditModelAndView(tremite, errorMessage);
			result.addObject("newspapers", newspapers);
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Tremite tremite) {
		ModelAndView result;

		result = this.createEditModelAndView(tremite, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Tremite tremite, final String message) {
		ModelAndView result;

		result = new ModelAndView("tremite/edit");
		result.addObject("tremite", tremite);
		result.addObject("message", message);

		return result;
	}
}
