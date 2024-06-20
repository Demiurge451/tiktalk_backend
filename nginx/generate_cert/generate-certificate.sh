#!/bin/bash

rm -rf /etc/letsencrypt/live/certfolder*

certbot certonly --standalone --email $DOMAIN_EMAIL -d $DOMAIN_URL --cert-name=certfolder --key-type rsa --agree-tos

rm -rf /etc/nginx/cert2.pem
rm -rf /etc/nginx/key2.pem

cp /etc/letsencrypt/live/certfolder*/fullchain.pem /etc/nginx/cert2.pem
cp /etc/letsencrypt/live/certfolder*/privkey.pem /etc/nginx/key2.pem