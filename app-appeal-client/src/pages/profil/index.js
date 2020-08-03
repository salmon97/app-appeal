import React, {Component} from 'react';
import DashboardLayout from "../../components/DashboardLayout";
import {connect} from "dva";
import CabinetTwo from "../../components/CabinetTwo";

@connect(({globalModel}) => ({globalModel}))
class Cabinet extends Component {

  render() {
    const {dispatch, globalModel} = this.props;
    const {currentUser} = globalModel;

    const changeRegisterCode=(e,v)=>{
      dispatch({
        type:'globalModel/registerCode',
        payload:{...v,id:currentUser.id}
      })
    };

    return (
      <div>
        <DashboardLayout>
          <CabinetTwo
            changeRegisterCode={changeRegisterCode}/>
        </DashboardLayout>
      </div>
    );
  }
}

Cabinet.propTypes = {};

export default Cabinet;
