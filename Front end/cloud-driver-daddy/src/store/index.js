import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') ? localStorage.getItem('token') : '',
    name:localStorage.getItem('name') ? localStorage.getItem('name') : ''
  },
  mutations: {
      login(state,[token,name]){
        state.token = token
        state.name = name
        localStorage.setItem("token",token)
        localStorage.setItem("name",name)
      },
    loginout(state){
      state.token = ""
      state.name = ""
      localStorage.setItem("token","")
      localStorage.setItem("name","")
    }
  },
  actions: {
  },
  modules: {
  }
})
