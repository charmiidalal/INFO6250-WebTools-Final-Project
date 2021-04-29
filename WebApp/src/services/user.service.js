import axios from 'axios';
import authHeader from './auth-header';

const apiurl = 'http://localhost:8080/HuskyTimes/';

class UserService {
  getPublicContent() {
    return axios.get(apiurl + 'all');
  }

  getUserBoard() {
    return axios.get(apiurl + 'user', { headers: authHeader() });
  }

  getAdminBoard() {
    return axios.get(apiurl + 'admin', { headers: authHeader() });
  }
}

export default new UserService();