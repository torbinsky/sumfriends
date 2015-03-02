package sumbet.services;

import play.libs.F.Either;
import play.libs.F.Promise;
import sumbet.models.UserAccount;

public interface AccountService {

	Promise<Void> clearSession(String sessionToken);

	Promise<Boolean> isValidSession(String token);

	Promise<UserAccount> getAccountForSession(String token);

	Promise<UserAccount> authorizeAsAccount(String email, String password);
	
	Promise<String> createAccountSession(long accountId);

	Promise<Either<UserAccount, String>> signupAccount(String email, String summonerName, String passhash);
}
