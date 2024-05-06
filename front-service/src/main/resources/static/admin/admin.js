angular.module('marketFrontendApp').controller('adminController', function ($scope, $http, $location) {

    const contextPath = 'http://localhost:8189/market/';

    $scope.loadProducts = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + 'api/v1/admin',
            method: 'GET',
            params: {
                productId: $scope.filter ? $scope.filter.productId : null,
                partName: $scope.filter ? $scope.filter.partName : null,
                minPrice: $scope.filter ? $scope.filter.minPrice : null,
                maxPrice: $scope.filter ? $scope.filter.maxPrice : null,
                p: pageIndex
            }
        }).then(function (response) {
            console.log(response)
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndex(1, $scope.productsPage.totalPages)
        },function failureCallback(response){
            alert("Нет доступа")
        });
    }

    $scope.generatePagesIndex = function (startPage, endPage){
        let array = [];
        for (let i = startPage; i < endPage + 1; i++){
            array.push(i);
        }
        return array;
    };

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + 'api/v1/admin/' + productId)
            .then(function (response) {
                console.log(response.data)
                $scope.loadProducts()
            });
    }

    $scope.navToEditProductPage = function (productId) {
        $location.path('/edit_product/' + productId);
    }

    $scope.navToCreateProductPage = function () {
        $location.path('/create_product');
    }

});