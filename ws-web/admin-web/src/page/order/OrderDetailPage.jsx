import React from 'react'
import { useState } from 'react'
import { useEffect } from 'react'
import { Link, useParams } from 'react-router-dom'
import AxiosApi from '../../api/AxiosApi'
import WsUrl from '../../utils/constants/WsUrl'
import HashSpinner from "../../component/spinner/HashSpinner";

const OrderDetailPage = () => {

    const [detail, setDetail] = useState({})
    const [loading, setLoading] = useState(false)

    const { id } = useParams()

    console.log("id: ", id)

    useEffect(() => {
        const fetchDetail = async () => {
            setLoading(true)
            const axiosRes = await AxiosApi.getAuth(`${WsUrl.ADMIN_ORDER_DETAIL}/${id}`)
            if (axiosRes) {
                setDetail(axiosRes.data.data)
                setLoading(false)
            }
        }
        fetchDetail()
    }, [id])

    return (
        <>
            {loading ? <HashSpinner /> :
                <div className="container-fluid">
                    <div className="card shadow mb-4">
                        <div className="card-header">
                            <h3> <span className="m-0 font-weight-bold text-primary">Chi tiết đơn hàng</span></h3>
                        </div>
                        <div className="card-body">
                            <h6 className='text-dark'><b>1. Lịch sử đơn hàng</b></h6>
                            <ul>
                                {detail.history && detail.history.map((obj, index) => (
                                    <li key={index} className='mt-2'>{obj}</li>
                                ))}
                            </ul>
                            <hr className='mt-4' />
                            <h6 className='text-dark'><b>2. Sản phẩm</b></h6>
                            <table className="table table-bordered mt-4" id="dataTable" width="100%" cellSpacing={0}>
                                <thead>
                                    <tr className='text-bold text-dark'>
                                        <th className='text-center' style={{ width: '10px' }}>No</th>
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
                                        <tr key={index}>
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
                            <hr className='mt-4' />
                            <h6 className='text-dark'><b>3. Khuyến mãi</b></h6>
                            {(detail.promotions && detail.promotions.length > 0) &&
                                <table className="table table-bordered mt-4" id="dataTable" width="100%" cellSpacing={0}>
                                    <thead>
                                        <tr className='text-bold text-dark'>
                                            <th className='text-center' style={{ width: '10px' }}>No</th>
                                            <th>Tên</th>
                                            <th>Mã voucher</th>
                                            <th>Loại</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {detail.promotions.map((obj, index) => (
                                            <tr key={index}>
                                                <td className='text-center'>{index + 1}</td>
                                                <td>{obj.name}</td>
                                                <td>{obj.voucher}</td>
                                                <td>{obj.typeName}</td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>}
                            <hr className='mt-4' />
                            <h6 className='text-dark'><b>4. Tổng tiền</b></h6>
                            <table className="table table-bordered mt-4" id="dataTable" width="100%" cellSpacing={0}>
                                <thead>
                                    <tr className='text-bold text-dark'>
                                        <th className='text-center' style={{ width: '10px' }}>No</th>
                                        <th>Tên</th>
                                        <th>Giá(VND)</th>
                                        <th>Giảm giá(VND)</th>
                                        <th>Tổng(VND)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>1</td>
                                        <td>Mua sắm</td>
                                        <td>{detail.result && detail.result.shop ? detail.result.shop.price : 0}</td>
                                        <td>{detail.result && detail.result.shop ? detail.result.shop.discount : 0}</td>
                                        <td>{detail.result && detail.result.shop ? detail.result.shop.total : 0}</td>
                                    </tr>
                                    <tr>
                                        <td>2</td>
                                        <td>Vận chuyển</td>
                                        <td>{detail.result && detail.result.ship ? detail.result.ship.price : 0}</td>
                                        <td>{detail.result && detail.result.ship ? detail.result.ship.discount : 0}</td>
                                        <td>{detail.result && detail.result.ship ? detail.result.ship.total : 0}</td>
                                    </tr>
                                    <tr>
                                        <td colSpan={4}></td>
                                        <td className='text-dark'>
                                            <b>{detail.result && detail.result.total ? detail.result.total : 0}</b></td>
                                    </tr>
                                </tbody>
                            </table>
                            <div className='p-2 row align-items-center justify-content-between'>
                            </div>
                        </div>
                    </div>
                </div>}</>
    )
}

export default OrderDetailPage