@echo off
setlocal

:: Ruta al SDK de JavaFX
set PATH_TO_FX=C:\Users\IvanM\Downloads\javafx-sdk-21.0.9\lib

:: Ruta a las librerías de iText (ajusta si están en otra carpeta)
set ITEXT_JARS=lib\itextpdf-7.2.5.jar;lib\kernel-7.2.5.jar;lib\layout-7.2.5.jar

:: Limpia carpeta bin
rd /s /q bin
mkdir bin

:: Compila con compatibilidad para Java 21
javac --release 21 -cp "%ITEXT_JARS%" --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml -d bin src\CRUD\*.java

:: Ejecuta la aplicación
java -cp "bin;%ITEXT_JARS%" --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml CRUD.MainApp

pause