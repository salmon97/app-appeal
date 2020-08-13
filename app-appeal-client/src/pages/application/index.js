import React, {Component} from 'react';
import {connect} from "dva";
import DashboardLayout from "../../components/DashboardLayout";
import ApplicationCom from "../../components/ApplicationCom";
import PaginationApp from "../../components/PaginationApp";
import {Tab, Tabs} from "react-bootstrap";
import ApplicationPersonal from "../../components/ApplicationPersonal";


@connect(({applicationModel}) => ({applicationModel}))
class applicationIndex extends Component {

  componentDidMount() {
    this.props.dispatch({
      type: 'applicationModel/getApplication'
    })
  }



  render() {
    const {dispatch, applicationModel} = this.props;
    const {page, size, totalElements, mainApplication, totalPages, applications, showMainApplicationModal} = applicationModel;

    const changePage = (page) => {
      dispatch({
        type: 'applicationModel/getApplication',
        payload: {
          page: page - 1,
          size: 10
        }
      });
    };

    const openMainApplic = (item) => {
      dispatch({
        type: 'applicationModel/updateState',
        payload: {
          mainApplication: item,
          showMainApplicationModal: !showMainApplicationModal
        }
      });
    };

    const deleteApplication = (id) => {

      // eslint-disable-next-line no-restricted-globals
      if (confirm("сиз мурожаатни ўчирмоқчимисиз"))
        dispatch({
          type: 'applicationModel/deleteApplication',
          payload: {id}
        })
    };

    const handleSelect = (source) => {
      dispatch({
        type: 'applicationModel/getApplication',
        payload: {
          page:0,
          size:10,
          source
        }
      })
    };

    return (
      <div>
        <DashboardLayout pathname={this.props.pathname}>
          <div className="container">
            <Tabs defaultActiveKey="Telegram_bot" onSelect={handleSelect} className="nav-tabs justify-content-center" id="uncontrolled-tab-example">
              <Tab eventKey="Telegram_bot" title={<h5>Telegram_bot</h5>}>
                <ApplicationCom
                  totalPages={totalPages}
                  totalElements={totalElements}
                  size={size}
                  page={page}
                  applications={applications}
                  changePage={changePage}
                  deleteApplication={deleteApplication}
                  openMainApplic={openMainApplic}
                  showMainApplicationModal={showMainApplicationModal}
                  mainApplication={mainApplication}
                />
              </Tab>
              <Tab eventKey="personal" title={<h5>Personal</h5>}>
                <ApplicationPersonal
                  totalPages={totalPages}
                  totalElements={totalElements}
                  size={size}
                  page={page}
                  applications={applications}
                  changePage={changePage}
                  deleteApplication={deleteApplication}
                  openMainApplic={openMainApplic}
                  showMainApplicationModal={showMainApplicationModal}
                  mainApplication={mainApplication}
                />
              </Tab>
            </Tabs>
          </div>
        </DashboardLayout>
      </div>
    );
  }
}

applicationIndex.propTypes = {};

export default applicationIndex;
