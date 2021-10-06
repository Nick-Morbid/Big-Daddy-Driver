<template>
  <div :class="isVisible?'mask':''" v-show="isVisible">
    <BuildFile ref="BuildFile" @children="parent"></BuildFile>
      <div class="dialog3">
        <button class="funcbtn3" style="left:3%" @click="back"><span>返回</span></button>
        <button class="funcbtn4" style="left: 20%;width: 25%;" @click="buildfile">新建文件夹</button>
        <button class="funcbtn4" style="left: 47%" @click="onsubmit">确定</button>
        <button class="funcbtn4" style="left: 69%" @click="exit">取消</button>
        <div class="folderpanel">
          <img src="../images/emptyfile.jpg" v-if="levelfiles==''?true:false" id="floderimg">
          <div v-for="item in levelfiles" class="folder-item" v-show="item.state>=0?true:false">
            <div :class="item.type" @click="item.type=='folder'?updateLevelFiles(item):''">
              <label class="folderlabel" v-if="renameId==item.id?false:true">{{item.name}}</label>
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script>
import BuildFile from "@/components/BuildFile";
export default {
  name: "ChosePath",
  components: {BuildFile},
  data(){
    return{
      isVisible:false,
      name:"",
      files:[],//该用户的所有文件
      level:-1,//用来描述现在是第几层
      filesstack:[],//用来描述该层文件的上级（这是一个栈，可用于回溯）（可以用level作为栈的指针）
      upIdstack:[],//用来存放文件上级的id（这是一个栈，可用于回溯）
      levelfiles:[],//用来存放该层的所有文件
      renameId:-1,//需要改名的那个文件的id
      filepath:"",//当前所在的文件路径
    }
  },
  watch:{
  },
  methods:{
    showDialog(name){
      this.name = name
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
      })
      this.isVisible = true
    },
    exit(){
      this.isVisible = false
    },
    onsubmit(){
      // console.log(this.level)
      this.$emit("children",this.name,this.upIdstack[this.level],this.level,this.filepath)
      this.isVisible=false
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
    buildfile:function (){
      this.$refs.BuildFile.showDialog()
    },
    parent:function (obj){
      console.log(obj)
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
          // console.log(resp.data.result)
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
    }
  }
}
</script>

<style scoped src="../css/ViewBase.css">
</style>
