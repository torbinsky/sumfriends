package sumfriends.models;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.db.ebean.Model;

@Embeddable
public class SummonerGoalId extends Model {
	@Id
	@GeneratedValue
	public int id;
	public long goalId;
	public long goalSummonerId;
	
	public SummonerGoalId() {
		super();
	}

	public SummonerGoalId(long goalId, long goalSummonerId) {
		super();
		this.goalId = goalId;
		this.goalSummonerId = goalSummonerId;
	}

	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof SummonerGoalId){
			SummonerGoalId that = (SummonerGoalId)arg0;
			return this.goalId == that.goalId && this.goalSummonerId == that.goalSummonerId;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 55;
	    hash = (int) (17 * hash + goalId);
	    hash = (int) (17 * hash + goalSummonerId);
	    return hash;
	}	
}
