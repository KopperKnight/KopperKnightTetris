SETLOCAL

set outdir=commandlinecompiledbinaries

javac -d %outdir%\kopper.tetris\ src\kopper.tetris\*.java src\kopper.tetris\kopper\tetris\core\*.java src\kopper.tetris\kopper\tetris\shape\*.java

copy src\kopper.tetris\kopper\tetris\core\kopper.png %outdir%\kopper.tetris\kopper\tetris\core\kopper.png
 

@echo off
@echo off
@echo off

echo if the compilation worked correctly you can execute batch-file-run.bat to run the program (multiple files)
echo or you can package the compilation into an executable .jar file (single file)

@echo ======================================================================
@echo ^|^|If the compilation worked correctly, then you can execute the     ^|^|
@echo ^|^|compiled bytecode (made up of numerious .class files which are in ^|^|
@echo       %outdir%\
@echo ^|^| root directory) or program by executing "batch-file-run.bat"     ^|^|
@echo ======================================================================


ENDLOCAL
pause