package sumbet.services;

import com.google.inject.AbstractModule;

public class _ServicesModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataService.class).to(EbeanDataServiceImpl.class);
		bind(RiotDataService.class).to(RiotDataServiceImpl.class);
		bind(AccountService.class).to(AccountServiceImpl.class);
	}
}
