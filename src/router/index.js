import Vue from 'vue'
import Router from 'vue-router'
import staticRouter from './staticRouter'
Vue.use(Router)
const router = new Router({
  routes: staticRouter
})

export default router
