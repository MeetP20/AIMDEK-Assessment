node('node') {

    // ========== VARIABLES ==========
    def AWS_REGION = "us-east-1"
    def IMAGE_TAG = "${env.BUILD_NUMBER}"
    def CONTAINER_NAME = "myapp"

    try {

        dir("App"){
            stage('Checkout Code') {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/MeetP20/Shopping-Cart-Application.git'
                    ]]
                ])
            }
        }


        dir("App"){
            stage('Build Docker Image') {
                sh """
                    docker build -t public.ecr.aws/x9u1q0x1/myapp:${IMAGE_TAG} .
                """
            }
        }

        stage('Push IMage to ECR'){
            sh"""
            aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/x9u1q0x1
                    docker push public.ecr.aws/x9u1q0x1/myapp:${IMAGE_TAG}
                    docker rmi public.ecr.aws/x9u1q0x1/myapp:${IMAGE_TAG}
            """
        }

        stage('Deploy') {
            sh """
                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true

                docker run -d \
                    --name ${CONTAINER_NAME} \
                    -p 3000:3000 \
                    --restart always \
                    public.ecr.aws/x9u1q0x1/myapp:${IMAGE_TAG}
            """
        }

        stage('Cleanup Old Images') {
            sh "docker image prune -f"
        }


        currentBuild.result = 'SUCCESS'
        echo "Deployment Successful 🚀"

    } catch (Exception e) {

        currentBuild.result = 'FAILURE'
        echo "Pipeline Failed ❌"

        // =============================
        // Rollback Stage
        stage('Rollback') {
            echo "Rolling back to previous version..."

            sh """
                PREV_TAG=\$((${IMAGE_TAG} - 1))
                docker stop ${CONTAINER_NAME} || true
                docker rm ${CONTAINER_NAME} || true

                docker run -d \
                    --name ${CONTAINER_NAME} \
                    -p 3000:3000 \
                    public.ecr.aws/x9u1q0x1/myapp:\$PREV_TAG
            """
        }

        throw e
    }
}
