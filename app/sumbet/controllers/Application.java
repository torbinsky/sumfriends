package sumbet.controllers;

import javax.inject.Inject;

import play.Logger;
import play.Routes;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;
import sumbet.services.DataService;

public class Application extends Controller {
	static final Logger.ALogger logger = Logger.of(Application.class);
	@SuppressWarnings("unused")
	private DataService database;

	@Inject
	public Application(DataService database){
		this.database = database;
	}
	
    public Promise<Result> index() {
    	return Promise.promise(new Function0<Result>(){
			@Override
			public Result apply() throws Throwable {				
				return ok(sumbet.views.html.index.render());
			}
    	});
    }

    public Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes", 
				sumbet.controllers.routes.javascript.ApiController.getMatch(),
				sumbet.controllers.routes.javascript.ApiController.getSummoner()
		));
	}
}
