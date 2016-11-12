(function () {
    'use strict';

    angular
        .module('loginApp', ['ui.router', 'angularUtils.directives.dirPagination', 'nvd3ChartDirectives', 'angularCharts','ngDialog','ngCookies','xeditable','mdo-angular-cryptography'])
        .service('dataService', function () {

            // private variable
            var dataObj;

            this.dataObj = dataObj;
        })
        .config(function ($stateProvider, $urlRouterProvider) {
            $stateProvider
                .state('form', {
                    url: '/form',
                    controller: 'HomeController',
                    templateUrl: 'admin-views/home/home.view.html'
                })

                .state('form.export', {
                    url: '/export',
                    templateUrl: 'admin-views/home/export.view.html'

                })
                .state('form.manage', {
                    url: '/admin',
                    templateUrl: 'admin-views/home/manage.view.html'

                })
                .state('form.listuser', {
                    url: '/list',
                    templateUrl: 'admin-views/home/listuser.view.html'

                })
                .state('form.deleteuser', {
                    url: '/deleteuser',
                    controller: 'HomeController',
                    templateUrl: 'admin-views/home/deleteuser.view.html'

                })
                .state('/login', {
                    url: '/login',
                    controller: 'LoginController',
                    templateUrl: 'admin-views/login/login.view.html',
                    params: {'Message': null}
                })

                .state('/home', {
                    url: '/home',
                    templateUrl: 'admin-views/home/home.view.html'
                })
                .state('/register', {
                    url: '/home',
                    controller: 'RegisterController',
                    templateUrl: 'admin-views/register/register.view.html'

                })

                .state('/modify', {
                    url: '/home',
                    templateUrl: 'admin-views/modify/modify.view.html'
                })
                .state('form.search', {
                    url: '/search',
                    templateUrl: 'admin-views/home/home.view.html'

                })
                .state('form.modify', {
                    url: '/modify',
                    templateUrl: 'admin-views/home/modify.view.html'

                })
                .state('form.welcome', {
                    url: '/welcome',
                    controller: 'HomeController',
                    templateUrl: 'admin-views/home/welcome.view.html'

                })
                .state('/reset', {
                    url: '/login',
                    controller: 'ResetController',
                    templateUrl: 'admin-views/login/reset-pass.html'
                })
                .state('form.reports', {
                    url: '/reports',
                    templateUrl: 'admin-views/home/reports.view.html'
                })
                .state('form.barreports', {
                    url: '/barreports',
                    templateUrl: 'admin-views/home/barreports.view.html'
                })

                .state('form.template', {
                    url: '/template_manager',
                    controller: 'TemplateManagerController',
                    templateUrl: 'admin-views/template/template.view.html'

                })

                .state('form.email', {
                    url: '/email',
                    controller: 'TemplateManagerController',
                    templateUrl: 'admin-views/template/email.view.html'

                })

                .state('form.disclaimer', {
                    url: '/disclaimer',
                    controller: 'TemplateManagerController',
                    templateUrl: 'admin-views/template/disclaimer.view.html'

                })

                .state('form.thankyou', {
                    url: '/thankyou',
                    controller: 'TemplateManagerController',
                    templateUrl: 'admin-views/template/thankyou.view.html'

                })

                .state('form.templateques', {
                    url: '/template_manager',
                    controller: 'TemplateQuestionarieController',
                    templateUrl: 'admin-views/template/template.questionarie.html'

                })
                .state('form.imagupload', {
                    url: '/upload',
                    controller: 'imageuploadController',
                    templateUrl: 'admin-views/template/upload.view.html'

                })
                .state('form.preview', {
                    url: '/preview',
                    controller:'PreviewContentController',
                    templateUrl: 'admin-views/template/preview.view.html'

                })
            $urlRouterProvider.otherwise('/login');
        });


})();
