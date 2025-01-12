# Spring Boot Application: Books Service

This document provides steps to build and run the Spring Boot application locally using Docker, deploy it on Minikube, and details the CI/CD pipeline in GitHub Actions.

---

## Prerequisites

- **Docker**: Ensure Docker is installed and running.
- **Minikube**: Install Minikube and start a cluster.
- **kubectl**: Install the Kubernetes CLI.
- **GitHub Actions**: Repository configured with secrets for DockerHub login.

---

## Local Setup Using Docker

### Steps to Build and Run Locally

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Build the Docker Image**:
   ```bash
   docker build -t spring-boot-books-app:latest .
   ```

3. **Run the Application**:
   ```bash
   docker run -p 8080:8080 spring-boot-books-app:latest
   ```
 ![Application Startup](https://github.com/gerdavid/InfraOps/blob/master/screenshots/1.PNG)
4. **Access the Application**:
    - Open your browser and navigate to: [http://localhost:8080/books](http://localhost:8080/books)

---
![Application Startup](https://github.com/gerdavid/InfraOps/blob/master/screenshots/2.PNG)
## Deploying on Minikube

### Required Minikube Setup Commands

1. **Start Minikube**:
   ```bash
   minikube start
   ```

2. **Enable Minikube Docker Environment** (optional if building locally):
   ```bash
   eval $(minikube docker-env)
   ```

3. **Build Docker Image in Minikube** (skip if using DockerHub):
   ```bash
   docker build -t spring-boot-books-app:latest .
   ```

4. **Verify Minikube is Running**:
   ```bash
   minikube status
   ```

### Deploy the Application

1. **Apply the PostgreSQL Manifest**:
   ```bash
   kubectl apply -f postgres.yaml
   ```

2. **Apply the Deployment and Service Manifests**:
   ```bash
   kubectl apply -f deployment.yaml
   kubectl apply -f service.yaml
   ```

3. **Check Pod and Service Status**:
   ```bash
   kubectl get pods
   kubectl get services
   ```
   ![Pods and service status](https://github.com/gerdavid/InfraOps/blob/master/screenshots/3.PNG)

4. **Access the Application**:
    - Get the URL using Minikube:
      ```bash
      minikube service spring-boot-service
      ```
    - This opens the application in your browser or provides a local URL.

---
![Application Startup](https://github.com/gerdavid/InfraOps/blob/master/screenshots/4.PNG)
![Application Startup](https://github.com/gerdavid/InfraOps/blob/master/screenshots/5.PNG)
## CI/CD Pipeline in GitHub Actions

### Workflow Overview

- **Trigger**:
    - Pushes to the `master` branch.
    - Pull requests targeting the `master` branch.

- **Pipeline Steps**:
    1. **Checkout Code**: Pulls the repository into the runner.
    2. **DockerHub Login**: Authenticates with DockerHub using repository secrets (`DOCKER_USERNAME`, `DOCKER_PASSWORD`).
    3. **Build Docker Image**: Builds the Docker image.
    4. **Push Image to DockerHub**: Publishes the image to DockerHub.

### Secrets Required in GitHub Actions

- `DOCKER_USERNAME`: Your DockerHub username.
- `DOCKER_PASSWORD`: Your DockerHub password or personal access token.

### File: `.github/workflows/docker-build.yml`
Refer to the provided GitHub Actions workflow file in the repository.

---
![Application Startup](https://github.com/gerdavid/InfraOps/blob/master/screenshots/6.PNG)
![Application Startup](https://github.com/gerdavid/InfraOps/blob/master/screenshots/7.PNG)
## Assumptions, Decisions, and Challenges

### Assumptions
- The application automatically preloads books data to the database.
- The application requires PostgreSQL, and its deployment is managed via Kubernetes manifests.
- DockerHub is used as the container registry.

### Key Decisions
- **PostgreSQL Deployment**: A Kubernetes manifest (`postgres.yaml`) was created to handle database deployment and service management.
- **NodePort Service**: Chosen for simplicity in local development with Minikube.

### Challenges
- **Networking Between Pods**: Ensured the application could discover and connect to the PostgreSQL Pod using a Kubernetes Service.
- **Persistence**: Added a persistent volume for PostgreSQL data to avoid data loss during Pod restarts.
- **Image Availability**: Provided instructions for both DockerHub and Minikube-local image building.

---

## Troubleshooting

1. **Application Not Accessible**:
    - Check the Pod and Service status:
      ```bash
      kubectl get pods
      kubectl get services
      ```
    - Verify Minikube service forwarding:
      ```bash
      minikube service spring-boot-service
      ```

2. **Database Connection Errors**:
    - Ensure PostgreSQL Pod is running:
      ```bash
      kubectl logs <postgresql-pod-name>
      ```

3. **DockerHub Authentication Errors in CI/CD**:
    - Verify the GitHub Secrets (`DOCKER_USERNAME`, `DOCKER_PASSWORD`) are correctly set.

---

