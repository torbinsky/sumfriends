package sumfriends.utils;

import java.util.ArrayList;
import java.util.List;

import sumfriends.dto.api.LeagueHistoryDto;
import sumfriends.dto.api.SummonerDto;
import sumfriends.models.Summoner;
import sumfriends.models.SummonerLeagueHistory;

public class ApiDataTransformer {
    public static List<LeagueHistoryDto> transformLeagueHistories(List<SummonerLeagueHistory> historyModels){
        List<LeagueHistoryDto> transformed = new ArrayList<>();
        for(SummonerLeagueHistory model : historyModels){
            transformed.add(transform(model));
        }
        return transformed;
    }
    public static LeagueHistoryDto transform(SummonerLeagueHistory historyModel){
        return new LeagueHistoryDto(
                historyModel.id.toIdString(), 
                historyModel.id.summonerId, 
                historyModel.id.wins, 
                historyModel.id.losses, 
                historyModel.leaguePoints, 
                historyModel.tier, 
                historyModel.division, 
                historyModel.score, 
                historyModel.id.queue,
                historyModel.createdAt
         );
    }
    public static List<SummonerDto> transformSummoners(List<Summoner> summonerModels){
        List<SummonerDto> transformed = new ArrayList<>();
        for(Summoner model : summonerModels){
            transformed.add(transform(model));
        }
        return transformed;
    }
    public static SummonerDto transform(Summoner summonerModel){
        return new SummonerDto(summonerModel.id, summonerModel.name, summonerModel.profileIconId, summonerModel.revisionDate, summonerModel.summonerLevel);
    }
}
