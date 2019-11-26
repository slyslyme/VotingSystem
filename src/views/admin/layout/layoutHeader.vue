<template>
  <el-header class="header" >
    <router-link to="/adminFirst">
      <div class="logo" :class="{'logo-hide': !openNav}">
        <span class="text" style="color: #ffd04b">VoteSystem</span>
      </div>
    </router-link>
    <div class="content">
      <i class="fa fa-align-justify toggle" @click="navOpenToggle" title="显示/隐藏菜单"></i>
    </div>
    <el-dropdown  trigger="click" class="user" @command="handleCommand">
      <span class="user-info">
        欢迎， {{this.user}}
        <i class="fa fa-user-circle-o fa-2x" style="margin-left: 10px"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="b">修改密码</el-dropdown-item>
        <el-dropdown-item command="a">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </el-header>
</template>

<script>
    export default {
        name: 'layoutHeader',data() {
            return{
                user:''
            }
        },
        props: ['openNav'],
        mounted(){
            var lastname = localStorage.getItem("key");
            this.user = lastname
        },
        methods: {
            navOpenToggle () {
                this.$emit('toggle-open')
            },
            handleCommand(command) {
                console.log('emmm')
                if(command == 'a'){
                    var lastname = localStorage.getItem("key");
                    console.log(lastname)
                    this.$axios
                        .post('/vote/logout',this.$qs.stringify({username:lastname}), {headers: {'Content-Type': "application/x-www-form-urlencoded"}})
                        .then(response => {
                            withCredentials: true
                            if(response.data.code == 0){
                                this.$router.push('/')
                            }
                        })
                        .catch(error => {
                            console.log(error)
                        })
                }else if(command == 'b'){
                    console.log('changepass')
                                this.$router.push('/account/changePassword')
                }
            },
        }
    }
</script>

<style scoped lang="scss">
  .header {
    line-height: 60px;
    background:#545c64;
    text-align:left;
    div {
      display: inline-block;
    }
    .logo {
      width: 240px;
      border-right: 1px solid #C0C4CC;
      margin-left: -20px;
      text-align: center;
      font-size: 25px;
      cursor: pointer;
      .image {
        width: 40px;
        height: 40px;
        vertical-align: middle;
      }
    }
    .logo-hide {
      width: 65px;
      .text {
        display: none;
      }
    }
    .content {
      padding: 0 20px;
      .toggle {
        font-size: 14px;
        cursor: pointer;
      }
    }
    .user {
      float: right;
      cursor: pointer;
      .user-info {
        color: #ffffff;
        i {
          vertical-align: middle;
        }
      }
    }
  }
</style>
