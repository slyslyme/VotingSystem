<template>
  <div class="header-wrapper">
    <div class="title-wrapper">
      <div class="logo"></div>
      <h2 class="title">Title</h2>
    </div>
    <div class="info-wrapper">
      <span class="username">你好，管理员！</span>
      <span class="cancel" @click.stop="switchCancelBoard">
        <div class="cancel-div" v-show="ifShowCancel">
          <ul>
            <li @click.stop="doSomething" title="用户设置">设置 </li>
            <li @click.stop="doSomething" title="退出登录">退出 </li>
          </ul>
        </div>
      </span>
    </div>
  </div>
</template>

<script>
    export default {
        name: "t-header",
        props:{
            showCancel:{
                type:Boolean
            }
        },
        methods:{
            doSomething(){},
            switchCancelBoard(){
                rhis.$emit("switch-show", !this.ifShowCancel);
            }
        },
        computed:{
            ifShowCancel(){
                return this.showCancel;
            }
        }
    };
</script>

<style long="stylus" rel="stylesheet/stylus" scoped>
.header-wrapper{
background:#1c60d1;
  color:#fff;
  width:100%;
  height:50px;
  line-height:50px;
  position:fixed;
  top:0px;
  left:0px;
  font-size:0;
}

  .title-wrapper{
    display:block;
    position:relative;
    float:left;
    height: 50px;
  }

  .logo{
    display: inline-block;
    background-image: url();
    background-size: 30px 30px;
    background-repeat: no-repeat;
    width:30px;
    height: 30px;
    mar-top: 10px;
  }

  .title{
    display: inline-block;
    font-size: 16px;
    height: 50px;
    line-height: 50px;
    margin: 0px auto 0px 16px;
    font-width: normal;
    vertical-align: top;
  }

  .info-wrapper{
    display: block;
    position: relative;
    float: right;
    height: 50px;
    width: 160px;
    font-size: 0;
  }

.username{
  display: inline-block;
  height: 50px;
  line-height: 50px;
  vertical-align: top;
}

  .cancel{
    display: inline-block;
    vertical-align: middle;
    background-image: url("");
    background-size: 32px 32px;
    cursor: pointer;
    background-repeat: no-repeat;
    width: 32px;
    height: 32px;
  }

  .cancel-div{
    position: absolute;
    display: block;
    width: 60px;
    height: 80px;
    background: #fff;
    z-index: 50;
    top: 40px;
    right: 16px;
    font-size: 14px;
    color: #646464;
    box-shadow: 1px 1px 8px rgba(0, 0, 0, 0.4);
  }

  ul{
    padding-left: 0px;
    margin: 0px;
  }

  li{
    width: 100%;
    height: 40px;
    line-height: 40px;
    text-align: center;
    list-style-type: none;
  }
  &:hover{
  background-color: #eaeaea;
   }
</style>
