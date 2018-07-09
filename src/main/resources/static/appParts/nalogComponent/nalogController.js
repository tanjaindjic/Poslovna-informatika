mainModule.controller('nalogController', ['$scope', '$window', 'userService','$location', '$http','$timeout',
    function($scope, $window, userService, $location, $http, $timeout){

        $scope.logovaniKorisnik = {};
        $scope.nalozi = [];
        $scope.odabranRacun;
        $scope.date;
        $scope.odabranaValuta;
        $scope.oznakaBanke;
        $scope.racunPt2;
        $scope.racunPt3;
        $scope.modelOdobrenja;
        $scope.pozivNaBroj;
        $scope.hitno;
        $scope.iznos;
        $scope.nalogodavac;
        $scope.primalac;
        $scope.currentDate = new Date();



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
                url: 'http://localhost:8096/valuta/getAll',
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
                if(response.data !=null)
                    $scope.valute = response.data;

                console.log($scope.valute)

            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });

        }


        $scope.initKlijent();

        $scope.odabirRacuna = function(odabranRacun){
            $scope.odabranRacun = odabranRacun;
            $scope.odabranRacunVal = odabranRacun.split(",")[0];
            var val = odabranRacun.split(", ").pop();
            $scope.odabranaValuta = val;
            document.getElementById("valuta").value = val;

        }

        $scope.profil = function(){
            var tempUser = parsirajToken();
            if(tempUser.uloga === 'KLIJENT'){
                $window.location('/klijent');
            }else{
                 $window.location('/home');
            }
        }

        $scope.odabranDatum = function(){

            $scope.currentTime = new Date();
            var sad = $scope.currentTime/1000;
            var tren = new Date(sad*1000);
            var epoch_date = $scope.date/1000;
            var a = new Date(epoch_date*1000);
            // Hours part from the timestamp
            var year = a.getFullYear();
            var month = a.getMonth()+1;
            var date = a.getDate();

            if(tren.getFullYear()>year || tren.getMonth()+1>month || tren.getDate()>date) {
                console.log("picked past date, no reservations allowed")
                $scope.message="Izabrani datum nije validan.";
                return;
            }

            $scope.datum = a;
            console.log($scope.datum)
        }

        $scope.izvrsi = function(){

            var date = new Date($('#projDate').val());
            day = date.getDate();
            month = date.getMonth() + 1;
            year = date.getFullYear();

            if(date < $scope.currentDate){
                alert("Datum nije validan.")
            }
            console.log("klijent id: " + $scope.klijent.id + "korisnikID: " + $scope.logovaniKorisnik.id)

            var data = {
                "nalogodavac": $scope.nalogodavac,
                "svrhaPlacanja": $scope.svrhaPlacanja,
                "primalac":$scope.primalac,
                "datumPrijema": date,
                "racunNalogodavca": $scope.odabranRacunVal,
                "modelZaduzenja" : $scope.modelOdobrenja,
                "pozivNaBroj": $scope.pozivNaBroj,
                "racunPrimaoca" : $scope.oznakaBanke + $scope.racunPt2 + $scope.racunPt3,
                "modelOdobrenja" : $scope.modelOdobrenja,
                "hitno": document.getElementById("hitno").checked,
                "iznos": $scope.iznos,
                "klijentId": $scope.klijent.id
            };


            $http({
                method: 'POST',
                url: 'http://localhost:8096/analitikaIzvoda',
                headers: {'token' : $window.localStorage.getItem('token')},
                data: data
            }).then(function successCallback(response) {

                $location.path("/racuni")

            }, function errorCallback(response) {
                $location.path("/racuni")
            });
        }
    }
]);