(function () {
    angular
        .module('marketFrontendApp', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/mainPage', {
                templateUrl: 'mainPage/mainPage.html',
                controller: 'mainPageController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/edit_product/:productId', {
                templateUrl: 'edit_product/edit_product.html',
                controller: 'editProductController'
            })
            .when('/create_product', {
                templateUrl: 'create_product/create_product.html',
                controller: 'createProductController'
            })
            .when('/admin', {
                templateUrl: 'admin/admin.html',
                controller: 'adminController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/order', {
                templateUrl: 'order/order.html',
                controller: 'orderController'
            })
            .when('/login', {
                templateUrl: 'login/login.html',
                controller: 'loginController'
            })
            .when('/searchPage', {
                templateUrl: 'searchPage/searchPage.html',
                controller: 'searchPageController'
            })
            .otherwise({
                redirectTo: '/mainPage'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.webmarketUser) {
            try {
                let jwt = $localStorage.webmarketUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    delete $localStorage.webmarketUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            if ($localStorage.webmarketUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webmarketUser.token;
            }
        }

        if(!$localStorage.webmarketGuestCartId){
            $http.get('http://localhost:5555/cart/api/v1/cart/generate')
                .then(function successCallback(response){
                    $localStorage.webmarketGuestCartId = response.data.value;
                });
        }
    }
}) ();

angular.module('marketFrontendApp').controller('indexController', function ($rootScope, $scope, $http, $localStorage, $location) {


    const contextPath = 'http://localhost:5555/';

    $scope.tryToAuth = function (){
        $http.post(contextPath + 'auth/api/v1/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webmarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $http.get(contextPath + 'cart/api/v1/cart/' + $localStorage.webmarketGuestCartId + '/merge')
                        .then(function successCallback (response){
                        });

                    $location.path('/mainPage')
                }
            }, function errorCallback (response){
            });
    };

    $scope.tryToLogout = function (){
        $scope.clearUser();
        if ($scope.user.username){
            $scope.user.username = null;
        }
        if ($scope.user.password){
            $scope.user.password = null;
        }
        $location.path('/mainPage')
    };

    $rootScope.clearUser = function (){
        delete $localStorage.webmarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function (){
        if ($localStorage.webmarketUser){
            return true;
        }else {
            return false;
        }
    };

    $rootScope.findProductByTitle = function (pageIndex = 1) {
        $http({
            url: contextPath + 'core/api/v1/products',
            method: 'GET',
            params: {
                p : pageIndex,
                partName: $scope.filter ? $scope.filter.partName : null
            }
        }).then(function (response) {
            $location.path('/searchPage')
            console.log(response)
            $rootScope.productsPageSearch = response.data;
            $rootScope.paginationArray = $scope.generatePagesIndex(1, $scope.productsPageSearch.totalPages)
            $rootScope.filter.partName = null;

        });
    }

    $rootScope.generatePagesIndex = function (startPage, endPage){
        let array = [];
        for (let i = startPage; i < endPage + 1; i++){
            array.push(i);
        }
        return array;
    };

});



