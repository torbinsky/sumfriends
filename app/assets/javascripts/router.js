App.ApplicationRoute = Ember.Route.extend({
    beforeModel: function(transition) {
        this._saveTransition(transition);
    },
    actions: {
        willTransition: function (transition) {
            this._saveTransition(transition);
        }
    },
    _saveTransition: function (transition) {
        if (transition.targetName !== 'signin') {
            this.controllerFor('signin').set('previousTransition', transition);
        }
    }
});

App.Router.map(function() {
	this.route('signin', { path: '/signin' });
	this.route('signup', { path: '/signup' });
	this.route('makebet', { path: '/makebet' });
	this.route('match-history', { path: '/match-history' });
	this.route('summoner', { path: '/summoner' });
});

App.SummonerRoute = Ember.Route.extend({
	model: function(){
		return this.store.find('summoner', 40856292);
	}
});