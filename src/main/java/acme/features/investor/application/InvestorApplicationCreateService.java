
package acme.features.investor.application;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.applications.Application;
import acme.entities.roles.Investor;
import acme.entities.rounds.Round;
import acme.features.investor.round.InvestorRoundRepository;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class InvestorApplicationCreateService implements AbstractCreateService<Investor, Application> {

	@Autowired
	InvestorApplicationRepository	repository;

	@Autowired
	InvestorRoundRepository			roundRepository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		boolean result;
		int roundId;
		Collection<Activity> activities;
		Date date = new Date();
		roundId = request.getModel().getInteger("id");
		activities = this.repository.findManyActivitiesByRound(roundId);
		result = activities.stream().map(m -> m.getEnd()).anyMatch(f -> f.compareTo(date) > 0);

		return result;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		String direccionApplication = "../application/create?id=" + request.getModel().getInteger("id");
		model.setAttribute("direccionApplication", direccionApplication);

		request.unbind(entity, model, "ticker", "creation", "statement", "offer"/* , "round", "investor" */);
	}

	@Override
	public Application instantiate(final Request<Application> request) {
		assert request != null;

		Application result;

		int investorId = request.getPrincipal().getAccountId();
		Investor investor = this.repository.findInvestorById(investorId);

		Date date = new Date(System.currentTimeMillis() - 1);

		result = new Application();
		result.setInvestor(investor);
		result.setCreation(date);

		Integer roundId = request.getModel().getInteger("id");
		Round round = this.roundRepository.findOneRoundById(roundId);
		result.setRound(round);

		result.setStatus("pending");

		return result;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Integer year = Calendar.getInstance().get(Calendar.YEAR);

		if (!entity.getTicker().isEmpty() && entity.getTicker().trim().split("-").length > 1) {
			String shortYear = year.toString().substring(2);
			String shortTickerYear = entity.getTicker().trim().split("-")[1];
			errors.state(request, shortTickerYear.equals(shortYear), "ticker", "investor.application.incorrectYearOfTicker");
		}
		if (entity.getOffer() != null) {
			boolean isCurrencyCorrect = entity.getOffer().getCurrency().equals("EUR") || entity.getOffer().getCurrency().equals("€");
			errors.state(request, isCurrencyCorrect, "offer", "investor.application.incorrect-currency");
		}
	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
