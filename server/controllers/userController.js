const userService = require('../services/user')


exports.createUser = async (req, res) => {
  try {
    const { username, displayName, profilePic, password } = req.body;
    const status = await userService.userValidation(username, displayName);
    if (status) {
      return res.status(409).json({ error: status });
    }
    const createdUser = await userService.createUser(username, displayName, profilePic, password);
    res.status(200).json(createdUser);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
};

exports.getAllUsers = async (_, res) => {
  const users = await userService.getUsers();
  res.json(users);
};


exports.getUserByUsername = async (req, res) => {
  const { username } = req.params;
  const user = await userService.getUserByName(username);
  res.status(200).json(user);
  
};





