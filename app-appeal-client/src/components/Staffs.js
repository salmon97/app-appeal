import React, {Component} from 'react';
import {Button, Modal, ModalBody, ModalFooter, Table} from "reactstrap";
import {AvField, AvForm} from 'availity-reactstrap-validation';
import {AiFillEdit, RiDeleteBin5Line} from "react-icons/all";

class Staffs extends Component {
  render() {

    const {staffs, showModal, openModal, staff, edit, deleteStaff, showDeleteModal, openDeleteModal} = this.props;

    return (
      <div className="container mt-2">
        {/*<Tabs id="tabs++" activeKey="staff" >*/}

        {/*  */}
        {/*</Tabs>*/}
        <div className="row">
          <div className="col-md-12">
            <Table className="table-dark">
              <thead>
              <tr>
                <th>T/r</th>
                <th>тел номер</th>
                <th>исм</th>
                <th>категоря</th>
                <th>Action</th>
              </tr>
              </thead>
              <tbody>
              {staffs.map((item, i) =>
                <tr key={item.id}>
                  <td>{i + 1}</td>
                  <td>{item.phoneNumber}</td>
                  <td>{item.name}</td>
                  <td>{item.category}</td>
                  <td><AiFillEdit className="mr-3" onClick={() => openModal(item)}/><RiDeleteBin5Line
                    onClick={() => openDeleteModal(item)}/></td>
                </tr>
              )}
              </tbody>
            </Table>
          </div>
        </div>
        <Modal isOpen={showModal} toggle={openModal}>
          <AvForm onValidSubmit={edit}>
            <ModalBody className="text-center">
              <AvField value={staff.id} name="id" className="d-none"/>
              <AvField value={staff.name} name="name" placeholder="исмини киритинг"/>
              <AvField value={staff.phoneNumber} name="phoneNum" placeholder="тел номерини киритинг"/>
              <AvField value={staff.category} name="category" type="select">
                <option value="УЙ-ЖОЙ">УЙ-ЖОЙ</option>
                <option value="СУВ">СУВ</option>
                <option value="ГАЗ">ГАЗ</option>
              </AvField>
            </ModalBody>
            <ModalFooter>
              <Button color="danger" type="button" onClick={() => openModal('')}>Close</Button>
              <Button color="success">Save</Button>
            </ModalFooter>
          </AvForm>
        </Modal>

        <Modal isOpen={showDeleteModal} toggle={openDeleteModal}>
          <ModalBody className="text-center">
            <h5>{staff.name + ' ни учирмокдасиз'}</h5>
            <h4>тасдиклаш учун save ни босинг</h4>
          </ModalBody>
          <ModalFooter>
            <Button color="danger" type="button" onClick={() => openDeleteModal('')}>Close</Button>
            <Button color="success" onClick={deleteStaff}>Save</Button>
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}

Staffs.propTypes = {};

export default Staffs;
