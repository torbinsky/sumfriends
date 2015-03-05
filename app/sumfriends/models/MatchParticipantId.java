package sumfriends.models;

import javax.persistence.Embeddable;

import play.db.ebean.Model;
import play.libs.Json;

@Embeddable
public class MatchParticipantId extends Model {
	public long summonerId;
	public long matchId;
	public MatchParticipantId(long summonerId, long matchId) {
		super();
		this.summonerId = summonerId;
		this.matchId = matchId;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof MatchParticipantId){
			MatchParticipantId that = (MatchParticipantId)arg0;
			return this.summonerId == that.summonerId && this.matchId == that.matchId;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 1441;
	    hash = (int) (217 * hash + summonerId);
	    hash = (int) (217 * hash + matchId);
	    return hash;
	}	
	
	@Override
	public String toString(){
		return Json.toJson(this).toString();
	}
}
