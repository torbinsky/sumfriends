App.Router.map(function() {
	this.route('makebet', { path: '/makebet' });
	this.route('match-history', { path: '/match-history' });
	this.route('summoner', { path: '/summoner' });
	this.resource('scooties', function() {
		this.resource('summoner', {path: "/:summoner_id"});
	});
});

App.SummonerRoute = Ember.Route.extend({
	model: function(){
		return this.store.find('summoner', 40856292);
	}
});