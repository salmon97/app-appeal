import {deleteStaff, editStaff, getDay, getStaff} from "../service";
export default ({
  namespace: 'staffModel',
  state: {
    staffs:[],
    staff:{},
    showModal:false,
    showDeleteModal:false,
  },
  subscriptions: {},
  effects: {
    *editStaff({payload},{call,put,select}){
      let res = yield call(editStaff,payload);
      let {staffs}  =  yield select(_=>_.staffModel);
      if (res.success){
        yield put({
          type: 'updateState',
          payload: {
            staffs: staffs.map(item => {
              if (item.id===res.object.id) {
                item = res.object
              }
              return item;
            })
          }
        });
      }
    },
    *deleteStaff({payload},{call,put,select}){
      let {staffs}  =  yield select(_=>_.staffModel);
      let res = yield call(deleteStaff,payload);
      if(res.success){
        staffs.splice(staffs.findIndex(item => item.id===res.message),1);
        yield put({
          type: 'updateState',
          payload: {
            staffs: staffs
          }
        })
      }
    },
    *getStaff({},{call,put}){
      let res = yield call(getStaff);
      if (res.success){
        yield put({
          type: 'updateState',
          payload: {
            staffs: res.object
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
