# build
FROM node:20-alpine AS build
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

# production
FROM node:20-alpine
WORKDIR /app
COPY --from=build /app/dist ./dist
EXPOSE 4173
CMD ["sh", "-c", "npx vite preview --port $PORT --host"]