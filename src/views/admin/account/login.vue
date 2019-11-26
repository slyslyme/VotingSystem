<template>
  <div class='page'>
    <div class="login-box">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-position="left" label-width="0px"
               class="demo-ruleForm login-container" status-icon>
        <h3 class="title">管理员登录</h3>
        <el-form-item prop="username">
          <el-input type="text" v-model="ruleForm.username" auto-complete="off" placeholder="账号"
                    id="loginEmail"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="ruleForm.password" auto-complete="off" placeholder="密码"
                    id="loginPassword" @keyup.enter.native="submitForm('ruleform')"></el-input>
        </el-form-item>
        <el-form-item style="width:100%;">
          <el-button type="primary" style="width:100%;" @click="submitForm('ruleForm')">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
    export default {
        name: "login",
        data () {
            return {
                ruleForm: {
                    username: '',
                    password: ''
                },
                rules: {
                    username: [
                        { required: true, message: '请输入账号', trigger: 'blur' },
                        {min: 3, max: 11, message: '长度在3到11个字符之间',trigger: 'blur'}
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        {min: 3, max: 8, message: '长度在3到8个字符之间',trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            submitForm (formName) {

                this.$refs[formName].validate((valid) => {

                    if (valid) {
                        console.log(this.ruleForm);
                        this.$axios
                            .post('/user/login', this.$qs.stringify(this.ruleForm),{headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                            .then(response =>{
                                withCredentials:true
                                console.log("111" + response.data.msg);
                                if(response.data.code == 0){
                                    localStorage.setItem("key",this.ruleForm.username);
                                    this.$router.push({path:'/adminFirst',query:{username:this.ruleForm.username}});
                                    return true;
                                }
                                else if(response.data.code == 500211){
                                    this.$message.error("用户名不存在！");
                                    return false;
                                }
                                else{
                                    this.$message.error("密码错误！");
                                    return false;
                                }
                            })
                            .catch(error =>{
                                console.log("11111111111111111")
                                console.log(error)
                            })
                    }else{
                        return false;
                    }
                })
            }
        }
    }

</script>

<style scoped>
  .title {
    text-align: center;
    margin-bottom: 15px;
  }

  .page {
    background-color: #eff3f4;
    position: absolute;
    width: 100%;
    height: 100%;
    font-size: 16px;
    font-family: 'Source Sans Pro', sans-serif;
    font-weight: 400;
    -webkit-font-smoothing: antialiased;
  }

  .login-box {
    position: absolute;
    top: 50%;
    left: 50%;
    -webkit-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    display: block;
    width: 100%;
    max-width: 400px;
    background-color: #FFF;
    margin: 0;
    padding: 2.25em;
    box-sizing: border-box;
    border: solid 1px #DDD;
    border-radius: .5em;
    font-family: 'Source Sans Pro', sans-serif;
  }
</style>
