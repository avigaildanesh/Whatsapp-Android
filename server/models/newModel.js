const mongoose = require('mongoose');

const messageSchema = new mongoose.Schema({
  id: { type: Number, required: true },
  created: {
    type: String,
    default: () => {
      const date = new Date();
      date.setHours(date.getHours() + 3);
      return date.toLocaleString('he-IL', { timeZone: 'GMT' });
    },
  },
  sender: {
    username: { type: String, required: true },
  },
  content: { type: String, required: true },
});

const contactSchema = new mongoose.Schema({
    username: { type: String, required: true },
    displayName: { type: String, required: true },
    profilePic: { type: String, required: true },
  });

const chatSchema = new mongoose.Schema({
  id: { type: Number, required: true },
  contact: contactSchema,
  messages: [messageSchema],
});

const userSchema = new mongoose.Schema({
  username: { type: String, required: true },
  displayName: { type: String, required: true },
  profilePic: { type: String, required: true },
  password: { type: String, required: true },
  chats: [chatSchema],
});


const Message = mongoose.model('Message', messageSchema);
const Chat = mongoose.model('Chat', chatSchema);
const User = mongoose.model('User', userSchema);
const Contact= mongoose.model('Contact',contactSchema);

module.exports = { User, Chat, Message,Contact };
