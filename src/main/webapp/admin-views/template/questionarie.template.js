'use strict';
angular.module('loginApp')
    .service('saveContent', ['$http', function ($http) {
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
    .controller('TemplateQuestionarieController', ['$scope', '$http', '$filter', 'saveContent', 'ngDialog', '$cookieStore', '$timeout', '$state', '$stateParams', '$window', '$cookies','$crypto', function ($scope, $http, $filter, saveContent, ngDialog, $cookieStore, $timeout, $state, $stateParams, $window, $cookies, $crypto) {
        $scope.isDisabled = true;
        var data = $cookies['myHeartKidID'];
        var val = data ? data.replace(/"/g, '') : '';

        $scope.redirectToDev = "index.html#/form/researchdev" + val;
        var decrypt_userroletype = sessionStorage.getItem('userrole');
        var userroletype = $crypto.decrypt(decrypt_userroletype, 'HeartKids_');

        if (userroletype != 'Superuser') {
            $state.go('form.welcome');
            $scope.responseMessage = "Login as a superuser to access the template manager";
        }


        $scope.inputlisttypeArray = ["text", "number", "textarea", "radio", "checkbox", "barscale", "select"];
        $scope.Patternarray = ["text", "telephone", "email", "numberonly", "alphanumeric", "custom"];
        $scope.truefalseArray = ["true", "false"];
        $scope.editquestiontab = 'false';
        $scope.showingdependentquestion = null;
        $scope.formDataQuestion = {};
        $scope.showqueslist = 'false';
        $scope.formPageData = {};
        $scope.addingquestions_new = true;
        $scope.showQuestionContent = false;
        $scope.SuccessMessage = false;

        var sessionID = $cookieStore.get('myHeartKidID');
        $scope.showdpntquestion = 'false';
        $scope.$watch('$viewContentLoaded', function () {
            $http({
                url: 'survey/downloadcmsfile/questionarietem',
                method: "GET"
            })
                .then(function (response) {
                    $scope.ques_template_content = angular.fromJson(response.data);
    $scope.getPageQuestions('0');

              })


        });


        $scope.getPageQuestions = function (index) {
            $scope.showeditor = false;
            $scope.successMessageContent = false;
            $scope.questions_content = '';
            $scope.page_header_content = "";
            $scope.showQuestionContent = true;
            $scope.showqueslist = 'true';
            $scope.QP_Sec_1 = 'true';
            $scope.QP_Sec_2 = 'false';
            $scope.ResponseMessage = '';
            var getQuestionList = $scope.ques_template_content;
          //  console.log("getQuestionList"+console.log(getQuestionList));
            $scope.page_header_content = getQuestionList[index];
          //  console.log(" $scope.page_header_content----------"+console.log($scope.page_header_content));

          $scope.questions_content = getQuestionList[index].questionnaire;
            $scope.index = index;
        }

        $scope.updateheadersection = function () {
            var finaljsonval = $scope.ques_template_content;

            $http({
                method: "POST",
                url: "survey/updatejson/question",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.editquestiontab = 'false';
                    $scope.successMessageContent = true;
                    $scope.ResponseMessageContent = "Page header section  updated successfully";
                })
        }

        $scope.editquestion = function (titleid, index) {
            $scope.showdpntquestion = 'false';
            $scope.formDataQuestion = {};
            $scope.updateoldquestion = 'true';
            $scope.editquestiontab = 'true';
            $scope.showingdependentquestion = null;

            var jsonhomecontent = $scope.ques_template_content;
            var result = $filter('filter')(jsonhomecontent[index].questionnaire, {title: titleid})[0];
            $scope.formDataQuestion = result;


            ngDialog.open({
                template: 'admin-views/template/edit.questionarie.html',
                scope: $scope,
                backdrop: 'static', closeByDocument: false, closeByEscape: true

            })
        }
        $scope.updatejsonquestion = function () {
            ngDialog.close();


            if ($scope.formDataQuestion.Setpattern == 'text') {
                $scope.formDataQuestion.pattern[0].regex = '^[a-zA-Z ]*$';
                $scope.formDataQuestion.pattern[0].message = 'Enter only text';
            }
            else if ($scope.formDataQuestion.Setpattern == 'telephone') {
                $scope.formDataQuestion.pattern[0].regex = '^\\d+$';
                $scope.formDataQuestion.pattern[0].message = 'Enter valid number';

            }
            else if ($scope.formDataQuestion.Setpattern == 'email') {
                $scope.formDataQuestion.pattern[0].regex = '/^[A-Za-z0-9._]+@[A-Za-z0-9.-]+\.[A-za-z]{2,4}$/';
                $scope.formDataQuestion.pattern[0].message = 'Enter valid email';
            }
            else if ($scope.formDataQuestion.Setpattern == 'numberonly') {
                $scope.formDataQuestion.pattern[0].regex = '^\\d+$';
                $scope.formDataQuestion.pattern[0].message = 'Enter valid number';
            }
            else if ($scope.formDataQuestion.Setpattern == 'alpha') {
                $scope.formDataQuestion.pattern[0].regex = '^[a-zA-Z0-9]*$';
                $scope.formDataQuestion.pattern[0].message = 'Enter only alpha number';
            }

            var finaljsonval = $scope.ques_template_content;
            $http({
                method: "POST",
                url: "survey/updatejson/question",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.editquestiontab = 'false';

                    $scope.successMessageContent = true;
                    $scope.ResponseMessageContent = "Question updated successfully";
                })

        }

        $scope.addnewquestion = function (index, depndt, deptitle) {
            $scope.formDataCreateQuestion = {};
            $scope.formDataCreateQuestion.pattern = [{}];
            $scope.formDataCreateQuestion.dependency = "";
            $scope.QuestionPositionArray = [];
            $scope.updateoldquestion = 'false';
            var jsnlength = new Date();
            var format = 'Hms';
            var questiontitle_ID = $filter('date')(jsnlength, format);
            var pageid = index + 1;
            var finaljsonval = $scope.ques_template_content;
            var jsnlength = finaljsonval[index].questionnaire.length;
            /* if(jsnlength === undefined)
             {
             jsnlength = 1;
             }*/
            $scope.formDataCreateQuestion.title = "page" + pageid + "_question" + questiontitle_ID;
            if (depndt == 'dependentques') {
                $scope.formDataCreateQuestion.questionposition = jsnlength;
                $scope.addingdepques = 'true';
                $scope.addingquestions_new = 'false';
                $scope.showoptionforDependency = true;
                $scope.dep_title = "";
                $scope.formDataCreateQuestion.showques = "false";
                $scope.depndtparentques = deptitle;
                /*$scope.formDataCreateQuestion.dependency = [{"dependentid":'"'+$scope.formDataCreateQuestion.title+'"',"value":"Yes","action":"show"}];
                 */
            }
            else {
                $scope.formDataCreateQuestion.showques = "true";
                $scope.formDataCreateQuestion.questionposition = jsnlength;

            }
            ngDialog.open({
                template: 'admin-views/template/CreateNewQuestion.html',
                scope: $scope,
                backdrop: 'static', closeByDocument: false, closeByEscape: true
            })

        }


        $scope.addingquestion = function (index) {
            ngDialog.close();
            $scope.ResponseMessage = "";
            var finaljsonval = $scope.ques_template_content;
            var insert_position = $scope.formDataCreateQuestion.questionposition;

            if ($scope.formDataCreateQuestion.Setpattern == 'text') {
                $scope.formDataCreateQuestion.pattern[0].regex = '^[a-zA-Z ]*$';
                $scope.formDataCreateQuestion.pattern[0].message = 'Enter only text';
            }
            else if ($scope.formDataCreateQuestion.Setpattern == 'telephone') {
                $scope.formDataCreateQuestion.pattern[0].regex = '^\\d+$';
                $scope.formDataCreateQuestion.pattern[0].message = 'Enter valid number';

            }
            else if ($scope.formDataCreateQuestion.Setpattern == 'email') {
                $scope.formDataCreateQuestion.pattern[0].regex = '/^[A-Za-z0-9._]+@[A-Za-z0-9.-]+\.[A-za-z]{2,4}$/';
                $scope.formDataCreateQuestion.pattern[0].message = 'Enter valid email';
            }
            else if ($scope.formDataCreateQuestion.Setpattern == 'numberonly') {
                $scope.formDataCreateQuestion.pattern[0].regex = '^\\d+$';
                $scope.formDataCreateQuestion.pattern[0].message = 'Enter valid number';
            }
            else if ($scope.formDataCreateQuestion.Setpattern == 'alpha') {
                $scope.formDataCreateQuestion.pattern[0].regex = '^[a-zA-Z0-9]*$';
                $scope.formDataCreateQuestion.pattern[0].message = 'Enter only alpha number';
            }

            if (insert_position == null || insert_position == "") {
                insert_position = finaljsonval[index].questionnaire.length;
                if (insert_position === undefined || insert_position == "") {
                    insert_position = 1;
                }
            }
           /* var dependvalue = {
                "dependentid": $scope.formDataCreateQuestion.title,
                "value": $scope.formDataCreateQuestion.selectedItem,
                "action": "show"
            }*/
            $scope.formDataCreateQuestion.dependency = [];
            finaljsonval[index].questionnaire.splice(insert_position, 0, angular.fromJson($scope.formDataCreateQuestion));

            //var result = $filter('filter')(finaljsonval[index].questionnaire, {title: dependent})[0].dependency.push(dependvalue);


            $http({
                method: "POST",
                url: "survey/updatejson/question",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.editquestiontab = 'false';
                    $scope.successMessageContent = true;
                    $scope.ResponseMessageContent = "Question added successfully";
                })
        }





        $scope.addingquestionDep = function (index, dependent) {
            ngDialog.close();
            $scope.ResponseMessage = "";
            var finaljsonval = $scope.ques_template_content;
            var insert_position = $scope.formDataCreateQuestion.questionposition;

            if ($scope.formDataCreateQuestion.Setpattern == 'text') {
                $scope.formDataCreateQuestion.pattern[0].regex = '^[a-zA-Z ]*$';
                $scope.formDataCreateQuestion.pattern[0].message = 'Enter only text';
            }
            else if ($scope.formDataCreateQuestion.Setpattern == 'telephone') {
                $scope.formDataCreateQuestion.pattern[0].regex = '^\\d+$';
                $scope.formDataCreateQuestion.pattern[0].message = 'Enter valid number';

            }
            else if ($scope.formDataCreateQuestion.Setpattern == 'email') {
                $scope.formDataCreateQuestion.pattern[0].regex = '/^[A-Za-z0-9._]+@[A-Za-z0-9.-]+\.[A-za-z]{2,4}$/';
                $scope.formDataCreateQuestion.pattern[0].message = 'Enter valid email';
            }
            else if ($scope.formDataCreateQuestion.Setpattern == 'numberonly') {
                $scope.formDataCreateQuestion.pattern[0].regex = '^\\d+$';
                $scope.formDataCreateQuestion.pattern[0].message = 'Enter valid number';
            }
            else if ($scope.formDataCreateQuestion.Setpattern == 'alpha') {
                $scope.formDataCreateQuestion.pattern[0].regex = '^[a-zA-Z0-9]*$';
                $scope.formDataCreateQuestion.pattern[0].message = 'Enter only alpha number';
            }

            if (insert_position == null || insert_position == "") {
                insert_position = finaljsonval[index].questionnaire.length;
                if (insert_position === undefined || insert_position == "") {
                    insert_position = 1;
                }
            }
            var dependvalue = {
             "dependentid": $scope.formDataCreateQuestion.title,
             "value": $scope.formDataCreateQuestion.selectedItem,
             "action": "show"
             }
            $scope.formDataCreateQuestion.dependency = [];
            finaljsonval[index].questionnaire.splice(insert_position, 0, angular.fromJson($scope.formDataCreateQuestion));

            var result = $filter('filter')(finaljsonval[index].questionnaire, {title: dependent})[0].dependency.push(dependvalue);


            $http({
                method: "POST",
                url: "survey/updatejson/question",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.editquestiontab = 'false';
                    $scope.successMessageContent = true;
                    $scope.ResponseMessageContent = "Question added successfully";
                })
        }





        $scope.deletequestion = function (titleval, index) {
            var finaljsonval = $scope.ques_template_content;
            var result = $filter('filter')(finaljsonval[index], {title: titleval});
            var index = finaljsonval[index].questionnaire.indexOf(titleval);
            result.questionnaire.splice(index, 1);

            $http({
                method: "POST",
                url: "survey/updatejson/question",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.editquestiontab = 'false';
                    $scope.successMessageContent = true;
                    $scope.ResponseMessageContent = "Question deleted successfully";
                })
        }
        $scope.createnewquespage = function () {
            $scope.formPageData = {};
            var unqueident = new Date();
            var format = 'Hms';
            var section_ID = $filter('date')(unqueident, format);
            var finaljsonval = $scope.ques_template_content.length;
            $scope.formPageData.pageid = finaljsonval + 1;
            $scope.formPageData.formName = "section_" + section_ID;
            $scope.successMessageContent = false;

            ngDialog.open({
                template: 'admin-views/template/createpage.questionnarie.html',
                scope: $scope,
                backdrop: 'static', closeByDocument: false, closeByEscape: true

            })
        }
        $scope.createnewpage = function (formPageData, index) {
            ngDialog.close();
            //var length_json = $scope.formPageData.enterpageno;

            /*var jsonString = formPageData;
             console.log(angular.toJson(formPageData));
             var appndval = '"questionnaire":[{ }]';
             jsonString.concat(appndval);
             console.log(angular.toJson(jsonString));*/

            $scope.formPageData.pagetitle =  $scope.formPageData.metadata.header;
            var test = angular.fromJson(formPageData);
            var pageid = formPageData.pageid;
            var finaljsonval = $scope.ques_template_content;
            var length_json = $scope.ques_template_content.length;
            finaljsonval.splice(length_json + 1, 0, angular.fromJson(formPageData));
            $http({
                method: "POST",
                url: "survey/updatejson/question",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.editquestiontab = 'false';
                    $scope.createpagetab = 'false';
                    $scope.successMessageContent = true;
                    $scope.ResponseMessageContent = "New page created successfully.";

                })

        }

        $scope.Showdependentques = function (dependentid, index, title) {
            $scope.showdpntquestion = 'true';
            $scope.formDependentQues = null;
            var result = [];
            var dependencyarray = dependentid[0];
            angular.forEach(dependentid, function (value, key) {
                var jsonhomecontent = $scope.ques_template_content;
                var val_result = $filter('filter')(jsonhomecontent[index].questionnaire, {title: value.dependentid})[0];
                result.push(val_result);
            });
            $scope.dependntidparent = title;
            $scope.formDependentQues = result;
        }

        $scope.deletedependentques = function (title, index, dependntid) {

            var finaljsonval = $scope.ques_template_content;

            var result = $filter('filter')(finaljsonval[index], {title: title});


            result.questionnaire[0].dependency.splice(dependntid, 1);

            /* var refreshdepentques = $filter('filter')(finaljsonval[index].questionnaire, {title: dependntid})[0];

             $scope.Showdependentques(refreshdepentques.dependency, index, dependntid);
             */

            $http({
                method: "POST",
                url: "survey/updatejson/question",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.ResponseMessage = "Dependent question deleted successfully";
                })
        }

        $scope.addependentques = function (dependentques, index) {
            if (dependentques == 'existques') {
                $scope.getPageQuestions(index);
                $scope.addexistingques = 'true';
            }
            else {
            }
        }

        $scope.Createdependentquestion = function () {
            $scope.Createorexistques = 'true';
            $scope.addexistingques = 'false';
        }

        $scope.savedependent = function (dependent, option, formDataQuestion) {

        }

        $scope.createNewDependency = function () {

        }

        $scope.Changeshowquestofalse = function (title, index, selvalue) {
            var finaljsonval = $scope.ques_template_content;

            var setshowquestion = '"showques":' + selvalue;
            var result = $filter('filter')(finaljsonval[index], {title: title});
            result.questionnaire.splice('showques', 1);

            finaljsonval.splice(2, 0, setshowquestion);

        }


        $scope.showDependencies = function (title, dependency, indexval) {
            $scope.dpndcontent = $scope.questions_content;
            var result = [];
            angular.forEach(dependency, function (value, key) {
                var val_result = $filter('filter')($scope.dpndcontent, {title: value.dependentid})[0];
                result.push(val_result);


                var myEl = angular.element(document.querySelector('#' + val_result.title));
                var mydepOpen = angular.element(document.querySelector('#' + title + '1'));

                if (myEl.hasClass('showdependencyques')) {
                    myEl.removeClass('showdependencyques');

                }
                else {
                    myEl.addClass('showdependencyques');
                }
                if (mydepOpen.hasClass('hidedep') && !myEl.hasClass('showdependencyques')) {
                    mydepOpen.removeClass('hidedep');

                }
                else if(myEl.hasClass('showdependencyques')){
                    mydepOpen.addClass('hidedep');
                }


                /* $scope.showdpntquestion = 'true';
                 $scope.formDependentQues = null;
                 var result = [];
                 var dependencyarray = dependentid[0];
                 angular.forEach(dependentid, function (value, key) {
                 var jsonhomecontent = $scope.questions_content;
                 var val_result = $filter('filter')(jsonhomecontent[index].questionnaire, {title: value.dependentid})[0];
                 result.push(val_result);
                 });
                 $scope.dependntidparent = title;
                 $scope.formDependentQues = result;*/

            })


        }

        $scope.questionsec = function (val) {
            if (val == 'questions') {

                $scope.QP_Sec_1 = "true";
                $scope.QP_Sec_2 = "false";
            }
            else {

                $scope.QP_Sec_1 = "false";
                $scope.QP_Sec_2 = "true";
            }
        }
        $scope.addingdep_question = function (formDataQuestion, index, deptitle, value, action) {
            ngDialog.close();
            $scope.ResponseMessage = "";
            var finaljsonval = $scope.ques_template_content;
            var jsnlength = finaljsonval[index].questionnaire.length;
            var dependvalue = {
                "dependentid": formDataQuestion.title,
                "value": "Yes",
                "action": "show"
            }
            if (jsnlength === undefined) {
                jsnlength = 1;
            }
            finaljsonval[index].questionnaire.splice(jsnlength, 0, angular.fromJson(formDataQuestion));

            var result = $filter('filter')(finaljsonval[index].questionnaire, {title: deptitle});
            var index_ques = finaljsonval[index].questionnaire.indexOf(result[0]);

            result[0].dependency.push(dependvalue);


            $http({
                method: "POST",
                url: "survey/updatejson/question",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {
                    $scope.editquestiontab = 'false';
                    $scope.successMessageContent = true;
                    $scope.ResponseMessageContent = "Question added successfully";
                })
        }
        $scope.addfieldforOptions = function () {
            $scope.formDataQuestion.options.push('');

        }

        $scope.addfield = function () {
            $scope.page_header_content.metadata.desc.push('');

        }
        $scope.removeChoice = function () {
            var lastItem = $scope.page_header_content.metadata.desc.length - 1;
            $scope.page_header_content.metadata.desc.splice(lastItem);
        };
        $scope.removeOptions = function () {
            var lastItem = $scope.formDataQuestion.options.length - 1;
            $scope.formDataQuestion.options.splice(lastItem);
        };
        $scope.deletepage = function (index) {
            var finaljsonval = $scope.ques_template_content;
            finaljsonval.splice(index, 1);

            $http({
                method: "POST",
                url: "survey/updatejson/question",
                headers: {'Content-Type': 'application/json'},
                data: finaljsonval

            })
                .then(function (response) {

                    $scope.successMessageContent = true;
                    $scope.ResponseMessageContent = "Page deleted successfully";
                    $scope.formDataQuestion = null;
                    $scope.page_header_content = null;
                })
        }
        $scope.showdependques = function (titleID, index, questitle) {
            var jsonhomecontent = $scope.ques_template_content;
            var result = $filter('filter')(jsonhomecontent[index].questionnaire, {title: titleID})[0];
            var resultques = $filter('filter')(jsonhomecontent[index].questionnaire, {title: questitle})[0];
            var dependencyaction = $filter('filter')(resultques.dependency, {dependentid: titleID})[0];
            $scope.showdependentvalue = dependencyaction.value;
            $scope.showdependentaction = dependencyaction.action;
            $scope.showingdependentquestion = result.question;
        }


        $scope.RangeLimit = function (start, end) {
            var result = [];
            if (end === 1) {
                result.push(1);
            } else {
                for (var i = start; i <= end; i++) {
                    result.push(i);
                }
            }
            return result;
        }


        $scope.publishsite = function () {
            $http({
                method: "GET",
                url: "survey/publish"

            })
                .then(function (response) {
                    $scope.successMessageContent = true;
                    $scope.ResponseMessageContent = "Contents published successfully";
                })


        }

        $scope.disableButton = function () {
            $scope.isDisabled = false;

            $window.open($scope.redirectToDev, '_blank');
        }
    }]);