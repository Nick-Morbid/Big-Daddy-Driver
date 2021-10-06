<!--这个组件用于新建文件夹-->
<template>
<div :class="isVisible?'mask':''" v-show="isVisible">
  <div class="dialog1">
    <input class="baseinput" v-model="name" style="position:absolute;width: 80%; height:30%;top: 25%;left: 10%" placeholder="请输入文件夹的名称"><br>
    <button class="basebtn1" style="width: 20%;height: 20%;top: 55%;left: 20%"@click="onsubmit">完成</button>
    <button class="basebtn2" style="width: 20%;height: 20%;top: 55%;left: 35%" @click="exit">取消</button>
  </div>
</div>
</template>

<script>
export default {
  name: "BuildFile",
  data(){
    return{
      isVisible:false,
      name:""
    }
  },
  methods:{
    showDialog(){
      this.isVisible = true
    },
    exit(){
      this.name=""
      this.isVisible = false
    },
    onsubmit(){
      if (this.name==""){
        alert("请输入文件名")
        return
      }else {
        this.$emit("children",this.name)
        this.name=""
        this.isVisible = false
      }
    //  向后端进行查重
    }

  }
}
</script>

<style scoped src="../css/ViewBase.css">
</style>