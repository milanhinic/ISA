mainModule.controller('mc1', function($scope, $window, $location, $http, $localStorage ) {
	$localStorage.rootUrl = 'http://localhost:8081/bioskopi-pozorista.com/';
	
	$scope.prikaziBioskope = function() {
		$localStorage.tipPB = 'B';
		$location.path('/bioskopi');	
	}
	
	$scope.prikaziPozorista = function() {
		$localStorage.tipPB = 'P';
		$location.path('/pozorista');	
	}
	
});

mainModule.controller('bioskopi1', function($scope, $window, $location, $http, $localStorage ) {

	$scope.pozBios = {};
	
	$scope.init = function() {
		
		if($localStorage.tipPB == 'B'){
		
			$http({
				method : 'POST',
				url : $localStorage.rootUrl+'app/bioskopi'
			}).success(function(data) {
				$scope.pozBios = data;
				console.log($scope.pozBios);
			}).error(function(data) {
				alert("Greska!");
			});	
			
		}else{
			
			$http({
				method : 'GET',
				url : $localStorage.rootUrl+'app/pozorista'
			}).success(function(data) {
				$scope.pozBios = data;
				console.log($scope.pozBios);
			}).error(function(data) {
				alert("Greska!");
			});	
		}
	
	}
	
});
