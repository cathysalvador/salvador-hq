angular.module('users').factory('Users', ['$resource', function ($resource) {

	return $resource('api/users/:userId', {
		userId: '@id'
	}, {
		update: {method: 'PUT'}
	});


}]);