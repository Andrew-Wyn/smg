FILE=/home/terraformer/.m2/repository/com/bmeme/lib/bmeme-lib-libannotation-spring-boot-starter/1.0.0-SNAPSHOT/bmeme-lib-libannotation-spring-boot-starter-1.0.0-SNAPSHOT.jar
if [ -f "$FILE" ];
    then
	    echo "\nALREADY INSTALLED\n"
    else
	    mvn install:install-file -Dfile=/home/terraformer/Scrivania/__Cartelle/Work/_work_project/CERERE/libannotation/build/libs/bmeme-lib-libannotation-spring-boot-starter-1.0.0-SNAPSHOT.jar -DgroupId=com.bmeme.lib -DartifactId=bmeme-lib-libannotation-spring-boot-starter -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar
        echo "\nINSTALLED LIBRARY\n"

    fi
