package sumbet.services;

import javax.inject.Inject;

import play.libs.F.Promise;
import sumbet.models.Bet;
import sumbet.models.Match;
import sumbet.models.MatchParticipant;
import sumbet.models.Summoner;
import sumbet.models.SummonerLeagueHistory;
import sumbet.models.TrackedSummoner;

public class EbeanDataServiceImpl implements DataService {
	@Inject
	public EbeanDataServiceImpl(){
		
	}
	
	@Override
	public Promise<Summoner> getSummonerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promise<Summoner> getSummonerById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promise<Summoner> createOrUpdateSummoner(Summoner summoner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promise<Summoner> updateSummonerHistory(SummonerLeagueHistory history) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promise<Bet> createBet(Bet bet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promise<Void> joinBet(long betId, long targetSummonerId,
			long joiningSummonerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promise<Match> saveMatch(Match match) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promise<MatchParticipant> saveMatchParticipant(
			MatchParticipant participant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promise<TrackedSummoner> trackSummoner(long summonerId) {
		// TODO Auto-generated method stub
		return null;
	}

}
