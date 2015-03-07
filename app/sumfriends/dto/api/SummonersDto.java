package sumfriends.dto.api;

import java.util.List;

public class SummonersDto {
	public List<SummonerDto> summoners;

	public SummonersDto() {}
	public SummonersDto(List<SummonerDto> summoners) {
		this.summoners = summoners;
	}
}
