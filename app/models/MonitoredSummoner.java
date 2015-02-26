package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class MonitoredSummoner extends Model {
	@Id
	public long summonerId;
	public Date lastCheck;
	public MonitoredSummoner(long summonerId, Date lastCheck) {
		this.summonerId = summonerId;
		this.lastCheck = lastCheck;
	}
}
