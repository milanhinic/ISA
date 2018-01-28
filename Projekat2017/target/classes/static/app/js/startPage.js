var mainModule = angular.module('mainModule', [ 'ngRoute', 'ngStorage' ]);

mainModule.config(function($routeProvider) {

	$routeProvider.when('/', {
		templateUrl : 'app/pages/osnova.html'
	}).when('/registracija', {
		templateUrl : 'app/pages/registracija.html'
	}).when('/logovanje', {
		templateUrl : 'app/pages/login.html'
	}).when('/bioskopi', {
		templateUrl : 'app/pages/pozBio.html'
	}).when('/pozorista', {
		templateUrl : 'app/pages/pozBio.html'
	}).otherwise({
		redirectTo : '/'
	});

});