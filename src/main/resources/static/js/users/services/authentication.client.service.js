angular.module('users').factory('Authentication', [
    function() {
        console.log('in service user:' + window.user);
        this.user = window.user;
        return {
            user: this.user
        };
    }
]);