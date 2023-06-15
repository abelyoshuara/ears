const express = require('express');
const validateToken = require('../middleware/validateToken');
const {
  addReservation,
  getAllReservations,
  getReservationById,
  editReservationById,
  deleteReservationById,
} = require('../handlers/reservationHandler');

const router = express.Router();

router.use(validateToken);
router.route('/').post(addReservation).get(getAllReservations);
router.route('/:id').get(getReservationById).put(editReservationById).delete(deleteReservationById);

module.exports = router;
