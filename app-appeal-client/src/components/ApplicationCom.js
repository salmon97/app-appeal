import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {AiFillEdit, RiDeleteBin5Line} from "react-icons/all";
import {Button, Modal, ModalBody, ModalFooter, ModalHeader, Table} from "reactstrap";
import PaginationApp from "./PaginationApp";
import {URLGETFILE} from "../contants/contants";
import {router} from "umi";
import {Link} from "react-router-dom";

class ApplicationCom extends Component {
  render() {
    const {applications, changePage, mainApplication, showMainApplicationModal, closeModal, totalElements, totalPages, size, deleteApplication, openMainApplic} = this.props;
    return (
      <div className="container mt-2">
        <div className="row">
          <div className="col-md-12">
            <Table className="table-dark">
              <thead>
              <tr>
                <th>№</th>
                <th>дата</th>
                <th>тел номер</th>
                <th>категоря</th>
                <th>худуд</th>
                <th>код</th>
                <th>статус</th>
                <th>мурожаати</th>
                <th>Action</th>
              </tr>
              </thead>
              <tbody>
              {applications.map((item, i) =>
                <tr key={item.id}>
                  <td>{i + 1}</td>
                  <td>{item.created_at}</td>
                  <td>{item.phoneNumber}</td>
                  <td>{item.category}</td>
                  <td>{item.district}</td>
                  <td>{item.code}</td>
                  <td>{item.status === 'CHECKED' ? 'ёпилган' : item.status === 'NO_CHECKED' ? 'ёпилмаган' : item.status === 'NO_RECEIVE' ? 'қабул қилинмаган' : item.status === 'RECEIVE' ? 'қабул қилинган' : ''}</td>
                  <td>
                    <button className="btn btn-success text-light" onClick={() => openMainApplic(item)}>open</button>
                  </td>
                  <td><RiDeleteBin5Line onClick={() => deleteApplication(item.id)}/></td>
                </tr>
              )}
              </tbody>
            </Table>
          </div>
        </div>
        <div className="row">
          <div className="col-md-4 offset-4">
            <PaginationApp
              activePage={0}
              totalElements={totalElements}
              size={size}
              showPageCount={totalPages < 5 ? totalPages : 5}
              changePage={changePage}
            />
          </div>
        </div>
        <Modal isOpen={showMainApplicationModal} toggle={openMainApplic}>
          <ModalHeader className="text-center">
            <h4>Murojaat</h4>
          </ModalHeader>
          <ModalBody className="text-center">
            {mainApplication.text != null ?
              <h4>{mainApplication.text}</h4> : mainApplication.fileType === 'ФОТО' ?
                <div style={{height: '250px', width: '250px'}}>
                  <img className="img-fluid"
                       src={`${URLGETFILE}${mainApplication.fileId}`}
                       alt=""/>
                </div>
                : mainApplication.fileType === 'voice' ?
                  <div>
                    <audio controls>
                      <source src={`${URLGETFILE}${mainApplication.fileId}`}/>
                    </audio>
                  </div>
                  : mainApplication.fileType === 'videoNote' ?
                    <div>
                      <video width="320" height="240" controls>
                        <source src={`${URLGETFILE}${mainApplication.fileId}`}/>
                      </video>
                    </div>
                    : mainApplication.fileType === 'АУДИО' ?
                      <div>
                        <audio controls>
                          <source src={`${URLGETFILE}${mainApplication.fileId}`}/>
                        </audio>
                      </div>
                      : mainApplication.fileType === 'ВИДЕО' ?
                        <div>
                          <video width="320" height="240" controls>
                            <source src={`${URLGETFILE}${mainApplication.fileId}`}/>
                          </video>
                        </div> : ''
            }
          </ModalBody>
          <ModalFooter>
            <Button color="danger" type="button" onClick={openMainApplic}>Close</Button>
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}

ApplicationCom.propTypes = {};

export default ApplicationCom;
