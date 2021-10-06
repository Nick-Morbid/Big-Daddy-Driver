<template>
  <div :style="isActive?'':'pointerEvents:none'">
  <ChosePath ref="ChosePath" @children="parent"></ChosePath>
   <div :class="mainclass">
     <div class="logo">
       <label :class="title">Uploader</label>
     </div>
     <div :class="panelclass" style="text-align: center">
       <input type="file" ref="chosefiles" multiple style="display: none" @change="getfiles($event)">
       <label :class="flabel">大宝贝助手</label><br>
       <label :class="slabel" v-show="files==''||files==null?true:false">选择要上传的文件</label><br>
       <div class="filenamecontainer" v-if="files==''?false:true">
          <label class="filenamelabel" title="点击以选择路径" v-for="item in files" @click="chosepath(item.name)">
            {{item.name}}
          </label>
       </div><br>
       <button class="funcbtn1" @click="chossfiles">选择文件</button><br>
       <div id="progress-container" v-if="progressBar.width=='0%'?false:true">
         <div id="progress-bar" :style="progressBar"></div>
       </div>
       <button class="funcbtn2" @click="uploadfiles">上传文件</button>
     </div>
   </div>
 </div>
</template>

<script>
import ChosePath from "@/components/ChosePath";
export default {
  name: "Uploader",
  components: {ChosePath},
  data() {
    return {
      isActive:false,
      path: '/uploader',
      mainclass: 'container1',
      panelclass: 'panel1',
      title: "title1",
      flabel: "flabel1",//f表示first
      slabel: "slabel1",//s表示second
      files: [],
      filesinfo:[],//文件信息，用于存入数据库（如文件的上级id、文件名、文件层级
      progressBar:{//上传进度的样式，用来控制进度条的显示
        width:0+'%'
      }
    }
  },
  mounted() {
    if (this.$route.path == this.$data.path) {
      this.$data.mainclass = "container2"
      this.$data.panelclass = "panel2"
      this.$data.title = "title2"
      this.$data.flabel = "flabel2"
      this.$data.slabel = "slabel2"
      this.isActive=true
    } else {
      this.$data.mainclass = "container1"
      this.$data.panelclass = "panel1"
      this.$data.title = "title1"
      this.$data.flabel = "flabel1"
      this.$data.slabel = "slabel1"
      this.isActive=false
    }
  },
  watch: {
    $route(to, from) {
      if (to.path == this.$data.path) {
        this.$data.mainclass = "container2"
        this.$data.panelclass = "panel2"
        this.$data.title = "title2"
        this.$data.flabel = "flabel2"
        this.$data.slabel = "slabel2"
        this.isActive=true
      } else {
        this.$data.mainclass = "container1"
        this.$data.panelclass = "panel1"
        this.$data.title = "title1"
        this.$data.flabel = "flabel1"
        this.$data.slabel = "slabel1"
        this.isActive=false
      }
    }
  },
  methods: {
    chossfiles: function () {
      this.$refs.chosefiles.click()
    },
    getfiles: function (event) {
      this.filesinfo=[]
      this.files = event.target.files
      for (let i = 0; i < this.files.length; i++) {
        //    上传类型判断
        let filename = this.files[i].name;
        let size = this.files[i].size / (1024*1024)//size的单位是mb
        if(size>50){
          alert(filename+"大小超过50mb")
          this.files=[]
          this.$refs.chosefiles.value=""
        }
        let idx = filename.lastIndexOf(".");
        if (idx != -1) {
          let suffix = filename.substr(idx + 1).toUpperCase();
          suffix = suffix.toLowerCase();
          if (suffix!="jpg"&&suffix!="png"&&suffix!="jpeg"&&suffix!="doc"&&suffix!="docx"&&suffix!="xls"
          &&suffix!="xlsx"&&suffix!="txt"&&suffix!="ppt"&&suffix!="pptx"&&suffix!="zip"&&suffix!="mp4"
          &&suffix!="avi"&&suffix!="mm"&&suffix!="gif"&&suffix!="mp3"&&suffix!="zip"&&suffix!="rar"){
            alert(filename+"文件类型不匹配")
            this.files=[]
            this.$refs.chosefiles.value=""
          }
        }
      }
    },
    uploadfiles: function () {
      var _this = this
      if (_this.files.length==0){
        alert("请选择要上传的文件")
        return
      }
      if (_this.filesinfo.length!=_this.files.length){
        alert("请选择文件路径")
      }
      var formData = new FormData()
      formData.append("filesinfo",JSON.stringify(_this.filesinfo))
      for (let i = 0; i < _this.files.length; i++) {
        formData.append("files",_this.files[i])
      }
      _this.progressBar.width=0.1+'%'//让进度条开始显示
      axios({
        method:"POST",
        url:"http://localhost:8090/file/uploadFiles",
        data:formData,
        headers:{
          'Content-Type': "multipart/form-data",
        },
        transformRequest: [function(){
          return formData;
        }],
        onUploadProgress:progressEvent =>{
          _this.progressBar.width = (progressEvent.loaded / progressEvent.total)*100+'%'
        }
      }).then(resp=>{
        if(resp.data.code==200){
          //  结束后记得初始化数据
          _this.filesinfo=[]
          _this.files=[]
          _this.$refs.chosefiles.value=""
          setTimeout(()=>{
            alert("文件上传成功")
          },100)
        }else {
          alert("文件上传失败")
        }
        //  恢复文件上传按钮
        _this.progressBar.width=0+'%'
      })
    },
    chosepath:function (name){
      this.$refs.ChosePath.showDialog(name)
    },
    parent(name,upId,level,path){
      var flag = true
      for (let i = 0; i < this.filesinfo.length; i++) {
          if (this.filesinfo[i].name==name){
            this.filesinfo[i].upId = upId
            this.filesinfo[i].level = level
            this.filesinfo[i].path = path
            flag = false
            break;
          }
      }
      if (flag){
        var len = this.filesinfo.length;
        //插入自定义对象的方法：需要先声明一个对象，然后再插入（不能直接去给数组对象赋值，会报类型未定义的错误）
        var fileinfo={
          name:name,
          upId:upId,
          level:level,
          path:path
        }
        this.filesinfo[len] = fileinfo
      }
    }
  }
}
</script>

<style scoped src="../css/ViewBase.css">
</style>
<style scoped>
#progress-container{
  height: 15%;
  width: 50%;
  border-radius: 15px;
  position: absolute;
  box-sizing: border-box;
  border: 3px solid gray;
  top: 66.5%;
  z-index: 1;
  left: 25%;
  background-color: rgb(250, 247, 247);
  transition: 1s;
  cursor: not-allowed;
}
#progress-bar{

  height: 94%;
  width: 0%;
  border-radius: 15px;
  position: absolute;
  box-sizing: border-box;
  border: 3px solid white;
  z-index: 1;
  left: 0%;
  top: 3%;
  background-color: rgb(83, 195, 135);
  transition: 1s;
  cursor: not-allowed;
  transition: 0.5s;
}
</style>