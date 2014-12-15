app = require('./module')

app.factory 'jsonRequestIntr', ()->
  request: (config)->
    console.log(config)
    config

app.service 'Order', ($http)->
  create: (data)->
    $http.post('/orders',data)
      .success (args...)->
        console.log(args)

