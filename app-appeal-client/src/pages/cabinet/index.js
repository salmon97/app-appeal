import React, {Component} from 'react';
import DashboardLayout from "../../components/DashboardLayout";
import CabinetCompanent from "../../components/CabinetCompanent";
import {connect} from "dva";

@connect(({cabinetModel,globalModel}) => ({cabinetModel,globalModel}))
class Cabinet extends Component {

  componentDidMount() {
    this.props.dispatch({
      type: 'cabinetModel/getDay',
    })
  }
  render() {
    const {dispatch, cabinetModel,globalModel} = this.props;
    const {dashboardPage}=globalModel;
    const {staffCount, murojaatlarCountDay,murojaatlarAllCount,murojaatlarChecked,murojaatlarNoChecked,murojaatlarCheckingNow} = cabinetModel;
    return (
      <div>
        <DashboardLayout>
          <CabinetCompanent
            staffCount={staffCount}
            murojaatlarCountDay={murojaatlarCountDay}
            murojaatlarAllCount={murojaatlarAllCount}
            murojaatlarChecked={murojaatlarChecked}
            murojaatlarNoChecked={murojaatlarNoChecked}
            murojaatlarCheckingNow={murojaatlarCheckingNow}
          />
        </DashboardLayout>
      </div>
    );
  }
}

Cabinet.propTypes = {};

export default Cabinet;
