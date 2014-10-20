var swordsApp = angular.module('swords', ['ui.router']);

swordsApp.config(function ($urlRouterProvider, $stateProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider.state('home', {
        url: '/',
        templateUrl: '/expansions'
    }).state('admin/login', {
        url: '/',
        templateUrl: '/admin/login',
        controller: function ($scope, $http, $state) {
            $scope.data = {};

            $scope.submit = function () {
                $scope.formDisabled = true;

                $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
                $http.post('/login', $.param($scope.data)).
                        success(function (data, status, headers, config) {
                            if (data.success) {
                                $state.go('admin/home');
                                $scope.$parent.setShowError(false);
                            } else {
                                $scope.$parent.setErrorText(data.error);
                                $scope.$parent.setShowError(true);
                                $scope.formDisabled = false;
                            }
                        }).
                        error(function (data, status, headers, config) {
                            $scope.$parent.setErrorText("Error while logging in!");
                            $scope.$parent.setShowError(true);
                        });
            };
        }
    }).state('admin/home', {
        url: '/',
        templateUrl: '/admin/home'
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