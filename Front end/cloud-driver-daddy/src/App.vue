<template>
    <div id="page">
      <button id="btn" v-show="$store.state.name==''?false:true" @click="loginout">登出</button>
      <div id="app">
        <!-- 通过给html元素的 class 属性绑定 vue 中的属性值，得到样式的动态绑定，在这里如果 temp=true，class就生效 -->
        <div v-for="(item,index) in div" :class="item.class" @click="change(index)"><router-view :name="item.name"></router-view> </div>
      </div>
    </div>
</template>

<style>
.leftdiv1 {
  /*width: 180px;*/
  /*height: 600px;*/
  /*position: absolute;*/
  /*left: 180px;*/
  width: 360px;
  height: 600px;
  position: absolute;
  left: 0px;
  top: 60px;
  z-index: -1;
  /*background-color: darkgrey;*/
  background-color: rgb(64,59,59);
  transition: 1s;
  /*pointer-events: auto;*/
  border-radius: 15px;
  box-sizing: border-box;
}

.leftdiv2 {
  width: 360px;
  height: 600px;
  position: absolute;
  left: 0px;
  top: 60px;
  z-index: 0;
  /*background-color: darkgrey;*/
  background-color: rgb(64,59,59);
  transition: 1s;
  /*pointer-events: auto;*/
  border-radius: 15px;
  box-sizing: border-box;
}

.leftdiv2:hover,
.rightdiv2:hover {
  /*background-color: lightgray;*/
  background-color: rgb(92, 86, 86);
}

.centerdiv {
  width: 420px;
  height: 700px;
  left: 30.5%;
  top: 0px;
  z-index: 10;
  position: absolute;
  /*background-color: gray;*/
  background-color: rgb(48,48,48);
  transition: 1s;
  margin: auto;
  /*pointer-events: none;*/
  border-radius: 15px;
  box-sizing: border-box;
}

/*.centerdiv:hover {*/
/*  background-color: lightgray;*/
/*}*/

.rightdiv1 {
  /*width: 180px;*/
  /*height: 600px;*/
  /*position: absolute;*/
  /*left: 720px;*/
  width: 360px;
  height: 600px;
  position: absolute;
  left: 66.6%;
  top: 60px;
  z-index: -1;
  /*background-color: darkgrey;*/
  background-color: rgb(64,59,59);
  transition: 1s;
  /*pointer-events: auto;*/
  border-radius: 15px;
  box-sizing: border-box;
}

