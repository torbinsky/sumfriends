package sumbet.services;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import play.Logger;
import play.libs.Akka;
import play.libs.F.Either;
import play.libs.F.Function0;
import play.libs.F.Promise;
import sumbet.models.Summoner;
import sumbet.models.UserAccount;

public class AccountServiceImpl implements AccountService {
	static final Logger.ALogger logger = Logger.of(AccountServiceImpl.class);
	
	private DataService database;
	private RiotDataService riotService;

	@Inject
	public AccountServiceImpl(DataService database, RiotDataService riotService) {
		this.database = database;
		this.riotService = riotService;
	}
	
	@Override
	public Promise<String> createAccountSession(long accountId){
		String token = UUID.randomUUID().toString();
		return database.updateUserAccountSession(accountId, token);
	}
	
	@Override
	public Promise<Either<UserAccount, String>> signupAccount(String email, String summonerName, String passhash) {
		Promise<Summoner> summonerPromise = lookupSummoner(summonerName);
		return summonerPromise.map(sum -> {
			if(sum == null){
				return Either.Right("Summoner not found");
			}else{
				Promise<Either<UserAccount, String>> registerPromise = database.registerAccount(email, sum.id, passhash);
				registerPromise.onRedeem(callback -> {
					// On successful register, track the summoner
					if(callback.left.isDefined()){
						database.trackSummoner(callback.left.get().summonerId);
					}
				});
				return registerPromise.get(10, TimeUnit.SECONDS);
			}
		});
	}

	protected Promise<Summoner> lookupSummoner(String summonerName) {
		return database.getSummonerByName(summonerName).map(sum -> {
			if(sum == null){
				logger.debug("Summoner not found in database, checking Riot...");
				Summoner summoner = null;
				try{
					summoner = riotService.getSummonerByName(summonerName).get(30, TimeUnit.SECONDS);
					if(summoner != null){
						logger.debug("Summoner found at Riot, updating database...");
						database.createOrUpdateSummoner(summoner);
					}
				}catch(Exception e){
					logger.warn("Encountered exception while fetching summoner from Riot for account signup.", e);
				}
				return summoner;
			}else{
				return sum;
			}
		});
	}

	@Override
	public Promise<UserAccount> authorizeAsAccount(String email, String password) {		
		return database.getUserAccountByEmail(email).map(ua -> {
			if(ua != null){
				if(BCrypt.checkpw(password, ua.passhash)){
					return ua;
				}
			}
			
			return null;
		});
	}

	@Override
	public Promise<Void> clearSession(String sessionToken) {		
		return database.clearUserAccountSession(sessionToken);
	}

	@Override
	public Promise<Boolean> isValidSession(String token) {
		return database.getUserAccountForSession(token).map(ua -> ua != null);
	}

	@Override
	public Promise<UserAccount> getAccountForSession(String token) {
		return database.getUserAccountForSession(token);
	}
	
	<T> Promise<T> createPromise(Function0<T> f){
		// TODO: Use a different execution context. For now we just use the default one
		return Promise.promise(f, Akka.system().dispatcher());
	}
}
