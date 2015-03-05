package sumfriends.services;

import java.util.List;
import java.util.Map;

import play.libs.F.Promise;
import sumfriends.models.Match;
import sumfriends.models.MatchParticipant;
import sumfriends.models.Summoner;
import sumfriends.models.SummonerLeagueHistory;

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
	Promise<List<SummonerLeagueHistory>> getRecentSummonerLeagueHistory(long summonerId);
}
