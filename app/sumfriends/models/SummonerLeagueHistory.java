package sumfriends.models;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import play.db.ebean.Model;
import play.libs.Json;

import com.avaje.ebean.annotation.CreatedTimestamp;

@Entity
public class SummonerLeagueHistory extends Model {
	@EmbeddedId
	public SummonerLeagueHistoryId id;
	public int leaguePoints;
	public String tier;
	public String division;
	public int score;
	@CreatedTimestamp
	public Date createdAt;
	
	public SummonerLeagueHistory(String queue, long summonerId, int leaguePoints, String tier, String division, int score, int wins, int losses) {
		super();
		id = new SummonerLeagueHistoryId(summonerId, queue, wins, losses);
		this.leaguePoints = leaguePoints;
		this.tier = tier;
		this.division = division;
		this.score = score;
	}

	@Override
	public String toString(){
		return Json.toJson(this).toString();
	}
}
