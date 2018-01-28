mainModule.controller('mc1', function($scope, $window, $location, $http, $localStorage ) {
	
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
	$scope.brojPB = 0;
	
	$scope.init = function() {
		
		if($localStorage.tipPB == 'B'){
		
			$http({
				method : 'GET',
				url : 'http://localhost:8081/bioskopi-pozorista.com/app/bioskopi'
			}).success(function(data) {
				$scope.pozBios = data;
				console.log($scope.pozBios);
			}).error(function(data) {
				alert("Greska!");
			});	
			
		}else{
			
			$http({
				method : 'GET',
				url : 'http://localhost:8081/bioskopi-pozorista.com/app/pozorista'
			}).success(function(data) {
				$scope.pozBios = data;
				console.log($scope.pozBios);
			}).error(function(data) {
				alert("Greska!");
			});	
		}
	
	}
	
});
