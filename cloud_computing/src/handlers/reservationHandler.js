const Firestore = require('@google-cloud/firestore');
const db = require('../config/db');

const usersRef = db.collection('users');
const reservationsRef = db.collection('reservations');

const addReservation = async (req, res) => {
  const {
    date, patient_id, driver_id, patient_location, driver_location, message,
  } = req.body;

  const referencedPatientRef = usersRef.doc(patient_id);
  const referencedDriverRef = usersRef.doc(driver_id);

  const docRef = await reservationsRef.add({
    date,
    patient_id: referencedPatientRef,
    driver_id: referencedDriverRef,
    patient_coordinate: new Firestore.GeoPoint(
      parseFloat(patient_location[0]),
      parseFloat(patient_location[1]),
    ),
    driver_coordinate: new Firestore.GeoPoint(
      parseFloat(driver_location[0]),
      parseFloat(driver_location[1]),
    ),
    message,
  });

  if (docRef.id) {
    return res.status(201).json({
      status: 'success',
      message: 'Reservation added successfully',
      data: {
        hospitalId: docRef.id,
      },
    });
  }

  return res.status(500).json({
    status: 'error',
    message: 'Reservation failed to add',
  });
};

const getAllReservations = async (req, res) => {
  const reservations = [];
  const snapshot = await reservationsRef.get();

  snapshot.forEach((doc) => {
    reservations.push(doc.data());
  });

  return res.status(200).json({
    status: 'success',
    data: {
      reservations,
    },
  });
};

const getReservationById = async (req, res) => {
  const { id } = req.params;
  const doc = await reservationsRef.doc(id).get();

  if (doc.exists) {
    return res.status(200).json({
      status: 'success',
      data: {
        reservation: doc.data(),
      },
    });
  }

  return res.status(404).json({
    status: 'fail',
    message: 'Reservation not found',
  });
};

const editReservationById = async (req, res) => {
  const { id } = req.params;
  const {
    date, patient_id, driver_id, patient_location, driver_location, message,
  } = req.body;

  const doc = await reservationsRef.doc(id).get();

  if (doc.exists) {
    await reservationsRef.doc(id).update({
      date,
      patient_id,
      driver_id,
      patient_coordinate: new Firestore.GeoPoint(
        parseFloat(patient_location[0]),
        parseFloat(patient_location[1]),
      ),
      driver_coordinate: new Firestore.GeoPoint(
        parseFloat(driver_location[0]),
        parseFloat(driver_location[1]),
      ),
      message,
    });

    return res.status(200).json({
      status: 'success',
      message: 'Reservation updated successfully',
    });
  }

  return res.status(404).json({
    status: 'fail',
    message: 'Reservation not found',
  });
};

const deleteReservationById = async (req, res) => {
  const { id } = req.params;
  const doc = await reservationsRef.doc(id).get();

  if (doc.exists) {
    await reservationsRef.doc(id).delete();

    return res.status(200).json({
      status: 'success',
      message: 'Reservation deleted successfully',
    });
  }

  return res.status(404).json({
    status: 'fail',
    message: 'Reservation not found',
  });
};

module.exports = {
  addReservation,
  getAllReservations,
  getReservationById,
  editReservationById,
  deleteReservationById,
};
