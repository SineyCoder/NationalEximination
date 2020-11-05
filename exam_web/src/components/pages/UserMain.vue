<template>
    <div class="userMain">
      <BackTop></BackTop>
      <Layout class="layout">
        <Header>
          <Menu mode="horizontal" :theme="theme" active-name="1" @on-select="menuSelect">
            <MenuItem name="1">
              <Icon type="ios-alert-outline" />
              主页
            </MenuItem>
          </Menu>
        </Header>
        <Content>
          <transition name="moveR" mode="out-in">
            <router-view name="content"/>
          </transition>
        </Content>
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
          path:['/userInfo'],
        }
      },
      computed:{
        ...mapState(['userState'])
      },
      methods:{
        menuSelect(name){
          this.$router.push(this.path[parseInt(name) - 1])
        }
      }
    }
</script>

<style scoped>
  .userMain{
    width: 80%;
    min-width: 1024px;
    margin: 0 auto;
  }
  .layout{
    min-height: 100vh;
  }
</style>
