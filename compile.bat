set SRC_DIR=./src
set SOURCES_DIR=./src/com/royshruti
set OUTPUT_DIR=./WEB-INF/classes
set CLASSES=../../lib/servlet-api.jar;../../lib/websocket-api.jar;../../lib/mysql-connector-java-5.1.49.jar;.

for /D %%a in (%SOURCES_DIR%\*) do (
    javac -sourcepath %SRC_DIR% -d %OUTPUT_DIR% -cp %CLASSES% %%a/*.java
)