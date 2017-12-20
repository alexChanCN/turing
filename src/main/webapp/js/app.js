var myApp = angular.module('myApp', ['ngRoute', 'ngAnimate', 'ngFileUpload']);

function myAppRouteConfig($routeProvider, $httpProvider) {
    $routeProvider.when('/index', {
        //controller: IndexCtrl,
        //templateUrl: 'goodslist.html'
    }).when('/quiz/list', {
        controller: quizListCtrl,
        templateUrl: 'module/quiz/list.html'
    }).when('/quiz/edit', {
        controller: quizEditCtrl,
        templateUrl: 'module/quiz/edit.html'
    }).when('/feedback/content', {
        controller: feedbackListCtrl,
        templateUrl: 'module/feedback/content.html'
    }).when('/feedback/edit', {
        controller: feedbackEditCtrl,
        templateUrl: 'module/feedback/edit.html'
    }).when('/student/list', {
        controller: studentListCtrl,
        templateUrl: 'module/student/list.html'
    }).when('/student/e', {
        controller: eEditCtrl,
        templateUrl: 'module/student/e.html'
    }).when('/student/s', {
        controller: sEditCtrl,
        templateUrl: 'module/student/s.html'
    }).when('/checkin', {
        controller: checkinCtrl,
        templateUrl: 'module/checkin/checkin.html'
    }).when('/task', {
        controller: taskListCtrl,
        templateUrl: 'module/task/tab.html'
    }).when('/task/content', {
        controller: answerListCtrl,
        templateUrl: 'module/task/content.html'
    }).when('/answer', {
        controller: studentTaskCtrl,
        templateUrl: 'module/answer/tab.html'
    }).when('/answer/edit', {
        controller: answerEditCtrl,
        templateUrl: 'module/answer/edit.html'
    }).when('/answer/upfile',{
        controller:fileupCtrl,
        templateUrl:'module/answer/upfile.html'
    }).when('/task/filecontent',{
        controller:adminfilelistCtrl,
        templateUrl:'module/task/filecontent.html'
    }).when('/task/selectlesson',{
        controller:selectlessonCtrl,
        templateUrl:'module/task/selectlesson.html'
    });
    //管理模块
    /* .when('/profilemgnt', {
     controller : ProfileMgntCtrl,
     templateUrl : 'modules/mgnt/profilemgnt/list.html'
     })*/
    // .otherwise({
    // redirectTo:'/'
    // });
    //$httpProvider.responseInterceptors.push('errorHttpInterceptor');
}
myApp.config(myAppRouteConfig);