var app = angular.module('atms', [],function(){

});

app.controller('mainController', function($scope, mainFactory) {

    var $body = $("body");

    $scope.currentFilter = "city";
    $scope.filterValue = "";

    $scope.updateFilter = function(filter) {
        $scope.currentFilter = filter;
    };


    $body.addClass("loading");
    mainFactory.getAtms().then(function(atms){
        $body.removeClass("loading");
        $scope.atms = atms.data;
    });

    $scope.findAtms = function() {
        $body.addClass("loading");
        mainFactory.getAtmsByFilter($scope.currentFilter.toLowerCase(),$scope.filterValue.toLowerCase()).then(function(atms){
            $body.removeClass("loading");
            $scope.atms = atms.data;
        });
    }
});

app.factory('mainFactory', ["$http", function($http){

    //build a factory here to map to my camel REST api endpoints

    return {
        getAtms: function() {
            return $http.get('/atm/rest/atms');
        },
        getAtmsByFilter: function(filter,value) {
            return $http.get('/atm/rest/atms/' + filter + '/' + value);
        }
    }

}]);