angular.module('marketFrontendApp').controller('mainPageController', function ($scope, $http) {
    const contextPath = 'http://localhost:5555/core/';


    $scope.getMainPage = function (productId){
        $http.get(contextPath + 'api/v1/products/' + productId)
            .then(function (response){
                console.log(response);
                $scope.discountProducts = response.data;
            });
    }


});