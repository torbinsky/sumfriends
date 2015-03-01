package sumbet.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import play.libs.F.Function;
import play.libs.F.Promise;
import sumbet.models.Match;
import sumbet.models.MatchParticipant;
import sumbet.models.Summoner;
import sumbet.models.SummonerLeagueHistory;
import sumbet.services.DataService;
import sumbet.services.RiotDataService;
import akka.actor.UntypedActor;

public class SummonerSyncActor extends UntypedActor {
	
	private DataService database;
	private RiotDataService riot;

	@Inject
	public SummonerSyncActor(DataService database, RiotDataService riot){
		this.database = database;
		this.riot = riot;
	}
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		if(arg0 instanceof SyncSummoner){
			SyncSummoner msg = (SyncSummoner)arg0;
			doSyncSummoner(msg.summonerId);
		}else{
			unhandled(arg0);
		}
	}

	private void doSyncSummoner(long summonerId) {
		// Ensure the summoner exists in our database
		Promise<Object> summonerPromise = database.getSummonerById(summonerId).flatMap(new Function<Summoner, Promise<Object>>(){
			@Override
			public Promise<Object> apply(Summoner arg0) throws Throwable {
				if(arg0 == null){
					return riot.getSummonerById(summonerId).map(_s -> (Object)_s);
				}else{
					return Promise.pure(arg0);
				}
			}
		});
		
		// Sync match history
		Promise<Object> matchSavePromises = riot.getRecentMatchesForSummoner(summonerId).map(matches -> doSaveRecentMatches(matches));
		
		// Sync league information
		Promise<Object> leagueHistorySavePromises = riot.getRecentSummonerLeagueHistory(summonerId).map(lh -> doSaveLeagueHistory(lh));
		
		Promise.sequence(summonerPromise, matchSavePromises, leagueHistorySavePromises).get(30, TimeUnit.SECONDS);
	}

	private Promise<List<SummonerLeagueHistory>> doSaveLeagueHistory(List<SummonerLeagueHistory> lh) {
		List<Promise<SummonerLeagueHistory>> promises = new ArrayList<>();
		for(SummonerLeagueHistory slh : lh){
			promises.add(database.getOrCreateSummonerLeagueHistory(slh));
		}
		return Promise.sequence(promises);
	}

	private Promise<List<Void>> doSaveRecentMatches(Map<Match, List<MatchParticipant>> matches) {
		List<Promise<Void>> promises = new ArrayList<>();
		for(Match m : matches.keySet()){
			// Save match
			promises.add(database.saveMatch(m).map(_m -> (Void)null));
			
			// Save participants
			for(MatchParticipant mp : matches.get(m)){
				promises.add(database.saveMatchParticipant(mp).map(_m -> (Void)null));
			}
		}
		
		// Sequence all of the save promises
		Promise<List<Void>> savePromise = Promise.sequence(promises);
		return savePromise;
	}

	public static class SyncSummoner {
		public final long summonerId;
		public SyncSummoner(long summonerId) {
			this.summonerId = summonerId;
		}
	}
}
