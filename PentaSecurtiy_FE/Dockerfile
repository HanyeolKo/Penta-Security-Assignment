# 빌드
FROM node:18 AS builder

WORKDIR /app

COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Express 서버로 정적 파일 서빙
FROM node:18

WORKDIR /app

# 의존성 설치를 위한 package.json 복사
COPY package*.json ./
RUN npm install --only=production

# 정적 리소스와 서버 파일 복사
COPY --from=builder /app/build ./build
COPY server.js ./

EXPOSE 3000
CMD ["node", "server.js"]
