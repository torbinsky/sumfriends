package common;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import jobs.SummonerMonitor;
import models.MonitoredSummonerStats;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.libs.Akka;
import scala.concurrent.duration.Duration;
import services.DataService;
import akka.actor.ActorRef;
import akka.actor.Props;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.LoadPolicy;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.common.Tier;


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
		try{
			DataService.saveMatchStats(new MonitoredSummonerStats(1742869232, 31243475, true, 1690, Tier.GOLD.name(), "III", new Date(1424915185902L)));
			DataService.saveMatchStats(new MonitoredSummonerStats(1742936748, 31243475, false, 1690, Tier.GOLD.name(), "III", new Date(1424920011899L)));
			DataService.saveMatchStats(new MonitoredSummonerStats(1743001170, 31243475, true, 1690, Tier.GOLD.name(), "III", new Date(1424923200748L)));
			DataService.saveMatchStats(new MonitoredSummonerStats(1743039968, 31243475, true, 1710, Tier.GOLD.name(), "II", new Date(1424954223)));
			DataService.saveMatchStats(new MonitoredSummonerStats(1743889555, 31243475, true, 1735, Tier.GOLD.name(), "II", new Date(1425030292)));
			DataService.saveMatchStats(new MonitoredSummonerStats(1743971569, 31243475, true, 1761, Tier.GOLD.name(), "II", new Date(1425034535)));
			DataService.saveMatchStats(new MonitoredSummonerStats(1744035371, 31243475, true, 1787, Tier.GOLD.name(), "II", new Date(1425038738)));
			DataService.saveMatchStats(new MonitoredSummonerStats(1744116636, 31243475, false, 1773, Tier.GOLD.name(), "II", new Date(1425044604)));
			DataService.saveMatchStats(new MonitoredSummonerStats(1744150643, 31243475, false, 1765, Tier.GOLD.name(), "II", new Date(1425046957)));
		}catch(Exception e){
			logger.warn("Legacy data error.", e);
		}
		logger.info("Finished startup.");
		super.onStart(app);
	}	
}
