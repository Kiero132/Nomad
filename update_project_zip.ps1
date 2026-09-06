$ProjectRoot = $PSScriptRoot
$ZipPath = Join-Path $ProjectRoot "nomad.zip"
$TempZipPath = Join-Path $ProjectRoot "nomad_temp.zip"

# Что включаем в архив
$ItemsToArchive = @(
    "src",
    "gradle",
    "build.gradle",
    "gradle.properties",
    "settings.gradle",
    "gradlew",
    "gradlew.bat"
)

# Удаляем временный архив, если остался после прошлого запуска
if (Test-Path $TempZipPath) {
    Remove-Item $TempZipPath -Force
}

# Если старый архив существует — удаляем его
# Он будет полностью заменён новой версией
if (Test-Path $ZipPath) {
    Remove-Item $ZipPath -Force
}

# Создаём список существующих файлов/папок
$ExistingItems = @()

foreach ($Item in $ItemsToArchive) {
    $Path = Join-Path $ProjectRoot $Item

    if (Test-Path $Path) {
        $ExistingItems += $Path
    }
    else {
        Write-Host "Пропущено: $Item" -ForegroundColor Yellow
    }
}

# Создаём архив
Compress-Archive `
    -Path $ExistingItems `
    -DestinationPath $TempZipPath `
    -CompressionLevel Optimal

# Переименовываем временный архив
Move-Item $TempZipPath $ZipPath -Force

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host " Архив проекта обновлён!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Файл:"
Write-Host $ZipPath
Write-Host ""

$SizeMB = [math]::Round((Get-Item $ZipPath).Length / 1MB, 2)
Write-Host "Размер: $SizeMB MB"