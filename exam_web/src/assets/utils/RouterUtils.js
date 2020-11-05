import {check} from "../api/request";
import ViewUI from 'view-design';
export default {
  isAllowed(toPath, store, next) {
    if(toPath === '/' || toPath === '/register'){
      next();
    }else{
      if(store.state.userState == null){
        check()
          .then(data => {
            if(data !== -1){
              store.commit('changeUserState', data);
              if(toPath.indexOf('/admin') === 0 && store.state.userState !== 1){
                ViewUI.Message.error('您没有权限访问');
                next('/');
              }else{
                next();
              }
            }else{
              ViewUI.Message.error('您还没登录');
              next('/');
            }
          });
      }else{
        if(store.state.userState !== 1 && toPath.indexOf('/admin') === 0){
          ViewUI.Message.error('您没有权限访问');
          next('/');
        }
        next();
      }
    }
  },
}


