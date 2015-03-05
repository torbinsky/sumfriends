App.LineChartComponent = Ember.Component.extend({
	chartLabels: [],
	chartData: [],
	didInsertElement : function() {
		var selector = '#' + this.elementId + ' .ct-chart';
		new Chartist.Line(selector, {
			labels : this.chartLabels,
			series : this.chartData
		}, {
			fullWidth : true
		});
	}
});