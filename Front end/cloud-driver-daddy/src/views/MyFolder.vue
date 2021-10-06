<template>
  <div :style="isActive?'':'pointerEvents:none'">
    <BuildFile ref="BuildFile" @children="parent"></BuildFile>
    <div :class="mainclass">
      <div class="head">
        <label :class="headtitle">FolderTree</label>
      </div>
      <div :class="body">
        <button class="funcbtn3" style="left:5%" @click="back"><span>返回</span></button>
        <button class="funcbtn4" style="left: 25%" @click="buildfile">新建文件夹</button>
        <div class="folderpanel">
          <img src="../images/emptyfile.jpg" v-if="levelfiles==''?true:false" id="floderimg">
          <div v-for="item in levelfiles" class="folder-item" v-show="item.state>=0?true:false">
            <div :class="item.type" @click="item.type=='folder'?updateLevelFiles(item):''">
              <label class="folderlabel" v-if="renameId==item.id?false:true">{{item.name}}</label>
              <label class="folderlabel" v-if="renameId==item.id?true:false" style="color: white">.</label>
            </div>
            <div class="folderdropdown">
              <span>···</span>
              <button class="folderdropdown-content" v-if="item.type!='folder'?true:false" @click="download(item)">下载</button>
              <button class="folderdropdown-content" @click="rename(item.id)">重命名</button>
              <button class="folderdropdown-content" v-if="item.state==0?true:false" @click="setshare(item)">共享</button>
              <button class="folderdropdown-content" v-if="item.state==0?false:true" @click="cancelshare(item)">取消共享</button>
              <button class="folderdropdown-content" @click="deletefile(item)">删除</button>
            </div>
            <input class="renameinput" v-if="renameId==item.id?true:false" v-model="item.name" @blur="finishrename(item)">
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BuildFile from "@/components/BuildFile";
export default {
  name: "MyFolder",
  components:{
    BuildFile
  },
  data(){
    return{
      isActive:false,
      path:"/myFolder",
      mainclass:"container1",
      body:"body1",
      headtitle:"headtitle1",
      // head:"head1",
      files:[],//该用户的所有文件
      level:-1,//用来描述现在是第几层
      filesstack:[],//用来描述该层文件的上级（这是一个栈，可用于回溯）（可以用level作为栈的指针）
      upIdstack:[],//用来存放文件上级的id（这是一个栈，可用于回溯）
      levelfiles:[],//用来存放该层的所有文件
      renameId:-1,//需要改名的那个文件的id
      filepath:"",//当前所在的文件路径
      downloadURL:""//文件下载路径
    }
  },
  mounted() {
    if (this.$route.path==this.$data.path){
      this.$data.mainclass="container2"
      this.$data.body="body2"
      this.$data.headtitle="headtitle2"
      this.isActive=true
      // this.$data.head="head2"
    }
    else {
      this.$data.mainclass="container1"
      this.$data.body="body1"
      this.$data.headtitle="headtitle1"
      this.isActive=false
      // this.$data.head="head1"
    }
    this.getFileList()
  },
  watch:{
    $route(to,from){
      if (to.path==this.$data.path){
        this.$data.mainclass="container2"
        this.$data.body="body2"
        this.$data.headtitle="headtitle2"
        this.isActive=true
        // this.$data.head="head2"
        this.getFileList()
      }
      else {
        this.$data.mainclass="container1"
        this.$data.body="body1"
        this.$data.headtitle="headtitle1"
        this.isActive=false
        // this.$data.head="head1"
      }
    }
  },
  methods:{
    getFileList:function (){
      axios({
        method:"GET",
        url:"http://localhost:8090/file/getFiles"
      }).then(resp=>{
        this.files = resp.data.result
        this.levelfiles = this.files
        this.level=0
        this.filesstack[this.level]=this.levelfiles
        this.upIdstack[this.level]=-1
        this.filepath="/"
        console.log(this.files)
      })
    },
    updateLevelFiles:function (item){
      if (this.filepath!="/") this.filepath+='/'
        this.filepath+=item.name
      console.log(this.filepath)
    //  根据层级来展示文件
        for (let i = 0; i < this.levelfiles.length; i++) {
            if (this.levelfiles[i].id==item.id){
              this.levelfiles = this.levelfiles[i].children
              this.filesstack[++this.level]=this.levelfiles
              this.upIdstack[this.level]=item.id
              console.log(this.levelfiles)
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
      console.log(this.filepath)

      if (this.level>0){
        this.levelfiles=this.filesstack[--this.level]
      }
    },
    rename:function (id){
      this.renameId=id//把要改名的id赋值为当前选中的文件的id
      // this.renameId=-1
    },
    finishrename:function (item){
      this.renameId=-1
    //  在这里调用后端的接口进行修改
      axios({
        method:"PUT",
        url:"http://localhost:8090/file/renameFile",
        data:{
          id:item.id,
          name:item.name
        }
      }).then(resp=>{
        if (resp.data.code==200){
          alert("重命名成功")
        }else {
          alert("出错了")
          location.reload()
        }
      })
    },
    setshare:function (item){
      item.state=1
      axios({
        method:"PUT",
        url:"http://localhost:8090/file/updateFileState",
        data:item
      }).then(resp=>{
        if (resp.data.code==200){
          alert("设置共享成功")
        }else {
          alert("出错了")
          location.reload()
        }
      })
    },
    cancelshare:function (item){
      item.state=0
      axios({
        method:"PUT",
        url:"http://localhost:8090/file/updateFileState",
        data:item
      }).then(resp=>{
        if (resp.data.code==200){
          alert("取消共享成功")
        }else {
          alert("出错了")
          location.reload()
        }
      })
    },
    deletefile:function (item){
      item.state=-1
      axios({
        method:"PUT",
        url:"http://localhost:8090/file/updateFileState",
        data:item
      }).then(resp=>{
        if (resp.data.code==200){
          alert("删除成功")
        }else {
          alert("出错了")
          location.reload()
        }
      })
    },
    buildfile:function (){
      this.$refs.BuildFile.showDialog()
    },
    parent:function (obj){
      var _this = this
      axios({
          method:"POST",
          url:"http://localhost:8090/file/buildFolder",
          data:{
            upId:_this.upIdstack[_this.level],//当upId为-1时，说明创建文件夹的位置为顶层
            name:obj,
            level:_this.level,
            type:"folder",
            path:_this.filepath+obj
          }
        }).then(resp=>{
          if (resp.data.code==200){
            console.log(resp.data.result)
            _this.files = resp.data.result
            if (_this.level==0){
              _this.levelfiles=_this.files
              _this.filesstack[0]=_this.files
            }else {
              var files = _this.files
              _this.filesstack[0]=_this.files
              for (let i = 1; i < _this.upIdstack.length; i++) {//回溯栈，寻找更新后的levelfiles
                for (let j = 0; j < files.length; j++) {
                    if (files[j].id==_this.upIdstack[i]){
                      files = files[j].children
                      continue
                    }
                }
                _this.filesstack[i] = files
              }
              _this.levelfiles = files
            }
          //  注意：此时files尚未更新
          }else{
            alert("出错了")
          }
      })
    },
    download:function (item){
      var _this = this
      axios({
        method:"GET",
        url:"http://localhost:8090/file/downloadFile",
        params:{
          fileId:item.id,
          uId:-1
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
<style scoped>
#floderimg{
  position: absolute;
  top: 20%;
  left: 25%;
  width: 50%;
  height: 40%;
}
</style>