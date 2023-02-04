const { Server } = require("socket.io");

function createServer({ httpServer }) {
  const io = new Server(httpServer);
  io.on("connection", (socket) => {
    console.log("user connected");
    const trackInterval = setInterval(() => {
      socket.emit(
        "crypto_rate",
        JSON.stringify({
          BTC: Math.random() * 10000,
          LIT: Math.random() * 100,
          ETH: Math.random() * 1000,
        })
      );
    }, 1000);
    socket.on("disconnect", () => {
      clearInterval(trackInterval);
      console.log("user disconnect");
    });
  });
}
module.exports = { createServer };
