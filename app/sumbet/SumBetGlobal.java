package sumbet;

import play.Application;
import play.GlobalSettings;
import play.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.LoadPolicy;
import com.robrua.orianna.type.core.common.Region;

public class SumBetGlobal extends GlobalSettings {
	static final Logger.ALogger logger = Logger.of(GlobalSettings.class);
	
	private static Injector INJECTOR = null;
	
	public static Injector getInjector() {
		if (INJECTOR != null) {
			logger.trace("Injector id[" + INJECTOR.hashCode() + "]");
		}else{
			INJECTOR = Guice.createInjector(new _SumbetGlobalModule());
		}
		
		return INJECTOR;
	}
	
	@Override
	public <A> A getControllerInstance(Class<A> controllerClass)
			throws Exception {
		logger.trace("Getting controller[" + controllerClass + "]");
		return getInjector().getInstance(controllerClass);
	}

	@Override
	public void onStart(Application app) {
		// TODO: Find a (thread-safe) way of allowing different regions at the same time
		// Hard code to NA for now
		RiotAPI.setMirror(Region.NA);
		RiotAPI.setRegion(Region.NA);
		RiotAPI.setLoadPolicy(LoadPolicy.LAZY);
		RiotAPI.setAPIKey(app.configuration().getString("riot.api.key"));
		super.onStart(app);
	}
	
	
}
