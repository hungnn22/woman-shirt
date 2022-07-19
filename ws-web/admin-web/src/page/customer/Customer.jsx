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
          <input
            type="text"
            placeholder="Tìm kiếm"
            onChange={(e) => setkeySearch(e.target.value)}
          />
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
          </div>
        </div>
      </div>
    </div>
  );
};

export default Customer;