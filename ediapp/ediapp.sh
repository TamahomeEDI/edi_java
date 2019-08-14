cd /opt/ediapp/

java -jar -server \
-Xms256m \
-Xmx256m \
-Dfile.encoding=UTF-8 \
-Djdk.tls.client.protocols=TLSv1.2 \
-Dhttps.protocols=TLSv1.2 \
/opt/ediapp/ediapp.jar \
> /dev/null 2>&1 \
& echo $!

