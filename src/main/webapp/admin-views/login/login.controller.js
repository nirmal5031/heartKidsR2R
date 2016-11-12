'use strict';
angular.module('loginApp')
    .controller('homepagecontroller', function ($scope)
    {
      $scope.development = false;
    })
   .controller('LoginController', ['$scope', 'AuthenticationService', 'LoginService','$state','dataService','$rootScope','$stateParams','$cookieStore','$crypto', function ($scope, AuthenticationService, LoginService,$state,dataService,$rootScope,$stateParams,$cookieStore,$crypto) {
        var accessToken = sessionStorage.getItem('tokenId');
        var loginflagvalue = sessionStorage.getItem('loginflag');
        var Message = $stateParams.Message;
       if($stateParams.Message != null)
       {
           $scope.errorMessage = Message;
       }
        if(accessToken == null) {

            sessionStorage.clear();
            localStorage.clear();
            $scope.isValidUser = false;
            $state.go('/login');
        }
        else {
            $state.go('/welcome');
        }
            $scope.login = function () {
            $scope.vm.dataLoading = true;
            var request = "/" + $scope.vm.heartkidusername + "/" + $scope.vm.heartkidpassword;

                LoginService.loginCustomer(request).then(function (successData) {

                    sessionStorage.setItem('loginflag',successData.loginflag);
                    if ((successData.status === "success")&&(successData.loginflag === 1)) {

                      var encrypted_userole = $crypto.encrypt(successData.userrole, 'HeartKids_');
                        sessionStorage.setItem('userrole',encrypted_userole);
                        sessionStorage.setItem('adminuser',successData.firstname+" "+ successData.lastname);
                        $scope.getAuthentionToken();
                    }
                    else if((successData.status === "success")&&(successData.loginflag != 1))
                    {
                        sessionStorage.setItem('username',successData.username );
                        $state.go('/reset');
                    }
                    else  {
                        $scope.isValidUser = true;
                        if (successData.status == "INVALIDCREDENTIALS") {
                            $scope.vm.error = "INVALID CREDENTIALS";
                            $scope.vm.dataLoading = false;
                        }
                        else if (successData.status == "NOUSER") {
                            $scope.vm.error = "INVALID CREDENTIALS";
                            $scope.vm.dataLoading = false;
                        }

                        else {
                            $scope.vm.error = "Error . Please try again later";
                            $scope.vm.dataLoading = false;
                        }
                    }
                });

            };

            $scope.getAuthentionToken = function () {
                var requestOauth = 'password=' + btoa($scope.vm.heartkidpassword) + '&username=' + $scope.vm.heartkidusername + '&grant_type=password';

                       AuthenticationService.getAuthention(requestOauth).then(function (successData) {
                    if (successData.access_token) {
                        sessionStorage.setItem('tokenId', successData.access_token);
                        $cookieStore.put('myHeartKidID',$scope.vm.heartkidusername.trim()+"_"+successData.access_token);
                        console.log("d.toJSON()"+$scope.vm.heartkidusername+"_"+successData.access_token);
                        $state.go('form.welcome');
                        $scope.vm.dataLoading = false;
                    }
                    else
                    {
                        var accessToken = sessionStorage.getItem('tokenId');
                        if(accessToken == null)
                        {
                            $state.go('/login');
                        }
                    }

                });
            }


        }]);
