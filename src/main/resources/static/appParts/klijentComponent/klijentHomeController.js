mainModule.controller('klijentHomeController', ['$scope', '$window', 'userService','$location', '$http',
    function($scope, $window, userService, $location, $http){

        $scope.logovaniKorisnik = {};
        $scope.oznakaBanke;
        $scope.racunPt2;
        $scope.racunPt3;


        $scope.initKlijent = function(){
            $scope.logovaniKorisnik = userService.parsirajToken();

            console.log($scope.logovaniKorisnik)

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

        //SAMO ZA TEST
        $scope.kliring = function(){
             $http({
                            method: 'GET',
                            url: 'http://localhost:8096/dnevnoStanje/kliring',
                            headers: {'token' : $window.localStorage.getItem('token')}

                        }).then(function successCallback(response) {

                            alert("PROSAO KLIRING")


                        }, function errorCallback(response) {
                            alert("Error occured check connection");

                        });
        }

        $scope.showDelete = function(id){
            document.getElementById(id).style.visibility='visible';
        }
        $scope.cancelDelete = function(id){
            document.getElementById(id).style.visibility='hidden';
        }
        $scope.deleteRacun = function(racun){
            if(this.oznakaBanke==null || this.oznakaBanke=="" || this.oznakaBanke.trim().length!=3){
                alert("Racun primaoca nije ispravan.1 " + this.oznakaBanke)
                return;
             }
             if(this.racunPt2==null || this.racunPt2=="" || this.racunPt2.trim().length<1 || this.racunPt2.trim().length>13){
                alert("Racun primaoca nije ispravan.2" + this.racunPt2)
                return;
             }
             if(this.racunPt3==null || this.racunPt3=="" || this.racunPt3.trim().length!=2){
                alert("Racun primaoca nije ispravan.3" + this.racunPt3)
                return;
             }

            this.ceoRacun = this.oznakaBanke + this.racunPt2 + this.racunPt3;
            if(!(/^\d+$/.test(this.ceoRacun))){
                alert("Racun primaoca se mora sastojati samo iz brojeva.")
                return;
            }

            $http({
                method: 'POST',
                url: 'http://localhost:8096/racun/gasenje/'+this.ceoRacun,
                headers: {'token' : $window.localStorage.getItem('token')},
                data: racun
            }).then(function successCallback(response) {

                $scope.initKlijent();


            }, function errorCallback(response) {
                alert("Error occured check connection");

            });
        }
    }
]);