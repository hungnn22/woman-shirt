import React, { useState } from 'react'
import { useEffect } from 'react'
import { NavLink } from 'react-router-dom'
import AxiosApi from '../../api/AxiosApi'
import AuthService from '../../service/AuthService'
import WsUrl from '../../utils/constants/WsUrl'

const Topbar = () => {

    const [unreadNumber, setUnreadNumber] = useState()
    const [notifications, setNotifications] = useState([])

    const name = AuthService.getNameOfCurrentUser()

    const handleLogout = () => {
        AuthService.logout()
    }

    useEffect(() => {
        getNotification()
    }, [])

    const getNotification = async () => {
        const res = await AxiosApi.getAuth(WsUrl.ADMIN_NOTIFICATION)
        if (res) {
            const { data } = res.data
            console.log(data);
            setUnreadNumber(data.unreadNumber)
            setNotifications(data.notifications)
        }
    }

    return (
        <nav className="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">
            <button id="sidebarToggleTop" className="btn btn-link d-md-none rounded-circle mr-3">
                <i className="fa fa-bars" />
            </button>
            <ul className="navbar-nav ml-auto">
                <li className="nav-item dropdown no-arrow d-sm-none">
                    <a className="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i className="fas fa-search fa-fw" />
                    </a>
                    <div className="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                        <form className="form-inline mr-auto w-100 navbar-search">
                            <div className="input-group">
                                <input type="text" className="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2" />
                                <div className="input-group-append">
                                    <button className="btn btn-primary" type="button">
                                        <i className="fas fa-search fa-sm" />
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </li>
                <li className="nav-item dropdown no-arrow mx-1">
                    <a className="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i className="fas fa-bell fa-fw" />
                        {unreadNumber > 0 && <span className="badge badge-danger badge-counter">{unreadNumber}</span>}
                    </a>
                    <div className="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="alertsDropdown">
                        <h6 className="dropdown-header">
                            Thông báo
                        </h6>
                        {notifications && notifications.map(obj => (
                            <a className="dropdown-item d-flex align-items-center bg-light" href="#" key={obj.id}>
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
                            </a>
                        ))}
                        <a className="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
                    </div>
                </li>
                {/* <li className="nav-item dropdown no-arrow mx-1">
                    <a className="nav-link dropdown-toggle" href="#" id="messagesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <i className="fas fa-envelope fa-fw" />
                        <span className="badge badge-danger badge-counter">7</span>
                    </a>
                    <div className="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="messagesDropdown">
                        <h6 className="dropdown-header">
                            Message Center
                        </h6>
                        <a className="dropdown-item d-flex align-items-center" href="#">
                            <div className="dropdown-list-image mr-3">
                                <img className="rounded-circle" src="img/undraw_profile_1.svg" alt="..." />
                                <div className="status-indicator bg-success" />
                            </div>
                            <div className="font-weight-bold">
                                <div className="text-truncate">Hi there! I am wondering if you can help me with a
                                    problem I've been having.</div>
                                <div className="small text-gray-500">Emily Fowler · 58m</div>
                            </div>
                        </a>
                        <a className="dropdown-item d-flex align-items-center" href="#">
                            <div className="dropdown-list-image mr-3">
                                <img className="rounded-circle" src="img/undraw_profile_2.svg" alt="..." />
                                <div className="status-indicator" />
                            </div>
                            <div>
                                <div className="text-truncate">I have the photos that you ordered last month, how
                                    would you like them sent to you?</div>
                                <div className="small text-gray-500">Jae Chun · 1d</div>
                            </div>
                        </a>
                        <a className="dropdown-item d-flex align-items-center" href="#">
                            <div className="dropdown-list-image mr-3">
                                <img className="rounded-circle" src="img/undraw_profile_3.svg" alt="..." />
                                <div className="status-indicator bg-warning" />
                            </div>
                            <div>
                                <div className="text-truncate">Last month's report looks great, I am very happy with
                                    the progress so far, keep up the good work!</div>
                                <div className="small text-gray-500">Morgan Alvarez · 2d</div>
                            </div>
                        </a>
                        <a className="dropdown-item d-flex align-items-center" href="#">
                            <div className="dropdown-list-image mr-3">
                                <img className="rounded-circle" src="https://source.unsplash.com/Mv9hjnEUHR4/60x60" alt="..." />
                                <div className="status-indicator bg-success" />
                            </div>
                            <div>
                                <div className="text-truncate">Am I a good boy? The reason I ask is because someone
                                    told me that people say this to all dogs, even if they aren't good...</div>
                                <div className="small text-gray-500">Chicken the Dog · 2w</div>
                            </div>
                        </a>
                        <a className="dropdown-item text-center small text-gray-500" href="#">Read More Messages</a>
                    </div>
                </li> */}
                <div className="topbar-divider d-none d-sm-block" />
                <li className="nav-item dropdown no-arrow">
                    <a className="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <span className="mr-2 d-none d-lg-inline text-gray-600 small">Xin chào{name ? `, ${name}` : ''}</span>
                        <img className="img-profile rounded-circle" src="img/undraw_profile.svg" />
                    </a>
                    <div className="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                        <a className="dropdown-item" href="#">
                            <i className="fas fa-user fa-sm fa-fw mr-2 text-gray-400" />
                            Cá nhân
                        </a>
                        <a className="dropdown-item" href="#">
                            <i className="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400" />
                            Settings
                        </a>
                        <a className="dropdown-item" href="#">
                            <i className="fas fa-list fa-sm fa-fw mr-2 text-gray-400" />
                            Activity Log
                        </a>
                        <div className="dropdown-divider" />
                        <NavLink to='/login' className="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal" onClick={handleLogout}>
                            <i className="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400" />
                            Đăng xuất
                        </NavLink>
                    </div>
                </li>
            </ul>
        </nav>)
}

export default Topbar