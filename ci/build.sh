#!/usr/bin/env bash

set -e

# build environment
CTENV=${CTENV:-""}
# build project
BUILD_PROJECT=${BUILD_PROJECT:-""}

help(){
    echo "Example:"
    echo -e "\t./build.sh -e \$BUILD_ENV -p \$BUILD_PROJECT"
    echo -e "\t -e: build environment, such as: 'dev' or 'www' or 'www'"
    echo -e "\t -p: build project name, such as: 'demo-project'"
}

while getopts "e:p:" OPT; do
    case $OPT in
        e)
            CTENV=$OPTARG;;
        p)
            BUILD_PROJECT=$OPTARG;;
    esac
done

if [ "$CTENV" == "" ]; then
    echo "Error: CTENV is blank!"
    help
    exit 1
fi

if [ "$BUILD_PROJECT" == "" ]; then
    echo "Error: BUILD_PROJECT is . or blank!"
    help
    exit 1
fi


if [ "$CTENV" == "www" ]; then
    cmvn -T 2 clean install -P ${CTENV} -am -pl ${BUILD_PROJECT}/pom.xml -Dmaven.test.skip=true
#    (cd vue-admin-template && cnpm install && cnpm run build:prod)
else
    mvn -T 2 clean install -P ${CTENV} -am -pl ${BUILD_PROJECT}/pom.xml -Dmaven.test.skip=true
#    (cd vue-admin-template && npm install && npm run build:prod)
fi
