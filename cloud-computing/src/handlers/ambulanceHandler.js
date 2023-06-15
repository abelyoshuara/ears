const db = require('../config/db');

const hospitalsRef = db.collection('hospitals');

const addAmbulance = async (req, res) => {
  const { hospitalId } = req.params;
  const {
    kendaraan, merk, no_rangka, no_mesin, no_polisi, no_bpkb,
  } = req.body;

  if (!kendaraan) {
    return res.status(400).json({
      status: 'fail',
      message: 'Vehicle name is required',
    });
  }

  const hospitalRef = await hospitalsRef.doc(hospitalId);
  const ambulancesRef = hospitalRef.collection('ambulances');
  const docRef = await ambulancesRef.add({
    kendaraan,
    merk,
    no_rangka,
    no_mesin,
    no_polisi,
    no_bpkb,
  });

  if (docRef.id) {
    return res.status(201).json({
      status: 'success',
      message: 'Ambulance added successfully',
      data: {
        ambulanceId: docRef.id,
      },
    });
  }

  return res.status(500).json({
    status: 'error',
    message: 'Ambulance failed to add',
  });
};

const getAllAmbulances = async (req, res) => {
  const { hospitalId } = req.params;
  const ambulances = [];
  const hospitalRef = await hospitalsRef.doc(hospitalId);
  const ambulancesRef = hospitalRef.collection('ambulances');

  const snapshot = await ambulancesRef.get();

  snapshot.forEach((doc) => {
    ambulances.push(doc.data());
  });

  return res.status(200).json({
    status: 'success',
    data: {
      ambulances,
    },
  });
};

const getAmbulanceById = async (req, res) => {
  const { hospitalId, ambulanceId } = req.params;
  const hospitalRef = await hospitalsRef.doc(hospitalId);
  const ambulanceRef = hospitalRef.collection('ambulances').doc(ambulanceId);
  const doc = await ambulanceRef.get();

  if (doc.exists) {
    return res.status(200).json({
      status: 'success',
      data: {
        ambulance: doc.data(),
      },
    });
  }

  return res.status(404).json({
    status: 'fail',
    message: 'Ambulance not found',
  });
};

const editAmbulanceById = async (req, res) => {
  const { hospitalId, ambulanceId } = req.params;
  const {
    kendaraan, merk, no_rangka, no_mesin, no_polisi, no_bpkb,
  } = req.body;

  if (!kendaraan) {
    return res.status(400).json({
      status: 'fail',
      message: 'Vehicle name is required',
    });
  }

  const hospitalRef = await hospitalsRef.doc(hospitalId);
  const ambulanceRef = hospitalRef.collection('ambulances').doc(ambulanceId);
  const doc = await ambulanceRef.get();

  if (doc.exists) {
    await ambulanceRef.update({
      kendaraan,
      merk,
      no_rangka,
      no_mesin,
      no_polisi,
      no_bpkb,
    });

    return res.status(200).json({
      status: 'success',
      message: 'Ambulance updated successfully',
    });
  }

  return res.status(404).json({
    status: 'fail',
    message: 'Ambulance not found',
  });
};

const deleteAmbulanceById = async (req, res) => {
  const { hospitalId, ambulanceId } = req.params;
  const hospitalRef = await hospitalsRef.doc(hospitalId);
  const ambulanceRef = hospitalRef.collection('ambulances').doc(ambulanceId);
  const doc = await ambulanceRef.get();

  if (doc.exists) {
    await ambulanceRef.delete();

    return res.status(200).json({
      status: 'success',
      message: 'Ambulance deleted successfully',
    });
  }

  return res.status(404).json({
    status: 'fail',
    message: 'Ambulance not found',
  });
};

module.exports = {
  addAmbulance,
  getAllAmbulances,
  getAmbulanceById,
  editAmbulanceById,
  deleteAmbulanceById,
};
