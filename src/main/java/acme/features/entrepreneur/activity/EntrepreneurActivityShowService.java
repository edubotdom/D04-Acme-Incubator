
package acme.features.entrepreneur.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.roles.Entrepreneur;
import acme.entities.rounds.Round;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class EntrepreneurActivityShowService implements AbstractShowService<Entrepreneur, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EntrepreneurActivityRepository repository;


	@Override
	public boolean authorise(final Request<Activity> request) {
		assert request != null;

		boolean result;
		int activityId;
		Round round;
		Entrepreneur entrepreneur;
		Principal principal;

		activityId = request.getModel().getInteger("id");
		round = this.repository.findRoundByActivity(activityId);
		entrepreneur = round.getEntrepreneur();
		principal = request.getPrincipal();
		result = entrepreneur.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public void unbind(final Request<Activity> request, final Activity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		/*
		 * boolean iAmPrincipal;
		 * int activityId;
		 * Round round;
		 * Entrepreneur entrepreneur;
		 * Principal principal;
		 * 
		 * activityId = request.getModel().getInteger("id");
		 * round = this.repository.findRoundByActivity(activityId);
		 * entrepreneur = round.getEntrepreneur();
		 * principal = request.getPrincipal();
		 * iAmPrincipal = entrepreneur.getUserAccount().getId() == principal.getAccountId();
		 * model.setAttribute("iAmPrincipal", iAmPrincipal);
		 */
		request.unbind(entity, model, "title", "start", "end", "budget");
	}

	@Override
	public Activity findOne(final Request<Activity> request) {
		assert request != null;

		Activity result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}
}
