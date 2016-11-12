/**
 * Created by 535222 on 1/2/2016.
 */


/**
 *
 * TO-DO :    Abstract Profile page related content to different JS
 *            Remove the reference of Gmap long , lat and make it configurable
 *
 *
 *
 *
 */

angular.module('formApp', ['ui.router', 'vcRecaptcha', 'rzModule', 'ui.bootstrap', 'ngDialog', 'ngCookies'])

    // configuring our routes
    // =============================================================================
    .config(function ($stateProvider, $urlRouterProvider) {
        $stateProvider

            .state('form', {
                url: '/form',
                templateUrl: 'form.html',
                controller: 'formController'
            })
            .state('home', {
                url: '/form/research:devid',
                controller: 'homepagecontroller',
                templateUrl: 'views/home.html'
            })
            .state('form.profile', {
                url: '/research:devid',
                controller: 'personalnfoContrler',
                templateUrl: 'views/form-profile.html',
                reloadOnSearch: false
            })

            .state('form.question', {
                url: '/research:devid',
                controller: 'pagecontroller',
                templateUrl: 'views/questionarie.html'
            })

            .state('form.thankyou', {
                url: '/research:devid',
                templateUrl: 'views/confirmation.html'
            })
            .state('generror', {
                url: '/error',
                templateUrl: 'error/error.html'
            })

            .state('form.401', {
                url: '/accessdenied',
                templateUrl: 'views/401_error.html'
            })

        $urlRouterProvider.otherwise('/form/research');
    })

    .directive('modal', function () {
        return {
            template: '<div class="modal fade">' +
            '<div class="modal-dialog">' +
            '<div class="modal-content">' +
            '<div class="modal-header">' +
            '<button type="button" style="    border: none;width: 30px;" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
            '<a href="" ng-show="showPrintImage" class="printbutton" style="float: right;padding: 0px;margin-top: 2%; margin-right:5%;" ng-click="printDiv()"><img style="max-width: 70%;" src="images/print_icon.png" alt=""></a>' + '<h4 class="modal-title" style="color:grey;">{{ title }}</h4>' +
            '</div>' +
            '<div class="modal-body" ng-transclude></div>' +
            '</div>' +
            '<div class="modal-footer">' +
            '</div>' +
            '</div>',
            restrict: 'E',
            transclude: true,
            replace: true,
            scope: true,
            link: function postLink(scope, element, attrs) {
                scope.title = attrs.title;

                scope.$watch(attrs.visible, function (value) {

                    if (value == true)
                        $(element).modal('show');
                    else
                        $(element).modal('hide');
                });

                $(element).on('shown.bs.modal', function () {
                    scope.$apply(function () {
                        scope.$parent[attrs.visible] = true;
                    });
                });

                $(element).on('hidden.bs.modal', function () {
                    scope.$apply(function () {
                        scope.$parent[attrs.visible] = false;
                    });
                });
            }
        };
    })
    .directive('ngConfirmClick', [
        function () {
            return {
                link: function (scope, element, attr) {
                    var msg = attr.ngConfirmClick || "Are you sure?";
                    var clickAction = attr.confirmedClick;
                    element.bind('click', function (event) {
                        if (window.confirm(msg)) {
                            scope.$eval(clickAction)
                        }
                    });
                }
            };
        }])
    .controller('formController', function ($scope, $rootScope, dataService, $location, $anchorScroll, $http, $stateParams) {
        var pagecontent = 'questioncontent';

        var devid = $stateParams.devid;


        $scope.$watch('$viewContentLoaded', function () {
            $http({
                url: 'survey/gethkpagecontent/' + pagecontent,
                method: "GET"
            })
                .then(function (response) {
                    var responseData = angular.fromJson(response.data);
                    $scope.quespagelength = responseData;
                    $scope.quespagelen = (responseData.length) + 1;
                    $scope.pagelength = $scope.quespagelength.length;
                })


        });

        $scope.scrollTo = function (id) {
            $location.hash(id);
            $anchorScroll();
            $location.hash('');

        }
        $scope.formData = {};
        $scope.submit = function () {

        }
    })

    .directive('ngHover', function () {
        return {
            link: function (scope, element) {
                element.bind('mouseenter', function () {
                    angular.element(
                        element.children()[0]).addClass('redclass')
                })
            }
        }
    })

    .service('dataService', function () {

        var _dataObj;
        var _surveyPageid;
        var _devid;
        this.dataObj = _dataObj;
        this.surveyPageid = _surveyPageid;
        this.devid = _devid;

    })

    .controller('homepagecontroller', function ($scope, $http, dataService, $state, $location, $anchorScroll, $stateParams, $cookies, $window, $timeout) {
        var json = {};
        $scope.errorDisplay = false;
        var sessionID = $cookies['myHeartKidID'];
        var sessionID = sessionID ? sessionID.replace(/"/g, '') : '';
        var pagecontent = 'homepagecontent';
        $scope.development = false;
        var devid = $stateParams.devid;
        dataService.devid = devid;
        var unsubsdevID = devid.split("_");
        $scope.showPrintImage = true;


        $scope.$watch('$viewContentLoaded', function () {

            if (devid == 'dev' + sessionID) {

                $scope.development = true;

                $http({
                    url: 'survey/gethkpagecontentdev/' + pagecontent,
                    method: "GET"
                })
                    .then(function (response) {
                        var responseData = angular.fromJson(response.data);
                        $scope.HK_CHD_Content = responseData;
                    })


                $http({
                    url: 'survey/gethkpagecontentdev/disclaimerpatient',
                    method: "GET"
                })
                    .then(function (response) {
                        var responseDataPatient = angular.fromJson(response.data);
                        $scope.disclaimerCont = responseDataPatient;
                    })

                $http({
                    url: 'survey/gethkpagecontentdev/disclaimercarer',
                    method: "GET"
                })
                    .then(function (response) {
                        var responseDatacarer = angular.fromJson(response.data);
                        $scope.disclaimerContCarer = responseDatacarer;
                    })
            }


            else if (devid == '' || unsubsdevID[0] == 'unsubscribe') {

                $http({
                    url: 'survey/gethkpagecontent/' + pagecontent,
                    method: "GET"
                })
                    .then(function (response) {
                        var responseData = angular.fromJson(response.data);
                        $scope.HK_CHD_Content = responseData;
                    })

                $http({
                    url: 'survey/gethkpagecontent/disclaimerpatient',
                    method: "GET"
                })
                    .then(function (response) {
                        var responseDataPatient = angular.fromJson(response.data);
                        $scope.disclaimerCont = responseDataPatient;
                    })

                $http({
                    url: 'survey/gethkpagecontent/disclaimercarer',
                    method: "GET"
                })
                    .then(function (response) {
                        var responseDatacarer = angular.fromJson(response.data);
                        $scope.disclaimerContCarer = responseDatacarer;
                    })


            }

            else {
                $state.go('form.401');
            }
        });

        $scope.scrollTo = function (id) {
            $location.hash(id);
            $anchorScroll();
            $location.hash('');

        }

  $scope.UnsubscribeSectionText = 'true';
        $scope.UnsubscribeSectionResp = 'false';
        $scope.UnsubscribeSectionRespNotFound = 'false';

        if(unsubsdevID[0] == "unsubscribe")
        {
            $scope.showPrintImage = false;
            $scope.unsubsReferenceNo = unsubsdevID[1];
            //unsubscribeOpenId

            //$scope.showUnsubscribe = 'true';
            // $scope.unSubscribeUserDetails();
           // $scope.showUnsubscribe = true;

            //$scope.unsubscribeClick();

            $timeout(function() {
                $scope.showUnsubscribe = true;
            })
            $timeout(function() {
                $scope.unsubscribeClick($scope.unsubsReferenceNo);

            })

        }

        $scope.unSubscribeUserDetails = function () {
            $scope.showUnsubscribe = !$scope.showUnsubscribe;
            $scope.UnsubscribeSectionText = 'true';
            $scope.UnsubscribeSectionResp = 'false';
            $scope.UnsubscribeSectionRespNotFound = 'false';
            $scope.showPrintImage = false;
        };


        $scope.unsubscribeClick = function(refno)
        {
            $http({
                url: 'survey/unsubscribe/'+refno,
                method: "GET"
            })
                .then(function (response) {
                    $scope.UnsubscribeSectionText = 'false';
                    console.log("2222  "+response.data);

                    if(response.data == "RECORDNOTFOUND")
                    {
                        $scope.UnsubscribeSectionRespNotFound = 'true';

                    }
                    else {
                        $scope.UnsubscribeSectionResp = 'true';
                    }
                })

        }
$scope.backToHome = function () {
    $scope.showUnsubscribe = false;

    $('.modal-backdrop').remove();

    $state.go('form.profile');


}
        $scope.printDiv = function () {
            var printContents = document.getElementById('printconsenform').innerHTML;
            var popupWin = window.open('', '_blank', 'width=300,height=300');
            popupWin.document.open();
            popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /></head><body onload="window.print()">' + printContents + '</html>');
            popupWin.document.close();
        }


        $scope.showModal = false;
        $scope.showCModal = false;
        $scope.showThankModal = false;
        $scope.showUnsubscribe = false;

        $scope.disclaimerCheck = function (usertype) {
            $scope.agreeconsentpatient = 'false';
            $scope.agreeconsentreqpatient = 'false';
            $scope.usertype = usertype;
            if (usertype == "Patient") {

                $scope.showModal = !$scope.showModal;
            }
            else {

                $scope.showCModal = !$scope.showCModal;
            }
        };


        $scope.openNavigator = function () {
            var geoString = 'https://maps.google.com/?q=-33.825286, 151.200206';
            window.open(geoString, '_system');
        }

        function isNumber(n) {
            return !isNaN(parseFloat(n)) && isFinite(n);
        }

        sessionStorage.clear();
        $scope.$watch('$viewContentLoaded', function () {
            $http({
                url: 'survey/regcount',
                method: "GET"
            })
                .then(function (response) {
                        var data = $.parseJSON(angular.toJson(response.data));
                        var value = isNumber(data);
                        if (value == false) {
                            $state.go('generror');
                        }
                        else {
                            $scope.regcount = data;
                        }
                    },
                    function (response) {
                        $state.go('generror');
                    })

        });

        $scope.publishsite = function () {

            $http({
                method: "GET",
                url: "survey/publish"

            })
                .then(function (response) {
                    $window.location.href = 'index.html#/form/research';
                    $scope.ResponseMessagePublish = "Site Updated Successfully";
                })


        }

        $scope.proceedtopatientsurvey = function (usertype, agreeconsentpatient, agreeconsentreqpatient) {
            $scope.formData = {};
            $scope.showPrintImage = true;

            var devid = $stateParams.devid;
            if (agreeconsentpatient == true && agreeconsentreqpatient == true) {
                $scope.agreeconsentpatientreq = '';
                // localStorage.setItem('usertype', usertype);
                $scope.formData.usertype = usertype;

                dataService.usertype = usertype;
                $scope.showModal = false;
                $scope.showCModal = false;
                $('.modal-backdrop').remove();


                $http({
                    url: 'survey/referencegen',
                    method: "GET"
                })
                    .then(function (response) {
                            var data = $.parseJSON(angular.toJson(response.data));
                            dataService.dataObj = data;
                            $(window).scrollTop(100);

                            if (devid == "dev" + sessionID) {


                                $state.go('form.profile', {devid: "dev" + sessionID});
                            }
                            else {
                                $state.go('form.profile');

                            }
                        },
                        function (response) {
                            $state.go('generror');

                        })
            }
            else {
                if (agreeconsentpatient == false) {
                    $scope.agreeconsentpatientreq = "You’re required to check the “I consent agree with above statements” checkbox before proceeding to the survey";
                }
                else {
                    $scope.agreeconsentpatientreq = "You’re required to check the “I am over age of 18” checkbox before proceeding to the survey";
                }
            }


        }
    })

    .controller('personalnfoContrler', ['$scope', '$http', 'dataService', '$state', '$rootScope', '$anchorScroll', '$location', 'ngDialog', '$stateParams', '$cookies', function ($scope, $http, dataService, $state, $rootScope, $anchorScroll, $location, ngDialog, $stateParams, $cookies) {
        var devid = $stateParams.devid;
        var sessionID = $cookies['myHeartKidID'];
        var sessionID = sessionID ? sessionID.replace(/"/g, '') : '';

        var pagecontent = 'personalcontent';
        $scope.showPrintImage = true;

        $scope.survypageid = "1";

        $scope.$watch('$viewContentLoaded', function () {
            $http({
                url: 'survey/gethkpagecontent/' + pagecontent,
                method: "GET"
            })
                .then(function (response) {
                    var responseData = angular.fromJson(response.data);
                    $scope.HK_CHD_Content = responseData;
                })


        });
        /* // var usertype = sessionStorage.getItem('usertype');

         */
        var usertype = dataService.usertype;

        if (usertype == 'Patient') {
            $scope.showcarer = 'false';
        }
        else {
            $scope.showcarer = 'true';
        }
        $scope.formData.usertype = usertype;
        $scope.formData.refno = dataService.dataObj;


        var progress = setInterval(function () {
            var $bar = $('.bar');
            if ($bar.width() >= 400) {
                clearInterval(progress);
                $('.progress').removeClass('active');
            } else {
                $bar.width($bar.width() + 40);
            }
            $bar.text($bar.width() / 4 + "%");
        }, 800);

        $scope.yesnoArray = ["Yes", "No"];
        $scope.usertypeArray = ["Patient", "Carer"];
        $scope.titleArray = ["Mr", "Mrs", "Miss", "Ms", "Dr", "Prof", "Lady"];
        $scope.sexArray = ["Male", "Female"];
        $scope.conttypeArray = ["Phone", "Email"];
        $scope.ethnicityArray = ["Caucasian", "Aborigional / Tores Strait Island", "Mouri", "Asian", "Indian", "Black/Afican American", "European", "Other"];
        $scope.lstofcountryArray = ["Australia", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua & Deps", "Argentina", "Armenia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Rep", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Congo {Democratic Rep}", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland {Republic}", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea North", "Korea South", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar, {Burma}", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russian Federation", "Rwanda", "St Kitts & Nevis", "St Lucia", "Saint Vincent & the Grenadines", "Samoa", "San Marino", "Sao Tome & Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad & Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican", "Other"];
        $scope.languageArray = ["English", "Chinese", "Italian", "Vietnamese", "Greek", "Cantonese", "Arabic", "Mandarin", "Macedonian", "French", "Spanish", "Other"];
        $scope.statelistArray = ["New South Wales", "Australian Capital Territory", "Victoria", "Queensland", "South Australia", "Western Australia", "Tasmania", "Northern Territory", "Other"];
        $scope.showcontactvia = 'true';


        $scope.chckAgeclac = function (birthday) {
            var dob = new Date(birthday);
            var today = new Date();
            var agevalue = 0;
            agevalue = today.getMonth() - dob.getMonth() + (12 * (today.getFullYear() - dob.getFullYear()));
            var ageval = (agevalue / 12).toFixed(0);

            return ageval;

        }

        $scope.chckAgeclacCarer = function (birthday) {
            var dob = new Date(birthday);
            var today = new Date();
            var agevalue = 0;
            agevalue = today.getMonth() - dob.getMonth() + (12 * (today.getFullYear() - dob.getFullYear()));
            var ageval = (agevalue / 12).toFixed(0);

            return ageval;

        }
        $scope.calculateAge = function calculateAge(birthday) { // birthday is a date

            var dob = new Date(birthday);
            var today = new Date();
            var agevalue = 0;
            agevalue = today.getMonth() - dob.getMonth() + (12 * (today.getFullYear() - dob.getFullYear()));
            if (isNaN(agevalue)) {
                $scope.formData.age = "0";
            }
            else {
                $scope.ageval = (agevalue / 12).toFixed(0);
                $scope.formData.age = (agevalue / 12).toFixed(0);
            }

        };

        $scope.opencal = function ($event) {

            $event.preventDefault();
            $event.stopPropagation();
            $scope.opened = true;
        };
        $scope.contctviaphone = function () {
            var selectd = $scope.formData.contctviaphone;

            if ((selectd == 'yes')) {

                $scope.showcontactviaphone = 'true';
            }
            else {
                $scope.showcontactviaphone = 'false';
                $scope.userForm.phone.$error.required = 'false';
            }
        }
        $scope.checkAge = function (birthdate) {
            $scope.agecheckMessage = "";
            var agevaluecheck = $scope.chckAgeclac(birthdate);
            var usertype = $scope.formData.usertype;
            if (agevaluecheck < 18 && usertype == 'Patient') {
                $scope.agecheckMessage = "you are no 18 years of age ! Please go back to home";
                ngDialog.open({
                    template: 'views/ageconfirmation.html',
                    backdrop: 'static',
                    closeByDocument: true,
                    closeByEscape: true
                })

            }
        }


        $scope.checkAgeCarer = function (birthdate) {
            $scope.agecheckMessage = "";
            var agevaluecheck = $scope.chckAgeclac(birthdate);
            var usertype = $scope.formData.usertype;
            if (agevaluecheck < 18 && usertype == 'Carer') {
                ngDialog.open({
                    template: 'views/ageconfirmation.html',
                    backdrop: 'static',
                    closeByDocument: true,
                    closeByEscape: true
                })

            }


        }
        $scope.personalInfoSubmit = function (e, page) {

            var devid = $stateParams.devid;

            if ($scope.formData.age < 18 && $scope.formData.usertype == 'Patient') {
                ngDialog.open({
                    template: 'views/ageconfirmation.html',
                    backdrop: 'static',
                    closeByDocument: true,
                    closeByEscape: true
                })

            }
            else {

                var formstatus = "incomplete";
                var usertype = $scope.formData.usertype;
                $scope.formData.surveystatus = formstatus;
                if (devid == 'dev' + sessionID) {
                    $state.go('form.question', {devid: "dev" + sessionID});
                }
                else {
                    $http({
                        url: 'survey/personalinfo/' + formstatus + '/' + usertype,
                        method: "POST",
                        data: $scope.formData

                    })
                        .then(function (response) {
                                var data = $.parseJSON(angular.toJson(response.data));

                                $state.go('form.question');

                                $scope.formData.id = data.id;
                                sessionStorage.setItem("refno", data.refno);
                                $(window).scrollTop(0);
                            },
                            function (status) {
                                $state.go('generror');

                            })
                }
            }
        }
    }])

    .controller('pagecontroller', function ($scope, $http, dataService, $state, $anchorScroll, $location, $cookies, vcRecaptchaService, $sce, $filter, $stateParams, $rootScope) {

        var sessionID = $cookies['myHeartKidID'];
        var sessionID = sessionID ? sessionID.replace(/"/g, '') : '';
        $scope.showThankModal = false;
        $scope.formData1 = {};
        var devid = $stateParams.devid;
        $scope.$watch('$viewContentLoaded', function () {
            var pagecontent = 'questioncontent';
            if (devid == 'dev' + sessionID) {
                $http({
                    url: 'survey/gethkpagecontentdev/' + pagecontent,
                    method: "GET"
                })
                    .then(function (response) {

                        $scope.responsedata = response.data;
                        $scope.pagelength = $scope.responsedata.length;
                        if ($scope.pagelength > 0) {
                            $scope.HK_CHD_Content = $scope.responsedata[0];
                        }
                        else {
                            $scope.HK_CHD_Content = response.data;
                        }
                        //    $scope.survypageid =pageid;


                    })

                $http({
                    url: 'survey/gethkpagecontentdev/thankyou',
                    method: "GET"
                })
                    .then(function (response) {
                        var responseData = angular.fromJson(response.data);
                        $scope.thankyoupagecontent = responseData;
                        //   console.log(angular.toJson(responseData.thankyoupopup[0]));
                    })
            }

            else {
                $http({
                    url: 'survey/gethkpagecontent/' + pagecontent,
                    method: "GET"
                })
                    .then(function (response) {

                        $scope.responsedata = response.data;
                        $scope.pagelength = $scope.responsedata.length;
                        if ($scope.pagelength > 0) {
                            $scope.HK_CHD_Content = $scope.responsedata[0];
                        }
                        else {
                            $scope.HK_CHD_Content = response.data;
                        }
                        //    $scope.survypageid =pageid;


                    })

                $http({
                    url: 'survey/gethkpagecontent/thankyou',
                    method: "GET"
                })
                    .then(function (response) {
                        var responseData = angular.fromJson(response.data);
                        $scope.thankyoupagecontent = responseData;
                        //   console.log(angular.toJson(responseData.thankyoupopup[0]));
                    })

            }
        })


        $scope.gotopreviouspage = function (pageid) {
            var previouspageid = pageid - 2;
            if (previouspageid == '-1') {
                if (devid == 'dev' + sessionID) {
                    $state.go('form.profile', {devid: "dev" + sessionID});
                }
                else {
                    $state.go('form.profile');
                }
            }
            else (pageid <= $scope.pagelength)
            {
                $http({
                    url: 'survey/getprevrecord/' + $scope.formData.refno,
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(function (response) {
                        var data = angular.fromJson(response.data);
                        angular.forEach(data[0].questionaire, function (value, key) {
                            console.log(key['question_id'] + ': ' + value['question_id'] + '::::' + value['id']);
                            var question_id = value['question_id'];
                            $scope.formData1[question_id] = value['answer'];

                        })
                       $scope.HK_CHD_Content = $scope.responsedata[previouspageid];
                        $(window).scrollTop(0);

                    })

            }
        }

        $scope.validationcheck = function (patternarray, valuetotest, titleval) {
            angular.element('#errorcontainer_' + titleval).html("");
            var errormsglist = "";
            var userformval = 'text_'+titleval;
            angular.forEach(patternarray, function (value, key) {
                var _regex = new RegExp(value.regex);

                if (!_regex.test(valuetotest)) {
                    $scope.errorDisplay = true;
                    errormsglist = "<span class='erroritem'> " + value.message + "</span><br>" + errormsglist;
                    $scope.errorMessageExists = "invalid";
                }
                else {
                    $scope.errorDisplay = false;
                    $scope.errorMessageExists = "valid";

                }
            });
            angular.element('#errorcontainer_'+titleval).html(errormsglist);
        }


        $scope.listener = function (itemval, titleval, type) {
            var depCounter = 0;
            var elementsArray;
            elementsArray = $scope.HK_CHD_Content.questionnaire;
            var result = $filter('filter')(elementsArray, {title: titleval})[0];
            if (type == 'text' || type == 'number') {
                $scope.validationcheck(result.pattern, itemval, titleval);
            }
            for (depCounter = 0; depCounter < result.dependency.length; depCounter++) {
                if (itemval == result.dependency[depCounter].value) {
                    var resultdep = $filter('filter')(elementsArray, {title: result.dependency[depCounter].dependentid})[0];

                    if (result.dependency[depCounter].action == 'show') {

                        resultdep.showques = 'true';
                    }
                    else if (result.dependency[depCounter].action == 'hide') {

                        resultdep.showques = 'false';
                    }

                }
                else {

                    if (type == 'radio' || type == 'select' || type == 'tel' || type == 'number' || type == 'barscale') {
                        var resultdep = $filter('filter')(elementsArray, {title: result.dependency[depCounter].dependentid})[0];


                        if (itemval != result.dependency[depCounter].value) {

                            var resultdep = $filter('filter')(elementsArray, {title: result.dependency[depCounter].dependentid})[0];
                            resultdep.showques = 'false';

                        }
                    }

                }
            }
        }

        $scope.siblingchd = {


            value: 0,
            options: {
                onEnd: function () {
                    $scope.formData1.siblingchldimpct = $scope.siblingchd.value;
                },
                floor: 0,
                ceil: 5,
                step: 1,
                showSelectionBar: true
            }
        };

        $scope.hospsurgexp = {
            value: 0,
            options: {
                onEnd: function () {
                    $scope.formData1.frstsurgerysel = $scope.hospsurgexp.value;
                },
                floor: 0,
                ceil: 5,
                step: 1,
                showSelectionBar: true
            }
        };
        $scope.relationimpactval = {
            value: 0,
            options: {
                onEnd: function () {
                    $scope.formData1.relationimpact = $scope.relationimpactval.value;
                },
                floor: 0,
                ceil: 5,
                showSelectionBar: true
            }
        };

        $scope.otherData = 0;


        $scope.translate = function (value) {
            return '$' + value;
        }

        $scope.onSliderChange = function () {
        }
        $scope.moneyspent = {
            value: 0,
            options: {
                onEnd: function () {
                    $scope.formData1.moneyspentinyear = $scope.moneyspent.value;
                },
                showSelectionBar: true,
                ceil: 10000,
                floor: 0,
                step: 100,
                translate: function (value) {
                    return '$' + value;
                }

            }

        };

        $scope.moneyspentuptosurg = {
            value: 0,
            options: {
                onEnd: function () {
                    $scope.formData1.moneyspentuptosurgery = $scope.moneyspentuptosurg.value;
                },
                showSelectionBar: true,
                ceil: 10000,
                floor: 0,
                step: 100,
                translate: function (value) {
                    return '$' + value;
                }

            }

        };


        $scope.moneyspentaftersurg = {
            value: 0,
            options: {
                onEnd: function () {
                    $scope.formData1.moneyspentaftersurgery = $scope.moneyspentaftersurg.value;
                },
                showSelectionBar: true,
                ceil: 10000,
                floor: 0,
                step: 100,
                translate: function (value) {
                    return '$' + value;
                }

            }

        };

        /*  if($scope.formData1.frstsurgerysel != null)
         {
         $scope.hospsurgexp.value=$scope.formData1.frstsurgerysel;
         }
         if($scope.formData1.aftrsurgfeel != null)
         {
         $scope.supporthome.value=$scope.formData1.aftrsurgfeel;
         }*/
        if ($scope.formData1.moneyspentinyear != null) {
            $scope.moneyspent.value = $scope.formData1.moneyspentinyear;
        }
        if ($scope.formData1.moneyspentuptosurgery != null) {
            $scope.moneyspentuptosurg.value = $scope.formData1.moneyspentinyear;
        }

        if ($scope.formData1.moneyspentaftersurgery != null) {
            $scope.moneyspentaftersurg.value = $scope.formData1.moneyspentinyear;
        }
        $scope.formData.publickey = "6LeSsBYTAAAAAGmU-jTN8i-90SWEZCyFyhQdQokz";
        $scope.response = null;
        $scope.widgetId = null;
        $scope.model = {
            key: '6LeSsBYTAAAAAGmU-jTN8i-90SWEZCyFyhQdQokz'
        };
        $scope.setResponse = function (response) {
            console.info('Response available' + response);
            $scope.response = response;
        };
        $scope.setWidgetId = function (widgetId) {
            console.info('Created widget ID: %s', widgetId);
            $scope.widgetId = widgetId;
        };
        $scope.cbExpiration = function () {
            console.info('Captcha expired. Resetting response object');
            $scope.response = null;
        }


        $scope.captchavalidate = function () {
            if (vcRecaptchaService.getResponse() === "") { //if string is empty
                alert("Please resolve the captcha and submit!")
            } else {
                alert("captcha vaidate successfukr;");
            }
        }

        //var usertype = sessionStorage.getItem('usertype');
        var usertype = $scope.formData.usertype;

        if (usertype == 'Patient') {
            $scope.showcarer = 'false';
        }
        else {
            $scope.showcarer = 'true';
        }

        $scope.formData.usertype = usertype;
        var conditioncalldelectd = $scope.formData.conditioncalld;
        $scope.usernameval = $scope.formData.firstname + " " + $scope.formData.lastname;
        $scope.referencenumberval = $scope.formData.refno;
        $scope.showcarerdetails = 'false';


        $scope.scale1to5 = ["0", "1", "2", "3", "4", "5"];
        $scope.scale1to10 = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", ">10"];
        $scope.scale1to20 = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", ">20"];
        $scope.yesnoArray = ["Yes", "No"];
        $scope.rating = 5;
        $scope.costinvolvedArray = ["< 100", "200 � 300", "400 � 500", "> 500"];
        $scope.heartcondsArray = ["Atrial septal defect", "Ventricular septal defect", "Atrioventricular septal defect", "Patent ductus arteriosus", "Pulmonary vein anomaly", "Tricuspid atresia", "Ebstein’s anomaly ", "Dysplastic tricuspid valve", "Pulmonary stenosis", "Pulmonary atresia", "Tetralogy of Fallot", "Abnormal mitral valve", "Aortic stenosis", "Coarctation of the aorta or interrupted aorta", "Truncus arteriosus", "Bicuspid aortic valve", "Hypoplastic left heart syndrome", "Transposition of the great arteries", "Congenitally corrected transposition of the great arteries", "Double-outlet right ventricle", "Double-inlet left ventricle", "Double-inlet right ventricle", "Atrial isomerism (left or right)", "None of the above"];

        $scope.myClass = [];


        $scope.onclickbarscale = function (node, item) {
            $scope.idSelectedNode = null;
            $scope.formData1[item] = node;
            $scope.idSelectedNode = node;

            $scope.listener(node, item, 'barscale');

        }
        $scope.savesurveyregistration = function (pageid) {
            if ($scope.errorMessageExists == 'invalid') {
                $scope.errorMessageNumber = "Please enter valid number";
            }
            else {
                $scope.errorMessageNumber = '';
                if (pageid < $scope.pagelength)
                    $scope.HK_CHD_Content = $scope.responsedata[pageid];
                var formstatus = "incomplete";
                var usertype = $scope.formData.usertype;
                var refnovalue = sessionStorage.getItem("refno");
                var formatjson;
                $scope.pagenumberid = pageid;
                $scope.formData.status = formstatus;
                var jsonval = $scope.formData1;
                var refno = '"refno":"' + refnovalue + '","QuestionEntity":';
                var formarray = [];
                angular.forEach(jsonval, function (value, key) {
                    formatjson = {

                        "question_id": key,
                        "answer": value,
                        "section_id": pageid
                    }
                    formarray.push(formatjson);
                });
                var jsonrequest = angular.toJson(formarray);
                var questionariearray = '{' + refno + jsonrequest + '}';

                if (devid != "dev" + sessionID) {
                    $http({
                        url: 'survey/newjsonrecord',
                        method: "POST",
                        data: questionariearray
                    })
                        .then(function (response) {
                                $(window).scrollTop(0);
                                $scope.formData1 = null;
                                for (var key in $scope.formData1) {
                                    delete $scope.formData1[key];
                                }

                                $scope.formData1 = {};

                            },
                            function (response) { // optional
                                $state.go('generror');
                            })

                }


                /* $http({
                 url: 'survey/getprevrecord/'+$scope.formData.refno,
                 method: "GET",
                 headers: {
                 'Content-Type': 'application/json'
                 }
                 })

                 .then(function (response) {
                 var data = angular.fromJson(response.data);
                 $scope.formData1 = data[0];

                 var questionarieFormat = "[{" +$scope.formData1.questionaire + "}]";
                 var questionarie = angular.fromJson(questionarieFormat);


                 $scope.formData1 = questionarie[0];

                 })*/

                if ($scope.formData1.surgerydelaycount != null) {
                    var node = $scope.formData1.surgerydelaycount;
                    var nodeclass = $scope.formData1.surgerydelaycount;
                    $scope.SurgerydelaycountClass = function (nodeclass) {
                        return {
                            bluebarrating: node === nodeclass
                        }
                    }

                }

                if ($scope.formData1.aftrsurgfeel != null) {
                    var node2 = $scope.formData1.aftrsurgfeel;
                    var nodeclass2 = $scope.formData1.aftrsurgfeel;
                    $scope.getClassaftrsurgfeel = function (nodeclass2) {
                        return {
                            bluebarrating: node2 === nodeclass2
                        }
                    }

                }
                if ($scope.formData1.chdimpactwork != null) {
                    var chdimwrk = $scope.formData1.chdimpactwork;
                    var chdimwrkC = $scope.formData1.chdimpactwork;
                    $scope.chdimpactworkClass = function (chdimwrkC) {
                        return {
                            bluebarrating: chdimwrk === chdimwrkC
                        }
                    }

                }


                if ($scope.formData1.siblingchld != null) {
                    var sibchld = $scope.formData1.siblingchld;
                    var sibchldC = $scope.formData1.siblingchld;
                    $scope.getClasssiblicksel = function (sibchldC) {
                        return {
                            bluebarrating: sibchld === sibchldC
                        }
                    }

                }
                if ($scope.formData1.siblingchldimpct != null) {
                    var sci = $scope.formData1.siblingchldimpct;
                    var SciC = $scope.formData1.siblingchldimpct;
                    $scope.getClasssiblickselCHDimpct = function (SciC) {
                        return {
                            bluebarrating: sci === SciC
                        }
                    }

                }

                if ($scope.formData1.hosptlsurgery != null) {
                    var hs = $scope.formData1.hosptlsurgery;
                    var hsc = $scope.formData1.hosptlsurgery;
                    $scope.getClasshosptlsurgery = function (hsc) {
                        return {
                            bluebarrating: hs === hsc
                        }
                    }

                }


                if ($scope.formData1.doctorcountsee != null) {
                    var node1 = $scope.formData1.doctorcountsee;
                    var nc;
                    for (nc = 0; nc < 5; nc++) {
                        $scope.getClassdoctorcountsee = function (nc) {
                            return {
                                bluebarrating: node1 == nc
                            }
                        }
                    }

                }

                if ($scope.formData1.traveldistdoc != null) {
                    var travdist = $scope.formData1.traveldistdoc;
                    var travdistclass = $scope.formData1.traveldistdoc;
                    $scope.getClasstraveldistdoc = function (travdistclass) {
                        return {
                            bluebarrating: travdist === travdistclass
                        }
                    }

                }


                if ($scope.formData1.feelsupport != null) {
                    var nodesupport = $scope.formData1.feelsupport;
                    var nodeclasssupport = $scope.formData1.feelsupport;
                    $scope.getClassfeelsupport = function (nodeclasssupport) {
                        return {
                            bluebarrating: nodesupport === nodeclasssupport
                        }
                    }

                }

                if ($scope.formData1.frstsurgerysel != null) {
                    var nodefrstsurgery = $scope.formData1.frstsurgerysel;
                    var nodefrstsurgeryClass = $scope.formData1.frstsurgerysel;
                    $scope.getClassfrstsurgerysel = function (nodefrstsurgeryClass) {
                        return {
                            bluebarrating: nodefrstsurgery === nodefrstsurgeryClass
                        }
                    }

                }

                if ($scope.formData1.relationimpact != null) {
                    var noderelimpct = $scope.formData1.relationimpact;
                    var noderelimpctClass = $scope.formData1.relationimpact;
                    $scope.getClassrelimpact = function (noderelimpctClass) {
                        return {
                            bluebarrating: noderelimpct === noderelimpctClass
                        }
                    }

                }

                if ($scope.formData1.siblingchldimpct != null) {
                    var nodesibimpct = $scope.formData1.siblingchldimpct;
                    var nodesibimpctClass = $scope.formData1.siblingchldimpct;
                    $scope.getClassrelimpact = function (nodesibimpctClass) {
                        return {
                            bluebarrating: nodesibimpct === nodesibimpctClass
                        }
                    }

                }


                if ($scope.formData1.ratetransition != null) {
                    var ratetransnode = $scope.formData1.ratetransition;
                    var ratetransnodeClass = $scope.formData1.ratetransition;
                    $scope.getClassratetransition = function (ratetransnodeClass) {
                        return {
                            bluebarrating: ratetransnode === ratetransnodeClass
                        }
                    }

                }
            }
        }


        $scope.returntohome = function () {

            $scope.showThankModal = false;
        }




        $scope.submitcompletedform = function (pageid) {
            $scope.HK_CHD_Content = $scope.responsedata[pageid];
            $scope.showThankModal = !$scope.showThankModal;

            var formstatus = "success";
            var usertype = $scope.formData.usertype;
            var refnovalue = sessionStorage.getItem("refno");
            var formatjson;
            $scope.pagenumberid = pageid;
            $scope.formData.status = formstatus;
            var jsonval = $scope.formData1;
            var refno = '"refno":"' + refnovalue + '","QuestionEntity":';
            var formarray = [];
            angular.forEach(jsonval, function (value, key) {
                formatjson = {
                    "question_id": key,
                    "answer": value,
                    "section_id": pageid
                }
                formarray.push(formatjson);
            });
            var jsonrequest = angular.toJson(formarray);
            var questionariearray = '{' + refno + jsonrequest + '}';


            $http({
                url: 'survey/outhospital/' + usertype,
                method: "POST",
                data: questionariearray
            })
                .then(function (response) {
                        var data = response.data;
                        var obj1 = angular.toJson(data);
                        var result = $.parseJSON(obj1);

                        if (data == "success") {
                            $scope.onsubmitsurvey = 'true';

                        }

                    },
                    function (response) { // optional
                        $state.go('generror');
                    });


        }

    });

