import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

/**
 * this.$store.commit('setTagList', this.tagsList);
 * alert(JSON.stringify(this.$store.state))
 */
export default new Vuex.Store({
  state: {
  	tagList: []
  },
  mutations: {
  	setTagList(state, tagList) {
  		state.tagList = tagList;
  	}
  }
})
