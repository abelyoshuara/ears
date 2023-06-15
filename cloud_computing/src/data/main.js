/* eslint-disable no-unused-vars */
const db = require('../config/db');
const hospitals = require('./hospitals');
const ambulances = require('./ambulances');
const users = require('./users');

const hospitalsRef = db.collection('hospitals');
const usersRef = db.collection('users');

const initialHospitalsCollectionWithAmbulance = () => {
  hospitals.map(async (hospital, index) => {
    const docRef = await hospitalsRef.add(hospital);

    if (docRef.id) {
      const hospitalRef = await hospitalsRef.doc(docRef.id);
      const ambulancesRef = await hospitalRef.collection('ambulances');

      try {
        const docRef2 = await ambulancesRef.add(ambulances[index]);
        console.log('Document added with ID:', docRef2.id);
      } catch (error) {
        console.error('Error adding document:', error);
      }
    }
  });
};

const initialUsersCollection = () => {
  users.map(async (user) => {
    await db.collection('users').add(user);
  });
};

const deleteAmbulanceCollection = async () => {
  const snapshot = await hospitalsRef.get();

  snapshot.forEach(async (doc) => {
    try {
      const hospitalRef = await hospitalsRef.doc(doc.id);
      const ambulancesRef = await hospitalRef.collection('ambulances');
      const snapshot2 = await ambulancesRef.get();

      const batch = db.batch();

      snapshot2.forEach((doc2) => {
        batch.delete(doc2.ref);
      });

      await batch.commit();

      console.log('Collection successfully deleted');
    } catch (error) {
      console.error('Error: ', error);
    }
  });
};

const deleteHospitalsCollection = async () => {
  try {
    const snapshot = await hospitalsRef.get();

    const batch = db.batch();

    snapshot.forEach((doc) => {
      batch.delete(doc.ref);
    });

    await batch.commit();

    console.log('Collection successfully deleted');
  } catch (error) {
    console.error('Error: ', error);
  }
};

const deleteUsersCollection = async () => {
  try {
    const snapshot = await usersRef.get();

    const batch = db.batch();

    snapshot.forEach((doc) => {
      batch.delete(doc.ref);
    });

    await batch.commit();

    console.log('Collection successfully deleted');
  } catch (error) {
    console.error('Error: ', error);
  }
};

initialHospitalsCollectionWithAmbulance();
initialUsersCollection();
// deleteAmbulanceCollection();
// deleteHospitalsCollection();
// deleteUsersCollection();
