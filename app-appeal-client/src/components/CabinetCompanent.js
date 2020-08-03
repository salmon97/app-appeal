import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {AiOutlineFileText, BsFillPeopleFill} from 'react-icons/all';

class CabinetCompanent extends Component {
  render() {

    const {staffCount,murojaatlarCountDay,murojaatlarAllCount,murojaatlarChecked,murojaatlarNoChecked,murojaatlarCheckingNow}=this.props;

    return (
      <div className="container mt-4">

        <div className="row">
          <div className="col-md-3 ml-4 ">
            <div className="cardd">
              <div className="card-class" id="adminTotalClients">
                <span className="font-weight-bold">Umumiy Xodimlar Soni</span>
                <div className="d-flex justify-content-around mt-2 ">
                  <BsFillPeopleFill className="iconDashboard"/>
                  <h2 className="font-weight-bold">{staffCount}</h2>
                </div>
              </div>
            </div>
          </div>

          <div className="col-md-3">
            <div className="cardd">
              <div className="card-class" id="adminTotalClients">
                <span className="font-weight-bold">Umumiy Murojaatlar soni</span>
                <div className="d-flex justify-content-around mt-2">
                  <AiOutlineFileText className="iconDashboard"/>
                  <h2 className="font-weight-bold">{murojaatlarAllCount}</h2>
                </div>
              </div>
            </div>
          </div>

          <div className="col-md-3">
            <div className="cardd">
              <div className="card-class" id="adminTotalClients">
                <span className="font-weight-bold">Bugungi Murojaatlar Soni</span>
                <div className="d-flex justify-content-around mt-2">
                  <AiOutlineFileText className="iconDashboard"/>
                  <h2 className="font-weight-bold">{murojaatlarCountDay}</h2>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="row mt-5">
          <div className="col-md-3 ml-4 ">
            <div className="cardd">
              <div className="card-class" id="adminTotalClients">
                <span className="font-weight-bold">Yo'pilgan murojaatlar soni</span>
                <div className="d-flex justify-content-around mt-2 ">
                  <AiOutlineFileText className="iconDashboard"/>
                  <h2 className="font-weight-bold">{murojaatlarChecked}</h2>
                </div>
              </div>
            </div>
          </div>

          <div className="col-md-3">
            <div className="cardd">
              <div className="card-class" id="adminTotalClients">
                <span className="font-weight-bold">Yo'pilmagan murojaatlar soni</span>
                <div className="d-flex justify-content-around mt-2">
                  <AiOutlineFileText className="iconDashboard"/>
                  <h2 className="font-weight-bold">{murojaatlarNoChecked}</h2>
                </div>
              </div>
            </div>
          </div>

          <div className="col-md-3">
            <div className="cardd">
              <div className="card-class" id="adminTotalClients">
                <span className="font-weight-bold">Ko'rilyotgan Murojaatlar Soni</span>
                <div className="d-flex justify-content-around mt-2">
                  <AiOutlineFileText className="iconDashboard"/>
                  <h2 className="font-weight-bold">{murojaatlarCheckingNow}</h2>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

CabinetCompanent.propTypes = {};

export default CabinetCompanent;
