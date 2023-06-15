require('dotenv').config();
const jwt = require('jsonwebtoken');

const validateToken = async (req, res, next) => {
  const authHeader = req.headers.Authorization || req.headers.authorization;

  if (!(authHeader && authHeader.startsWith('Bearer'))) {
    return res.status(403).json({
      status: 'fail',
      message: 'A token is required for authentication',
    });
  }

  const [, token] = authHeader.split(' ');

  try {
    const decoded = jwt.verify(token, process.env.TOKEN_KEY);
    req.user = decoded.user;
  } catch (error) {
    return res.status(401).json({
      status: 'fail',
      message: 'Invalid token',
    });
  }

  return next();
};

module.exports = validateToken;
