angular.module('users').config(['$routeProvider', function($routeProvider) {

    $routeProvider
        .when('/users', {templateUrl: 'js/users/views/list-users.client.view.html'})
        .when('/users/create', {templateUrl: 'js/users/views/create-user.client.view.html'})
        .when('/users/:userId', {templateUrl: 'js/users/views/users.client.view.html'})
        .when('/users/:userId/edit', {templateUrl: 'js/users/views/edit-user.client.view.html'})
        .otherwise({redirectTo: '/'});
    }]);