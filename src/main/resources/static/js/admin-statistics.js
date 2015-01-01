$(function () {
    $.getJSON('http://www.highcharts.com/samples/data/jsonp.php?filename=analytics.csv&callback=?', function (csv) {

        $('#chart-collection-change').highcharts({
            title: {
                text: 'Collection size change'
            },
            subtitle: {
                text: 'by rarity'
            },
            xAxis: {
                type: 'datetime',
                tickInterval: 24 * 3600 * 1000
            },
            yAxis: [{ // left y axis
                title: {
                    text: null
                },
                labels: {
                    align: 'left',
                    x: 3,
                    y: 16,
                    format: '{value:.,0f}'
                },
                showFirstLabel: false
            }, { // right y axis
                linkedTo: 0,
                gridLineWidth: 0,
                opposite: true,
                title: {
                    text: null
                },
                labels: {
                    align: 'right',
                    x: -3,
                    y: 16,
                    format: '{value:.,0f}'
                },
                showFirstLabel: false
            }],
            credits: {
                enabled: false
            },
            legend: {
                align: 'left',
                verticalAlign: 'top',
                y: 20,
                floating: true,
                borderWidth: 0
            },
            tooltip: {
                shared: true,
                crosshairs: true
            },
            series: [{
                name: 'All cards',
                pointInterval: 24 * 3600 * 1000,
                pointStart: Date.UTC(2015, 0, 1),
                data: [3,17,3,7,6,5,9],
                color: '#000000'
            }, {
                name: 'Mythic rare',
                pointInterval: 24 * 3600 * 1000,
                pointStart: Date.UTC(2015, 0, 1),
                data: [5,10,7,4,5,6,3],
                color: '#FFA000'
            }, {
                name: 'Rare',
                pointInterval: 24 * 3600 * 1000,
                pointStart: Date.UTC(2015, 0, 1),
                data: [2,7,8,5,11,2,3],
                color: '#FFDF00'
            }, {
                name: 'Uncommon',
                pointInterval: 24 * 3600 * 1000,
                pointStart: Date.UTC(2015, 0, 1),
                data: [4,3,6,13,2,4,5],
                color: '#E0E0E0'
            }, {
                name: 'Common',
                pointInterval: 24 * 3600 * 1000,
                pointStart: Date.UTC(2015, 0, 1),
                data: [5,4,3,4,12,17,7],
                color: '#808080'
            }]
        });

        $('#chart-collection-rarity').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: 1,//null,
                plotShadow: false
            },
            title: {
                text: 'Collection rarity share'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            credits: {
                enabled: false
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: 'Collection percent',
                data: [
                    {
                        name: 'Mythic rare',
                        y: 10.0,
                        color: '#FFA000'
                    },
                    {
                        name: 'Rare',
                        y: 20.0,
                        color: '#FFDF00'
                    },
                    {
                        name: 'Uncommon',
                        y: 30.0,
                        color: '#E0E0E0'
                    },
                    {
                        name: 'Common',
                        y: 40.0,
                        color: '#808080'
                    }
                ]
            }]
        });
    });

});