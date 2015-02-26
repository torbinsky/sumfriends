package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.ebean.Model;

@Entity
public class SummonerResult extends Model {
	public long summonerId;
	public int leaguePoints;
	public long matchId;
	public boolean win;
	public Date date;
	public SummonerResult(long summonerId, int leaguePoints, boolean win) {
		super();
		this.summonerId = summonerId;
		this.leaguePoints = leaguePoints;
		this.win = win;
	}
}
