require('dotenv').config();
const Firestore = require('@google-cloud/firestore');

const db = new Firestore({
  projectId: process.env.PROJECT_ID,
  keyFilename: './serviceaccountkey.json',
});

module.exports = db;
