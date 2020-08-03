import {OPEN_PAGES, OPEN_PAGES2, TOKEN_NAME} from "../contants/contants";
import router from "umi/router";
import {toast} from "react-toastify";
import {login, registerCode, userMe} from "../pages/service";

export default ({
  namespace: 'globalModel',
  state: {
    currentUser: '',
    isAdmin: false,
    registerCoed:''
  },
  subscriptions: {
    setupHistory({dispatch, history}) {
      history.listen((location) => {
        if (!OPEN_PAGES.includes(location.pathname)) {
          dispatch({
            type: 'userMe',
            payload: {
              pathname: location.pathname
            }
          })
        }
      })
    }
  },
  effects: {
    * userMe({payload}, {call, put}) {
      const res = yield call(userMe);
      if (!res.success) {
        console.log(payload.pathname);
        console.log(payload.pathname.split('/'));
        console.log(OPEN_PAGES2.includes(`/${payload.pathname.split('/')[1]}`));
        if (!OPEN_PAGES2.includes(payload.pathname)
          && !OPEN_PAGES2.includes('/' + payload.pathname.split('/')[1])) {
          localStorage.removeItem(TOKEN_NAME);
          router.push('/auth/login')
        }
      } else {
        yield put({
          type: 'updateState',
          payload: {
            currentUser: res,
            isAdmin:  !!res.roles.filter(item => item.roleName === 'ROLE_ADMIN').length,
          }
        })
      }
    },
    * login({payload}, {call, put}) {
      const res = yield call(login, payload);
      if (res.success) {
        localStorage.setItem(TOKEN_NAME, res.tokenType + res.token);
        router.push('/cabinet');
        // window.location.reload();
      }else {
        toast.error("неверный логин или парол");
      }
    },
    *registerCode({payload},{call,put}){
      yield call(registerCode,payload);
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
});
