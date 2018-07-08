mainModule.service('sluzbenikService', [ '$http', '$window','$localStorage', 
    function($http, $window, $localStorage) {

        this.dobaviPoTipu = function(tip){
            var req = {
                method: 'GET',
                url: 'http://localhost:8096/klijent/getKlijentsByTip/'+tip
            }
            return $http(req);
        }

        this.dobaviValute = function(){
            var req = {
                method: 'GET',
                url: 'http://localhost:8096/valuta/getAll'
            }
            return $http(req);
        }

        this.napraviRacun = function(racun){

            var req = {
                method: 'POST',
                url: 'http://localhost:8096/racun/saveRacun',
                data: racun,
                headers: {'token' : $window.localStorage.getItem('token')}
            }
            return $http(req);
        }

    }
]);