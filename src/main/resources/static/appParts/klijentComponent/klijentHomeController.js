mainModule.controller('klijentHomeController', ['$scope', '$window', 'userService','$location', '$http',
    function($scope, $window, userService, $location, $http){

        $scope.logovaniKorisnik = {};

        $scope.initKlijent = function(){
            $scope.logovaniKorisnik = userService.parsirajToken();

            console.log($scope.logovaniKorisnik)

            $http({
                method: 'GET',
                url: 'http://localhost:8096/klijent/'+$scope.logovaniKorisnik.id,
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
                if(response.data ==null)
                    $location.path('/login');

                $scope.klijent = response.data;

            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });
        }

        $scope.initKlijent();

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