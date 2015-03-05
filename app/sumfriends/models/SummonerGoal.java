package sumfriends.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import play.db.ebean.Model;

@Entity
public class SummonerGoal extends Model {
	@EmbeddedId
	public SummonerGoalId id;

	
	public SummonerGoal() {
		super();
	}

	public SummonerGoal(SummonerGoalId id) {
		super();
		this.id = id;
	}
}
