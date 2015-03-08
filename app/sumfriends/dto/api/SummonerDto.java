package sumfriends.dto.api;

import java.util.Date;

import com.robrua.orianna.type.core.common.QueueType;

public class SummonerDto extends EmberDataSingleElementDto {

	public long id;
	public String name;
	public int profileIconId;
	public Date revisionDate;
	public long summonerLevel;

	public SummonerDto(long id, String name, int profileIconId, Date revisionDate, long summonerLevel) {
		super();
		this.id = id;
		this.name = name;
		this.profileIconId = profileIconId;
		this.revisionDate = revisionDate;
		this.summonerLevel = summonerLevel;
		addLink("history", "/summoners/" + id + "/leagueHistories");
		addLink("last", "/summoners/" + id + "/leagueHistories/last");
		addLink("lastSoloRanked", "/summoners/" + id + "/leagueHistories/last/" + QueueType.RANKED_SOLO_5x5);
	}

	@Override
	protected String _getWrapperName() {
		return "summoner";
	}
}
