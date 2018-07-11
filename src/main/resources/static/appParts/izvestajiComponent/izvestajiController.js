mainModule.controller('izvestajiController', ['$scope', '$window','$location', '$http','$timeout', 'userService',
      function($scope, $window, $location, $http, $timeout, userService){

        $scope.logovaniKorisnik = {};
        $scope.odabranRacun;



        $scope.initIzvestaji = function(){
            $scope.logovaniKorisnik = userService.parsirajToken();
            $scope.izvestaji={};
            $http({
                method: 'GET',
                url: 'http://localhost:8096/klijent/'+$scope.logovaniKorisnik.id,
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
                if(response.data ==null)
                    $location.path('/login');

                $scope.sluzbenik = response.data;

            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });
            $scope.racuni = [];
            $http({
                method: 'GET',
                url: 'http://localhost:8096/racun/svi',
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
                if(response.data ==null)
                    $location.path('/login');

                $scope.racuni = response.data;                


            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });
            $scope.banke = [];
            $http({
                method: 'GET',
                url: 'http://localhost:8096/banka/svi',
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
                if(response.data ==null)
                    $location.path('/login');

                $scope.banke = response.data;
                


            }, function errorCallback(response) {
                alert("Error occured check connection");
                $location.path('/home');
            });
        }


        $scope.initIzvestaji();

        

        $scope.profil = function(){
            var tempUser = parsirajToken();
            if(tempUser.uloga === 'KLIJENT'){
                $window.location('/klijent');
            }else{
                 $window.location('/home');
            }
        }
        

        var setDnevnaStanja = function(){
            var i = 0;
            for(i = 0; i < $scope.racuni.length; i++)
                if($scope.racuni[i].brojRacuna==$scope.odabranRacun){
                    $scope.dnevnaStanja = $scope.racuni[i].dnevnaStanja;
                    break;
                }
            console.log($scope.dnevnaStanja.length)
        }

        var setTrenutniRacun = function(){
            var i = 0;
            for(i = 0; i < $scope.racuni.length; i++)
                if($scope.racuni[i].brojRacuna==$scope.odabranRacun){
                    $scope.trenutniRacun = $scope.racuni[i];
                    break;
                }
                console.log($scope.trenutniRacun)
        }

        var setTrenutnoStanje = function(){
            $scope.trenutnoStanje = $scope.trenutniRacun.dnevnaStanja[$scope.trenutniRacun.dnevnaStanja.length-1];
        }

        var setRezervisanaSredstva = function(){
            $scope.rezervisanaSredstva = 0;
            var i;
            for(i=0; i < $scope.nalozi.length; i++){
                if($scope.nalozi[i].status=='E' && $scope.nalozi[i].racunNalogodavca == $scope.odabranRacun)
                    $scope.rezervisanaSredstva+=$scope.nalozi[i].iznos;

            }
        }
        
        $scope.odabirRacuna = function(odabranRacun){
            $scope.odabranRacun = odabranRacun;
            setTrenutniRacun();
            $scope.nalozi = [];
            setNalozi();
            $scope.dnevnaStanja = [];
            setDnevnaStanja();
            setTrenutnoStanje();
            setRezervisanaSredstva();
        }

       
        $scope.skiniIzvestaj = function(){
        	if($scope.izvestaj.datumOd=="" || $scope.izvestaj.datumDo=="" || $scope.odabranRacun==undefined)
        		return;
        	var data = {
        			"datumOd" : $scope.izvestaj.datumOd,
        			"datumDo" : $scope.izvestaj.datumDo,
        			"username": "",
        			"brojRacuna" : $scope.odabranRacun.brojRacuna
        	}
        	$http({
                method: 'POST',
                url: 'http://localhost:8096/izvestaj/izvodKlijenta',
                data: data,
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
            	alert("Uspesno napravljen izvod. Smesten u 'D:\\nalozi'");
            }, function errorCallback(response) {
            	alert("Greska u zahtevu!");
            });
        	
        }
        $scope.skiniIzvestajBanka = function(){
        	if($scope.odabranaBanka=={} || $scope.odabranaBanka==undefined)
        		return;
        	$http({
                method: 'GET',
                url: 'http://localhost:8096/izvestaj/izvodBanke/'+$scope.odabranaBanka.id,
                headers: {'token' : $window.localStorage.getItem('token')}
            }).then(function successCallback(response) {
            	alert("Uspesno napravljen izvod. Smesten u 'D:\\nalozi'");
            }, function errorCallback(response) {
            	alert("Greska u zahtevu!");
            });
        }
        
        $scope.setMinDatumDo = function(){
        	var today = new Date($scope.izvestaj.datumOd);
        	var dd = today.getDate();
        	var mm = today.getMonth()+1; //January is 0!
        	var yyyy = today.getFullYear();
        	 if(dd<10){
        	        dd='0'+dd
        	    } 
        	    if(mm<10){
        	        mm='0'+mm
        	    } 

        	$scope.minDatumDo = yyyy+'-'+mm+'-'+dd; 
        }

        
    }
]);