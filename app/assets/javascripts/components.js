App.LineChartComponent = Ember.Component.extend({
	hasData: function(){
		var doesHave = (typeof this.get('data') !== 'undefined') && this.get('data').length > 0;
		return doesHave;
	}.property('data.@each'),
	didInsertElement : function() {
		var self = this;
		if(self.get('hasData')){
			var selector = '#' + this.elementId + ' .ct-chart';
			new Chartist.Line(selector, {
				labels : self.get('labels'),
				series : [self.get('data')]
			}, {
				fullWidth : true
			});
		}
	}.observes('hasData')
});