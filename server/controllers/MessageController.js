const MessageServise = require('../services/message');
const userService = require('../services/user');


exports.getMessagesByChatId = async (req, res) => {
  try {
    const messages = await Message.find({ chatId: req.params.id });
    res.status(200).json(messages);
  } catch (error) {
    console.error('Error:', error);
    res.status(500).json({ error: 'Server error' });
  }
};


exports.createMessage = async (req, res) => {
  const { msg } = req.body;
  const id = req.params.id;
  const user=await userService.getUserByName(req.user);
  const message= await MessageServise.createMessage(user,id,msg);
  res.status(200).json(message);
};




exports.getMessages = async (req, res) => {

  const user=await userService.getUserByName(req.user);
  const id = req.params.id;
  const messages = await MessageServise.getMessages(user,id);
  return res.json(messages);
};

module.exports = exports;
