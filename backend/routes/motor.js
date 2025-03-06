const express = require('express');
const router = express.Router();
const drivingProfiles = require('../config/drivingProfiles.json');
const logger = require('../utils/logger');
const { exec } = require('child_process');
const path = require('path');

// Hilfsfunktion zur Ausführung von deditec-Befehlen
function runDeditecCommand(args, callback) {
  const javaCmd = `java -cp ${path.join(__dirname, '..', '..', 'DelibJava')} DelibJava.DeditecCLI ${args.join(' ')}`;
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

// Starten eines Fahrprofils
router.post('/start-profile', (req, res) => {
  const { profileName } = req.body;
  const profile = drivingProfiles[profileName];
  if (!profile) {
    logger.error(`Fahrprofil ${profileName} nicht gefunden`);
    return res.status(404).json({ error: 'Fahrprofil nicht gefunden' });
  }
  logger.info(`Fahrprofil ${profileName} wird gestartet`);
  // Hier kann später die deditec-API eingebunden werden
  res.json({ status: 'Fahrprofil gestartet', profile });
});

// Manuelle Steuerung
router.post('/manual-control', (req, res) => {
  const { command } = req.body;
  logger.info(`Manuelle Steuerung: ${command}`);
  res.json({ status: 'Befehl ausgeführt', command });
});

// Not-Aus
router.post('/emergency-stop', (req, res) => {
  logger.warn('Not-Aus aktiviert');
  res.json({ status: 'Not-Aus aktiviert' });
});

// Setze einzelnen Output
router.post('/set-output', (req, res) => {
  const { pin, state } = req.body;
  runDeditecCommand(['set-output', pin.toString(), state.toString()], (err, result) => {
    if (err) {
      logger.error(`Fehler bei set-output: ${err}`);
      return res.status(500).json({ error: 'Fehler beim Setzen des Outputs' });
    }
    res.json(result);
  });
});

// Toggle aller Outputs
router.post('/toggle-all', (req, res) => {
  runDeditecCommand(['toggle-all'], (err, result) => {
    if (err) {
      logger.error(`Fehler bei toggle-all: ${err}`);
      return res.status(500).json({ error: 'Fehler beim Umschalten aller Outputs' });
    }
    res.json(result);
  });
});

// Alle Outputs ausschalten
router.post('/all-off', (req, res) => {
  runDeditecCommand(['all-off'], (err, result) => {
    if (err) {
      logger.error(`Fehler bei all-off: ${err}`);
      return res.status(500).json({ error: 'Fehler beim Ausschalten aller Outputs' });
    }
    res.json(result);
  });
});

// Abfrage der digitalen Eingänge
router.get('/get-inputs', (req, res) => {
  runDeditecCommand(['get-inputs'], (err, result) => {
    if (err) {
      logger.error(`Fehler bei get-inputs: ${err}`);
      return res.status(500).json({ error: 'Fehler beim Abrufen der Eingänge' });
    }
    res.json(result);
  });
});

module.exports = router;
