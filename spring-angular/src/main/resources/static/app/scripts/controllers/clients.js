'use strict';

/**
 * @ngdoc function
 * @name yapp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of yapp
 */
var app = angular.module('yapp');
    app.controller('ClientsCtrl', function($scope, $location) {

        $scope.clients = [ {
            "name": "client 1",
            "cnp": "cnp 1",
            "address": "address 1"
        }];

        $scope.addClient = function () {
            $scope.clients.push({
                "name": $scope.clientName,
                "cnp": $scope.cnp,
                "address": $scope.address
            });
            $scope.clientName = "";
            $scope.cnp = "";
            $scope.address = "";
            console.log($scope.clients);

            $location.path('/dashboard/clients');

            }

    });
