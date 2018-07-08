mainModule.controller('kreirajRacunController', [ '$scope','$window','$localStorage','$location', 'sluzbenikService', 
    function($scope, $window, $localStorage, $location, sluzbenikService) {
    
        $scope.klijenti = [];
        $scope.valute = [];
        $scope.tipKlijenta = "P";
        $scope.selKlijent = -1;
        $scope.isKreiranje = false;
        $scope.racun = {};

        $scope.initKreirajRacun = function(){
            $scope.dobaviKlijente();
        }

        $scope.$watch('tipKlijenta', function(value) {
            $scope.selKlijent = -1;
            $scope.isKreiranje = false;
            $scope.racun = {};
            $scope.dobaviKlijente();
            $scope.dobaviValute();
        });

        $scope.dobaviKlijente = function(){
        
            sluzbenikService.dobaviPoTipu($scope.tipKlijenta).then(
                function (response){
                    $scope.klijenti = response.data;
                },
                function (error){
                    alert("Greska prilikom dobavljanja klijenata.");
                }
            );
        }

        $scope.dobaviValute = function(){
        
            sluzbenikService.dobaviValute().then(
                function (response){
                    $scope.valute = response.data;
                },
                function (error){
                    alert("Greska prilikom dobavljanja valuta.");
                }
            );
        }

        $scope.selektujRed = function(idx){
            $scope.selKlijent = idx;
        }

        $scope.otvoriKreiranje = function(){
            $scope.isKreiranje = !$scope.isKreiranje;
            $scope.racun = {};
        }

        $scope.potvrdiKreiranje = function(){
            
            $scope.racun.vlasnikId = $scope.selKlijent;

            sluzbenikService.napraviRacun($scope.racun).then(
                function (response){
                    if(response.data == false){
                        alert("Uneti broj racuna vec postoji u evidenciji.");
                    }else{
                        alert("Uspesno kreiranje novog racuna.");
                        $scope.isKreiranje = false;
                    }
                },
                function (error){
                    alert("Greska prilikom kreiranja racuna.");
                }
            );

        }

    }
]);