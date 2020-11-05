<template>
    <div class="adminMain">
      <BackTop></BackTop>
      <Layout class="layout">
        <Header style="position:relative;">
          <Dropdown trigger="click" style="position:absolute;right: 20px;" @on-click="select">
            <a href="javascript:void(0)">
              <Avatar src="https://i.loli.net/2017/08/21/599a521472424.jpg" />
              <Icon type="ios-arrow-down"></Icon>
            </a>
            <DropdownMenu slot="list">
              <DropdownItem name="exit">退出</DropdownItem>
            </DropdownMenu>
          </Dropdown>
        </Header>
        <Layout>
          <Sider>
            <Menu mode="vertical" :theme="theme" width="auto" active-name="1" @on-select="menuSelect" >
              <MenuItem name="1">
                <Icon type="ios-alert-outline" />
                主页
              </MenuItem>
              <MenuItem name="2">
                <Icon type="ios-add-circle" />
                添加内容
              </MenuItem>
              <MenuItem name="3">
                <Icon type="ios-add-circle" />
                管理内容
              </MenuItem>
              <!--<MenuItem name="2">
                <Icon type="ios-people" />
                用户管理
              </MenuItem>
              <Submenu name="3">
                <template slot="title">
                  <Icon type="ios-stats" />
                  统计分析
                </template>
                <MenuGroup title="使用">
                  <MenuItem name="3-1">新增和启动</MenuItem>
                  <MenuItem name="3-2">活跃分析</MenuItem>
                  <MenuItem name="3-3">时段分析</MenuItem>
                </MenuGroup>
                <MenuGroup title="留存">
                  <MenuItem name="3-4">用户留存</MenuItem>
                  <MenuItem name="3-5">流失用户</MenuItem>
                </MenuGroup>
              </Submenu>
              <MenuItem name="4">
                <Icon type="ios-construct" />
                综合设置
              </MenuItem>-->
            </Menu>
          </Sider>
          <Layout>
            <Content>
              <transition name="moveR" mode="out-in">
                <router-view name="content"/>
              </transition>
            </Content>
          </Layout>
        </Layout>
      </Layout>
    </div>
</template>

<script>
  import {mapState, mapMutations} from 'vuex';
    export default {
      name: "Main",
      data () {
        return {
          theme: 'dark',
          path:['/admin/adminInfo', '/admin/addQuestion', '/admin/questionAdmin'],
        }
      },
      computed:{
        ...mapState(['userState'])
      },
      methods:{
        ...mapMutations(['changeUserState']),
        select(name){
          if(name === 'exit'){
            this.changeUserState(null);
            this.$router.replace('/');
          }
        },
        menuSelect(name){
          this.$router.push(this.path[parseInt(name) - 1])
        }
      }
    }
</script>

<style scoped>
  .adminMain{
    width: 100%;
    min-width: 1024px;
    margin: 0 auto;
  }
  .layout{
    min-height: 100vh;
  }
</style>
