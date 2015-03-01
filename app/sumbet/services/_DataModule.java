package sumbet.services;

import com.google.inject.AbstractModule;

public class _DataModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataService.class).to(EbeanDataServiceImpl.class).asEagerSingleton();
		bind(RiotDataService.class).to(RiotDataServiceImpl.class).asEagerSingleton();
	}
}
