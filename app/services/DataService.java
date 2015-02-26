package services;
import java.util.ArrayList;

import models.SummonerResult;
import play.libs.F.Tuple;

import com.avaje.ebean.Ebean;


public class DataService {
	public static final long JON_SUMMONER_ID = 31243475;
	public static Tuple<ArrayList<Long>, ArrayList<Integer>> get14DayTrend(long summonerId){
//		List<SummonerResult> results = Ebean.find(SummonerResult.class)
//		.where().eq("summonerId", summonerId)
//		.findList();
//		System.out.println(Json.toJson(results).toString());
		return DATA;
	}
	
	private static final Tuple<ArrayList<Long>, ArrayList<Integer>> DATA = new Tuple<>(new ArrayList<Long>(), new ArrayList<Integer>());
	static {
		Ebean.save(new SummonerResult(JON_SUMMONER_ID, 1340, true));
		Ebean.save(new SummonerResult(JON_SUMMONER_ID, 1325, false));
		Ebean.save(new SummonerResult(JON_SUMMONER_ID, 1350, true));
		DATA._1.add(1424912054156L - 86400000L*3);
		DATA._2.add(1340);
		
		DATA._1.add(1424912054156L - 86400000L*2);
		DATA._2.add(1390);
		
		DATA._1.add(1424912054156L - 86400000L);
		DATA._2.add(1300);
		
		DATA._1.add(1424912054156L);
		DATA._2.add(1350);
	}
}
