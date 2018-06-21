angular.module('users').config(['$routeProvider', function ($routeProvider) {

    $routeProvider
        .when('/', {templateUrl: 'js/users/views/list-users.client.view.html'})
        .when('/create', {templateUrl: 'js/users/views/create-user.client.view.html'})
        .when('/:userId', {templateUrl: 'js/users/views/users.client.view.html'})
        .when('/:userId/edit', {templateUrl: 'js/users/views/edit-user.client.view.html'})
        .otherwise({redirectTo: '/'});
}]);