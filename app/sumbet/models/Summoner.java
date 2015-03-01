package sumbet.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
import play.libs.Json;

@Entity
public class Summoner extends Model {
	@Id
	public long id;
	public String name;
	public int profileIconId;
	public Date revisionDate;
	public long summonerLevel;
	public Summoner(long id, String name, int profileIconId, Date revisionDate, long summonerLevel) {
		super();
		this.id = id;
		this.name = name;
		this.profileIconId = profileIconId;
		this.revisionDate = revisionDate;
		this.summonerLevel = summonerLevel;
	}
	
	@Override
	public String toString(){
		return Json.toJson(this).toString();
	}
}
