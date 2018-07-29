angular.module('users').controller('UserController', ['$scope', '$routeParams', '$location', 'Authentication', 'Users', function ($scope, $routeParams, $location, Authentication, Users) {

	$scope.name = Authentication.user ? Authentication.user : 'Guest Only';
	$scope.authentication = Authentication;

	$scope.find = function () {
		$scope.users = Users.query();
	};

	$scope.findOne = function () {
		$scope.user = Users.get({userId: $routeParams.userId});
	};

	$scope.update = function () {
		$scope.user.$update(
			function () {
				$location.path('/' + $scope.user.id);

			},
			function (errorResponse) {
				$scope.error = errorResponse.data.message;
			});
	};

	$scope.delete = function (user) {
		if (user) {
			user.$remove(
				function () {
					for (var userIdx in $scope.users) {
						if ($scope.users[userIdx] === user) {
							$scope.users.splice(userIdx, 1);
						}
					}
				});
		} else {
			$scope.user.$remove(
				function () {
					$location.path('/');
				});
		}
	};

	$scope.create = function () {
		var user = new Users({
			email: this.email,
			firstName: this.firstName,
			lastName: this.lastName
		});

		user.$save(
			//removed response parameter as it is not used.
			function () {
				$location.path('/' + user.id);
			},
			function (errorResponse) {
				$scope.error = errorResponse.data.message;
			});
	};
}
]);