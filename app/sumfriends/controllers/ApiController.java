package sumfriends.controllers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import play.Logger;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import sumfriends.actions.SessionRequiredAction;
import sumfriends.dto.api.SummonerDto;
import sumfriends.dto.api.SummonersDto;
import sumfriends.dto.api.UserAccountDto;
import sumfriends.models.Match;
import sumfriends.models.SummonerLeagueHistory;
import sumfriends.services.AccountService;
import sumfriends.services.DataService;

import com.robrua.orianna.type.core.common.QueueType;

public class ApiController extends Controller {
	static final Logger.ALogger logger = Logger.of(ApiController.class);
	
	private DataService database;
	private AccountService accountService;

	@Inject
	public ApiController(DataService database, AccountService accountService){
		this.database = database;
		this.accountService = accountService;		
	}
	
	@With({SessionRequiredAction.class})
	public Promise<Result> getAccount(){
		logger.debug("getAccount");
		String token = getSessionToken();		
		return accountService.getAccountForSession(token).map(ua -> {
			if(ua == null){
				return notFound();
			}else{
				return (Result)ok(Json.toJson(new UserAccountDto(ua)));
			}
		});		
	}
	
	public Result getMatch(long matchId){
		logger.debug("getMatch(" + matchId + ")");		
		return ok(Json.toJson(new Match()));
	}
	
	public Promise<Result> getSummoners(int limit){
		return database.getSummoners(limit).map(summoners -> {
			return ok(Json.toJson(new SummonersDto(summoners)));
		});
	}
	
	public Promise<Result> getSummoner(long summonerId){
		logger.debug("getSummoner(" + summonerId + ")");
		return database.getSummonerById(summonerId).map(s -> {
			if(s == null){
				return notFound();
			}else{
				SummonerLeagueHistory history = database.getLastSummonerLeagueHistory(summonerId, QueueType.RANKED_SOLO_5x5.toString()).get(5, TimeUnit.SECONDS);
				int wins = 0, losses = 0;
				if(history != null){
					wins = history.id.wins;
					losses = history.id.losses;
				}
				return (Result)ok(Json.toJson(new SummonerDto(s,wins,losses)));
			}
		});
	}
	
	protected String getSessionToken() {
		return SessionRequiredAction.getCookieToken(ctx());
	}
}
