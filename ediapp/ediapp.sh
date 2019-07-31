cd /opt/ediapp/

java -jar -server \
-Xms256m \
-Xmx256m \
-Dfile.encoding=UTF-8 \
/opt/ediapp/ediapp.jar \
> /dev/null 2>&1 \
& echo $!

