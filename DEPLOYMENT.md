# Deploying to Render (free tier)

This project ships with a `Dockerfile` so Render can build it without you installing Maven on the server.

## Steps

1. Push the project to a **public** GitHub repository.

2. Go to [render.com](https://render.com) → **New +** → **Web Service**.

3. Connect your GitHub repo.

4. Settings:
   - **Runtime**: Docker
   - **Health Check Path**: `/api/health`
   - **Instance type**: Free (if available)

5. Deploy. First build can take 5–10 minutes.

6. When status is **Live**, copy the service URL and append `/api`:
   ```
   https://your-service-name.onrender.com/api
   ```

7. Run the official tests:
   ```bash
   bash financial_manager_tests.sh https://your-service-name.onrender.com/api
   ```

## Cold starts

Free tier sleeps after inactivity. The first request after sleep may take 30–60 seconds — wait, then re-run the test script if a few calls fail with timeout.

## Troubleshooting

| Problem | Fix |
|---------|-----|
| Health check failing | Confirm path is `/api/health`, not `/health` alone |
| 502 on first request | Wait for container to finish starting |
| Tests fail only on Render | Redeploy; run script again after service is warm |

## Why Docker here?

You do **not** need Docker Desktop on your laptop for development. Render reads the `Dockerfile` in the cloud: it runs `mvn package` inside a Java image and starts the JAR. That’s the hosting requirement, not an extra local install.
