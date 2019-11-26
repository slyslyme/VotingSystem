<template>
  <div align="center" style="width: 100%;margin:-20px">
    <el-container>
      <el-main class="app-body">
        <template>
          <router-view/>
      <el-card class="size">
        <div>
          <div class="text item" style="color: #333333;font-size: 30px;font-family: 华文隶书;text-align: center">{{this.title}}</div>
          <div style="height: 10px"></div>
          <div class="text item" style="font-size: 20px;color: #545c64"> {{this.detail}}</div>
        </div>
      </el-card>
      <el-card id="myChart" class="tu">
      </el-card>
        </template>
      </el-main>
    </el-container>
  </div>
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
                var doc = document.getElementById('myChart');
                let myChart = echarts.init(doc);
                this.$axios
                    .post('/admin/detailHistogram',this.$qs.stringify({id:id}),{headers:{'Content-Type': "application/x-www-form-urlencoded"}})
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

            searchHead(){
                var id = this.$route.query.id
                this.$axios
                    .post('/admin/detailVote',this.$qs.stringify({id:id}), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response =>{
                        if(response.data.code == 0){
                            withCredentials: true
                            console.log(response.data.data.voteDetail.detail)
                            this.title = response.data.data.voteDetail.title
                            this.detail = response.data.data.voteDetail.detail
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

  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 150px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

  .tu{
    width: 900px;
    height: 600px;
    margin-left: 150px;
    margin-top: 30px;
  }
  .size{
    width: 900px;
    height: auto;
    margin-left: 150px;
  }

  .box-card{
    width: 100%;
    height: 80%;
  }
</style>
