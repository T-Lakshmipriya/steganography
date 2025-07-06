@echo off
title Audio Steganography
cls

:menu
echo ==========================
echo  Audio Steganography Menu
echo ==========================
echo 1. Record Audio
echo 2. Encode Message into Audio
echo 3. Decode Message from Audio
echo 4. Exit
echo ==========================
set /p choice="Enter your choice: "

if "%choice%"=="1" goto record
if "%choice%"=="2" goto encode
if "%choice%"=="3" goto decode
if "%choice%"=="4" goto exit

echo Invalid choice, try again!
pause
goto menu

:record
echo Recording audio...
java AudioRecorder audio_steg.wav 5
echo Audio recorded successfully! Saved as audio_steg.wav.
pause
goto menu

:encode
set /p message="Enter message to encode: "
java AudioSteg encode audio_steg.wav encoded_audio.wav "%message%"
echo Message encoded successfully! Saved as encoded_audio.wav.
pause
goto menu

:decode
java AudioSteg decode encoded_audio.wav
pause
goto menu

:exit
echo Exiting program...
exit
