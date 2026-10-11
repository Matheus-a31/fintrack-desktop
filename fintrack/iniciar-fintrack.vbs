Set WshShell = CreateObject("WScript.Shell")
Set fso = CreateObject("Scripting.FileSystemObject")
scriptDir = fso.GetParentFolderName(WScript.ScriptFullName)

exePath = scriptDir & "\dist\FinTrack\FinTrack.exe"
jarPath = scriptDir & "\target\fintrack.jar"

If fso.FileExists(exePath) Then
    WshShell.CurrentDirectory = scriptDir & "\dist\FinTrack"
    WshShell.Run """" & exePath & """", 1, False
ElseIf fso.FileExists(jarPath) Then
    WshShell.CurrentDirectory = scriptDir
    WshShell.Run "javaw -jar """ & jarPath & """", 0, False
Else
    MsgBox "Arquivo executavel nao encontrado. Execute 'mvn package' primeiro.", 16, "FinTrack"
End If
