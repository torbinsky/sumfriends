package sumfriends.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.libs.Json;

@Entity
public class TrackedSummoner extends Model {
	@Id
	public long summonerId;
	public Date lastCheck;

	public TrackedSummoner() {
		super();
	}

	public TrackedSummoner(long summonerId) {
		super();
		this.summonerId = summonerId;
	}

	public TrackedSummoner(long summonerId, Date lastCheck) {
		super();
		this.summonerId = summonerId;
		this.lastCheck = lastCheck;
	}
	
	@Override
	public String toString(){
		return Json.toJson(this).toString();
	}
}
