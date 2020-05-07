#!/usr/bin/env bash

set -e

BUILD_PROJECT=${BUILD_PROJECT:-"."}

while getopts "e:p:" OPT; do
    case $OPT in
        e)
            SPRING_ENV=$OPTARG;;
        p)
            BUILD_PROJECT=$OPTARG;;
    esac
done


# docker login ${REGISTRY_ADDRESS} -u ${REGISTRY_USER} -p ${REGISTRY_PASSWORD}
docker build -t ${IMAGE_NAME} --build-arg SPRING_PROFILES_ACTIVE=${SPRING_ENV} ${BUILD_PROJECT}
# docker tag ${IMAGE_NAME} ${LATEST_IMAGE_NAME}
# docker push ${IMAGE_NAME}
# docker push ${LATEST_IMAGE_NAME}
# docker rmi ${IMAGE_NAME}
# docker rmi ${LATEST_IMAGE_NAME}

echo "Build image success --> ${IMAGE_NAME}"
