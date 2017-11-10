#!/bin/bash


HostCount="1"
TestCount="3"
CdnAddress="http://ube.ege.edu.tr/~cetinkaya/ubi614/bunny_ibmff_1080.mpd"
TestName="BeinMedia"
HOME=`pwd`
echo $HOME

startTest(){
    echo "------------------------ startTest------------------------"
	echo "Sistemdeki host $HostCount cdn address = $CdnAddress"
    cd $HOME
    cd ./DigiClient/src/
    javac Streaming/*.java
    java Streaming.Startup $HostCount $CdnAddress 
    echo "------------------------end of startTest------------------------"
}

waitForTest(){
    echo "------------------------ waitTest------------------------"
    sleep 10
    echo "------------------------end of Test------------------------"
}

copyResult(){
    echo "------------------------ copyTest------------------------"
    testNo=$1
    cd $HOME
    mkdir -p $TestName/TEST$testNo
    for i in $(seq 1 1 $HostCount);
    do
      mkdir -p $TestName/TEST$testNo/$i
    done

    for clientNo in $(seq 1 1 $HostCount);
    do
      mv ./DigiClient/src/Clients/$clientNo/chunk/*.txt  ./$TestName/TEST$testNo/$clientNo/
    done
}

finishTest(){
    echo "------------------------ finishTest------------------------"
    kill -9 $(pidof xterm)
    echo "------------------------end of Test------------------------"
}


for i in $(seq 1 1 $TestCount);
do
    startTest 
    waitForTest
    finishTest
    copyResult $i
done
