package sumbet.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import org.joda.time.DateTime;

import play.Logger;
import play.libs.F.Function;
import play.libs.F.Promise;
import sumbet.Constants;
import sumbet.models.Match;
import sumbet.models.MatchParticipant;
import sumbet.models.Summoner;
import sumbet.models.SummonerLeagueHistory;
import sumbet.services.DataService;
import sumbet.services.RiotDataService;
import akka.actor.UntypedActor;

import com.typesafe.config.Config;

public class SummonerSyncActor extends UntypedActor {
	static final Logger.ALogger logger = Logger.of(SummonerSyncActor.class);
	
	private DataService database;
	private RiotDataService riot;
	private Provider<Config> config;

	@Inject
	public SummonerSyncActor(DataService database, RiotDataService riot, Provider<Config> config){
		this.database = database;
		this.riot = riot;
		this.config = config;
	}
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		if(arg0 instanceof SyncSummonerRequest){
			SyncSummonerRequest msg = (SyncSummonerRequest)arg0;			
			doSyncSummoner(msg.summonerId);
		}else{
			unhandled(arg0);
		}
	}

	private void doSyncSummoner(long summonerId) {
		// TODO: Update DB copy of summoner periodically
		logger.debug("Syncing summoner[" + summonerId + "]...");
		// Ensure the summoner exists in our database
		Promise<Object> summonerPromise = database.getSummonerById(summonerId).flatMap(new Function<Summoner, Promise<Object>>(){
			@Override
			public Promise<Object> apply(Summoner arg0) throws Throwable {
				if(arg0 == null){
					return riot.getSummonerById(summonerId).map(_s -> (Object)database.createOrUpdateSummoner(_s));
				}else{
					return Promise.pure(arg0);
				}
			}
		});
		
		// Sync match history
		Promise<Object> matchSavePromises = riot.getRecentMatchesForSummoner(summonerId).map(matches -> doSaveRecentMatches(matches));
		
		// Sync league information
		Promise<Object> leagueHistorySavePromises = riot.getRecentSummonerLeagueHistory(summonerId).map(lh -> doSaveLeagueHistory(lh));
		Promise<List<Object>> combinedPromises = Promise.sequence(summonerPromise, matchSavePromises, leagueHistorySavePromises);
				
		// On failure, give the tracked summoner a slightly higher priority (about 1/2 the time depending on the settings)
		int summonerUpdateIntervalMinutes = config.get().getInt(Constants.ConfigPropNameConstants.SUMMONER_UPDATE_INTERVAL);
		int failureGraceMinutes = Math.max(summonerUpdateIntervalMinutes / 2, 1);
		combinedPromises.onFailure(callback -> {
			logger.debug("Summoner sync on [" + summonerId + "] was NOT successful", callback);
			database.updateTrackedSummoner(summonerId, new DateTime().minusMinutes(failureGraceMinutes).toDate());
		});
		
		// SUCCESS!
		combinedPromises.onRedeem(callback -> {
			logger.debug("Summoner sync on [" + summonerId + "] was successful");
			database.updateTrackedSummoner(summonerId, null).onFailure(callable -> {logger.error("",callable);});
		});
	}

	private Promise<List<SummonerLeagueHistory>> doSaveLeagueHistory(List<SummonerLeagueHistory> lh) {
		logger.debug("Saving summoner league history...");
		List<Promise<SummonerLeagueHistory>> promises = new ArrayList<>();
		for(SummonerLeagueHistory slh : lh){
			promises.add(database.getOrCreateSummonerLeagueHistory(slh));
		}
		return Promise.sequence(promises);
	}

	private Promise<List<Void>> doSaveRecentMatches(Map<Match, List<MatchParticipant>> matches) {
		logger.debug("Saving recent matches...");
		List<Promise<Void>> promises = new ArrayList<>();
		for(Match m : matches.keySet()){
			// Save match
			promises.add(database.saveMatchIfNew(m).map(_m -> (Void)null));
			
			// Save participants
			for(MatchParticipant mp : matches.get(m)){
				promises.add(database.saveMatchParticipantIfNew(mp).map(_m -> (Void)null));
			}
		}
		
		// Sequence all of the save promises
		Promise<List<Void>> savePromise = Promise.sequence(promises);
		return savePromise;
	}

	public static class SyncSummonerRequest {
		public final long summonerId;
		public SyncSummonerRequest(long summonerId) {
			this.summonerId = summonerId;
		}
	}
}
