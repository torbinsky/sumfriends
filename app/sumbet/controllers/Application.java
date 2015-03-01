package sumbet.controllers;

import javax.inject.Inject;

import play.Logger;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
	static final Logger.ALogger logger = Logger.of(Application.class);

	@Inject
	public Application(){
	}
	
    public Promise<Result> index() {
    	return Promise.promise(new Function0<Result>(){
			@Override
			public Result apply() throws Throwable {
				return ok(sumbet.views.html.index.render());
			}    		
    	});    	
    }

}
