package sumfriends.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import play.db.ebean.Model;
import play.libs.Json;

@Entity
public class MatchParticipant extends Model {
	@EmbeddedId
	public MatchParticipantId id;
	public long championId;
	public long teamId;
	public long assists;
	public long champLevel;
	public long combatPlayerScore;
	public long deaths;
	public long doubleKills;
	public boolean firstBloodAssist;
	public boolean firstBloodKill;
	public boolean firstInhibitorAssist;
	public boolean firstInhibitorKill;
	public boolean firstTowerAssist;
	public boolean firstTowerKill;
	public long goldEarned;
	public long goldSpent;
	public long inhibitorKills;
	public long item0;
	public long item1;
	public long item2;
	public long item3;
	public long item4;
	public long item5;
	public long item6;
	public long killingSprees;
	public long kills;
	public long largestCriticalStrike;
	public long largestKillingSpree;
	public long largestMultiKill;
	public long magicDamageDealt;
	public long magicDamageDealtToChampions;
	public long magicDamageTaken;
	public long minionsKilled;
	public long neutralMinionsKilled;
	public long neutralMinionsKilledEnemyJungle;
	public long neutralMinionsKilledTeamJungle;
	public long nodeCapture;
	public long nodeCaptureAssist;
	public long nodeNeutralize;
	public long nodeNeutralizeAssist;
	public long objectivePlayerScore;
	public long pentaKills;
	public long physicalDamageDealt;
	public long physicalDamageDealtToChampions;
	public long physicalDamageTaken;
	public long quadraKills;
	public long sightWardsBoughtInGame;
	public long teamObjective;
	public long totalDamageDealt;
	public long totalDamageDealtToChampions;
	public long totalDamageTaken;
	public long totalHeal;
	public long totalPlayerScore;
	public long totalScoreRank;
	public long totalTimeCrowdControlDealt;
	public long totalUnitsHealed;
	public long towerKills;
	public long tripleKills;
	public long trueDamageDealt;
	public long trueDamageDealtToChampions;
	public long trueDamageTaken;
	public long unrealKills;
	public long visionWardsBoughtInGame;
	public long wardsKilled;
	public long wardsPlaced;
	public boolean winner;
	public MatchParticipant() {
		super();
	}
	public MatchParticipant(long summonerId, long matchId, long championId, long teamId,
			long assists, long champLevel, long combatPlayerScore, long deaths,
			long doubleKills, boolean firstBloodAssist, boolean firstBloodKill,
			boolean firstInhibitorAssist, boolean firstInhibitorKill,
			boolean firstTowerAssist, boolean firstTowerKill, long goldEarned,
			long goldSpent, long inhibitorKills, long item0, long item1,
			long item2, long item3, long item4, long item5, long item6,
			long killingSprees, long kills, long largestCriticalStrike,
			long largestKillingSpree, long largestMultiKill,
			long magicDamageDealt, long magicDamageDealtToChampions,
			long magicDamageTaken, long minionsKilled,
			long neutralMinionsKilled, long neutralMinionsKilledEnemyJungle,
			long neutralMinionsKilledTeamJungle, long nodeCapture,
			long nodeCaptureAssist, long nodeNeutralize,
			long nodeNeutralizeAssist, long objectivePlayerScore,
			long pentaKills, long physicalDamageDealt,
			long physicalDamageDealtToChampions, long physicalDamageTaken,
			long quadraKills, long sightWardsBoughtInGame, long teamObjective,
			long totalDamageDealt, long totalDamageDealtToChampions,
			long totalDamageTaken, long totalHeal, long totalPlayerScore,
			long totalScoreRank, long totalTimeCrowdControlDealt,
			long totalUnitsHealed, long towerKills, long tripleKills,
			long trueDamageDealt, long trueDamageDealtToChampions,
			long trueDamageTaken, long unrealKills,
			long visionWardsBoughtInGame, long wardsKilled, long wardsPlaced,
			boolean winner) {
		super();
		this.id = new MatchParticipantId(summonerId, matchId);
		this.championId = championId;
		this.teamId = teamId;
		this.assists = assists;
		this.champLevel = champLevel;
		this.combatPlayerScore = combatPlayerScore;
		this.deaths = deaths;
		this.doubleKills = doubleKills;
		this.firstBloodAssist = firstBloodAssist;
		this.firstBloodKill = firstBloodKill;
		this.firstInhibitorAssist = firstInhibitorAssist;
		this.firstInhibitorKill = firstInhibitorKill;
		this.firstTowerAssist = firstTowerAssist;
		this.firstTowerKill = firstTowerKill;
		this.goldEarned = goldEarned;
		this.goldSpent = goldSpent;
		this.inhibitorKills = inhibitorKills;
		this.item0 = item0;
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.item4 = item4;
		this.item5 = item5;
		this.item6 = item6;
		this.killingSprees = killingSprees;
		this.kills = kills;
		this.largestCriticalStrike = largestCriticalStrike;
		this.largestKillingSpree = largestKillingSpree;
		this.largestMultiKill = largestMultiKill;
		this.magicDamageDealt = magicDamageDealt;
		this.magicDamageDealtToChampions = magicDamageDealtToChampions;
		this.magicDamageTaken = magicDamageTaken;
		this.minionsKilled = minionsKilled;
		this.neutralMinionsKilled = neutralMinionsKilled;
		this.neutralMinionsKilledEnemyJungle = neutralMinionsKilledEnemyJungle;
		this.neutralMinionsKilledTeamJungle = neutralMinionsKilledTeamJungle;
		this.nodeCapture = nodeCapture;
		this.nodeCaptureAssist = nodeCaptureAssist;
		this.nodeNeutralize = nodeNeutralize;
		this.nodeNeutralizeAssist = nodeNeutralizeAssist;
		this.objectivePlayerScore = objectivePlayerScore;
		this.pentaKills = pentaKills;
		this.physicalDamageDealt = physicalDamageDealt;
		this.physicalDamageDealtToChampions = physicalDamageDealtToChampions;
		this.physicalDamageTaken = physicalDamageTaken;
		this.quadraKills = quadraKills;
		this.sightWardsBoughtInGame = sightWardsBoughtInGame;
		this.teamObjective = teamObjective;
		this.totalDamageDealt = totalDamageDealt;
		this.totalDamageDealtToChampions = totalDamageDealtToChampions;
		this.totalDamageTaken = totalDamageTaken;
		this.totalHeal = totalHeal;
		this.totalPlayerScore = totalPlayerScore;
		this.totalScoreRank = totalScoreRank;
		this.totalTimeCrowdControlDealt = totalTimeCrowdControlDealt;
		this.totalUnitsHealed = totalUnitsHealed;
		this.towerKills = towerKills;
		this.tripleKills = tripleKills;
		this.trueDamageDealt = trueDamageDealt;
		this.trueDamageDealtToChampions = trueDamageDealtToChampions;
		this.trueDamageTaken = trueDamageTaken;
		this.unrealKills = unrealKills;
		this.visionWardsBoughtInGame = visionWardsBoughtInGame;
		this.wardsKilled = wardsKilled;
		this.wardsPlaced = wardsPlaced;
		this.winner = winner;
	}
	
	@Override
	public String toString(){
		return Json.toJson(this).toString();
	}
}
