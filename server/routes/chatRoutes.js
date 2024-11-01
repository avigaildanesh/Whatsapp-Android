const express = require('express');
const router = express.Router();
const chatController = require('../controllers/chatController');
const messageController = require('../controllers/MessageController');


router.get('/', chatController.getChats);

router.post('/', chatController.createChat);

router.get('/:id', chatController.getChatById);

router.delete('/:id', chatController.deleteChat);

router.post('/:id/Messages', messageController.createMessage);

router.get('/:id/Messages', messageController.getMessages);

module.exports = router;