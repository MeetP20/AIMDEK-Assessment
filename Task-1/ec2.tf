data "aws_ami" "amazon" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["amzn2-ami-hvm-*"]
  }
}

resource "aws_instance" "master" {
  ami           = data.aws_ami.amazon.id
  instance_type = "t3.micro"
  subnet_id     = aws_subnet.public_1.id
  key_name      = var.key_name

  vpc_security_group_ids = [aws_security_group.jenkins_master.id]

  tags = { Name = "jenkins" }
}

resource "aws_instance" "agent" {
  ami           = data.aws_ami.amazon.id
  instance_type = "t3.micro"
  subnet_id     = aws_subnet.public_1
  key_name      = var.key_name

  vpc_security_group_ids = [aws_security_group.jenkins_agent.id]

  tags = { Name = "node-project" }
}
