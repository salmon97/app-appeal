export default ({
  namespace: 'profilModel',
  state: {
    registerCoed:''
  },
  subscriptions: {},
  effects: {

  },
  reducers: {
    updateState(state, {payload}) {
      return {
        ...state,
        ...payload
      }
    }
  }
})
