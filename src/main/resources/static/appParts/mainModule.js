var mainModule = angular.module('mainModule', [ 'ui.router' ]);

mainModule.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider.state('home', {
        url: '/home',
        templateUrl : 'appParts/centerComponent/center.html'
    })
    .state('login', {
        url: '/login',
        templateUrl: 'appParts/loginComponent/login.html',
        controller : 'loginController'
    })

});