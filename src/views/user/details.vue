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
     <!-- <div class="size" align="center">nt
        <el-card class="box-card">
          <p>{{this.title}}</p>
          <p>{{this.synopsis}}</p>
        </el-card>
      </div>-->
      <div class="out">
      <div class="size">
          <div class="text item">{{this.title}}</div>
          <div class="text item">{{this.detail}}</div>
      </div>
      <div id="myChart" class="tu">
      </div>
      </div>
    </el-main>
    <el-footer style="color: #B3C0D1; font-family: 幼圆;background-color: #646464;">在线投票系统</el-footer>
  </el-container>

</template>

<script>
    export default {
        data(){
            return{
                title:'',
               detail:''
            }
        },
        mounted(){
          this.searchHead()
            this.drawline()
        },
        methods:{
            drawline(){
                var id = this.$route.query.id
                let myChart = echarts.init(document.getElementById('myChart'))
                this.$axios
                    .post('/vote/detailHistogram',this.$qs.stringify({id:id}),{headers:{'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response =>{
                    if(response.data.code == 0){
                        withCredentials:true
                        myChart.setOption({
                            color: ['#3398DB'],
                            tooltip : {
                                trigger: 'axis',
                                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                }
                            },
                       /* this.data = []
                        this.data = response.data.data
                        console.log("data--"+JSON.stringify(this.data))
                        this.type = []
                        for(let i = 0; i < response.data.data.length; i++){
                            this.type[i] = response.data.data[i].name
                        }
                        console.log("type--+this.type")*/
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            xAxis : [
                                {
                                    type : 'category',
                                    data :  response.data.data.options,
                                    axisTick: {
                                        alignWithLabel: true
                                    }
                                }
                            ],
                            yAxis : [
                                {
                                    type : 'value'
                                }
                            ],
                            series : [
                                {
                                    name:'投票人数S',
                                    type:'bar',
                                    barWidth: '50%',
                                    data:response.data.data.nums
                                }
                            ]
                        });

                    }
                    else{
                        this.$message({showClose:true,message: '柱状图数据加载失败！', type:'error'});
                        return false
                    }
                })
                    .catch(error =>{
                        console.log(error)
                    })
            },
            out(){
                var username = '';
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
            searchHead(){
                var id = this.$route.query.id
                this.$axios
                    .post('/vote/detailOne',this.$qs.stringify({id:id}), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response =>{
                        if(response.data.code == 0){
                            withCredentials: true
                            this.title = response.data.data.title
                            this.detail = response.data.data.detail
                            return true;

                        }
                        else if(response.data.code == 1){
                            this.$message({showClose: true, message: '数据加载失败！', type:'error'});
                            return false;
                        }
                        else{
                            this.$message({showClose: true, message:'数据加载失败！', type:'error'});
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
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-footer{
    background-color: #646464;
  }

  a {
    text-decoration: none;
    color: black;
  }

  .router-link-active {
    text-decoration: none;
  }

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background: url("../../assets/grey4.jpg");
    color: #333;
    text-align: center;
    line-height: 100px;
    height: 500px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }



.tu{
  width: 450px;
  height: 400px;
  float:right;
  margin-right: 70px;
}
.out{
  width: 80%;
  background-color: #42b983;
  margin: 0 auto;
}
  .size{
    width: 400px;
    height: 400px;
    float: left;
    margin-left: 50px;
  }

  .box-card{
    width: 80%;
    height: 80%;
  }
</style>
