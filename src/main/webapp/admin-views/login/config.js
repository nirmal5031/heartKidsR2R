
var loginApp = angular.module('loginApp');
/************************************** Login Form Error Messages *************************************/
loginApp.value("validateUserErrorMessages", "Invalid Login");
/************************************** Service URL *************************************/
loginApp.constant("AUTHENTICATION_URL", "oauth/token");
loginApp.constant("LOGIN_URL", "survey/login");
//loginApp.constant("MENU_URL", "/adminauth/menu");