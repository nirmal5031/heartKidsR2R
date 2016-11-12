'use strict';

var app = angular.module('loginApp');

app.service('AuthenticationService', ['$http', '$q', 'AUTHENTICATION_URL', function($http, $q, AUTHENTICATION_URL) {
    this.getAuthention = function(request) {
        var deferred = $q.defer();
        $http.post(AUTHENTICATION_URL, request,
		{
			headers: {
				'Authorization': 'Basic Y2xpZW50YXBwOmNsaWVudHBhc3M=',
				'Content-Type': 'application/x-www-form-urlencoded'				
				}
			}).success(function(data) {
            deferred.resolve(data);
			}).error(function(data) {
            deferred.reject(data);
		});
        return deferred.promise;
	};
}]);