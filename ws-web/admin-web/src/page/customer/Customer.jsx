import React, { useState, useEffect } from "react";
import data from "./MOCK_DATA (1).json";
import ButtonInfo from "../../component/button/ButtonInfo";

const Customer = () => {
  const [keySearch, setkeySearch] = useState("");

  useEffect(() => {
    console.log(keySearch);
  }, [keySearch]);

  return (
    <div className="container-fluid">
      <h1 className="h3 mb-2 text-gray-800">Quản lý danh sách khách hàng</h1>

      <div className="card shadow mb-4">
        <div className="card-header py-3 d-flex justify-content-between align-items-center">
          <h6 className="m-0 font-weight-bold text-primary">
            Quản lý khách hàng
          </h6>
          <form className="d-none d-sm-inline-block form-inline navbar-search col-4">
            <div className="input-group">
              <input
                onChange={(e) => setkeySearch(e.target.value)}
                type="text"
                className="form-control bg-light border-1 small"
                placeholder="Tìm kiếm..."
                aria-label="Search"
                aria-describedby="basic-addon2"
                defaultValue=""
              />
              <div className="input-group-append">
                <button className="btn btn-primary" type="submit">
                  <i className="fas fa-search fa-sm" />
                </button>
              </div>
            </div>
          </form>
        </div>
        <div className="card-body">
          <div className="table-responsive">
            <table
              className="table table-bordered"
              id="dataTable"
              width="100%"
              cellspacing="0"
            >
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Tên khách hàng</th>
                  <th>Tuổi</th>
                  <th>Giới tính</th>
                  <th>Số điện thoại</th>
                  <th>Số đơn đã đặt</th>
                  <th>Hành động</th>
                </tr>
              </thead>
              <tbody>
                {data &&
                  data
                    .filter((ele) =>
                      ele.fullname
                        .toString()
                        .toLowerCase()
                        .includes(keySearch.toString().toLowerCase())
                    )
                    .map((e, index) => (
                      <tr key={index}>
                        <td>{e.id}</td>
                        <td>{e.fullname}</td>
                        <td>{e.age}</td>
                        <td>{e.gender}</td>
                        <td>{e.phone}</td>
                        <td>{e.total_orders}</td>
                        <td>
                          <ButtonInfo
                            title="Xem chi tiết"
                            link="abc"
                            className="mr-2"
                          />
                        </td>
                      </tr>
                    ))}
              </tbody>
            </table>
            <div className="d-flex align-items-center justify-content-between">
              <div className=" d-flex align-items-center">
                Hiển thị:{" "}
                <select className="border-1 form-control col-1 mx-2">
                  <option value={10}>10</option>
                  <option value={20}>20</option>
                  <option value={50}>50</option>
                  <option value={100}>100</option>
                </select>
              </div>
              <div className=" d-flex align-items-center">
                <button className="btn btn-outline-dark btn-sm mx-1 px-2">
                  Trước
                </button>
                <button className="btn btn-outline-dark btn-sm mx-1 px-2">
                  Sau
                </button>
                <span>Trang</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Customer;
