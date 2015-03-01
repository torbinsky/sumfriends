package sumbet.services;

import java.util.List;

import play.libs.F.Promise;
import sumbet.models.Bet;
import sumbet.models.Match;
import sumbet.models.MatchParticipant;
import sumbet.models.Summoner;
import sumbet.models.SummonerLeagueHistory;
import sumbet.models.TrackedSummoner;

public interface DataService {
	Promise<Summoner> getSummonerByName(String name);
	Promise<Summoner> getSummonerById(long id);
	Promise<Summoner> createOrUpdateSummoner(Summoner summoner);
	Promise<SummonerLeagueHistory> getOrCreateSummonerLeagueHistory(SummonerLeagueHistory history);
	Promise<Bet> createBet(Bet bet);
	Promise<Void> joinBet(long betId, long joiningSummonerId);
	Promise<Match> saveMatch(Match match);
	Promise<MatchParticipant> saveMatchParticipant(MatchParticipant participant);
	Promise<TrackedSummoner> trackSummoner(long summonerId);
	Promise<List<TrackedSummoner>> getTrackedSummoners();
}
