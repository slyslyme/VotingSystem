<template>
    <div class="page">
      <div class="login-box">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-position="left" label-width="0px"
                 class="demo-ruleForm login-container" status-icon>
          <h3 class="title">修改密码</h3>
          <el-form-item prop="account" required>
            <el-input type="password" v-model="ruleForm.account" auto-complete="off" placeholder="请输入原密码"
                      id="loginEmail"></el-input>
          </el-form-item>
          <el-form-item prop="password" required>
            <el-input type="password" v-model="ruleForm.password" auto-complete="off" placeholder="请输入密码"
                      id="loginPassword"></el-input>
          </el-form-item>
          <el-form-item prop="checkPass" required>
            <el-input type="password" v-model="ruleForm.checkPass" auto-complete="off" placeholder="请再次输入密码"
                      id="loginCheckPass"></el-input>
          </el-form-item>

          <el-form-item style="width:100%;">
            <div class="rightbtn">
            <el-button  type="primary" style="width:100%;" @click.native.prevent="handleSubmit" :loading="logining" label>
              确定
            </el-button>
            </div>
            <div class="returnbtn">
              <el-button @click="backto()" type="primary" style="</router-link>width:100%;" label>返回</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
</template>

<script>
    export default {
        name: "changePassworld",
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'))
                } else {
                    if (this.ruleForm.checkPass !== '') {
                        this.$refs.ruleForm.validateField('checkPass')
                    }
                    callback()
                }
            }
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'))
                } else if (value !== this.ruleForm.password) {
                    callback(new Error('两次输入密码不一致!'))
                } else {
                    callback()
                }
            }
            return{
                ruleForm:{
                    account: '',
                    password: '',
                    checkPass: ''
                },
                rules: {
                    account: [
                        {required: true, message: '请输入原密码', trigger: 'blur'},
                        {min: 3, max: 8, message: '长度在3到8个字符之间！', trigger: 'blur'}
                    ],
                    password: [
                        {validator: validatePass, trigger: 'blur'},
                        {min: 3, max: 8, message: '长度在3到8个字符之间！', trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validatePass2, trigger: 'blur'},
                        {min: 3, max: 8, message: '长度在3到8个字符之间！', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            handleSubmit (ev) {
                this.$refs.ruleForm.validate((valid) => {
                    if (valid) {
                        var registerParams = {
                            oldPass: this.ruleForm.account,
                            newPass: this.ruleForm.password,
                        }
                        this.$axios
                            .post('/vote/updatePw', this.$qs.stringify(registerParams),{headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                            .then(response =>{
                                if(response.data.code == 0){
                                    this.$message({showClose: true, message: '密码修改成功！', type: 'success'});
                                    this.$router.push('/login')
                                }
                                else if(response.data.code == 500214){
                                    this.$message({showClose: false, message: '原密码错误！', type: 'error'});
                                }
                                else{
                                    this.$message({showClose: false, message: '密码修改失败！', type: 'error'});
                                }
                            })
                    } else {
                        console.log('error submit!!')
                        return false
                    }
                })
            },
            backto(){
                this.$router.go(-1);
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
    background: url("../../assets/image7.jpg");
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

  .login-box .svgContainer {
    position: relative;
    width: 200px;
    height: 200px;
    margin: 0 auto 1em;
    border-radius: 50%;
    pointer-events: none;
  }

  .rightbtn{
    width:35%;
    margin-top:35px;
    margin-left: 10px;
    float: left;
  }
  .returnbtn{
    width: 35%;
    margin-top: 35px;
    margin-right: 10px;
    float:right;
  }

</style>
