pipeline {
    agent any
    environment {
        APP_NAME       = "studentmarkservice"
        APP_NAMESPACE  = "${APP_NAME}-ns"
        IMAGE_NAME     = "${APP_NAME}-image"
        IMAGE_TAG      = "${BUILD_NUMBER}" // Cleaned tag
        APP_PORT       = "8100"
        NODE_PORT      = "30081"
        REPLICA_COUNT  = "2"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/ruthvika1536/StudentsMarksDemo.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Removed '--network host' as it's typically unnecessary for a standard build
                bat """
                docker build -t ${IMAGE_NAME}:${IMAGE_TAG} -f Dockerfile .
                """
            }
        }

        stage('Generate Kubernetes YAMLs') {
            steps {
                script {
                    // This stage generates the final deployment files from the templates
                    def ns = readFile("k8s/namespace-template.yaml")
                    ns = ns.replace("\${APP_NAMESPACE}", APP_NAMESPACE)
                           .replace("\${APP_NAME}", APP_NAME)
                    writeFile file: "k8s/namespace.yaml", text: ns

                    def dep = readFile("k8s/deployment-template.yaml")
                    dep = dep.replace("\${APP_NAME}", APP_NAME)
                             .replace("\${APP_NAMESPACE}", APP_NAMESPACE)
                             .replace("\${IMAGE_NAME}", IMAGE_NAME)
                             .replace("\${IMAGE_TAG}", IMAGE_TAG)
                             .replace("\${APP_PORT}", APP_PORT)
                             .replace("\${REPLICA_COUNT}", REPLICA_COUNT)
                    writeFile file: "k8s/deployment.yaml", text: dep

                    def svc = readFile("k8s/service-template.yaml")
                    svc = svc.replace("\${APP_NAME}", APP_NAME)
                             .replace("\${APP_NAMESPACE}", APP_NAMESPACE)
                             .replace("\${APP_PORT}", APP_PORT)
                             .replace("\${NODE_PORT}", NODE_PORT)
                    writeFile file: "k8s/service.yaml", text: svc
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Uses the secure 'k8s-kubeconfig' ID and sets the KUBECONFIG variable
                    withCredentials([file(credentialsId: 'k8s-kubeconfig', variable: 'KUBECONFIG')]) {
                        bat "kubectl apply -f k8s/namespace.yaml"
                        bat "kubectl apply -f k8s/deployment.yaml"
                        bat "kubectl apply -f k8s/service.yaml"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Successfully built, generated YAML and deployed to Kubernetes!"
        }
        failure {
            echo "❌ Build failed. Check logs!"
        }
    }
}
