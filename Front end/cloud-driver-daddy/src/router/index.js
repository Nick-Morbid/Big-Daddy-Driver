import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
//要先导入store在能访问store中的state！！！
import store from "../store"


Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
// 重写了原型上的push方法，统一的处理了错误信息
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/uploader',
    name: 'Uploader',
    components:{
      "Uploader": () => import('../views/Uploader'),
      "MyFolder": () => import('../views/MyFolder'),
      "MyAlbum": () => import('../views/MyAlbum'),
      "MyFriends":()=>import('../views/MyFriends'),
      "MyShare":()=>import('../views/MyShare'),
      "Notification":()=>import('../views/Notification'),
      "RecycleBin":()=>import('../views/RecycleBin'),
      "Space":()=>import('../views/Space'),
      "AddFriends":()=>import('../views/AddFriends')
    },
    meta:{
      auto:"afterLogin"
    }
  },
  {
    path: '/myFriends',
    name: 'MyFriends',
    components:{
      "Uploader": () => import('../views/Uploader'),
      "MyFolder": () => import('../views/MyFolder'),
      "MyAlbum": () => import('../views/MyAlbum'),
      "MyFriends":()=>import('../views/MyFriends'),
      "MyShare":()=>import('../views/MyShare'),
      "Notification":()=>import('../views/Notification'),
      "RecycleBin":()=>import('../views/RecycleBin'),
      "Space":()=>import('../views/Space'),
      "AddFriends":()=>import('../views/AddFriends')
    },
    meta:{
      auto:"afterLogin"
    }
  },

  {
    path: '/addFriends',
    name: 'AddFriends',
    components:{
      "Uploader": () => import('../views/Uploader'),
      "MyFolder": () => import('../views/MyFolder'),
      "MyAlbum": () => import('../views/MyAlbum'),
      "MyFriends":()=>import('../views/MyFriends'),
      "MyShare":()=>import('../views/MyShare'),
      "Notification":()=>import('../views/Notification'),
      "RecycleBin":()=>import('../views/RecycleBin'),
      "Space":()=>import('../views/Space'),
      "AddFriends":()=>import('../views/AddFriends')
    },
    meta:{
      auto:"afterLogin"
    }
  },
  {
    path: '/notification',
    name: 'Notification',
    components:{
      "Uploader": () => import('../views/Uploader'),
      "MyFolder": () => import('../views/MyFolder'),
      "MyAlbum": () => import('../views/MyAlbum'),
      "MyFriends":()=>import('../views/MyFriends'),
      "MyShare":()=>import('../views/MyShare'),
      "Notification":()=>import('../views/Notification'),
      "RecycleBin":()=>import('../views/RecycleBin'),
      "Space":()=>import('../views/Space'),
      "AddFriends":()=>import('../views/AddFriends')
    },
    meta:{
      auto:"afterLogin"
    }
  },
  {
    path: '/recycleBin',
    name: 'RecycleBin',
    components:{
      "Uploader": () => import('../views/Uploader'),
      "MyFolder": () => import('../views/MyFolder'),
      "MyAlbum": () => import('../views/MyAlbum'),
      "MyFriends":()=>import('../views/MyFriends'),
      "MyShare":()=>import('../views/MyShare'),
      "Notification":()=>import('../views/Notification'),
      "RecycleBin":()=>import('../views/RecycleBin'),
      "Space":()=>import('../views/Space'),
      "AddFriends":()=>import('../views/AddFriends')
    },
    meta:{
      auto:"afterLogin"
    }
  },
  {
    path: '/myShare',
    name: 'MyShare',
    components:{
      "Uploader": () => import('../views/Uploader'),
      "MyFolder": () => import('../views/MyFolder'),
      "MyAlbum": () => import('../views/MyAlbum'),
      "MyFriends":()=>import('../views/MyFriends'),
      "MyShare":()=>import('../views/MyShare'),
      "Notification":()=>import('../views/Notification'),
      "RecycleBin":()=>import('../views/RecycleBin'),
      "Space":()=>import('../views/Space'),
      "AddFriends":()=>import('../views/AddFriends')
    },
    meta:{
      auto:"afterLogin"
    }
  },
  // {
  //   path: '/space',
  //   name: 'Space',
  //   components:{
  //     "Uploader": () => import('../views/Uploader'),
  //     "MyFolder": () => import('../views/MyFolder'),
  //     "MyAlbum": () => import('../views/MyAlbum'),
  //     "MyFriends":()=>import('../views/MyFriends'),
  //     "MyShare":()=>import('../views/MyShare'),
  //     "Notification":()=>import('../views/Notification'),
  //     "RecycleBin":()=>import('../views/RecycleBin'),
  //     "Space":()=>import('../views/Space'),
  //     "AddFriends":()=>import('../views/AddFriends')
  //   },
  //   meta:{
  //     auto:"afterLogin"
  //   }
  // },
  // {
  //   path: '/myAlbum',
  //   name: 'MyAlbum',
  //   components:{
  //     "Uploader": () => import('../views/Uploader'),
  //     "MyFolder": () => import('../views/MyFolder'),
  //     "MyAlbum": () => import('../views/MyAlbum'),
  //     "MyFriends":()=>import('../views/MyFriends'),
  //     "MyShare":()=>import('../views/MyShare'),
  //     "Notification":()=>import('../views/Notification'),
  //     "RecycleBin":()=>import('../views/RecycleBin'),
  //     "Space":()=>import('../views/Space'),
  //     "AddFriends":()=>import('../views/AddFriends')
  //   },
  //   meta:{
  //     auto:"afterLogin"
  //   }
  // },
  {
    path: '/myFolder',
    name: 'MyFolder',
    components:{
      "Uploader": () => import('../views/Uploader'),
      "MyFolder": () => import('../views/MyFolder'),
      "MyAlbum": () => import('../views/MyAlbum'),
      "MyFriends":()=>import('../views/MyFriends'),
      "MyShare":()=>import('../views/MyShare'),
      "Notification":()=>import('../views/Notification'),
      "RecycleBin":()=>import('../views/RecycleBin'),
      "Space":()=>import('../views/Space'),
      "AddFriends":()=>import('../views/AddFriends')
    },
    meta:{
      auto:"afterLogin"
    }
  },
  {
    path: '/clapboard',
    name: 'Clapboard',
    components: {
      "Login": () => import('../views/Login'),
      "Register": () => import('../views/Register'),
      "Prompt": () => import('../views/Prompt'),
      "Clapboard": () => import('../views/Clapboard')
    }
  },
  {
    path: '/prompt',
    name: 'Prompt',
    components: {
      "Login": () => import('../views/Login'),
      "Register":() => import('../views/Register'),
      "Prompt":()=>import('../views/Prompt'),
      "Clapboard":()=>import('../views/Clapboard')
    }
  },
  {
    path: '/login',
    name: 'Login',
    components:{
      "Login": () => import('../views/Login'),
      "Register":() => import('../views/Register'),
      "Prompt":()=>import('../views/Prompt'),
      "Clapboard":()=>import('../views/Clapboard')
    },
    meta:{
      auto:"beforeLogin"
    }
  },
  {
    path: '/register',
    name: 'Register',
    components:{
      "Login": () => import('../views/Login'),
      "Register":() => import('../views/Register'),
      "Prompt":()=>import('../views/Prompt'),
      "Clapboard":()=>import('../views/Clapboard')
    },
    meta:{
      auto:"beforeLogin"
    }
  },
  {
    path: '/clapboard',
    name: 'Clapboard',
    components: {
      "Login": () => import('../views/Login'),
      "Register":() => import('../views/Register'),
      "Prompt":()=>import('../views/Prompt'),
      "Clapboard":()=>import('../views/Clapboard')
    }
  }
]

const router = new VueRouter({
  routes
})

router.beforeEach((to,from,next)=>{
  if (to.path=='/'&&store.state.name==''){
    next("/login")
  }
  if (to.path=='/clapboard'){
    next(false)
  }
  else {
    next();
  }
})

export default router


