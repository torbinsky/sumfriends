package sumfriends;

import java.util.concurrent.TimeUnit;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import sumfriends.Constants.ConfigPropNameConstants;
import sumfriends.actors.GuiceUntypedActorFactory;
import sumfriends.actors.TrackedSummonerPollingActor;
import akka.actor.ActorRef;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.LoadPolicy;
import com.robrua.orianna.type.core.common.Region;

public class SumFriendsGlobal extends GlobalSettings {
	static final Logger.ALogger logger = Logger.of(GlobalSettings.class);
	
	private static Injector INJECTOR = null;
	
	public static Injector getInjector() {
		if (INJECTOR != null) {
			logger.trace("Injector id[" + INJECTOR.hashCode() + "]");
		}else{
			INJECTOR = Guice.createInjector(new _SumFriendsGlobalModule());
		}
		
		return INJECTOR;
	}
	
	@Override
	public <A> A getControllerInstance(Class<A> controllerClass)
			throws Exception {
		logger.trace("Getting controller[" + controllerClass + "]");
		return getInjector().getInstance(controllerClass);
	}

	@Override
	public void onStart(Application app) {
		// TODO: Find a (thread-safe) way of allowing different regions at the same time
		// Hard code to NA for now
		RiotAPI.setMirror(Region.NA);
		RiotAPI.setRegion(Region.NA);
		RiotAPI.setLoadPolicy(LoadPolicy.LAZY);
		RiotAPI.setAPIKey(app.configuration().getString(ConfigPropNameConstants.RIOT_API_KEY));
		
		// TODO: REMOVE THIS
//		getInjector().getInstance(DataService.class).trackSummoner(40856292L);
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		ActorRef trackedSummonerPoller = GuiceUntypedActorFactory.createActorRef(TrackedSummonerPollingActor.class, "TrackedSumPoller");
		Integer minuteBetweenTrackedSummonerPolls = app.configuration().getInt(ConfigPropNameConstants.TRACKED_SUMMONERS_POLLING_INTERVAL);
		logger.info("Polling tracked summoners at an interval of " + minuteBetweenTrackedSummonerPolls + " minutes");
		Akka.system().scheduler().schedule(
				Duration.Zero(), 
				Duration.create(minuteBetweenTrackedSummonerPolls, TimeUnit.MINUTES), 
				trackedSummonerPoller, 
				TrackedSummonerPollingActor.TICK_MSG,
				Akka.system().dispatcher(),
				null
			);
		
		
		super.onStart(app);
	}
	
	
}
