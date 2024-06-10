FROM node:18-alpine AS base

ENV NODE_ENV=production

WORKDIR /app/web

FROM base AS build

COPY package*.json yarn.lock ./
RUN yarn install

COPY . .
RUN yarn build

FROM base

COPY --from=build /app/web/.output /app/web/.output

CMD ["node", ".output/server/index.mjs"]