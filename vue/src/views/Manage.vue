<template>
  <el-container style="min-height: 100vh; ">
      <el-aside :width="sideWidth + 'px'" style="background-color: rgb(238, 241, 246); box-shadow: #cccccc 4px 2px 6px" >
        <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>

      </el-aside>

      <el-container>
        <el-header style="border-bottom: 1px solid #ccc;">
          <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"/>

        </el-header>

        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
</template>

<script>
// @ is an alias to /src
// import HelloWorld from '@/components/HelloWorld.vue'
// import request from "../../utils/request";

import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'Manage',
  data(){
    return {
      collapseBtnClass:'el-icon-s-fold',
      isCollapse: false,
      sideWidth: 200,
      logoTextShow: true,
    }
  },
  components: {
    Aside,
    Header
  },


  methods: {
    collapse() { //点击收缩
      this.isCollapse = !this.isCollapse
      if(this.isCollapse){ //收缩
        this.sideWidth= 64
        this.collapseBtnClass = 'el-icon-s-unfold';
        this.logoTextShow = false;
      }else{  //展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold';
        this.logoTextShow = true;
      }
    }
  }

}
</script>

<style>
  .headerBg{
    background-color: #cccccc !important;
  }
</style>