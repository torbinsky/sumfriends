package sumfriends.controllers;

import java.util.Date;

import javax.inject.Inject;

import play.Logger;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import sumfriends.actions.SessionRequiredAction;
import sumfriends.dto.api.UserAccountDto;
import sumfriends.models.Summoner;
import sumfriends.models.SummonerLeagueHistory;
import sumfriends.services.AccountService;
import sumfriends.services.DataService;
import sumfriends.utils.ApiDataTransformer;

public class ApiController extends Controller {
	static final Logger.ALogger logger = Logger.of(ApiController.class);
	
	private DataService database;
	private AccountService accountService;

	@Inject
	public ApiController(DataService database, AccountService accountService){
		this.database = database;
		this.accountService = accountService;		
	}
	
	public Result foo(){
	    database.createOrUpdateSummoner(new Summoner(1234, "Test Summoner", 444, new Date(), 30));
	    database.getOrCreateSummonerLeagueHistory(new SummonerLeagueHistory("RANKED_SOLO_5X5", 1234, 40, "Diamond", "I", 1024, 5, 5));
	    database.getOrCreateSummonerLeagueHistory(new SummonerLeagueHistory("RANKED_SOLO_5X5", 1234, 32, "Diamond", "I", 1000, 5, 6));
	    return ok();
	}
	
	@With({SessionRequiredAction.class})
	public Promise<Result> getAccount(){
		logger.debug("getAccount");
		String token = getSessionToken();		
		return accountService.getAccountForSession(token).map(ua -> {
			if(ua == null){
				return notFound();
			}else{
				return ok(new UserAccountDto(ua).toJson());
			}
		});		
	}
	
	public Result getMatch(long matchId){
		logger.debug("getMatch(" + matchId + ")");		
		return ok();
	}
	
	public Promise<Result> getSummoners(int limit){
		return database.getSummoners(limit).map(summoners -> {
			return ok(Json.newObject().set("summoners", Json.toJson(ApiDataTransformer.transformSummoners(summoners))));
		});
	}
	
	public Promise<Result> getSummonerLeagueHistory(long summonerId){
	    logger.debug("getSummonerLeagueHistory(" + summonerId + ")");
	    return database.getSummonerLeagueHistory(summonerId).map(history -> {
	        return ok(Json.toJson(ApiDataTransformer.transformLeagueHistories(history)));
	    });
	}
	
	public Promise<Result> getSummoner(long summonerId){
		logger.debug("getSummoner(" + summonerId + ")");
		return database.getSummonerById(summonerId).map(s -> {
		    if(s == null){
		        return notFound();
		    }
			return ok(ApiDataTransformer.transform(s).toJson());
		});
	}
	
	protected String getSessionToken() {
		return SessionRequiredAction.getCookieToken(ctx());
	}
}
