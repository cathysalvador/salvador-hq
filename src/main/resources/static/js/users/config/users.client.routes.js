angular.module('users').config(['$routeProvider', function($routeProvider) {

    $routeProvider
        .when('/', {templateUrl: 'js/users/views/users.client.view.html'})
        .otherwise({redirectTo: '/'});
    }]);