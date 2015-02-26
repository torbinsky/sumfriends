package common;
import java.util.concurrent.TimeUnit;

import jobs.SummonerMonitor;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Props;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.LoadPolicy;
import com.robrua.orianna.type.api.RateLimit;
import com.robrua.orianna.type.core.common.Region;


public class Global extends GlobalSettings {
	final Logger.ALogger logger = Logger.of(this.getClass());
	
	@Override
	public void beforeStart(Application app) {
		logger.info("HELLO WORLD");
		super.beforeStart(app);
	}

	@Override
	public void onStart(Application app) {
		logger.info("Initializing Riot API...");
		RiotAPI.setMirror(Region.NA);
		RiotAPI.setRegion(Region.NA);
		RiotAPI.setLoadPolicy(LoadPolicy.LAZY);
		RiotAPI.setAPIKey(app.configuration().getString("riot.api.key"));
		
		logger.info("Starting summoner monitor...");
		ActorRef summonerActor = Akka.system().actorOf(Props.create(SummonerMonitor.class));
		Akka.system().scheduler().schedule(
				Duration.Zero(), 
				Duration.create(60, TimeUnit.SECONDS), 
				summonerActor,
				new SummonerMonitor.UpdateSummonerMsg(Constants.JON_SUMMONER_ID),
				Akka.system().dispatcher(),
				null
			);
		
		logger.info("Finished startup.");
		super.onStart(app);
	}	
}
