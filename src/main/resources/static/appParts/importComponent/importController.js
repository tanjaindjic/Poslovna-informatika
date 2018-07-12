mainModule.controller('importController', [ '$scope','$window','$localStorage','$location', 'exportService',
    function($scope, $window, $localStorage, $location, exportService) {

        $scope.putanja = "";

        $scope.importInit = function(){

        }

        $(document).on('change', '.btn-file :file', function() {
            var input = $(this),
                numFiles = input.get(0).files ? input.get(0).files.length : 1,
                label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
            input.trigger('fileselect', [numFiles, label]);
        });
          
        $(document).ready( function() {
            $('.btn-file :file').on('fileselect', function(event, numFiles, label) {
                  
                var input = $(this).parents('.input-group').find(':text'),
                    log = numFiles > 1 ? numFiles + ' files selected' : label;
                  
                if( input.length ) {
                    input.val(log);
                } else {
                    if( log ) alert(log);
                }
                  
            });
        });

        $scope.setFile = function(element) {
            $scope.$apply(function($scope) {
                var chosenFile = element.files[0];
                if(chosenFile == undefined || chosenFile == null){
                    $scope.putanja = "";
                }else{
                    $scope.putanja = chosenFile.name;
                }
            });
        };

        $scope.importuj = function(){
            
            exportService.importujNalog($scope.putanja).then( 
                function(response){
                    alert('Vas import je uspesno zabelezen.');
                },
                function(error){
                    alert('Vas import NIJE uspesno zabelezen, potencijalni problem u formatu ili stanju na racunu uplatioca.');
                }
            );

        }

    }
]);