package sumbet.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import play.Logger;
import play.libs.Akka;
import play.libs.F.Function0;
import play.libs.F.Promise;
import sumbet.models.Bet;
import sumbet.models.BettingSummoner;
import sumbet.models.BettingSummonerId;
import sumbet.models.Match;
import sumbet.models.MatchParticipant;
import sumbet.models.Summoner;
import sumbet.models.SummonerLeagueHistory;
import sumbet.models.TrackedSummoner;

import com.avaje.ebean.Ebean;

public class EbeanDataServiceImpl implements DataService {
	static final Logger.ALogger logger = Logger.of(EbeanDataServiceImpl.class);
	
	@Inject
	public EbeanDataServiceImpl(){
		
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
	public Promise<Bet> createBet(Bet bet) {
		logger.debug("Creating bet " + bet);
		return createPromise(() -> doCreateBet(bet));
	}
	
	private Bet doCreateBet(Bet bet){
		Ebean.save(bet);
		return bet;
	}

	@Override
	public Promise<Void> joinBet(long betId, long joiningSummonerId) {
		logger.debug("Summoner " + joiningSummonerId + " is joining bet " + betId);
		BettingSummoner bs = new BettingSummoner(new BettingSummonerId(betId, joiningSummonerId));
		return createPromise(() -> doJoinBet(bs));
	}
	
	private Void doJoinBet(BettingSummoner bettingSummoner){
		Ebean.save(bettingSummoner);
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
