mainModule.service('exportService', [ '$http', '$window','$localStorage', 
    function($http, $window, $localStorage) {

        var rootUrl = 'http://localhost:8096/';

        this.eksportujNalog = function(idNaloga){

            var req = {
                method: 'GET',
                url: rootUrl+'impexp/exportAnalitikaIzvoda/'+idNaloga,
                headers: {'token' : $window.localStorage.getItem('token')}
            }
            return $http(req);
        }

        this.eksportujIzvestaj = function(idIzvestaja){

            var req = {
                method: 'GET',
                url: rootUrl+'impexp/exportIzvod/'+idIzvestaja,
                headers: {'token' : $window.localStorage.getItem('token')}
            }
            return $http(req);
        }

        this.importujNalog = function(putanja){

            var req = {
                method: 'POST',
                url: rootUrl+'impexp/importAnalitikaIzvoda',
                data: putanja,
                headers: {
                    'token' : $window.localStorage.getItem('token'), 
                    'Content-Type': 'text/plain'
                }
            }
            return $http(req);
        }

        this.eksportujZaDatume = function(datumOd, datumDo, racun){

            var theData = {
                "datumOd" : datumOd,
                "datumDo" : datumDo,
                "username": "",
                "brojRacuna" : racun
            }

            var req = {
                method: 'POST',
                url: rootUrl+'impexp/exportZaDatume',
                data: theData,
                headers: {'token' : $window.localStorage.getItem('token')}
            }
            return $http(req);
        }

        this.eksportujMedjubankarski = function(){
            
            var req = {
                method: 'GET',
                url: rootUrl+'impexp/exportMedjubankarski',
                headers: {'token' : $window.localStorage.getItem('token')}
            }
            return $http(req);
        }

    }
]);