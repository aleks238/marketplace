angular.module('marketFrontendApp').controller('createProductController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.createProduct = function (){
        if ($scope.new_product == null){
            alert('Форма не заполнена');
            return;
        }
        $http.post(contextPath + 'api/v1/admin', $scope.new_product)
            .then(function successCallback(response) {
                $scope.new_product = null;
                alert('Новый продукт создан');
            },function failureCallback(response){
                console.log(response);
                alert(response.data.messages)
            });
    }
});