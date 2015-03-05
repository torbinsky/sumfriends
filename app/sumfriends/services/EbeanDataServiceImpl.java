package sumfriends.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.inject.Inject;

import play.Logger;
import play.libs.Akka;
import play.libs.F.Either;
import play.libs.F.Function0;
import play.libs.F.Promise;
import sumfriends.models.Challenge;
import sumfriends.models.Match;
import sumfriends.models.MatchParticipant;
import sumfriends.models.Summoner;
import sumfriends.models.SummonerChallenge;
import sumfriends.models.SummonerChallengeId;
import sumfriends.models.SummonerLeagueHistory;
import sumfriends.models.TrackedSummoner;
import sumfriends.models.UserAccount;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Update;

public class EbeanDataServiceImpl implements DataService {
	static final Logger.ALogger logger = Logger.of(EbeanDataServiceImpl.class);
	
	@Inject
	public EbeanDataServiceImpl(){
		
	}
	
	@Override
	public Promise<List<Summoner>> getSummoners(int limit) {
		return createPromise(() -> doGetSummoners(limit));
	}

	private List<Summoner> doGetSummoners(int limit) {
		return Ebean.find(Summoner.class).setMaxRows(limit).findList();
	}

	@Override
	public Promise<SummonerLeagueHistory> getLastSummonerLeagueHistory(long summonerId, String queueType) {		
		return createPromise(() -> doGetLastSummonerLeagueHistory(summonerId, queueType));
	}

	private SummonerLeagueHistory doGetLastSummonerLeagueHistory(long summonerId, String queueType) {
		return Ebean.find(SummonerLeagueHistory.class).orderBy().desc("createdAt").setMaxRows(1).findUnique();
	}

	@Override
	public Promise<String> updateUserAccountSession(long accountId, String token){
		return createPromise(() -> doUpdateUserAccountSession(accountId, token));
	}
	
	private @Nullable String doUpdateUserAccountSession(long accountId, String token){
		logger.debug("Updating session for account[" + accountId + "]...");
		String updStatement = "update user_account set session_token = :token where id = :accountId";
		Update<UserAccount> update = Ebean.createUpdate(UserAccount.class, updStatement);
		update.setParameter("token", token);
		update.setParameter("accountId", accountId);
		
		int accountUpdateCount = update.execute();
		logger.debug("Updated " + accountUpdateCount + " accounts");
		
		// Only return the token if the update was successful
		if(accountUpdateCount > 0){
			return token;
		}
		return null;
	}
	
	@Override
	public Promise<Either<UserAccount, String>> registerAccount(String email, long summonerId, String passhash) {
		return createPromise(() -> doRegisterAccount(email, summonerId, passhash));
	}

	private Either<UserAccount, String> doRegisterAccount(String email, long summonerId, String passhash){
		try{
			UserAccount userAccount = new UserAccount(summonerId, email, passhash);
			userAccount.sessionToken = UUID.randomUUID().toString();
			Ebean.save(userAccount);
			return Either.Left(userAccount);
		}catch(Exception e){
			if(Ebean.find(UserAccount.class).where().eq("email", email).findRowCount() == 1){
				return Either.Right("E-mail address already taken.");
			}
			if(Ebean.find(UserAccount.class).where().eq("summonerId", summonerId).findRowCount() == 1){
				return Either.Right("Summoner already taken.");
			}
			return Either.Right("Unable to create account. Please try again later.");
		}
	}

	@Override
	public Promise<UserAccount> getUserAccountByEmail(String email) {
		return createPromise(() -> doGetUserAccountByEmail(email));
	}

	public UserAccount doGetUserAccountByEmail(String email){
		return Ebean.find(UserAccount.class).where().eq("email", email).findUnique();
	}

	@Override
	public Promise<UserAccount> getUserAccountForSession(String token) {
		return createPromise(() -> doGetUserAccountForSession(token));
	}

	public UserAccount doGetUserAccountForSession(String token){
		return Ebean.find(UserAccount.class).where().eq("sessionToken", token).findUnique();
	}
	
	@Override
	public Promise<Void> clearUserAccountSession(String sessionToken) {
		return createPromise(() -> doClearUserAccountSession(sessionToken));
	}

	private Void doClearUserAccountSession(String sessionToken){
		logger.debug("Clearing session...");
		String updStatement = "update user_account set session_token = NULL where session_token = :token";
		Update<UserAccount> update = Ebean.createUpdate(UserAccount.class, updStatement);
		update.setParameter("token", sessionToken);
		logger.debug("Cleared " + update.execute() + " sessions");
		return null;
	}

	@Override
	public Promise<Void> updateTrackedSummoner(long summonerId, @Nullable Date lastUpdated) {
		// If null, set last updated to now
		Date lu = lastUpdated == null ? new Date() : lastUpdated;
		logger.debug("Updating tracked summoner to being last updated on " + lu);
		return createPromise(() -> doUpdateTrackedSummoner(summonerId, lu));
	}
	
	private Void doUpdateTrackedSummoner(long summonerId, Date lastUpdated){
		Ebean.update(new TrackedSummoner(summonerId, lastUpdated));
		return null;
	}

	@Override
	public Promise<List<TrackedSummoner>> getLeastRecentlyUpdatedTrackedSummoners(int limit) {
		logger.debug("Getting " + limit + " of the least recently tracked summoners...");
		return createPromise(() -> doGetLeastRecentlyUpdatedTrackedSummoners(limit));
	}
	
