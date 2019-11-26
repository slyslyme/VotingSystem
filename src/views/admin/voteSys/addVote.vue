<template>
  <div class="app">
    <el-card class="usera">
      <div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="130px" class="demo-ruleForm"
        >
          <h3 class="title" style="text-align: center">添加新投票</h3>
          <el-form-item label="投票名称" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="投票模式" prop="region">
            <el-select v-model="ruleForm.region" placeholder="请选择投票模式">
              <el-option label="单选" value="0"></el-option>
              <el-option label="多选" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="投票开始时间" required>
            <el-date-picker
              v-model.datetime="ruleForm.date1"
              type="datetime"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="pickerOptionsStart"
              :rules="{
    type: 'date',  required: true, message: '内容不能为空', trigger: 'blur'
    }"
              placeholder="选择日期时间">
            </el-date-picker>
          </el-form-item>
          <el-form-item
            label="投票结束时间" required>
            <el-date-picker
              v-model.date="ruleForm.date2"
              type="datetime"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
              :picker-options="pickerOptionsEnd"
              placeholder="选择日期时间">
            </el-date-picker>
              </el-form-item>
          <el-form-item label="内容简介" prop="desc">
            <el-input type="textarea" v-model="ruleForm.desc"></el-input>
          </el-form-item>
          <el-form-item
            v-for="(domain, index) in ruleForm.domains"
            :label="'选项内容' + index"
            :key="domain.index"
            :prop="'domains.' + index + '.value'"
            :rules="{
      required: true, message: '内容不能为空', trigger: 'blur'
    }"
          >
            <el-input
              style="width: 85%"
              v-model="domain.value" ></el-input>

            <el-button
            style="float: right"
            @click.prevent="removeDomain(domain)">删除</el-button>
          </el-form-item>
          <el-form-item>
            <el-button @click="addDomain">新增选项</el-button>
          </el-form-item>
          <el-form-item>
            <div style="width: 70%">
              <div style="float: left;text-align: center">
            <el-button type="primary" @click="submitForm('ruleForm')">发起投票</el-button>
          </div>
            <div style="float: right;text-align: center">
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
  var i = 1
    import moment from 'moment'
    export default {
        name: "addVote",
        data() {
            return {
                ruleForm: {
                        domains: [{
                            value: ''

                        }],
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    desc: ''
                }, pickerOptionsStart: {

            disabledDate: (time) => {
                    var time1 = moment(new Date() ).format("YYYY-MM-DD hh:mm:ss")
                                return time.getTime() < new Date(time1).getTime() - 8.64e7
                    }  
                },
                pickerOptionsEnd: {
                    disabledDate: (time) => {
                        if (this.ruleForm.date1) {
                            return time.getTime() < new Date(this.ruleForm.date1).getTime() - 86400000 + 1
                        }

                    }
                },
                rules: {
                    name: [
                        { required: true, message: '请输入投票名称', trigger: 'blur' },
                        { min: 1, max: 15, message: '长度在 1 到 15 个字符', trigger: 'change' }
                    ],
                    region: [
                        { required: true, message: '请选择投票模式', trigger: 'change' }
                    ],
                    desc: [
                        { required: true, message: '请填写简介', trigger: 'blur' }
                    ]
                }
            };
        },
        methods: {
            removeDomain(item) {
                var index = this.ruleForm.domains.indexOf(item)
                if (index !== 0  && i > 1) {
                    i = i - 1
                    this.ruleForm.domains.splice(index, 1)
                }else{
                    this.$notify({
                        title: '警告',
                        message: '请至少输入一个选项',
                        type: 'warning'
                    });
                }
            },
            addDomain() {
                i = i + 1;
                this.ruleForm.domains.push({
                    value: ''
                });
            },
            stopForm(){
                this.$message({showClose: true, message: '您已取消操作，回到管理页', type: 'warning'});
                this.$router.push('./ManageVote');
                return false;
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        var form ={}
                        form.title = this.ruleForm.name;
                        form.type = this.ruleForm.region;
                        form.start = this.ruleForm.date1;
                        form.end = this.ruleForm.date2;
                        form.detail = this.ruleForm.desc;
                        form.voteOptions = []
                        var j
                        for(j = 0;j < i;j++){
                            form.voteOptions[j] = this.ruleForm.domains[j].value
                        }
                        console.log(form)
                        var times = moment(new Date() ).format("YYYY-MM-DD hh:mm:ss")//获取当前时间
                        var time1 = new Date(times).getTime();//转换
                        var time3 = new Date(this.ruleForm.date2).getTime()
                        var time2 =  new Date(this.ruleForm.date1).getTime();
                        console.log(form.start)
                        if(form.start ==''||form.end ==''){
                            this.$message({showClose: true, message: '请输入时间！', type: 'error'});
                        }
                        else{
                            if(time1 > time2){
                                this.$message({showClose: true, message: '开启时间不能早于当前时间！', type: 'error'});
                            }else{
                                if(time3 == time2){
                                    this.$message({showClose: true, message: '并不支持只有零分钟的投票=-=', type: 'error'});
                                }
                                else{
                                    this.$axios
                                        .post('/admin/addVote', this.$qs.stringify(form), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                                        .then(response => {
                                            withCredentials: true
                                            if (response.data.code == 0) {
                                                this.$message({showClose: true, message: '发起投票成功！', type: 'success'});
                                                this.$router.push('./ManageVote');
                                                return true;
                                            } else if(response.data.code == 500500){
                                                this.$message({showClose: true, message: '已有相同题目投票！', type: 'error'});
                                                return false;
                                            }else if(response.data.code == 500505){
                                                this.$message({showClose: true, message: '选项重复！', type: 'error'});
                                                return false;
                                            }
                                            else{
                                                this.$message({showClose: true, message: '投票发起失败！', type: 'error'});
                                                return false;
                                            }
                                        })
                                        .catch(error => {
                                            console.log(error)
                                        })
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                });
            }
        }
    }
</script>

<style scoped>
  .usera{
    text-align: left;
    display: inline-block;
    font-size: 25px;
    margin-top: 20px;
    margin-left: 20px;
    width: 60%;
    height: auto;
  }
</style>
