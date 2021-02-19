SETLOCAL

set thedir=bin
set output=dist

IF EXIST %thedir%\kopper.tetris\kopper\tetris\core\TetrisStarter.class (
jar -c -v -f %output%\koppertetris.jar --main-class=kopper.tetris.core.TetrisStarter -C %thedir%\kopper.tetris . 

@echo off
@echo off
@echo off
@echo off
@echo ======================================================================
@echo ^|^|If jar packaging was successful, then one can now run the program ^|^|
@echo ^|^|by simply double clicking the koppertetris.jar file in the current^|^|
@echo ^|^|directory. Likewise, the executable jar file can be copied and ran^|^|
@echo ^|^|that same way to and on any computer that has java platform       ^|^|
@echo ^|^|installed.                                                        ^|^|
@echo ======================================================================
pause
) ELSE (
@echo off
@echo off
@echo off
@echo off
@echo off
@echo ======================================================================
@echo ^|^|The Java program is not compiled yet.                             ^|^|
@echo ^|^|Please run the batch-file-build.bat file first.                   ^|^|
@echo ^|^|Then run this batch file to package the resultant,                ^|^|
@echo ^|^|java .class files into an executable .jar file.                   ^|^|
@echo ======================================================================

pause)
ENDLOCAL