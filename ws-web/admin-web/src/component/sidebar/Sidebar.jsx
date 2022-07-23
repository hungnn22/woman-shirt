import React from "react";
import { Link, NavLink } from "react-router-dom";

const Sidebar = () => {
  return (
    <ul
      className="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
      id="accordionSidebar"
    >
      <a
        className="sidebar-brand d-flex align-items-center justify-content-center"
        href="index.html"
      >
        <div className="sidebar-brand-icon rotate-n-15">
          <i className="fas fa-laugh-wink" />
        </div>
        <div className="sidebar-brand-text mx-3">My Shop</div>
      </a>
      <hr className="sidebar-divider my-0" />
      <li className="nav-item active">
        <NavLink to="/" className="nav-link" href="index.html">
          <i className="fas fa-fw fa-tachometer-alt" />
          <span>Dashboard</span>
        </NavLink>
      </li>
      <hr className="sidebar-divider" />
      <div className="sidebar-heading">Management</div>
      <li className="nav-item">
        <a
          className="nav-link collapsed"
          href="#"
          data-toggle="collapse"
          data-target="#collapseTwo"
          aria-expanded="true"
          aria-controls="collapseTwo"
        >
          <i className="fas fa-fw fa-cog" />
          <span>Bán hàng</span>
        </a>
        <div
          id="collapseTwo"
          className="collapse"
          aria-labelledby="headingTwo"
          data-parent="#accordionSidebar"
        >
          <div className="bg-white py-2 collapse-inner rounded">
            {/* <h6 className="collapse-header">Danh sách đơn hàng</h6> */}
            <NavLink to="order" className="collapse-item" href="buttons.html">
              Danh sách đặt hàng
            </NavLink>
            <a className="collapse-item" href="cards.html">
              Danh sách đơn hàng
            </a>
          </div>
        </div>
      </li>
      <li className="nav-item">
        <a
          className="nav-link collapsed"
          href="#"
          data-toggle="collapse"
          data-target="#collapseUtilities"
          aria-expanded="true"
          aria-controls="collapseUtilities"
        >
          <i className="fas fa-fw fa-wrench" />
          <span>Quản lý</span>
        </a>
        <div
          id="collapseUtilities"
          className="collapse"
          aria-labelledby="headingUtilities"
          data-parent="#accordionSidebar"
        >
          <div className="bg-white py-2 collapse-inner rounded">
            {/* <h6 className="collapse-header">Custom Utilities:</h6> */}
            <NavLink to="./category" className="collapse-item">
              Loại sản phẩm
            </NavLink>
            <NavLink to="./account" className="collapse-item">
              Tài khoản
            </NavLink>
            <NavLink className="collapse-item" to="./customer">
              Khách hàng
            </NavLink>
            <a className="collapse-item" href="utilities-other.html">
              Nhà cung cấp
            </a>
          </div>
        </div>
      </li>
      <hr className="sidebar-divider" />
      <div className="sidebar-heading">Report</div>
      <li className="nav-item">
        <a
          className="nav-link collapsed"
          href="#"
          data-toggle="collapse"
          data-target="#collapsePages"
          aria-expanded="true"
          aria-controls="collapsePages"
        >
          <i className="fas fa-fw fa-folder" />
          <span>Pages</span>
        </a>
        <div
          id="collapsePages"
          className="collapse"
          aria-labelledby="headingPages"
          data-parent="#accordionSidebar"
        >
          <div className="bg-white py-2 collapse-inner rounded">
            <h6 className="collapse-header">Login Screens:</h6>
            <Link className="collapse-item" to="/login">
              Login
            </Link>
            <a className="collapse-item" href="register.html">
              Register
            </a>
            <a className="collapse-item" href="forgot-password.html">
              Forgot Password
            </a>
            <div className="collapse-divider" />
            <h6 className="collapse-header">Other Pages:</h6>
            <a className="collapse-item" href="404">
              404 Page
            </a>
            <a className="collapse-item" href="blank.html">
              Blank Page
            </a>
          </div>
        </div>
      </li>
      <li className="nav-item">
        <a className="nav-link" href="charts.html">
          <i className="fas fa-fw fa-chart-area" />
          <span>Charts</span>
        </a>
      </li>
      <li className="nav-item">
        <a className="nav-link" href="tables.html">
          <i className="fas fa-fw fa-table" />
          <span>Tables</span>
        </a>
      </li>
    </ul>
  );
};

export default Sidebar;
