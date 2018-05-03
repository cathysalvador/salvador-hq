angular.module('users').controller('UserController', ['$scope', 'Authentication',
    function($scope, Authentication) {
//        console.log(Authentication);
         $scope.name = Authentication.user ? Authentication.user : 'Guest Only';
     }
]);