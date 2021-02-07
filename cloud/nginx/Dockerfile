FROM nginx:stable-alpine
EXPOSE 80/tcp
EXPOSE 443/tcp
COPY default.conf /etc/nginx/conf.d
WORKDIR /usr/share/nginx/html
CMD ["/bin/sh", "-c", "exec nginx -g 'daemon off;';"]
