package sumbet.models;

import javax.persistence.Embeddable;

import play.db.ebean.Model;

@Embeddable
public class BettingSummonerId extends Model {
	public long betId;
	public long bettingSummonerId;
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof BettingSummonerId){
			BettingSummonerId that = (BettingSummonerId)arg0;
			return this.betId == that.betId && this.bettingSummonerId == that.bettingSummonerId;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 55;
	    hash = (int) (17 * hash + betId);
	    hash = (int) (17 * hash + bettingSummonerId);
	    return hash;
	}	
}
