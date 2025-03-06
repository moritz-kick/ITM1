@echo off
REM Kompiliere Java Klassen
cd DelibJava
javac *.java
cd ..

REM Starte Backend
start cmd /k "cd backend && npm start"

REM Starte Frontend
start cmd /k "cd frontend && npm start"

REM Warte 5 Sekunden, damit die Server hochfahren
timeout /t 5

REM Öffne das Frontend im Browser
start "" "chrome" "http://localhost:3000"
