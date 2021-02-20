

@echo off
SETLOCAL
set thedir=bin

IF EXIST %thedir%\kopper.tetris\kopper\tetris\core\TetrisStarter.class (
start javaw -cp %thedir%\kopper.tetris kopper.tetris.core.TetrisStarter
) ELSE (
@echo off
@echo off
@echo off
@echo ======================================================================
@echo ^|^|The Java program is not compiled yet.                             ^|^|
@echo ^|^|Please run the win-build.bat file first,                          ^|^|
@echo ^|^|then run this batch file to run the resultant, compiled program   ^|^|
@echo ======================================================================
pause 
)
@echo off

ENDLOCAL