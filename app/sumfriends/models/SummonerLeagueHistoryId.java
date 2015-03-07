package sumfriends.models;

import javax.persistence.Embeddable;

import play.db.ebean.Model;
import play.libs.Json;

@Embeddable
public class SummonerLeagueHistoryId extends Model {
    public long summonerId;
	public String queue;
	public int wins;
	public int losses;
	
	public SummonerLeagueHistoryId() {
		super();
	}
	
	public SummonerLeagueHistoryId(long summonerId, String queue, int wins, int losses) {
		super();
        this.summonerId = summonerId;
		this.queue = queue;
		this.wins = wins;
		this.losses = losses;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof SummonerLeagueHistoryId){
			SummonerLeagueHistoryId that = (SummonerLeagueHistoryId)arg0;
			return 
					this.summonerId == that.summonerId &&
			        this.wins == that.wins && 
					this.losses == that.losses &&
					this.queue.equals(that.queue);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 15;
		hash = (int) (62 * hash + summonerId);
	    hash = (62 * hash + wins);
	    hash = (62 * hash + losses);
	    hash = (62 * hash + queue.hashCode());
	    
	    return hash;
	}
	
	public String toIdString(){
	    return summonerId + "_" + queue + "_" + wins + "_" + losses;
	}
	
	@Override
	public String toString(){
		return Json.toJson(this).toString();
	}
}
