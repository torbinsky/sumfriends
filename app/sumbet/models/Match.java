package sumbet.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import play.db.ebean.Model;
import play.libs.Json;

@Entity
public class Match extends Model {
	@Id
	@JsonProperty(value="id")
	public long matchId;
	public long mapId;
	public Date matchCreation;
	public long matchDuration;
	public String matchMode;
	public String matchType;
	public String matchVersion;
	public String queueType;
	public String region;
	public String season;
	public Match() {
		super();
	}
	public Match(long matchId, long mapId, Date matchCreation,
			long matchDuration, String matchMode, String matchType,
			String matchVersion, String queueType, String region, String season) {
		super();
		this.matchId = matchId;
		this.mapId = mapId;
		this.matchCreation = matchCreation;
		this.matchDuration = matchDuration;
		this.matchMode = matchMode;
		this.matchType = matchType;
		this.matchVersion = matchVersion;
		this.queueType = queueType;
		this.region = region;
		this.season = season;
	}	
	
	@Override
	public String toString(){
		return Json.toJson(this).toString();
	}
}
