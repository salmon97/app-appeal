import React, {Component} from 'react';
import DashboardLayout from "../../components/DashboardLayout";
import {connect} from "dva";
import Staffs from "../../components/Staffs";

@connect(({staffModel}) => ({staffModel}))
class Cabinet extends Component {

    componentDidMount() {
        this.props.dispatch({
            type: 'staffModel/getStaff',
        })
    }

    render() {

        const {dispatch, staffModel} = this.props;
        const {staffs, staff, showModal,showDeleteModal} = staffModel;

        const edit = (e,v) => {
            dispatch({
                type: 'staffModel/editStaff',
                payload: {...v}
            });
            dispatch({
                type: 'staffModel/updateState',
                payload: {showModal:false}
            })
        };

      const deleteStaff = () => {
        dispatch({
          type: 'staffModel/deleteStaff',
          payload: {id: staff.id}
        });
        dispatch({
          type: 'staffModel/updateState',
          payload: {showDeleteModal:false}
        })
      };

        const openModal = (item) => {
            dispatch({
                type: 'staffModel/updateState',
                payload: {
                    staff: item,
                    showModal: !showModal
                }
            });
        };

      const openDeleteModal = (item) => {
        dispatch({
          type: 'staffModel/updateState',
          payload: {
            staff: item,
            showDeleteModal: !showDeleteModal
          }
        });
      };

        return (
            <div>
                <DashboardLayout>
                    <Staffs
                        staff={staff}
                        edit={edit}
                        openModal={openModal}
                        showModal={showModal}
                        staffs={staffs}
                        openDeleteModal={openDeleteModal}
                        showDeleteModal={showDeleteModal}
                        deleteStaff={deleteStaff}
                    />
                </DashboardLayout>
            </div>
        );
    }
}

Cabinet.propTypes = {};

export default Cabinet;
