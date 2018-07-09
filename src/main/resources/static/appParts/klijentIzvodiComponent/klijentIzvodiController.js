mainModule.controller('klijentIzvodiController', ['$scope', '$window', 'userService','$location', '$http','$timeout',
    function($scope, $window, userService, $location, $http, $timeout){

        $scope.logovaniKorisnik = {};
        $scope.nalozi = [];
        $scope.odabranRacun;

        var setNalozi = function(){
            var i = 0;
            for(i = 0; i < $scope.sviNalozi.length; i++)
                if($scope.sviNalozi[i].racunNalogodavca==$scope.odabranRacun || $scope.sviNalozi[i].racunPrimaoca==$scope.odabranRacun)
                    $scope.nalozi.push($scope.sviNalozi[i]);

        }


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

                $scope.sviNalozi = response.data;
                $scope.sviNalozi.sort(function compare(a, b) {
                  var dateA = new Date(a.datumPrijema);
                  var dateB = new Date(b.datumPrijema);
                  return dateB - dateA;
                });
                $scope.odabranRacun = $scope.klijent.racuni[0].brojRacuna;
                setNalozi();

                console.log($scope.sviNalozi)

            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });
        }


        $scope.initKlijent();

        $scope.odabirRacuna = function(odabranRacun){
            $scope.odabranRacun = odabranRacun;
            $scope.nalozi = [];
            setNalozi();
        }

        $scope.profil = function(){
            var tempUser = parsirajToken();
            if(tempUser.uloga === 'KLIJENT'){
                $window.location('/klijent');
            }else{
                 $window.location('/home');
            }
        }

        $scope.sve = function(){
            var i = 0;
            for( i = 0; i < $scope.nalozi.length; i++){
                document.getElementById($scope.nalozi[i].id).style.display = 'table-row';
            }
        }

        $scope.samoUplate = function(){
            var checkBox = document.getElementById("upl");
            if (checkBox.checked == true){
                var i = 0;
                for( i = 0; i < $scope.nalozi.length; i++){
                    if($scope.nalozi[i].racunPrimaoca == $scope.odabranRacun)
                        document.getElementById($scope.nalozi[i].id).style.display = 'table-row';
                    else document.getElementById($scope.nalozi[i].id).style.display = 'none';
                }
            }else {
                var i = 0;
                for( i = 0; i < $scope.nalozi.length; i++){
                    document.getElementById($scope.nalozi[i].id).style.display = 'table-row';
                }
            }
            $timeout(function(){ $scope.$apply(); }, 150);

        }

        $scope.samoIsplate = function(){
            var checkBox = document.getElementById("ispl");
            if (checkBox.checked == true){
                var i = 0;
                for( i = 0; i < $scope.nalozi.length; i++){
                    if($scope.nalozi[i].racunNalogodavca == $scope.odabranRacun)
                        document.getElementById($scope.nalozi[i].id).style.display = 'table-row';
                    else document.getElementById($scope.nalozi[i].id).style.display = 'none';
                }
            }else{
                var i = 0;
                for( i = 0; i < $scope.nalozi.length; i++){
                    document.getElementById($scope.nalozi[i].id).style.display = 'table-row';
                }
            }
            $timeout(function(){ $scope.$apply(); }, 150);
        }
    }
]);