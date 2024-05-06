angular.module('marketFrontendApp').controller('editProductController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.prepareProductForUpdate = function (){
        $http.get(contextPath + 'api/v1/products/' + $routeParams.productId)
            .then(function successCallback(response) {
                $scope.updated_product = response.data;
            }, function failureCallback(response){
                console.log(response);
                alert(response.data.messages);
        });
    }

    $scope.updateProduct = function (){
        $http.put(contextPath + 'api/v1/admin', $scope.updated_product)
            .then(function successCallback(response){
                $scope.updated_product = null;
                alert('Продукт обновлен');
            },function failureCallback(response){
                alert(response.data.messages)
            });
    }

    $scope.prepareProductForUpdate();
});
