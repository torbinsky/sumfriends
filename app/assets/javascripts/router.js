App.Router.map(function() {
	this.route('login', { path: '/login' });
	this.route('makebet', { path: '/makebet' });
	this.route('match-history', { path: '/match-history' });
	this.route('summoner', { path: '/summoner' });
});

App.SummonerRoute = Ember.Route.extend({
	model: function(){
		return this.store.find('summoner', 40856292);
	}
});