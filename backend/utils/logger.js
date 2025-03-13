const fs = require('fs');
const path = require('path');

const logFilePath = path.join(__dirname, '..', 'logs', 'backend.log');

function logToFile(level, msg) {
    const logMessage = `${new Date().toISOString()} [${level}] ${msg}\n`;
    fs.appendFileSync(logFilePath, logMessage);
}

const logger = {
    info: msg => {
        console.log(`INFO: ${msg}`);
        logToFile('INFO', msg);
    },
    error: msg => {
        console.error(`ERROR: ${msg}`);
        logToFile('ERROR', msg);
    },
    warn: msg => {
        console.warn(`WARN: ${msg}`);
        logToFile('WARN', msg);
    }
};

module.exports = logger;
