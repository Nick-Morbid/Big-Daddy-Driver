<template>
  <div>
    <div :class="isVisible?'mask':''" v-show="isVisible">
      <div class="dialog1" style="text-align: center">
        <label style="position:relative;top: 30%;">{{ msg }}</label>
        <button class="basebtn1" style="position:absolute;width: 20%;height: 20%;top: 70%;left: 25%"@click="onsubmit(true)">确定</button>
        <button class="basebtn2" style="position:absolute;width: 20%;height: 20%;top: 70%;left: 55%" @click="onsubmit(false)">取消</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Notice",
  data(){
    return{
      isVisible:false,
      msg:"",
      arg:""
    }
  },
  methods:{
    showDialog(msg,id){
      this.msg = msg
      this.arg = id
      this.isVisible=true
    },
    onsubmit(identify){
      this.$emit("children",identify,this.arg)
      this.isVisible=false
    }
  }
}
</script>

<style scoped src="../css/ViewBase.css">

</style>