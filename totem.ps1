Add-Type -AssemblyName System.Drawing
$out = "src\main\resources\assets\nomad\textures\block"
New-Item -ItemType Directory -Force -Path $out | Out-Null

$cW = [System.Drawing.Color]::FromArgb(255,110,80,50)
$cD = [System.Drawing.Color]::FromArgb(255,60,40,25)
$T  = [System.Drawing.Color]::FromArgb(0,0,0,0)

$rows = @(
"WWWWWWWWWWWWWWWW",
"WWWWWWWWWWWWWWWW",
"WWDDWWWWWWWWDDWW",
"WWDDWWWWWWWWDDWW",
"WWWWWWWWWWWWWWWW",
"WWWWWWDDDDWWWWWW",
"WWWWWWDDDDWWWWWW",
"WWWWWWWWWWWWWWWW",
"WWDDDDDDDDDDDDWW",
"WWWWWWWWWWWWWWWW",
"WWWWDWWWWWWWDWWW",
"WWWWDWWWWWWWDWWW",
"WWWWWWWWWWWWWWWW",
"DDDDDDDDDDDDDDDD",
"WWWWWWWWWWWWWWWW",
"DDDDDDDDDDDDDDDD"
)

$bmp = New-Object System.Drawing.Bitmap 16,16
for ($y=0; $y -lt 16; $y++) {
    for ($x=0; $x -lt 16; $x++) {
        $c = $rows[$y][$x]
        $col = $T
        if ($c -eq 'W') { $col = $cW }
        elseif ($c -eq 'D') { $col = $cD }
        $bmp.SetPixel($x,$y,$col)
    }
}
$bmp.Save((Join-Path $out "totem.png"), [System.Drawing.Imaging.ImageFormat]::Png)
$bmp.Dispose()
