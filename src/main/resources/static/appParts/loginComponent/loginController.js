mainModule.controller('loginController', function($scope, $window) {

    $scope.korisnik = {};

    $scope.ulogujSe = function(e) {

        if (!$scope.korisnik.korisnickoIme || !$scope.korisnik.lozinka) {
            alert("Molimo vas da unesete sva polja za prijavu.");
        } else {

            console.log($scope.korisnik)
        }
    }
});