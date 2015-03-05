package sumfriends.actions;

import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import play.Logger;
import play.libs.Json;
import play.libs.F.Promise;
import play.mvc.Action.Simple;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import sumfriends.Constants;
import sumfriends.SumFriendsGlobal;
import sumfriends.dto.api.ApiErrorDto;
import sumfriends.services.AccountService;

public class SessionRequiredAction extends Simple {	
	static final Logger.ALogger logger = Logger.of(SessionRequiredAction.class);
//	private static String SSL_HEADER = "X-Forwarded-Proto";

	@Override
	public Promise<Result> call(Context context) throws Throwable {
		String token = getCookieToken(context);
		if(token == null){
			// NO SESSION
			logger.debug("No session found");
			return redirectUnauthorized(context);
		}
		
		// VALIDATE SESSION
		AccountService accountService = SumFriendsGlobal.getInjector().getInstance(AccountService.class);
		if(!accountService.isValidSession(token).get(30, TimeUnit.SECONDS)){
			logger.debug("Session is NOT valid");
			return redirectUnauthorized(context);
		}
		
		// AUTHORIZED
		logger.debug("Session is valid");
		return delegate.call(context);
	}

	protected Promise<Result> redirectUnauthorized(Context context) {
		if(context.request().path().startsWith("/api")){
			return Promise.pure(unauthorized(Json.toJson(new ApiErrorDto("Unauthorized", Http.Status.UNAUTHORIZED))));
		}
		return Promise.pure(redirect(sumfriends.controllers.routes.AuthController.login()));
	}
	
	public static @Nullable String getCookieToken(Context context) {
        final String token = context.session().get(Constants.SESSION_TOKEN_COOKIE);
        return token;
    }

//	public static Boolean isHttpsRequest(Request request) {
//		if (request.getHeader(SSL_HEADER) != null && StringUtils.contains(request.getHeader(SSL_HEADER), "https")) {
//			return true;
//		}
//
//		return false;
//	}

}