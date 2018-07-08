mainModule.controller('loginController', ['$scope','$window','$localStorage','$location', 'userService', 
    function($scope, $window, $localStorage, $location, userService) {

        $scope.korisnik = {};

        $scope.ulogujSe = function(e) {

            if (!$scope.korisnik.korisnickoIme || !$scope.korisnik.lozinka) {
                alert("Unesete sva polja za prijavu.");
            } else {

                userService.ulogujSe($scope.korisnik).then(
                    function (response){
                        $window.localStorage.setItem('token', response.data);
                        $location.path('/home');
                    },
                    function (error){
                        alert("Greska prilikom prijave.");
                    }
                );
            }
        }
    }
]);