import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

//状态
const state = {
  userState: null,
};

//方法
const mutations = {
  changeUserState(state, userState) {
    state.userState = userState;
  },
};

export default new Vuex.Store({
  state, mutations
});