	private List<TrackedSummoner> doGetLeastRecentlyUpdatedTrackedSummoners(int limit) {
		List<TrackedSummoner> results = Ebean.find(TrackedSummoner.class).setMaxRows(limit).orderBy("lastCheck asc").findList();
		logger.debug("Found " + results.size() + " results.");
		return results;
	}

	@Override
	public Promise<Summoner> getSummonerByName(String name) {
		logger.debug("Getting summoner by name " + name);
		return createPromise(() -> doGetSummonerByName(name));
	}
	
	private Summoner doGetSummonerByName(String name){
		Summoner summoner = Ebean.find(Summoner.class).where().eq("name", name).findUnique();
		return summoner;
	}

	@Override
	public Promise<Summoner> getSummonerById(long id) {
		logger.debug("Getting summoner by ID " + id);
		return createPromise(() -> doGetSummonerById(id));
	}
	
	private Summoner doGetSummonerById(long id) {
		Summoner summoner = Ebean.find(Summoner.class, id);
		logger.debug("Found summoner " + summoner);
		return summoner;
	}

	@Override
	public Promise<Summoner> createOrUpdateSummoner(Summoner summoner) {
		logger.debug("Saving summoner " + summoner.id);
		return createPromise(() -> doCreateOrUpdateSummoner(summoner));
	}
	
	private Summoner doCreateOrUpdateSummoner(Summoner summoner){
		if(Ebean.find(Summoner.class).where().idEq(summoner.id).findRowCount() == 0){
			logger.debug("Saving new summoner " + summoner);
			Ebean.save(summoner);
		}else{
			logger.debug("Updating summoner " + summoner);
			Ebean.update(summoner);
		}
		return summoner;
	}

	@Override
	public Promise<SummonerLeagueHistory> getOrCreateSummonerLeagueHistory(SummonerLeagueHistory history) {
		logger.debug("Saving summoner league history " + history);
		return createPromise(() -> doGetOrCreateSummonerLeagueHistory(history));
	}
	
	private SummonerLeagueHistory doGetOrCreateSummonerLeagueHistory(SummonerLeagueHistory history){
		// GET
		SummonerLeagueHistory existing = Ebean.find(SummonerLeagueHistory.class, history.id);
		if(existing != null){
			logger.debug("LeagueHistory already exists for " + history.id);
			return existing;
		}
		
		// CREATE
		logger.debug("Saving new history " + history.id);
		Ebean.save(history);
		return history;
	}

	@Override
	public Promise<Challenge> createChallenge(Challenge challenge) {
		logger.debug("Creating challenge " + challenge);
		return createPromise(() -> doCreateChallenge(challenge));
	}
	
	private Challenge doCreateChallenge(Challenge challenge){
		Ebean.save(challenge);
		return challenge;
	}

	@Override
	public Promise<Void> watchChallenge(long challengeId, long watchingSummonerId) {
		logger.debug("Summoner " + watchingSummonerId + " is watching challenge " + challengeId);
		SummonerChallenge bs = new SummonerChallenge(new SummonerChallengeId(challengeId, watchingSummonerId));
		return createPromise(() -> doJoinChallenge(bs));
	}
	
	private Void doJoinChallenge(SummonerChallenge watchingSummoner){
		Ebean.save(watchingSummoner);
		return null;
	}

	@Override
	public Promise<Match> saveMatchIfNew(Match match) {
		logger.debug("Saving match " + match);
		return createPromise(() -> doSaveMatchIfNew(match));
	}
	
	private Match doSaveMatchIfNew(Match match){
		if(Ebean.find(Match.class).where().idEq(match.matchId).findRowCount() == 0){
			logger.info("Saving match " + match.matchId);
			Ebean.save(match);
		}else{
			logger.debug("Match " + match.matchId + " already exists. Not saving.");
		}
		return match;
	}

	@Override
	public Promise<MatchParticipant> saveMatchParticipantIfNew(MatchParticipant participant) {
		logger.trace("Saving match participant " + participant);
		return createPromise(() -> doSaveMatchParticipantIfNew(participant));
	}
	
	private MatchParticipant doSaveMatchParticipantIfNew(MatchParticipant participant){
		if(Ebean.find(MatchParticipant.class).where().idEq(participant.id).findRowCount() == 0){
			logger.info("Saving new match participant " + participant.id);
			Ebean.save(participant);
		}else{
			logger.debug("MatchParticipant " + participant.id + " already exists. Not saving.");
		}
		return participant;
	}

	@Override
	public Promise<TrackedSummoner> trackSummoner(long summonerId) {
		logger.debug("Tracking summoner " + summonerId);
		TrackedSummoner ts = new TrackedSummoner(summonerId);
		return createPromise(() -> doTrackSummoner(ts));
	}
	
	private TrackedSummoner doTrackSummoner(TrackedSummoner ts){
		TrackedSummoner existing = Ebean.find(TrackedSummoner.class, ts.summonerId);
		if(existing != null){
			return existing;
		}else{
			Ebean.save(ts);
			return ts;
		}
	}
	
	@Override
	public Promise<List<TrackedSummoner>> getTrackedSummoners() {
		logger.debug("Getting tracked summoners");
		return createPromise(() -> doGetTrackedSummoners());
	}
	
	private List<TrackedSummoner> doGetTrackedSummoners(){
		return Ebean.find(TrackedSummoner.class).findList();
	}

	<T> Promise<T> createPromise(Function0<T> f){
		// TODO: Use a different execution context. For now we just use the default one
		return Promise.promise(f, Akka.system().dispatcher());
	}

}