.rightdiv2 {
  width: 360px;
  height: 600px;
  position: absolute;
  left: 66.6%;
  top: 60px;
  z-index: 0;
  /*background-color: darkgrey;*/
  background-color: rgb(64,59,59);
  transition: 1s;
  /*pointer-events: auto;*/
  border-radius: 15px;
  box-sizing: border-box;
}
#app {
  top: 2%;
  width: 1080px;
  height: 700px;
  position: relative;
  margin: auto;
}
#page{
  height: 100%;
  width: 100%;
  position: fixed;
  top:0px;
  left:0px;
  right:0px;
  bottom:0px;
  background-color: black;
}
#btn{
  height: 40px;
  width: 60px;
  border-radius: 15px;
  border: 3px solid lightgrey;
  position: absolute;
  right: 5%;
  top: 2%;
  /*border: 3px solid #6ec2ff;*/
  background-color: rgb(64,59,59);
  color: white;
  font-size: 16px;
  font-family: "Microsoft soft";
  transition: 1s;
}
#btn:hover{
  background-color: rgb(92, 86, 86);
}
#btn:active{
  background-color: rgb(48,48,48);
}
@media screen and (max-width: 1080px){
  #app{
    width: 480px;
  }
}
@media screen and (max-width: 540px){
  #app{
    width: 440px;
  }
}
</style>
<script>
import Register from "@/views/Register";
import Login from "@/views/Login";
import Uploader from "@/views/Uploader";
import MyFolder from "@/views/MyFolder";
import MyAlbum from "@/views/MyAlbum";
export default {
  components: {
    Register},
  data(){
    return{ // 数据
      isLogin: false,
      div: [{
        direction: -2,
        class: "leftdiv1 ",
        route:"/register",
        name:"Register"
      }, {
        direction: -1,
        class: "leftdiv2 ",
        route:"/login",
        name:"Login"
      }, {
        direction: 0,
        class: "centerdiv ",
        route: "/uploader",
        name:"Uploader"
      }, {
        direction: 1,
        class: "rightdiv2 ",
        route: "/myFolder",
        name:"MyFolder"
      }, {
        direction: 2,
        class: "rightdiv1 ",
        route: "/myAlbum",
        name:"MyAlbum"
      }]
    }
  },
  mounted() {
    let _this = this;
    setTimeout(function() {
      let i;
      for (i = 0; i < _this.$router.options.routes.length; i++) {
        if (_this.$router.options.routes[i].name==_this.$route.name){
          break;
        }
      }
      //说明已登录
      if (_this.$store.state.name!=''){
        if (i>=2){
          _this.div[0].name =_this.$router.options.routes[i-2].name
          _this.div[0].route = _this.$router.options.routes[i-2].path
        }else{
          _this.div[0].name =_this.$router.options.routes[i+5].name
          _this.div[0].route = _this.$router.options.routes[i+5].path
        }
        if (i>=1){
          _this.div[1].name = _this.$router.options.routes[i-1].name
          _this.div[1].route = _this.$router.options.routes[i-1].path
        }else {
          _this.div[1].name = _this.$router.options.routes[i+6].name
          _this.div[1].route = _this.$router.options.routes[i+6].path
        }
        _this.div[2].name = _this.$router.options.routes[(i)%7].name
        _this.div[2].route = _this.$router.options.routes[(i)%7].path
        _this.div[3].name = _this.$router.options.routes[(i+1)%7].name
        _this.div[3].route = _this.$router.options.routes[(i+1)%7].path
        _this.div[4].name = _this.$router.options.routes[(i+2)%7].name
        _this.div[4].route = _this.$router.options.routes[(i+2)%7].path
      }
      else {
        // console.log(1)
        _this.div[0].name=_this.$router.options.routes[(i-2>6)?i-2:i-2+5].name
        _this.div[0].route=_this.$router.options.routes[(i-2>6)?i-2:i-2+5].path
        _this.div[1].name=_this.$router.options.routes[(i-1>6)?i-1:i-1+5].name
        _this.div[1].route=_this.$router.options.routes[(i-1>6)?i-1:i-1+5].path
        _this.div[2].name=_this.$router.options.routes[i].name
        _this.div[2].route=_this.$router.options.routes[i].path
        _this.div[3].name=_this.$router.options.routes[(i+1<12)?i+1:i+1-5].name
        _this.div[3].route=_this.$router.options.routes[(i+1<12)?i+1:i+1-5].path
        _this.div[4].name=_this.$router.options.routes[(i+2<12)?i+2:i+2-5].name
        _this.div[4].route=_this.$router.options.routes[(i+2<12)?i+2:i+2-5].path
      }
      console.log(_this.div)
    }, 100);
  },
  methods:{
    change: function(index) {
      if (this.$store.state.name!=''){
        let i;
        for (i = 0; i < this.$router.options.routes.length; i++) {
          if (this.$router.options.routes[i].name==this.$route.name){
            break;
          }
        }
        if (this.div[index].direction==-1){
          this.div[(index + 3) % 5].name = this.$router.options.routes[(i + 2) % 7].name
          this.div[(index + 3) % 5].route = this.$router.options.routes[(i + 2) % 7].path

        }else if (this.div[index].direction==1){
          this.div[(index + 2) % 5].name = this.$router.options.routes[(i - 2 + 7) % 7].name
          this.div[(index + 2) % 5].route = this.$router.options.routes[(i - 2 + 7) % 7].path
        }
      }
      if (!(this.$route.name=='Register'&&this.div[index].direction==1||this.$route.name=='Prompt'&&this.div[index].direction==-1)){
       this.div[(index - 1 + 5) % 5].class = "leftdiv2"
       this.div[(index - 1 + 5) % 5].direction = -1
       // 可以保证当中间页被点击时，不会有视图切换的改变
       this.div[index].class = "centerdiv"
       this.div[index].direction = 0;
       this.$router.push(this.div[index].route)
       this.div[(index + 1) % 5].class = "rightdiv2"
       this.div[(index + 1) % 5].direction = 1
       this.div[(index + 2) % 5].class = "rightdiv1"
       this.div[(index + 2) % 5].direction = 2
       this.div[(index + 3) % 5].class = "leftdiv1"
       this.div[(index + 3) % 5].direction = -2
     }
    },
    loginout:function (){
      this.$store.commit("loginout")
      alert("注销成功")
      this.$router.push("/login")
      axios({
        method:"GET",
        url:"http://localhost:8090/user/loginout"
      })
      setTimeout(()=>{
        window.location.reload()
      },100)
    },
    reload:function (){
      this.routerState = false;
      this.$nextTick(()=>{
        this.routerState = true;
      })
    }
  }
}

</script>