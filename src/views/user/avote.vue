<template>
  <el-container>
    <el-header>
      <div>
        <router-link to="/first">
          <div class="logo" style="float: left">
            <span class="text">在线投票系统</span>
          </div>
        </router-link>
        <div style="float: right">
          <el-dropdown trigger="hover" class="user">
      <span class="user-info">
        <i class="el-icon-s-custom" style="margin-left: 10px"></i>
      </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <router-link to="/changePassworld">
                  修改密码
                </router-link>
              </el-dropdown-item>
              <el-dropdown-item divided>
                <el-link :underline="false" @click="out()">退出登录</el-link>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>
    </el-header>
    <el-main>
      <el-form ref="options" :model="options" label-position="left" label-width="0px">
        <el-card class="box-card">
          <div slot="header" >
            <span>{{this.title}}</span>
          </div>
          <div v-if="voteStatus==1">
           <template>
             <el-form-item prop="checked">
               <div v-for="(choice, index) in options.list" :key="index" class="text item">
                    <el-radio disabled v-model="options.checked" label="禁用">{{choice.optiondesc}}</el-radio>
               </div>
               <div v-for="(site, index) in options.sites" :key="index" class="ta" >
                 <p>已选择：{{site.optiondesc}}</p>
               </div>
             </el-form-item>
           </template>
          </div>
          <div style="float: left">
          <div v-if="voteStatus==0">
            <div v-if="this.options.typ==0">
              <el-form-item prop="checked1">
                  <el-radio-group v-model="options.checked1">
                    <div v-for="(choice, index) in options.list" :key="index" class="text item">
                      <el-radio :label= index name="checked1" v-model=index>{{choice.optiondesc}}</el-radio>
                    </div>
                  </el-radio-group>
              </el-form-item>
            </div>
            <div v-if="this.options.typ==1">
              <el-form-item prop="checked">
                  <el-checkbox-group v-model="options.checked">
                    <div v-for="(choice, index) in options.list" :key="index" class="text item">
                    <el-checkbox :label=index name="checked">{{choice.optiondesc}}</el-checkbox>
                    </div>
                  </el-checkbox-group>
              </el-form-item>
              </div>
          </div>
          </div>
        </el-card>
      <div class="out">
        <el-form-item style="width:100%;">
          <div class="rightbtn">
              <el-button type="primary"  @click="submit('options')">
                投票
              </el-button>
          </div>
          <div class="returnbtn">
              <el-button  type="primary" @click="change()">
                查看详情
              </el-button>
          </div>
        </el-form-item>
      </div>
      </el-form>
    </el-main>
    <el-footer style="font-family: 幼圆; color: #B3C0D1">在线投票系统</el-footer>
  </el-container>
</template>

<script>
    export default {
        name: "avote",
        data(){
            return{
                id2:'',
                id3:'',
                radio:'禁用',
                typ:'',
                voteStatus:'',
                title: '',
                index: '',
                options:{
                    id:'',
                    name:'',
                    checked: [],
                    checked1: [],
                    list:[],
                    option:[],
                    sites:[]
                },
            }
        },
        mounted(){
            this.search()
        },
        methods:{
            search(){
                var id1 = this.$route.query.id
                this.id2 = id1
                console.log("~~~--" + this.id2)
               console.log('7777' + id1)
                this.$axios
                    .post('/vote/detail',this.$qs.stringify({id:id1}),{headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response =>{
                        if(response.data.code == 0){
                            withCredentials:true
                            this.id3 = response.data.data.id
                            this.options.id1 = response.data.data.voteDetail.id
                            this.options.list = response.data.data.voteOptions
                            this.options.typ = response.data.data.voteDetail.type
                            this.title = response.data.data.voteDetail.title
                            this.voteStatus = response.data.data.voteStatus
                            this.options.sites = response.data.data.choiced
                            console.log("status:  " + this.voteStatus)
                            console.log("wqqeww" + this.options.typ)
                            console.log("wefe" + this.title)
                            console.log("dsvdfdgvf    "+this.options.id)
                            console.log("sfsfdsvd     +"+this.options.sites[0].optiondesc)
                            return true;
                        }
                        else if(response.data.code == 1){
                            this.$message({showClose:true,message:'数据加载失败！',type:'error'});
                            return false;
                        }else{
                            this.$message({showClose:true,message:'数据加载失败！',type:'error'});
                            return false;
                        }
                    })
            },
            out(){
                var username = localStorage.getItem("key")
                this.$axios
                    .post('/vote/logout',this.$qs.stringify({username: username}),{headers:{'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response =>{
                        if(response.data.code == 0){
                            this.$router.push({path:'/login'});
                            return true;
                        }
                        else if(response.data.code == 1){
                            this.$message.error("数据加载失败！");
                            return false;
                        }
                        else{
                            this.$message.error("数据加载失败！");
                            return false;
                        }
                    })
                    .catch(error =>{
                        console.log(error)
                    })
            },
            change(){
                this.$router.push({path:'/details',query:{id:this.id2}});
            },
            submit(formName){
                var obj = {}
                obj.voteOptions = [];
                obj.id = this.id2;
                var num = 0
                console.log('类型  ' + this.options.typ)
                if(this.voteStatus == 0){
                    if(this.options.typ == 0){
                        var arr = document.getElementsByName("checked1")
                        console.log('arr    '+arr.length)
                        console.log('ddds      '+this.options.checked1)
                        if(this.options.checked1){

                            obj.voteOptions[num] = this.options.checked1
                            console.log('选中的index  ' + this.options.checked[num])
                            if(obj.voteOptions=='' || obj.voteOptions==null)
                                num=0;
                            else
                                num = 1;
                        }

                    }
                    else if(this.options.typ == 1){
                        var arr = document.getElementsByName("checked")
                        for(var i = 0; i < arr.length; i++){
                            if(arr[i].checked){
                                obj.voteOptions[num] = this.options.checked[num]
                                console.log('选中的index  ' + this.options.checked[num])
                                num++;
                            }
                        }
                    }
                    console.log('个数    ' + num)
                    if(num >= 1){
                        this.$refs[formName].validate((valid) => {
                            if (valid) {
                                this.$axios
                                    .post('/vote/choice', this.$qs.stringify(obj),{headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                                    .then(response =>{
                                        withCredentials:true
                                        console.log("111" + response.data.msg);
                                        if(response.data.code == 0){
                                            this.$router.push({path:'/details',query:{id:this.id2}});
                                            return true;
                                        }
                                        else if(response.data.code == 1){
                                            this.$message.error("数据加载失败！");
                                            return false;
                                        }
                                        else{
                                            this.$message.error("数据加载失败！");
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
                    else{
                        this.$message.error("请选择选项！");
                        return false;
                    }
                }
                else if(this.voteStatus == 1){
                    this.$message.error("您已进行选择，不能在提交！");
                }
            }
        }
    }
</script>

<style scoped>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }


  a {
    text-decoration: none;
    color: black;
  }

  .router-link-active {
    text-decoration: none;
  }

  .ta{
    margin-bottom: 0px;
    margin-left: 150px;
    float: left;
  }

  .el-main {
    background: url("../../assets/grey2.jpg");
    color: #333;
    text-align: center;
    line-height: 40px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .size{
    width: 90%;
    height:60%;
  }


  .el-footer{
    background-color: #646464;
    text-align: center;
    line-height: 60px;
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
  .text {
    font-size: 14px;
    color: #333333;
  }

  .item {
    margin-bottom: 18px;
    margin-left: 150px;
    float: left;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
    margin: 0 auto;
  }

  .out{
    width: 400px;
    margin: 0 auto;
  }

</style>
