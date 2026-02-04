output "jenkins_url" {
  value = "http://${aws_instance.master.public_ip}:8080"
}
