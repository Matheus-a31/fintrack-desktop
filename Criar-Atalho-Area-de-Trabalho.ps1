# Script para criar o atalho do FinTrack na Area de Trabalho com o icone oficial da logo
$ErrorActionPreference = "Stop"

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$exePath = Join-Path $scriptDir "fintrack\dist\FinTrack\FinTrack.exe"
$iconPath = Join-Path $scriptDir "fintrack\app-icon.ico"

if (-not (Test-Path $exePath)) {
    $exePath = Join-Path $scriptDir "dist\FinTrack\FinTrack.exe"
    $iconPath = Join-Path $scriptDir "app-icon.ico"
}

if (-not (Test-Path $exePath)) {
    $exePath = Join-Path $scriptDir "iniciar-fintrack.vbs"
}

$workingDir = Split-Path -Parent $exePath
$desktopPath = [Environment]::GetFolderPath("Desktop")
$shortcutPath = Join-Path $desktopPath "FinTrack.lnk"

$WshShell = New-Object -ComObject WScript.Shell
$Shortcut = $WshShell.CreateShortcut($shortcutPath)
$Shortcut.TargetPath = $exePath
$Shortcut.WorkingDirectory = $workingDir
$Shortcut.Description = "FinTrack - Controle Financeiro Pessoal"
if (Test-Path $iconPath) {
    $Shortcut.IconLocation = "$iconPath,0"
}
$Shortcut.Save()

Write-Host "=========================================================="
Write-Host " Atalho do FinTrack criado com sucesso na Area de Trabalho!" -ForegroundColor Green
Write-Host " Local do Atalho: $shortcutPath"
Write-Host " Destino:         $exePath"
Write-Host " Icone:           $iconPath"
Write-Host "=========================================================="
