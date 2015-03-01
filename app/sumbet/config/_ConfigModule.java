package sumbet.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class _ConfigModule extends AbstractModule {

	@Override
	protected void configure() {
		
	}
	
	@Provides
	Config provideConfig(){
		return ConfigFactory.load();
	}

}
