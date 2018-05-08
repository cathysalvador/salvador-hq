angular.module('users').config(['$routeProvider', function($routeProvider) {

    $routeProvider
        .when('/', {templateUrl: 'js/users/views/users.client.view.html'})
        .when('/users', {templateUrl: 'js/users/views/list-users.client.view.html'})
        .otherwise({redirectTo: '/'});
    }]);