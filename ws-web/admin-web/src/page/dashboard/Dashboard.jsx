import { useEffect, useState } from "react";
import AuthService from "../../service/AuthService";
import { Link, useNavigate } from 'react-router-dom'
import AxiosApi from "../../api/AxiosApi";
import WsUrl from "../../utils/constants/WsUrl";
import HashSpinner from "../../component/spinner/HashSpinner";
import WsChart from "../../component/chart/WsChart";

const Dashboard = () => {
    const navigate = useNavigate()

    const [dashboard, setDashboard] = useState({})
    const [report, setReport] = useState({})
    const [thisWeek, setThisWeek] = useState({})
    const [lastWeek, setLastWeek] = useState({})
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const checkUser = () => {
            const isAccess = AuthService.isAccessAdminLayout()
            if (!isAccess) {
                navigate("/login")
            }
        }
        const getDashboard = async () => {
            setLoading(true)
            const res = await AxiosApi.getAuth(WsUrl.ADMIN_DASHBOARD)
            if (res) {
                const data = res.data.data
                if (data) {
                    setDashboard(data)
                    setReport(data.report)
                    setThisWeek(data.thisWeek)
                    setLastWeek(data.lastWeek)
                    setLoading(false)
                }
            }
        }

        const getNotification = async () => {
            const res = await AxiosApi.getAuth(WsUrl.ADMIN_NOTIFICATION)
        }

        checkUser()
        getDashboard()
        
    }, [])

    return (
        <div id="content-wrapper" className="d-flex flex-column">
            {loading ? <HashSpinner /> :
                <div id="content">
                    <div className="container-fluid">
                        <div className="d-sm-flex align-items-center justify-content-between mb-4">
                            <h1 className="h3 mb-0 text-gray-800">Dashboard</h1>
                            <a href="#" className="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm"><i className="fas fa-download fa-sm text-white-50" /> Generate Report</a>
                        </div>
                        <div className="row">
                            <div className="col-xl col-md-6 mb-4">
                                <div className="card border-left-primary shadow h-100 py-2">
                                    <div className="card-body">
                                        <div className="row no-gutters align-items-center">
                                            <div className="col mr-2">
                                                <Link to='order/pending' className="text-xs font-weight-bold text-primary text-uppercase mb-1" >
                                                    Đơn hàng đang chờ xử lý</Link>
                                                <div className="h5 mb-0 font-weight-bold text-gray-800">{report.pending || 0}</div>
                                            </div>
                                            <div className="col-auto">
                                                <i className="fas fa-calendar fa-2x text-gray-300" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="col-xl col-md-6 mb-4">
                                <div className="card border-left-danger shadow h-100 py-2">
                                    <div className="card-body">
                                        <div className="row no-gutters align-items-center">
                                            <div className="col mr-2">
                                                <div className="text-xs font-weight-bold text-danger text-uppercase mb-1">
                                                    ĐH bị hủy/từ chối hôm nay</div>
                                                <div className="h5 mb-0 font-weight-bold text-gray-800">{report.cancel || 0}</div>
                                            </div>
                                            <div className="col-auto">
                                                <i className="fas fa-comments fa-2x text-gray-300" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="col-xl col-md-6 mb-4">
                                <div className="card border-left-success shadow h-100 py-2">
                                    <div className="card-body">
                                        <div className="row no-gutters align-items-center">
                                            <div className="col mr-2">
                                                <div className="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                    Doanh thu hôm nay</div>
                                                <div className="h5 mb-0 font-weight-bold text-gray-800">{report.earning || 0} VND</div>
                                            </div>
                                            <div className="col-auto">
                                                <i className="fas fa-dollar-sign fa-2x text-gray-300" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="col-xl col-md-6 mb-4">
                                <div className="card border-left-info shadow h-100 py-2">
                                    <div className="card-body">
                                        <div className="row no-gutters align-items-center">
                                            <div className="col mr-2">
                                                <div className="text-xs font-weight-bold text-info text-uppercase mb-1">Doanh thu tuần này
                                                </div>
                                                <div className="row no-gutters align-items-center">
                                                    <div className="col-auto">
                                                        <div className="h5 mb-0 mr-3 font-weight-bold text-gray-800">{report.user || 0}</div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div className="col-auto">
                                                <i className="fas fa-clipboard-list fa-2x text-gray-300" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="col-xl col-md-6 mb-4">
                                <div className="card border-left-warning shadow h-100 py-2">
                                    <div className="card-body">
                                        <div className="row no-gutters align-items-center">
                                            <div className="col mr-2">
                                                <div className="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                    Người dùng mới trong tuần</div>
                                                <div className="h5 mb-0 font-weight-bold text-gray-800">{dashboard && dashboard.cancel ? dashboard.cancel : 0}</div>
                                            </div>
                                            <div className="col-auto">
                                                <i className="fas fa-comments fa-2x text-gray-300" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-xl-8 col-lg-7">
                                <div className="card shadow mb-4">
                                    <div className="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                        <h6 className="m-0 font-weight-bold text-primary">Doanh Thu 2 Tuần Gần Đây</h6>
                                        <div className="dropdown no-arrow">
                                            <a className="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <i className="fas fa-ellipsis-v fa-sm fa-fw text-gray-400" />
                                            </a>
                                            <div className="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
                                                <div className="dropdown-header">Dropdown Header:</div>
                                                <a className="dropdown-item" href="#">Action</a>
                                                <a className="dropdown-item" href="#">Another action</a>
                                                <div className="dropdown-divider" />
                                                <a className="dropdown-item" href="#">Something else here</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="card-body">
                                        {thisWeek && lastWeek ? <WsChart thisWeek={thisWeek} lastWeek={lastWeek} /> : <></>}
                                    </div>
                                </div>
                            </div>
                            <div className="col-xl-4 col-lg-5">
                                <div className="card shadow mb-4">
                                    <div className="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                        <h6 className="m-0 font-weight-bold text-primary">Revenue Sources</h6>
                                        <div className="dropdown no-arrow">
                                            <a className="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <i className="fas fa-ellipsis-v fa-sm fa-fw text-gray-400" />
                                            </a>
                                            <div className="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
                                                <div className="dropdown-header">Dropdown Header:</div>
                                                <a className="dropdown-item" href="#">Action</a>
                                                <a className="dropdown-item" href="#">Another action</a>
                                                <div className="dropdown-divider" />
                                                <a className="dropdown-item" href="#">Something else here</a>
                                            </div>
                                        </div>
                                    </div>
                                    <div className="card-body">
                                        <div className="chart-pie pt-4 pb-2">
                                            <canvas id="myPieChart" />
                                        </div>
                                        <div className="mt-4 text-center small">
                                            <span className="mr-2">
                                                <i className="fas fa-circle text-primary" /> Direct
                                            </span>
                                            <span className="mr-2">
                                                <i className="fas fa-circle text-success" /> Social
                                            </span>
                                            <span className="mr-2">
                                                <i className="fas fa-circle text-info" /> Referral
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>}
        </div>
    )

}

export default Dashboard