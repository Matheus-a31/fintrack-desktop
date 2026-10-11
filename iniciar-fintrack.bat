@echo off
cd /d "%~dp0"
if exist "fintrack\dist\FinTrack\FinTrack.exe" (
    start "" "fintrack\dist\FinTrack\FinTrack.exe"
) else if exist "dist\FinTrack\FinTrack.exe" (
    start "" "dist\FinTrack\FinTrack.exe"
) else if exist "fintrack\target\fintrack.jar" (
    start "" javaw -jar "fintrack\target\fintrack.jar"
) else (
    cd fintrack
    start "" mvn javafx:run
)
