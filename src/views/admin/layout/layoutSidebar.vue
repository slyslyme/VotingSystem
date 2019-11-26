<template>
  <aside class="sidebar" :class="{'sidebar-hide': !openNav}">
    <vue-scroll>
      <el-menu :default-active="$route.path" class="sidebar-menu" :collapse="!openNav" @select="menuSelect" :collapse-transition="false"
               background-color="#545c64"
               text-color="#fff"
               active-text-color="#ffd04b"
      >
        <template v-for="(item, index) in $router.options.routes" v-if="item.menu">
          <!-- 一级菜单 -->
          <el-menu-item v-if="item.children.length === 1" :index="item.children[0].path" :key="index">
            <i :class="item.children[0].icon"></i>
            <span slot="title">{{item.children[0].name}}</span>
          </el-menu-item>
          <el-submenu :index="index+''" v-if="item.children.length > 1" :key="index+''">
            <template slot="title">
              <i :class="item.icon"></i>
              <span slot="title">{{item.name}}</span>
            </template>
            <template v-for="(item2, index2) in item.children">
              <el-menu-item v-if="!item2.children" :index="item2.path" :key="index+'-'+index2">
                {{item2.name}}
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
      </el-menu>
    </vue-scroll>
  </aside>
</template>

<script>
    export default {
        name: 'layoutSidebar',
        props: ['openNav'],
        methods: {
            menuSelect (index) {
                this.$router.push(index)
            }
        }
    }
</script>

<style scoped lang="scss">
  .sidebar {
    width: 240px;
    position: absolute;
    top: 0;
    bottom: 0;
    border-right: 1px solid #e6e6e6;
    background-color:#545c64;
    .sidebar-menu {
      border: none;
      height: 100%;
    }
  }
  .sidebar-hide {
    width: 65px;
  }
</style>
