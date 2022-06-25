import axios from 'axios'
import React, { useEffect, useState } from 'react'
import AxiosApi from '../../api/AxiosApi'
import WsUrl from "../../utils/constants/WsUrl";
import { useForm } from "react-hook-form";
import ToastUtils from '../../utils/ToastUtils';
import WsToastType from '../../utils/constants/WsToastType';
import WsMessage from '../../utils/constants/WsMessage';
import { NavLink, Outlet } from 'react-router-dom'

let location = {
    provinceCode: null,
    districtCode: null,
    wardCode: null
};

const initReq = {
    id: null,
    status: null,
    cusId: null,
    provinceCode: null,
    districtCode: null,
    wardCode: null,
    time: null,
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



const OrderPage = () => {
    const [provinces, setProvinces] = useState([])
    const [districts, setDistricts] = useState([])
    const [wards, setWards] = useState([])
    const [req, setReq] = useState(initReq)
    const [orders, setOrders] = useState([])
    const [pageInfo, setPageInfo] = useState(initPageInfo)
    const [detail, setDetail] = useState(null)

    const { register, handleSubmit, formState: { errors } } = useForm()

    useEffect(() => {
        const getProvincesFromOpenApi = async () => {
            const url = 'https://provinces.open-api.vn/api/?depth=1';
            const res = await axios.get(url)
            setProvinces(res.data)
        }
        getProvincesFromOpenApi()
        getOrderList()
    }, [req])

    const getOrderList = async () => {
        const axiosRes = await AxiosApi.postAuth(`${WsUrl.ORDER_BASE}${WsUrl.ADMIN_ORDER_SEARCH}`, req)
        const { data } = axiosRes
        setOrders(data.data)
        setPageInfo({
            ...pageInfo,
            page: data.page,
            pageSize: data.pageSize,
            totalElements: data.totalElements,
            totalPages: data.totalPages
        })
    }

    const handleChangeProvinceFilter = e => {
        location = {
            ...location,
            provinceCode: e.target.value,
        }
        setReq({
            ...req,
            provinceCode: e.target.value
        })
    }

    const handleChangeDistrictFilter = e => {
        location = {
            ...location,
            districtCode: e.target.value
        }
        setReq({
            ...req,
            districtCode: e.target.value
        })
    }

    const handleChangeWardFilter = e => {
        setReq({
            ...req,
            wardCode: e.target.value
        })
    }

    const handleClickDistrictFilter = async () => {
        if (location.provinceCode !== null) {
            const res = await axios.get(`https://provinces.open-api.vn/api/p/${location.provinceCode}?depth=2`)
            setDistricts(res.data.districts)
        }
    }

    const handleClickWardFilter = async () => {
        if (location.districtCode !== null) {
            const res = await axios.get(`https://provinces.open-api.vn/api/d/${location.districtCode}?depth=2`)
            setWards(res.data.wards)
        }
    }

    const handleRemoveLocationFilter = () => {
        document.getElementById('provinceFilter').value = ''
        document.getElementById('districtFilter').value = ''
        document.getElementById('wardFilter').value = ''
        setReq({
            ...req,
            provinceCode: null,
            districtCode: null,
            wardCode: null,
        })
    }

    const handleChangeStatusFilter = e => {
        setReq({
            ...req,
            status: e.target.value || null
        })
    }

    const handleChangeTimeFilter = e => {
        setReq({
            ...req,
            time: e.target.value,
        })
    }

    const handleChangeTextSearchFilter = values => {
        const textSearch = values.textSearch.trim() == '' ? null : values.textSearch.trim()
        setReq({
            ...req,
            textSearch: textSearch
        })
    }

    const handleChangePageSizeFilter = e => {
        setReq({
            ...req,
            pageReq: {
                ...req.pageReq,
                pageSize: Number(e.target.value)
            }
        })
    }

    const handleChangePageFilter = newPage => {
        setReq({
            ...req,
            pageReq: {
                ...req.pageReq,
                page: Number(newPage)
            }
        })
    }

    const handleGetDetail = async orderId => {
        const axiosRes = await AxiosApi.getAuth(`${WsUrl.ORDER_BASE}${WsUrl.ADMIN_ORDER_DETAIL}/${orderId}`)
        console.log(axiosRes)
        setDetail(axiosRes.data.data)
        // setOrderId(orderId)
    }

    const handleChangeStatus = async (id, status) => {
        try {
            const obj = {
                id: '12312',
                status: status,
                note: document.getElementById('reason').value
            }
            const axiosRes = await AxiosApi.postAuth('/order/admin/change-status', obj)
            getOrderList()
            console.log(axiosRes)
            ToastUtils.createToast(WsToastType.SUCCESS, WsMessage.CHANGE_ORDER_STATUS_SUCCESS)
        } catch (e) {
            ToastUtils.createToast(WsToastType.ERROR, WsMessage.CHANGE_ORDER_STATUS_FAILED)
        }
    }

    return (
        <div className="container-fluid">
            <div className="card shadow mb-4">
                <div className="card-header py-3">
                    <h3 className="m-0 font-weight-bold text-primary">Danh sách đơn hàng</h3>
                </div>
                <div className="card-body">
                    <div className='row d-flex align-items-center py-1'>
                        <div className='col d-flex align-items-center'>
                            <span className='' style={{ minWidth: '64px' }}>Trạng thái:</span>
                            <select className='border-1 form-control col-2 mx-2' onChange={handleChangeStatusFilter}>
                                <option value="">Tất cả</option>
                                <option value="PENDING">Đang chờ xử lý</option>
                                <option value="ACCEPT">Đã xác nhận</option>
                                <option value="CANCEL_OR_REJECT">Đã hủy</option>
                                <option value="SHIPPING">Đang giao</option>
                                <option value="FINISH">Đã nhận hàng</option>
                            </select>
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

                    <div className='row d-flex align-items-center px-3 py-1'>
                        <span className='' style={{ minWidth: '64px' }}>Địa chỉ:</span>
                        <div className='col d-flex align-items-center'>
                            <select id='provinceFilter' className='border-1 form-control mx-1 col-2'
                                onChange={handleChangeProvinceFilter}>
                                <option value="" disabled selected text-muted>---Tỉnh/T.Phố---</option>
                                {provinces && provinces.map(p => (
                                    <option key={p.code} value={p.code}>{p.name}</option>
                                ))}
                            </select>
                            <select id='districtFilter' className='border-1 form-control mx-1 col-2'
                                onChange={handleChangeDistrictFilter} onClick={handleClickDistrictFilter}>
                                <option value="" disabled selected text-muted>---Quận/Huyện---</option>
                                {districts && districts.map(d => (
                                    <option key={d.code} value={d.code}>{d.name}</option>
                                ))}
                            </select>
                            <select id='wardFilter' className='border-1 form-control mx-1 col-2'
                                onClick={handleClickWardFilter} onChange={handleChangeWardFilter}>
                                <option value="" disabled selected>---Phường/Xã---</option>
                                {wards && wards.map(w => (
                                    <option value={w.code}>{w.name}</option>
                                ))}
                            </select>
                            <button className='btn btn-outline-danger' onClick={handleRemoveLocationFilter}>Xóa</button>
                        </div>
                        <div className='row d-flex align-items-center'>
                            <select className='form-control col mx-1' onChange={handleChangeTimeFilter}>
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
                    <h6>Tìm thấy {pageInfo.totalElements} dữ liệu phù hợp.</h6>
                    <table className="table table-bordered mt-4" id="dataTable" width="100%" cellSpacing={0}>
                        <thead>
                            <tr className='text-bold text-dark'>
                                <th>No</th>
                                <th>Mã đơn hàng</th>
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
                        <tbody>
                            {orders && orders.map((order, index) => (
                                <tr key={order.id}>
                                    <td className="text-center">{index + 1}</td>
                                    <td>{order.code}</td>
                                    <td className='col-1'>{order.customer}</td>
                                    <td className="col-1">{order.phone}</td>
                                    <td>{order.orderDate}</td>
                                    <td className="col-2">{order.address}</td>
                                    <td className="col-1">{order.total}</td>
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
                                                <NavLink to={`${order.id}`} className="dropdown-item">Chi tiết</NavLink>
                                                <a className="dropdown-item" href="#" data-toggle="modal"
                                                    data-target={`#statusModal${order.id}`}>Chỉnh sửa trạng thái</a>
                                            </div>
                                        </div>

                                        {/* {detail && <div className="modal fade" id={`detailModal${order.id}`} tabIndex={-1}
                                            role="dialog"
                                            aria-labelledby="detailModalLabel" aria-hidden="true">
                                            <div className="modal-dialog modal-xl" role="document">
                                                <div className="modal-content">
                                                    <div className="modal-header">
                                                        <h5 className="modal-title" id="exampleModalLabel">Chi tiết đơn
                                                            hàng</h5>
                                                        <button type="button" className="close" data-dismiss="modal"
                                                            aria-label="Close">
                                                            <span aria-hidden="true">×</span>
                                                        </button>
                                                    </div>
                                                    <div className="modal-body p-4">
                                                        <div>
                                                            <h6 className='text-dark'><b>1. Sản phẩm</b></h6>
                                                            <table className="table table-bordered mt-2" id="dataTable"
                                                                width="100%" cellSpacing={0}>
                                                                <thead>
                                                                    <tr className='text-bold text-dark'>
                                                                        <th className='text-center'>No</th>
                                                                        <th>Sản phẩm</th>
                                                                        <th>Đơn giá</th>
                                                                        <th>Số lượng</th>
                                                                        <th>Size</th>
                                                                        <th>Màu sắc</th>
                                                                        <th>Chất liệu</th>
                                                                        <th>Tổng(VND)</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    {detail.items && detail.items.map((obj, index) => (
                                                                        <tr key={obj.id}>
                                                                            <td className='text-center'>{index + 1}</td>
                                                                            <td>{obj.name}</td>
                                                                            <td>{obj.priceFmt}</td>
                                                                            <td>{obj.qty}</td>
                                                                            <td>{obj.size}</td>
                                                                            <td>{obj.color}</td>
                                                                            <td>{obj.material}</td>
                                                                            <td>{obj.totalFmt}</td>
                                                                        </tr>
                                                                    ))}
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div className='mt-4'>
                                                            <h6 className='text-dark'><b>2. Khuyến mãi</b></h6>
                                                            <table className="table table-bordered mt-2" id="dataTable"
                                                                width="100%" cellSpacing={0}>
                                                                <thead>
                                                                    <tr className='text-bold text-dark'>
                                                                        <th className='text-center'>No</th>
                                                                        <th>Tên</th>
                                                                        <th>Voucher</th>
                                                                        <th>Giảm giá(%)</th>
                                                                        <th>Loại</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    {detail.promotions && detail.promotions.map((pro, index) => (
                                                                        <tr key={index}>
                                                                            <td className='text-center'>{index + 1}</td>
                                                                            <td>{pro.name}</td>
                                                                            <td>{pro.voucher}</td>
                                                                            <td>{pro.percentDiscount}</td>
                                                                            <td>{pro.typeName}</td>
                                                                        </tr>
                                                                    ))}
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                        <div className='mt-4'>
                                                            <h6 className='text-dark'><b>3. Tổng tiền</b></h6>
                                                        </div>
                                                    </div>
                                                    <div className="modal-footer">
                                                        <button type="button" className="btn btn-secondary"
                                                            data-dismiss="modal">Đóng
                                                        </button>
                                                        <button type="button" className="btn btn-primary">Lưu
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>} */}

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
                        </tbody>
                    </table>
                    <div className='p-2 row align-items-center justify-content-between'>
                        <div className='col d-flex align-items-center'>
                            Hiển thị: <select className='border-1 form-control col-1 mx-2'
                                onChange={handleChangePageSizeFilter}>
                                <option value={10}>10</option>
                                <option value={20}>20</option>
                                <option value={50}>50</option>
                                <option value={100}>100</option>
                            </select>
                        </div>
                        <div className=''>
                            <button className='btn btn-outline-dark btn-sm mx-1 px-2'
                                onClick={() => handleChangePageFilter(pageInfo.page - 1)}
                                disabled={pageInfo.page == 0}>Trước
                            </button>
                            <button className='btn btn-outline-dark btn-sm mx-1 px-2'
                                onClick={() => handleChangePageFilter(pageInfo.page + 1)}
                                disabled={pageInfo.page == pageInfo.totalPages - 1}>Sau
                            </button>
                            <span>Trang {pageInfo.page + 1}/{pageInfo.totalPages}</span>
                        </div>
                    </div>
                </div>
            </div>
        </div >
    )
}

export default OrderPage