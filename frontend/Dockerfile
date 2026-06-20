# ── Stage 1: build ──────────────────────────────────────────────────────────
FROM node:22-alpine AS build
WORKDIR /app

# Install dependencies before copying source so this layer is cached
COPY package*.json ./
RUN npm config set fetch-timeout 600000 \
    && npm config set fetch-retry-maxtimeout 600000 \
    && npm config set fetch-retries 5 \
    && npm ci --ignore-scripts

COPY . .

# VITE_API_BASE_URL is baked into the bundle at build time.
# With nginx proxying /api → backend:8080, a relative path is correct here.
ARG VITE_API_BASE_URL=https://priyanshu.dev/api
ENV VITE_API_BASE_URL=${VITE_API_BASE_URL}

RUN npm run build

# ── Stage 2: serve ───────────────────────────────────────────────────────────
FROM nginx:1.27-alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
