<template>
  <div :class="isVisible?'mask':''" v-show="isVisible">
    <div class="dialog3">
      <button class="funcbtn3" style="left:3%" @click="back"><span>返回</span></button>
      <button class="funcbtn4" style="left: 20%" @click="exit">取消</button>
      <div class="folderpanel">
        <img src="../images/emptyfile.jpg" v-if="levelfiles==''?true:false" id="floderimg">
        <div v-for="item in levelfiles" class="folder-item" v-show="item.state>=0?true:false">
          <div :class="item.type" @click="item.type=='folder'?updateLevelFiles(item):''">
            <label class="folderlabel" v-if="renameId==item.id?false:true">{{item.name}}</label>
          </div>
          <div class="folderdropdown" v-if="item.type!='folder'?true:false">
            <span>···</span>
            <button class="folderdropdown-content" @click="download(item)">下载</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FriendFiles",
  data(){
    return{
      id:"",//所查询好友的id
      isVisible:false,
      files:[],//该用户的所有文件
      level:-1,//用来描述现在是第几层
      filesstack:[],//用来描述该层文件的上级（这是一个栈，可用于回溯）（可以用level作为栈的指针）
      upIdstack:[],//用来存放文件上级的id（这是一个栈，可用于回溯）
      levelfiles:[],//用来存放该层的所有文件
      renameId:-1,//需要改名的那个文件的id
      filepath:"",//当前所在的文件路径
    }
  },
  methods:{
    showDialog(id){
      this.id = id
      axios({
        method:"GET",
        url:"http://localhost:8090/file/getShareFiles",
        params:{
          id:id
        }
      }).then(resp=>{
        this.files = resp.data.result
        this.levelfiles = this.files
        this.level=0
        this.filesstack[this.level]=this.levelfiles
        this.upIdstack[this.level]=-1
        this.filepath="/"
      })
      this.isVisible = true
    },
    exit(){
      this.isVisible = false
    },
    //接下来是关于文件树的操作
    updateLevelFiles:function (item){
      if (this.filepath!="/") this.filepath+='/'
      this.filepath+=item.name
      // console.log(this.filepath)
      //  根据层级来展示文件
      for (let i = 0; i < this.levelfiles.length; i++) {
        if (this.levelfiles[i].id==item.id){
          this.levelfiles = this.levelfiles[i].children
          this.filesstack[++this.level]=this.levelfiles
          this.upIdstack[this.level]=item.id
          // console.log(this.levelfiles)
          break;
        }
      }
    },
    back:function (){
      var i;
      for (i = this.filepath.length-1; i >=0; i--) {
        if (this.filepath[i]=='/') break;
      }
      if (i==0) this.filepath="/"
      else this.filepath = this.filepath.substr(0,i)
      // console.log(this.filepath)

      if (this.level>0){
        this.levelfiles=this.filesstack[--this.level]
      }
    },
    download:function (item){
      var _this = this
      axios({
        method:"GET",
        url:"http://localhost:8090/file/downloadFile",
        params:{
          fileId:item.id,
          uId:_this.id
        },
        responseType:'blob'
      }).then(resp=>{
        console.log(resp.data)
        const content = resp.data
        const blob = new Blob([content])
        const fileName = item.name
        if ('download' in document.createElement('a')) { // 非IE下载
          const elink = document.createElement('a')
          elink.download = fileName
          elink.style.display = 'none'
          elink.href = URL.createObjectURL(blob)
          document.body.appendChild(elink)
          elink.click()
          URL.revokeObjectURL(elink.href) // 释放URL 对象
          document.body.removeChild(elink)
        } else { // IE10+下载
          navigator.msSaveBlob(blob, fileName)
        }
      })
    }
  }
}
</script>

<style scoped src="../css/ViewBase.css">
</style>
