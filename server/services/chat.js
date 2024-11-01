const { User, Chat, Contact } = require('../models/newModel');

const createContact = async (contact) => {
    const username = contact.username;
    const displayName = contact.displayName;
    const profilePic = contact.profilePic;
    const newContact = new Contact({ username, displayName, profilePic });
    return await newContact.save();
}


exports.createChat = async (user, contact) => {
    let chat = user.chats.find((chat) => chat.contact.username == contact.username);
    if (chat) {
        return chat;
    }
    let existingContact = await Contact.findOne({ username: contact.username });
    if (!existingContact) {
        existingContact = await createContact(contact);
    }
    const messages = [];
    const id = user.chats.length + 1;
    const newChat = new Chat({ id, contact: existingContact, messages });
    await newChat.save();
    user.chats.push(newChat);
    await user.save();
    return newChat;
};


exports.getChats = async (username) => {
    const user = await User.findOne({ username: username });
    const chats = user.chats;
    let items = [];
    for (let chat of chats) {
        const item = {
            id: chat.id,
            user: chat.contact,
            lastMessage: chat.messages[chat.messages.length - 1],
        };
        items.push(item);
    }
    return items.reverse();
};

exports.getChatByName = async (username ,contactName) => {
    const user = await User.findOne({ username: username });
    const chat = user.chats.find((chat) => chat.contact.username === contactName);
    return chat;
}

exports.getChatsById = async (username, id) => {
    const chat = await this.getById(username, id);
    const item = {
        id: chat.id,
        user: chat.contact,
        lastMessage: chat.messages[chat.messages.length - 1],
    };
    return item;
};

exports.getById = async (username, id) => {
    const user = await User.findOne({ username: username });
    const chat = user.chats.find((chat) => chat.id == id);
    return chat;
};


exports.deleteChat = async (username, id) => {
    const user = await User.findOne({ username: username });
    const chat = await this.getById(username, id);

    if (chat) {
        user.chats = user.chats.filter((c) => c.id != id);
        await Chat.deleteOne({ _id: chat._id });
        await user.save();
    }
};





