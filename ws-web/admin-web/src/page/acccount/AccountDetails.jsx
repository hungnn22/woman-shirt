import React from "react";
import ButtonDelete from "../../component/button/ButtonDelete";
import avatar from "../../assets/image/undraw_profile_1.svg";
const AccountDetails = () => {
  return (
    <div class="container-fluid">
      <h1 class="h3 mb-4 text-gray-800">Nguyễn Văn A</h1>

      <div class="row">
        <div class="col-lg-6">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">
                Thông tin tài khoản
              </h6>
            </div>
            <div class="card-body">
              <div className="row">
                <div className="col-lg-8 row">
                  <div className="col-4 d-flex flex-column font-weight-bold">
                    <span>Họ và tên</span>
                    <span>Ngày sinh</span>
                    <span>Địa chỉ email</span>
                  </div>
                  <div className="col-8 d-flex flex-column">
                    <span>Nguyễn Văn A</span>
                    <span>01/01/1999</span>
                    <span>nguyenvana@gmail.com</span>
                  </div>
                </div>
                <div className="col-lg-4 d-flex  justify-content-end align-items-center">
                  <img
                    style={{ width: "100px" }}
                    className="img-profile rounded-circle"
                    src={avatar}
                  />
                </div>
              </div>
            </div>
          </div>

          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Hành động</h6>
            </div>
            <div class="card-body">
              <ButtonDelete title="Xóa tài khoản" />
            </div>
          </div>
        </div>

        <div class="col-lg-6">
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Địa chỉ liên hệ</h6>
            </div>
            <div class="card-body"></div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AccountDetails;
