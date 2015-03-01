package sumbet.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class TrackedSummoner extends Model {
	@Id
	public long summonerId;
	public Date lastCheck;
	public TrackedSummoner(long summonerId, Date lastCheck) {
		super();
		this.summonerId = summonerId;
		this.lastCheck = lastCheck;
	}	
}
