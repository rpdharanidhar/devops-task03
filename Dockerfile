FROM node:14
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
EXPOSE 3000
COPY . .

CMD ["node", "app.js"]
