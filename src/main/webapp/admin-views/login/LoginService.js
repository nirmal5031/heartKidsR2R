'use strict';

var app = angular.module('loginApp');

app.service('LoginService', ['$http', '$q', 'LOGIN_URL', function($http, $q, LOGIN_URL) {
     this.loginCustomer = function(request) {
        var deferred = $q.defer();

        $http.post(LOGIN_URL+request).success(function(data) {
            deferred.resolve(data);

        }).error(function(data) {
            deferred.reject(data);
        });
        return deferred.promise;
    };
}]);