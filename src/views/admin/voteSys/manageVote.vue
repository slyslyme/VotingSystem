<template>
  <div align="center" style="width: 100%;margin:-20px">
    <router-view/>
    <el-card class="box-card" style="width: 1200px;height: 100%">
      <div slot="header" style="width: 1000px">
        <span>投票列表</span>
        <el-form :model="ruleForm"  ref="ruleForm" label-width="120px" class="demo-ruleForm"
                 style="margin-top:30px"
        >
          <el-form-item prop="account" label="">
            <div style="float: left">
              <span>搜索：</span> <el-select v-model="ruleForm.region"
                                                 style="width: 100px">
              <el-option label="投票名称" value="title" fixed></el-option>
              <el-option label="投票模式" value="type"></el-option>
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
                @click="addVotes">投票</el-button>
            </div>
          </el-form-item>
        </el-form>
      </div>
      <el-table
        :data="tableData"
        style="width:1200px;text-align: center" header-align="center"
        height="600px">
        <el-table-column
          align="center"
          prop="title"
          label="投票名称"
          fixed
          width="250">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top"  width="200">
              <p>投票名称: {{ scope.row.title }}</p>
              <p>简介: {{ scope.row.detail }}</p>
              <div slot="reference" class="name-wrapper">
                <el-tag  size="medium" effect="plain">{{ scope.row.title }}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          fixed
          prop="type"
          label="投票模式"
          width="120">
        </el-table-column><el-table-column
        prop="create"
        v-if="hide()"
        width="120">
      </el-table-column><el-table-column
        prop="id"
        v-if="hide()"
        width="120">
      </el-table-column>
        <el-table-column
          align="center"
          prop="counts"
          label="已投票人数"
          width="120">
        </el-table-column>
        <el-table-column
          align="center"
          fixed
          prop="start"
          label="投票开启日期"
          width="180">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.start }}</span>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          fixed
          prop="end"
          label="投票结束日期"
          width="180">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{ scope.row.end }}</span>
          </template>
        </el-table-column>
        <el-table-column
          align="center" label="操作">
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
  var flag = 0
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
                //console.log(this.ruleForm.region)
                //console.log(this.ruleForm.account)
                var list = {}
                list.type = this.ruleForm.region
                list.key = this.ruleForm.account
                console.log(list)
                this.$axios
                    .post('/admin/voteListDim', this.$qs.stringify(list), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response => {if(response.data.code == 500506) {
                        this.$message({showClose: true, message: '请输入“单选”或者“多选”', type: 'error'});
                        return false;
                    }
                        //console.log(response.data.msg + '111');
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
                                obj.title = response.data.data[i].title
                                obj.detail = response.data.data[i].detail
                                obj.counts = response.data.data[i].counts
                                obj.start = response.data.data[i].start
                                obj.end = response.data.data[i].end
                                obj.type = response.data.data[i].type
                                if(obj.type == 1){
                                    obj.type = '多选'
                                }
                                else obj.type = '单选'
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
                    .post('/admin/voteList', this.$qs.stringify(this.formall), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response => {
                        console.log(response.data.msg + '111');
                        if (response.data.code != 0) {
                            this.$message({showClose: true, message: '页面加载失败！', type: 'error'});
                            return false;
                        }
                        else {
                            withCredentials: true
                            for (let i = 0; i < response.data.data.length; i++) {
                                var obj = {}
                                obj.id = response.data.data[i].id
                                obj.title = response.data.data[i].title
                                obj.detail = response.data.data[i].detail
                                obj.counts = response.data.data[i].counts
                                obj.start = response.data.data[i].start
                                obj.end = response.data.data[i].end
                                obj.type = response.data.data[i].type
                                if(obj.type == 1){
                                    obj.type = '多选'
                                }
                                else obj.type = '单选'
                                data[i] = obj
                            }
                            this.tableData = data
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            open(row) {
                this.$confirm('此操作将永久删除该投票, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios
                        .post('/admin/deleteVote', this.$qs.stringify({id:row.id}), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                        .then(response => {
                            withCredentials: true
                            console.log(row.id)
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
                            this.$router.push({path:'./modVote',query:{id:row.id}});
            },
            addVotes() {
                this.$router.push('./addVote');
            },
        }
    }
</script>

<style scoped>

</style>
