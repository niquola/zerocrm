require('file?name=jquery.js!../../bower_components/jquery/dist/jquery.min.js');
require('../../bower_components/angular/angular.js')
require('../../bower_components/angular-animate/angular-animate.js')
require('../../bower_components/angular-cookies/angular-cookies.js')
require('../../bower_components/ngFileReader/vendor/filereader.swf');
require('../../bower_components/ngFileReader/vendor/jquery.FileReader.js');
require('../../bower_components/ngFileReader/vendor/jquery.FileReader.position.ver.js');
require('../../bower_components/ngFileReader/vendor/swfobject.js');
require('../../bower_components/ngFileReader/src/ngFileReader.min.js');
require('../../bower_components/angular-route/angular-route.js')
require('../../bower_components/angular-sanitize/angular-sanitize.js')
require('../../bower_components/angular-formstamp/formstamp.js')
require('../../bower_components/angular-formstamp/formstamp.css')
require('../../bower_components/fhir.js/dist/ngFhir.js')

module.exports = angular.module 'app', [
  'ngAnimate'
  'ngCookies'
  'ngFileReader'
  'ngRoute'
  'ngSanitize'
  'formstamp'
  'ng-fhir'
]
