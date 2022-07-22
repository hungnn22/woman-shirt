import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { useForm } from "react-hook-form";
import { useEffect } from 'react';
import AxiosApi from '../../api/AxiosApi';
import WsUrl from '../../utils/constants/WsUrl';


const initReq = {
    status: null,
    textSearch: null,
    pageReq: {
        page: 0,
        pageSize: 10,
        sortField: '',
        sortDirection: ''
    }
};

let initPageInfo = {
    page: null,
    pageSize: null,
    totalElements: null,
    totalPages: null,
};

const DiscountListPage = () => {
    const { register, handleSubmit } = useForm()
    const navigate = useNavigate()
    const [req, setReq] = useState(initReq)
    const [pageInfo, setPageInfo] = useState(initPageInfo)
    const [loading, setLoading] = useState(false)
    const [discounts, setDiscounts] = useState([])

    useEffect(() => {
        getDiscounts()
    })

    const getDiscounts = async () => {
        const res = await AxiosApi.postAuth(WsUrl.ADMIN_DISCOUNT_SEARCH, req)
        console.log("res", res);
        
    }


    const handleChangeTextSearchFilter = () => {

    }

  return (
    <div className="container-fluid w-100 reponsive">
    <div className="card shadow mb-4">
        <div className="card-header py-3">
            <h3 className="m-0 font-weight-bold text-primary">Danh sách khuyến mãi</h3>
        </div>
        <div className="card-body">
            <div className='row d-flex align-items-center py-1'>
                <div className='col d-flex align-items-center'>
                    <span className='' style={{ minWidth: '64px' }}>Trạng thái:</span>
                    {/* <select className='border-1 form-control col-2 mx-2'
                        onChange={handleChangeStatusFilter}>
                        <option value="">Tất cả</option>
                        <option selected={status && status.toUpperCase() === 'PENDING'} value="PENDING">Đang chờ xử lý</option>
                        <option selected={status && status.toUpperCase() === 'ACCEPT'} value="ACCEPT">Đã xác nhận</option>
                        <option selected={status && status.toUpperCase() === 'REJECT'} value="REJECT">Đã từ chối</option>
                        <option selected={status && status.toUpperCase() === 'CANCEL'} value="CANCEL">Đã hủy</option>
                        <option selected={status && status.toUpperCase() === 'SHIPPING'} value="SHIPPING">Đang giao</option>
                        <option selected={status && status.toUpperCase() === 'FINISH'} value="FINISH">Đã nhận hàng</option>
                    </select> */}
                </div>

                <form className="d-none d-sm-inline-block form-inline navbar-search col-4"
                    onSubmit={handleSubmit(handleChangeTextSearchFilter)}>
                    <div className="input-group">
                        <input type="text" className="form-control bg-light border-0 small"
                            placeholder="Tìm kiếm..." aria-label="Search" aria-describedby="basic-addon2"
                            defaultValue=""
                            {...register("textSearch")} />
                        <div className="input-group-append">
                            <button className="btn btn-primary" type="submit">
                                <i className="fas fa-search fa-sm" />
                            </button>
                        </div>
                    </div>
                </form>
            </div>

            <hr className='pb-2' />
            {/* <h6>Tìm thấy <b>{pageInfo.totalElements}</b> dữ liệu phù hợp.</h6> */}
            <div className='table-responsive'>
                <table className="table table-bordered mt-4" id="dataTable" width="100%" cellSpacing={0}>
                    <thead>
                        <tr className='text-bold text-dark'>
                            <th>No</th>
                            <th>Mã</th>
                            <th>Chi tiết</th>
                            <th>Trạng thái</th>
                            <th>Đã dùng</th>
                            <th>Bắt đầu</th>
                            <th>Kết thúc</th>
                            <th>More</th>
                        </tr>
                    </thead>
                    <tbody>
                        {/* {loading ? <HashSpinner /> :
                            orders && orders.map((order, index) => (
                                <tr key={order.id}>
                                    <td className="text-center">{index + 1}</td>
                                    <td>{order.code}</td>
                                    <td className='col-1'>{order.customer}</td>
                                    <td className="col-1">{order.phone}</td>
                                    <td>{order.orderDate}</td>
                                    <td className="col-2">{order.address}</td>
                                    <td className='col-1'>{order.total}</td>
                                    <td>{order.type}</td>
                                    <td>{order.note}</td>
                                    <td className="col-2">{order.status}</td>
                                    <td>
                                        <div className="btn-group dropleft">
                                            <a className="btn text-dark" type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <i className="fa fa-ellipsis-h" aria-hidden="true" />
                                            </a>
                                            <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                                <NavLink to={`detail/${order.id}`} className="dropdown-item">Chi tiết</NavLink>
                                                <a className="dropdown-item" href="#" data-toggle="modal"
                                                    data-target={`#statusModal${order.id}`}>Chỉnh sửa trạng thái</a>
                                            </div>
                                        </div>
                                        <div className="modal fade" id={`statusModal${order.id}`} tabIndex={-1}
                                            role="dialog"
                                            aria-labelledby="statusModalLabel" aria-hidden="true">
                                            <div className="modal-dialog modal-lg" role="document">
                                                <div className="modal-content">
                                                    <div className="modal-header">
                                                        <h5 className="modal-title" id="exampleModalLabel">Chỉnh sửa
                                                            trạng
                                                            thái</h5>
                                                        <button type="button" className="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                            <span aria-hidden="true">×</span>
                                                        </button>
                                                    </div>
                                                    {order.options &&
                                                        <div className="modal-body p-4">
                                                            <div className="form-group mt-2">
                                                                <h6 className='text-dark'><b>1. Lý do chỉnh
                                                                    sửa</b> (không bắt buộc)</h6>
                                                                <textarea id='reason' className='form-control'
                                                                    rows={4}></textarea>
                                                            </div>
                                                            <h6 className='text-dark'><b>2. Chọn trạng thái mới cho đơn
                                                                hàng</b></h6>
                                                            <div className='row d-flex justify-content-left p-2'>
                                                                {order.options.map((obj, index1) => (
                                                                    <button key={index1}
                                                                        className={`btn mr-2 btn-${obj.clazz}`}
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
                                </tr>))} */}
                    </tbody>
                </table>
            </div>

            {pageInfo.totalElements > 0 && <div className='p-2 row align-items-center justify-content-between'>
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
            </div>}
        </div>
    </div>
</div>  )
}

export default DiscountListPage