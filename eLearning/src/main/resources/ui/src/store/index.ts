import { createStore } from 'vuex'

export default createStore({
  state: {
    session: null,
    general: null,
    involve: null,

    // Added store elements
    training: null,
    lesson: null,
  },
  getters: {

    getCurrentSession(state) {
       return state.session ;
    },
    getGeneral(state) {
      return state.general;
    },
    getInvolve(state) {
      return state.involve;
    },
  },
  mutations: {
    setCurrentSession(state, session) {
        state.session = session ;
    }, 
    setGeneral(state, general) {
       state.general = general;
    },
    setInvolve(state, item) {
       state.involve = item;
    },
  },
  actions: {
  },
  modules: {
  }
})
