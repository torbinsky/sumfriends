package controllers;
import java.util.ArrayList;

import play.libs.F.Tuple;


public class DataService {
	public static Tuple<ArrayList<Long>, ArrayList<Integer>> getTrend(){
		return DATA;
	}
	
	private static final Tuple<ArrayList<Long>, ArrayList<Integer>> DATA = new Tuple<>(new ArrayList<Long>(), new ArrayList<Integer>());
	static {
		DATA._1.add(1424912054156L - 86400000L*3);
		DATA._2.add(1340);
		
		DATA._1.add(1424912054156L - 86400000L*2);
		DATA._2.add(1370);
		
		DATA._1.add(1424912054156L - 86400000L);
		DATA._2.add(1300);
		
		DATA._1.add(1424912054156L);
		DATA._2.add(1350);
	}
}
