<template>
  <el-container>
    <el-header>
      <div>
        <router-link to="/first">
          <div class="logo" style="float: left; color: black">
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
     <el-carousel :interval="4000" type="card" height="200px">
       <el-carousel-item v-for="item in imagesbox" :key="item">
         <img :src="item.idview" class="image" style="backgroundSize:cover">
       </el-carousel-item>
     </el-carousel>
        <div v-for="(site,index) in sites" :key="index" style="margin-bottom: 20px">
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>{{site.title}}</span>
                <el-button  style="float:right; padding: 3px 0" type="primary" @click="judge(site)">查看</el-button>
            </div>
            <div class="text item">简介    ：{{site.detail}}</div>
            <div class="text item">投票人数：{{site.counts}}</div>
            <div class="text item">开始时间：{{site.start}}</div>
            <div class="text item">结束时间：{{site.end}}</div>
          </el-card>
      </div>
    </el-main>
    <el-footer>在线投票系统</el-footer>
  </el-container>
</template>

<script>
  import moment from "moment";
    export default {
        name: "first",
        data() {
            return{
                imagesbox:[
                    {id:0,idview:require("../../assets/image15.jpg")},
                    {id:1, idview: require("../../assets/image16.jpg")},
                    {id:2, idview:require("../../assets/image17.jpg")}
                ],
                showCancel:false,
                currentDate:new Date(),
                sites:[],
            };
        },
        created:function(){
         var data = []
            var a=[]
            var b =[]
         this.$axios
             .post('/vote/list')
             .then(response =>{
                 console.log(response.data)
                 if(response.data.code == 0){
                     withCredentials:true
                     this.sites = response.data.data
                     this.a=response.data.data[0].start
                     this.b=response.data.data[1].end
                     console.log('111111' + this.sites)

                 }
                 else{
                     this.$message({showClose:true,message:'数据加载失败！',type:'error'});
                     return false;
                 }
             })
             .catch(error =>{
                 console.log(error)
             })
        },
        methods:{
            hideCancel(){
                this.showCancel = false;
            },
            switchShow(val){
                this.showCancel = val;
            },
            judge(site){
               var time = moment(new Date()).format("YYYY-MM-DD hh:mm:ss")
                var al = new Date(time).getTime();
                var bl = new Date(site.start).getTime();
                var cl = new Date(site.end).getTime();
                if(al < bl){
                    this.$message.error("投票未开始！");
                    return false;
                }
                else if((al >= bl) && (al <= cl)){
                    var username = localStorage.getItem("key");
                    localStorage.setItem("key","username");
                    this.$router.push({path:'/vote',query:{id:site.id}});
                }
                else if(al > cl){
                    this.$router.push({path:'/details',query:{id:site.id}});
                }
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
    };
</script>

<style scoped>
  .el-header{
    /*background-color: #B3C0D1;*/
    background: url("../../assets/grey1.jpg");
    text-align: center;
    line-height: 60px;
  }

 .el-footer{
    background-color: #646464;
    text-align: center;
    line-height: 60px;
  }
  .el-main{
   /* background: url("../../assets/image11.jpg");*/
    color: #333;
    text-align: center;
    line-height: 20px;
  }
  body > .el-container{
    margin-bottom: 40px;
  }

  .el-carousel__item h3{
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 80px;
    margin: 0;
  }


  a {
    text-decoration: none;
    color: black;
  }

  .el-carousel__item:nth-child(2n){
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1){
    background-color: #d3dce6;
  }

  .text {
    font-size: 14px;
    color: #333333;
  }


  .router-link-active {
    text-decoration: none;
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
    background: url("../../assets/grey3.jpg");
    margin: 0 auto;
    min-width: 500px;
  }
</style>
