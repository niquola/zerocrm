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

  rp.when '/orders/:id',
    templateUrl: '/views/order.html'
    name: 'orders'
    controller: 'show_order_ctrl'

  rp.otherwise
    templateUrl: '/views/404.html'

activate = (name)->
  sitemap.main.forEach (x)->
    if x.name == name
      x.active = true
    else
      delete x.active

app.run ($rootScope, $location, $http)->
  $rootScope.strings = strings
  $rootScope.sitemap = sitemap
  $rootScope.$on  "$routeChangeStart", (event, next, current)->
    activate(next.name)

  $http.get('/user')
    .success (data)->
      sitemap.user.push(label: data.login)
      console.log(sitemap.user)
    .error ()-> $location.path('/')

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

app.controller 'index_ctrl', ($rootScope, $scope, $http, $location)->
  $scope.signin = ()->
    $http.post('/signin', $scope.session)
      .success (data)->
        $rootScope.user = data
        $location.path('/orders')

app.controller 'new_order_ctrl', ($scope, $location, Order)->
  $scope.order = {}

  $scope.create = ()->
    Order.create($scope.order)
      .success ()->
        $location.path('/orders')

app.controller 'show_order_ctrl', ($scope, $routeParams, $location, Order)->
  Order.find($routeParams.id)
    .success (data)->
      $scope.order = data

app.controller 'orders_ctrl', ($scope, Order)->
  Order.all().success (data)->
    $scope.orders = data
