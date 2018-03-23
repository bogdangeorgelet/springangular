'use strict';

/**
 * @ngdoc overview
 * @name yapp
 * @description
 * # yapp
 *
 * Main module of the application.
 */
var states = [
        { name: 'base', state: { abstract: true, url: '', templateUrl: 'views/base.html', data: {text: "Base", visible: false } } },
        { name: 'login', state: { url: '/login', parent: 'base', templateUrl: 'views/login.html', controller: 'LoginCtrl', data: {text: "Login", visible: false } } },
        { name: 'register', state: { url: '/register', parent: 'base', templateUrl: 'views/register.html', data: {text: "Login", visible: false } } },
        { name: 'dashboard', state: { url: '/dashboard', parent: 'base', templateUrl: 'views/dashboard.html', controller: 'DashboardCtrl', data: {text: "Dashboard", visible: false } } },
        { name: 'overview', state: { url: '/overview', parent: 'dashboard', templateUrl: 'views/dashboard/overview.html', data: {text: "OVERVIEW", visible: true } } },
        { name: 'clients', state: { url: '/clients', parent: 'dashboard', templateUrl: 'views/dashboard/clients.html', controller:'ClientsCtrl', data: {text: "CLIENTS", visible: true } } },
        { name: 'reviews', state: { url: '/reviews', parent: 'dashboard', templateUrl: 'views/dashboard/reviews.html', controller:'ReviewsCtrl',data: {text: "REVIEWS", visible: true } } },
        { name: 'addClient', state: { url: '/addClient', parent: 'base', templateUrl: 'views/dashboard/addClient.html', controller:'ClientsCtrl', data: {text: "Add Client", visible: false } } },
        { name: 'addReview', state: { url: '/addReview', parent: 'base', templateUrl: 'views/dashboard/addReview.html', controller:'ReviewsCtrl', data: {text: "Add Review", visible: false } } },
        { name: 'logout', state: { url: '/login', data: {text: "LOGOUT", visible: true }} }
    ];
   
angular.module('yapp', [
                'ui.router',
                'snap',
                'ngAnimate'
            ])
        .config(function($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.when('/dashboard', '/dashboard/overview');
            $urlRouterProvider.otherwise('/login');
            
            angular.forEach(states, function (state) {
                $stateProvider.state(state.name, state.state);
            });
        });
