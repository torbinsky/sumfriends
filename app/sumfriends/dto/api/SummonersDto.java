package sumfriends.dto.api;

import java.util.List;

import sumfriends.models.Summoner;

public class SummonersDto {
	public List<Summoner> summoners;

	public SummonersDto() {}
	public SummonersDto(List<Summoner> summoners) {
		this.summoners = summoners;
	}
}
