import React from 'react'
import { useEffect } from 'react'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import AxiosApi from '../../api/AxiosApi'
import HashSpinner from '../../component/spinner/HashSpinner'
import WsUrl from '../../utils/constants/WsUrl'

const NotificationPage = () => {

    const [pageSize, setPageSize] = useState(10)
    const [notifications, setNotifications] = useState([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetchNotifications = async () => {
            setLoading(true)
            const res = await AxiosApi.getAuth(`${WsUrl.ADMIN_NOTIFICATION_SEARCH}?pageSize=${pageSize}`)
            if (res) {
                setNotifications(res.data)
                setLoading(false)
            }
        }
        fetchNotifications()
    }, [pageSize])

    const handleChangePageSize = async () => {
        setPageSize(pageSize + 1)
    }

    const handleReadById = async id => {
        const res = await AxiosApi.getAuth(`${WsUrl.ADMIN_NOTIFICATION_READ}?id=${id}`)
    }

    return (
        <div className="container-fluid">
            <div className="card shadow mb-4">
                <div className="card-header">
                    <h3><span className="m-0 font-weight-bold text-primary">Thông báo</span></h3>
                </div>
                {loading ? <HashSpinner /> : <div className="card-body">
                    {notifications && notifications.map(obj => (
                        <Link to={`/order/detail/${obj.objectTypeId}`} className="dropdown-item d-flex align-items-center py-4" href="#" key={obj.id} onClick={() => handleReadById(obj.id)}>
                            <div className="mr-3">
                                <div className={obj.div}>
                                    <i className={obj.icon} />
                                </div>
                            </div>
                            <div>
                                <div className="small text-gray-500">{obj.createdDate}</div>
                                <span className={obj.isRead || 'font-weight-bold'} style={{
                                    lineHeight: '0.2em',
                                    overflow: 'hidden',
                                    whiteSpace: 'normal',
                                    textOverflow: 'ellipsis',
                                    width: '100%',
                                    height: '1em'
                                }}>{obj.content}</span>
                            </div>
                        </Link>
                    ))}

                    {notifications && notifications.length >= pageSize ?<a href='#' onClick={handleChangePageSize} className='nav-link p-2 row align-items-center justify-content-center text-center'>Xem thêm...
                    </a> : <></>}
                </div>}
            </div>
        </div>)
}

export default NotificationPage