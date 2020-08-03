import React, { Component } from "react";
import ReactDOM from "react-dom";
import Pagination from "react-js-pagination";
import ApplicationCom from "./ApplicationCom";

class App extends Component {
  render() {
    const {activePage,size,showPageCount,totalElements,changePage}=this.props;
    return (
      <div>
        <Pagination
          itemClass="page-item"
          linkClass="page-link"
          activePage={activePage}
          itemsCountPerPage={size}
          totalItemsCount={totalElements}
          pageRangeDisplayed={showPageCount}
          onChange={changePage.bind(this)}
        />
      </div>
    );
  }
}

App.propTypes = {};

export default App;
