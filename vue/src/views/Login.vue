<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: aliceblue; width: 350px; height: 370px;padding: 22px; border-radius: 5px">
      <div style="margin: 20px 0; text-align: center;font-size:25px; font-style: oblique; font-family: 华文楷体">
        <b >Welcome</b>
      </div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin-top: 10px" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password" >
          <el-input size="medium" style="margin-top: 20px" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>

        <el-form-item style="margin-top:50px;text-align: center">
          <el-button size="medium" type="success" autocomplete="off" style="margin-right: 60px;width: 90px" @click="login">登录</el-button>
          <el-button size="medium" type="success" autocomplete="off" style="width: 90px" @click="$router.push('/register')">注册</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data(){
    return{
      user:{},
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 2, max: 10, message: '账号长度在 2 到 10 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '密码长度在 1 到 20 个字符', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    login() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          this.request.post("/user/login", this.user).then(res => {
            if(res.code == '200'){
              localStorage.setItem("user", JSON.stringify(res.data))
              this.$message.success("登录成功")
              this.$router.push("/")
            } else{
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style scoped>
 .wrapper{
   height: 100vh;
   overflow: hidden;
   /*background-image: linear-gradient(to top left, #42b983, #cccccc);*/
  background: url("C:\Users\16673\Pictures\Camera Roll\bg2.png");
 }

</style>

