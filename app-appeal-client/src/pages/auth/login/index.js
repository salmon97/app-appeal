import React, {Component} from 'react';
import {AvField, AvForm} from 'availity-reactstrap-validation'
import {connect} from "dva";
import {Col, Container, CustomInput, NavLink, Row} from "reactstrap";
import router from "umi/router";

@connect(({globalModel}) => ({globalModel}))
class Login extends Component {
  render() {
    const login = (e, v) => {
      this.props.dispatch({
        type: 'globalModel/login',
        payload: {
          ...v
        }
      })
    };
    return (
      <div className="div-body">
        <Container className="login-page text-primary" fluid>
          <Row className="login-row">
            <div className="col-md-4 col-10 offset-1 offset-md-4">
              <h1 className="card-body text-center ">Log in</h1>
              <AvForm onValidSubmit={login}>
                <AvField className="email" name="email" placeholder="email"/>
                <AvField className="login-password" type="password" name="password" placeholder="Password"/>
                <Row className="login-row2">
                  <Col className="col-6"><CustomInput type="checkbox" label="Remember Me" id="aa"/></Col>
                  <Col className="col-6 float-right"><NavLink href="#" className="text-primary pt-0">Forgot
                    password?</NavLink></Col>
                </Row>
                <button className="btn btn-primary btn-block">Login</button>
                <div className="justify-content-center d-flex align-content-center">
                  <h6 className=" text-center">Don't have an account? </h6>
                  <NavLink href="#" onClick={function () {router.push("/checkRegister")}} className="text-primary pt-0">Sign up</NavLink>
                </div>
              </AvForm>
            </div>
          </Row>
        </Container>
      </div>

    );
  }
}

Login.propTypes = {};

export default Login;
