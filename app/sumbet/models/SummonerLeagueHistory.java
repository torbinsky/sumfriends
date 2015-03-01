package sumbet.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.PrePersist;

import play.db.ebean.Model;

@Entity
public class SummonerLeagueHistory extends Model {
	public String queue;
	public long summonerId;
	public int leaguePoints;
	public String tier;
	public String division;
	public int score;
	public Date createdAt;
	public int wins;
	public int losses;
	
	public SummonerLeagueHistory(String queue, long summonerId, int leaguePoints, String tier, String division, int score, int wins, int losses) {
		super();
		this.queue = queue;
		this.summonerId = summonerId;
		this.leaguePoints = leaguePoints;
		this.tier = tier;
		this.division = division;
		this.score = score;
		this.wins = wins;
		this.losses = losses;
	}

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

}
