import TheLayout from '@/views/admin/layout/theLayout'
import Home from '@/views/admin/adminFirst'
import ModVote from '@/views/admin/voteSys/modVote'
import AddVote from '@/views/admin/voteSys/addVote'
import ManageUser from "@/views/admin/userSys/ManageUser"
import ManageVote from '@/views/admin/voteSys/manageVote'
import LookVote from '@/views/admin/voteSys/lookVote'
import AddUser from '@/views/admin/userSys/addUser'
import ModUser from '@/views/admin/userSys/modUser'
import Login from '@/views/admin/account/login'
import VoteMassege from '@/views/admin/voteSys/voteMassege'
import ChangePassword from '@/views/admin/account/changePassword'
/* 静态页面路由 */
const staticRouter = [
  {
    path: '/',
    name: '登录',
    component:Login
  },{
    path: '/account/changePassword',
    name: '修改密码',
    component:ChangePassword
  },
  {
    path: '/',
    component: TheLayout,
    menu: true,              //菜单目录要添加此值，login等非菜单目录不需要
    children: [              //一级菜单，children[0]即是链接，要指明名字、图标、组件
      {
        path: '/adminFirst',
        name: '首页',
        component: Home
      },
    ]
  },
  {
    path: '/',
    component: TheLayout,
    menu: true,
    children: [
      {
        path: '/userSys/ManageUser',
        name: '用户管理',
        component: ManageUser
      }
    ]
  },{
    path: '/',
    component: TheLayout,
    menu: true,
    icon: 'el-icon-tickets',
    children: [
      {
        path: '/voteSys/ManageVote',
        name: '投票管理',
        component: ManageVote
      }
    ]
  },{
    path: '/',
    component: TheLayout,
    menu: true,
    icon: 'el-icon-tickets',
    children: [
      {
        path: '/voteSys/lookVote',
        name: '投票查询',
        component: LookVote
      }
    ]
  },{
    path: '/',
    component: TheLayout,
    children: [
      {
        path: '/userSys/addUser',
        name: '添加用户',
        component: AddUser
      },
      {
        path: '/voteSys/addVote',
        name: '发起投票',
        component: AddVote
      },
      {
        path: '/voteSys/modVote',
        name: '修改投票',
        component: ModVote
      },{
        path: '/userSys/modUser',
        name: '修改用户',
        component: ModUser
      },
      {
        path: '/voteSys/voteMassege',
        name: '投票详情',
        component: VoteMassege
      }

    ]
  }
]

export default staticRouter
