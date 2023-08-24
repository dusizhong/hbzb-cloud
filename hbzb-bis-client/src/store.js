import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

/**
 * usage:
 * this.$store.commit('setOpenRecord', newValue);
 * alert(JSON.stringify(this.$store.state))
 */
export default new Vuex.Store({
  state: {
  	openRecord: {}
  },
  mutations: {
  	setOpenRecord(state, newValue) {
  		state.openRecord = newValue;
  	}
  }
})
