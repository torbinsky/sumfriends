package sumbet.dto.api;

import sumbet.models.Summoner;

public class SummonerDto {
	public Summoner summoner;
	
	public SummonerDto() {}
	public SummonerDto(Summoner summoner) {
		super();
		this.summoner = summoner;
	}	
}
