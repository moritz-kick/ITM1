const express = require('express');
const bodyParser = require('body-parser');
const motorRoutes = require('./routes/motor');
const WebSocket = require('ws');
const http = require('http');
const { exec } = require('child_process');
const path = require('path');

const app = express();
app.use(bodyParser.json());
app.use('/api/motor', motorRoutes);

// Hilfsfunktion, um einen deditec-Befehl via Java CLI auszuführen
function runDeditecCommand(args, callback) {
  // Annahme: Die kompilierten Java-Klassen liegen im Ordner DelibJava (relativ zum Projektroot)
  const javaCmd = `java -cp ${path.join(__dirname, '..', 'DelibJava')} DelibJava.DeditecCLI ${args.join(' ')}`;
  exec(javaCmd, (error, stdout, stderr) => {
    if (error) {
      return callback(error, null);
    }
    try {
      const result = JSON.parse(stdout);
      callback(null, result);
    } catch (e) {
      callback(e, null);
    }
  });
}

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
