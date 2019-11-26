<template>
  <div class='page'>
    <div class="login-box">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-position="left" label-width="120px"
               class="demo-ruleForm login-container" status-icon>
        <h3 class="title">添加新用户</h3>
        <el-form-item prop="account" label="账号">
          <el-input type="text" v-model="ruleForm.account" auto-complete="off" placeholder="账号"
                    id="Account"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input type="password" v-model="ruleForm.password" auto-complete="off" placeholder="密码（三至八位）"
                    id="Password"></el-input>
        </el-form-item>
        <el-form-item prop="checkPass" label="再次输入密码">
          <el-input type="password" v-model="ruleForm.checkPass" auto-complete="off" placeholder="重复密码"
                    id="CheckPass"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="手机号">
          <el-input type="text" v-model="ruleForm.phone" auto-complete="off" placeholder="十一位"
                    id="Phone" ></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio v-model="ruleForm.gender" :label="0" value="0">男</el-radio>
          <el-radio v-model="ruleForm.gender" :label="1">女</el-radio>
        </el-form-item>
        <el-form-item prop="age" label="年龄"
        >
          <el-input type="text" v-model.number="ruleForm.age" auto-complete="off" placeholder="年龄" id ="Age"></el-input>
        </el-form-item>
        <el-form-item style="width:100%;">
          <div style="width: 70%">
            <div style="float: left;text-align: center">
              <el-button type="primary" @click="submitForm('ruleForm')">添加用户</el-button>
            </div>
            <div style="float: right;text-align: center">
              <el-button type="primary" @click="stopForm()">取消</el-button>
            </div>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
    export default {
        name: "addUser",
        data() {  var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.ruleForm.againPw !== '') {
                    this.$refs.ruleForm.validateField('againPw');
                }
                callback();
            }
        };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            var checkPhone = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('请输入手机号'));
                } else {
                    const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
                    console.log(reg.test(value));
                    if (reg.test(value)) {
                        callback();
                    } else {
                        return callback(new Error('请输入正确的手机号'));
                    }
                }
            };
            return {
                ruleForm: {
                    account: '',
                    password: '',
                    checkPass: '',
                    phone: '',
                    gender: 1,
                    age: ''
                },
                rules: {
                    account: [
                        { required: true, message: '请输入用户名!', trigger: 'blur' },
                        { min: 3, max: 8, message: '长度在 3 到 8 个字符', trigger: 'blur' }
                    ],
                    password: [
                        {validator: validatePass, trigger: 'blur'},
                        {min: 3, max: 8, message: '长度在3到8之间！', trigger: 'blur'}
                    ],
                    checkPass: [
                        {validator: validatePass2, trigger: 'blur'},
                        {min: 3, max: 8, message: '长度在3到8之间！', trigger: 'blur'}
                    ],
                    phone: [
                        {required: true,validator:checkPhone, trigger: 'blur'},

                    ],
                    age: [
                        { required: true, message: '请输入年龄！', trigger: 'blur'},
                        { type: 'number', required:false, message: '年龄必须为数字值'}
                    ]
                }
            };
        },
        methods: {
            stopForm(){
                this.$message({showClose: true, message: '您已放弃添加，转回管理页面！', type: 'warning'});
                this.$router.push('./ManageUser');
                return false;
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        var form ={}
                        form.username = this.ruleForm.account;
                        form.password = this.ruleForm.password;
                        form.gender = this.ruleForm.gender;
                        form.phone = this.ruleForm.phone;
                        form.age = this.ruleForm.age;
                        this.$axios
                            .post('/admin/addAccount', this.$qs.stringify(form), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                            .then(response => {
                                withCredentials: true
                                if (response.data.code == 0) {
                                    this.$message({showClose: true, message: '添加用户成功！', type: 'success'});
                                    this.$router.push('./ManageUser');
                                    return true;
                                } else if(response.data.code == 500300){
                                    this.$message({showClose: true, message: '此账号已被注册！', type: 'error'});
                                    return false;
                                }else if(response.data.code == 500301){
                                    this.$message({showClose: true, message: '此手机已被注册！', type: 'error'});
                                    return false;
                                }
                                else{
                                    this.$message({showClose: true, message: '添加用户失败！', type: 'error'});
                                    return false;
                                }
                            })
                            .catch(error => {
                                console.log(error)
                            })
                    } else {
                        this.$message({showClose: true, message: '添加用户失败！', type: 'error'});
                        return false;
                    }
                });
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
    max-width: 600px;
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

</style>
