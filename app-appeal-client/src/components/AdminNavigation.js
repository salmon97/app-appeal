import React, {Component} from 'react';
import {Button, Nav, Navbar, NavbarText, NavItem, NavLink} from "reactstrap";
import {connect} from "dva";
import router from "umi/router";
import {TOKEN_NAME} from "../contants/contants";

@connect(({globalModel}) => ({globalModel}))
class AdminNavigation extends Component {
  render() {

    const logOut = () => {
      localStorage.removeItem(TOKEN_NAME);
      this.props.dispatch({
        type: 'globalModel/updateState',
        payload: {
          currentUser: '',
          isStaff: false,
          isTeacher: false,
          isStudent: false
        }
      });
      router.push("/auth/login");
    };
    return (
      <div>
        <div>
          <Navbar className="navbar-sss" expand="md">
            {/*<NavbarToggler  onClick={toggle}/>*/}
            {/*<Collapse isOpen={isOpen} navbar>*/}
              <Nav className="mr-auto" navbar>
                <NavItem>
                  <NavLink className="navlink" href="/cabinet">Home</NavLink>
                </NavItem>

              </Nav>
            {localStorage.length ? <NavbarText><Button onClick={logOut} className="btn bg-dark">log out</Button></NavbarText> :''}
            {/*</Collapse>*/}
          </Navbar>
        </div>
      </div>
    );
  }
}
AdminNavigation.propTypes = {};
export default AdminNavigation;
