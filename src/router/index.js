import Vue from "vue";
import Router from "vue-router";
import login from "@/views/login/login";
import register from "../views/login/register";
import changePassworld from "../views/user/changePassworld";
import first from "@/views/user/first";
import vote from "../views/user/vote";
import avote from "../views/user/avote";
import details from "../views/user/details";
Vue.use(Router);

export default new Router({
  routes: [
  {
    path: "/",
    name: "login",
    component: login
  },
    {
      path:"/register",
      name:"register",
      component:register
    },
    {
      path: "/login",
      name:"login",
      component:login
    },
    {
      path:'/changePassworld',
      name:"changePassword",
      component:changePassworld
    },
    {
      path:'/first',
      name:"first",
      component:first
    },
    {
      path:'/vote',
      name:"vote",
      component:vote
    },
    {
      path:'/avote',
      name:"avote",
      component:avote
    },
    {
      path:'/details',
      name:"details",
      component:details
    }
  ]
});
