package sumbet.controllers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import play.Logger;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import sumbet.actions.SessionRequiredAction;
import sumbet.dto.api.SummonerDto;
import sumbet.dto.api.UserAccountDto;
import sumbet.models.Match;
import sumbet.models.SummonerLeagueHistory;
import sumbet.services.AccountService;
import sumbet.services.DataService;

import com.robrua.orianna.type.core.common.QueueType;

@With({SessionRequiredAction.class})
public class ApiController extends Controller {
	static final Logger.ALogger logger = Logger.of(ApiController.class);
	
	private DataService database;
	private AccountService accountService;

	@Inject
	public ApiController(DataService database, AccountService accountService){
		this.database = database;
		this.accountService = accountService;		
	}
	
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
	
	public Promise<Result> getSummoner(long summonerId){
		logger.debug("getSummoner(" + summonerId + ")");
		return database.getSummonerById(summonerId).map(s -> {
			if(s == null){
				return notFound();
			}else{
				SummonerLeagueHistory history = database.getLastSummonerLeagueHistory(summonerId, QueueType.RANKED_SOLO_5x5.toString()).get(5, TimeUnit.SECONDS);
				return (Result)ok(Json.toJson(new SummonerDto(s,history.id.wins,history.id.losses)));
			}
		});
	}
	
	protected String getSessionToken() {
		return SessionRequiredAction.getCookieToken(ctx());
	}
}
