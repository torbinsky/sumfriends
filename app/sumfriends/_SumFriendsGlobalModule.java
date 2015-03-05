package sumfriends;

import sumfriends.config._ConfigModule;
import sumfriends.services._ServicesModule;

import com.google.inject.AbstractModule;

public class _SumFriendsGlobalModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new _ConfigModule());
		install(new _ServicesModule());
	}

}
