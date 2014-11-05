var swordsApp = angular.module('swords', ['ui.router']);

swordsApp.filter('capitalize', function () {
    return function (input, all) {
        return (!!input) ? input.replace(/([^\W_]+[^\s-]*) */g, function (txt) {
            return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        }) : '';
    };
});

swordsApp.directive('integer', function () {
    return {
        require: 'ngModel',
        link: function (scope, ele, attr, ctrl) {
            ctrl.$parsers.unshift(function (viewValue) {
                return parseInt(viewValue, 10);
            });
        }
    };
});

swordsApp.directive('integer', function () {
    return function (scope, element, attrs, ctrl) {

        var keyCode = [8, 9, 37, 39, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 110];
        element.bind("keydown", function (event) {
            if ($.inArray(event.which, keyCode) == -1) {
                scope.$apply(function () {
                    scope.$eval(attrs.onlyNum);
                    event.preventDefault();
                });

                event.preventDefault();
            }
        });
    };
});

swordsApp.config(function ($urlRouterProvider, $stateProvider) {
    $urlRouterProvider.otherwise('/');

    $stateProvider.state('home', {
        url: '/',
        templateUrl: '/expansion/template',
        controller: function ($scope, $http) {
            $scope.expansions = {};
            $scope.openExpansion = function (expansionId) {
                $('#expansion-accordion').find('.collapse.in').collapse('hide');
                $('#expansion-accordion').find('#' + expansionId).collapse('show');
            };

            $http.get('/expansion/data').success(function (data, status, headers, config) {
                $scope.expansions = data;
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
        controller: function ($scope, $state, $http, $sce) {
            $scope.cards = {};
            $scope.expansion = [];

            $scope.openCard = function (cardId) {
                $('#card-accordion').find('.collapse.in').collapse('hide');
                $('#card-accordion').find('#' + cardId).collapse('show');
            };

            $scope.sendUpdate = function (index, type) {
                var data = {};

                data.card = $scope.cards[index].data.id;
                data.field = type;
                data.value = $scope.cards[index].collection[type];

                $http.post('/admin/collection/update', $.param(data)).
                        error(function (data, status, headers, config) {
                            console.log("ERROR!");
                        });
            };

            $scope.replaceSymbols = function (text) {
                if (text === null) {
                    return '';
                }

                var newText = '';

                newText = text.replace(/\{([^T].*?)\}/ig, '<img src="http://mtgimage.com/symbol/mana/$1/16.gif" alt="$1 mana"/>');
                newText = newText.replace(/\{(\w+)\}/ig, '<img src="http://mtgimage.com/symbol/other/$1/16.gif" alt="$1 symbol"/>');

                return $sce.trustAsHtml(newText);
            };

            $http.get('/expansion/data/' + $state.params.expansionId).success(function (data, status, headers, config) {
                $scope.expansion = data.expansion;
                $scope.cards = data.cardlist;
            });
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