data "aws_ami" "ubuntu" {

    most_recent = true

    filter {
        name   = "name"
        values = ["ubuntu/images/hvm-ssd/ubuntu-noble-24.04-amd64-*"]
    }

    filter {
        name = "virtualization-type"
        values = ["hvm"]
    }

    owners = ["099720109477"]
}

resource "aws_instance" "jenkins" {
  ami = data.aws_ami.ubuntu.id

  instance_type = "t3.micro"
  subnet_id     = aws_subnet.public_1.id
  key_name      = var.key_name

  vpc_security_group_ids = [aws_security_group.jenkins_master.id]

  tags = { Name = "jenkins" }
}

resource "aws_instance" "node-project" {
  ami = data.aws_ami.ubuntu.id
  instance_type = "t3.micro"
  subnet_id     = aws_subnet.public_1.id
  key_name      = var.key_name

  vpc_security_group_ids = [aws_security_group.jenkins_agent.id]

  tags = { Name = "node-project" }
}
