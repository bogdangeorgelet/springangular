'use strict';

/**
 * @ngdoc function
 * @name yapp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of yapp
 */
angular.module('yapp', [])
    .controller('ClientsCtrl', ClientsCtrl);
   function ClientsCtrl() {

        this.clients = [
            {
                "name": "client 1",
                "cnp": "cnp nou 1 jbd",
                "address": "address 1"
            },
            {
                "name": "client 2",
                "cnp": "cnp nou 2jbd",
                "address": "address 2"
            }

        ];

       /* angular.forEach($state.get(), function (item) {

                $scope.clients.push({name: item.name, text: item.data.text});

        });*/
    });
