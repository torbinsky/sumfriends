package controllers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTimeConstants;

import models.MonitoredSummonerStats;
import play.mvc.Controller;
import play.mvc.Result;
import services.DataService;
import common.Constants;

public class Application extends Controller {	
	
    public static Result index() {
    	List<MonitoredSummonerStats> stats = DataService.getStats(Constants.JON_SUMMONER_ID);
    	List<Long> dates = new ArrayList<>();
    	List<Integer> leaguePoints = new ArrayList<>();
    	
    	for(MonitoredSummonerStats mss : stats){
    		dates.add(mss.matchDate.getTime());
    		leaguePoints.add(mss.leaguePoints);
    	}
    	return ok(views.html.index.render(dates, leaguePoints, 1850));
    }

}
