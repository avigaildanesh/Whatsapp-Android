const { User, Message,} = require('../models/newModel');


exports.getMessages = async (user, id) => {

  const chat = user.chats.find((chat) => chat.id == id);
  if (!chat) {
    throw new Error('Chat not found');
  }
  return chat.messages.reverse();
}

exports.createMessage = async (user, id, msg) => {
  const userQuery = User.findOne({ 'username': user.username }, { chats: { $elemMatch: { id: id } } });
  const friendQuery = User.findOne({ 'chats.contact.username': user.username }, { chats: { $elemMatch: { 'contact.username': user.username } } });
  
  const [userResult, friendResult] = await Promise.all([userQuery.exec(), friendQuery.exec()]);
  const chat = userResult.chats[0];
  const friendChat = friendResult.chats[0];
  const sender = {
    username: user.username
  };
  const messageId = chat.messages.length + 1;
  const newMessage = new Message({ id: messageId, sender, content: msg });

  chat.messages.push(newMessage);
  friendChat.messages.push(newMessage);

  await Promise.all([
    newMessage.save(),
    userResult.save(),
    friendResult.save()
  ]);

  return newMessage;
};








