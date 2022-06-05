import React from 'react'
import { Outlet } from 'react-router-dom'
import Footer from '../component/footer/Footer'
import Sidebar from '../component/sidebar/Sidebar'
import Topbar from '../component/topbar/Topbar'

const AdminLayout = () => {
    return (
        <div id="page-top">
            <div id="wrapper">
                <Sidebar />
                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content">
                        <Topbar />
                        <Outlet />
                        <Footer />
                    </div>
                </div>

            </div>
        </div>
    )
}

export default AdminLayout