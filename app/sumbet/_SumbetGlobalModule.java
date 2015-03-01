package sumbet;

import sumbet.config._ConfigModule;
import sumbet.services._DataModule;

import com.google.inject.AbstractModule;

public class _SumbetGlobalModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new _ConfigModule());
		install(new _DataModule());
	}

}
