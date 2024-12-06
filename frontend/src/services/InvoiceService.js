// src/services/InvoiceService.js
import axiosInstance from '../utils/axiosConfig';

const InvoiceService = {
    createInvoice: async (invoiceData) => {
        try {
            const response = await axiosInstance.post('/invoices', invoiceData);
            return response.data;
        } catch (error) {
            console.error('Create invoice error:', error);
            throw error;
        }
    },

    getAllInvoices: async () => {
        try {
            const response = await axiosInstance.get('/invoices');
            return response.data;
        } catch (error) {
            console.error('Fetch invoices error:', error);
            throw error;
        }
    },

    updateInvoice: async (id, invoiceData) => {
        try {
            const response = await axiosInstance.put(`/invoices/${id}`, invoiceData);
            return response.data;
        } catch (error) {
            console.error('Update invoice error:', error);
            throw error;
        }
    },

    deleteInvoice: async (id) => {
        try {
            const response = await axiosInstance.delete(`/invoices/${id}`);
            return response.data;
        } catch (error) {
            console.error('Delete invoice error:', error);
            throw error;
        }
    }
};

export default InvoiceService;