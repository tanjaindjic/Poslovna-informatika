mainModule.service('exportService', [ '$http', '$window','$localStorage', 
    function($http, $window, $localStorage) {

        var rootUrl = 'http://localhost:8096/';

        this.napraviRacun = function(racun){

            var req = {
                method: 'POST',
                url: rootUrl+'racun/saveRacun',
                data: racun,
                headers: {'token' : $window.localStorage.getItem('token')}
            }
            return $http(req);
        }

        

    }
]);