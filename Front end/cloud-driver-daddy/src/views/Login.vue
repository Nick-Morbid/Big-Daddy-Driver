<template>
  <div :style="isActive?'':'pointerEvents:none'">
    <div :class="mainclass">
      <div class="logo">
        <label :class="title">Login</label>
      </div>
      <div :class="panelclass">
        <input class="baseinput" style="left: 25%;top: 20%" v-model="name" placeholder="请输入用户名..."><br>
        <input class="baseinput" style="left: 25%;top: 25%" type="password" v-model="password" placeholder="请输入密码..."><br>
        <!--    <button class="basebtn1" style="right: 20%;top: 40%" >登录</button>-->
        <!--    <button class="basebtn2" style="right: 10% ;top: 40%">重置</button>-->
        <button class="basebtn1" style="left: 30%; top:30%" @click="login">登录</button>
        <button class="basebtn2" style="left: 40%; top: 30%;" @click="reset">重置</button>
      </div>
    </div>
  </div>

</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      isActive:false,
      path:'/login',
      mainclass:'container1',
      panelclass:'panel1',
      title:'title1',
      name:"",
      password:""
    }
  },
  mounted() {
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
        //调用methods中的方法的写法
        this.reset()
      }
    }
  },
  methods:{
    login:function (){
      var _this = this
      if (_this.name=="") {
        alert("请输入用户名")
        return
      }
      else if (_this.password=="") {
        alert("请输入密码")
        return
      }
      axios({
        method:"POST",
        url:"http://localhost:8090/user/login",
        data:{
          name:_this.name,
          password:_this.password
        },
        headers: {
          'Content-Type': 'application/json'
        }
      }).then(function (resp){
        alert(resp.data.msg)
        if (resp.data.code==200){
            _this.$store.commit("login",[resp.headers.authorization,_this.name])
            _this.$router.push("/uploader")
            //刷新界面，以保证样式生效
            setTimeout(()=>{
              window.location.reload()
            },50)
        }
      })
    },
    reset:function (){
      this.name=""
      this.password=""
    }
  }
}
</script>

<style scoped src="../css/ViewBase.css">
</style>
<style scoped>

</style>