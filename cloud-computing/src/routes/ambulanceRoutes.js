const express = require('express');
const validateToken = require('../middleware/validateToken');
const {
  addAmbulance,
  getAllAmbulances,
  getAmbulanceById,
  editAmbulanceById,
  deleteAmbulanceById,
} = require('../handlers/ambulanceHandler');

const router = express.Router();

router.use(validateToken);
router.route('/:hospitalId/ambulances').post(addAmbulance).get(getAllAmbulances);
router.route('/:hospitalId/ambulances/:ambulanceId').get(getAmbulanceById).put(editAmbulanceById).delete(deleteAmbulanceById);

module.exports = router;
