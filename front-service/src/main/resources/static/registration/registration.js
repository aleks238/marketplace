angular.module('marketFrontendApp').controller('registrationController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:5555/core/';

    $scope.registration = function (){

            $http.post(contextPath + 'api/v1/registration', $scope.registrationData)
                .then(function successCallback(response) {
                    $scope.registrationData = null;
                    alert('Регистрация успешна');
                    $location.path('/welcome');
                }, function failureCallback(response) {
                    alert(response.data.message);
                });
        }

});