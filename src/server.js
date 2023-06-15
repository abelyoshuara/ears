require('dotenv').config();
const express = require('express');
const cors = require('cors');

const app = express();
const port = process.env.PORT || 3000;

app.use(cors());
app.use(express.json());

app.get('/', (req, res) => res.status(200).json({ message: 'EARS API' }));
app.use('/', require('./routes/userRoutes'));
app.use('/hospitals', require('./routes/hospitalRoutes'));
app.use('/hospitals', require('./routes/ambulanceRoutes'));
app.use('/reservations', require('./routes/reservationRoutes'));

app.use('*', (req, res) => {
  res.status(404).json({
    success: 'fail',
    message: 'You reached a route that is not defined on this server',
  });
});

app.listen(port, () => console.log(`Server listening on port ${port}`));
