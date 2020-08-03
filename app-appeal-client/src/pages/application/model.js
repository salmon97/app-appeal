import {deleteApplication, getApplication} from "../service";

export default ({
  namespace: 'applicationModel',
  state: {
    page:0,
    size:10,
    totalElements: 0,
    totalPages: 0,
    applications:[],
    showMainApplicationModal:false,
    mainApplication:{}
  },
  subscriptions: {},
  effects: {
    *deleteApplication({payload},{call,put,select}){
     const {applications} = yield select(_=>_.applicationModel);
      let res = yield call(deleteApplication,payload);
      if (res.success) {
        applications.splice(applications.findIndex(item => item.id.toString===res.message),1);
        yield put({
          type: 'updateState',
          payload: {
            applications: applications
          }
        })
      }
    },
    *getApplication({payload},{call,put,select}){
      if (!payload) {
        let {page, size} = yield select(_ => _.applicationModel);
        payload = {page, size}
      }
      let res = yield call(getApplication,payload);
      if (res.success){
        yield put({
          type: 'updateState',
          payload: {
            applications: res.object,
            page: res.page,
            size: res.size,
            totalElements: res.totalElements,
            totalPages: res.totalPages
          }
        })
      }
    },
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
