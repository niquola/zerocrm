app = require('./module')
require('file?name=index.html!../index.html')
require('../less/app.less')

require('./views')
sitemap = require('./sitemap')
require('./data')


strings =
  brand: 'Zerocrm'


app.config ($routeProvider, $httpProvider) ->
  rp = $routeProvider

  $httpProvider.interceptors.push('jsonRequestIntr')

  mkRoute = (acc, x)->
    acc.when(x.when, x)

  rp = sitemap.main.reduce mkRoute, rp

  rp.otherwise
    templateUrl: '/views/404.html'

activate = (name)->
  sitemap.main.forEach (x)->
    if x.name == name
      x.active = true
    else
      delete x.active

app.run ($rootScope, $location)->
  $rootScope.strings = strings
  $rootScope.sitemap = sitemap
  $rootScope.$on  "$routeChangeStart", (event, next, current)->
    activate(next.name)
  $rootScope.$watch 'progress', (v)->
    return unless v && v.success
    $rootScope.loading = 'Loading'
    delete $rootScope.error
    v.success (vv, status, _, req)->
       delete $rootScope.loading
     .error (vv, status, _, req)->
       console.error(arguments)
       $rootScope.error = vv || "Server error #{status} while loading:  #{req.url}"
       delete $rootScope.loading

window.app = app

app.controller 'index_ctrl', ($scope, $http)->
  $http.get('/data').success (data)->
    $scope.data = data

app.controller 'new_order_ctrl', ($scope, Order)->
  $scope.order = {
    nested: {a: [1,2,3]}
  }

  $scope.create = ()->
    Order.create($scope.order)

app.controller 'orders_ctrl', ($scope, $http)->
  $http.get('/data').success (data)->
    $scope.data = data
