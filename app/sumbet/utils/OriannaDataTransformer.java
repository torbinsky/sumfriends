package sumbet.utils;

import java.util.ArrayList;
import java.util.List;

import play.libs.F.Tuple;
import sumbet.models.Match;
import sumbet.models.MatchParticipant;
import sumbet.models.Summoner;
import sumbet.models.SummonerLeagueHistory;

import com.robrua.orianna.type.core.common.Tier;
import com.robrua.orianna.type.core.league.League;
import com.robrua.orianna.type.core.league.LeagueEntry;
import com.robrua.orianna.type.core.match.Participant;
import com.robrua.orianna.type.core.match.ParticipantStats;
import com.robrua.orianna.type.core.matchhistory.MatchSummary;

public class OriannaDataTransformer {
	public static Tuple<Match, List<MatchParticipant>> transformSummary(MatchSummary oms) {
		// Transform Match
		Match match = transformMatch(oms);
		
		// Transform participants
		List<MatchParticipant> participants = transformParticipants(match.matchId, oms.getParticipants());

		return new Tuple<>(match, participants);
	}

	public static Match transformMatch(MatchSummary oms) {
		com.robrua.orianna.type.core.match.Match oMatch = oms.getMatch();
		Match transformed = new Match(
				oMatch.getID(), 
				oms.getMap().getID(), 
				oMatch.getCreation(), 
				oMatch.getDuration(), 
				oMatch.getMode().toString(), 
				oMatch.getType().toString(), 
				oMatch.getVersion(), 
				oMatch.getQueueType().toString(), 
				oMatch.getRegion().toString(), 
				oMatch.getSeason().toString()
		);		

		return transformed;
	}
	
	public static List<MatchParticipant> transformParticipants(long matchId, List<Participant> oParticipants){
		List<MatchParticipant> transformed = new ArrayList<>();
		for(Participant oParticipant : oParticipants){
			ParticipantStats oStats = oParticipant.getStats();
			MatchParticipant currentTransformed = new MatchParticipant(
					oParticipant.getSummonerID(),
					matchId, 
					oParticipant.getChampionID(), 
					oParticipant.getTeam().getID(), 
					oStats.getAssists(), 
					oStats.getLevel(),
					oStats.getCombatPlayerScore(), 
					oStats.getDeaths(), 
					oStats.getDoubleKills(), 
					oStats.getFirstBloodAssist(), 
					oStats.getFirstBloodKill(), 
					oStats.getFirstInhibitorAssist(), 
					oStats.getFirstInhibitorKill(), 
					oStats.getFirstTowerAssist(), 
					oStats.getFirstTowerKill(), 
					oStats.getGoldEarned(), 
					oStats.getGoldSpent(), 
					oStats.getInhibitorKills(), 
					oStats.getItem0ID(), 
					oStats.getItem1ID(), 
					oStats.getItem2ID(), 
					oStats.getItem3ID(), 
					oStats.getItem4ID(), 
					oStats.getItem5ID(), 
					oStats.getItem6ID(), 
					oStats.getKillingSprees(), 
					oStats.getKills(), 
					oStats.getLargestCriticalStrike(), 
					oStats.getLargestKillingSpree(), 
					oStats.getLargestMultiKill(), 
					oStats.getMagicDamageDealt(), 
					oStats.getMagicDamageDealtToChampions(), 
					oStats.getMagicDamageTaken(), 
					oStats.getMinionsKilled(), 
					oStats.getNeutralMinionsKilled(), 
					oStats.getNeutralMinionsKilledEnemyJungle(), 
					oStats.getNeutralMinionsKilledTeamJungle(), 
					oStats.getNodeCaptures(), 
					oStats.getNodeCaptureAssists(), 
					oStats.getNodeNeutralizations(), 
					oStats.getNodeNeutralizeAssists(), 
					oStats.getObjectivePlayerScore(), 
					oStats.getPentaKills(), 
					oStats.getPhysicalDamageDealt(), 
					oStats.getPhysicalDamageDealtToChampions(), 
					oStats.getPhysicalDamageTaken(), 
					oStats.getQuadraKills(), 
					oStats.getSightWardsBought(), 
					oStats.getTeamObjectives(), 
					oStats.getTotalDamageDealt(), 
					oStats.getTotalDamageDealtToChampions(), 
					oStats.getTotalDamageTaken(), 
					oStats.getTotalHealing(), 
					oStats.getTotalPlayerScore(), 
					oStats.getTotalScoreRank(), 
					oStats.getTotalTimeCrowdControlDealt(), 
					oStats.getTotalUnitsHealed(), 
					oStats.getTowerKills(), 
					oStats.getTripleKills(), 
					oStats.getTrueDamageDealt(), 
					oStats.getTrueDamageDealtToChampions(),
					oStats.getTrueDamageTaken(), 
					oStats.getUnrealKills(), 
					oStats.getVisionWardsBought(), 
					oStats.getWardsKilled(), 
					oStats.getWardsPlaced(), 
					oStats.getWinner()
				);
			transformed.add(currentTransformed);
		}
		
		return transformed;
	}

	public static Summoner transformSummoner(com.robrua.orianna.type.core.summoner.Summoner oSummoner) {
		return new Summoner(oSummoner.getID(), oSummoner.getName(), oSummoner.getProfileIconID(), oSummoner.getRevisionDate(), oSummoner.getLevel());
	}

	public static List<SummonerLeagueHistory> transformLeagues(long summonerId, List<League> oLeagues) {
		List<SummonerLeagueHistory> transformed = new ArrayList<>();
		for(League oLeague : oLeagues){
			for(LeagueEntry oLEntry : oLeague.getEntries()){
				String playerOrTeamId = oLEntry.getDto().getPlayerOrTeamId();
				if(!playerOrTeamId.startsWith("TEAM") && Long.parseLong(playerOrTeamId) == summonerId){
					int score = convertTierAndDivisionToScoreBase(oLeague.getTier(), oLEntry.getDivision()) + oLEntry.getLeaguePoints();
					SummonerLeagueHistory slh = new SummonerLeagueHistory(
							oLeague.getQueueType().toString(), 
							summonerId, 
							oLEntry.getLeaguePoints(), 
							oLeague.getTier().toString(), 
							oLEntry.getDivision(), 
							score, 
							oLEntry.getWins(),
							oLEntry.getLosses()
						);
					transformed.add(slh);
    				break;
    			}
			}
		}
		return transformed;
	}
	
	public static int convertTierAndDivisionToScoreBase(Tier tier, String division){
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

	protected static int getTierValue(Tier tier) {
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
	
	public static Tier getNextTier(Tier tier){
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
