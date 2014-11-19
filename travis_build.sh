#!/bin/bash

function moan(){
  echo -e "$1" 1>&2
  exit 1
}

cd "$( dirname "$0" )"

version=${BUILD_NUMNER:-'dev.build'}
sbt -Dversion=${version} clean update test publish

cd target

function prepare_for_publish() {
    mkdir s3
    cp com/raymanoz/typesafeconfig-reloaded_2.11/${version}/*${version}.jar s3
    cp com/raymanoz/typesafeconfig-reloaded_2.11/${version}/*${version}-sources.jar s3
    cp com/raymanoz/typesafeconfig-reloaded_2.11/${version}/*${version}.pom s3
}

if [[ "${TRAVIS_BRANCH}" == 'master' && "${TRAVIS_PULL_REQUEST}" == 'false' ]]; then
    prepare_for_publish || moan 'Failed to prepare_for_publish'
fi