package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import play.libs.F.Tuple;
import play.mvc.Controller;
import play.mvc.Result;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.QueueType;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.league.League;
import com.robrua.orianna.type.core.league.LeagueEntry;

public class Application extends Controller {
	public static final long JON_SUMMONER_ID = 31243475;
	
    public static Result index() {
    	RiotAPI.setMirror(Region.NA);
        RiotAPI.setRegion(Region.NA);
        RiotAPI.setAPIKey("851a8f32-a96f-4074-9751-bbcd853a0df6");
        List<League> leagues = RiotAPI.getLeaguesBySummonerID(JON_SUMMONER_ID);
        for(League league : leagues){
        	if(league.getQueueType() == QueueType.RANKED_SOLO_5x5){        		
        		List<LeagueEntry> entries = league.getEntries();
        		for(LeagueEntry le : entries){
        			if(le.getSummoner().getID() == JON_SUMMONER_ID){
        				//System.out.println(league.getTier() + " - " + le.getDivision() + " - " + le.getLeaguePoints());
        				break;
        			}
        		}
        		break;
        	}
        }
        Tuple<ArrayList<Long>, ArrayList<Integer>> data = DataService.getTrend();
        return ok(views.html.index.render(data._1, data._2));
    }

}
