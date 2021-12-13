FROM node:14.17.3-buster 
ENV PATH = "./node_modules/.bin:$PATH"
WORKDIR /app

COPY package.json package.json
COPY package-lock.json package-lock.json

RUN npm install

COPY . .
EXPOSE 3000

CMD ["npm", "run", "start"]