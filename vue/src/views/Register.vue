<template>
  <div class="wrapper">
    <div style="margin: 200px auto; background-color: aliceblue; width: 350px; height: 400px;padding: 22px; border-radius: 5px">
      <div style="margin: 20px 0; text-align: center;font-size:25px; font-style: oblique; font-family: 华文楷体">
        <b >注 册</b>
      </div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号 (#^.^#)" size="medium" style="margin-top: 10px" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password" >
          <el-input placeholder="请输入密码 (#^.^#)" size="medium" style="margin-top: 15px" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword" >
          <el-input placeholder="请确认密码 (#^.^#)" size="medium" style="margin-top: 15px" prefix-icon="el-icon-paperclip" show-password v-model="user.confirmPassword"></el-input>
        </el-form-item>

        <el-form-item style="margin-top:45px;text-align: center">
          <el-button size="medium" type="success" autocomplete="off" style="margin-right: 60px;width: 90px" @click="login">确定</el-button>
          <el-button size="medium" type="danger" autocomplete="off" style="width: 90px" @click="$router.push('/login')">取消</el-button>
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
          {required: true, message: '账号为空！', trigger: 'blur'},
          {min: 2, max: 10, message: '账号长度在 2 到 10 个字符！', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '密码为空！', trigger: 'blur'},
          {min: 1, max: 20, message: '密码长度在 1 到 20 个字符！', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '确认密码为空！', trigger: 'blur'},
          {min: 1, max: 20, message: '密码长度在 1 到 20 个字符！', trigger: 'blur'}
        ]
      }
    }
  },
  methods:{
    login() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          if(this.user.password != this.user.confirmPassword){
            this.$message.warning("两次密码输入不一致！")
            return false;
          }
          this.request.post("/user/register", this.user).then(res => {
            if(res.code == '200'){
              this.$message.success("注册成功")
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

