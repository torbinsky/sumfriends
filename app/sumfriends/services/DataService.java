package sumfriends.services;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import play.libs.F.Either;
import play.libs.F.Promise;
import sumfriends.models.Goal;
import sumfriends.models.Match;
import sumfriends.models.MatchParticipant;
import sumfriends.models.Summoner;
import sumfriends.models.SummonerLeagueHistory;
import sumfriends.models.TrackedSummoner;
import sumfriends.models.UserAccount;

public interface DataService {
	Promise<Summoner> getSummonerByName(String name);
	Promise<Summoner> getSummonerById(long id);
	Promise<Summoner> createOrUpdateSummoner(Summoner summoner);
	Promise<SummonerLeagueHistory> getOrCreateSummonerLeagueHistory(SummonerLeagueHistory history);
	Promise<Goal> createGoal(Goal goal);
	Promise<Void> watchGoal(long goalId, long watchingSummonerId);
	Promise<Match> saveMatchIfNew(Match match);
	Promise<MatchParticipant> saveMatchParticipantIfNew(MatchParticipant participant);
	Promise<TrackedSummoner> trackSummoner(long summonerId);
	Promise<List<TrackedSummoner>> getTrackedSummoners();
	Promise<List<TrackedSummoner>> getLeastRecentlyUpdatedTrackedSummoners(int limit);
	/**
	 * Updates a {@link TrackedSummoner}'s last updated field
	 * 
	 * @parma summonerId the id of the tracked summoner
	 * @param lastUpdated optional override of last updated. If null, defaults to current date
	 * @return nothing
	 */
	Promise<Void> updateTrackedSummoner(long summonerId, @Nullable Date lastUpdated);
	Promise<Void> clearUserAccountSession(String sessionToken);
	Promise<UserAccount> getUserAccountForSession(String token);
	Promise<UserAccount> getUserAccountByEmail(String email);
	Promise<Either<UserAccount, String>> registerAccount(String email, long summonerId, String passhash);
	Promise<String> updateUserAccountSession(long accountId, String token);
	Promise<SummonerLeagueHistory> getLastSummonerLeagueHistory(long summonerId, String queueType);
}
