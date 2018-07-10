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

                setNalozi();


            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });
        }


        $scope.initKlijent();

        var setDnevnaStanja = function(){
            var i = 0;
            for(i = 0; i < $scope.klijent.racuni.length; i++)
                if($scope.klijent.racuni[i].brojRacuna==$scope.odabranRacun){
                    $scope.dnevnaStanja = $scope.klijent.racuni[i].dnevnaStanja;
                    break;
                }
            console.log($scope.dnevnaStanja.length)
        }

        var setTrenutniRacun = function(){
            var i = 0;
            for(i = 0; i < $scope.klijent.racuni.length; i++)
                if($scope.klijent.racuni[i].brojRacuna==$scope.odabranRacun){
                    $scope.trenutniRacun = $scope.klijent.racuni[i];
                    break;
                }
                console.log($scope.trenutniRacun)
        }

        var setTrenutnoStanje = function(){
            $scope.trenutnoStanje = $scope.trenutniRacun.dnevnaStanja[$scope.trenutniRacun.dnevnaStanja.length-1];
        }

        var setRezervisanaSredstva = function(){
            $scope.rezervisanaSredstva = 0;
            var i;
            for(i=0; i < $scope.nalozi.length; i++){
                if($scope.nalozi[i].status=='E' && $scope.nalozi[i].racunNalogodavca == $scope.odabranRacun)
                    $scope.rezervisanaSredstva+=$scope.nalozi[i].iznos;

            }
        }

        $scope.odabirRacuna = function(odabranRacun){
            $scope.odabranRacun = odabranRacun;
            setTrenutniRacun();
            $scope.nalozi = [];
            setNalozi();
            $scope.dnevnaStanja = [];
            setDnevnaStanja();
            setTrenutnoStanje();
            setRezervisanaSredstva();


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
                document.getElementById($scope.nalozi[i].id).style.display = 'table';
            }
        }

        $scope.samoUplate = function(){
            var checkBox = document.getElementById("upl");
            if (checkBox.checked == true){
                var i = 0;
                for( i = 0; i < $scope.nalozi.length; i++){
                    if($scope.nalozi[i].racunPrimaoca == $scope.odabranRacun)
                        document.getElementById($scope.nalozi[i].id).style.display = 'table';
                    else document.getElementById($scope.nalozi[i].id).style.display = 'none';
                }
            }else {
                var i = 0;
                for( i = 0; i < $scope.nalozi.length; i++){
                    document.getElementById($scope.nalozi[i].id).style.display = 'table';
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
                        document.getElementById($scope.nalozi[i].id).style.display = 'table';
                    else document.getElementById($scope.nalozi[i].id).style.display = 'none';
                }
            }else{
                var i = 0;
                for( i = 0; i < $scope.nalozi.length; i++){
                    document.getElementById($scope.nalozi[i].id).style.display = 'table';
                }
            }
            $timeout(function(){ $scope.$apply(); }, 150);
        }
    }
]);