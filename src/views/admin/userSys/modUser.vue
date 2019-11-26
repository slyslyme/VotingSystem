<template>
  <div class="app">
    <el-card class="userp">
      <div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-position="left" label-width="120px"
               class="demo-ruleForm" status-icon>
        <h3 class="title" style="text-align: center">修改用户信息</h3>
        <el-form-item prop="account" label="账号">
          <el-input type="text" v-model="ruleForm.account" auto-complete="off" placeholder="账号"
                    id="Account"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input type="text" v-model="ruleForm.password" auto-complete="off" placeholder="密码（三至八位）"
                    ></el-input>
        </el-form-item>
        <el-form-item prop="checkPass" label="再次输入密码">
          <el-input type="text" v-model="ruleForm.checkPass" auto-complete="off" placeholder="重复密码"
                    ></el-input>
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
        <el-form-item>
          <div>
            <div style="float: left;text-align: center">
              <el-button type="primary" @click="submitForm('ruleForm')">确认修改</el-button>
            </div>
            <div style="float: right;text-align: center;margin-right: 100px">

              <el-button type="primary" @click="stopForm()">取消</el-button>
            </div>
          </div>
        </el-form-item>
      </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
    export default {
        name: "modUser",
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
                    //console.log(reg.test(value));
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
                        { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'blur' }
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
        mounted() {
            var username = this.$route.query.username;
            this.messegeSearch(username);
        },
        methods: {
            messegeSearch(username){
                this.$axios
                    .post('/admin/accountOne', this.$qs.stringify({username:username}), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response => {
                        withCredentials: true
                        if (response.data.code == 0) {
                            //console.log(response.data.data)
                            this.ruleForm.account = response.data.data.username;
                            this.ruleForm.password = response.data.data.password;
                            this.ruleForm.checkPass = response.data.data.password;
                            this.ruleForm.phone = response.data.data.phone;
                            this.ruleForm.gender = response.data.data.gender;
                            this.ruleForm.age = response.data.data.age;
                            this.ruleForm.id = response.data.data.id;
                            return true;
                        }
                        else{
                            console.log(username)
                            this.$message({showClose: true, message: '失败！', type: 'error'});
                            return false;
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },

            stopForm(){
                this.$message({showClose: true, message: '您已放弃添加，转回管理页面！', type: 'warning'});
                this.$router.push('./ManageUser');
                return false;
            },
            submitForm(formName) {
                console.log(this.ruleForm.id)
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        var form ={}
                        form.id = this.ruleForm.id;
                        form.username = this.ruleForm.account;
                        form.password = this.ruleForm.password;
                        form.gender = this.ruleForm.gender;
                        form.phone = this.ruleForm.phone;
                        form.age = this.ruleForm.age;
                        this.$axios
                            .post('/admin/updateAccount', this.$qs.stringify(form), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                            .then(response => {
                                withCredentials: true
                                console.log(response.data.code)
                                if (response.data.code == 0) {
                                    this.$message({showClose: true, message: '修改用户成功！', type: 'success'});
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
                                    this.$message({showClose: true, message: '修改用户失败！', type: 'error'});
                                    return false;
                                }
                            })
                            .catch(error => {
                               // console.log(error)
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

  .userp{
    text-align: left;
    display: inline-block;
    font-size: 25px;
    margin-top: 20px;
    margin-left: 20px;
    width: 50%;
    height: auto;
  }

</style>
