var attr = DS.attr;
var hasMany = DS.hasMany;
var belongsTo = DS.belongsTo;

App.Summoner = DS.Model.extend({
	name: attr('string'),
	profileIconId: attr('number'),
	revisionDate: attr('date'),
	summonerLevel: attr('number'),
	history: hasMany('league-history', {async: true})
});

App.LeagueHistory = DS.Model.extend({	
	leaguePoints: attr('number'),
	tier: attr('string'),
	division: attr('string'),
	score: attr('number'),
	createdAt: attr('date'),
	summonerId: attr('number'),
	queue: attr('string'),
	wins: attr('number'),
	losses: attr('number'),
	summoner: belongsTo('summoner', {async: true})
});

App.Account = DS.Model.extend({  
	summonerId: attr(),
	email: attr('string')
});