import Vue from 'vue'
import Router from 'vue-router'
import Welcome from '@/components/Welcome'
import UserMain from '@/components/pages/UserMain'
import UserInfo from '@/components/pages/UserInfo'

import AdminMain from '@/components/adminPages/AdminMain'
import AdminInfo from '@/components/adminPages/AdminInfo'
import AddQuestion from '@/components/adminPages/AddQuestion'
import QuestionAdmin from '@/components/adminPages/QuestionAdmin'

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Welcome',
      component: Welcome,
    },
    {
      path: '/userInfo',
      name: 'UserMain',
      component: UserMain,
      children:[{
        path:'/',
        name:'userInfo',
        components:{
          content:UserInfo
        }
      }]
    },
    {
      path: '/admin/adminInfo',
      name: 'AdminMain',
      component: AdminMain,
      children:[{
        path:'/',
        name:'adminInfo',
        components:{
          content:AdminInfo
        }
      }]
    },
    {
      path: '/admin/addQuestion',
      name: 'AdminMain',
      component: AdminMain,
      children:[{
        path:'/',
        name:'addQuestion',
        components:{
          content:AddQuestion
        }
      }]
    },
    {
      path: '/admin/questionAdmin',
      name: 'AdminMain',
      component: AdminMain,
      children:[{
        path:'/',
        name:'questionAdmin',
        components:{
          content:QuestionAdmin
        }
      }]
    },
  ]
})
