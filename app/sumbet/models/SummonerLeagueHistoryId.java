package sumbet.models;

import javax.persistence.Embeddable;

import play.db.ebean.Model;
import play.libs.Json;

@Embeddable
public class SummonerLeagueHistoryId extends Model {
	public String queue;
	public int wins;
	public int losses;
	
	public SummonerLeagueHistoryId() {
		super();
	}
	
	public SummonerLeagueHistoryId(String queue, int wins, int losses) {
		super();
		this.queue = queue;
		this.wins = wins;
		this.losses = losses;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof SummonerLeagueHistoryId){
			SummonerLeagueHistoryId that = (SummonerLeagueHistoryId)arg0;
			return 
					this.wins == that.wins && 
					this.losses == that.losses &&
					this.queue.equals(that.queue);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 15;
	    hash = (62 * hash + wins);
	    hash = (62 * hash + losses);
	    hash = (62 * hash + queue.hashCode());
	    
	    return hash;
	}
	
	@Override
	public String toString(){
		return Json.toJson(this).toString();
	}
}
