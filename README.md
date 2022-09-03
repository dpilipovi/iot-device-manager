# IoT Device Manager

## Running the application
There are three options when it comes to running the application.

### docker-compose
If Docker is installed and docker-compose is available, you can start the application in development mode
(active `docker-dev` profile).

Prerequisites:
- Docker
- docker-compose

1. Run `mvn clean package`
2. Run `docker-compose up --build -d` inside the `docker` folder located in the root of the project. Remove the `-d` flag
   if you don't want to detach it.

### Kubernetes
If you have Kubernetes and Helm locally installed, you can use the helm charts inside /kubernetes/helm to run the application inside your k8s cluster
(active `k8s` profile)

Prerequisites:
- Kubernetes
- Helm

1. Create namespace iot-device-manager with command `kubectl create namespace iot-device-manager`
2. Run `helm upgrade --install <chart_name>` for all charts
3. Install IngressController with command give inside /kubernetes/ingress/ingress-controller.txt'
