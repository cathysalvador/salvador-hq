angular.module('users').controller('UserController', ['$scope', '$routeParams', '$location',  'Authentication', 'Users', function($scope, $routeParams, $location, Authentication, Users) {

         $scope.name = Authentication.user ? Authentication.user : 'Guest Only';
         $scope.authentication = Authentication;

         $scope.find = function() {
            console.log('user controller');
            $scope.users = Users.query();
         };
     }
]);