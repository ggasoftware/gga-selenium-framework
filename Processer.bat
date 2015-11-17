@Echo Files processed: >ProcessedFiles.log

@for /R %%F in ("*.java") do @(
@echo Updating %%F
@echo %%F >>ProcessedFiles.log
@move %%F tmp.tmp >nul
@copy copyright.txt + tmp.tmp %%F >nul
)
@Echo.
@Echo Done.