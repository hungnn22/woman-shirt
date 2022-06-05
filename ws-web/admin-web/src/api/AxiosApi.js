import axios from "axios";
import WsField from "../utils/constants/WsField";
import WsUrl from "../utils/constants/WsUrl";
import WsValue from "../utils/constants/WsValue";
import {logDOM} from "@testing-library/react";

const axiosIns = axios.create({
    baseURL: WsUrl.BASE
})

const getConfig = () => {
    const accessToken = localStorage.getItem(WsField.ACCESS_TOKEN_ADMIN)
    const author = `${WsValue.BEARER}` + accessToken.trim()
    return {
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `${author}`
        }
    }
}

/** Auth */
const getAuth = url => {
    return axiosIns.get(url, getConfig())
}

const postAuth = (url, body) => {
    return axiosIns.post(`${url}`, body, getConfig())
}


/** No Auth */

const get = url => (
    axiosIns.get(`${WsValue.NO_AUTH}/${url}`)
)

const post = (url, body) => (
    axiosIns.post(`${WsValue.NO_AUTH}/${url}`, body)
)


export default {
    getAuth,
    postAuth,
    get,
    post
}
