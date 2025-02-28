const express = require('express');
const router = express.Router();
const drivingProfiles = require('../config/drivingProfiles.json');
const logger = require('../utils/logger');

// Starten eines Fahrprofils
router.post('/start-profile', (req, res) => {
  const { profileName } = req.body;
  const profile = drivingProfiles[profileName];
  if (!profile) {
    logger.error(`Fahrprofil ${profileName} nicht gefunden`);
    return res.status(404).json({ error: 'Fahrprofil nicht gefunden' });
  }
  logger.info(`Fahrprofil ${profileName} wird gestartet`);
  // Logik zur Motorsteuerung (z.B. über deditec API) implementieren
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

module.exports = router;
