const express = require('express');
const router = express.Router();
const drivingProfiles = require('../config/drivingProfiles.json');
const logger = require('../utils/logger');
const { exec } = require('child_process');
const path = require('path');

// Helper function to run a Deditec command via the Java CLI.
function runDeditecCommand(args, callback) {
  const javaCmd = `java -cp ${path.join(__dirname, '../..')} DelibJava.DeditecCLI ${args.join(' ')}`;
  logger.info(`Executing Deditec command: ${javaCmd}`);
  exec(javaCmd, (error, stdout, stderr) => {
    if (error) {
      logger.error(`Deditec command error: ${stderr}`);
      return callback(error, null);
    }
    logger.info(`Deditec command stdout: ${stdout}`);
    try {
      const result = JSON.parse(stdout);
      callback(null, result);
    } catch (e) {
      logger.error(`JSON parse error: ${e.message}`);
      callback(e, null);
    }
  });
}

// Set a specific pin (output)
router.post('/set-pin', (req, res) => {
  const { pin, state } = req.body;
  logger.info(`Received set-pin request: pin=${pin}, state=${state}`);
  runDeditecCommand(['set-pin', pin.toString(), state.toString()], (err, result) => {
    if (err) {
      logger.error(`Error in set-pin: ${err}`);
      return res.status(500).json({ error: 'Error setting pin' });
    }
    logger.info(`set-pin result: ${JSON.stringify(result)}`);
    res.json(result);
  });
});

// Get the output status of a specific pin
router.get('/get-pin-output', (req, res) => {
  const pin = req.query.pin;
  logger.info(`Received get-pin-output request: pin=${pin}`);
  runDeditecCommand(['get-pin-output', pin.toString()], (err, result) => {
    if (err) {
      logger.error(`Error in get-pin-output: ${err}`);
      return res.status(500).json({ error: 'Error getting pin output status' });
    }
    logger.info(`get-pin-output result: ${JSON.stringify(result)}`);
    res.json(result);
  });
});

router.get('/', (req, res) => {
  res.send('Server is running!');
});


// Toggle a specific pin
router.post('/toggle-pin', (req, res) => {
  const { pin } = req.body;
  logger.info(`Received toggle-pin request: pin=${pin}`);
  runDeditecCommand(['toggle-pin', pin.toString()], (err, result) => {
    if (err) {
      logger.error(`Error in toggle-pin: ${err}`);
      return res.status(500).json({ error: 'Error toggling pin' });
    }
    logger.info(`toggle-pin result: ${JSON.stringify(result)}`);
    res.json(result);
  });
});

// Get the input status of a specific pin
router.get('/get-pin-input', (req, res) => {
  const pin = req.query.pin;
  logger.info(`Received get-pin-input request: pin=${pin}`);
  runDeditecCommand(['get-pin-input', pin.toString()], (err, result) => {
    if (err) {
      logger.error(`Error in get-pin-input: ${err}`);
      return res.status(500).json({ error: 'Error getting pin input status' });
    }
    logger.info(`get-pin-input result: ${JSON.stringify(result)}`);
    res.json(result);
  });
});

// --- New stub endpoints for additional features ---

// Emergency Stop
router.post('/emergency-stop', (req, res) => {
  logger.info(`Received emergency-stop request`);
  runDeditecCommand(['emergency-stop'], (err, result) => {
    if (err) {
      logger.error(`Error in emergency-stop: ${err}`);
      return res.status(500).json({ error: 'Error executing emergency stop' });
    }
    logger.info(`emergency-stop result: ${JSON.stringify(result)}`);
    res.json(result);
  });
});

// Toggle all outputs
router.post('/toggle-all', (req, res) => {
  logger.info(`Received toggle-all request`);
  runDeditecCommand(['toggle-all'], (err, result) => {
    if (err) {
      logger.error(`Error in toggle-all: ${err}`);
      return res.status(500).json({ error: 'Error toggling all outputs' });
    }
    logger.info(`toggle-all result: ${JSON.stringify(result)}`);
    res.json(result);
  });
});

// Turn all outputs off
router.post('/all-off', (req, res) => {
  logger.info(`Received all-off request`);
  runDeditecCommand(['all-off'], (err, result) => {
    if (err) {
      logger.error(`Error in all-off: ${err}`);
      return res.status(500).json({ error: 'Error turning all outputs off' });
    }
    logger.info(`all-off result: ${JSON.stringify(result)}`);
    res.json(result);
  });
});

// Start a driving profile (stub)
router.post('/start-profile', (req, res) => {
  const { profileName } = req.body;
  logger.info(`Received start-profile request: profileName=${profileName}`);
  // Here you could add logic to apply parameters from drivingProfiles.json.
  res.json({ status: `Profile ${profileName} started (stub)` });
});

module.exports = router;
