import {toast} from "react-toastify"
import 'react-toastify/dist/ReactToastify.css'
import WsToastType from "./constants/WsToastType";

const options = {
    autoClose: 1000,
    theme: 'colored'
}

// toast.configure()
const createToast = (type, mess) => {

    switch (type) {
        case WsToastType.SUCCESS: {
            toast.success(mess, options)
            break
        }
        case WsToastType.ERROR: {
            toast.error(mess, options)
            break
        }
        case WsToastType.INFO: {
            toast.info(mess, options)
            break
        }
        case WsToastType.WARNING: {
            toast.warning(mess, options)
            break
        }
        default:
            break
    }

}

export default {
    createToast
}