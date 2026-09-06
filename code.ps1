Get-ChildItem -Path src\main\java -Recurse -Filter *.java |
  ForEach-Object {
    "===== " + $_.FullName.Substring((Get-Location).Path.Length + 1) + " ====="
    Get-Content $_.FullName -Raw
  } | Set-Content -Encoding UTF8 all_code.txt