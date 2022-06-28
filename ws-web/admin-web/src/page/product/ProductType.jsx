import React from 'react'

function ProductType() {
  return (
    <div className="container-fluid">
            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <h3 className="m-0 font-weight-bold text-primary">Danh sách Loại sản phẩm</h3>
                </div>
                <div className="card-body">
                    <div className='row d-flex align-items-center py-1'>
                        <div className='col d-flex align-items-center'>
                            <span className='' style={{ minWidth: '64px' }}>Trạng thái:</span>
                            <select className='border-1 form-control col-2 mx-2' /*onChange={handleChangeStatusFilter}*/>
                                <option value="">Tất cả</option>
                                <option value="PENDING">Đang chờ xử lý</option>
                                <option value="ACCEPT">Đã xác nhận</option>
                                <option value="CANCEL_OR_REJECT">Đã hủy</option>
                                <option value="SHIPPING">Đang giao</option>
                                <option value="FINISH">Đã nhận hàng</option>
                            </select>
                        </div>

                        <form className="d-none d-sm-inline-block form-inline navbar-search col-4"
                            /*onSubmit={handleSubmit(handleChangeTextSearchFilter)}*/>
                            <div className="input-group">
                                <input type="text" className="form-control bg-light border-0 small"
                                    placeholder="Tìm kiếm..." aria-label="Search" aria-describedby="basic-addon2"
                                    defaultValue=""
                                    /*{...register("textSearch")}*/ />
                                <div className="input-group-append">
                                    <button className="btn btn-primary" type="submit">
                                        <i className="fas fa-search fa-sm" />
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div className='row d-flex align-items-center px-3 py-1'>
                        <span className='' style={{ minWidth: '64px' }}>Địa chỉ:</span>
                        <div className='col d-flex align-items-center'>
                            <select id='provinceFilter' className='border-1 form-control mx-1 col-2'
                                /*onChange={handleChangeProvinceFilter}*/>
                                <option value="" disabled selected text-muted>---Tỉnh/T.Phố---</option>
                                {/* {provinces && provinces.map(p => (
                                    <option key={p.code} value={p.code}>{p.name}</option>
                                ))} */}
                            </select>
                            <select id='districtFilter' className='border-1 form-control mx-1 col-2'
                                /*onChange={handleChangeDistrictFilter} onClick={handleClickDistrictFilter}*/>
                                <option value="" disabled selected text-muted>---Quận/Huyện---</option>
                                {/* {districts && districts.map(d => (
                                    <option key={d.code} value={d.code}>{d.name}</option>
                                ))} */}
                            </select>
                            <select id='wardFilter' className='border-1 form-control mx-1 col-2'
                                /*onClick={handleClickWardFilter} onChange={handleChangeWardFilter}*/>
                                <option value="" disabled selected>---Phường/Xã---</option>
                                {/* {wards && wards.map(w => (
                                    <option value={w.code}>{w.name}</option>
                                ))} */}
                            </select>
                            <button className='btn btn-outline-danger' /*onClick={handleRemoveLocationFilter}*/>Xóa</button>
                        </div>
                        <div className='row d-flex align-items-center'>
                            <select className='form-control col mx-1' /*onChange={handleChangeTimeFilter}*/>
                                <option value="" disabled selected>---Thời gian đặt---</option>
                                <option value="">Mặc định</option>
                                <option value="day">Hôm nay</option>
                                <option value="week">Tuần này</option>
                                <option value="month">Tháng này</option>
                            </select>
                            <select className='form-control col mx-1'>
                                <option value="" disabled selected>---Thanh toán---</option>
                                <option value="">Tất cả</option>
                                <option value="none-pay">Chưa thanh toán</option>
                                <option value="pay">Đã thanh toán</option>
                            </select>
                        </div>
                    </div>

                    <hr className='pb-2' />
                    {/* <h6>Tìm thấy {pageInfo.totalElements} dữ liệu phù hợp.</h6> */}
                    <table className="table table-bordered mt-4" id="dataTable" width="100%" cellSpacing={0}>
                        <thead>
                            <tr className='text-bold text-dark'>
                                <th>No</th>
                                <th>Khách hàng</th>
                                <th>SDT</th>
                                <th>Thời gian đặt</th>
                                <th>Địa chỉ giao</th>
                                <th>Tổng(VND)</th>
                                <th>Phương thức</th>
                                <th>Ghi chú</th>
                                <th>Trạng thái</th>
                                <th>More</th>
                            </tr>
                        </thead>
                        {/* <tbody>
                            {orders && orders.map((order, index) => (
                                <tr key={order.id}>
                                    <td className="text-center">{index + 1}</td>
                                    <td className='col-1'>{order.customer}</td>
                                    <td className="col-1">{order.phone}</td>
                                    <td>{order.orderDate}</td>
                                    <td className="col-2">{order.address}</td>
                                    <td className="col-1">{order.totalFmt}</td>
                                    <td>{order.type}({order.payed})</td>
                                    <td>{order.note}</td>
                                    <td className="col-2">{order.status}</td>
                                    <td>
                                        <div className="btn-group dropleft">
                                            <a className="btn text-dark" type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <i className="fa fa-ellipsis-h" aria-hidden="true" />
                                            </a>
                                            <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                <a className="dropdown-item" href="#" data-toggle="modal"
                                                    data-target={`#detailModal${order.id}`}
                                                    onClick={() => handleGetDetail(order.id)}>Chi tiết</a>
                                                <a className="dropdown-item" href="#" data-toggle="modal"
                                                    data-target={`#statusModal${order.id}`}>Chỉnh sửa trạng thái</a>
                                            </div>
                                        </div>
                                        {detail && <OrderDetail detail={detail} />}

                                        <div className="modal fade" id={`statusModal${order.id}`} tabIndex={-1}
                                            role="dialog"
                                            aria-labelledby="statusModalLabel" aria-hidden="true">
                                            <div className="modal-dialog modal-lg" role="document">
                                                <div className="modal-content">
                                                    <div className="modal-header">
                                                        <h5 className="modal-title" id="exampleModalLabel">Chỉnh sửa trạng thái</h5>
                                                        <button type="button" className="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                            <span aria-hidden="true">×</span>
                                                        </button>
                                                    </div>
                                                    {order.options &&
                                                        <div className="modal-body p-4">
                                                            <div className="form-group mt-2">
                                                                <h6 className='text-dark'><b>Lý do chỉnh sửa</b></h6>
                                                                <textarea id='reason' className='form-control' rows={4}></textarea>
                                                            </div>
                                                            <h6 className='text-dark'><b>Chọn trạng thái mới cho đơn hàng</b></h6>
                                                            <div className='row d-flex justify-content-left p-2'>
                                                                {order.options.map((obj, index) => (
                                                                    <button key={index} className={`btn mr-2 btn-${obj.clazz}`}
                                                                        onClick={() => handleChangeStatus(order.id, obj.status)}
                                                                        data-dismiss="modal"
                                                                    >{obj.name}</button>
                                                                ))}
                                                            </div>

                                                        </div>}
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>))}
                        </tbody> */}
                    </table>
                    <div className='p-2 row align-items-center justify-content-between'>
                        <div className='col d-flex align-items-center'>
                            {/* Hiển thị: <select className='border-1 form-control col-1 mx-2'
                                onChange={handleChangePageSizeFilter}>
                                <option value={10}>10</option>
                                <option value={20}>20</option>
                                <option value={50}>50</option>
                                <option value={100}>100</option>
                            </select> */}
                        </div>
                        <div className=''>
                            {/* <button className='btn btn-outline-dark btn-sm mx-1 px-2'
                                onClick={() => handleChangePageFilter(pageInfo.page - 1)}
                                disabled={pageInfo.page == 0}>Trước
                            </button>
                            <button className='btn btn-outline-dark btn-sm mx-1 px-2'
                                onClick={() => handleChangePageFilter(pageInfo.page + 1)}
                                disabled={pageInfo.page == pageInfo.totalPages - 1}>Sau
                            </button>
                            <span>Trang {pageInfo.page + 1}/{pageInfo.totalPages}</span> */}
                        </div>
                    </div>
                </div>
            </div>
        </div >
  )
}

export default ProductType