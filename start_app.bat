@echo off
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :3000') do (
    echo Terminating process with PID %%a...
    taskkill /PID %%a /F
)
pause

REM Beende alle Prozesse auf Port 3000
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :3000') do (
    echo Beende Prozess mit PID %%a
    taskkill /PID %%a /F 2>nul
)

timeout /t 3 >nul

netstat -ano | findstr :3000
if %errorlevel% == 0 (
    echo Fehler: Port 3000 ist immer noch belegt!
    echo Bitte starte den PC neu oder beende die Prozesse manuell.
    pause
    exit /b
)

REM Kompiliere Java Klassen
cd DelibJava
javac *.java
cd ..

REM Starte den Backend-Server, der jetzt auch die Frontend-App hostet
start /B cmd /k "cd backend && npm start"
