# PB-Backend Noti-Service
This is the notification server for the voice phishing prevention application **Phishing Block**.

## Installation
Before running, add the corresponding yml file for firebase configuration in `src/noti-service/src/main/resources/firebase/`
```yml
{
  "type": "{type}",
  "project_id": "{project_id}",
  "private_key_id": "{private_key_id}",
  "private_key": "{private_key}",
  "client_email": "{client_email}",
  "client_id": "{client_id}",
  "auth_uri": "{auth_uri}",
  "token_uri": "{token_uri}",
  "auth_provider_x509_cert_url": "{auth_provider_x509_cert_url}",
  "client_x509_cert_url": "{client_x509_cert_url}",
  "universe_domain": "{universe_domain}"
}

```

move `scripts/` and start `scripts/env-start.sh`

```bash
#!/bin/sh

docker-compose down
docker-compose up -d
```

After then, start `src/auth-service/src/main/java/com/phishing/notiservice/NotiServiceApplication`