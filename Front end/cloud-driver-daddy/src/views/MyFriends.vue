<template>
  <div :style="isActive?'':'pointerEvents:none'">
    <FriendFiles ref="FriendFiles"></FriendFiles>
    <Notice ref="Notice" @children="parent"></Notice>
    <div :class="mainclass">
      <div class="head">
        <label :class="headtitle">FriendList</label>
      </div>
      <div :class="body">
        <div class="friendpanel">
          <div v-for="item in friendList" class="friend">
            <div class="friend-icon"></div>
            <label :class="friendName">{{item.name}}</label>
            <button class="basebtn1" style="width: 25%;height: 60%;position:absolute; top: 20%;right: 24%" @click="getFriendFiles(item.id)">好友文件</button>
            <button class="basebtn2" style="width: 20%;height: 60%;position:absolute; top: 20%;right: 2%" @click="deleteFriend(item.id)">删除</button>
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
  name: "MyFriends",
  components:{
    Notice,
    FriendFiles
  },
  data(){
    return{
      isActive:false,
      path:"/myFriends",
      mainclass:"container1",
      body:"body1",
      headtitle:"headtitle1",
      friendName:"friend-name1",
      friendList:[]
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
    this.getFriendList();
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
      this.getFriendList();
    },
  },
  methods:{
    getFriendList:function (){
      axios({
        method:"GET",
        url:"http://localhost:8090/friend/getFriends"
      }).then(resp=>{
        if (resp.data.code == 200){
          this.friendList = resp.data.result
          console.log(this.friendList)
        }else {
          console.log("获取好友列表失败")
        }
      })
    },
    getFriendFiles:function (id){
      this.$refs.FriendFiles.showDialog(id)
    },
    deleteFriend:function (id){
      this.$refs.Notice.showDialog("你确定要删除好友吗？",id)
    },
    parent:function (obj,id){
      if (obj){
        axios({
          method:"GET",
          url:"http://localhost:8090/friend/deleteFriend",
          params:{
            id:id
          }
        }).then(resp=>{
          if (resp.data.code==200){
            //删完好友之后再更新一下
            this.getFriendList();
          }else {
            console.log("好友删除失败")
          }
        })
      }
    }
  }
}

</script>

<style scoped src="../css/ViewBase.css">

</style>