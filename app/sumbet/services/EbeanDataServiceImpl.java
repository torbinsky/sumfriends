package sumbet.services;

import java.util.List;

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
		return summoner;
	}

	@Override
	public Promise<Summoner> createOrUpdateSummoner(Summoner summoner) {
		logger.debug("Saving summoner " + summoner.id);
		return createPromise(() -> doCreateOrUpdateSummoner(summoner));
	}
	
	private Summoner doCreateOrUpdateSummoner(Summoner summoner){
		Ebean.save(summoner);
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
			return existing;
		}
		
		// CREATE
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
	public Promise<Match> saveMatch(Match match) {
		logger.debug("Saving match " + match);
		return createPromise(() -> doSaveMatch(match));
	}
	
	private Match doSaveMatch(Match match){
		Ebean.save(match);
		return match;
	}

	@Override
	public Promise<MatchParticipant> saveMatchParticipant(MatchParticipant participant) {
		logger.debug("Saving match participant " + participant);
		return createPromise(() -> doSaveMatchParticipant(participant));
	}
	
	private MatchParticipant doSaveMatchParticipant(MatchParticipant participant){
		Ebean.save(participant);
		return participant;
	}

	@Override
	public Promise<TrackedSummoner> trackSummoner(long summonerId) {
		logger.debug("Tracking summoner " + summonerId);
		TrackedSummoner ts = new TrackedSummoner(summonerId);
		return createPromise(() -> doTrackSummoner(ts));
	}
	
	private TrackedSummoner doTrackSummoner(TrackedSummoner ts){
		Ebean.save(ts);
		return ts;
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
