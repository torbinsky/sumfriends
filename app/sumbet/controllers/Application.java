package sumbet.controllers;

import play.*;
import play.mvc.*;

public class Application extends Controller {

    public Result index() {
        return ok(sumbet.views.html.index.render());
    }

}
