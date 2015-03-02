package sumbet.services;

import play.libs.F.Promise;

public interface AccountService {

	Promise<Void> clearSession(String sessionToken);

	boolean isValidSession(String token);

}
