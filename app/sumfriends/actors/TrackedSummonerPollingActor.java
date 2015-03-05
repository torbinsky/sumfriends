package sumfriends.actors;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Provider;

import org.joda.time.DateTime;

import play.Logger;
import play.libs.F.Promise;
import sumfriends.Constants.ConfigPropNameConstants;
import sumfriends.actors.SummonerSyncActor.SyncSummonerRequest;
import sumfriends.models.TrackedSummoner;
import sumfriends.services.DataService;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.typesafe.config.Config;

public class TrackedSummonerPollingActor extends UntypedActor {
	static final Logger.ALogger logger = Logger.of(TrackedSummonerPollingActor.class);
	
	public static final String TICK_MSG = "TICK";
	
	private DataService database;
	private ActorRef summonerSyncActor;
	private Provider<Config> configProvider;

	@Inject
	public TrackedSummonerPollingActor(DataService database, Provider<Config> configProvider){
		this.database = database;
		this.configProvider = configProvider;
		summonerSyncActor = GuiceUntypedActorFactory.createActorRef(SummonerSyncActor.class, "SumSyncer");
	}
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		if(arg0.equals(TICK_MSG)){
			logger.debug("Syncing tracked summoners...");
			int maxSummonersPerPoll = configProvider.get().getInt(ConfigPropNameConstants.MAX_SUMMONERS_PER_POLL);
			int maxUpdateMinutes = configProvider.get().getInt(ConfigPropNameConstants.SUMMONER_UPDATE_INTERVAL);
			Promise<List<TrackedSummoner>> trackedSummonersPromise = database.getLeastRecentlyUpdatedTrackedSummoners(maxSummonersPerPoll);
			trackedSummonersPromise.onRedeem(
					trackedSummoners -> trackedSummoners.stream()
					.filter(ts -> {
						// We only want the tracked summoners who haven't been updated recently
						return ts.lastCheck == null || new DateTime(ts.lastCheck).plusMinutes(maxUpdateMinutes).isBeforeNow();
					})
					.forEach(ts -> {
						summonerSyncActor.tell(new SyncSummonerRequest(ts.summonerId), self());
					})
			);
			int minutesToTimeout = configProvider.get().getInt(ConfigPropNameConstants.TRACKED_SUMMONERS_POLLING_INTERVAL);
			trackedSummonersPromise.get(minutesToTimeout, TimeUnit.MINUTES);
		}else{
			unhandled(arg0);
		}
	}

}
