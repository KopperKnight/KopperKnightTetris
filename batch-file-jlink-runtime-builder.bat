:: This is the script for generating a custom Java Runtime Environment with the KopperKnight Tetris game.
:: Once this script executes successfully, this application can be launched on any Windows machine with or without
:: Java virtual machine installed, by simply executing the batch launcher at %runtimeoutput%\bin\kopperknighttetris.bat
:: all files and directorys structure must be maintained from the root %runtimeoutput% downwards because it contains a 
:: self contained slim java virtual machine to run Tetris. A Zip or tar archive of the %runtimeoutput% directory will 
:: ensure the proper mobility of the application on machines that do not have the java platform installed.

SETLOCAL

set runtimeoutput=custom_runtime
set indir=binary

jlink --output %runtimeoutput% --add-modules kopper.tetris --module-path %indir% --launcher kopperknighttetris=kopper.tetris/kopper.tetris.core.TetrisStarter

pause