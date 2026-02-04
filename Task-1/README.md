# Task-1

## Architecture

Internet
   |
IGW
   |
Public Subnet -> Jenkins and App (I have used same ec2 for deployment of app and jenkins agent)
   |
NAT
   |
Private Subnet 

## Resource Description

- Created VPC with 2 public subnets , 2 private subnets , Internet Gateway , NAT Gateway , Routetable and Routeable association , 2 EC2 and security groups 



### 1️⃣ Initialize Terraform
```bash
terraform init
```

### 2️⃣ Validate
```bash
terraform validate
```

### 3️⃣ Plan
```bash
terraform plan
```

### 4️⃣ Deploy Infrastructure
```bash
terraform apply
```

### 5️⃣ Destroy Infrastructure
```bash
terraform destroy
```
