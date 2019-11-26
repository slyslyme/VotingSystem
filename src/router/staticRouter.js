/* 静态页面路由 */
const staticRouter = [
  {
    path: '/',
    redirect: '/index'
  }, {
    path: '/login',
    name: '登录',
    component: () => import('@/views/login/AppLogin')
  }, {
    path: '/register',
    name: '注册',
    component: () => import('@/views/login/AppRegister')
  }, {
    path: '/index',
    name: '首页',
    component: () => import('@/components/HelloWorld')
  }
]

export default staticRouter
