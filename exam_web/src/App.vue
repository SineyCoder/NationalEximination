<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  overflow: hidden;;
}
.moveR-enter-active,  .moveR-leave-active {
  transition: all 0.3s;
  opacity: 1;
  transform: translateY(0);
}
.moveR-enter,  .moveR-leave {
  opacity: 0;
  transform: translateY(3px);
}
.moveR-leave-to{
  opacity: 0;
  transform: translateY(3px);
}

</style>
