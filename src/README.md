# API lab pack (Postman + Mock + CI/CD)

## 1) Run API locally
- `mvn spring-boot:run`
- Check: http://localhost:8080/api/ping

## 2) Postman
- Import `postman/collection.json`
- Import env `postman/env.local.json`
- Run collection in Runner (screenshots for report)

## 3) Mock in Postman (manual step)
- Open GET request -> Save Example -> Create Mock Server (Use examples)
- Replace baseUrl in env to https://xxxx.mock.pstmn.io

## 4) CI
- Commit `.github/workflows/api-tests.yml`
- In GitHub -> Actions -> open run -> screenshot Success + logs
