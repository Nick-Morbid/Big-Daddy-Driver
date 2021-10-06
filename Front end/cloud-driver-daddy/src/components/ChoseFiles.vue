<template>
  <div :class="isVisible?'mask':''" v-show="isVisible">
    <div class="dialog2">
      <label v-for="item in files">{{item.name}}</label>
      <input style="display: none" @change="upload($event)" ref="uploadinput" type="file" name="filename" multiple >
      <button class="basebtn1" style="position: absolute; width: 20%;height: 15%;top: 75%;left: 12%"@click="chosefile">选择</button><br>
      <button class="basebtn2" style="position: absolute; width: 20%;height: 15%;top: 75%;left: 37%">确定</button><br>
      <button class="basebtn1" style="position: absolute; width: 20%;height: 15%;top: 75%;left: 62%"><span>返回</span></button>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChoseFiles",
  data(){
    return{
      isVisible:true,
      files:[]
    }
  },
  watch:{
  },
  methods:{
    showDialog(){
      this.isVisible = true
    },
    exit(){
      this.isVisible = false
    },
    chosefile(){
      this.$refs.uploadinput.click()
    },
    upload(event){
      this.files = event.target.files
    }

  }
}
</script>

<style scoped src="../css/ViewBase.css">
</style>
