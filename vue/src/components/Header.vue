<template>
  <div style=" line-height: 60px; display: flex">
    <div style="flex: 1; margin-top: 2px">
      <span :class="collapseBtnClass" style="cursor: pointer; font-size: 20px" @click="collapse"></span>

      <el-breadcrumb separator-class="el-icon-arrow-right" style="display: inline-block;margin-left: 15px">
        <el-breadcrumb-item :to="'/'">后台管理</el-breadcrumb-item>
        <el-breadcrumb-item >{{currentPathName}}</el-breadcrumb-item>
      </el-breadcrumb>

    </div>

    <el-dropdown style="width: 100px; cursor: pointer">
      <div style="display: inline-block">
        <img :src="user.avatar" alt=""
              style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
        <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 4px"></i>
      </div>

      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item><router-link to="/person">个人中心</router-link></el-dropdown-item>
        <el-dropdown-item>
          <span style="text-decoration: none" @click="logout">退出</span></el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass: String,
    collapse: ""
  },
  // computed:{
  //   name
  // }
  watch:{
    $route:function () {
      this.currentPathName = localStorage.getItem("currentPathName")
    }
  },
  data(){
    return{
      currentPathName: "",
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")):{}
    }
  },
  created() {
    console.log(this.$route)
  },
  methods:{
    logout(){
      this.$router.push("/login")
      localStorage.removeItem("user")
      this.$message.success("退出成功")
    }
  }



}
</script>

<style scoped>

</style>