<template>
  <div style="margin: -20px;width: 100%;height: 100%" align="center">
          <router-view/>
      <el-card class="box-card" style="width: 80%;height: 800px">
        <div slot="header" >
          <span>用户列表</span>
            <el-form :model="ruleForm"  ref="ruleForm" class="demo-ruleForm"
                     style="margin-top:30px"
            >
              <el-form-item prop="account" label="">
                <div style="float: left">
                  <span>搜索：</span>
                  <el-select v-model="ruleForm.region" style="width: 100px">
                  <el-option label="账号" value="username" fixed></el-option>
                  <el-option label="性别" value="gender"></el-option>
                  <el-option label="年龄" value="age"></el-option>
                  <el-option label="手机号" value="phone"></el-option>
                </el-select>
                <el-input type="text" v-model="ruleForm.account" auto-complete="off"
                          style="width:200px;"
                          placeholeder="请输入搜索内容"
                          label=""
                          id="Account"></el-input>
                  <el-button
                    size="medium"
                    type="primary"
                    icon="el-icon-search"
                    @click="searchOps">搜索</el-button><el-button
                    size="medium"
                    type="info"
                    icon="el-icon-delete"
                    @click="stopOps">重置</el-button>
          </div>
                <div style="float: right;margin-left: 5px;">
                  <el-button
                    style="float: right;"
                    size="medium"
                    type="success"
                    icon="el-icon-plus"
                    @click="addUsers">新增用户</el-button>
                </div>
              </el-form-item>
            </el-form>

        </div>
      <el-table
        :data="tableData"
        style="width:80%;text-align: center"
        height="750">
        <el-table-column
          prop="id"
          v-if="hide()"
          width="200">
        </el-table-column>
        <el-table-column
          align="center"
          prop="username"
          label="账号"
          width="200">
        </el-table-column>
        <el-table-column
          align="center"
          prop="password"
          label="密码"
          v-if="hide()"
          width="120">
        </el-table-column>
        <el-table-column
          align="center"
          prop="gender"
          label="性别"
          width="120">
        </el-table-column>
        <el-table-column
          align="center"
          prop="age"
          label="年龄"
          width="120">
        </el-table-column>
        <el-table-column
          align="center"
          prop="phone"
          label="手机号"
          width="120">
        </el-table-column>
        <el-table-column
          align="center"
          width="200" label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.row)">修改信息</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="open(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
    export default {
        name: "manageVote",
        data() {
            return {
                ruleForm: {
                    account:' ',
                    region:' '
                },
                tableData: []
            }
        },
        mounted() {
            var formall = {};
            this.allSearch(formall);
        },
        methods: {
            stopOps(){
                location. reload()
            },
            searchOps(){
                var list = {}
                list.type = this.ruleForm.region
                list.key = this.ruleForm.account
                console.log(list)
                this.$axios
                    .post('/admin/someAccounts', this.$qs.stringify(list), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response => {
                        //console.log(response.data.msg + '111');
                        if (response.data.code == 500604){
                            this.$message({showClose: true, message: '性别格式出错', type: 'error'});
                            return false;
                        }
                        else if (response.data.code == 500605){
                            this.$message({showClose: true, message: '年龄必须为整数', type: 'error'});
                            return false;
                        }
                        else if (response.data.code != 0 || list.key ==' '||list.type == ' ') {
                            this.$message({showClose: true, message: '搜索失败！', type: 'error'});
                            return false;
                        }
                        else {
                            var data = []
                            withCredentials: true
                            this.tableData = []
                            for (let i = 0; i < response.data.data.length; i++) {
                                console.log(response.data.data[i])
                                var obj = {}
                                obj.id = response.data.data[i].id
                                obj.gender = response.data.data[i].gender
                                obj.username = response.data.data[i].username
                                obj.password = response.data.data[i].password
                                obj.age = response.data.data[i].age
                                obj.phone = response.data.data[i].phone
                                if(obj.gender  == 0){
                                    obj.gender = '男'
                                }
                                else obj.gender = '女'
                                data[i] = obj
                            }
                            this.tableData = data
                            console.log('true= =')

                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            hide(){},
            allSearch(formall) {
                var data = [
                ]
                this.$axios
                    .post('/admin/accounts', this.$qs.stringify(this.formall), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response => {
                        //console.log(response.data.msg + '111');
                        if (response.data.code != 0) {
                            this.$message({showClose: true, message: '页面初始化失败！', type: 'error'});
                            return false;
                        }
                        else {
                            withCredentials: true
                            for (let i = 0; i < response.data.data.length; i++) {
                                var obj = {}
                                obj.id = response.data.data[i].id
                                obj.gender = response.data.data[i].gender
                                obj.username = response.data.data[i].username
                                obj.password = response.data.data[i].password
                                obj.age = response.data.data[i].age
                                obj.phone = response.data.data[i].phone
                                if(obj.gender  == 0){
                                    obj.gender = '男'
                                }
                                else obj.gender = '女'
                                data[i] = obj
                            }
                            this.tableData = data
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            addUsers() {
                this.$router.push('./addUser')
            },
            open(row) {
                this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                       this.$axios
                            .post('/admin/deleteAccount', this.$qs.stringify({username:row.username}), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                            .then(response => {
                                withCredentials: true
                                console.log(response.data.code)
                                if (response.data.code == 0) {
                                    this.$message({
                                        type: 'success',
                                        message: '删除成功!'
                                    })
                                    this.allSearch()
                                }else{
                                    this.$message({
                                        type: 'error',
                                        message: '删除失败!'
                                    })
                                }
                            })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handleEdit(row) {
                //console.log(row.username);
                this.$router.push({path:'./modUser',query:{username:row.username}});
            }
        }
    }
</script>

<style scoped>

</style>
