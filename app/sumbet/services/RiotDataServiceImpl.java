package sumbet.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;

import play.Logger;
import play.libs.Akka;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.libs.F.Tuple;
import sumbet.models.Match;
import sumbet.models.MatchParticipant;
import sumbet.models.Summoner;
import sumbet.models.SummonerLeagueHistory;
import sumbet.utils.OriannaDataTransformer;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.league.League;
import com.robrua.orianna.type.core.matchhistory.MatchSummary;

public class RiotDataServiceImpl implements RiotDataService {
	static final Logger.ALogger logger = Logger.of(RiotDataServiceImpl.class);
	
	@Inject
	public RiotDataServiceImpl(){
		
	}

	@Override
	public Promise<Summoner> getSummonerById(final long id) {
		logger.debug("Getting summoner " + id);
		return createPromise(() -> doGetSummoner(id, null));
	}
	
	@Override
	public Promise<Summoner> getSummonerByName(String name) {
		logger.debug("Getting summoner " + name);
		return createPromise(() -> doGetSummoner(null, name));
	}

	@Override
	public Promise<Map<Match, List<MatchParticipant>>> getRecentMatchesForSummoner(long summonerId) {
		logger.debug("Getting recent matches for summoner " + summonerId);
		return createPromise(() -> doGetRecentMatchesForSummoner(summonerId));
	}

	@Override
	public Promise<List<SummonerLeagueHistory>> getRecentSummonerLeagueHistory(long summonerId) {
		logger.debug("Getting league history for summoner " + summonerId);
		return createPromise(() -> doGetRecentSummonerLeagueHistory(summonerId));
	}
	
	private Map<Match, List<MatchParticipant>> doGetRecentMatchesForSummoner(long summonerId){
		List<MatchSummary> oHistory = RiotAPI.getMatchHistory(summonerId);
		Map<Match, List<MatchParticipant>> matches = new HashMap<>();
		for(MatchSummary oms : oHistory){
			Tuple<Match, List<MatchParticipant>> transformed = OriannaDataTransformer.transformSummary(oms);
			matches.put(transformed._1, transformed._2);
		}
		
		return matches;
	}
	
	private Summoner doGetSummoner(@Nullable Long id, @Nullable String name){
		com.robrua.orianna.type.core.summoner.Summoner oSummoner;
		if(id == null){
			oSummoner = RiotAPI.getSummonerByName(name);
		}else{
			oSummoner = RiotAPI.getSummonerByID(id);
		}
		
		logger.debug("Found summoner " + oSummoner);
		
		return OriannaDataTransformer.transformSummoner(oSummoner);		
	}
	
	private List<SummonerLeagueHistory> doGetRecentSummonerLeagueHistory(long summonerId){
		List<League> oLeagues = RiotAPI.getLeaguesBySummonerID(summonerId);		
		logger.debug("Found " + oLeagues.size() + " leagues for summoner " + summonerId);
		return OriannaDataTransformer.transformLeagues(summonerId, oLeagues);
	}
	
	<T> Promise<T> createPromise(Function0<T> f){
		// TODO: Use a different execution context. For now we just use the default one
		return Promise.promise(f, Akka.system().dispatcher());
	}
	
}
