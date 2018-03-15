module.exports = function(config) {
  'use strict';

  config.set({
    autoWatch: true,
    frameworks: ['jasmine'],
    files: [
      // bower:js
      '../bower_components/angular/angular.js',
      '../bower_components/angular-mocks/angular-mocks.js',
      '../bower_components/ui-router/release/angular-ui-router.js',
      '../bower_components/angular-animate/angular-animate.js',
      '../bower_components/snapjs/snap.js',
      '../bower_components/angular-snap/angular-snap.js',
      // endbower
      '../app/scripts/**/*.js',
      //'../test/mock/**/*.js',
      '../test/spec/**/*.js'
    ],

    exclude: [
    ],

    port: 8080,

    browsers: [
      'PhantomJS'
    ],

    // Which plugins to enable
    plugins: [
      'karma-phantomjs-launcher',
      'karma-jasmine'
    ],

    singleRun: false,

    colors: true,

    logLevel: config.LOG_INFO,

  });
};
