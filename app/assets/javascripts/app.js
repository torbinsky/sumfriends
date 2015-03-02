window.App = Ember.Application.create();

App.ApplicationAdapter = DS.RESTAdapter.extend({
  namespace: 'api/v1'
});

App.SessionController = Ember.Controller.extend({
	isAuthenticated: false
});

App.IndexController = Ember.Controller.extend({
	needs: ['session'],
	// Using needs, the controller instance will be available on `controllers`
	isLoggedIn: Ember.computed.alias('controllers.session.isAuthenticated')
});

App.SigninController = Ember.Controller.extend({
	needs: ['session'],
	isLoggedIn: Ember.computed.alias('controllers.session.isAuthenticated'),
	username: '',
	password: '',	
	actions: {
		signIn: function () {
			var self = this;
			// There is an alias to the session property, so this change propagates
			// to the session object then the IndexController.
			this.set('isLoggedIn', true);
			App.session.logIn(this.get('username'), this.get('password')).then(
			function() {
				var previousTransition = self.get('previousTransition');
				if (previousTransition) {
					previousTransition.retry();
					return;
				}
				
				self.transitionToRoute('index');
			});
		}
	}
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