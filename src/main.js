// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '../node_modules/element-ui/lib/theme-chalk/index.css'
import qs from 'qs'
import VueScroll from 'vuescroll'
import 'vuescroll/dist/vuescroll.css'
import VueParticles from 'vue-particles'
import axios from 'axios'
import moments from'moment'

window.echarts = require('echarts');
Vue.config.productionTip = false
Vue.use(ElementUI)
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded'
axios.defaults.baseURL = 'http://192.168.43.169:8080/'
axios.defaults.withCredentials = true
Vue.prototype.$http = axios
Vue.prototype.$axios = axios

Vue.prototype.$moment = moments

Vue.use(VueParticles)
Vue.use(VueScroll, {ops: {bar: {background: '#C0C4CC'}}})
Vue.prototype.$qs = qs

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  axios,
  components: { App },
  template: '<App/>'
})
