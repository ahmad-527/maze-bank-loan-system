@echo off
setlocal
cd /d "%~dp0"
set "MAZEBANK_JAVA=java"
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" set "MAZEBANK_JAVA=%JAVA_HOME%\bin\java.exe"
"%MAZEBANK_JAVA%" -version >nul 2>&1
if errorlevel 1 (
  echo Java 17 or newer is required. Install a desktop runtime from https://adoptium.net/
  pause
  exit /b 1
)
if not exist "maze-bank-demo-1.0.0-preview.jar" (
  echo The application JAR is missing. Extract the complete release ZIP first.
  pause
  exit /b 1
)
"%MAZEBANK_JAVA%" -jar "maze-bank-demo-1.0.0-preview.jar"
if errorlevel 1 (
  echo The demo could not start. Check Java 17+, the data folder, and that another copy is not running.
  pause
  exit /b 1
)
