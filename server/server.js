const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const chatRoutes = require('./routes/chatRoutes');
const userRoutes = require('./routes/userRoutes');
const cors = require('cors');
const customEnv = require('custom-env');
const mongoose = require('mongoose');
const tokenService = require('./services/token');
const messageService = require('./services/message');
const http = require("http");
const { Server } = require("socket.io");
const path = require('path');
const { User, Chat, Contact } = require('./models/newModel');
const PORT = 5000;
const CONNECTION_STRING = "mongodb://127.0.0.1:27017";

app.use(cors());
app.use(express.json());
app.use(bodyParser.urlencoded({ extended: true }));

mongoose.connect(CONNECTION_STRING, {
  useNewUrlParser: true,
  useUnifiedTopology: true
});

app.use('/api/Chats', tokenService.checkToken, chatRoutes);
app.use('/api/Users', userRoutes);
app.post('/api/Tokens', tokenService.getToken);

const server = http.createServer(app);





const io = new Server(server, {
  cors: {
    origin: "http://localhost:3000",
    methods: ["GET", "POST"],
  },
});

io.on("connection", (socket) => {
  socket.on("sendMessage", async (data) => {
    const id = data.id;
    const msg = data.msg;
    const user = await User.findOne({ username: data.username });
    const newMessage = messageService.createMessage(user, id, msg);
    io.emit("newMessage", "hi");
  });

  socket.on("username", (data) => {
    io.emit("newUser", { username: data.username });
  });

  socket.on("newMessage", (data) => {
    io.emit("newMessage", data);
  });

  socket.on("newChat", (data) => {
    io.emit("newChat", data);
  });

  socket.on("disconnect", () => {});
});

const buildPath = path.join(__dirname, 'public', 'build');
app.use(express.static(buildPath));

app.get('*', (req, res) => {
  res.sendFile(path.join(buildPath, 'index.html'));
});

server.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
