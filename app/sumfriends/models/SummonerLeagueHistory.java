package sumfriends.models;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

import play.db.ebean.Model;
import play.libs.Json;

@Entity
public class SummonerLeagueHistory extends Model {
	@EmbeddedId
	public SummonerLeagueHistoryId id;
	public long summonerId;
	public int leaguePoints;
	public String tier;
	public String division;
	public int score;
	public Date createdAt;
	
	public SummonerLeagueHistory(String queue, long summonerId, int leaguePoints, String tier, String division, int score, int wins, int losses) {
		super();
		id = new SummonerLeagueHistoryId(queue, wins, losses);
		this.summonerId = summonerId;
		this.leaguePoints = leaguePoints;
		this.tier = tier;
		this.division = division;
		this.score = score;
	}

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

	@Override
	public String toString(){
		return Json.toJson(this).toString();
	}
}
