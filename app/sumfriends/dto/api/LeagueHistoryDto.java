package sumfriends.dto.api;

import java.util.Date;


public class LeagueHistoryDto extends AbstractWrappedApiDto {
    public String id;
    public long summonerId;
    public int wins;
    public int losses;
    public int leaguePoints;
    public String tier;
    public String division;
    public int score;
    public String queue;
	public Date createdAt;

    public LeagueHistoryDto() {
        super();
    }

    public LeagueHistoryDto(String id, long summonerId, int wins, int losses, int leaguePoints, String tier, String division, int score, String queue, Date createdAt) {
        super();
        this.id = id;
        this.summonerId = summonerId;
        this.wins = wins;
        this.losses = losses;
        this.leaguePoints = leaguePoints;
        this.tier = tier;
        this.division = division;
        this.score = score;
        this.queue = queue;
		this.createdAt = createdAt;
    }
    
	@Override
    protected String _getWrapperName() {
        return "leagueHistory";
    }

}
