import axiosInstance from '../utils/axiosConfig';

class ClientService {
    static async getAllClients() {
        try {
            const response = await axiosInstance.get('/client');
            return response.data;
        } catch (error) {
            console.error('Error fetching clients:', error);
            throw error;
        }
    }
}

export default ClientService;