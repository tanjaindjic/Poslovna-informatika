mainModule.controller('centerController', ['$scope', '$window', '$location',  'userService',
    function($scope, $window, $location, userService){

        $scope.logovaniKorisnik = {};

        $scope.initCenter = function(){
            $scope.logovaniKorisnik = userService.parsirajToken();

            if($scope.logovaniKorisnik.uloga === 'KLIJENT')
                $scope.isKlijent = true;
            else $scope.isKlijent = false;
        }



        $scope.odjaviSe = function(){
            $window.localStorage.removeItem('token');
            $scope.logovaniKorisnik = {};
            $location.path('/home');
        }


        $scope.profil = function(){
            var tempUser = parsirajToken();
            if(tempUser.uloga === 'KLIJENT'){
                $window.location('/klijent');
            }else{
                 $window.location('/home');
            }
        }

    }
]);