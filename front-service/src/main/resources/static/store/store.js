angular.module('marketFrontendApp').controller('storeController', function ($scope, $http, $location, $rootScope, $localStorage) {

    const contextPath = 'http://localhost:5555/core/';
    const cartContextPath = 'http://localhost:8084/cart/';


    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + 'api/v1/products',
            method: 'GET',
            params: {
                p : pageIndex,
                minPrice: $scope.filter ? $scope.filter.minPrice : null,
                maxPrice: $scope.filter ? $scope.filter.maxPrice : null,
                partName: $scope.filter ? $scope.filter.partName : null,
                category: $scope.filter ? $scope.filter.category : null
            }
        }).then(function (response) {
            console.log(response)
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndex(1, $scope.productsPage.totalPages)
        });
    }



    $scope.generatePagesIndex = function (startPage, endPage){
        let array = [];
        for (let i = startPage; i < endPage + 1; i++){
            array.push(i);
        }
        return array;
    };

    $scope.getProductById = function (productId){
        $http.get(contextPath + 'api/v1/products/' + productId)
            .then(function (response){
                alert(response.data.title)

        });
    }

    $scope.addToCart = function (productId){
        $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.webmarketGuestCartId + '/add/' + productId)
            .then(function (response){
            });
    }

    $scope.navToCreateOrderPage = function () {
        $location.path('/orders');
    }

    $scope.loadProducts();
});