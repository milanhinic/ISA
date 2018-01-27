var mainModule = angular.module('mainModule', [ 'ngCookies', 'ngRoute', 'ngStorage' ]);

mainModule.config(function($routeProvider) {

	$routeProvider.when('/', {
		templateUrl : 'app/pages/osnova.html'
	}).when('/registracija', {
		templateUrl : 'app/pages/registracija.html'
	}).when('/logovanje', {
		templateUrl : 'app/pages/login.html'
	}).when('/bioskopi', {
		templateUrl : 'app/pages/bioskopi.html'
	}).when('/pozorista', {
		templateUrl : 'app/pages/pozorista.html'
	}).otherwise({
		templateUrl : 'app/pages/osnova.html'
	});

});