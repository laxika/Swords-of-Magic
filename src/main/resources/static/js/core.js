function toUpperCase(input) {
    return input.substring(0, 1).toUpperCase() + input.substring(1);
}

var swordsApp = angular.module('swords', ['ui.router']);

swordsApp.filter('capitalize', function () {
    return function (input, all) {
        return (!!input) ? input.replace(/([^\W_]+[^\s-]*) */g, function (txt) {
            return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        }) : '';
    };
});

swordsApp.config(function ($urlRouterProvider, $stateProvider) {
    $urlRouterProvider.when('', '/index');

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
            
            $scope.toTrusted = function (htmlCode) {
                return $sce.trustAsHtml(htmlCode);
            };

            $http.get('/expansion/data/' + $state.params.expansionId).success(function (data, status, headers, config) {
                $scope.expansion = data.expansion;
                $scope.cards = data.cardlist;

                //TODO: Move this normalisation to serverside. The server is 
                //much faster than the shitty js and we can cache the data.
                for (var i = 0; i < $scope.cards.length; i++) {
                    var card = $scope.cards[i];
                    
                    if($scope.cards[i].text) {
                        $scope.cards[i].text = $scope.cards[i].text.replace(/\{([^T].*?)\}/ig, '<img src="http://mtgimage.com/symbol/mana/$1/16.gif" alt="G mana"/>');
                        $scope.cards[i].text = $scope.cards[i].text.replace(/\{(\w+)\}/ig, '<img src="http://mtgimage.com/symbol/other/$1/16.gif" alt="G mana"/>');
                    }
                    
                    var printinfo = [];

                    printinfo.push({title: "Name", value: card.name});

                    if (card.color) {
                        var manacostStr = card.manacost.replace(/\{(\w+)\}/ig, '<img src="http://mtgimage.com/symbol/mana/$1/16.gif" alt="G mana"/>');

                        printinfo.push({title: "Manacost", value: manacostStr + " - " + '<img src="http://mtgimage.com/symbol/mana/' + card.cmc + '/16.gif" alt="' + card.cmc + ' mana"/>'});
                    }

                    if (card.color) {
                        printinfo.push({title: "Color", value: card.color.join(', ')});
                    } else {
                        printinfo.push({title: "Color", value: 'Colorless'});
                    }
                    if (card.subtypes) {
                        printinfo.push({title: "Type", value: card.types.join(', ') + " — " + card.subtypes.join(', ')});
                    } else {
                        printinfo.push({title: "Type", value: card.types.join(', ')});
                    }
                    printinfo.push({title: "Rarity", value: card.rarity});
                    printinfo.push({title: "Artist", value: card.artist});
                    if (card.number) {
                        printinfo.push({title: "Expansion number", value: card.number});
                    }
                    if (card.power) {
                        printinfo.push({title: "Power", value: card.power});
                    }
                    if (card.toughness) {
                        printinfo.push({title: "Toughness", value: card.toughness});
                    }
                    if (card.layout) {
                        printinfo.push({title: "Layout", value: toUpperCase(card.layout)});
                    }

                    var finalinfo = [];
                    var counter = 0;
                    for (var j = 0; j < printinfo.length; j += 2) {
                        if (counter + 2 < printinfo.length) {
                            finalinfo.push([printinfo[counter], printinfo[counter + 1]]);
                            counter += 2;
                        } else {
                            if (printinfo.length % 2 === 0) {
                                finalinfo.push([printinfo[printinfo.length - 1]]);
                            }
                        }
                    }

                    $scope.cards[i].printinfo = finalinfo;
                }
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