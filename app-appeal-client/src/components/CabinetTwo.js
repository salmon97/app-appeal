import React, {Component} from 'react';
import {AvField, AvForm} from 'availity-reactstrap-validation';
import {Button} from "reactstrap";

class CabinetTwo extends Component {
  render() {
    const {changeRegisterCode}=this.props;
    return (
      <div className="container profil-main">
        <div className="row ">
          <div className="col-md-4 text-center profil-card offset-4">
            <AvForm onValidSubmit={changeRegisterCode}>
                <AvField  name="registerCode" placeholder="register code for staff in bot"/>
              <Button color="success">Save</Button>
            </AvForm>
          </div>
        </div>
      </div>
    );
  }
}

CabinetTwo.propTypes = {};

export default CabinetTwo;
