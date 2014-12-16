app = require('./module')

capitalize = (s)->
  s && s[0].toUpperCase() + s.slice(1)

buildSiteMap = (x)->
  x.href ||= "#/#{x.name}"
  x.templateUrl ||= "/views/#{x.name}.html"
  x.controller ||= "#{x.name}_ctrl"
  x

module.exports = {
  user: []
  main: [
    {when: '/', name: 'index', label: 'Welcome', href: '#/'}
    {when: '/order/new', name: 'new_order', label: 'New order', href: '#/order/new'}
    {when: '/orders', name: 'orders', label: 'Orders', href: '#/orders'}
  ].map(buildSiteMap)
}
