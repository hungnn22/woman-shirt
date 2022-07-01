import { Routes, Route, Outlet } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import LoginPage from "./page/login/LoginPage";
import AdminLayout from "./layout/AdminLayout";
import Dashboard from "./page/dashboard/Dashboard";
import OrderPage from "./page/order/OrderPage";
import OrderDetailPage from "./page/order/OrderDetailPage";

function App() {

  return (
    <div className="page-top">
      <div className="wrapper">
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="" element={<AdminLayout />} >
            <Route path="/" element={<Dashboard />} />
            <Route path='order' element={<OrderPage />} />
            <Route path='order/:status' element={<OrderPage />} />
            <Route path='order/detail/:id' element={<OrderDetailPage />} />
          </Route>
        </Routes>
        <ToastContainer />
      </div>
    </div>

  );
}

export default App;
