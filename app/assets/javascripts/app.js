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
	chartData: function(){
		return this.get('history').map(function(history){
			return history.get('score');
		});
	}.property('model.history'),
	chartLabels: function(){
		return this.get('history').map(function(history){
			return history.get('createdAt');
		});
	}.property('model.history')
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