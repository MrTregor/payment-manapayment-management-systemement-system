import React, { useState, useEffect } from 'react';
import {
    Typography,
    Container,
    Paper,
    CircularProgress,
    Box
} from '@mui/material';

const AboutPage = () => {
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const timer = setTimeout(() => {
            setLoading(false);
        }, 500);

        return () => clearTimeout(timer);
    }, []);

    if (loading) {
        return (
            <Container>
                <Box
                    display="flex"
                    justifyContent="center"
                    alignItems="center"
                    minHeight="70vh"
                >
                    <CircularProgress />
                </Box>
            </Container>
        );
    }

    return (
        <Container maxWidth="md">
            <Paper
                elevation={3}
                sx={{
                    padding: 3,
                    marginTop: 4,
                    backgroundColor: '#f5f5f5'
                }}
            >
                <Typography variant="h4" gutterBottom align="center">
                    О проекте и авторе
                </Typography>

                <Typography variant="h6" sx={{ mt: 2 }}>
                    Информация о проекте
                </Typography>
                <Typography variant="body1" paragraph>
                    Payment Management System - современное веб-приложение
                    для управления финансовыми транзакциями и договорами.
                </Typography>

                <Typography variant="h6" sx={{ mt: 2 }}>
                    Личная информация
                </Typography>
                <Typography variant="body1">
                    <strong>ФИО:</strong> Кусакина Александра Сергеевна
                </Typography>
                <Typography variant="body1">
                    <strong>Группа:</strong> ПИ22-1в
                </Typography>
                <Typography variant="body1">
                    <strong>Университет:</strong> Финансовый университет при правительстве РФ
                </Typography>
                <Typography variant="body1">
                    <strong>Email:</strong> 222796@edu.fa.ru
                </Typography>

                <Typography variant="h6" sx={{ mt: 2 }}>
                    Технологический стек
                </Typography>
                <Typography variant="body1" paragraph>
                    {/* Использую данные из вашего бэкенда */}
                    Java Spring Boot, React, JWT, MySQL
                </Typography>

                <Typography variant="h6" sx={{ mt: 2 }}>
                    Сроки проекта
                </Typography>
                <Typography variant="body1">
                    <strong>Начало проекта:</strong> 11.11.2024
                </Typography>
                <Typography variant="body1">
                    <strong>Окончание проекта:</strong> 01.12.2024
                </Typography>
            </Paper>
        </Container>
    );
};

export default AboutPage;