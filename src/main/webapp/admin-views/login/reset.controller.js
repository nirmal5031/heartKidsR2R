/**
 * Created by 535222 on 12/10/2015.
 */
angular.module('loginApp')
    .controller('ResetController',function ($scope,$http,$state) {

$scope.resetpassword = function() {
    if ( $scope.formAdminData.confirm_new_password !=   $scope.formAdminData.newpassword) {
        $scope.resetpass = "New Password and confirm password doesn't match";
    }
    else {
        var username = sessionStorage.getItem('username');
        $scope.formAdminData.username = username;

        $http({
            url: 'survey/resetnewpassword',
            method: "POST",
            data: $scope.formAdminData
            /* headers: {
             'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
             'Authorization': 'Basic ' + accessToken
             }*/
        })

            .then(function (response) {
                    var data = angular.toJson(response.data);
                    if (response.data.status === "RESETSUCCESS") {
                        localStorage.setItem("resetSuccess", "Password reset Successful ! Try login with new password")
                        $state.go('/login', {'Message': 'Reset successful. Please login with new password'});
                    }
                    else if (response.data.status === "INVALIDCREDENTIALS") {
                        $scope.resetMessage = "Old password entered is wrong. Please try again";
                    }
                    else {
                        $scope.resetMessage = "User is Not valid";
                    }
                },
                function (response) {
                    alert("Error respomse----->" + response.data);
                })
    }
}
    });