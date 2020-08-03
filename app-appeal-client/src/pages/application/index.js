import React, {Component} from 'react';
import {connect} from "dva";
import DashboardLayout from "../../components/DashboardLayout";
import ApplicationCom from "../../components/ApplicationCom";
import PaginationApp from "../../components/PaginationApp";


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
        type:'applicationModel/updateState',
        payload:{
          mainApplication:item,
          showMainApplicationModal: !showMainApplicationModal
        }
      });
    };

    const closeModal = () => {

    };

    const deleteApplication = (id) => {
      dispatch({
        type: 'applicationModel/deleteApplication',
        payload: {id}
      })
    };

    return (
      <div>
        <DashboardLayout pathname={this.props.pathname}>
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
            closeModal={closeModal}
            mainApplication={mainApplication}
          />
        </DashboardLayout>
      </div>
    );
  }
}

applicationIndex.propTypes = {};

export default applicationIndex;
