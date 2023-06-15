const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const db = require('../config/db');

const usersRef = db.collection('users');

const regiserUser = async (req, res) => {
  const {
    firstName, lastName, phone_number, email, password,
  } = req.body;

  if (!firstName || !lastName || !email || !password) {
    return res.status(400).json({
      status: 'fail',
      message: 'The fields are required',
    });
  }

  const snapshot = await usersRef.where('email', '==', email).get();
  if (!snapshot.empty) {
    return res.status(400).json({
      status: 'fail',
      message: 'User already registered',
    });
  }

  const newPassword = await bcrypt.hash(password, 10);
  const docRef = await usersRef.add({
    firstName,
    lastName,
    phone_number,
    email,
    password: newPassword,
    role: 'member',
  });

  if (docRef.id) {
    return res.status(201).json({
      status: 'success',
      message: 'User created',
    });
  }

  return res.status(500).json({
    status: 'error',
    message: 'Registration failed',
  });
};

const loginUser = async (req, res) => {
  const { email, password } = req.body;

  if (!email || !password) {
    return res.status(400).json({
      status: 'fail',
      message: 'The fields are required',
    });
  }

  const users = [];
  const snapshot = await usersRef.where('email', '==', email).get();
  snapshot.forEach((doc) => users.push(doc.data()));

  if (snapshot.empty) {
    return res.status(400).json({
      status: 'fail',
      message: 'Email not found',
    });
  }

  const {
    email: user_email, password: user_password, firstName, lastName, phone_number, role,
  } = users[0];

  if (!await bcrypt.compare(password, user_password)) {
    return res.status(400).json({
      status: 'fail',
      message: 'Password is wrong',
    });
  }

  const accessToken = jwt.sign({
    user: {
      firstName,
      lastName,
      phone_number,
      email: user_email,
      role,
    },
  }, process.env.TOKEN_KEY, { expiresIn: '3h' });

  return res.status(200).json({
    status: 'success',
    message: 'User logged successfully',
    data: {
      accessToken,
    },
  });
};

const getUserloggedIn = (req, res) => {
  const {
    firstName, lastName, phone_number, email, role,
  } = req.user;

  res.status(200).json({
    status: 'success',
    message: 'User retrieved',
    data: {
      firstName,
      lastName,
      phone_number,
      email,
      role,
    },
  });
};

module.exports = { regiserUser, loginUser, getUserloggedIn };
