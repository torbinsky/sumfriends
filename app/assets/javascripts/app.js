window.App = Ember.Application.create({
	rootElement: '#app'
});

App.ApplicationAdapter = DS.RESTAdapter.extend({
  namespace: 'api/v1'
});

App.SessionController = Ember.Controller.extend({
	account: null,
	getSession: function(){
		var self = this;
		$.ajax({
			dataType: "json",
			url: '/api/v1/account',
			success: function(result){
				self.set('account', result.account);
			}
		});
	}.on('init')
});

App.IndexController = Ember.Controller.extend({
	needs: ['session'],
	// Using needs, the controller instance will be available on `controllers`
	account: Ember.computed.alias('controllers.session.account')
});

App.SummonerGlanceController = Ember.ObjectController.extend({
	summoner: null,
	data: [[1,2,3,4,5]],
	labels: ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday']
});

App.ScootiesController = Ember.Controller.extend({
	summonerIds: [31335008, 31243475, 50693141, 40856292, 35324185, 27463371, 30530946],
	getScooties: function(){
		var self = this;
		return this.get('summonerIds').map(function(summonerId) {
			return self.store.find('summoner', summonerId);
		});
	},
	scooties: function() {
		return this.getScooties();
	}.property('summonerIds.@each')
});

var attr = DS.attr;

App.Summoner = DS.Model.extend({
	name: attr('string'),
	profileIconId: attr('number'),
	revisionDate: attr('date'),
	summonerLevel: attr('number'),
	wins: attr('number'),
	losses: attr('number'),
	winPercent: function() {
		var wins = this.get('wins');
		var losses = this.get('losses');
		return (wins / (wins + losses) * 100).toPrecision(3);
	}.property('wins', 'losses')
});

App.Account = DS.Model.extend({  
	summonerId: attr(),
	email: attr('string')
});