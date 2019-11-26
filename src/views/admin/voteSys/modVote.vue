<template>
  <div class="app">
    <el-card class="usera">
      <div>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm"
        >
          <h3 class="title" style="text-align: center">修改投票信息</h3>
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
              style="width: 74%"
              v-model="domain.value" ></el-input>
            <el-button
              style="float: right;width:25%"
              @click.prevent="removeDomain(domain)">删除</el-button>
          </el-form-item>

          <el-form-item style="width: 100%; text-align: center">
            <el-button @click="addDomain">新增选项</el-button>
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
        mounted() {

            var id = this.$route.query.id;
            this.messegeSearch(id);
        },
        methods: {
            messegeSearch(id){
                this.$axios
                    .post('/admin/detailVote', this.$qs.stringify({id:id}), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response => {
                        withCredentials: true
                        //console.log(id)
                        if (response.data.code == 0) {
                            this.ruleForm.id = response.data.data.voteDetail.id;
                            this.ruleForm.name = response.data.data.voteDetail.title;
                            this.ruleForm.desc = response.data.data.voteDetail.detail;
                            this.ruleForm.date1 = response.data.data.voteDetail.start;
                            this.ruleForm.date2 = response.data.data.voteDetail.end;
                            if(response.data.data.voteDetail.type == '0'){
                                this.ruleForm.region = "单选"
                            }else{
                                this.ruleForm.region = "多选"
                            }
                            var iz = response.data.data.voteOptions.length;
                            this.ruleForm.domains[0].value = response.data.data.voteOptions[0].optiondesc
                            //console.log(response.data.data.voteOptions[0].optiondesc)
                            for(var j = 1;j < iz;j++){
                                i = i + 1;
                                this.ruleForm.domains.push({
                                    value: response.data.data.voteOptions[j].optiondesc
                                });
                            }
                            return true;
                        }
                        else{
                            this.$message({showClose: true, message: '失败！', type: 'error'});
                            return false;
                        }
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
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
                        form.id = this.ruleForm.id;
                        form.title = this.ruleForm.name;
                        if(this.ruleForm.region == "单选")
                            form.type = '0'
                        else if(this.ruleForm.region == "多选") form.type = '1'
                        form.start = this.ruleForm.date1;
                        form.end = this.ruleForm.date2;
                        form.detail = this.ruleForm.desc;
                        var x =this.ruleForm.domains.length
                        form.voteOptions = []
                        var j
                        console.log(x);
                        for(j = 0;j < x;j++){
                            form.voteOptions[j] = this.ruleForm.domains[j].value
                            //console.log(form.voteOptions[j])
                        }
                        //console.log(form)
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
                                        .post('/admin/updateVote', this.$qs.stringify(form), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                                        .then(response => {
                                            withCredentials: true
                                            if (response.data.code == 0) {
                                                this.$message({showClose: true, message: '修改投票成功！', type: 'success'});
                                                this.$router.push('./ManageVote');
                                                return true;
                                            } else if(response.data.code == 500505){
                                                this.$message({showClose: true, message: '投票选项重复！', type: 'error'});
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
    width: 55%;
    height: auto;
  }
</style>
