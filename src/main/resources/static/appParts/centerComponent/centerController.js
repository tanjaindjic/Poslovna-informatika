mainModule.controller('centerController', ['$scope', '$window', 'userService',
    function($scope, $window, userService){

        $scope.logovaniKorisnik = {};

        $scope.initCenter = function(){
            $scope.logovaniKorisnik = userService.parsirajToken();
        }

        $scope.odjaviSe = function(){
            $window.localStorage.removeItem('token');
            $scope.logovaniKorisnik = {};
            $window.location.reload();
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