var mainModule = angular.module('mainModule', [ 'ui.router', 'ngStorage', 'angular-jwt' ]);

mainModule.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/home');

    $stateProvider.state('home', {
        url: '/home',
        templateUrl : 'appParts/centerComponent/center.html',
    })
    .state('login', {
        url: '/login',
        templateUrl: 'appParts/loginComponent/login.html',
        controller : 'loginController'
    })
    .state('klijent', {
        url: '/klijent',
        templateUrl: 'appParts/klijentHomeComponent/klijentHome.html',
        controller : 'klijentHomeController'
    })
    .state('noviRacun', {
        url: '/noviRacun',
        templateUrl: 'appParts/kreirajRacunComponent/kreirajRacun.html',
        controller : 'kreirajRacunController'
    })

});