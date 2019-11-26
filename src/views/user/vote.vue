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
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>{{this.title}}</span>
        </div>
        <div class="text item">投票人数：{{this.counts}}</div>
        <div class="text item">简介：{{this.detail}}</div>
        <div class="text item">开始时间：{{this.start}}</div>
        <div class="text item">结束时间：{{this.end}}</div>
      </el-card>
      <div class="allBtn">
      <div class="rightbtn">
        <router-link :to="{path:'/avote',query: {id:this.id1}}">
          <el-button type="primary"  label>
            投票
          </el-button>
        </router-link>
      </div>
      <div class="returnbtn">
          <el-button  type="primary">
            返回
          </el-button>
      </div>
      </div>
    </el-main>
    <el-footer style="color: #B3C0D1; font-family: 幼圆;background-color: #646464;">在线投票系统</el-footer>
  </el-container>
</template>

<script>
    export default {
        name: "vote",
        data(){
            return{
                title:'',
                counts:'',
                end:'',
                detail:'',
                start:'',
                id1:''
            }
        },
        mounted(){
            this.searchMain()
        },
        methods:{
            searchMain(){
                var username = localStorage.getItem("key");
                localStorage.setItem("key", "username");
                var id = this.$route.query.id
                this.$axios
                    .post('/vote/detailOne',this.$qs.stringify({id:id}),{headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response =>{
                        if(response.data.code == 0){
                            withCredentials:true
                            console.log(response.data);
                            this.title = response.data.data.title
                            this.detail = response.data.data.detail
                            this.counts = response.data.data.counts
                            this.end = response.data.data.end
                            this.start = response.data.data.start
                            this.id1 = response.data.data.id
                            console.log('dsdsds' + this.id1)
                            return true
                        }
                        else{
                            this.$message({showClose: true,message: '数据加载失败！',type: 'error'});
                            return false
                        }
                    })
                    .catch(error =>{
                        console.log(error)
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
            }
        }
    }
</script>

<style scoped>
  .el-header{
    /*background-color: #B3C0D1;*/
    background: url("../../assets/grey1.jpg");
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-footer{
    background-color: #646464;
    text-align: center;
    line-height: 60px;
  }

  .el-main {
    color: #333;
    text-align: center;
    line-height:30px;
  }

  a {
    text-decoration: none;
    color: black;
  }

  .router-link-active {
    text-decoration: none;
  }

  .allBtn{
    width: 400px;
    height: 60px;
    margin: 0 auto;
  }

  body > .el-container {
    margin-bottom: 40px;
  }


  .text {
    font-size: 14px;
    color: #333333;
  }

  .item {
    margin-bottom: 18px;
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
    width: 80%;
    min-width: 500px;
    height:400px;
    background: url("../../assets/image12.jpg");
    margin: 0 auto;
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
