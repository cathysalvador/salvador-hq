angular.module('users').factory('Users', ['$resource', function($resource) {

    console.log('users service');
    return $resource('api/users/:userId', {
        userId: '@id'
      }, {
        update: {method: 'PUT'}
        });


    }]);