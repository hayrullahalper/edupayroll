.PHONY: fetch-secrets
fetch-secrets:
	@chmod +x ./scripts/fetch_secrets.sh && ./scripts/fetch_secrets.sh

.PHONY: run/api/development
run/api/development:
	cd packages/api && mvn spring-boot:run -Dspring-boot.run.profiles=dev

.PHONY: run/api/production
run/api/production:
	cd packages/api && mvn spring-boot:run -Dspring-boot.run.profiles=prod

.PHONY: run/web/development
run/web/development:
	cd packages/web && yarn dev

.PHONY: run/web/production
run/web/production:
	cd packages/web && yarn build && yarn preview

.PHONY: generate/web
generate/web:
	cd packages/web && yarn generate:api