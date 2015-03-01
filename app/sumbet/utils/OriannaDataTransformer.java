package sumbet.utils;

import java.util.ArrayList;
import java.util.List;

import play.libs.F.Tuple;
import sumbet.models.Match;
import sumbet.models.MatchParticipant;

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
}
