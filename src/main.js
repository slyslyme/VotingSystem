// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import qs from 'qs'
import moment from 'moment'
Vue.use(ElementUI)
window.echarts = require('echarts')

axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
axios.defaults.baseURL = 'http://192.168.43.169:8080/'
axios.defaults.withCredentials = true

Vue.prototype.$axios = axios
Vue.prototype.$qs = qs
Vue.prototype.$http = axios
Vue.prototype.$moment = moment
Vue.config.productionTip = false



/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  axios,
  moment,
  components: { App },
  template: '<App/>',

})
