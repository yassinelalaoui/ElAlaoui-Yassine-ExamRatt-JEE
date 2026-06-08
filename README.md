# Gestion de Crédit Bancaire

## Structure du projet
- `backend/`: Spring Boot application
- `frontend/`: Angular application

## Backend
1. Ouvrir le dossier `backend`.
2. Lancer la commande:
   ```bash
   mvn clean spring-boot:run
   ```
3. Accéder à l'API Swagger:
   - `http://localhost:8080/swagger-ui/index.html`
4. Console H2:
   - `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:creditdb`

### Utilisateurs de test
- Admin: `admin` / `admin123`
- Employé: `employe` / `employe123`
- Client: `client1@example.com` / `client123`

## Frontend
1. Ouvrir le dossier `frontend`.
2. Installer les dépendances:
   ```bash
   npm install
   ```
3. Lancer l'application:
   ```bash
   npm start
   ```
4. Accéder à l'application Angular:
   - `http://localhost:4200`

## Commit & Push
> Pour travailler de manière sûre, faites un commit toutes les 20 minutes.

1. Initialiser le dépôt git si nécessaire:
   ```bash
   git init
   git add .
   git commit -m "Initial project scaffold for Gestion de Crédit Bancaire"
   ```
2. Ensuite, toutes les 20 minutes:
   ```bash
   git add .
   git commit -m "WIP: update features / fix issues"
   ```
3. Si vous avez un remote:
   ```bash
   git push origin main
   ```
