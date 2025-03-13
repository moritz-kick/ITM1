const express = require('express');
const bodyParser = require('body-parser');
const motorRoutes = require('./routes/motor');
const WebSocket = require('ws');
const http = require('http');
const path = require('path');

const app = express();
app.use(bodyParser.json());

// API routes
app.use('/api/motor', motorRoutes);

// Serve static files from the frontend build folder
const frontendBuildPath = path.join(__dirname, '../frontend/build');
app.use(express.static(frontendBuildPath));

// For any routes not handled by the API, send back the React app.
app.get('*', (req, res) => {
  res.sendFile(path.join(frontendBuildPath, 'index.html'));
});

const PORT = process.env.PORT || 3000;
const server = http.createServer(app);

// WebSocket server for real-time updates
const wss = new WebSocket.Server({ server });
wss.on('connection', ws => {
  console.log('WebSocket client connected');
  ws.send(JSON.stringify({ type: 'message', message: 'WebSocket connection established' }));
});

server.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
