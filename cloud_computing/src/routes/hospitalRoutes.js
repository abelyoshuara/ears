const express = require('express');
const validateToken = require('../middleware/validateToken');
const {
  addHospital,
  getAllHospitals,
  getHospitalById,
  editHospitalById,
  deleteHospitalById,
} = require('../handlers/hospitalHandler');

const router = express.Router();

router.use(validateToken);
router.route('/').post(addHospital).get(getAllHospitals);
router.route('/:id').get(getHospitalById).put(editHospitalById).delete(deleteHospitalById);

module.exports = router;
