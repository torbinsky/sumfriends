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
		addLink("history", sumfriends.controllers.routes.ApiController.getSummonerLeagueHistory(id).url());
		addLink("lastSoloRanked", sumfriends.controllers.routes.ApiController.getLastSummonerLeagueHistoryForQueue(id, QueueType.RANKED_SOLO_5x5.toString()).url());
	}

	@Override
	protected String _getWrapperName() {
		return "summoner";
	}
}
