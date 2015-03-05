package sumfriends.models;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.db.ebean.Model;

@Embeddable
public class SummonerChallengeId extends Model {
	@Id
	@GeneratedValue
	public int id;
	public long challengeId;
	public long challengeSummonerId;
	
	public SummonerChallengeId() {
		super();
	}

	public SummonerChallengeId(long challengeId, long challengeSummonerId) {
		super();
		this.challengeId = challengeId;
		this.challengeSummonerId = challengeSummonerId;
	}

	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof SummonerChallengeId){
			SummonerChallengeId that = (SummonerChallengeId)arg0;
			return this.challengeId == that.challengeId && this.challengeSummonerId == that.challengeSummonerId;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 55;
	    hash = (int) (17 * hash + challengeId);
	    hash = (int) (17 * hash + challengeSummonerId);
	    return hash;
	}	
}
