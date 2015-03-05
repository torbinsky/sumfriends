App.LineChartComponent = Ember.Component.extend({
	chartLabels: [],
	chartData: [],
	hasData: function(){
		return (typeof this.chartData !== 'undefined') && this.chartData.length > 0;
	}.property('chartData.@each'),
	didInsertElement : function() {
		var selector = '#' + this.elementId + ' .ct-chart';
		new Chartist.Line(selector, {
			labels : this.chartLabels,
			series : this.chartData
		}, {
			fullWidth : true
		});
	}.observes('hasData')
});