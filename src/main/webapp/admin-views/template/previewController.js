/**
 * Created by 237251 on 24/03/2016.
 */
'use strict';
angular.module('loginApp')

    .controller('PreviewContentController', ['$scope', '$http', '$filter', 'saveContent', 'ngDialog', '$cookieStore', '$timeout', '$state', '$stateParams', function ($scope, $http, $filter, saveContent, ngDialog, $cookieStore, $timeout, $state, $stateParams) {
alert("previewing");

 $scope.publishsite = function()

        {
            alert("publishing");

            $http({
                method: "GET",
                url: "survey/publish"

            })
                .then(function (response) {
                  alert(angular.toJson(response.data));
                })


        }

    }])