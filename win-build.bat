
@echo off
SETLOCAL
set classoutdir=bin
set jaroutput=dist
set version=0.2.9-21050
set runtimeoutput=dist\win10runtime-kopper-knight-tetris-v%version%


javac -version

IF %ERRORLEVEL%==0 (
 echo the java compiler was found!
GOTO askcompile
) else (
echo the java compiler WAS NOT FOUND!
echo You need to install java in order to build and run this source code!
echo please download and install Java version 11+ at one of the following websites:
echo https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
echo https://adoptopenjdk.net/
GOTO EXITQUICK
)

:askcompile
@echo off

SET choice0=
SET /p choice0= Do you want to compile the source code found in the src directory[Y]? y/n:

IF '%choice0%'=='n' ( 
echo Script cannot proceed if nothing is compiled first.
echo Script will now exit.
GOTO EXITQUICK
) else (
GOTO compile
)

:compile

IF NOT EXIST %classoutdir%\kopper.tetris\ (
mkdir %classoutdir%\kopper.tetris\
@echo %classoutdir%\kopper.tetris\ created
)
@echo on

javac -d %classoutdir%\kopper.tetris\ src\kopper.tetris\kopper\tetris\core\*.java src\kopper.tetris\kopper\tetris\shape\*.java

javac -d %classoutdir%\kopper.tetris\ src\kopper.tetris\*.java

copy src\kopper.tetris\kopper\tetris\core\kopper.png %classoutdir%\kopper.tetris\kopper\tetris\core\kopper.png
 
@echo off
if EXIST %classoutdir%\kopper.tetris\kopper\tetris\core\TetrisStarter.class (

@echo ================================================
@echo ^|==============================================^|
@echo ^|^| The compiliation appears to be successful! ^|^|
@echo ^|==============================================^|
@echo ================================================

) else (
@echo ================================================
@echo ^|==============================================^|
@echo ^|^| The compiliation appears to have failed!   ^|^|
@echo ^|==============================================^|
@echo ================================================
GOTO EXIT
)

:askjar
@echo off

SET choice=
SET /p choice= Do you want to build an executable jar file[Y]? y/n:

IF '%choice%'=='n' ( 
GOTO nojar
) else (
GOTO yesjar
)




:yesjar

@echo yes jar chosen

IF NOT EXIST dist (
mkdir dist
echo dist\ directory created!
)

@echo on
jar -c -v -f %jaroutput%\koppertetris%version%.jar --main-class=kopper.tetris.core.TetrisStarter -C %classoutdir%\kopper.tetris . 
@echo off

if EXIST %jaroutput%\koppertetris%version%.jar (

@echo ===========================================================
@echo ^|=========================================================
@echo ^|^| The executable jar creation appears to be successful! 
@echo ^|^| The jar can be found at:                              
@echo ^|^| %jaroutput%\koppertetris%version%.jar
@echo ^|=========================================================
@echo ===========================================================

) else (

@echo ===========================================================
@echo ^|=========================================================
@echo ^|^| The executable jar creation appears to be have failed!
@echo ^|=========================================================
@echo ===========================================================

)
GOTO askjlink

:nojar
@echo no jar chosen
GOTO askjlink



:askjlink
@echo off

SET choice2=
SET /p choice2= Do you want to build an runtime image of KopperKnightTetris%version% [Y]? y/n:

IF '%choice2%'=='n' ( 
GOTO nojlink
) else (
GOTO yesjlink
)

:yesjlink
@echo off
@echo JLink KopperKnightTetris runtime image creation selected!
@echo on
jlink --output %runtimeoutput% --add-modules kopper.tetris --module-path %classoutdir% --launcher kopperknighttetris=kopper.tetris/kopper.tetris.core.TetrisStarter
copy w10runtimelauncherbat.txt %runtimeoutput%\kopperknighttetris.bat
@echo off

if exist %runtimeoutput%\bin\kopperknighttetris.bat (
@echo =============================================================================
@echo ^|===========================================================================
@echo ^|^| The win10 java runtime image of KopperKnightTetris 
@echo ^|^| was successfully created! To launch application, run the batch file  at
@echo ^|^| %runtimeoutput%\kopperknighttetris.bat
@echo ^|===========================================================================
@echo =============================================================================

) else (

@echo =============================================================================
@echo ^|===========================================================================
@echo ^|^| The win10 java runtime image creation of KopperKnightTetris failed!
@echo ^|===========================================================================
@echo =============================================================================
)

GOTO EXIT

:nojlink
@echo JLink option skipped.

:EXIT
@echo off
echo ===========================================================
echo ===========================================================
echo The windows build script is complete. 
echo You can distribute the builds in the dist directory
echo to a location on your filesystem or a compatible computer
echo system of your choosing and execute there. Or you can keep
echo everything here and simply run the 'win-run.bat' to run the
echo compiled program.

:EXITQUICK
@echo off
pause

@echo on
ENDLOCAL