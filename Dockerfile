<<<<<<< HEAD
FROM nginx:1.10.1-alpine
COPY index.html /usr/share/nginx/html
EXPOSE 8080
=======
FROM nginx:1.10.1-alpine
COPY index.html /usr/share/nginx/html
EXPOSE 8080
>>>>>>> b853185 (first commit)
CMD ["nginx", "-g", "daemon off;"]