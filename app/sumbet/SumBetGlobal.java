package sumbet;

import play.GlobalSettings;
import play.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

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
}
