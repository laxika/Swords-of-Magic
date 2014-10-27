var swordsApp = angular.module('swords', ['ui.router']);

swordsApp.filter('capitalize', function () {
    return function (input, all) {
        return (!!input) ? input.replace(/([^\W_]+[^\s-]*) */g, function (txt) {
            return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        }) : '';
    };
});

swordsApp.config(function ($urlRouterProvider, $stateProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider.state('home', {
        url: '/',
        templateUrl: '/expansion/template',
        controller: function ($scope, $http) {
            $scope.sets = {};

            $http.get('/expansion/data').success(function (data, status, headers, config) {
                $scope.sets = data;
            });
        }
    }).state('admin/login', {
        url: '/',
        templateUrl: '/admin/login',
        controller: function ($scope, $http, $state) {
            $scope.data = {};

            $scope.submit = function () {
                $scope.formDisabled = true;

                $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
                $http.post('/admin/login', $.param($scope.data)).
                        success(function (data, status, headers, config) {
                            if (data.success) {
                                $state.go('admin/home');
                                $scope.$parent.setShowError(false);
                                $scope.$parent.setLoggedIn(true);
                                $scope.$parent.removeButton('Login');
                                $scope.$parent.addButton('Admin', 'admin/home');
                                $scope.$parent.addButton('Load carddata', 'admin/carddata');
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
    }).state('admin/carddata', {
        url: '/',
        templateUrl: '/admin/carddata'
    }).state('expansion/cardlist', {
        url: '/expansion/:expansionId',
        templateUrl: '/expansion/specific',
        controller: function ($scope, $state) {
            console.log($state.params.expansionId);
        }
    });
});

swordsApp.controller('MainController', function ($scope) {
    $scope.error = {
        show: false,
        text: ''
    };
    $scope.loggedIn = false;
    $scope.buttons = [
        {
            title: 'Home',
            sref: 'home'
        },
        {
            title: 'Login',
            sref: 'admin/login'
        }
    ];

    $scope.setShowError = function (show) {
        $scope.error.show = show;
    };

    $scope.setErrorText = function (errorText) {
        $scope.error.text = errorText;
    };

    $scope.setLoggedIn = function (loggedIn) {
        $scope.loggedIn = loggedIn;
    };

    $scope.removeButton = function (title) {
        $scope.buttons = $scope.buttons.filter(function (el) {
            return el.title !== title;
        });
    };

    $scope.addButton = function (title, sref) {
        $scope.buttons.push({
            title: title,
            sref: sref
        });
    };
});