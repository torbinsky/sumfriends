package sumbet.services;

import java.util.List;
import java.util.Map;

import play.libs.F.Promise;
import sumbet.models.Match;
import sumbet.models.MatchParticipant;
import sumbet.models.Summoner;
import sumbet.models.SummonerLeagueHistory;

/**
 * Service for getting our data types from Riot
 * 
 * @author torben
 *
 */
public interface RiotDataService {
	Promise<Summoner> getSummonerById(long id);
	Promise<Summoner> getSummonerByName(String name);
	Promise<Map<Match, List<MatchParticipant>>> getRecentMatchesForSummoner(long summonerId);
	Promise<List<SummonerLeagueHistory>> getRecentSummonerLeagueHistory();
}
