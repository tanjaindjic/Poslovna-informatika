mainModule.controller('centerController', ['$scope', '$window', 'userService',
    function($scope, $window, userService){

        $scope.logovaniKorisnik = {};

        $scope.initCenter = function(){
            $scope.logovaniKorisnik = userService.parsirajToken();

            console.log($scope.logovaniKorisnik);
        }

        $scope.odjaviSe = function(){
            $window.localStorage.removeItem('token');
            $scope.logovaniKorisnik = {};
            $window.location.reload();
        }

    }
]);