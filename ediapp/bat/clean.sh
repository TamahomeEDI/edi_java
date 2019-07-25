#!/bin/sh

echo "0" > /proc/sys/vm/drop_caches
echo "3" > /proc/sys/vm/drop_caches
swapoff -a && swapon -a

exit 0
