window.App = Ember.Application.create();

App.ApplicationController = Ember.Controller.extend({});

App.ApplicationAdapter = DS.RESTAdapter.extend({
  namespace: 'api/v1'
});

var attr = DS.attr;
App.Summoner = DS.Model.extend({
	name: attr('string'),
	profileIconId: attr(),
	revisionDate: attr('date'),
	summonerLevel: attr()
});