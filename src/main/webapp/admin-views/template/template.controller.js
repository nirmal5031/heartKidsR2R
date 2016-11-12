/**
 * Created by 237251 on 21/02/2016.
 */

'use strict';
angular.module('loginApp')
    .directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function () {
                    scope.$apply(function () {
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }])
    .service('dataServiceAdmin', function () {

        var _pageQuestionId;

        this.pageQuestionId = _pageQuestionId;


    })
    .directive('elastic', [
        '$timeout',
        function($timeout) {
            return {
                restrict: 'A',
                link: function($scope, element) {
                    $scope.initialHeight = $scope.initialHeight || element[0].style.height;
                    var resize = function() {
                        element[0].style.height = $scope.initialHeight;
                        element[0].style.height = "" + element[0].scrollHeight + "px";
                    };
                    element.on("input change", resize);
                    $timeout(resize, 0);
                }
            };
        }
    ])

    .service('fileUpload', ['$http', function ($http) {
        this.uploadFileToUrl = function (file, uploadUrl) {
            var fd = new FormData();
            fd.append('file', file);
            $http.post(uploadUrl, fd, {
                    transformRequest: angular.identity,
                    headers: {'Content-Type': undefined}
                })
                .success(function () {
                })
                .error(function () {
                });
        }
    }])
    .controller('imageuploadController', ['$scope', '$http', 'fileUpload', function ($scope, $http, fileUpload) {
        $scope.formUpload = {};
        $scope.formUpload.short_desc = {};
        $scope.isDisabled = true;
        $scope.uploadFile = function () {
            var file = $scope.myFile;
            console.log('file is ' + file);
            console.dir(file);
            var uploadUrl = "survey/upload";
            fileUpload.uploadFileToUrl(file, uploadUrl);
        };

    }])
    .controller('TemplateManagerController', ['$scope', '$http', '$filter', 'fileUpload','ngDialog','$timeout','$state','$stateParams','$cookies','$window','$controller','dataServiceAdmin','$crypto', function ($scope, $http, $filter, fileUpload, ngDialog, $timeout, $state, $stateParams, $cookies, $window, $controller, dataServiceAdmin, $crypto) {

        $controller('TemplateQuestionarieController', {$scope: $scope})
		var decrypt_userroletype = sessionStorage.getItem('userrole');
                var userroletype = $crypto.decrypt(decrypt_userroletype, 'HeartKids_');
        $scope.isDisabled = true;
       var data = $cookies['myHeartKidID'];
        var val =  data?data.replace(/"/g, ''):'';

        $scope.redirectToDev = "index.html#/form/researchdev"+val;
        if(userroletype != 'Superuser')
        {
            $state.go('form.welcome');
            $scope.responseMessage = "Login as a superuser to access the template manager";
        }

        $scope.HP_Sec_1 = 'true';
        $scope.typelistArray = ["button", "no button"];
        $scope.createbannerbtn = 'false';
        $scope.editorcreatetab = 'false';
        $scope.formThankyou = {};
        $scope.formDisclaimer = {};
        $scope.formDisclaimerCarer = {};
        $scope.formEmailTemp = {};
        $scope.advancedoption = false;
        $scope.SuccessMessage = false;

        $scope.$watch('advancedoption', function () {
            $scope.toggleText = $scope.advancedoption ? '- Standard edit' : '+ Advanced edit';
        })
        $scope.$watch('$viewContentLoaded', function () {

            $http({
                url: 'survey/downloadcmsfile/homepage',
                method: "GET"
            })
                .then(function (response) {
                    $scope.template_content = angular.fromJson(response.data);
                    $scope.bannernum  = 8-($scope.template_content.homebannercontent.length);
                    $scope.usertypnumb = 2-($scope.template_content.usertypetab.length);
                })


            $http({
                url: 'survey/downloadcmsfile/questionarietem',
                method: "GET"
            })
                .then(function (response) {
                    $scope.ques_template_content = angular.fromJson(response.data);
                })

                $http({
                    url: 'survey/downloadcmsfile/thankyou',
                    method: "GET"
                })
                    .then(function (response) {
                        var response = angular.fromJson(response.data);
                        $scope.formThankyou = response;
                    })

            $http({
                url: 'survey/downloadcmsfile/disclaimerpatient',
                method: "GET"
            })
                .then(function (response) {
                    var response = angular.fromJson(response.data);
                    $scope.formDisclaimer = response;
                })

            $http({
                url: 'survey/downloadcmsfile/disclaimercarer',
                method: "GET"
            })
                .then(function (response) {
                    var response = angular.fromJson(response.data);
                    $scope.formDisclaimerCarer = response;
                })


            $http({
                url: 'survey/downloadcmsfile/emailtemp',
                method: "GET"
            })
                .then(function (response) {
                    var response = angular.fromJson(response.data);
                    $scope.formEmailTemp = response;
                })

            });

            $scope.updatethankyou = function()
            {
                $http({
                    method: "POST",
                    url: "survey/updatejson/thankyou",
                    headers: {'Content-Type': 'application/json'},
                    data: $scope.formThankyou

                })
                    .then(function (response) {
                        $scope.ResponseMessage = "Confirmation page updated successfully";
                    })

            }
        $scope.updatedisclaimercarer = function()
        {
            $http({
                method: "POST",
                url: "survey/updatejson/disclaimercarer",
                headers: {'Content-Type': 'application/json'},
                data: $scope.formDisclaimerCarer

            })
                .then(function (response) {
                    $scope.ResponseMessage = "Disclaimer page updated successfully";
                })

        }
        $scope.updatedisclaimer = function()
        {
            $http({
                method: "POST",
                url: "survey/updatejson/disclaimerpatient",
                headers: {'Content-Type': 'application/json'},
                data: $scope.formDisclaimer

            })
                .then(function (response) {
                    $scope.ResponseMessage = "Disclaimer page updated successfully";
                })

        }


        $scope.updateemailtemp = function()
        {
            $http({
                method: "POST",
                url: "survey/updatejson/emailtemp",
                headers: {'Content-Type': 'application/json'},
                data: $scope.formEmailTemp

            })
                .then(function (response) {
                    $scope.ResponseMessage = "email template updated successfully";
                })

        }

        $scope.range = function(n) {
            return new Array(n);
        };
        $scope.homepagecontentedit = function () {
            $http({
                url: 'survey/downloadcmsfile',
                method: "GET"
            })
                .then(function (response) {
                    $scope.template_homecontent = angular.fromJson(response.data);


                })
        }


        $scope.imagecontentedit = function (idval) {
            $scope.ResponseMessage = "";
            $scope.formUpload = {};
            $scope.createbannerbtn = false;

            var jsonhomecontent = $scope.template_content.homebannercontent;
            var result = $filter('filter')(jsonhomecontent, {id: idval})[0];
            $scope.formUpload = result;
            ngDialog.open({
                template: 'admin-views/template/edit-homebannercont.html',
                scope: $scope,
                backdrop: 'static', closeByDocument: false, closeByEscape: true

            })     }

        $scope.createnewusertype = function () {
            $scope.ResponseMessage = '';
$scope.createusertypetab = true;
            $scope.formUsertype = {};
            var finaljsonval = $scope.template_content.usertypetab;

            var jsnlength = new Date();
            var format= 'ddmmyyHms';
            var refid = $filter('date')(jsnlength, format);
                $scope.formUsertype.id = refid;

            ngDialog.open({
                template: 'admin-views/template/homepagesection-3.html',
                scope: $scope,
                backdrop: 'static', closeByDocument: false, closeByEscape: true

            })

            /*  $scope.ResponseMessage = "";
             var finaljsonval = $scope.template_content;
             var jsnlength = finaljsonval.usertypetab.length;
             finaljsonval.usertypetab.splice(jsnlength, 0, angular.fromJson(formUsertype));
             console.log("form crreatea user---->"+finaljsonval);
             */

            /*  $http({
             method: "POST",
             url: "survey/updatejson",
             headers: {'Content-Type': 'application/json'},
             data: finaljsonval

             })
             .then(function (response) {
             $scope.editorcreatetab = 'false';
             $scope.createbannerbtn = 'false';
             $scope.ResponseMessage = "Banner created successfully";
             })
             */

        }
        $scope.createbanner = function () {


            $scope.formUpload = {};
            $scope.formUpload.short_desc = ["SomeValue Line 1", "SomeValue Line 2"];
            $scope.createbannerbtn = true;
            $scope.ResponseMessage = "";

            var finaljsonval = $scope.template_content.homebannercontent;
            var jsnlength = finaljsonval.length;
            $scope.formUpload.id = jsnlength;
            ngDialog.open({
                template: 'admin-views/template/edit-homebannercont.html',
                scope: $scope,
                backdrop: 'static', closeByDocument: false, closeByEscape: true

            })
        }
        $scope.updatehomeuser = function (idval) {
            ngDialog.close();
            $scope.ResponseMessage = '';
            var finaljsonval = $scope.template_content;
            if ($scope.myFile != null) {
                $scope.uploadFile();
            }
            $http({
                method: "POST",
                url: "survey/updatejson/homepage",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.editusertypetab = 'false';
                    $scope.SuccessMessage = true;
                    $scope.ResponseMessage = "Usertype content updated successfully";
                })
        }

        $scope.updatebannerjson = function (idval) {
            ngDialog.close();
            $scope.ResponseMessage = '';
            var finaljsonval = $scope.template_content;

            if ($scope.myFile != null) {
                $scope.uploadFile();
            }
            $http({
                method: "POST",
                url: "survey/updatejson/homepage",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {

                 $scope.editorcreatetab = 'false';
                    $scope.close = function(result) {
                        close(result, 500);

                        // close, but give 500ms for bootstrap to animate
                    };


                    $scope.SuccessMessage = 'true';
                    $scope.ResponseMessage = "Banner updated successfully";


                })
        }

        $scope.addinghomeuser = function (formUsertypec) {
            ngDialog.close();

            $scope.ResponseMessage = "";
            var finaljsonval = $scope.template_content;
            var jsnlength = finaljsonval.usertypetab.length;
            finaljsonval.usertypetab.splice(jsnlength, 0, angular.fromJson(formUsertypec));
            $http({
                method: "POST",
                url: "survey/updatejson/homepage",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {

                    $scope.ResponseMessage = "new usertype created successfully";
                    $scope.usertypnumb = 4-($scope.template_content.usertypetab.length);

                })

        }

        $scope.addingbannerjson = function () {

            ngDialog.close();
            $scope.ResponseMessage = "";
            var finaljsonval = $scope.template_content;
            var jsnlength = finaljsonval.homebannercontent.length;
            finaljsonval.homebannercontent.splice(jsnlength, 0, angular.fromJson($scope.formUpload));
console.log(angular.toJson($scope.formUpload));
            $http({
                method: "POST",
                url: "survey/updatejson/homepage",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.editorcreatetab = false;
                    $scope.createbannerbtn = false;
                    $scope.bannernum  = 8-($scope.template_content.homebannercontent.length);
                    $scope.SuccessMessage = true;
                    $scope.ResponseMessage = "Banner created successfully";
                })

        }

        $scope.deletehomeuser = function (idvalue) {

            $scope.ResponseMessage = "";

            var result = $filter('filter')($scope.template_content, {id: idvalue});
            // var test = result.usertypetab.splice(idvalue, 1);
            var index = $scope.template_content.usertypetab.indexOf(idvalue);
            result.usertypetab.splice(index, 1);


            $http({
                method: "POST",
                url: "survey/updatejson/homepage",
                headers: {'Content-Type': 'application/json'},
                data: result

            })
                .then(function (response) {
                    $scope.ResponseMessage = "User type deleted successfully";
                    $scope.createusertypetab = 'false';
                    $scope.editusertypetab = 'false';
                    $scope.usertypnumb  = 4-($scope.template_content.usertypetab.length);

                })
        }
        $scope.deletebannerjson = function (idvalue) {

            $scope.ResponseMessage = "";

            var result = $filter('filter')($scope.template_content, {id: idvalue});

           // alert(angular.toJson(result));
            // console.log('resul' + angular.toJson(result));
            //var test = result.homebannercontent.splice(idvalue, 1);

            result.homebannercontent.splice(idvalue, 1);

            $scope.editorcreatetab = 'false';
          //  console.log(result);

            $http({
                method: "POST",
                url: "survey/updatejson/homepage",
                headers: {'Content-Type': 'application/json'},
                data: result

            })
                .then(function (response) {
                    $scope.ResponseMessage = "Banner deleted successfully";
                    $scope.bannernum  = 8-($scope.template_content.homebannercontent.length);


                })
        }
        $scope.uploadFile = function () {
            var file = $scope.myFile;

            if (file != null || file != "") {
                console.dir(file);
                var uploadUrl = "survey/upload";
                fileUpload.uploadFileToUrl(file, uploadUrl);
            }
        };
        $scope.homepagesec = function (section) {
            $scope.SuccessMessage = false;
            $scope.successResponse = false;
            $scope.ResponseMessage = '';
            if (section == 'sec1') {
                $scope.HP_Sec_1 = "true";
                $scope.HP_Sec_2 = "false";

                $scope.HP_Sec_3 = "false";

            }
            if (section == 'sec2') {
                $scope.HP_Sec_1 = "false";
                $scope.HP_Sec_2 = "true";

                $scope.HP_Sec_3 = "false";
                $scope.formContent = {};

            }
            if (section == 'sec3') {
                $scope.HP_Sec_1 = "false";
                $scope.HP_Sec_2 = "false";
                $scope.HP_Sec_3 = "true";

            }

        }


        $scope.usertypecontentedit = function (idval) {
            $scope.editusertypetab = 'true';

            $scope.ResponseMessage = "";
            $scope.formUsertype = {};

            var jsonhomecontent = $scope.template_content.usertypetab;
            var result = $filter('filter')(jsonhomecontent, {id: idval})[0];
            $scope.formUsertype = result;

            ngDialog.open({
                template: 'admin-views/template/homepagesection-3.html',
                scope: $scope,
                backdrop: 'static', closeByDocument: false, closeByEscape: true

            })


        }

        $scope.updatesec2content = function()
        {
        var finaljsonval = $scope.template_content;

            $http({
                method: "POST",
                url: "survey/updatejson/homepage",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.SuccessMessage = true;
                    $scope.ResponseMessage = "Section 2 homepage contents updated successfully";
                })
        }
        $scope.notSorted = function(obj){
            if (!obj) {
                return [];
            }
            return Object.keys(obj);
        }
        $scope.validatekey = function(obj){
            if (!obj) {
               console.log("objyec rendering"+obj);
            }
            return Object.keys(obj);
        }
$scope.deleteDisContent = function(items, index)

{
    var disclaimerContent =  $scope.formDisclaimer;

   var updateDisclaimerContent = $scope.formDisclaimer.disclaimercontent.splice(index, 1);
    $http({
        method: "POST",
        url: "survey/updatejson/disclaimerpatient",
        headers: {'Content-Type': 'application/json'},
        data:  $scope.formDisclaimer
    })
        .then(function (response) {
            $scope.ResponseMessage = "Disclaimer page updated successfully";
        })

}

        $scope.deleteDisContentCarer = function(items, index)

        {
            var disclaimerContent =  $scope.formDisclaimerCarer;

            //var result = $filter('filter')(disclaimerContent.disclaimercontent, {id: });
            var updateDisclaimerContent = $scope.formDisclaimerCarer.disclaimercontent.splice(index, 1);
            $http({
                method: "POST",
                url: "survey/updatejson/disclaimercarer",
                headers: {'Content-Type': 'application/json'},
                data:  $scope.formDisclaimerCarer
            })
                .then(function (response) {
                    $scope.ResponseMessage = "Disclaimer page updated successfully";
                })



        }


        $scope.showEditContent= function(usertype)
        {

            if(usertype=='Patient') {
                $scope.showEditContentpatient = true;
                var disclaimerContent = $scope.formDisclaimer;
                //var result = $filter('filter')(disclaimerContent.disclaimercontent, {id: });
                var updateDisclaimerContent = $scope.formDisclaimer.disclaimercontent.splice(0, 100);
                $http({
                    method: "POST",
                    url: "survey/updatejson/disclaimerpatient",
                    headers: {'Content-Type': 'application/json'},
                    data: $scope.formDisclaimer
                })
                    .then(function (response) {
                        $scope.ResponseMessage = "Disclaimer page updated successfully";
                        $scope.formDisclaimer.disclaimercontent.push({});

                    })

            }
            else {

                $scope.showEditContentcarer = true;
                var disclaimerContent = $scope.formDisclaimerCarer;
                //var result = $filter('filter')(disclaimerContent.disclaimercontent, {id: });
                var updateDisclaimerContent = $scope.formDisclaimerCarer.disclaimercontent.splice(0, 100);
                $http({
                    method: "POST",
                    url: "survey/updatejson/disclaimercarer",
                    headers: {'Content-Type': 'application/json'},
                    data: $scope.formDisclaimer
                })
                    .then(function (response) {
                        $scope.ResponseMessage = "Disclaimer page updated successfully";
                        $scope.formDisclaimerCarer.disclaimercontent.push({});

                    })

            }




        }

        $scope.addLine = function()
        {
            $scope.formDisclaimer.disclaimercontent.push({});

        }


        $scope.saveNewDisclaimer = function(usertype) {
            if (usertype == 'Patient') {

                $http({
                    method: "POST",
                    url: "survey/updatejson/disclaimerpatient",
                    headers: {'Content-Type': 'application/json'},
                    data: $scope.formDisclaimer
                })
                    .then(function (response) {
                        $scope.ResponseMessage = "Disclaimer page updated successfully";
                    })


            }

            else if(usertype == 'Carer') {

                $http({
                    method: "POST",
                    url: "survey/updatejson/disclaimercarer",
                    headers: {'Content-Type': 'application/json'},
                    data: $scope.formDisclaimerCarer
                })
                    .then(function (response) {
                        $scope.ResponseMessage = "Disclaimer page updated successfully";
                    })


            }

        }
        $scope.updateemailtemp = function()
        {
            $http({
                method: "POST",
                url: "survey/updatejson/emailtemp",
                headers: {'Content-Type': 'application/json'},
                data: $scope.formEmailTemp
            })
                .then(function (response) {
                    $scope.ResponseMessage = "eMail template updated successfully";
                    $scope.editorEnabledEmail=!$scope.editorEnabledEmail;
                })

        }


        $scope.publishsite = function()

        {
            $http({
                method: "GET",
                url: "survey/publish"

            })
                .then(function (response) {
                })


        }

        $scope.disableButton = function() {
            $scope.isDisabled = false;

            $window.open($scope.redirectToDev,'_blank');
        }




    }]);