const jwt = require('jsonwebtoken');
const { User } = require('../models/newModel');

const ACCESS_TOKEN_SECRET="dba0ebaaf34b034516e3c509978237f41b050096b0ffe258c0be18ec62f66292f2394c09a1ad9244830a0bbf523faa673c2226ee66f3de929f795435ef3249d5";

exports.checkToken = (req, res, next) => {
  const authHeader = req.headers.authorization;
  if (authHeader) {
    const token = authHeader.split(' ')[1];
    jwt.verify(token, ACCESS_TOKEN_SECRET, (err, user) => {
      if (err) {
        return res.sendStatus(403);
      }
      req.user = user.username;
      next();
    });
  } else {
    res.sendStatus(401);
  }
};

exports.getToken = async (req, res) => {

  const { username, password } = req.body;
  const token = await createToken(username, password);
  if (!token) {
    return res.status(404).json({ error: 'username and/or password not valid' });
  }
  res.status(200).send(token);
};

const createToken = async (username, password) => {
  const existingUser = await User.findOne({ username: username }).exec();
  if (!existingUser || existingUser.password !== password) {
    return null;
  }
  const token = generateToken(username);
  return token;
}

function generateToken(username) {
  const payload = {
    username: username,
  };
  const options = {
    expiresIn: '1h',
  };
  return jwt.sign(payload, ACCESS_TOKEN_SECRET, options);
}