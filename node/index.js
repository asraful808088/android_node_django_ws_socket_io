require("dotenv").config();
const express = require("express");
const app = express();
const http = require("http");
const path = require("path");
const server = http.createServer(app);
const { createServer } = require("./socket/socket");
app.use(
  require("cors")({
    origin: "*",
  })
);
app.use(express.static(path.join(__dirname, "static")));
createServer({ httpServer: server });
const PORT = process.env.PORT;
server.listen(PORT, [process.env.HOST1, process.env.HOST2], (err) => {
  if (err) {
    console.log("server start failed");
  } else {
    console.log(`server start on ${PORT} || http://localhost:${PORT}`);
  }
});
