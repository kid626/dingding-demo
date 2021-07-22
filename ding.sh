#!/usr/bin/env bash

echo "FORWARDING http://bruce.vaiwan.com/dd to http:127.0.0.1:9001/dd"
echo "FORWARDING http://bruce.vaiwan.com/zzd to http:127.0.0.1:9001/zzd"
./pierced/windows_64/ding.exe -config=./pierced/windows_64/ding.cfg -subdomain=bruce 9001