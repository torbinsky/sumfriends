package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.ebean.Model;

import com.robrua.orianna.type.core.common.Tier;
import com.robrua.orianna.type.core.league.League;
import com.robrua.orianna.type.core.league.LeagueEntry;
import com.robrua.orianna.type.core.match.Participant;
import com.robrua.orianna.type.core.matchhistory.MatchSummary;

@Entity
public class MonitoredSummonerStats extends Model {	
	public long matchId;
	public long monitoredSummonerId;
	public boolean win;
	public int leaguePoints;
	public String tier;
	public String division;
	public Date matchDate;
	
	public MonitoredSummonerStats(long matchId, long monitoredSummonerId,
			boolean win, int leaguePoints, String tier, String division,
			Date matchDate) {
		super();
		this.matchId = matchId;
		this.monitoredSummonerId = monitoredSummonerId;
		this.win = win;
		this.leaguePoints = leaguePoints;
		this.tier = tier;
		this.division = division;
		this.matchDate = matchDate;
	}

	public MonitoredSummonerStats(long summonerId, League league, LeagueEntry entry, MatchSummary summary) {
		this.monitoredSummonerId = summonerId;
		this.matchId = summary.getID();
		this.matchDate = summary.getCreation();
		tier = league.getTier().name();		
		division = entry.getDivision();
		
		leaguePoints = convertTierAndDivisionToLP(league.getTier(), division) + entry.getLeaguePoints();
		for(Participant participant : summary.getParticipants()){
			win = participant.getStats().getWinner();
			break;
		}
	}
	
	public int convertTierAndDivisionToLP(Tier tier, String division){
		int tierVal = getTierValue(tier);
		int nextTierVal = getTierValue(getNextTier(tier));
		int divVal = (nextTierVal - tierVal) / 5;
		switch(division){
		case "I":
			return tierVal + (divVal*4);
		case "II":
			return tierVal + (divVal*3);
		case "III":
			return tierVal + (divVal*2);
		case "IV":
			return tierVal + (divVal);
		case "V":
		default:
			return tierVal;
		}
	}

	protected int getTierValue(Tier tier) {
		int tierValue;
		switch(tier){
		case SILVER:
			tierValue = 1150;
			break;
		case GOLD:
			tierValue = 1500;
			break;
		case PLATINUM:
			tierValue = 1850;
			break;
		case DIAMOND:
			tierValue = 2200;
			break;
		case BRONZE:
		default:
			tierValue = 850;
		}
		
		return tierValue;
	}
	
	public Tier getNextTier(Tier tier){
		switch(tier){
		case SILVER:
			return Tier.GOLD;
		case GOLD:
			return Tier.PLATINUM;
		case PLATINUM:
			return Tier.DIAMOND;
		case DIAMOND:
			return Tier.MASTER;
		case BRONZE:
		default:
			return Tier.SILVER;
		}
	}
}
