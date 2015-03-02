package sumbet.actions;

import javax.annotation.Nullable;

import play.libs.F.Promise;
import play.mvc.Action.Simple;
import play.mvc.Http.Context;
import play.mvc.Result;
import sumbet.Constants;
import sumbet.SumBetGlobal;
import sumbet.services.AccountService;

public class AuthenticationRequiredAction extends Simple {	

//	private static String SSL_HEADER = "X-Forwarded-Proto";

	@Override
	public Promise<Result> call(Context context) throws Throwable {
		String token = getCookieToken(context);
		if(token == null){
			// NO SESSION
			return Promise.pure(redirect(sumbet.controllers.routes.AuthController.login()));
		}
		
		// VALIDATE SESSION
		AccountService accountService = SumBetGlobal.getInjector().getInstance(AccountService.class);
		if(!accountService.isValidSession(token)){
			return Promise.pure(redirect(sumbet.controllers.routes.AuthController.login()));
		}
		
		// AUTHORIZED
		return Promise.pure(redirect(sumbet.controllers.routes.Application.index()));
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
