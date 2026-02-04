variable "region" {
  default = "ap-south-1"
}

variable "key_name" {
  description = "Your EC2 key pair name"
}

variable "my_ip" {
  description = "Your laptop IP for SSH (example: 1.2.3.4/32)"
}
