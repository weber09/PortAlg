angular.module('app', ['ui.ace'])
.controller('compileController', ['$scope', '$http', function($scope, $http){    
    $scope.callCompiler = function(){
        console.log("vamo la");
        var editor = ace.edit("aceEditor");        
        var code = editor.getSession().getValue();
        var consoleEdt = ace.edit("consoleAceEditor");
        consoleEdt.getSession().setValue("");
        $scope.messagerest = "";
    $http.put('http://localhost:8080/PortAlg.WebService-1.0/PortAlgWS/compiler',code,
    { headers: { "Content-Type": "text/plain"} })
    .then(function successMethod(response){
        var consoleEdt = ace.edit("consoleAceEditor");
        consoleEdt.getSession().setValue(response.data);
        console.log(response);
    },
    function errorMethod(response){
        var console = ace.edit("consoleAceEditor");
        consoleEdt.getSession().setValue(response.data);
        console.log(response);
    })};
    
    $scope.getCompiler = function(){
        $http.get('http://localhost:8080/PortAlg.WebService-1.0/PortAlgWS/compiler')
        .then(function successMethod(response){
           $scope.messagegetrest = response;
           console.log(response); 
        },
        function errorMethod(response){
            $scope.messagegetrest = response;
            console.log(response);
        });
    }
}]);