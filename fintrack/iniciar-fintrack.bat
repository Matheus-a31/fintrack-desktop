@echo off
cd /d "%~dp0"
if exist "dist\FinTrack\FinTrack.exe" (
    start "" "dist\FinTrack\FinTrack.exe"
) else if exist "target\fintrack.jar" (
    start "" javaw -jar "target\fintrack.jar"
) else (
    echo Compilando e iniciando o FinTrack...
    mvn javafx:run
)
