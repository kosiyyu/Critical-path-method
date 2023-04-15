import axios from "axios"
import {API_BASE_URL} from "./config"

export const getData = () => {
    return axios.get(`${API_BASE_URL}`)
        .then(response => response.data)
        .catch(error => {
            console.log(error)
        });
}