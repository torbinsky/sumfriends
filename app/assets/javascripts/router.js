App.IndexRoute = Ember.Route.extend({
	model: function(){
		return this.store.find('summoner');
	}
});

App.Router.map(function() {
	this.route('makegoal', { path: '/makegoal' });
	this.route('match-history', { path: '/match-history' });
	this.route('summoner', { path: '/summoner/:summoner_id' });
	this.route('scooties');
});

App.SummonerRoute = Ember.Route.extend({
	model: function(params){
		return this.store.find('summoner', params.summoner_id);
	}
});