mainModule.controller('upravljanjeRacunimaController', [ '$scope','$window','$localStorage','$location', 'sluzbenikService', 
    function($scope, $window, $localStorage, $location, sluzbenikService) {
    
        $scope.racuni = [];
        $scope.selRacun = -1;
        $scope.isPrenos = false;
        $scope.naRacun = null;
        $scope.pageNum = 1;
        $scope.maxPages = -1;

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

        $scope.selektujRed = function(idx){
            $scope.selRacun = idx;
        }

        $scope.deaktiviraj = function(idx){
            $scope.selRacun = idx;
            $scope.isPrenos = !$scope.isPrenos;
            $scope.naRacun = null;
        }

        $scope.potvrdiPrenos = function(){

            console.log($scope.naRacun);

        }

    }
]);