var attr = DS.attr;

App.Summoner = DS.Model.extend({
	name: attr('string'),
	profileIconId: attr('number'),
	revisionDate: attr('date'),
	summonerLevel: attr('number')
});

App.LeagueHistory = DS.Model.extend({
	
});

App.Account = DS.Model.extend({  
	summonerId: attr(),
	email: attr('string')
});