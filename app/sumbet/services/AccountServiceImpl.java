package sumbet.services;

import javax.inject.Inject;

import play.libs.F.Promise;

public class AccountServiceImpl implements AccountService {
	@Inject
	public AccountServiceImpl() {

	}

	@Override
	public Promise<Void> clearSession(String sessionToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValidSession(String token) {
		// TODO Auto-generated method stub
		return false;
	}

}
