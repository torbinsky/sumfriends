package sumbet.controllers;

import javax.inject.Inject;

import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import sumbet.models.Match;
import sumbet.services.AccountService;
import sumbet.services.DataService;
import sumbet.actions.SessionRequiredAction;
import sumbet.dto.api.*;

@With({SessionRequiredAction.class})
public class ApiController extends Controller {
	private DataService database;
	private AccountService accountService;

	@Inject
	public ApiController(DataService database, AccountService accountService){
		this.database = database;
		this.accountService = accountService;		
	}
	
	public Promise<Result> getAccount(){
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
		return ok(Json.toJson(new Match()));
	}
	
	public Promise<Result> getSummoner(long summonerId){
		return database.getSummonerById(summonerId).map(s -> {
			if(s == null){
				return notFound();
			}else{				
				return (Result)ok(Json.toJson(new SummonerDto(s)));
			}
		});
	}
	
	protected String getSessionToken() {
		return SessionRequiredAction.getCookieToken(ctx());
	}
}
