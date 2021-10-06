<template>
  <div :style="isActive?'':'pointerEvents:none'">
    <div :class="mainclass">
      <div class="logo">
        <label :class="title">Prompt</label>
      </div>
      <div :class="panelclass">
      </div>
    </div>
  </div>
  </template>

<script>
export default {
  name: "Prompt",
  data(){
    return{
      isActive:false,
      path:'/prompt',
      mainclass:'container1',
      panelclass:'panel1',
      title:"title1"
    }
  },  mounted() {
    if (this.$route.path==this.$data.path){
      this.$data.mainclass="container2"
      this.$data.panelclass="panel2"
      this.$data.title="title2"
      this.isActive=true
    }
    else {
      this.$data.mainclass = "container1"
      this.$data.panelclass = "panel1"
      this.$data.title="title1"
      this.isActive=false
    }
  },
  watch:{
    $route(to,from){
      if (to.path==this.$data.path){
        this.$data.mainclass="container2"
        this.$data.panelclass="panel2"
        this.isActive=true
      }
      else {
        this.$data.mainclass = "container1"
        this.$data.panelclass = "panel1"
        this.isActive=false
      }
    }
  }
}
</script>

<style scoped src="../css/ViewBase.css">
</style>