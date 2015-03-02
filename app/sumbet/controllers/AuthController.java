package sumbet.controllers;

import javax.inject.Inject;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import sumbet.Constants;
import sumbet.services.AccountService;

public class AuthController extends Controller {
	static final Logger.ALogger logger = Logger.of(AuthController.class);

	private AccountService accountService;

	@Inject
	public AuthController(AccountService accountService) {
		this.accountService = accountService;
	}

	public Result login() {
		return ok(sumbet.views.html.login.render());
	}

	public Result loginSubmit() {
		// TODO: IMPLEMENT THIS
		return redirect(sumbet.controllers.routes.Application.index());
	}

	public Result signup() {
		return ok(sumbet.views.html.signup.render());
	}

	public Result signupSubmit() {
		// TODO: IMPLEMENT THIS
		return redirect(sumbet.controllers.routes.Application.index());
	}

	public Result logout() {
		// Lookup the session token
		final String sessionToken = session().get(
				Constants.SESSION_TOKEN_COOKIE);

		// Clear out the session
		if (sessionToken != null) {
			logger.debug("Deleting session[" + sessionToken + "]");
			session().remove(Constants.SESSION_TOKEN_COOKIE);
			accountService.clearSession(sessionToken);
		}

		// redirect to login
		return redirect(sumbet.controllers.routes.AuthController.login());
	}
}
