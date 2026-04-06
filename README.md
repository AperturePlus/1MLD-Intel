# IMLD-Intelligence

IMLD-Intelligence 是一个面向医疗场景的 `Spring Boot` 模块化单体系统，支持同一代码基线下的三种运行形态：

- `SaaS`
- `private`（院内部署）
- `private + private-bridge`（院内部署并开启受控云通信）

## 环境准备

- JDK `21`
- PostgreSQL（默认 `localhost:5432/imld_core`）
- Redis（默认 `localhost:6379`）
- Windows 下命令示例均使用 `.\gradlew.bat`（Linux/macOS 可替换为 `./gradlew`）

## 邮箱验证码（SMTP）配置

身份模块的注册、忘记密码/重置密码使用邮箱验证码，后端通过第三方 SMTP 服务发送邮件。

最少需要配置以下环境变量：

- `IMLD_SMTP_HOST`：SMTP 地址
- `IMLD_SMTP_PORT`：SMTP 端口（例如 `587`）
- `IMLD_SMTP_USERNAME`：SMTP 账号/发件箱
- `IMLD_SMTP_PASSWORD`：SMTP 密码或授权码

可选配置：

- `IMLD_SMTP_FROM`：发件人地址（不配置时默认使用 `IMLD_SMTP_USERNAME`）
- `IMLD_SMTP_STARTTLS_ENABLED`：是否启用 STARTTLS（默认 `true`）
- `IMLD_SMTP_STARTTLS_REQUIRED`：是否强制 STARTTLS（默认 `true`）

## PostgreSQL（Docker 初始化 Schema）

项目已改为由 PostgreSQL 容器在首次启动时执行 Schema 初始化，脚本目录为 `docker/postgresql-16/init`。

启动数据库：

```powershell
cd .\docker\postgresql-16
docker compose up -d
```

说明：

- 初始化脚本通过 `docker/postgresql-16/docker-compose.yml` 挂载到 `/docker-entrypoint-initdb.d`。
- 仅在数据目录为空时执行初始化；如需重新初始化，请先停止容器并清理 `docker/postgresql-16/data`。
- 开发环境已关闭 Spring 启动阶段的 SQL 自动初始化，避免与容器初始化重复执行。

## 启动命令（开发联调）

### 1) SaaS

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=dev-saas"
```

### 2) Private（严格验签+激活）

先配置许可证相关环境变量：

```powershell
$env:IMLD_LICENSE_FILE_PATH="D:\imld\license\license.json"
$env:IMLD_LICENSE_PUBLIC_KEY_FILE_PATH="D:\imld\license\public.pem"
$env:IMLD_ACTIVATION_STATE_FILE_PATH="D:\imld\license\activation-state.json"
```

启动：

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=dev-private"
```

如果只是本地功能联调、暂不做激活验签，可临时关闭启动校验：

```powershell
$env:IMLD_PRIVATE_STARTUP_VALIDATION_ENABLED="false"
.\gradlew.bat bootRun --args="--spring.profiles.active=dev-private"
```

### 3) Private + Bridge（受控云通信）

```powershell
$env:IMLD_LICENSE_FILE_PATH="D:\imld\license\license.json"
$env:IMLD_LICENSE_PUBLIC_KEY_FILE_PATH="D:\imld\license\public.pem"
$env:IMLD_ACTIVATION_STATE_FILE_PATH="D:\imld\license\activation-state.json"
.\gradlew.bat bootRun --args="--spring.profiles.active=dev-private-bridge"
```

## 启动命令（部署环境）

- `saas`：

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=saas"
```

- `private`：

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=private"
```

- `private + private-bridge`：

```powershell
.\gradlew.bat bootRun --args="--spring.profiles.active=private,private-bridge"
```

## Private 激活 CLI

导入并激活许可证（离线）：

```powershell
.\gradlew.bat bootRun --args="--spring.main.web-application-type=none --spring.profiles.active=private --license-cli-command=import-license --license-cli-license-file=D:\imld\license\license.json --license-cli-public-key-file=D:\imld\license\public.pem --license-cli-activation-code=ACT-2026-LOCAL --license-cli-target-license-file=D:\imld\runtime\license\license.json --license-cli-expected-mode=private --license-cli-machine-binding=true"
```

更多参数见：[docs/license-cli.md](docs/license-cli.md)

## IMLD Java 推理接口

后端已集成 Java 原生 IMLD 推理，不需要额外拉起 Python 服务。默认接口：

- `GET /api/imld/health`
- `POST /api/imld/predict`
- `POST /api/imld/batch_predict`

默认模型与元数据路径：

- `classpath:models/imld/imld_xgboost_model.deprecated.bin`
- `classpath:models/imld/imld_model_meta.json`

可通过环境变量覆盖：

- `IMLD_INFERENCE_IMLD_MODEL_FILE_PATH`
- `IMLD_INFERENCE_IMLD_METADATA_FILE_PATH`
