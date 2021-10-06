<template>
  <div :style="isActive?'':'pointerEvents:none'">
    <div :class="mainclass">
      <div class="head">
        <label :class="headtitle">AddFriend</label>
      </div>
      <div :class="body">
        <input class="seatchinput" placeholder="请输入要搜索的用户名" v-model="searchcontent">
        <button class="funcbtn5" @click="searchUser">搜索</button>
        <div class="friendpanel" style="height: 80%;top: 5%">
          <img src="../images/emptyfile.jpg" v-if="userList==''?true:false" id="floderimg">

          <div v-for="item in userList" class="friend">
            <div class="friend-icon"></div>
            <label :class="friendName">{{item.name}}</label>
            <button class="basebtn1" style="width: 25%;height: 60%;position:absolute; top: 20%;right: 5%" @click="requestFriend(item.id)">添加好友</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FriendFiles from "@/components/FriendFiles";
import Notice from "@/components/Notice";
export default {
  name: "AddFriends",
  data(){
    return{
      isActive:false,
      path:"/addFriends",
      mainclass:"container1",
      body:"body1",
      headtitle:"headtitle1",
      friendName:"friend-name1",
      userList:[],
      searchcontent:""

    }
  },
  mounted() {
    if (this.$route.path==this.$data.path){
      this.$data.mainclass="container2"
      this.$data.body="body2"
      this.$data.headtitle="headtitle2"
      this.$data.friendName="friend-name2"
      this.isActive=true
      // this.$data.head="head2"
    }
    else {
      this.$data.mainclass="container1"
      this.$data.body="body1"
      this.$data.headtitle="headtitle1"
      this.$data.friendName="friend-name1"
      this.isActive=false
      // this.$data.head="head1"
    }
    this.getUserList();
  },
  watch: {
    $route(to, from) {
      if (to.path == this.$data.path) {
        this.$data.mainclass = "container2"
        this.$data.body = "body2"
        this.$data.headtitle = "headtitle2"
        this.$data.friendName="friend-name2"
        this.isActive=true
        // this.$data.head="head2"
      } else {
        this.$data.mainclass = "container1"
        this.$data.body = "body1"
        this.$data.headtitle = "headtitle1"
        this.$data.friendName="friend-name1"
        this.isActive=false
        // this.$data.head="head1"
      }
      this.getUserList();
    },
    //检测searchcontent值的变化
    searchcontent(newValue,oldValue){
      if (newValue==""){
        this.getUserList()
      }else {
        this.searchUser()
      }
    }
  },
  methods:{
    getUserList:function (){
      axios({
        method:"GET",
        url:"http://localhost:8090/user/getUserList"
      }).then(resp=>{
        if (resp.data.code == 200){
          this.userList = resp.data.result
        }else {
          console.log("获取用户列表失败")
        }
      })
    },
    searchUser:function (){
      var _this = this
      axios({
          method:"GET",
          url:"http://localhost:8090/user/searchUsers",
          params:{
            name:_this.searchcontent
          }
        }).then(resp=>{
          if (resp.data.code==200){
              _this.userList = resp.data.result
          }else {
            console.log("查询用户失败")
          }
      })
    },
    requestFriend:function (id){
        axios({
          method:"GET",
          url:"http://localhost:8090/friend/requestFriend",
          params:{
            id:id
          }
        }).then(resp=>{
          if (resp.data.code==200){
            alert("好友申请发送成功")
          }else {
            console.log("好友申请发送失败")
          }
        })
    }
  }
}

</script>

<style scoped src="../css/ViewBase.css">

</style>