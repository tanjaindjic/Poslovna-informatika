mainModule.controller('klijentIzvodiController', ['$scope', '$window', 'userService','$location', '$http',
    function($scope, $window, userService, $location, $http){

        $scope.logovaniKorisnik = {};
        $scope.odabranRacun = "";
        $scope.
        $scope.initKlijent = function(){
            $scope.logovaniKorisnik = userService.parsirajToken();

            $http({
                method: 'GET',
                url: 'http://localhost:8096/klijent/'+$scope.logovaniKorisnik.id,
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
                if(response.data ==null)
                    $location.path('/login');

                $scope.klijent = response.data;
                console.log($scope.klijent)

            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });

            $http({
                method: 'GET',
                url: 'http://localhost:8096/analitikaIzvoda/'+$scope.logovaniKorisnik.id,
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
                if(response.data ==null)
                    $location.path('/login');

                $scope.nalozi = response.data;
                console.log($scope.nalozi.length)

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