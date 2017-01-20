#!/bin/zsh

RESOURCES_DIR=src/test/resources
TEST_DIR=/tmp/lzw
LOG=$TEST_DIR/log
size(){
echo $(stat --printf="%s" $1)
}
for file in $(ls $RESOURCES_DIR); do
bin/lzw-test $RESOURCES_DIR/$file;
compressed=$(size $TEST_DIR/compressed.dat)
decompressed=$(size $TEST_DIR/decompressed.dat)
codes=$(expr $compressed / 4)
ratio=$(echo "5 k $compressed $decompressed / p" | dc)
echo "$file\t$decompressed\t$compressed\t$ratio" >> $LOG
done
