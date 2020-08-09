# Preparation for CI/CD

## userdata for EC2 creation
```
#!/bin/bash
sudo yum update -y
sudo yum install -y ruby wget
wget https://aws-codedeploy-eu-west-1.s3.eu-west-1.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto
sudo service codedeploy-agent status
sudo yum install -y java-11-amazon-corretto
```

