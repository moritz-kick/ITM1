@echo off
REM Starte Backend
start cmd /k "cd backend && npm start"

REM Starte Frontend
start cmd /k "cd frontend && npm start"

REM Warte 5 Sekunden, damit die Server hochfahren
timeout /t 5

REM Öffne das Frontend
start "" "chrome" "http://localhost:3000"

REM Öffne eine weitere Browser-Instanz mit der dryve D1 IP-Adresse
start "" "chrome" "http://169.254.104.33"
