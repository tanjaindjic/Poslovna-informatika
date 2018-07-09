mainModule.controller('upravljanjeRacunimaController', [ '$scope','$window','$localStorage','$location', 'sluzbenikService', 
    function($scope, $window, $localStorage, $location, sluzbenikService) {
    
        $scope.racuni = [];
        $scope.isPrenos = false;
        $scope.naRacun = {};
        $scope.pageNum = 1;
        $scope.maxPages = -1;
        $scope.tempRacun = {};

        $scope.initUpravljanjeRacunima = function(){
            $scope.dobaviRacune();
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

    }
]);