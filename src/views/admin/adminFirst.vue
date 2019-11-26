<template>
  <div class="app">
        <el-container>
          <el-main class="app-body">
            <template>
              <router-view/>
              <div style="margin-left: 50px;width: 1000px;">
              <el-card class="box-card">
                  <span>当前系统用户数量:</span>
                  <div class="text item" style="font-size: 20px;color: #545c64"> {{this.peopleNum}}</div>
              </el-card>
              <el-card class="box-card" style="margin-right: 1%;margin-left: 1%;">
                  <span>当前系统投票数量:</span>
                  <div class="text item" style="font-size: 20px;color: #545c64"> {{this.detail}}</div>
              </el-card>
              <el-card class="box-card">
                  <span>最近一周投票量:</span>
                  <div class="text item" style="font-size: 20px;color: #545c64"> {{this.detailss}}</div>
              </el-card>
              </div>
              <el-card id="myChart" class="tu">
              </el-card>
            </template>
          </el-main>
        </el-container>
  </div>
</template>
<script type="text/javascript">
    export default {
        name: 'adminFirst',
        data() {
            return{
                peopleNum:'',
                detail:'',
                detailss:''
            }
        },
        mounted(){
            var formall = {};
            this.drawline(formall)
        },
        methods:{
            drawline(formall){
                var doc = document.getElementById('myChart');
                let myChart = echarts.init(doc);
                this.$axios
                    .post('/admin/index',this.$qs.stringify(formall),{headers:{'Content-Type': "application/x-www-form-urlencoded"}})
                    .then(response =>{
                       // console.log(response.data.data.peopleNum)
                        if(response.data.code == 0){
                            withCredentials:true
                            this.peopleNum = response.data.data.peopleNum
                            console.log(this.peopleNum)
                            this.detail = response.data.data.voteNum
                            this.detailss = response.data.data.weekVoteNum
                            myChart.setOption({
                                title: {
                                    left: 'center',
                                    text: '最近七天用户登录情况',
                                },
                                tooltip: {
                                    trigger: 'axis',
                                    position: function (pt) {
                                        return [pt[0], '10%'];
                                    }
                                },
                                toolbox: {
                                    feature: {
                                        dataZoom: {
                                            yAxisIndex: 'none'
                                        },
                                        restore: {},
                                        saveAsImage: {}
                                    }
                                },
                                xAxis: {
                                    type: 'category',
                                    boundaryGap: false,
                                    data: response.data.data.weekDate
                                },
                                yAxis: {
                                    type: 'value',
                                    boundaryGap: [0, '100%']

                                },
                                series: [{
                                    name:'登录人数',
                                    symbol: 'none',
                                    sampling: 'average',
                                    itemStyle: {
                                        color: 'rgb(255, 70, 131)'
                                    },
                                    data: response.data.data.weekDateNum,
                                    type: 'line',
                                    areaStyle: {}
                                }]
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
        }
    }



</script>

<style scoped>
  div {
    display: inline-block;
  }
.item {
     margin-bottom: 18px;
   }
  .box-card {
    width: 32%;
  }
  .tu{
    width: 1000px;
    height: 400px;
    margin-left: 50px;
    margin-top: 30px;
  }
</style>
