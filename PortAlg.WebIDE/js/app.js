angular.module('app', [])
.controller('editor', function ($scope) {
    $scope.content = '# Ace Directive ';
})
.controller('compileController', ['$scope', '$http', function($scope, $http){
    $scope.messagerest = 'No REST!';
    console.log($scope.content);
    
    $scope.callCompiler = function(){
        
        var editor = ace.edit("aceEditor");        
        var code = editor.getSession().getValue();
        $scope.messagerest = "";
    $http.put('http://localhost:8080/PortAlg.WebService-1.0/PortAlgWS/compiler',code,
    { headers: { "Content-Type": "text/plain"} })
    .then(function successMethod(response){
        $scope.messagerest = response.data;
        console.log(response);
    },
    function errorMethod(response){
        $scope.messagerest = response.data;
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
}])
.directive('ace', ['$timeout', function ($timeout) {

    var resizeEditor = function (editor, elem) {
        var lineHeight = editor.renderer.lineHeight;
        var rows = editor.getSession().getLength();

            $(elem).height("100%");
            $(elem).width("100%");
        editor.resize();
    };

    return {
        restrict: 'A',
        require: '?ngModel',
        scope: true,
        link: function (scope, elem, attrs, ngModel) {
            var node = elem[0];

            var editor = ace.edit(node);

            editor.setTheme('ace/theme/clouds_midnight');

            editor.getSession().setMode("ace/mode/csharp");

            // data binding to ngModel
            ngModel.$render = function () {
                console.log(ngModel.$viewValue);
                editor.setValue(ngModel.$viewValue);
                resizeEditor(editor, elem);
            };
            
            scope.$watch
        }
    };
}]);