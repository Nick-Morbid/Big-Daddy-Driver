<template>
  <div :style="isActive?'':'pointerEvents:none'">
    <div :class="mainclass">
      <div class="logo">
        <label :class="title">Register</label>
      </div>
      <div :class="panelclass">
        <input class="baseinput" style="left: 25%;top: 20%" v-model="name" placeholder="请输入用户名..."><br>
        <input class="baseinput" style="left: 25%;top: 25%" type="password" v-model="password" placeholder="请输入密码..."><br>
        <input class="baseinput" style="left: 25%;top: 30%" type="password" v-model="repassword" placeholder="请重复输入密码..."><br>
        <button class="basebtn1" style="left: 30%; top:35%" @click="register">注册</button>
        <button class="basebtn2" style="left: 40%; top: 35%;" @click="reset">重置</button>
      </div>
    </div>
  </div>
  </template>

<script>
export default {
  name: "Register",
  data(){
    return{
      isActive:false,
      path:'/register',
      mainclass:'container1',
      panelclass:'panel1',
      title:"title1",
      name:"",
      password:"",
      repassword:""
    }
  },   mounted() {
    if (this.$route.path==this.$data.path){
      this.$data.mainclass="container2"
      this.$data.panelclass="panel2"
      this.$data.title="title2"
      this.isActive=true
    }
    else {
      this.$data.mainclass = "container1"
      this.$data.panelclass = "panel1"
      this.$data.title="title1"
      this.isActive=false
    }
  },
  watch:{
    $route(to,from){
      if (to.path==this.$data.path){
        this.$data.mainclass="container2"
        this.$data.panelclass="panel2"
        this.$data.title="title2"
        this.isActive=true
      }
      else {
        this.$data.mainclass = "container1"
        this.$data.panelclass = "panel1"
        this.$data.title="title1"
        this.isActive=false
        this.reset()
      }
    }
  },
  methods:{
    register:function (){
      var _this = this
      if (_this.name==""){
        alert("请输入用户名")
        return
      }else if (_this.password==""){
        alert("请输入密码")
        return
      }else if (_this.repassword==""){
        alert("请再次输入密码")
        return
      }else if (_this.password!=_this.repassword){
        alert("两次输入的密码不一致")
        _this.repassword=""
        return
      }
      axios({
        method:"POST",
        url:"http://localhost:8090/user/register",
        data:{
          name:_this.name,
          password:_this.password
        },
        headers:{
          'Content-Type': 'application/json'
        }
      }).then(function (resp){
        alert(resp.data.msg)
        if (resp.data.code==400){
          _this.name=""
        }else if (resp.data.code==200){
          _this.reset()
        }
      })
    },
    reset:function (){
      this.name=""
      this.password=""
      this.repassword=""
    }
  }
}
</script>

<style scoped src="../css/ViewBase.css">
</style>