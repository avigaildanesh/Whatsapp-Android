const userService = require('../services/user');
const chatService = require('../services/chat');


exports.getChats = async (req, res) => {
    const chats = await chatService.getChats(req.user);
    return res.json(chats);
};

exports.createChat = async (req, res) => {

    const { contactName } = req.body;
    const user = await userService.getUserByName(req.user);
    const existUser = await userService.getUserByName(contactName);
    if (!existUser) {
        return res.status(409).json({ error: 'user does not exist' });
    }
    const chatData = await chatService.createChat(user, existUser);
    await chatService.createChat(existUser, user);
    const chat = { id: chatData.id, user: chatData.contact }
    return res.json(chat);
};


exports.getChatById = async (req, res) => {
    const { id } = req.params;
    const chat = await chatService.getChatsById(req.user, id);
    return chat;
};

exports.deleteChat = async (req, res) => {
    const username = req.user;
    const { id } = req.params;
    const chat = await chatService.getById(username, id);
    const contactName = chat.contact.username;
    const myChat = await chatService.getChatByName(contactName, username);
    await chatService.deleteChat(contactName, myChat.id);
    await chatService.deleteChat(req.user, id);
    return res.json(id);
};

module.exports = exports;