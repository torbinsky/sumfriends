package sumbet.controllers;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.F.Either;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import sumbet.Constants;
import sumbet.models.UserAccount;
import sumbet.services.AccountService;

public class AuthController extends Controller {
	static final Logger.ALogger logger = Logger.of(AuthController.class);

	private AccountService accountService;

	@Inject
	public AuthController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	public Result login() {
		return ok(sumbet.views.html.login.render(null));
	}

	public Promise<Result> loginSubmit() {
		final DynamicForm loginForm = Form.form().bindFromRequest();
		String email = loginForm.get("email");
		String password = loginForm.get("password");
		return accountService.authorizeAsAccount(email, password)
				.flatMap(ua -> {
					if(ua == null){
						return null;
					}
					return accountService.createAccountSession(ua.id);	
				})
				.map(token -> getResultForAuthorizedAccount(token));
	}
	
	private Result getResultForAuthorizedAccount(String token){
		if(token != null){
			createSession(token);
			return redirect(sumbet.controllers.routes.Application.index());
		}
		
		return unauthorized(sumbet.views.html.login.render("Invalid email/password"));
	}
	
	private void createSession(String token){
		session().put(Constants.SESSION_TOKEN_COOKIE, token);
	}

	public Result signup() {
		return ok(sumbet.views.html.signup.render(null));
	}

	public Promise<Result> signupSubmit() {
		final DynamicForm loginForm = Form.form().bindFromRequest();
		String email = loginForm.get("email");
		String summonerName = loginForm.get("summonerName");
		String pass1 = loginForm.get("password");
		String pass2 = loginForm.get("passwordConfirm");
		
		// VALIDATE
		Result validationError = validateSignup(email, summonerName, pass1, pass2);
		if(validationError != null){
			return Promise.pure(validationError);
		}
		
		// DO THE SIGNUP
		String passhash = BCrypt.hashpw(pass1, BCrypt.gensalt());
		return accountService.signupAccount(email, summonerName, passhash).map(result -> getResultForAccountSignup(result));
	}
	
	/**
	 * Returns NULL IFF valid params
	 */
	private @Nullable Result validateSignup(String email, String summonerName, String pass1, String pass2){
		if(email == null || email.trim().length() < 6){
			return badRequest(sumbet.views.html.signup.render("Invalid email address"));
		}
		
		if(summonerName == null || summonerName.trim().length() < 4){
			return badRequest(sumbet.views.html.signup.render("Invalid summoner name"));
		}
		
		if(pass1 == null || pass1.trim().length() < 6){
			return badRequest(sumbet.views.html.signup.render("Your password must be at least 6 characters"));
		}
		
		if(!pass1.equals(pass2)){
			return badRequest(sumbet.views.html.signup.render("Passwords do not match"));
		}
		
		// VALID
		return null;
	}
	
	private Result getResultForAccountSignup(Either<UserAccount, String> result){
		if(result.left.isDefined()){
			createSession(result.left.get().sessionToken);
			return redirect(sumbet.controllers.routes.Application.index());
		}else{
			return badRequest(sumbet.views.html.signup.render(result.right.get()));
		}
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

		// redirect to homepage
		return redirect(sumbet.controllers.routes.Application.index());
	}
}
