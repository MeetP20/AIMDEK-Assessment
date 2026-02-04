# CI/CD Setup

- In jenkins groovy script I have mentioned line node("node") , which shows that I have executed it on agent which was labeled as "node"

- There are 5 stages :
    Checkout code : clone repo
    Create docker image : run docker command to build docker image with tag as jenkins build-number
    Push to ECR : It has command to login to public ECR and push image to that ECR
    Deploy : Pulls that image and deploys using docker run command
    Cleanup : Delete unused images

- Deployment Strategy : As i have deployed on EC2 and docker we can get downtime when old container is removed and new container starts .

- For rollback approach , I have written code to revert to older image bu pull that image and deploying it if jenkins job fails for some reason

