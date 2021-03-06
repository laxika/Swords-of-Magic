var swordsApp = angular.module('swords', ['ui.router']);

swordsApp.factory('dataHolder', function () {
    var service = {};

    service.isLoading = false;

    return service;
});

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

swordsApp.directive('loading', function ($http, dataHolder) {
    return {
        restrict: 'A',
        link: function (scope, elm, attrs)
        {
            scope.isLoading = function () {
                return dataHolder.isLoading;//$http.pendingRequests.length > 0;
            };

            scope.$watch(scope.isLoading, function (v)
            {
                if (v) {
                    elm.show();
                } else {
                    elm.hide();
                }
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
    $urlRouterProvider.otherwise('expansion/index');

    $stateProvider.state('home', {
        url: '/expansion/index',
        templateUrl: '/expansion/index',
        controller: function ($scope, $http, dataHolder) {
            $scope.search = {
                name: '',
                modern: false,
                standard: false,
                onlyWithCards: true
            };
            $scope.expansions = {};

            $scope.isModern = function () {
                return function (item) {
                    if (!$scope.search.modern) {
                        return true;
                    }

                    return item['data']['releaseDate'] >= 1059350400000;
                };
            };

            $scope.isStandard = function () {
                return function (item) {
                    if (!$scope.search.standard) {
                        return true;
                    }

                    return item['data']['releaseDate'] >= 1349395200000;
                };
            };

            $scope.isWithCards = function () {
                return function (item) {
                    if (!$scope.search.onlyWithCards) {
                        return true;
                    }

                    return item['collection'] !== null && (item['collection']['commonAmount'] != 0 || item['collection']['uncommonAmount'] || item['collection']['rareAmount'] || item['collection']['mythicAmount']);
                };
            };
            
            $scope.inArray = function(target, array) {
                return $.inArray(target, array) > -1;
            }

            $scope.openExpansion = function (expansionId) {
                $('#expansion-accordion').find('.collapse.in').collapse('hide');
                $('#expansion-accordion').find('#' + expansionId).collapse('show');
            };
            
            dataHolder.isLoading = true;

            $http.get('/expansion/data').success(function (data, status, headers, config) {
                $scope.expansions = data.expansionlist;
                dataHolder.isLoading = false;
            });
        }
    }).state('admin/login', {
        url: '/admin/login',
        templateUrl: '/admin/login',
        controller: function ($scope, $http, $state) {
            $scope.data = {};

            $scope.submit = function () {
                $scope.formDisabled = true;

                $http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded";
                $http.post('/admin/login', $.param($scope.data)).success(function (data, status, headers, config) {
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
                        }).error(function (data, status, headers, config) {
                            $scope.$parent.setErrorText("Error while logging in!");
                            $scope.$parent.setShowError(true);
                        });
            };
        }
    }).state('admin/home', {
        url: '/admin/index',
        templateUrl: '/admin/index'
    }).state('admin/carddata', {
        url: '/',
        templateUrl: '/admin/carddata'
    }).state('expansion/cardlist', {
        url: '/expansion/:expansionId',
        templateUrl: '/expansion/entry',
        controller: function ($scope, $state, $http, $sce, dataHolder) {
            $scope.cards = {};
            $scope.priceDivider = 1;
            $scope.priceUnit = '$';
            $scope.expansion = [];
            $scope.search = {
                name: ''
            };

            $scope.uppercaseFirst = function(string) {
                return string.charAt(0).toUpperCase() + string.slice(1);
            }

            $scope.openCard = function (cardId) {
                $('#card-accordion').find('.collapse.in').collapse('hide');
                $('#card-accordion').find('#' + cardId).collapse('show');
            };

            $scope.sendUpdate = function (card, type) {
                var data = {};

                data.card = card.data.id;
                data.field = type;
                data.value = card.collection[type];

                $http.post('/admin/collection/update', $.param(data))
                    .error(function (data, status, headers, config) {
                            console.log("ERROR!");
                    });
            };

            $scope.replaceSymbols = function (text) {
                if (text === null) {
                    return '';
                }

                    //fixing {B/G} because only BG is accepted by mtgimages
                var newText = text.replace(/\{([^T])\/([^T])\}/ig, '<img src="http://mtgimage.com/symbol/mana/$1$2/16.gif" alt="$1$2 mana"/>');
                    //{B} ->
                    newText = newText.replace(/\{([^T].*?)\}/ig, '<img src="http://mtgimage.com/symbol/mana/$1/16.gif" alt="$1 mana"/>');
                    //Fixing symbols like {T}
                    newText = newText.replace(/\{(\w+)\}/ig, '<img src="http://mtgimage.com/symbol/other/$1/16.gif" alt="$1 symbol"/>');

                return $sce.trustAsHtml(newText);
            };

            dataHolder.isLoading = true;

            $http.get('/expansion/data/' + $state.params.expansionId).success(function (data, status, headers, config) {
                $scope.expansion = data.expansion;
                $scope.cards = data.cardlist;
                $scope.priceDivider = data.priceDivider;
                $scope.priceUnit = data.priceUnit;
                dataHolder.isLoading = false;
            });
        }
    });
});

swordsApp.controller('MainController', function ($scope, $http) {
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

    $http.get('/user/data').success(function (data, status, headers, config) {
        if(data.loggedIn) {
            $scope.setLoggedIn(true);
            $scope.removeButton('Login');
            $scope.addButton('Admin', 'admin/home');
            $scope.addButton('Load carddata', 'admin/carddata');
        }
    });
});