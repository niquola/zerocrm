app = require('./module')

app.factory 'jsonRequestIntr', ()->
  request: (config)-> config

app.service 'Order', ($http)->
  find: (id)->
    $http.get("/orders/#{id}")
  all: (params)->
    $http.get('/orders',params)
  create: (data)->
    $http.post('/orders',data)
