@echo off
setlocal
set "MAZE_NODE=node"
where node >nul 2>&1
if errorlevel 1 (
  set "MAZE_NODE=%USERPROFILE%\.cache\codex-runtimes\codex-primary-runtime\dependencies\node\bin\node.exe"
  if not exist "%USERPROFILE%\.cache\codex-runtimes\codex-primary-runtime\dependencies\node\bin\node.exe" (
    echo Node.js 22 or newer is required. Install it from https://nodejs.org/
    echo Then double-click Run.cmd again.
    pause
    exit /b 1
  )
)
"%MAZE_NODE%" "%~dp0server.cjs" --open
if errorlevel 1 pause
