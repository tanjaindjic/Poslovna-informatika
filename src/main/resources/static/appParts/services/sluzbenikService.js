mainModule.service('sluzbenikService', [ '$http', '$window','$localStorage', 
    function($http, $window, $localStorage) {

        var rootUrl = 'http://localhost:8096/';

        this.dobaviPoTipu = function(tip){
            var req = {
                method: 'GET',
                url: rootUrl+'klijent/getKlijentsByTip/'+tip
            }
            return $http(req);
        }

        this.dobaviValute = function(){
            var req = {
                method: 'GET',
                url: rootUrl+'valuta/getAll'
            }
            return $http(req);
        }

        this.napraviRacun = function(racun){

            var req = {
                method: 'POST',
                url: rootUrl+'racun/saveRacun',
                data: racun,
                headers: {'token' : $window.localStorage.getItem('token')}
            }
            return $http(req);
        }

        this.vratiRacuneStranica = function(pagenum){

            var req = {
                method: 'GET',
                url: rootUrl+'racun/getRacuniNum/'+pagenum,
                headers: {'token' : $window.localStorage.getItem('token')}
            }
            return $http(req);
        }

        this.deaktivirajRacun = function(racun, naRacun){

            console.log(racun)

            var req = {
                method: 'PUT',
                url: rootUrl+'racun/deaktiviraj/'+naRacun,
                data: racun, 
                headers: {'token' : $window.localStorage.getItem('token')}
            }
            return $http(req);
        }

        this.aktivirajRacun = function(racun){

            var req = {
                method: 'PUT',
                url: rootUrl+'racun/aktiviraj',
                data: racun, 
                headers: {'token' : $window.localStorage.getItem('token')}
            }
            return $http(req);
        }

    }
]);