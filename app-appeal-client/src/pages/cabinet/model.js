import {getDay} from "../service";
export default ({
  namespace: 'cabinetModel',
  state: {
    resCabinet: [],
    page: 0,
    size: 5,
    totalElements: 0,
    totalPages:0,
    staffCount:0,
    murojaatlarCountDay:0,
    murojaatlarAllCount:0,
    murojaatlarChecked:0,
    murojaatlarNoChecked:0,
    murojaatlarCheckingNow:0,
    dashBoardPage:false
  },
  subscriptions: {},
  effects: {
    *getDay({},{call,put}){
      let res = yield call(getDay);
      if (res.success){
        yield put({
          type: 'updateState',
          payload: {
            staffCount:res[0],
            murojaatlarCountDay:res[1],
            murojaatlarAllCount:res[2],
            murojaatlarChecked:res[3],
            murojaatlarNoChecked:res[4],
            murojaatlarCheckingNow:res[5]
          }
        })
      }
    },
    * cabinet({payload}, {call, put, select}) {
      if (!payload) {
        let {page, size} = yield select(_ => _.cabinetModel);
        payload = {page, size}
      }
      let res = yield call( payload);
      if (res.success) {
        yield put({
          type: 'updateState',
          payload: {
            resCabinet: res.object,
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
