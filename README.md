# Backend_Vaku

## Docker (backend + postgres)

1. Crea tu archivo de entorno:
```bash
cp .env.example .env
```

2. Levanta los servicios:
```bash
docker compose up --build -d
```

3. Verifica logs del backend:
```bash
docker compose logs -f backend
```

API disponible en `http://localhost:8080` (o el puerto configurado en `BACKEND_PORT`).
