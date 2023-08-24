import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

/**
 * usage:
 * refresh page will lose store
 * this.$store.commit('setSection', newValue);
 * alert(JSON.stringify(this.$store.state))
 */
export default new Vuex.Store({
  state: {
  	section: {},
  	openRecord: {}
  },
  mutations: {
  	setSection(state, newValue) {
  		state.section = newValue;
  	},
  	setOpenRecord(state, newValue) {
  		state.openRecord = newValue;
  	}
  }
})
