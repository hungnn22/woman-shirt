import WsValue from "../utils/constants/WsValue"
import jwtDecode from "jwt-decode";
import WsField from "../utils/constants/WsField";

const login = (accessToken, refreshToken) => {
    if (isAdminOrStaff(accessToken)) {
        if (localStorage.getItem(WsField.ACCESS_TOKEN_ADMIN) && localStorage.getItem(WsField.REFRESH_TOKEN_ADMIN)) {
            removeTokenValues()
        }
        setTokenValues(accessToken, refreshToken)
        return true
    }
    return false
}

const logout = () => {
    removeTokenValues()
}

const removeTokenValues = () => {
    localStorage.removeItem(WsField.ACCESS_TOKEN_ADMIN)
    localStorage.removeItem(WsField.REFRESH_TOKEN_ADMIN)
}

const setTokenValues = (accessToken, refreshToken) => {
    localStorage.setItem(WsField.ACCESS_TOKEN_ADMIN, accessToken)
    localStorage.setItem(WsField.REFRESH_TOKEN_ADMIN, refreshToken)
}


const isAdminOrStaff = accessToken => {
    const user = jwtDecode(accessToken)
    return user.role == WsValue.ROLE_ADMIN || user.role == WsValue.ROLE_STAFF
}

const isAccessAdminLayout = () => {
    if (localStorage.getItem(WsField.ACCESS_TOKEN_ADMIN)) {
        return isAdminOrStaff(localStorage.getItem(WsField.ACCESS_TOKEN_ADMIN))
    }
    return false
}

const getNameOfCurrentUser = () => {
    if (localStorage.getItem(WsField.ACCESS_TOKEN_ADMIN)) {
        const user = jwtDecode(localStorage.getItem(WsField.ACCESS_TOKEN_ADMIN))
        return user.name
    }
}

const isValidExpired = expTime => {
    return new Date() <= new Date(expTime)
}

export default {
    login,
    logout,
    isAdmin: isAdminOrStaff,
    isAccessAdminLayout,
    getNameOfCurrentUser
}