
package acme.features.investor.accounting;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.accountings.Accounting;
import acme.entities.roles.Investor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class InvestorAccountingListService implements AbstractListService<Investor, Accounting> {

	// Internal state ---------------------------------------------------------

	@Autowired
	InvestorAccountingRepository repository;


	@Override
	public boolean authorise(final Request<Accounting> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Accounting> request, final Accounting entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "creation");
	}

	@Override
	public Collection<Accounting> findMany(final Request<Accounting> request) {
		assert request != null;

		Collection<Accounting> result;
		result = this.repository.findManyAccountsByRound(request.getModel().getInteger("id"));

		return result;
	}
}
