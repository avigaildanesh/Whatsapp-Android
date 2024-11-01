
const { User } = require('../models/newModel');



exports.createUser = async (username, displayName, profilePic, password) => {
  const user = new User({ username, displayName, profilePic, password });
  return await user.save();
};


exports.userValidation = async (username, displayName) => {
  const existingUser = await User.findOne({ username: username }).exec();
  if (existingUser) {
    return 'User already exists';
  }
  if (username === displayName) {
    return "Username and display name can't be the same";
  }
  return null;
};

exports.getUserById = async (id) => { return await User.find({ id: id }) };
exports.getUsers = async () => { return await User.find({}) };


exports.getUserByName = async (contactName) => {
  return await User.findOne({ username: contactName });
};


