mainModule.controller('klijentHomeController', ['$scope', '$window', 'userService','$location'
    function($scope, $window, userService, $location){

        $scope.logovaniKorisnik = {};

        $scope.initCenter = function(){
            $scope.logovaniKorisnik = userService.parsirajToken();
        }
        console.log($scope.logovaniKorisnikx)

          $http({
                method: 'GET',
                url: 'https://localhost:8096/klijent/'+$scope.logovaniKorisnik.id,
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
                if(response.data ==null)
                    $location.path('/login');

                $scope.user = response.data;

            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });



        $scope.odjaviSe = function(){
            $window.localStorage.removeItem('token');
            $scope.logovaniKorisnik = {};
            $window.location.reload();
        }

    }
]);