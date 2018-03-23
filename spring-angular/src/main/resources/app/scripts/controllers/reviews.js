'use strict';

/**
 * @ngdoc function
 * @name yapp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of yapp
 */
var app = angular.module('yapp');
app.controller('ReviewsCtrl', function($scope, $location) {

    $scope.reviews = [{
        "clientName": "client 1",
        "text": "blabla",
        "value": "5"
    } ];

    $scope.addReview = function () {
        $scope.reviews.push({
            "clientName": $scope.clientName,
            "text": $scope.text,
        });

        $scope.clientName = "";
        $scope.text = "";

        console.log($scope.reviews);

        $location.path('/dashboard/reviews');

    }

});