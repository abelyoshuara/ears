const express = require('express');
const validateToken = require('../middleware/validateToken');
const {
  regiserUser,
  loginUser,
  getUserloggedIn,
} = require('../handlers/userHandler');

const router = express.Router();

router.post('/register', regiserUser);
router.post('/login', loginUser);
router.get('/users/me', validateToken, getUserloggedIn);

module.exports = router;
