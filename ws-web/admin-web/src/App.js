import { Routes, Route, Outlet, useNavigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import LoginPage from "./page/login/LoginPage";
import AdminLayout from "./layout/AdminLayout";
import Dashboard from "./page/dashboard/Dashboard";
import OrderPage from "./page/order/OrderPage";
import OrderDetailPage from "./page/order/OrderDetailPage";
import NotificationPage from "./page/notification/NotificationPage";
import { useEffect } from "react";
import AuthService from "./service/AuthService";
import Account from "./page/acccount/Account";
import AccountDetails from "./page/acccount/AccountDetails";

import Category from "./page/category/Category";
import Customer from "./page/customer/Customer";
import NotFound from "./page/404/NotFound";

import DiscountListPage from "./page/discount/DiscountListPage";


function App() {
  const navigate = useNavigate();

  useEffect(() => {
    const checkUser = () => {
      const isAccess = AuthService.isAccessAdminLayout();
      if (!isAccess) {
        navigate("/login");
      }
    };
    // checkUser()
  });

  return (
    <div className="page-top">
      <div className="wrapper">
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="" element={<AdminLayout />}>
            <Route path="/" element={<Account />} />
            <Route path="order" element={<OrderPage />} />
            <Route path="order/:status" element={<OrderPage />} />
            <Route path="order/detail/:id" element={<OrderDetailPage />} />
            <Route path="account" element={<Account />} />
            <Route path="account/:id" element={<AccountDetails />} />
            <Route path="category" element={<Category />} />
            <Route path="category/:id" element={<AccountDetails />} />
            <Route path="customer" element={<Customer />} />
            <Route path="customer/:id" element={<AccountDetails />} />
            <Route path="notification" element={<NotificationPage />} />

            <Route path="404" element={<NotFound />} />

            <Route path="discount" element={<DiscountListPage />} />

          </Route>
        </Routes>
        <ToastContainer />
      </div>
    </div>
  );
}

export default App;
