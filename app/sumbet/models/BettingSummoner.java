package sumbet.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import play.db.ebean.Model;

@Entity
public class BettingSummoner extends Model {
	@EmbeddedId
	public BettingSummonerId id;

	
	public BettingSummoner() {
		super();
	}

	public BettingSummoner(BettingSummonerId id) {
		super();
		this.id = id;
	}
}
