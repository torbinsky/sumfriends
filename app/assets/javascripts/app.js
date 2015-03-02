window.App = Ember.Application.create();

App.ApplicationAdapter = DS.RESTAdapter.extend({
  namespace: 'api/v1'
});

App.SessionController = Ember.Controller.extend({
	account: null
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

App.User = DS.Model.extend({  
	summonerId: attr(),
	email: attr('string')
});