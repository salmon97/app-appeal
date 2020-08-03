import React, {Component} from 'react';
import {ListGroup, ListGroupItem} from "reactstrap";
import {Link} from "react-router-dom";
import {FaUserEdit} from 'react-icons/fa';
import {AiFillDashboard, AiOutlineTeam} from 'react-icons/ai';
import {connect} from "dva";
import {AiOutlineFileText} from "react-icons/all";


@connect(({globalModel}) => ({globalModel}))
class DashboardSidebar extends Component {
  render() {
    const {globalModel} = this.props;
    const {currentUser} = globalModel;

    return (
      <div className="katalog-sidebar">
        <div className="menuSidebar">
          <div className="userStatus text-center text-white">
            <h5 className="">{currentUser.firstName}</h5>
            <div className="m-auto w-25">
              <h6>Online</h6>
            </div>
          </div>
          <ListGroup className="">
            <ListGroupItem>
              <Link to="/cabinet"
                    className={window.location.pathname === "/cabinet" ? "active-catalog" : "nav-link"}>
                <AiFillDashboard className="list-group-item-icon"/> Dashboard</Link>
            </ListGroupItem>

            <ListGroupItem>
              <Link to="/staff"
                    className={window.location.pathname === "/staff" ? " active-catalog" : " nav-link"}>
                <AiOutlineTeam className="list-group-item-icon"/> Xodimlar</Link>
            </ListGroupItem>
            <ListGroupItem>
              <Link to="/application"
                    className={window.location.pathname === "/application" ? "active-catalog" : "nav-link"}>
                <AiOutlineFileText className="list-group-item-icon"/> Murojaatlar</Link>
            </ListGroupItem>
            <ListGroupItem>
              <Link to="/profil"
                    className={window.location.pathname === "/profil" ? "active-catalog" : "nav-link"}>
                <FaUserEdit className="list-group-item-icon"/> Profil</Link>
            </ListGroupItem>
          </ListGroup>
        </div>
      </div>
    );
  }
}

DashboardSidebar.propTypes = {};

export default DashboardSidebar;
