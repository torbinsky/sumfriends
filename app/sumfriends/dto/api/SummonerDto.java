package sumfriends.dto.api;

import java.util.Date;

import sumfriends.models.Summoner;

public class SummonerDto {
	public SummonerApiView summoner;
	
	public SummonerDto() {}
	public SummonerDto(long summonerId, int wins, int losses) {
		this.summoner = new SummonerApiView(summonerId, wins, losses);
	}
	public SummonerDto(Summoner summoner, int wins, int losses) {
		super();
		this.summoner = new SummonerApiView(summoner, wins, losses);
	}
	
	public static class SummonerApiView {
		public long id;
		public String name;
		public int profileIconId;
		public Date revisionDate;
		public long summonerLevel;
		public int wins;
		public int losses;
		
		public SummonerApiView(long summonerId, int wins, int losses) {
			this.id = summonerId;
			this.wins = wins;
			this.losses = losses;
		}
		
		public SummonerApiView(Summoner summoner, int wins, int losses) {
			this.id = summoner.id;
			this.name = summoner.name;
			this.profileIconId = summoner.profileIconId;
			this.revisionDate = summoner.revisionDate;
			this.summonerLevel = summoner.summonerLevel;
			this.wins = wins;
			this.losses = losses;
		}
	}
}
