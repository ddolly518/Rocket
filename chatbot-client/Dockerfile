# build
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
RUN npm run build

# production
FROM node:20-alpine
WORKDIR /app
COPY --from=build /app/dist ./dist
EXPOSE 4173
CMD ["npx", "vite", "preview", "--port", "4173"]