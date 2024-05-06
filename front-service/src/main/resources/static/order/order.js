angular.module('marketFrontendApp').controller('orderController', function ($scope, $http, $location, $rootScope, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.loadOrders = function (){
        if(!$rootScope.isUserLoggedIn()){
            alert('Необходимо войти в учетную запись');
            $location.path('/mainPage');
            return;
        }
        $http.get(contextPath + 'api/v1/orders')
            .then(function (response){
                $scope.myOrders = response.data;
            });
    }

    $scope.loadOrders();


});