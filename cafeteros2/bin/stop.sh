#!/bin/bash

folder=$(dirname $(readlink -f  "$0"))

/usr/bin/java -cp $folder/../lib/web.jar com.laetienda.web.Run -stop &>> $folder/../var/log/catalina.out 
