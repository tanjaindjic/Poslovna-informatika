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
                        var tempUser = userService.parsirajToken();

                        if(tempUser.uloga === 'KLIJENT'){
                            [].forEach.call(document.querySelectorAll('.isKlijent'), function (el) {
                              el.style.display = 'block';
                            });

                        }else{
                            [].forEach.call(document.querySelectorAll('.isKlijent'), function (el) {
                              el.style.display = 'none';
                            });

                        }
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