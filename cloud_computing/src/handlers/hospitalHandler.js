const Firestore = require('@google-cloud/firestore');
const db = require('../config/db');

const hospitalsRef = db.collection('hospitals');

const addHospital = async (req, res) => {
  const {
    name, price, room_capacity, latitude, longitude,
  } = req.body;

  if (!name) {
    return res.status(400).json({
      status: 'fail',
      message: 'Hospital name is required',
    });
  }

  const docRef = await hospitalsRef.add({
    name,
    price,
    room_capacity,
    coordinate: new Firestore.GeoPoint(parseFloat(latitude), parseFloat(longitude)),
  });

  if (docRef.id) {
    return res.status(201).json({
      status: 'success',
      message: 'Hospital added successfully',
      data: {
        hospitalId: docRef.id,
      },
    });
  }

  return res.status(500).json({
    status: 'error',
    message: 'Hospital failed to add',
  });
};

const getAllHospitals = async (req, res) => {
  const hospitals = [];
  const { name } = req.query;
  let snapshot = await hospitalsRef.get();

  if (name) {
    snapshot = await hospitalsRef
      .orderBy('name')
      .startAt(name)
      .endAt(`${name}\uf8ff`)
      .get();
  }

  snapshot.forEach((doc) => {
    hospitals.push(doc.data());
  });

  return res.status(200).json({
    status: 'success',
    data: {
      hospitals,
    },
  });
};

const getHospitalById = async (req, res) => {
  const { id } = req.params;
  const doc = await hospitalsRef.doc(id).get();

  if (doc.exists) {
    return res.status(200).json({
      status: 'success',
      data: {
        hospital: doc.data(),
      },
    });
  }

  return res.status(404).json({
    status: 'fail',
    message: 'Hospital not found',
  });
};

const editHospitalById = async (req, res) => {
  const { id } = req.params;
  const {
    name, price, room_capacity, latitude, longitude,
  } = req.body;

  if (!name) {
    return res.status(400).json({
      status: 'fail',
      message: 'Hospital name is required',
    });
  }

  const doc = await hospitalsRef.doc(id).get();

  if (doc.exists) {
    await hospitalsRef.doc(id).update({
      name,
      price,
      room_capacity,
      latitude,
      longitude,
    });

    return res.status(200).json({
      status: 'success',
      message: 'Hospital updated successfully',
    });
  }

  return res.status(404).json({
    status: 'fail',
    message: 'Hospital not found',
  });
};

const deleteHospitalById = async (req, res) => {
  const { id } = req.params;
  const doc = await hospitalsRef.doc(id).get();

  if (doc.exists) {
    await hospitalsRef.doc(id).delete();

    return res.status(200).json({
      status: 'success',
      message: 'Hospital deleted successfully',
    });
  }

  return res.status(404).json({
    status: 'fail',
    message: 'Hospital not found',
  });
};

module.exports = {
  addHospital,
  getAllHospitals,
  getHospitalById,
  editHospitalById,
  deleteHospitalById,
};
