package sumfriends.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import play.db.ebean.Model;

@Entity
public class SummonerChallenge extends Model {
	@EmbeddedId
	public SummonerChallengeId id;

	
	public SummonerChallenge() {
		super();
	}

	public SummonerChallenge(SummonerChallengeId id) {
		super();
		this.id = id;
	}
}
