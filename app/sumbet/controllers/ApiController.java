package sumbet.controllers;

import javax.inject.Inject;

import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import sumbet.models.Match;
import sumbet.services.DataService;
import sumbet.actions.AuthenticationRequiredAction;
import sumbet.dto.api.*;

@With({AuthenticationRequiredAction.class})
public class ApiController extends Controller {
	private DataService database;

	@Inject
	public ApiController(DataService database){
		this.database = database;		
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
}
