window.App = Ember.Application.create();

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
	}.observes('account').on('init')
});

App.IndexController = Ember.Controller.extend({
	needs: ['session'],
	// Using needs, the controller instance will be available on `controllers`
	account: Ember.computed.alias('controllers.session.account')
});

var attr = DS.attr;

App.Summoner = DS.Model.extend({
	name: attr('string'),
	profileIconId: attr(),
	revisionDate: attr('date'),
	summonerLevel: attr()
});

App.Account = DS.Model.extend({  
	summonerId: attr(),
	email: attr('string')
});