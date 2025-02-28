const express = require('express');
const bodyParser = require('body-parser');
const motorRoutes = require('./routes/motor');
const WebSocket = require('ws');
const http = require('http');

const app = express();
app.use(bodyParser.json());
app.use('/api/motor', motorRoutes);

// HTTP-Server erstellen
const server = http.createServer(app);

// WebSocket-Server für Echtzeit-Updates
const wss = new WebSocket.Server({ server });
wss.on('connection', ws => {
  console.log('Client via WebSocket verbunden');
  ws.on('message', message => {
    console.log(`Nachricht erhalten: ${message}`);
    // Weitere Verarbeitung falls erforderlich
  });
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
  console.log(`Server läuft auf Port ${PORT}`);
});
