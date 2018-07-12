mainModule.controller('upravljanjeRacunimaController', [ '$scope','$window','$localStorage','$location', 'sluzbenikService', '$http',
    function($scope, $window, $localStorage, $location, sluzbenikService, $http) {
    
        $scope.racuni = [];
        $scope.isPrenos = false;
        $scope.naRacun = {};
        $scope.pageNum = 1;
        $scope.maxPages = -1;
        $scope.tempRacun = {};

        $scope.initUpravljanjeRacunima = function(){
            $scope.dobaviRacune();
            $scope.isUplata=false;
            $scope.isIsplata=true;
        }

        $scope.dobaviRacune = function(){
        
            sluzbenikService.vratiRacuneStranica($scope.pageNum).then(
                function (response){
                    $scope.racuni = response.data.content;
                    $scope.maxPages = response.data.totalPages;
                },
                function (error){
                    alert("Greska prilikom dobavljanja racuna.");
                }
            );
        }

        $scope.prev = function(){
            $scope.pageNum--;

            if($scope.pageNum <= 0){
                $scope.pageNum = 1;
            }

            $scope.dobaviRacune();
        }

        $scope.next = function(){
            $scope.pageNum++;

            if($scope.pageNum > $scope.maxPages){
                $scope.pageNum = $scope.maxPages;
            }

            $scope.dobaviRacune();
        }

        $scope.parsirajDatum = function(datum){
            return datum.slice(0, 10);
        }

        $scope.deaktiviraj = function(idx){
            $scope.isPrenos = !$scope.isPrenos;
            $scope.tempRacun = $scope.racuni[idx];
            $scope.naRacun = null;
        }

        $scope.aktiviraj = function(idx){
            $scope.tempRacun = $scope.racuni[idx];

            sluzbenikService.aktivirajRacun($scope.tempRacun).then(
                function (response){
                    $scope.tempRacun = {};
                    $scope.dobaviRacune();
                },
                function (error){
                    alert("Greska prilikom aktivacije racuna.");
                }
            );
        }

        $scope.potvrdiPrenos = function(naRacun){

            sluzbenikService.deaktivirajRacun($scope.tempRacun, naRacun).then(
                function (response){
                    $scope.tempRacun = {};
                    $scope.isPrenos = false;
                    $scope.dobaviRacune();
                },
                function (error){
                    alert("Greska prilikom deaktivacije racuna.");
                }
            );
            
        }
        $scope.izvrsiUplatu = function(racun){
        	$scope.isUplata=true;
        	$scope.isPrenos=false;
        	$scope.uplata = {};
        	$scope.uplata.racun = racun;
        	$scope.uplata.tekst = "";
        	$scope.uplata.hitno=false;
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/racun/brojRacuna/'+racun.brojRacuna,
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
            	$scope.uplata.klijent = response.data;
            	$scope.uplata.tekst = response.data.ime+" "+response.data.prezime+" "+response.data.adresa;
            }, function errorCallback(response) {
            });
        	
        }
        $scope.finalnaUplata = function(){
        	if($scope.uplata.date==undefined || $scope.uplata.hitno==undefined || $scope.uplata.modelOdobrenja==undefined ||
        			$scope.uplata.pozivNaBroj==undefined || $scope.uplata.svrhaPlacanja==undefined ||
        			$scope.uplata.date=="" || $scope.uplata.hitno=="" || $scope.uplata.modelOdobrenja=="" ||
        			$scope.uplata.pozivNaBroj=="" || $scope.uplata.svrhaPlacanja==""){
        		alert("potrebno uneti sve podatke u polja");
        		return;
        	}
        	var data = {
        			"nalogodavac": "",
        	        "svrhaPlacanja": $scope.uplata.svrhaPlacanja, 
        	        "primalac": $scope.uplata.klijent.ime+$scope.uplata.klijent.prezime,
        	        "datumPlacanja": $scope.uplata.date,
        	        "racunNalogodavca": "00000",
        	        "modelZaduzenja" : 0,
        	        "pozivNaBroj" : $scope.uplata.pozivNaBroj,
        	        "racunPrimaoca" : $scope.uplata.racun.brojRacuna,
        	        "modelOdobrenja" : $scope.uplata.modelOdobrenja,
        	        "hitno" : $scope.uplata.hitno,
        	        "iznos" : $scope.uplata.iznos,
        	        "klijentId" : 0
        	};
        	$http({
                method: 'POST',
                url: 'http://localhost:8096/analitikaIzvoda/uplata',
                data: data,
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
            	alert(response.data);
            	$scope.isUplata=false;
            	$scope.uplata = {};
            }, function errorCallback(response) {
            });
        }

    }
]);