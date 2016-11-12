(function () {
    'use strict';

    angular.module('loginApp')

        .filter('isEmpty', function () {
            var bar;
            return function (obj) {
                for (bar in obj) {
                    if (obj.hasOwnProperty(bar)) {
                        return false;
                    }
                }
                return true;
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
        .directive("passwordVerify", function () {
            return {
                require: "ngModel",
                scope: {
                    passwordVerify: '='
                },
                link: function (scope, element, attrs, ctrl) {
                    scope.$watch(function () {
                        var combined;

                        if (scope.passwordVerify || ctrl.$viewValue) {
                            combined = scope.passwordVerify + '_' + ctrl.$viewValue;
                        }
                        return combined;
                    }, function (value) {
                        if (value) {
                            ctrl.$parsers.unshift(function (viewValue) {
                                var origin = scope.passwordVerify;
                                if (origin !== viewValue) {
                                    ctrl.$setValidity("passwordVerify", false);
                                    return undefined;
                                } else {
                                    ctrl.$setValidity("passwordVerify", true);
                                    return viewValue;
                                }
                            });
                        }
                    });
                }
            };
        })

        .controller('HomeController', ['$scope', '$http', '$state', '$window', 'dataService', '$rootScope', '$anchorScroll', '$location', '$timeout', '$cookieStore', '$crypto', function ($scope, $http, $state, $window, dataService, $rootScope, $anchorScroll, $location, $timeout, $cookieStore, $crypto) {
            $scope.config = {
                title: 'Monthly',
                tooltips: true,
                labels: false,
                waitForHeightAndWidth: false,
                colors: ['#0673B5', '#cccccc'],
                line: true
            };

            $scope.config_week = {
                title: 'This week',
                tooltips: true,
                labels: false,
                waitForHeightAndWidth: false,
                colors: ['#0673B5', '#cccccc']
            };
            $scope.edituserdetailshow = false;
            $scope.questionariesection = false;
            $scope.formData = {};
            $scope.init = function () {
                $scope.listadminuser();
            }
            $scope.scrollTo = function (id) {
                $location.hash(id);
                $anchorScroll();
            }
            $scope.advancedoption = false;

            $scope.$watch('advancedoption', function () {
                $scope.toggleText = $scope.advancedoption ? '- Standard Search' : '+ Advanced Search';
            })
            var accessToken = sessionStorage.getItem('tokenId');
            var adminuser = sessionStorage.getItem('adminuser');

            if (accessToken != null) {
                $timeout(function () {

                    $scope.logoutadmin();
                }, 1700000);
            }
            $scope.adminuser = adminuser;


            $scope.sexArray = ["Male", "Female"];
            $scope.yesnoArray = ["yes", "no"];
            $scope.yesnoArraycaps = ["yes", "no"];
            $scope.yesnoArrayCaps = ["Yes", "No"];

            $scope.usertypeArray = ["Patient", "Carer"];
            $scope.titleArray = ["Mr", "Mrs", "Miss", "Ms", "Dr", "Prof", "Lady"];
            $scope.sexArray = ["Male", "Female"];
            $scope.conttypeArray = ["Phone", "Email"];
            $scope.ethnicityArray = ["Caucasian", "Aborigional / Tores Strait Island", "Mouri", "Asian", "Indian", "Black/Afican American", "European", "None of the above"];
            $scope.lstofcountryArray = ["Australia", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua & Deps", "Argentina", "Armenia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Rep", "Chad", "Chile", "China", "Colombia", "Comoros", "Congo", "Congo {Democratic Rep}", "Costa Rica", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland {Republic}", "Israel", "Italy", "Ivory Coast", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea North", "Korea South", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar, {Burma}", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russian Federation", "Rwanda", "St Kitts & Nevis", "St Lucia", "Saint Vincent & the Grenadines", "Samoa", "San Marino", "Sao Tome & Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad & Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican", "Other"];
            $scope.languageArray = ["English", "Chinese", "Italian", "Vietnamese", "Greek", "Cantonese", "Arabic", "Mandarin", "Macedonian", "French", "Spanish", "Other"];
            $scope.statelistArray = ["New South Wales", "Australian Capital Territory", "Victoria", "Queensland", "South Australia", "Western Australia", "Tasmania", "Northern Territory", "Other"];
            $scope.heartcondsArray = ["Atrial septal defect", "Ventricular septal defect", "Atrioventricular septal defect", "Patent ductus arteriosus", "Pulmonary vein anomaly", "Tricuspid atresia", "Ebstein’s anomaly ", "Dysplastic tricuspid valve", "Pulmonary stenosis", "Pulmonary atresia", "Tetralogy of Fallot", "Abnormal mitral valve", "Aortic stenosis", "Coarctation of the aorta or interrupted aorta", "Truncus arteriosus", "Bicuspid aortic valve", "Hypoplastic left heart syndrome", "Transposition of the great arteries", "Congenitally corrected transposition of the great arteries", "Double-outlet right ventricle", "Double-inlet left ventricle", "Double-inlet right ventricle", "Atrial isomerism (left or right)", "None of the above"];
                $scope.myNumber = 100;
            $scope.getNumber = function (num) {
                return new Array(num);
            }

            var istokenvalid = sessionStorage.getItem('isTokenValid');

            if (accessToken == null) {
                $state.go('/login');
            }
            else {
                var decrypt_userroletype = sessionStorage.getItem('userrole');
                var userroletype = $crypto.decrypt(decrypt_userroletype, 'HeartKids_');
                if (userroletype != "" && userroletype == "Admin") {
                    $scope.admin = true;
                }
                else if(userroletype != "" && userroletype == "Superuser")
                {
                    $scope.admin = true;
                    $scope.superuser = true;
                }
                else
                {
                    $scope.admin = false;
                    $scope.superuser = false;

                }
                $scope.userroleArray = ["Admin","Viewer","Superuser"];
                $scope.showModal = false;
                $scope.buttonClicked = "";
                $scope.toggleModal = function (btnClicked) {
                    $scope.buttonClicked = btnClicked;
                    $scope.showModal = !$scope.showModal;
                };
                $scope.sort = function (keyname) {
                    $scope.sortKey = keyname;   //set the sortKey to the param passed
                    $scope.reverse = !$scope.reverse; //if true make it false and vice versa
                }


                $scope.saveContent = function (fileContents, fileName) {
                    var anchor = angular.element('<a/>');
                    anchor.attr({
                        href: 'data:attachment/csv;charset=utf-8,' + encodeURI(fileContents),
                        target: '_blank',
                        download: fileName
                    })[0].click();
                }
                $scope.exportDataBySearch = function()
                {
                    $scope.searchHeartKid = false;
                    var date = new Date().getDate() + "_" + (new Date().getMonth() + 1) + "_" + new Date().getFullYear();
                    var filename = 'HeartKid_Results' + date + '.csv';
                    if ($scope.formAdminData.surgerydelaycount == "") {
                        $scope.formAdminData.surgerydelaycount = null;
                    }
                    /* if ($scope.formAdminData.conditioncalld == 'No') {
                        $scope.formAdminData.conditioncalld = null;
                    }
                    if ($scope.formAdminData.surgeryheld == 'No') {
                        $scope.formAdminData.surgeryheld = null;
                    }
                    if ($scope.formAdminData.surgerydelay == 'No') {
                        $scope.formAdminData.surgerydelay = null;
                    }
                    if ($scope.formAdminData.anxietycond == 'No') {
                        $scope.formAdminData.anxietycond = null;
                    }
                    if ($scope.formAdminData.trvlsurg == 'No') {
                        $scope.formAdminData.trvlsurg = null;
                    }*/

                    if ($scope.formAdminData.agefrom == "") {
                        $scope.formAdminData.ageto = "";
                        $scope.formAdminData.age = "empty-empty";
                    }
                    else {
                        if ($scope.formAdminData.ageto == "") {
                            $scope.formAdminData.ageto = $scope.formAdminData.agefrom;
                        }
                        $scope.formAdminData.age = $scope.formAdminData.agefrom + "-" + $scope.formAdminData.ageto;
                    }


                    if ($scope.formAdminData.contactvia == 'Phone') {
                        $scope.formAdminData.contctviaphone = 'yes';
                    }
                    if ($scope.formAdminData.contactvia == 'Email') {
                        $scope.formAdminData.contctviaemail = 'yes';
                    }
                    $http({
                        url: 'survey/downloadExcelBySearch',
                        method: "POST",
                        data: $scope.formAdminData
                    })

                        .success(function (data, status, headers, config) {
                          //  $scope.JSONToCSVConvertor(data, "Vehicle Report", true);



                          /*  var uri = "data:text/csv;charset=utf-8," + encodeURIComponent(csv);
                            $(".csv a.download").attr("href", uri);*/

                            var csv = $.csv.fromObjects(data);
                           var uri = "data:text/csv;charset=utf-8," + encodeURIComponent(csv);
                            var link = document.createElement("a");
                            link.setAttribute("href", uri);
                            link.setAttribute("download", "HeartKidReports.csv");

                            link.click();

                        }).error(function (data, status, headers, config) {
                        //upload failed
                    })

                }


                $scope.flatten =function(data) {
                    var result = {};
                    function recurse (cur, prop) {
                        if (Object(cur) !== cur) {
                            result[prop] = cur;
                        } else if (Array.isArray(cur)) {
                            for(var i=0, l=cur.length; i<l; i++)
                                recurse(cur[i], prop + "[" + i + "]");
                            if (l == 0)
                                result[prop] = [];
                        } else {
                            var isEmpty = true;
                            for (var p in cur) {
                                isEmpty = false;
                                recurse(cur[p], prop ? prop+"."+p : p);
                            }
                            if (isEmpty && prop)
                                result[prop] = {};
                        }
                    }
                    recurse(data, "");
                    return result;
                };


                $scope.JSONToCSVConvertor = function(JSONData, ReportTitle, ShowLabel)
                {
                    var arrayObj = [];
               //    console.log($scope.flatten(JSONData));

                    var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;

                    //arrayObj.push($scope.flatten(JSONData));//

                    var CSV = '';
                    //Set Report title in first row or line

                    CSV += ReportTitle + '\r\n\n';

                    //This condition will generate the Label/Header
                   if (ShowLabel) {
                        var row = "";
                        //This loop will extract the label from 1st index of on array
                        for (var index in arrData[0]) {

                            if(typeof arrData[0][index] =='object')
                            {
                                for(var x in arrData[0][index])
                                {
                                  //  row += '"' + arrData[i][index][x] + '",';
                                    row += x + ',';
                                }
           }
                            else {
                                row += index + ',';

                            }
                            //Now convert each value to string and comma-seprated
                          //  row += index + ',';
                        }

                        row = row.slice(0, -1);

                        //append Label row with line break
                        CSV += row + '\r\n';
                    }
/**/
                    //1st loop is to extract each row
                    for (var i = 0; i < arrData.length; i++) {
                        var row = "";

                        //2nd loop will extract each column and convert it in string comma-seprated
                        for (var index in arrData[i]) {

                            if(typeof arrData[i][index] =='object')
                            {
                               // console.log( arrData[i][index]);


                                for(var x in arrData[i][index])
                                {
                                    row += '"' + arrData[i][index][x] + '",';
                                }
                           }
                            else {
                                row += '"' + arrData[i][index] + '",';
                            }

                        }

                        row.slice(0, row.length - 1);

                        //add a line break after each row
                        CSV += row + '\r\n';
                    }

                    if (CSV == '') {
                        alert("Invalid data");
                        return;
                    }

                    //Generate a file name
                    var fileName = "R2R";
                    //this will remove the blank-spaces from the title and replace it with an underscore
                    fileName += ReportTitle.replace(/ /g,"_");

                    //Initialize file format you want csv or xls
                    var uri = 'data:text/csv;charset=utf-8,' + escape(CSV);

                    // Now the little tricky part.
                    // you can use either>> window.open(uri);
                    // but this will not work in some browsers
                    // or you will not get the correct file extension

                    //this trick will generate a temp <a /> tag
                    var link = document.createElement("a");
                    link.href = uri;

                    //set the visibility hidden so it will not effect on your web-layout
                    link.style = "visibility:hidden";
                    link.download = fileName + ".csv";

                    //this part will append the anchor tag and remove it after automatic click
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);

                }

                $scope.exportData = function () {

                    return new Blob([json], {type: "application/json"});
    }


                $scope.statusArray = ["incomplete", "success", "pending approval"];
                $scope.usertypeArray = ["Patient", "Carer"];

                $scope.modifyuser = function (a) {

                    var b = $.parseJSON(angular.fromJson(a));


                    $scope.formAdminData = a;


                }


                // the dialog is injected in the specified controller


                $scope.searchheartkid = function () {
                    $scope.deleteMessage = "";
                    $scope.EditMessage = "";
                    $scope.ErrorMessage = "";
                    $scope.searchusers = "";
                    $scope.searchHeartKid = false;
                    $scope.totalSearchCount = '0';
                    $scope.edituserdetailshow = false;
               if ($scope.formAdminData.surgerydelaycount == "") {
                        $scope.formAdminData.surgerydelaycount = null;
                  }
                    /*  if ($scope.formAdminData.conditioncalld == 'No') {
                        $scope.formAdminData.conditioncalld = null;
                    }
                    if ($scope.formAdminData.surgeryheld == 'No') {
                        $scope.formAdminData.surgeryheld = null;
                    }
                    if ($scope.formAdminData.surgerydelay == 'No') {
                        $scope.formAdminData.surgerydelay = null;
                    }
                    if ($scope.formAdminData.anxietycond == 'No') {
                        $scope.formAdminData.anxietycond = null;
                    }
                    if ($scope.formAdminData.trvlsurg == 'No') {
                        $scope.formAdminData.trvlsurg = null;
                    }*/

                    if ($scope.formAdminData.agefrom == "") {
                        $scope.formAdminData.ageto = "";
                        $scope.formAdminData.age = "empty-empty";
                    }
                    else {
                        if ($scope.formAdminData.ageto == "") {
                            $scope.formAdminData.ageto = $scope.formAdminData.agefrom;
                        }
                        $scope.formAdminData.age = $scope.formAdminData.agefrom + "-" + $scope.formAdminData.ageto;
                    }


                    if ($scope.formAdminData.contactvia == 'Phone') {
                        $scope.formAdminData.contctviaphone = 'yes';
                    }
                    if ($scope.formAdminData.contactvia == 'Email') {
                        $scope.formAdminData.contctviaemail = 'yes';
                    }
                    var d = new Date();
                    var n = d.getMilliseconds();
                    $http({
                        url: 'survey/getrecord',
                        method: "POST",
                        headers: {
                            'Authorization': 'Bearer ' + accessToken,
                            'Content-Type': 'application/json'
                        },
                        data: $scope.formAdminData
                    })
                        .then(function (response) {
                                var a = new Date();
                                var b = a.getMilliseconds();
                                var data = angular.fromJson(response.data);
                                var data1 = angular.toJson(response.data);
                                var exporttoexcel;
                                $scope.searchHeartKid = true;
                                if (data != '' && data.length <500) {
                                    $scope.totalSearchCount = data.length;
                                    $scope.searchusers = data;
                                    $scope.norecordsfnd = false;
                                    $scope.userrecordspresent = true;
                                }
                                else if(data != '' && data.length >500)
                                {
                                    $scope.totalSearchCount = data.length;

                                     exporttoexcel =  $window.confirm('There are more than 500 records. Please refine your search');
                                   if(!exporttoexcel)
                                   {
                                       $scope.totalSearchCount = data.length;

                                       $scope.searchusers = data.profile;
                                       $scope.norecordsfnd = false;
                                       $scope.userrecordspresent = true;
                                   }


                                }
                                else {
                                    $scope.norecordsfnd = true;
                                    $scope.userrecordspresent = false;

                                }
                                $scope.totalrecords = data.length;
                                $location.hash('searchtable');

                                // call $anchorScroll()
                                $anchorScroll();
                            },
                            function (response) { // optional
                                // failed
                                alert("Failure" + response.status);
                            });
                }


                $scope.showview = function (id) {


                    if (id == 'search') {
                        $scope.searchuser = true;
                        $scope.modifyuser = true;
                    }
                    if (id == 'modify') {
                        $scope.modifyuser = false;
                        $scope.searchuser = false;
                    }
                }

                $scope.clearall = function () {
                    $scope.formAdminData.referencenumber = "";
                    $scope.formAdminData.agefrom = "";
                    $scope.formAdminData.sex = null;
                    $scope.formAdminData.state = null;
                    $scope.formAdminData.contactvia = null;
                    $scope.formAdminData.ethnicity = null;
                    $scope.formAdminData.ageto = "";
                    $scope.formAdminData.heartconds = null;
                    $scope.formAdminData.surgerydelaycount = "";
                    $scope.formAdminData.usertype = null;
                    $scope.formAdminData.status = null;
                    $scope.formAdminData.conditioncalld = null;
                    $scope.formAdminData.surgeryheld = null;
                    $scope.formAdminData.surgerydelay = null;
                    $scope.formAdminData.trvlsurg = null;
                    $scope.formAdminData.anxietycond = null;
                    $scope.formAdminData.countrybirth = null;
                    $scope.deleteMessage = '';
                    $scope.EditMessage = "";
                    $scope.ErrorMessage = "";

                }

                $scope.clearallmanage = function () {
                    $scope.formAdminData = '';
                    $scope.userForm.$dirty = false;
                    $scope.userForm.$pristine = true;
                    $scope.userForm.$setPristine();
                    $scope.userForm.$setUntouched();
                }
                $scope.$watch('$viewContentLoaded', function () {
                    $http({
                        url: 'survey/reportcount',
                        method: "GET",
                        async: false

                    })

                        .then(function (response) {
                                $scope.reportcount = response.data;
                                $scope.string = $scope.reportcount;
                                $scope.usertype1 = $scope.string[0];
                                if ($scope.string[1] == undefined)
                                    $scope.usertype2 = [];
                                else
                                    $scope.usertype2 = $scope.string[1];


                                $scope.reportData = [{key: $scope.usertype1[0], y: $scope.usertype1[1]}, {
                                    key: $scope.usertype2[0],
                                    y: $scope.usertype2[1]
                                }];
                                $scope.xFunction = function () {
                                    return function (d) {
                                        return d.key;
                                    };
                                }
                                $scope.yFunction = function () {
                                    return function (d) {
                                        return d.y;
                                    };
                                }

                                $scope.descriptionFunction = function () {
                                    return function (d) {
                                        return d.key;
                                    }
                                }
                            },
                            function (response) {
                                $state.go('form.generror');
                            })





                    $http({
                        url: 'survey/reportbarcount',
                        method: "GET",
                        async: false

                    })


                        .then(function (response) {
                            $scope.reportbarcount = response.data;



                            var parray;
                            var carray;
                            var larray;

                            function get_months() {
                                "use strict";
                                var monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

                                var today = new Date();
                                var d;
                                var month = [];

                                for (var i = 5; i >= 0; i -= 1) {

                                    d = new Date(today.getFullYear(), today.getMonth() - i, 1);
                                    month.push(monthNames[d.getMonth()])
                                }
                                return month;
                            }

                            function get_bar_values() {
                                var xx = get_months();
                                var all_data = []
                                for (var dd = 0; dd < xx.length; dd++) {
                                    var y_array = []
                                    var pbar_val = get_pvalues(xx[dd])
                                    var cbar_val = get_cvalues(xx[dd])
                                    var lbar_val = get_lvalues(xx[dd])
                                    y_array.push(pbar_val)
                                    y_array.push(cbar_val)
                                    y_array.push(lbar_val)
                                    all_data.push({x: xx[dd].substring(0, 3), y: y_array})
                                }
                                return all_data;
                            }

                            function get_pvalues(a_month) {
                                var pvals = $scope.reportbarcount[0]
                                if (typeof $scope.reportbarcount[0] == null) {
                                    return 0;
                                }

                                if (pvals.length == 0) {
                                    return 0;
                                }

                                for (var ii = 0; ii < pvals.length; ii++) {

                                    if (a_month.substring(0, 3) == pvals[ii][2].substring(0, 3)) {
                                        return pvals[ii][1];
                                    }
                                }
                                return 0;
                            }

                            function get_cvalues(a_month) {

                                if (typeof $scope.reportbarcount[1] == null) {
                                    return 0;
                                }
                                var cvals = $scope.reportbarcount[1]
                                if (cvals.length == 0) {
                                    return 0;
                                }
                                for (var ii = 0; ii < cvals.length; ii++) {
                                    if (a_month.substring(0, 2) == cvals[ii][2].substring(0, 2)) {
                                        return cvals[ii][1];
                                    }
                                }
                                return 0;

                            }

                            function get_lvalues(a_month) {


                                if (typeof $scope.reportbarcount[2] == null) {
                                    return 0;
                                }

                                var lvals = $scope.reportbarcount[2]

                                if (lvals.length == 0) {
                                    return 0;
                                }
                                for (var ii = 0; ii < lvals.length; ii++) {
                                    if (a_month.substring(0, 2) == lvals[ii][2].substring(0, 2)) {
                                        return lvals[ii][1];
                                    }
                                }
                                return 0;
                            }

                            var month_array = []
                            for (var i_rec = 0; i_rec < $scope.reportbarcount.length; i_rec++) {
                                var a_rec = $scope.reportbarcount[i_rec]
                            }

                            var d = new Date();

                            $scope.data = {
                                series: ['Patient', 'Carer'],
                                data: get_bar_values()
                            };
                            var colorArray = ['#FF0000', '#0000FF', '#FFFF00', '#00FFFF'];
                            $scope.colorFunction = function () {
                                return function (d, i) {
                                    return colorArray[i];
                                };
                            }

                        })

                    $http({
                        url: 'survey/reportweekbarcount',
                        method: "GET",
                        async: false

                    })
                        .then(function (response) {
                            $scope.reportweekbarcount = response.data;
                            //  alert($scope.reportweekbarcount)



                            function get_weekdays() {

                                var days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];
                                var goBackDays = 7;

                                var today = new Date();
                                var daysSorted = [];
                                var daysSorted_rev = [];

                                for (var i = 0; i < goBackDays; i++) {
                                    //alert(i)
                                    var newDate = new Date(today.setDate(today.getDate() - 1));
                                    //alert(days[newDate.getDay()])
                                    daysSorted.push(days[newDate.getDay()]);
                                }

                                for (var j = daysSorted.length - 1; j >= 0; j--) {
                                    //alert(j);
                                    daysSorted_rev.push(daysSorted[j]);
                                    //alert(daysSorted[j]);
                                }
                                // alert(daysSorted_rev.length )
                                return daysSorted_rev;

                            };

                            get_bar_values_weekly()
                            function get_bar_values_weekly() {
                                var xxx = get_weekdays();
                                var all_data_weekly = []

                                for (var dd = 0; dd < xxx.length; dd++) {
                                    var y_array_weekly = []

                                    var pbar_val = get_pvalues_weekly(xxx[dd])
                                    var cbar_val = get_cvalues_weekly(xxx[dd])
                                    var lbar_val = get_lvalues_weekly(xxx[dd])
                                    y_array_weekly.push(pbar_val)
                                    y_array_weekly.push(cbar_val)
                                    y_array_weekly.push(lbar_val)
                                    all_data_weekly.push({x: xxx[dd].substring(0, 3), y: y_array_weekly})
                                }
                                return all_data_weekly;
                            }

                            function get_pvalues_weekly(a_day) {
                                if (typeof $scope.reportweekbarcount[0] == null) {
                                    return 0;
                                }

                                var pvals = $scope.reportweekbarcount[0]

                                if (pvals.length == 0) {
                                    return 0;
                                }
                                for (var ii = 0; ii < pvals.length; ii++) {
                                    if (a_day == pvals[ii][2]) {
                                        return pvals[ii][1];
                                    }
                                }
                                return 0;
                            }

                            function get_cvalues_weekly(a_day) {
                                if (typeof $scope.reportweekbarcount[1] == null) {
                                    return 0;
                                }

                                var cvals = $scope.reportweekbarcount[1]

                                if (cvals.length == 0) {
                                    return 0;
                                }
                                for (var ii = 0; ii < cvals.length; ii++) {
                                    if (a_day == cvals[ii][2]) {
                                        return cvals[ii][1];
                                    }
                                }
                                return 0;
                            }


                            function get_lvalues_weekly(a_day) {
                                if (typeof $scope.reportweekbarcount[2] == null) {
                                    return 0;
                                }

                                var lvals = $scope.reportweekbarcount[2]

                                if (lvals.length == 0) {
                                    return 0;
                                }
                                for (var ii = 0; ii < lvals.length; ii++) {
                                    if (a_day == lvals[ii][2]) {
                                        return lvals[ii][1];
                                    }
                                }
                                return 0;
                            }

                            $scope.data1 = {
                                series: ['Patient', 'Carer'],
                                data: get_bar_values_weekly()
                            };
                        })

                });


                $scope.deleteuser = function (index, deluser) {

                    $http({
                        url: 'survey/deleterecord/' + deluser,
                        headers: {
                            'Authorization': 'Bearer ' + accessToken,
                            'Content-Type': 'application/json'
                        },
                        method: "GET"
                    })
                        .then(function (response) {
                                // success
                                $scope.deleteMessage = response.data;
                                $scope.searchusers.splice(index, 1);
                                // $scope.users = "";
                            },
                            function (response) { // optional
                                // failed
                                $scope.deleteMessage = "Error in deleting the record ! Try later";

                            });

                }
                $scope.createadminuser = function () {
                    $scope.passwordmismatch = false;

                    $scope.creationMessage = '';


                    if ($scope.formAdminData.password != $scope.formAdminData.confirm_password) {
                        $scope.passwordmismatch = true;
                        $scope.formAdminData.confirm_password = "";
                        $scope.formAdminData.password = "";

                    }
                    else {
                        $http({
                            url: 'survey/createadminuser',
                            method: "POST",
                            headers: {
                                'Authorization': 'Bearer ' + accessToken,
                                'Content-Type': 'application/json'
                            },
                            data: $scope.formAdminData
                        })
                            .then(function (response) {
                                    // success

                                    if (response.data == "success") {

                                        var creationMessage = response.data + $scope.formAdminData.username;
                                        $scope.creationMessage = "User has been successfully created";
                                        $scope.clearallmanage();
                                        /* $scope.formAdminData = null;*/
                                    }
                                    else if (response.data == "useridexist") {
                                        $scope.creationMessage = 'User ID is not available . Please choose different user ID';
                                    }
                                    else {
                                        $scope.formAdminData = "";

                                        $scope.creationMessage = "Error in creating a user. Please try again later";
                                    }
                                },
                                function (response) { // optional
                                    // failed
                                    alert("Failure" + response.status);
                                });
                    }
                }


                $scope.logoutadmin = function () {
                    // var accessToken = sessionStorage.getItem('tokenId');

                    $http({
                        url: 'token/revoke',
                        method: "DELETE",
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                            'Authorization': 'Basic ' + accessToken
                        }

                    })

                    if (accessToken != null) {
                        sessionStorage.clear();
                        localStorage.clear();
                        $scope.isValidUser = false;
                        $cookieStore.remove('myHeartKidID');
                        $state.go('/login');

                    }
                    else {
                        sessionStorage.clear();
                        localStorage.clear();
                        $state.go('/login');
                    }
                }

                $scope.listadminuser = function () {

                    $scope.adminusershw = '';
                    $scope.manageaadmin = 'true';


                    $http({
                        url: 'survey/listadminuser',
                        method: "GET",
                        headers: {
                            'Authorization': 'Bearer ' + accessToken,
                            'Content-Type': 'application/json'
                        }

                    })
                        .then(function (response) {
                                var data = angular.fromJson(response.data);
                                $scope.showadmincreate = "false";
                                $scope.manageuser = 'true';

                                $scope.users = data;
                                $scope.totalrecords = data.length;
                            },
                            function (response) {
                                alert("Error respomse----->" + response.data);
                            })
                }
                $scope.deleteuseradmin = function () {
                    var username = $scope.formAdminData.delusername;
                    var logedUsername = sessionStorage.getElementsByClassName('className')
alert($scope.vm.heartkidusername.trim());
                    $http({
                        url: 'survey/fetchadminuser/' + username,
                        headers: {
                            'Authorization': 'Bearer ' + accessToken,
                            'Content-Type': 'application/json'
                        },
                        method: "GET"


                    })

                        .then(function (response) {
                                var data = angular.fromJson(response.data);
                                $scope.delusers = data;
                                $scope.totalrecords = data.length;
                                var adminuser = sessionStorage.getItem('adminuser');
                                if(username == adminuser)
                                  {
                                    $scope.logoutadmin();

                                  }
                            },
                            function (response) {
                                alert("Error respomse----->" + response.data);
                            })

                }
                $scope.adminusershow = function () {
                    $scope.showadmincreate = "true";
                    $scope.manageuser = "false";

                    $scope.adminusershw = 'true';
                    $scope.manageaadmin = '';

                }
                $scope.deleteadminuser = function (index, username) {
                    $http({
                        url: 'survey/deleteadminuser/' + username,
                        headers: {

                            'Authorization': 'Bearer ' + accessToken,
                            'Content-Type': 'application/json'
                        },
                        method: "GET"
                    })

                        .then(function (response) {
                                var data = angular.fromJson(response.data);

                                if (data == 1) {
                                    $scope.manageAdminMessage = "User deleted successfully  - Username : " + username;
                                    $scope.users.splice(index, 1);
                                }
                                else {
                                    $scope.manageAdminMessage = "Error in deleting user. Please try again later! ";

                                }
                            },
                            function (response) {
                                $scope.manageAdminMessage = "Error in deleting user. Please try again later! ";
                            })

                }
                $scope.resetuserpassword = function (index, username) {

if(username != "")

                    $http({
                        url: 'survey/resetuserpass/'+username,
                        headers: {
                            'Authorization': 'Bearer ' + accessToken,
                            'Content-Type': 'application/json'
                        },
                        method: "GET"
                    })

                        .then(function (response) {
                                var data = angular.fromJson(response.data);

                                if (data == 1) {
                                    $scope.manageAdminMessage = "User password reset successful for user -: " + username;
                                }
                                else {
                                    $scope.manageAdminMessage = "Error in resetting password user. Please try again later! ";

                                }
                            },
                            function (response) {
                                $scope.manageAdminMessage = "Error in resetting user. Please try again later! ";
                            })

                }
                $scope.getuserdetails = function (editID) {
                    $scope.formData = {};
                    $scope.formDataQuestion = {};
                    $scope.edituserdetailshow = 'true';
                    $scope.questionairescopew = {};
                    $scope.editMenu('personal');
                    $scope.showNumber = 'personal';
                    $scope.activeMenu = 'personal';
                    $scope.EditMessage = '';

                    var pagecontent = 'questioncontent';
                    $timeout(function () {
                        $http({
                            url: 'survey/gethkpagecontent/' + pagecontent,
                            method: "GET"
                        })
                            .then(function (response) {
                                var responseData = angular.fromJson(response.data);
                                $scope.HK_CHD_Content = responseData;
                                var responseData = angular.fromJson(response.data);
                                $scope.responsedata = responseData;
                                $scope.pagelength = $scope.responsedata.length;
                                $scope.HK_CHD_Content = $scope.responsedata;

                            })
                    }, 0);

                    $http({
                        url: 'survey/getuserdetails/' + editID,
                        headers: {
                            'Authorization': 'Bearer ' + accessToken,
                            'Content-Type': 'application/json'
                        },
                        method: "GET"

                    })

                        .then(function (response) {
                                var data = angular.toJson(response.data);
                                var data1 = angular.fromJson(response.data);
                            console.log("data----"+data);

                                console.log();

                                $scope.formData = data1[0];
                                $location.hash('edituser');
                                $anchorScroll();


                                angular.forEach(data1[0].questionaire, function(value, key) {
                                    console.log(key['question_id'] + ': ' + value['question_id']);
                                    var question_id = value['question_id'] ;
                                   $scope.questionairescopew[question_id] = value['answer'];
                                })
                          // var questionarieFormat = "[{"+$scope.formData.questionaire+"}]";

                          // console.log(questionarieFormat);
                             //   var questionarie = angular.fromJson(questionarieFormat);
                             //   console.log(questionarie);

                               // $scope.questionairescopew = data1[0].questionaire;

                            },
                            function (response) {
                                alert("Error respomse----->" + response.data);
                            })
            }

                $scope.updateProfilesavedetails = function () {
                    var adminuser = sessionStorage.getItem('adminuser');
                    $scope.formData.profile.updatedBy = adminuser;
                    $http({

                        url: 'survey/updatePersonalInfo',
                        method: "POST",
                        headers: {
                            'Authorization': 'Bearer ' + accessToken,
                            'Content-Type': 'application/json'
                        },
                        data: $scope.formData.profile
                    })
                        .then(function (response) {
                            var data = angular.toJson(response.data);

                            $scope.EditMessage = "Data updated Successfully for user reference number : " + $scope.formData.profile.refno;


                        })

                }
                $scope.getQuestionContent = function (menu) {
                    $scope.questionariesection = true;
                    $scope.tab = menu;
                    /*alert(menu);
                     $scope[menu] = false;
                     $scope.showNumber = menu;
                     $scope.tab = menu;
                     $scope.editRecordUpdate = $scope.responsedata;
                     console.log("dsadasdsad"+$scope.editRecordUpdate);*/

                }
                $scope.calculateAge = function calculateAge(birthday) { // birthday is a date

                    var dob = new Date(birthday);
                    var today = new Date();
                    var agevalue = 0;
                    agevalue = today.getMonth() - dob.getMonth() + (12 * (today.getFullYear() - dob.getFullYear()));
                    if (isNaN(agevalue)) {
                        $scope.formData.age = "<1";
                    }
                    else {
                        $scope.formData.age = (agevalue / 12).toFixed(0);
                    }
                }
                //EDIT FUCNTION
                $scope.editsavedetails = function () {
                    $scope.formData.updateddate = "admin";
                    var adminuser = sessionStorage.getItem('adminuser');


                    var refnovalue = $scope.formData.profile.refno;
                    var formatjson;
                    var formarray = [];
                    var valuejson = [];
                    var jsonval = $scope.questionairescopew;
                    var refno = '"refno":"' + refnovalue + '","QuestionEntity":';
valuejson = valuejson.push({"version":"123"});
                    angular.forEach(jsonval, function (value, key) {
                        formatjson = {
                            "refno": refno,
                            "question_id": key,
                            "answer": value,
                        }
                        formarray.push(formatjson);
                    });

                    var jsonrequest = angular.toJson(formarray);
                    var questionariearray = '{' + refno + jsonrequest + '}';

                     $http({
                        url: 'survey/updatequestionarierecords/'+adminuser,
                        method: "POST",
                        headers: {
                            'Authorization': 'Bearer ' + accessToken,
                            'Content-Type': 'application/json'
                        },
                        data: questionariearray
                    })
                        .then(function (response) {
                                var data = angular.toJson(response.data);

                                $scope.EditMessage = "Data Saved Successfully for reference number : " + $scope.formData.profile.refno;


                            },
                            function (status) {
                                $state.go('form.generror');
                                $scope.EditMessage = "Error in saving data. Please close and login again";
                            })
                }
                $scope.closeedittable = function () {
                    $scope.edituserdetailshow = 'false';
                    $scope.EditMessage = "";
                }
                $scope.tab = '';
                $scope.editMenu = function (menu) {
                    $scope.questionariesection = false;
                    $scope.showNumber = menu;
                    $scope.tab = menu;
                }
                $scope.isSelected = function (checkTab) {
                    return $scope.tab === checkTab;
                }

            }


          $scope.setPageQuestion = function(index)
            {
                $scope.editRecordContent =  $scope.HK_CHD_Content[index];
                $scope.tab = index;

            }
    }])

})();
