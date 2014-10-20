var swordsApp = angular.module('swords', ['ui.router']);

swordsApp.config(function ($urlRouterProvider, $stateProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider.state('home', {
        url: '/',
        templateUrl: '/expansions'
    }).state('admin/login', {
        url: '/',
        templateUrl: '/admin/login',
        controller: function ($scope) {
            $scope.data = {};
            $scope.submit = function () {
                console.log($scope.data);
            };
        }
    });
});

swordsApp.controller('MainController', function ($scope) {
    $scope.error = {
        show: false,
        text: ''
    };

    $scope.setShowError = function (show) {
        $scope.error.show = show;
    };

    $scope.setErrorText = function (errorText) {
        $scope.error.text = errorText;
    };
});