<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden;"
           background-color="#545c64" text-color="#fff" active-text-color="#F5DEB3"
           :collapse-transition="false"
           :collapse="isCollapse"
            router
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <i class="el-icon-guide" style="color: cornflowerblue; margin-right: 8px; left:20px; font-size: 25px"></i>
      <b style="color: bisque" v-show="logoTextShow">共享单车管理系统</b>
    </div>
    <el-menu-item index="/home">
      <template slot="title"><i class="el-icon-house"></i>
        <span slot="title">首页信息</span>
      </template>

    </el-menu-item>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-menu"></i>
        <span slot="title">菜单栏</span>
      </template>

      <el-menu-item index="/user">
        <i class="el-icon-s-custom"></i>用户管理</el-menu-item>

    </el-submenu>
<!--    <el-submenu index="3">-->
<!--      <template slot="title"><i class="el-icon-setting"></i>-->
<!--        <span slot="title">其他选项</span>-->
<!--      </template>-->
<!--      <el-menu-item-group>-->
<!--        <template slot="title">分组一</template>-->
<!--        <el-menu-item index="3-1">选项1</el-menu-item>-->
<!--        <el-menu-item index="3-2">选项2</el-menu-item>-->
<!--      </el-menu-item-group>-->

<!--    </el-submenu>-->
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  methods:{
    // handleSelect(index){
    //   console.log(index)
    //
    //   // this.$route.fullPath.split("/")
    // }
  }
}
</script>

<style scoped>

</style>