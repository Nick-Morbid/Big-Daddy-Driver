<template>
  <div :style="isActive?'':'pointerEvents:none'">
    <div :class="mainclass">
      <div class="head">
        <label :class="headtitle">Notification</label>
      </div>
      <div :class="body">
        <button class="funcbtn4" style="left: 13%" @click="type=0">好友申请</button>
        <button class="funcbtn4" style="left: 38%" @click="type=1">申请通知</button>
        <button class="funcbtn4" style="left: 63%" @click="type=2">下载通知</button>
        <div class="folderpanel">
          <img src="../images/emptyfile.jpg" style="position:absolute;left: 15%;top: 15%" v-if="isEmpty" id="floderimg">
          <div v-for="item in requestFriends" v-if="type==0?true:false" class="notificationlabel">{{item.timestamp+" "+item.body}}
          <button class="funcbtn3" style="width: 15%;height: 50%;position:absolute;right: 16%;top: 35%"@click="dealFriendRequest(item.id,item.senderId,1)">同意</button>
          <button class="funcbtn4" style="width: 15%;height: 50%;position:absolute;right: 0%;top: 35%"@click="dealFriendRequest(item.id,item.senderId,1)">拒绝</button>
          </div>
          <div v-for="item in resultResult" v-if="type==1?true:false" class="notificationlabel">{{item.timestamp+" "+item.body}}</div>
          <div v-for="item in downLoadNotification" v-if="type==2?true:false" class="notificationlabel">{{item.timestamp+" "+item.body}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import FriendFiles from "@/components/FriendFiles";
import Notice from "@/components/Notice";
import BuildFile from "@/components/BuildFile";
export default {
  name: "Notification",
  components: {BuildFile},
  data(){
    return{
      isActive:false,
      path:"/notification",
      mainclass:"container1",
      body:"body1",
      headtitle:"headtitle1",
      type:0,
      requestFriends:[],//好友申请的消息
      resultResult:[],//申请结果通知
      downLoadNotification:[],//消息下载通知
      isEmpty:false
    }
  },
  mounted() {
    if (this.$route.path==this.$data.path){
      this.$data.mainclass="container2"
      this.$data.body="body2"
      this.$data.headtitle="headtitle2"
      this.isActive=true
      if (this.requestFriends=="") this.isEmpty=true
      else this.isEmpty=false
      // this.$data.head="head2"
    }
    else {
      this.$data.mainclass="container1"
      this.$data.body="body1"
      this.$data.headtitle="headtitle1"
      this.isActive=false
      // this.$data.head="head1"
    }
    this.$data.type=0
    this.getNotification(0)
  },
  watch: {
    $route(to, from) {
      if (to.path == this.$data.path) {
        this.$data.mainclass = "container2"
        this.$data.body = "body2"
        this.$data.headtitle = "headtitle2"
        this.isActive=true
        // this.$data.head="head2"
      } else {
        this.$data.mainclass = "container1"
        this.$data.body = "body1"
        this.$data.headtitle = "headtitle1"
        this.isActive=false
        // this.$data.head="head1"
      }
    },
    type(newValue,oldValue){
      this.getNotification(newValue)
    }
  },
  methods:{
    getNotification:function (type){
      axios({
        method:"GET",
        url:"http://localhost:8090/notification/getNotification",
        params:{
          type:type
        }
      }).then(resp=>{
        if (resp.data.code==200){
          switch (type){
            case 0:{
              this.requestFriends=resp.data.result
              if (this.requestFriends=="") this.isEmpty=true
              else this.isEmpty=false
              break
            }
            case 1:{
              this.resultResult=resp.data.result
              if (this.resultResult=="") this.isEmpty=true
              else this.isEmpty=false
              break
            }
            case 2:{
              this.downLoadNotification=resp.data.result
              if (this.downLoadNotification=="") this.isEmpty=true
              else this.isEmpty=false
            }
          }
        }
      })
    },
    dealFriendRequest:function (notificationId,senderId,oparation){
        axios({
          method:"GET",
          url:"http://localhost:8090/friend/dealFriendRequest",
          params:{
            operation:oparation,
            notificationId:notificationId,
            friendId:senderId
          }
        }).then(resp=>{
          if (resp.data.code!=200){
            console.log("好友申请处理失败")
          }else {
            this.getNotification(0)
          }
        })
    }
  }
}

</script>

<style scoped src="../css/ViewBase.css">

</style>