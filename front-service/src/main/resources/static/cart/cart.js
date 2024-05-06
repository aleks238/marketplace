angular.module('marketFrontendApp').controller('cartController', function ($scope, $rootScope, $http, $location, $localStorage) {

    const contextPath = 'http://localhost:5555/cart/';

    $scope.loadCart = function (){
        $http({
           url: contextPath + 'api/v1/cart/' + $localStorage.webmarketGuestCartId,
           method: 'GET'
        }).then(function (response){
           $scope.cart = response.data;
        });
    }

    $scope.clearCart = function (){
        $http.get(contextPath + 'api/v1/cart/' + $localStorage.webmarketGuestCartId + '/clear')
            .then(function (response){
                $scope.loadCart();
            });
    }

    $scope.removeItemFromCart = function (productId){
        $http.get(contextPath + 'api/v1/cart/remove/' + productId)
            .then(function (response){
                $scope.loadCart();
            });
    }

    $scope.createOrder = function (){
        if(!$rootScope.isUserLoggedIn()){
            alert('Для оформления заказа необходимо войти в учетную запись')
            return;
        }

        if ($scope.cart.items.length < 1){
            alert('Корзина пуста, добавьте хотя бы один товар')
            return;
        }


        $http({
            url: 'http://localhost:5555/core/api/v1/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response){
            $scope.loadCart();
            $scope.orderDetails = null;
            $location.path('/order')
        });
    }

    $scope.disabledCheckOut = function (){
        alert("Для оформления заказа необходимо войти в учетную запись")
    }


    $scope.loadCart();
});