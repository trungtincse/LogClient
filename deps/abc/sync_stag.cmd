#!/bin/sh

SERVER="zdeploy@10.30.80.220"

ENV="development" #dev/live/stag

PRJ_NAME="tindpt-LogManager"

ENTRY_PATH=`readlink -f $0`
CURR_PATH=`dirname $ENTRY_PATH`
PRJ_DIR=$CURR_PATH/../../

#sync
rsync --delete -aurv "$PRJ_DIR"dist "$PRJ_DIR"conf "$PRJ_DIR"cmd "$PRJ_DIR"stc $CURR_PATH/runservice $CURR_PATH/cmd $SERVER:/zserver/java-projects/$PRJ_NAME/ --exclude='.svn'
#override cmd per instance
rsync -aurv $CURR_PATH/cmd $SERVER:/zserver/java-projects/$PRJ_NAME/ --exclude='.svn'
