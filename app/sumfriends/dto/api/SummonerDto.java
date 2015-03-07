package sumfriends.dto.api;

import java.util.Date;

public class SummonerDto extends AbstractWrappedApiDto {
    public long id;
    public String name;
    public int profileIconId;
    public Date revisionDate;
    public long summonerLevel;
    
    public SummonerDto(long summonerId) {
        super();
        this.id = summonerId;
    }

    public SummonerDto(long id, String name, int profileIconId, Date revisionDate, long summonerLevel) {
        super();
        this.id = id;
        this.name = name;
        this.profileIconId = profileIconId;
        this.revisionDate = revisionDate;
        this.summonerLevel = summonerLevel;
    }

    @Override
    protected String _getWrapperName() {
        return "summoner";
    }
}
