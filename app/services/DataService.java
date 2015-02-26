package services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.MonitoredSummoner;
import models.MonitoredSummonerStats;
import play.Logger;
import play.libs.Json;

import com.avaje.ebean.Ebean;


public class DataService {
	static final Logger.ALogger logger = Logger.of(DataService.class);
	
	public static Date getLastSummonerCheck(long summonerId){
		MonitoredSummoner result = Ebean.find(MonitoredSummoner.class, summonerId);
		if(result != null){
			return result.lastCheck;
		}
		
		return null;
	}
	
	public static void updateSummonerCheck(long summonerId){		
		MonitoredSummoner summoner = Ebean.find(MonitoredSummoner.class, summonerId);
		if(summoner == null){
			summoner = new MonitoredSummoner(summonerId, new Date());
		}
		Ebean.save(summoner);
	}

	public static void saveMatchStats(MonitoredSummonerStats mss) {
		Map<String, Object> statFind = new HashMap<>();
		statFind.put("matchId", mss.matchId);
		statFind.put("monitoredSummonerId", mss.monitoredSummonerId);
		
		if(Ebean.find(MonitoredSummonerStats.class).where().allEq(statFind).findUnique() == null){
			logger.info("Saving stats: " + Json.toJson(mss).toString());
			Ebean.save(mss);
		}else{
			logger.info("Match stats already exists for " + mss.matchId + "," + mss.monitoredSummonerId);
		}
	}
	
	public static List<MonitoredSummonerStats> getStats(long summonerId){
		return Ebean
				.find(MonitoredSummonerStats.class)
				.where().eq("monitoredSummonerId", summonerId)
				.order().asc("matchDate")
				.findList();
	}
	
//	public static void updateSummonerStats(long summonerId, )
}
