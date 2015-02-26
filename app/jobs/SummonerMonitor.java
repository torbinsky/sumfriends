package jobs;

import java.util.Date;
import java.util.List;

import models.MonitoredSummonerStats;
import play.Logger;
import services.DataService;
import akka.actor.UntypedActor;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.QueueType;
import com.robrua.orianna.type.core.league.League;
import com.robrua.orianna.type.core.league.LeagueEntry;
import com.robrua.orianna.type.core.matchhistory.MatchSummary;

public class SummonerMonitor extends UntypedActor {
	final Logger.ALogger logger = Logger.of(this.getClass());
	
	public static final long MAX_CHECK_FREQUENCY = 1000 * 60;
	
	public SummonerMonitor(){
		
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof UpdateSummonerMsg){
			UpdateSummonerMsg updateReq = (UpdateSummonerMsg)msg;
			logger.info("Monitoring summoner " + updateReq.summonerId);
			Date lastCheck = DataService.getLastSummonerCheck(updateReq.summonerId);
			if(lastCheck == null || System.currentTimeMillis() - lastCheck.getTime() >= MAX_CHECK_FREQUENCY){
				logger.info("Summoner has not been updated recently.");
				try{
					DataService.updateSummonerCheck(updateReq.summonerId);
					doSummonerUpdate(updateReq.summonerId);
				}catch(Exception e){
					logger.error("Couldn't do update.", e);
				}
			}else{
				logger.info("Summoner has been updated too recently. Skipping this check.");
			}
		}else{
			unhandled(msg);
		}
	}	
	
	private void doSummonerUpdate(long summonerId) {
		logger.debug("Getting leagues...");
		
		// Fetch league information
		List<League> leagues = RiotAPI.getLeaguesBySummonerID(summonerId);
		LeagueEntry leagueEntry = null;
		League league = null;
        for(League l : leagues){
        	// We want the ranked solo league
        	if(l.getQueueType() == QueueType.RANKED_SOLO_5x5){
        		league = l;
        		List<LeagueEntry> entries = league.getEntries();
        		for(LeagueEntry le : entries){
        			// We want the entry for the monitored summoner
        			if(le.getSummoner().getID() == summonerId){
        				leagueEntry = le;
        				break;
        			}
        		}
        		break;
        	}
        }
        // Fetch recent match summary
        List<MatchSummary> history = RiotAPI.getMatchHistory(summonerId, QueueType.RANKED_SOLO_5x5);
//        for(MatchSummary ms : history){
        MatchSummary ms = history.get(history.size() - 1); // Grab the most recent match        
        MonitoredSummonerStats mss = new MonitoredSummonerStats(summonerId, league, leagueEntry, ms);
        DataService.saveMatchStats(mss);
//        }
	}

	public static class UpdateSummonerMsg {
		public final long summonerId;

		public UpdateSummonerMsg(long summonerId) {
			this.summonerId = summonerId;
		}
	}
}
