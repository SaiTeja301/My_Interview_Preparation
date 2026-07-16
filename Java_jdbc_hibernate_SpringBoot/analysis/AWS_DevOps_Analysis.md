# AWS & DEVOPS — COMPLETE INTERVIEW PREPARATION GUIDE
> *Target: 2–5 Years Experienced Java/Backend Engineers*

---

## TABLE OF CONTENTS
1. [Cloud Computing Fundamentals](#topic-1-cloud-computing-fundamentals)
2. [AWS Global Infrastructure](#topic-2-aws-global-infrastructure)
3. [EC2 — Elastic Compute Cloud](#topic-3-ec2--elastic-compute-cloud)
4. [EBS — Elastic Block Store](#topic-4-ebs--elastic-block-store)
5. [S3 — Simple Storage Service](#topic-5-s3--simple-storage-service)
6. [IAM — Identity & Access Management](#topic-6-iam--identity--access-management)
7. [VPC — Virtual Private Cloud (Deep Dive)](#topic-7-vpc--virtual-private-cloud)
   - 7.1 — What Is VPC and Why Does It Exist?
   - 7.2 — IP Addressing Fundamentals (IPv4 vs IPv6, IP Types)
   - 7.3 — CIDR Block Allocation & Calculation
   - 7.4 — Subnets — Public vs Private
   - 7.5 — Route Tables — VPC GPS
   - 7.6 — Internet Gateway (IGW)
   - 7.7 — NAT Gateway — Private Subnet Outbound Access
   - 7.8 — Security Groups — Instance-Level Firewall
   - 7.9 — NACL — Subnet-Level Firewall
   - 7.10 — VPC Peering
   - 7.11 — Complete Production VPC Architecture
   - 7.12 — Step-by-Step VPC Practical (Console Walkthrough)
   - 7.13 — AWS CLI Commands for VPC
   - 7.14 — Security Group vs NACL Deep Dive
   - 7.15 — VPC Interview Q&A (9 Questions)
   - 7.16 — Best Practices & Common Mistakes
   - 7.17 — Real-World Use Cases
8. [Load Balancer & Auto Scaling](#topic-8-load-balancer--auto-scaling)
9. [RDS — Relational Database Service](#topic-9-rds--relational-database-service)
10. [AWS Lambda — Serverless Computing](#topic-10-aws-lambda--serverless-computing)
11. [ECS & EKS — Container Orchestration](#topic-11-ecs--eks--container-orchestration)
12. [CloudWatch — Monitoring & Logging (Deep Dive)](#topic-12-cloudwatch--monitoring--logging)
    - 12.1 — What Is CloudWatch and Why Does It Exist?
    - 12.2 — CloudWatch Core Components
    - 12.3 — CloudWatch Metrics
    - 12.4 — CloudWatch Alarms & States
    - 12.5 — CloudWatch Logs
    - 12.6 — CloudWatch Dashboards
    - 12.7 — CloudWatch + SNS Practical (Class Example with Stress Test)
    - 12.8 — Spring Boot CloudWatch Integration
    - 12.9 — Interview Questions & Answers
    - 12.10 — Production Best Practices
    - 12.11 — Real-World Architecture
13. [SNS & SQS — Messaging Services](#topic-13-sns--sqs--messaging-services)
14. [Route 53 — DNS & Domain Management](#topic-14-route-53--dns--domain-management)
15. [CloudFormation & Terraform — Infrastructure as Code](#topic-15-cloudformation--terraform--infrastructure-as-code)
16. [CI/CD Pipeline — Jenkins, GitHub Actions, Harness](#topic-16-cicd-pipeline--jenkins-github-actions-harness)
17. [Docker — Containerization for Java Developers](#topic-17-docker--container-ization-for-java-developers)
18. [Kubernetes — Container Orchestration](#topic-18-kubernetes--container-orchestration)
19. [Security — SonarQube, Twistlock, Contrast Security](#topic-19-security--sonarqube-twistlock-contrast)
20. [Deployment Strategies — Blue-Green, Canary, Rolling](#topic-20-deployment-strategies)
21. [Monitoring & Observability — ELK, Splunk, Prometheus](#topic-21-monitoring--observability)
22. [Cost Optimization Strategies](#topic-22-cost-optimization)
23. [Real-World Production Scenarios & Troubleshooting](#topic-23-real-world-production-scenarios)
24. [Comparison Tables](#topic-24-comparison-tables)
25. [Interview Quick Reference](#topic-25-interview-quick-reference)
26. [EFS — Elastic File System](#topic-26-efs--elastic-file-system)
27. [Elastic Beanstalk — Platform as a Service](#topic-27-elastic-beanstalk--platform-as-a-service)
28. [AWS CLI — Command Line Interface](#topic-28-aws-cli--command-line-interface)
29. [Static Website Hosting on EC2](#topic-29-static-website-hosting-on-ec2)
30. [Troubleshooting Quick Reference](#troubleshooting-quick-reference-1)

---

## TOPIC 1: CLOUD COMPUTING FUNDAMENTALS

### 1. Concept Explanation

#### Beginner
Cloud Computing is the delivery of IT resources (servers, storage, databases, networking, software, analytics) over the Internet on an on-demand basis with a "Pay As You Go" model. Instead of buying and maintaining physical hardware, you rent computing power from a cloud provider (such as AWS, Azure, or GCP) and pay only for what you use.

On-premises infrastructure has major limitations:
* **High Upfront Capital Cost:** Buying servers, racks, and cooling setups before writing code.
* **Slow Scaling:** Buying and installing hardware takes weeks or months.
* **Single Point of Failure Risks:** Power outages, floods, or hardware defects can bring down the entire site.
* **High Operational Burden:** Continuous patching, physical security, and hardware upgrades.

#### Intermediate
Cloud Computing solves these problems through:
* **Elasticity:** Automatically scale resources up or down based on current traffic demand.
* **High Availability:** Built-in redundancies across multiple geographic locations to guarantee uptimes up to 99.99%.
* **Global Reach:** Deploy your application to regions worldwide within minutes.
* **Managed Services:** The cloud provider manages OS upgrades, patching, and hardware failures.

#### Advanced
Cloud computing runs on the **Shared Responsibility Model**:
* **Cloud Provider (AWS) is responsible for:** Security **of** the cloud (physical infrastructure, virtualization layer, hardware hypervisors).
* **Customer (You) is responsible for:** Security **in** the cloud (application code, database schemas, network configurations, encryption, IAM policies, and OS patching for EC2).

##### Cloud Service Models
| Model | What Provider Gives | What You Manage | AWS Examples |
| :--- | :--- | :--- | :--- |
| **IaaS** (Infrastructure as a Service) | Virtualization, Servers, Storage, Networking | Operating System, Middleware, Applications, Data | EC2, EBS, VPC |
| **PaaS** (Platform as a Service) | OS, Runtime, Middleware, Scaling | Application Code, Data, Configuration | Elastic Beanstalk, RDS |
| **SaaS** (Software as a Service) | Fully-functional software application | Nothing (User utilization and client configuration) | Zoom, Dropbox, AWS WorkMail |

##### Cloud Deployment Models
* **Public Cloud:** Resources are shared across multiple tenants (e.g., standard AWS commercial accounts).
* **Private Cloud:** Infrastructure dedicated to a single organization (e.g., AWS GovCloud).
* **Hybrid Cloud:** A mix of on-premises data centers and public cloud infrastructure.
* **Multi-Cloud:** Using multiple public cloud providers simultaneously (e.g., AWS + Azure).

### 2. Architecture Diagram

```mermaid
flowchart TD
    subgraph SaaS ["SaaS (Software as a Service)"]
        S["AWS WorkMail / Zoom / Gmail
        (Consume Only - AWS manages all layers)"]
    end
    subgraph PaaS ["PaaS (Platform as a Service)"]
        P["Elastic Beanstalk / RDS
        (You manage: App Code & Data)"]
    end
    subgraph IaaS ["IaaS (Infrastructure as a Service)"]
        I["EC2 / EBS / VPC
        (You manage: OS, Runtime, App, Data)"]
    end
    subgraph HW ["Physical Layer (Managed by AWS)"]
        H["AWS Global Data Centers & Hypervisors"]
    end

    S -.-> P -.-> I -.-> HW

    classDef saas fill:#581C87,stroke:#D8B4FE,color:#FFFFFF,stroke-width:2px;
    classDef paas fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef iaas fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef hw fill:#374151,stroke:#9CA3AF,color:#FFFFFF,stroke-width:2px;
    class S saas;
    class P paas;
    class I iaas;
    class H hw;
```

### 3. Interview Questions & Answers

#### Q: What is the difference between IaaS, PaaS, and SaaS?
**A:** 
* **IaaS** gives you raw infrastructure (like EC2). You have full root control and must configure the OS, runtimes, and libraries.
* **PaaS** gives you a managed platform (like Elastic Beanstalk). You upload application code, and the platform handles scaling, patching, and infrastructure provisioning.
* **SaaS** is a ready-to-use software product (like Slack or Zoom) that you access directly via web browser or API.

#### Q: Why would a company choose AWS over on-premises?
**A:** 
1. **No Capital Expense (CapEx):** Shift to Operational Expense (OpEx) by paying only for what you consume.
2. **Infinite Scale:** Instantly scale from 1 to 1,000 instances via Auto Scaling.
3. **Speed to Market:** Deploy code globally in minutes.
4. **Managed Maintenance:** Let AWS maintain hardware, power, cooling, and security compliance (SOC2, PCI-DSS, HIPAA).

#### Q (Scenario): Your company experiences a 10x traffic spike during Black Friday. How does the cloud solve this?
**A:** On-premises requires purchasing hardware to handle peak load, which sits idle for the rest of the year. AWS resolves this through **Auto Scaling** and **Elasticity**. Under normal conditions, you run a minimal fleet (e.g., 2 EC2 instances). When traffic spikes, Auto Scaling launches additional instances to handle the load and terminates them when demand drops. You only pay for compute capacity used during the peak.

### 4. Key Takeaways
* Cloud computing shifts infrastructure management from capital expenditure (CapEx) to operational expenditure (OpEx).
* The Shared Responsibility Model defines security bounds: AWS secures the cloud infrastructure; you secure the application and data.
* Deployments leverage varying levels of abstraction depending on need: IaaS offers maximum control, PaaS offers deployment speed, and SaaS offers zero maintenance.

---

## TOPIC 2: AWS GLOBAL INFRASTRUCTURE

### 1. Concept Explanation

#### Beginner
AWS operates physical data centers globally, structured hierarchically:
* **Regions:** A geographical area containing isolated resource clusters (e.g., Mumbai `ap-south-1`, N. Virginia `us-east-1`).
* **Availability Zones (AZs):** One or more discrete data centers within a Region, separated by distance to minimize damage from local disasters, and connected via high-speed, redundant fiber networks.
* **Edge Locations:** Network endpoints used by Amazon CloudFront (CDN) to cache static content closer to users to reduce latency.

#### Intermediate
* Regions are completely isolated from one another to prevent failure propagation. Data does not travel between regions unless explicitly configured.
* AZs are physically separated by miles (protecting against fires, floods, earthquakes) but are close enough to maintain low single-digit millisecond latency.
* Most AWS services are **Regional** (e.g., S3 bucket metadata, VPC configuration, RDS). Some are **Global** (e.g., IAM, Route 53, CloudFront).

##### Region & AZ Naming Convention Examples:
* `ap-south-1` (Mumbai Region)
  * `ap-south-1a` (Availability Zone A)
  * `ap-south-1b` (Availability Zone B)
  * `ap-south-1c` (Availability Zone C)

#### Advanced
High Availability (HA) deployments leverage Multi-AZ architectures.
* **RDS Multi-AZ:** The active database (Primary) sits in `ap-south-1a` and replicates synchronously to a Standby in `ap-south-1b`. If the primary AZ suffers an outage, AWS updates the DNS record to point to the standby instance within 60–120 seconds.
* **EC2 Multi-AZ:** Instances are distributed across multiple AZs under an Application Load Balancer (ALB). If an AZ goes down, the ALB stops routing traffic to instances in that AZ, while healthy AZ instances handle the traffic.

### 2. Architecture Diagram

```mermaid
flowchart TB
    subgraph Region ["Mumbai Region (ap-south-1)"]
        subgraph AZ1 ["Availability Zone A (ap-south-1a)"]
            EC1["EC2 App Instance 1"]
            DB1[("RDS Primary Database")]
        end
        subgraph AZ2 ["Availability Zone B (ap-south-1b)"]
            EC2["EC2 App Instance 2"]
            DB2[("RDS Standby Database")]
        end
        subgraph AZ3 ["Availability Zone C (ap-south-1c)"]
            EC3["EC2 App Instance 3"]
        end

        ALB["Application Load Balancer (ALB)"]
    end

    Internet((Internet Users)) --> ALB
    ALB --> EC1
    ALB --> EC2
    ALB --> EC3
    EC1 & EC2 & EC3 --> DB1
    DB1 ==>|Synchronous Replication| DB2

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef client fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;

    class EC1,EC2,EC3,ALB layer;
    class DB1,DB2 db;
    class Internet client;
```

### 3. Interview Questions & Answers

#### Q: What is the difference between a Region and an Availability Zone?
**A:** A Region is a global geographic location (e.g., Mumbai, Ireland) hosting multiple independent infrastructure zones. An Availability Zone (AZ) is a group of physical data centers within that region. Each Region contains a minimum of three AZs.

#### Q: How do you choose which AWS Region to deploy your application in?
**A:** Choose a region based on:
1. **Latency:** Deploy near your end users to minimize round-trip network time.
2. **Data Compliance:** Adhere to regional regulations (e.g., GDPR requires EU regions).
3. **Feature Availability:** Not all new AWS services are available in all regions initially.
4. **Cost:** Resource pricing varies slightly by region (e.g., `us-east-1` is typically the cheapest).

#### Q (Scenario): A primary RDS database crashes in production. How does Multi-AZ prevent data loss?
**A:** With Multi-AZ enabled, RDS maintains a synchronous standby instance in a different AZ. All writes to the primary database are replicated to the standby before completing. When the primary crashes, AWS detects the failure, switches the DNS endpoint to point to the standby, and completes the failover within 60–120 seconds. No application code changes are required because the database connection string remains the same.

---

### 4. AWS Management Access Methods (From Board Work)

> **From Board Work:** The instructor explicitly drew the 5 ways to interact with AWS, clarifying that every tool ultimately talks to the same AWS APIs underneath.

```mermaid
flowchart LR
    subgraph ACCESS ["🖥️ Access Methods (5 Ways to Interact with AWS)"]
        CONSOLE["🌐 Web Console / UI\n(AWS Management Console)\nBrowser-based GUI"]
        CLI["⌨️ AWS CLI\n(aws ec2 describe-instances)\nTerminal commands"]
        CFN["📋 CloudFormation (IAC)\n(YAML/JSON Templates)\nAWS-native IaC tool"]
        TF["🔧 Terraform (IAC)\n(HCL Templates)\nMulti-cloud IaC tool"]
        SDK["💻 SDK\n(Java, Python, Go, Node)\nProgrammatic API access"]
    end

    AWS["☁️ AWS Cloud\n(All services:\nEC2, S3, RDS, VPC...)"]

    CONSOLE -->|"HTTPS API calls"| AWS
    CLI -->|"HTTPS API calls"| AWS
    CFN -->|"Deploys stacks via API"| AWS
    TF -->|"Provisions via AWS API"| AWS
    SDK -->|"Programmatic API calls"| AWS

    classDef access fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef aws fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    class CONSOLE,CLI,CFN,TF,SDK access;
    class AWS aws;
```

#### Access Methods Comparison

| Method | Best For | Skill Level | Example |
|:---|:---|:---|:---|
| **Web Console (UI)** | Learning, visual exploration, one-time tasks | Beginner | Click through EC2 → Launch Instance |
| **AWS CLI** | Automation scripts, quick ad-hoc management | Intermediate | `aws s3 cp ./file.txt s3://my-bucket/` |
| **CloudFormation** | AWS-only infrastructure-as-code, drift detection | Intermediate | YAML template deploys entire VPC stack |
| **Terraform** | Multi-cloud IaC, version-controlled infrastructure | Advanced | `terraform apply` provisions EC2 + RDS + VPC |
| **SDK (Java/Python)** | Application integration, programmatic AWS access | Intermediate-Advanced | Spring Boot uploads to S3 via AWS SDK v2 |

> [!TIP]
> In production engineering, you should **never click around the console** to create resources. Use **Terraform or CloudFormation** so every infrastructure change is tracked in Git, reviewable, and repeatable. The console is for learning and debugging only.

---

### 5. Key Takeaways
* Regions are completely isolated failure domains. Availability Zones are physically separated but connected via low-latency networks.
* Edge locations are CDN caching nodes used by CloudFront, distinct from AZ data centers.
* Multi-AZ deployments are the foundation of high availability, automated failover, and disaster recovery.
* There are **5 ways to access AWS**: Web Console, CLI, CloudFormation (IAC), Terraform (IAC), and SDK.

---


## TOPIC 3: EC2 — ELASTIC COMPUTE CLOUD

### 1. Concept Explanation

#### Beginner
Elastic Compute Cloud (EC2) provides virtual servers (Virtual Machines) in the cloud. You customize the operating system, CPU, memory, and networking properties.

Key Terminology:
* **Instance:** A single running EC2 virtual server.
* **AMI (Amazon Machine Image):** A pre-configured template containing the OS, libraries, and configurations needed to boot an instance.
* **Instance Type:** Defines the hardware capabilities (CPU cores, RAM size, network bandwidth).
* **Security Group:** A virtual firewall controlling inbound and outbound network traffic to your instance.
* **Key Pair (.pem file):** Public/Private keys used for secure SSH terminal authentication.

#### Intermediate
##### EC2 Instance Types
| Family | Purpose | Example | Primary Use Case |
| :--- | :--- | :--- | :--- |
| **t3 / t2** | Burstable General Purpose | `t3.micro`, `t3.medium` | Dev/Test environments, small web servers |
| **m5** | Balanced General Purpose | `m5.large`, `m5.xlarge` | Backend APIs, enterprise Spring Boot apps |
| **c5** | Compute Optimized | `c5.large`, `c5.2xlarge` | High-CPU tasks, batch processors, encoders |
| **r5** | Memory Optimized | `r5.large`, `r5.2xlarge` | In-memory databases (Redis), large caches |
| **i3** | Storage Optimized | `i3.large` | Databases with high direct SSD I/O needs |
| **p3** | GPU/Accelerated Compute | `p3.2xlarge` | Machine Learning training, GPU rendering |

##### IP Types in AWS

AWS instances utilize three primary types of IP addresses:

1. **Private IP (Fixed IP):**
   * **Purpose:** Used for internal communication within the VPC (Virtual Private Cloud).
   * **Lifecycle:** Stays fixed for the lifetime of the instance. It does not change upon stop/start.
   * **Example:** `172.31.7.164`

2. **Public IP (Dynamic IP):**
   * **Purpose:** Used to connect to the EC2 instance from outside the network (the Internet).
   * **Lifecycle:** Dynamic. If you stop and restart the instance, the public IP address is released and a new one is assigned.
   * **Example:** `3.109.213.248` changes to `13.235.79.233` upon restart.

3. **Elastic IP (Static/Persistent Public IP):**
   * **Purpose:** Used when a persistent, fixed public IP address is required (e.g., DNS mapping).
   * **Billing Warning:** Elastic IPs are paid resources. Charges accumulate if they are allocated but not associated, or if the associated instance is stopped/terminated.
   * **Example:** `65.0.78.209` remains unchanged irrespective of how many times you restart or stop the VM.

###### Elastic IP Hands-On Practical Steps:
* **Step 1: Allocate Elastic IP:** Navigate to the EC2 Console -> Under **Network & Security**, select **Elastic IPs** -> Click **Allocate Elastic IP** to request a static public IP from the AWS pool (e.g., `65.0.78.209`).
* **Step 2: Associate Elastic IP:** Select the allocated IP -> Go to **Actions** -> **Associate Elastic IP address** -> Select your target EC2 instance.
* **Step 3: Verification:** Restart the EC2 instance and verify that the public IP remains exactly the same.
* **Step 4: Disassociate Elastic IP:** Select the IP -> Go to **Actions** -> **Disassociate Elastic IP address** to detach it from the instance.
* **Step 5: Release Elastic IP:** Select the IP -> Go to **Actions** -> **Release Elastic IP address** to return the IP to the AWS pool (this stops active billing charges).

##### Billing Models
* **On-Demand (Hourly):** Pay per hour/second with no upfront commitment. Best for developmental, unpredictable, or short-term tasks.
  * **Minimum Billing Period is 1 hour** regardless of actual usage:
    * `11:15 AM → 11:30 AM` = 15 mins → billed for 1 hour
    * `09:15 AM → 09:20 AM` = 5 mins → billed for 1 hour
    * `09:15 AM → 10:10 AM` = 55 mins → billed for 1 hour
* **Free Tier:** AWS provides `t2.micro` / `t3.micro` for **6 months free** to encourage new learners.
* **Reserved Instances (RI) / Savings Plans:** Commit to 1 or 3 years of usage for a discount up to 72%. Best for stable, baseline production workloads.
* **Spot Instances:** Bid on spare AWS capacity at up to a 90% discount. AWS can reclaim these instances with a 2-minute warning. Best for fault-tolerant workloads like CI/CD runners or batch processing.

##### EC2 VM Creation — Step-by-Step Checklist
1. **Create a Key Pair (.pem file):**
   * AWS retains the public key; you download the private key (`.pem` file).
   * One Key Pair can be reused across multiple EC2 instances.
2. **Create / Configure a Security Group:**
   * Add inbound rules for required ports (e.g., SSH: 22, HTTP: 80, HTTPS: 443, App: 8080).
   * One Security Group can be applied to multiple instances.
3. **Launch EC2 Instance:**
   * Provide a name for the VM.
   * Select an **AMI** (Windows AMI, Ubuntu AMI, RedHat AMI, Amazon Linux AMI).
   * Choose an **Instance Type** (e.g., `t3.micro` for dev).
   * Attach your Key Pair (existing or create new).
   * Attach your Security Group (existing or create new).
   * Configure **EBS Storage** — Linux default: 8 GB, Windows default: 30 GB, max: 16 TB.
   * Click **Launch Instance**.

> [!NOTE]
> You can create a new Key Pair and Security Group directly at the time of instance creation without pre-creating them separately.

##### EC2 Bootstrapping
You can configure **User Data** scripts to execute automatically once during the first launch of the instance:
```bash
#!/bin/bash
sudo yum update -y
sudo yum install httpd -y
sudo systemctl start httpd
sudo systemctl enable httpd
echo "<h1>Hello World from EC2 Bootstrapping</h1>" > /var/www/html/index.html
```

#### Advanced
For automated scaling, attach EC2 instances to an **Auto Scaling Group (ASG)**:
* **Desired/Min/Max Limits:** Define capacity targets (e.g., Min=2, Desired=3, Max=10).
* **Scaling Policies:**
  * *Target Tracking:* Automatically scale out when the target average CPU utilization exceeds 60%.
  * *Scheduled Scaling:* Scale the instance count to 10 ahead of an expected marketing campaign on Friday at 9 AM.

### 2. Architecture Diagram

```mermaid
flowchart LR
    Users((Users)) --> ALB["Application Load Balancer (ALB)"]
    
    subgraph ASG ["Auto Scaling Group (Min: 2, Max: 10, Desired: 3)"]
        EC1["EC2 Instance (AZ-1a)"]
        EC2["EC2 Instance (AZ-1b)"]
        EC3["EC2 Instance (AZ-1c)"]
    end

    ALB --> EC1
    ALB --> EC2
    ALB --> EC3
    EC1 & EC2 & EC3 --> DB[("RDS DB (Multi-AZ)")]

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef client fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;

    class Users client;
    class ALB,EC1,EC2,EC3 layer;
    class DB db;
```

### 3. Interview Questions & Answers

#### Q: What is the difference between stopping and terminating an EC2 instance?
**A:** 
* **Stopping:** Shuts down the operating system. The instance remains in your account, and any data on the root EBS volume is preserved. You only pay for the EBS storage, not compute hours. The public IP address changes when restarted unless using an Elastic IP.
* **Terminating:** Permanently deletes the virtual machine and its root EBS volume (by default). The instance is destroyed and cannot be restarted.

#### Q: How do you deploy a Spring Boot application on an EC2 instance securely?
**A:** 
1. Place the EC2 instance in a **Private Subnet** inside your VPC (so it has no public IP).
2. Deploy an **Application Load Balancer (ALB)** in a Public Subnet to receive traffic from the internet and route it to your EC2 instance on port 8080.
3. Configure the **Security Group** of the EC2 instance to only allow inbound traffic from the ALB's Security Group on port 8080.
4. Attach an **IAM Role** to the EC2 instance to grant it necessary permissions (like reading database credentials from AWS Secrets Manager) without hardcoded credentials in the application files.

#### Q: What is the difference between Security Groups and Network ACLs (NACLs)?
**A:** 
* **Security Groups** act as virtual firewalls at the **instance level**, are **stateful** (allowing return traffic automatically), and support allow-only rules.
* **NACLs** act as firewalls at the **subnet level**, are **stateless** (requiring explicit rule configuration for both inbound and outbound traffic), and support both allow and deny rules.

### 4. Commands & Configuration Examples

#### SSH into EC2
```bash
# Set permissions so key file is not publicly readable
chmod 400 my-key.pem

# SSH using default OS users
ssh -i my-key.pem ec2-user@<public-ip-or-dns>  # Amazon Linux
ssh -i my-key.pem ubuntu@<public-ip-or-dns>    # Ubuntu
```

#### Production systemd Configuration for Spring Boot JAR
Create `/etc/systemd/system/policy-service.service`:
```ini
[Unit]
Description=Spring Boot Policy Service
After=network.target

[Service]
User=ec2-user
WorkingDirectory=/home/ec2-user
ExecStart=/usr/bin/java -jar /home/ec2-user/policy-service.jar --spring.profiles.active=prod
SuccessExitStatus=143
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

```bash
# Reload systemd, enable service to start on boot, and start service
sudo systemctl daemon-reload
sudo systemctl enable policy-service
sudo systemctl start policy-service
```

---

### 4a. EC2 Apache Web Server — Deep Dive Command Reference

This section provides an in-depth analysis of every command used in a real-world EC2 Apache (`httpd`) setup, as seen in the terminal session below:

```
[ec2-user@ip-172-31-45-101 html]$ sudo service httpd stop
Redirecting to /bin/systemctl stop httpd.service

[ec2-user@ip-172-31-45-101 html]$ sudo service httpd start
Redirecting to /bin/systemctl start httpd.service

[ec2-user@ip-172-31-45-101 html]$ pwd
/var/www/html

[ec2-user@ip-172-31-45-101 html]$ ls -l
total 8
-rw-r--r--. 1 root root 4202 Jul  1 11:35 index.html
```

---

#### 4a.1 Understanding the Shell Prompt

Before diving into commands, understand what the prompt tells you:

```
[ec2-user@ip-172-31-45-101 html]$
```

| Prompt Part | Meaning |
| :--- | :--- |
| `ec2-user` | The currently logged-in Linux username (default user on Amazon Linux) |
| `ip-172-31-45-101` | The hostname of the EC2 instance (derived from its private IP `172.31.45.101`) |
| `html` | The current working directory name (`/var/www/html`) |
| `$` | Indicates a regular (non-root) user. A `#` symbol would indicate root user |

> [!NOTE]
> On Amazon Linux 2 and Amazon Linux 2023, the default SSH user is `ec2-user`. On Ubuntu it is `ubuntu`, on Red Hat it is `ec2-user`, on Debian it is `admin`, and on SUSE it is `ec2-user` or `root`.

---

#### 4a.2 `sudo service httpd stop` — Stopping Apache

**What it does:**
Stops the running Apache HTTP Server (httpd) service gracefully. Apache will stop accepting new connections and complete any in-flight requests before shutting down.

**Why it is used:**
- Before deploying a new version of a website to avoid serving partial/inconsistent content
- When performing OS or Apache configuration changes that require a service restart
- During maintenance windows to take the server offline intentionally
- To free up port 80/443 for troubleshooting

**Syntax:**
```bash
sudo service httpd stop
```

**Breaking down the command:**

| Part | Meaning |
| :--- | :--- |
| `sudo` | Execute with superuser (root) privileges. Apache runs as root to bind to privileged ports (80, 443) |
| `service` | The SysV init compatibility wrapper (legacy command that redirects to systemctl) |
| `httpd` | The service name. `httpd` stands for **HTTP Daemon** — the Apache web server background process |
| `stop` | The action — gracefully terminates the service |

**Expected Output:**
```
Redirecting to /bin/systemctl stop httpd.service
```

> [!NOTE]
> The message `Redirecting to /bin/systemctl stop httpd.service` is **not an error**. It is informational. On Amazon Linux 2, the legacy `service` command is a compatibility wrapper that automatically delegates to `systemctl` — the modern systemd init system. The actual work is done by `systemctl`.

**Real-world DevOps use cases:**
- **Blue-Green Deployments:** Stop httpd on the "blue" instance before switching traffic to the "green" instance via the Load Balancer
- **Maintenance Mode:** Take Apache offline before running database migrations that would cause the app to be unavailable
- **Certificate Renewal:** Stop Apache to free port 80 when using Certbot (Let's Encrypt) in standalone mode
- **Deployment Scripts:** Part of a CI/CD shell script that stops the server, replaces files, and restarts

**Common Interview Questions:**
- *Q: What does the `Redirecting to /bin/systemctl` message mean?* — A: It means `service` is a wrapper script that delegates to `systemctl` on systemd-based systems.
- *Q: Is `sudo service httpd stop` the same as `sudo systemctl stop httpd`?* — A: Yes, functionally identical on Amazon Linux 2.
- *Q: How do you verify Apache stopped successfully?* — A: Run `sudo systemctl status httpd`.

**Best Practices:**
- Always verify the service stopped: `sudo systemctl status httpd`
- If in production, update the Load Balancer to route traffic away from this instance **before** stopping Apache
- Prefer `systemctl` over `service` in modern scripts for clarity and predictability

**Troubleshooting:**
```bash
# If stop hangs, check for zombie processes:
ps aux | grep httpd

# Force kill all httpd processes (last resort):
sudo pkill -9 httpd

# Check why Apache failed to stop cleanly:
sudo journalctl -u httpd -n 50 --no-pager
```

---

#### 4a.3 `sudo service httpd start` — Starting Apache

**What it does:**
Starts the Apache HTTP Server daemon. Apache begins listening on port 80 (HTTP) and/or 443 (HTTPS) and starts serving web content from `/var/www/html`.

**Why it is used:**
- After deploying new website files to make them live
- After stopping Apache for maintenance or configuration changes
- As part of deployment automation scripts
- To verify that Apache starts cleanly after configuration changes

**Syntax:**
```bash
sudo service httpd start
```

**Expected Output:**
```
Redirecting to /bin/systemctl start httpd.service
```

**The full startup sequence (what happens internally):**

```mermaid
flowchart TD
    A["sudo service httpd start"] --> B["Redirects to systemctl start httpd.service"]
    B --> C["systemd reads /usr/lib/systemd/system/httpd.service"]
    C --> D["Forks httpd master process as root"]
    D --> E["httpd reads /etc/httpd/conf/httpd.conf"]
    E --> F["Binds to port 80 and/or 443"]
    F --> G["Spawns worker child processes as apache user"]
    G --> H["Starts serving requests from /var/www/html"]

    classDef step fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef finish fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class A,B,C,D,E,F,G step;
    class H finish;
```

**Real-world DevOps use cases:**
- **Post-Deployment Start:** After `scp` or `aws s3 cp` of new HTML/JS files, start httpd to serve them
- **Auto-Recovery:** In monitoring scripts, automatically restart httpd if it crashes
- **Bootstrap Scripts:** In EC2 User Data scripts, `sudo service httpd start` is run after `yum install httpd -y`

**Important Notes:**
- If Apache fails to start, it usually means a configuration syntax error or port conflict
- Check logs at `/var/log/httpd/error_log` for startup failures
- `start` does NOT enable auto-start on reboot — use `sudo systemctl enable httpd` separately

**Best Practices:**
```bash
# After deployment, always verify Apache is running:
sudo systemctl status httpd

# Check which ports httpd is listening on:
sudo ss -tlnp | grep httpd

# Validate Apache config before starting (catches syntax errors):
sudo apachectl configtest
```

**Troubleshooting:**
```bash
# If Apache fails to start, check the error log:
sudo tail -100 /var/log/httpd/error_log

# Check if port 80 is already in use by another process:
sudo lsof -i :80

# Check systemd for startup failure reason:
sudo journalctl -u httpd --since "5 minutes ago" --no-pager
```

---

#### 4a.4 `service` vs `systemctl` — Complete Comparison

This is one of the **most frequently asked Linux interview questions** for DevOps roles.

| Feature | `service` (Legacy SysV) | `systemctl` (Modern systemd) |
| :--- | :--- | :--- |
| **Origin** | SysV init system (Unix legacy, ~1980s) | systemd (modern Linux, 2010+) |
| **Availability** | Works on all Linux distros for backwards compatibility | Default on Amazon Linux 2, RHEL 7+, Ubuntu 16+, CentOS 7+ |
| **Behavior on Amazon Linux 2** | Wrapper script — redirects all calls to `systemctl` | Native systemd command |
| **Start service** | `sudo service httpd start` | `sudo systemctl start httpd` |
| **Stop service** | `sudo service httpd stop` | `sudo systemctl stop httpd` |
| **Restart service** | `sudo service httpd restart` | `sudo systemctl restart httpd` |
| **Check status** | `sudo service httpd status` | `sudo systemctl status httpd` |
| **Enable on boot** | `sudo chkconfig httpd on` | `sudo systemctl enable httpd` |
| **Disable on boot** | `sudo chkconfig httpd off` | `sudo systemctl disable httpd` |
| **Reload config** | `sudo service httpd reload` | `sudo systemctl reload httpd` |
| **View logs** | `cat /var/log/httpd/error_log` | `sudo journalctl -u httpd -f` |

> [!TIP]
> **Interview Tip:** When asked "What is the difference between `service` and `systemctl`?", explain that on modern Amazon Linux 2, `service` is just a compatibility wrapper that calls `systemctl`. The real engine is `systemd`. In new scripts, always prefer `systemctl` as it provides better output, journald integration, and dependency management.

**Why Amazon Linux uses `httpd` (not `apache2`):**

| Distribution | Package Name | Service Name | Reason |
| :--- | :--- | :--- | :--- |
| **Amazon Linux 2 / RHEL / CentOS** | `httpd` | `httpd` | Follows Red Hat naming conventions. `httpd` = HTTP Daemon |
| **Ubuntu / Debian** | `apache2` | `apache2` | Follows Debian naming — named after the software product |

Amazon Linux 2 is derived from **Red Hat Enterprise Linux (RHEL)**, so it follows Red Hat/Fedora naming conventions.

```bash
# Install Apache on Amazon Linux (RHEL-family):
sudo yum install httpd -y

# Install Apache on Ubuntu (Debian-family):
sudo apt install apache2 -y
```

---

#### 4a.5 `pwd` — Print Working Directory

**What it does:**
Prints the absolute path of the current working directory to the terminal.

**Syntax:**
```bash
pwd
```

**Expected Output:**
```
/var/www/html
```

**Why `/var/www/html` is the default Apache web root:**

| Directory Component | Meaning |
| :--- | :--- |
| `/var` | Variable data — files that change during normal system operation (logs, caches, web content) |
| `/var/www` | The web server's root directory — Apache's home |
| `/var/www/html` | The **DocumentRoot** — the directory Apache serves files from by default |

This path is configured in Apache's main configuration file:
```bash
cat /etc/httpd/conf/httpd.conf | grep DocumentRoot
# Output: DocumentRoot "/var/www/html"
```

> [!IMPORTANT]
> `DocumentRoot` tells Apache which directory maps to the URL `/`. So if you put `index.html` in `/var/www/html/`, it is accessible at `http://<EC2-Public-IP>/`.

**Directory Structure:**
```
/var/
+-- www/
    +-- html/          <- DocumentRoot (web content served from here)
    |   +-- index.html <- Accessible at http://<IP>/
    +-- cgi-bin/       <- CGI scripts
    +-- icons/         <- Apache default icons
```

---

#### 4a.6 `ls -l` — List Files with Details

**What it does:**
Lists all files and directories in the current directory in **long format**, showing detailed metadata for each file.

**Syntax:**
```bash
ls -l
ls -la    # Include hidden files (starting with .)
ls -lh    # Human-readable file sizes (e.g., 4.2K instead of 4202)
ls -ltr   # Sort by modification time, oldest first
```

**Output from the terminal session:**
```
total 8
-rw-r--r--. 1 root root 4202 Jul  1 11:35 index.html
```

**Breaking down every field of the output:**

```
-rw-r--r--. 1 root root 4202 Jul  1 11:35 index.html
|__________|   |    |    |    |__________  |
Permissions  HardLink  Owner  Group  Size  Timestamp  Filename
```

**Total line:**
`total 8` means 8 x 512 bytes = 4096 bytes of actual disk space allocated.

**Deep Dive: Permission String `-rw-r--r--.`**

```
- rw- r-- r-- .
| |-- |-- |-- |
| |   |   |   +-- ACL indicator (. = no ACL, + = has ACL)
| |   |   +------- Other permissions: r-- = read-only for all other users
| |   +----------- Group permissions: r-- = read-only for the root group
| +--------------- Owner permissions: rw- = read + write for the owner (root)
+----------------- File type: - = regular file
```

**Permission Values:**

| Symbol | Octal Value | Meaning |
| :---: | :---: | :--- |
| `r` | 4 | **Read** — view file content or list directory contents |
| `w` | 2 | **Write** — modify file content or create/delete files |
| `x` | 1 | **Execute** — run as a program (files) or traverse (directories) |
| `-` | 0 | **No permission** |

**Octal representation of `-rw-r--r--`:**

| Who | Permissions | Calculation | Octal |
| :--- | :--- | :--- | :--- |
| **Owner** (root) | `rw-` | r(4) + w(2) + -(0) | **6** |
| **Group** (root) | `r--` | r(4) + -(0) + -(0) | **4** |
| **Others** | `r--` | r(4) + -(0) + -(0) | **4** |
| **Full octal** | | | **644** |

```bash
# -rw-r--r-- is equivalent to chmod 644
sudo chmod 644 /var/www/html/index.html
```

**Why can Apache (httpd) still read a root-owned file?**

Apache's worker processes run as the `apache` user (not root). The file permissions `-rw-r--r--` give **read access to everyone** (the `r--` for "others"). So the `apache` user can read and serve the file even though it is owned by root.

```bash
# Verify Apache's running user:
ps aux | grep httpd
# Output shows: apache  12345  ... httpd -DFOREGROUND

# Check Apache user config:
grep "^User\|^Group" /etc/httpd/conf/httpd.conf
# Output:
# User apache
# Group apache
```

**Full field-by-field explanation:**

| Field | Value | Explanation |
| :--- | :--- | :--- |
| File type | `-` | Regular file. Other types: `d` = directory, `l` = symlink, `b` = block device |
| Owner permissions | `rw-` | Owner (root) can read and write. Cannot execute |
| Group permissions | `r--` | Group members can only read |
| Other permissions | `r--` | All other users (including `apache`) can only read |
| ACL indicator | `.` | No extended ACL |
| Hard links | `1` | Only one directory entry points to this file's inode |
| Owner username | `root` | The Linux user who owns this file |
| Group name | `root` | The Linux group that owns this file |
| Size | `4202` | File size in **bytes** |
| Timestamp | `Jul 1 11:35` | Last modification time |
| Filename | `index.html` | The file name |

**Apache Web Server Architecture Diagram:**

```mermaid
flowchart TD
    subgraph Internet["Internet Traffic"]
        Browser["User's Browser\nGET http://3.109.x.x/"]
    end

    subgraph EC2["EC2 Instance (Amazon Linux 2)"]
        SG["Security Group\nInbound: Port 80 Allow"]
        subgraph Apache["Apache httpd Process"]
            Master["httpd Master Process\n(runs as root)"]
            Worker1["Worker Process 1\n(runs as apache user)"]
            Worker2["Worker Process 2\n(runs as apache user)"]
        end
        subgraph DocRoot["/var/www/html (DocumentRoot)"]
            Index["index.html\n-rw-r--r-- root root 4202"]
        end
    end

    Browser -->|HTTP GET :80| SG
    SG --> Master
    Master --> Worker1 & Worker2
    Worker1 -->|Read file - r-- others| Index
    Index -->|200 OK + HTML content| Browser

    classDef internet fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef sg fill:#581C87,stroke:#A855F7,color:#FFFFFF,stroke-width:2px;
    classDef apache fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef file fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class Browser internet;
    class SG sg;
    class Master,Worker1,Worker2 apache;
    class Index file;
```

---

#### 4a.7 Deployment & Troubleshooting Workflow

**Standard Deployment Flow:**

```mermaid
flowchart TD
    A["1. Upload new HTML/assets to EC2\nscp -i key.pem ./dist/* ec2-user@IP:/tmp/"] --> B["2. Stop Apache\nsudo service httpd stop"]
    B --> C["3. Copy new files to DocumentRoot\nsudo cp -r /tmp/dist/* /var/www/html/"]
    C --> D["4. Set correct permissions\nsudo chmod -R 644 /var/www/html/*"]
    D --> E["5. Start Apache\nsudo service httpd start"]
    E --> F{"6. Verify\nsudo systemctl status httpd"}
    F -->|Active running| G["7. Test in browser\nhttp://EC2-Public-IP"]
    F -->|Failed| H["8. Check error log\nsudo tail -50 /var/log/httpd/error_log"]
    H --> I["9. Fix config/file issue"]
    I --> E

    classDef step fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef decision fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef success fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class A,B,C,D,E,H,I step;
    class F decision;
    class G success;
```

**Key Troubleshooting Commands:**

```bash
# Check if httpd is running:
sudo systemctl status httpd

# Follow Apache access log in real-time:
sudo tail -f /var/log/httpd/access_log

# Follow Apache error log in real-time:
sudo tail -f /var/log/httpd/error_log

# Verify Apache is listening on port 80:
sudo ss -tlnp | grep :80

# Test locally from within the EC2 instance:
curl -v http://localhost/

# Check current directory and list files:
pwd && ls -la /var/www/html/

# Fix permissions:
sudo chmod -R 755 /var/www/html/         # directories need execute (traverse)
sudo chmod -R 644 /var/www/html/*.html   # files need read

# Validate Apache config syntax before restarting:
sudo apachectl configtest
# Expected output: Syntax OK

# Graceful restart (reloads config without dropping active connections):
sudo apachectl graceful
```

**Common Issues & Fixes:**

| Problem | Symptom | Root Cause | Fix |
| :--- | :--- | :--- | :--- |
| Browser shows connection refused | `ERR_CONNECTION_REFUSED` | httpd not running | `sudo service httpd start` |
| Browser shows connection timeout | Page spins forever | Security Group port 80 blocked | Add inbound rule: TCP 80 from `0.0.0.0/0` |
| HTTP 403 Forbidden | `403 Forbidden` in browser | File permissions wrong | `sudo chmod 644 /var/www/html/index.html` |
| Apache fails to start | `Active: failed` in status | Port 80 in use, or config syntax error | `sudo apachectl configtest` to find error |
| Changes not reflected | Old content still showing | Browser cache | Hard refresh (`Ctrl+Shift+R`) |

---

#### 4a.8 Important Interview Questions — EC2 Apache & Linux

**Q1: What is the difference between `service httpd stop` and `systemctl stop httpd`?**
> **A:** On Amazon Linux 2, they are functionally identical. `service` is a legacy SysV compatibility wrapper that automatically redirects commands to `systemctl`. When you run `sudo service httpd stop`, you see `Redirecting to /bin/systemctl stop httpd.service` — confirming the delegation. In modern scripts, `systemctl` is preferred.

**Q2: Why is Apache called `httpd` on Amazon Linux, but `apache2` on Ubuntu?**
> **A:** Amazon Linux 2 is based on Red Hat Enterprise Linux (RHEL), which names the Apache web server package `httpd` (HTTP Daemon). Ubuntu/Debian distributions name it `apache2`. The underlying Apache HTTP Server software is identical — only the package and service naming differs.

**Q3: Why is `/var/www/html` the default web root for Apache?**
> **A:** The Filesystem Hierarchy Standard (FHS) designates `/var` for variable/runtime data. Apache's `DocumentRoot` configuration in `/etc/httpd/conf/httpd.conf` points to `/var/www/html` by default. Any file placed in this directory is served by Apache at the corresponding URL path.

**Q4: Explain the output of `ls -l`: `-rw-r--r--. 1 root root 4202 Jul 1 11:35 index.html`**
> **A:** The `-` means it is a regular file. `rw-` means the owner (root) has read+write. `r--` means the group (root) has read-only. `r--` means all others (including the apache process user) have read-only. The `.` means no extended ACLs. `1` is the hard link count. `root root` are the owner and group. `4202` is the size in bytes. `Jul 1 11:35` is the last modification time. `index.html` is the filename.

**Q5: If Apache is running but the browser shows 403 Forbidden, what is wrong?**
> **A:** Most likely a file permission issue. Apache's `apache` user must be able to read the HTML files. The file needs at least `r--` permission for "others" (octal 644). Also check directory permissions — Apache needs execute (`x`) permission on all directories in the path (e.g., `/var/www/html` needs `755`).

**Q6: How do you make Apache start automatically when the EC2 instance reboots?**
> **A:** `sudo systemctl enable httpd` — this creates a symlink in the systemd run-level directories so that httpd starts automatically on every boot. Without this, `service httpd start` only starts Apache for the current session.

---

### 5. Best Practices
* **Principle of Least Privilege:** Do not write AWS credential keys inside your application files; always attach an IAM Role to the EC2 instance profile.
* **Network Isolation:** Run EC2 backend apps in private subnets and expose them using an ALB.
* **Secure Access:** Never open port 22 (SSH) to the entire internet (`0.0.0.0/0`); limit access to your corporate network range or use AWS Systems Manager Session Manager.

### 6. Key Takeaways
* EC2 provides customizable, elastic virtual machines.
* Billing models (Reserved, Spot, On-Demand) must be matched to workloads to optimize costs.
* Security groups are stateful, instance-level firewalls; NACLs are stateless, subnet-level firewalls.

---

## TOPIC 4: EBS — ELASTIC BLOCK STORE

### 1. Concept Explanation

#### Beginner
Elastic Block Store (EBS) is a network-attached hard drive (block storage) for EC2 instances. It is used to store the operating system, databases, and application files.

Key Characteristics:
* **Zone-Locked:** An EBS volume must reside in the same Availability Zone (AZ) as the EC2 instance it attaches to.
* **Detachable:** An EBS volume can be detached from an instance and attached to another in the same AZ, acting as a portable hard drive.
* **One-to-One:** By default, an EBS volume attaches to one EC2 instance at a time (though io1/io2 volumes support Multi-Attach).
* **Network-Attached:** Unlike local instance store disks, EBS volumes communicate with EC2 instances over a dedicated storage network fabric.

#### Intermediate
##### EBS Volume Types
| Volume Type | Technology | Throughput / IOPS | Primary Use Cases |
| :--- | :--- | :--- | :--- |
| **gp3** (General Purpose SSD) | SSD | Up to 16,000 IOPS / 1,000 MB/s | System boot volumes, virtual desktops, development environments |
| **gp2** (General Purpose SSD) | SSD | Up to 16,000 IOPS (bursts based on size) | Legacy general workloads |
| **io2** (Provisioned IOPS SSD) | SSD | Up to 256,000 IOPS / 4,000 MB/s | Large, low-latency relational databases (Oracle, SQL Server) |
| **st1** (Throughput Optimized HDD) | HDD | Up to 500 MB/s | Large data streams, log analysis, data warehousing |
| **sc1** (Cold HDD) | HDD | Up to 250 MB/s | Large archives, infrequently accessed backup files |

* **Snapshots:** Point-in-time backups of your EBS volumes, stored in Amazon S3. Snapshots are incremental, storing only changed blocks, and are region-wide (not zone-locked).
* **EBS is AZ-Locked:** A volume must be in the same AZ as its EC2 instance.
  * Example: If your EC2 instance is in `ap-south-1a` (Mumbai), your EBS volume must also be in `ap-south-1a`.
  * Available Mumbai AZs: `ap-south-1a`, `ap-south-1b`, `ap-south-1c`.

#### Advanced
* **EBS Encryption:** Uses AES-256 to encrypt data at rest, snapshots, and data in transit between the EC2 host and the EBS volume. Encryption uses KMS keys and has negligible latency impact.
* **Cross-AZ Migration using Snapshots:** Because EBS volumes are zone-locked, to move data between AZs:
  1. Take a **Snapshot** from the volume in AZ `ap-south-1a` (snapshots are region-wide, not AZ-locked).
  2. Create a new **EBS Volume** from that snapshot in the target AZ (`ap-south-1b`).
  3. Attach the new volume to an EC2 instance in `ap-south-1b`.
* **Nitro System Block Device Mapping:** On modern AWS Nitro-based instances, EBS volumes attached as `/dev/sdb` through the AWS Console are automatically mapped by the Linux kernel to virtual NVMe device names (e.g., `/dev/nvme1n1`).

> [!TIP]
> Use this same pattern (`Volume → Snapshot → Volume`) whenever you need to migrate data between Availability Zones or create a backup copy of your application data.

### 2. Architecture & Data Flow Diagrams

#### System Architecture
Shows the physical and virtual boundary between local compute host storage (Instance Store) and network-attached persistent storage (EBS), showing Availability Zone isolation.

```mermaid
flowchart TB
    subgraph Region ["AWS Mumbai Region (ap-south-1)"]
        subgraph AZ_A ["Availability Zone A (ap-south-1a)"]
            subgraph EC2_Host ["Physical Host Server"]
                subgraph VM ["EC2 Instance (Virtual Machine)"]
                    OS["Operating System Layer<br/>(Filesystem: ext4/xfs)"]
                    IS_Mount["/mnt/instance-store<br/>(Ephemeral Mount)"]
                end
                Hypervisor["Nitro Hypervisor / Host Card"]
                Local_SSD[("Instance Store<br/>(Local NVMe SSD)<br/>Ephemeral & Ultra-fast")]
            end
            
            EBS_Vol[("EBS Volume<br/>(Persistent Block Storage)<br/>AZ-Locked")]
            KMS["AWS KMS<br/>(AES-256 Keys)"]
        end
        
        AZ_B["Availability Zone B (ap-south-1b)"]
        S3_Bucket[("Amazon S3 Bucket<br/>(Regional Snapshot Store)<br/>11 Nines Durability")]
    end

    %% Connections
    OS -->|Local IO| IS_Mount
    IS_Mount -->|Direct Path| Local_SSD
    OS -->|Mount Point: /dataofVolume| Hypervisor
    Hypervisor -->|AWS NVMe Protocol / Network fabric| EBS_Vol
    EBS_Vol -.->|KMS Keys| KMS
    EBS_Vol -->|Incremental Backups| S3_Bucket
    S3_Bucket -.->|Restore Volume across AZ| AZ_B

    %% Styling
    classDef ec2 fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef ebs fill:#0369A1,stroke:#0EA5E9,color:#FFFFFF,stroke-width:2px;
    classDef host fill:#334155,stroke:#475569,color:#F1F5F9,stroke-width:2px;
    classDef local fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef regional fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef key fill:#581C87,stroke:#A855F7,color:#FFFFFF,stroke-width:2px;
    
    class VM,OS,IS_Mount ec2;
    class EBS_Vol ebs;
    class EC2_Host,Hypervisor host;
    class Local_SSD local;
    class S3_Bucket,AZ_B regional;
    class KMS key;
```

#### Service Relationships
Visualizes how EBS orchestrates and communicates with key AWS services.

```mermaid
graph TD
    IAM["AWS IAM<br/>(Control Access Policies)"]
    EC2["Amazon EC2 Instance<br/>(Compute Host)"]
    EBS["EBS Volume<br/>(Block Storage)"]
    KMS["AWS KMS Key<br/>(Envelope Encryption)"]
    S3["Amazon S3<br/>(Snapshot Storage)"]
    CW["Amazon CloudWatch<br/>(Performance Metrics)"]

    IAM -->|Authorizes: Attach/Detach| EC2
    IAM -->|Authorizes: Create/Delete| EBS
    EC2 <-->|Attaches block device| EBS
    EBS -->|Secured by AES-256| KMS
    EBS -->|Creates point-in-time Snapshot| S3
    S3 -->|Restores new volume| EBS
    EBS -->|Pushes IOPS/Throughput metrics| CW

    classDef service fill:#0F172A,stroke:#64748B,color:#F1F5F9,stroke-width:2px;
    classDef iam fill:#581C87,stroke:#D8B4FE,color:#FFFFFF,stroke-width:2px;
    classDef compute fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef storage fill:#0369A1,stroke:#0EA5E9,color:#FFFFFF,stroke-width:2px;
    classDef security fill:#991B1B,stroke:#F87171,color:#FFFFFF,stroke-width:2px;
    classDef monitor fill:#075985,stroke:#38BDF8,color:#FFFFFF,stroke-width:2px;

    class IAM iam;
    class EC2 compute;
    class EBS storage;
    class KMS security;
    class S3 storage;
    class CW monitor;
```

#### Data Flow
Shows the vertical data flow from a Java application down to the physical EBS volume SAN, as well as the backup and recovery flow.

```mermaid
sequenceDiagram
    autonumber
    actor App as Java App (JVM)
    participant OS as EC2 OS (Kernel VFS)
    participant Driver as NVMe / Xen Driver
    participant Hypervisor as AWS Nitro Hypervisor
    participant KMS as AWS KMS
    participant EBS as EBS Block Storage (SAN)
    participant S3 as Amazon S3 (Snapshots)

    %% Flow 1: I/O Write Path
    Note over App, EBS: I/O Write Path Flow
    App->>OS: Write file chunk to /dataofVolume/file.txt
    OS->>OS: Page Cache check & Journal write (ext4)
    OS->>Driver: Translate to block-level write command (/dev/nvme1n1)
    Driver->>Hypervisor: Send blocks via virtual PCIe bus
    Hypervisor->>KMS: Decrypt Data Key (if encrypted volume)
    KMS-->>Hypervisor: Plaintext Data Key
    Hypervisor->>Hypervisor: Encrypt block payload (AES-256)
    Hypervisor->>EBS: Stream encrypted blocks over network fabric
    EBS-->>OS: Write Acknowledged (Durable in AZ)

    %% Flow 2: Backup and Copy Flow
    Note over EBS, S3: Snapshot Backup & Cross-AZ Restore Flow
    EBS->>S3: Backup: Create Snapshot (Copies changed blocks to S3)
    Note over S3: Snapshot stored regionally in S3 (11 Nines Durability)
    S3->>EBS: Restore: Create Volume from Snapshot in target AZ (ap-south-1b)
```

#### Cross-AZ Volume Migration Flow (Volume → Snapshot → Volume)
Illustrates how to migrate data across Availability Zones using a Point-in-time Snapshot, bypassing the AZ-lock limitation of EBS volumes.

```mermaid
flowchart TD
    subgraph Region ["AWS Mumbai Region (ap-south-1)"]
        subgraph AZ_A ["Availability Zone: ap-south-1a"]
            EC2_A["EC2 Instance A<br/>(Source VM)"]
            Vol_A[("EBS Volume A<br/>(AZ-Locked Disk)")]
            EC2_A <-->|Attached| Vol_A
        end

        subgraph S3_Reg ["Regional Backup Store (Amazon S3)"]
            Snap_A[("EBS Snapshot A<br/>(Point-in-time Backup)<br/>Region-Wide Access")]
        end

        subgraph AZ_B ["Availability Zone: ap-south-1b"]
            EC2_B["EC2 Instance B<br/>(Target VM)"]
            Vol_B[("EBS Volume B<br/>(AZ-Locked Disk)")]
            EC2_B <-->|Attached| Vol_B
        end
    end

    %% Migration Actions
    Vol_A -->|1. Create Snapshot| Snap_A
    Snap_A -->|2. Create Volume in Target AZ| Vol_B

    %% Styling
    classDef ec2 fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef ebs fill:#0369A1,stroke:#0EA5E9,color:#FFFFFF,stroke-width:2px;
    classDef s3 fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    
    class EC2_A,EC2_B ec2;
    class Vol_A,Vol_B ebs;
    class Snap_A s3;
```

#### Deployment & Mounting Flow
Visualizes the sequence of commands needed to provision, format, and safely mount a block storage device.

```mermaid
flowchart TD
    Start(["1. Provision EBS Volume"]) --> Attach["2. Attach to EC2 Instance in same AZ"]
    Attach --> Login["3. SSH into EC2 Instance"]
    Login --> Lsblk["4. Run 'lsblk' <br/>Identify device name (e.g. /dev/sdb, /dev/nvme1n1)"]
    Lsblk --> CheckFS{"5. Run 'sudo file -s /dev/xxx'<br/>Does it have a File System?"}
    
    CheckFS -->|No - Raw Device| Format["6. Format Volume<br/>'sudo mkfs -t ext4 /dev/nvme1n1'"]
    CheckFS -->|Yes - Existing Data| Mkdir["7. Create Mount Directory<br/>'sudo mkdir /dataofVolume'"]
    
    Format --> Mkdir
    Mkdir --> Mount["8. Mount Volume<br/>'sudo mount /dev/nvme1n1 /dataofVolume'"]
    Mount --> Verify["9. Verify Mount<br/>'df -h' or write a test file"]
    Verify --> Persist["10. Add to '/etc/fstab' for Persistent Reboot Mount<br/>Use UUID to prevent device name drift"]
    Persist --> End(["Volume Ready for Production Use"])

    classDef step fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef decision fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef finish fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class Start,Attach,Login,Lsblk,Format,Mkdir,Mount,Verify,Persist step;
    class CheckFS decision;
    class End finish;
```

### 3. Interview Questions & Answers

#### Q: What is the difference between EBS and Instance Store?
**A:** 
* **EBS** is persistent network-attached storage. The data persists even if the EC2 instance is stopped or restarted.
* **Instance Store** is physical, ephemeral storage attached directly to the host machine. If the instance is stopped, terminated, or suffers a hardware crash, all data in the instance store is permanently lost.

#### Q: What happens to the EBS root volume when an EC2 instance is terminated?
**A:** By default, the root EBS volume is deleted upon instance termination (`DeleteOnTermination` attribute is set to `true`). Additional volumes attached to the instance are preserved by default (`DeleteOnTermination` is set to `false`). You can change this behavior via CLI, CloudFormation, or Console configurations to preserve root volumes.

#### Q: If your application requires 50,000 IOPS and low-latency database access, which EBS type and instance combination would you choose?
**A:** Use **io2 (Provisioned IOPS SSD)** volumes attached to **EBS-Optimized EC2 instances**. General Purpose SSDs (gp3) scale up to 16,000 IOPS, while io2 volumes can scale up to 256,000 IOPS. EBS-Optimized instances ensure dedicated network bandwidth between the EC2 compute instance and the EBS storage backend, preventing bandwidth contention with regular network traffic.

### 4. Linux Commands & Hands-on Guide

#### Raw Commands Walkthrough
```bash
# 1. List block storage devices to find the device name
lsblk

# 2. Check if a device has an existing filesystem (returns 'data' if empty/raw)
sudo file -s /dev/nvme1n1

# 3. Format the newly attached raw EBS volume with ext4
sudo mkfs -t ext4 /dev/nvme1n1

# 4. Create the target mount directory (mount point)
sudo mkdir /dataofVolume

# 5. Mount the formatted block device to the directory
sudo mount /dev/nvme1n1 /dataofVolume

# 6. Verify filesystem disk space usage and mount point status
df -h
lsblk

# 7. Configure /etc/fstab to persist the mount across system reboots.
# Step A: Find the UUID of the partition
sudo blkid /dev/nvme1n1

# Step B: Add UUID entry to /etc/fstab (Prevents mount failures if device names shift)
# Format: UUID=xxxx-xxxx /dataofVolume ext4 defaults,nofail 0 2
# Note: 'nofail' ensures instance boots successfully even if the EBS volume is missing or detached.
echo 'UUID=8be76228-4444-486d-bc11-a8e5781a7b45 /dataofVolume ext4 defaults,nofail 0 2' | sudo tee -a /etc/fstab

# 8. Create an EBS snapshot via AWS CLI
aws ec2 create-snapshot --volume-id vol-0abc123456789def0 --description "Backup before application patch"
```

### 5. Troubleshooting & Linux Storage Fundamentals (Teachable Moments)

Analyzing the command history of an engineer formatting and mounting an EBS volume highlights key misconceptions about Linux filesystems and AWS instance block storage mapping.

#### Pitfall 1: Trying to `cd` into a block device (`cd /dev/sdb` or `cd /sdb`)
* **Error:** `bash: cd: /dev/sdb: Not a directory`
* **Root Cause:** In Linux, block devices (like `/dev/sdb` or `/dev/nvme1n1`) are represented as **device special files**, not directories. They represent raw hardware access streams. You cannot navigate (`cd`), read (`cat`), or write (`touch`) directly inside them.
* **Correction:** You must create an empty directory (called a **mount point**, e.g., `/dataofVolume`) and **mount** the device to that directory. The mount operation links the device's formatted filesystem structure to the directory tree.

```bash
# WRONG (Will error):
cd /dev/sdb

# CORRECT:
sudo mkdir -p /dataofVolume
sudo mount /dev/nvme1n1 /dataofVolume
cd /dataofVolume
```

#### Pitfall 2: Syntax Errors during Mount Point Creation (`sudo mkdir/dataofVolume`)
* **Error:** `sudo: mkdir/dataofVolume: command not found`
* **Root Cause:** In bash/powershell, command names and arguments must be separated by a space. The shell treats `mkdir/dataofVolume` as a single program name, which does not exist.
* **Correction:** Ensure a space is present: `sudo mkdir /dataofVolume`.

#### Pitfall 3: Device Mapping Drift on AWS Nitro Instances
* **Observation:** An engineer attaches a volume as `/dev/sdb` in the AWS Console, but fails to mount it: `sudo mount /dev/sdb /dataofVolume` fails because the device does not exist under that name.
* **Root Cause:** Modern AWS EC2 instances built on the **Nitro System** (such as `t3`, `c5`, `m5`, `r5` instances) expose all block storage devices as **NVMe devices**. The Linux kernel names them using the `/dev/nvmeXn1` naming convention instead of legacy `/dev/sdX` or `/dev/xvdX`.
  * AWS Console `/dev/sdb` maps to `/dev/nvme1n1`.
  * AWS Console `/dev/sdf` maps to `/dev/nvme2n1`.
* **Correction:** Run `lsblk` to identify the correct device block file name (e.g., `nvme1n1`), format it as `/dev/nvme1n1`, and mount it.

```bash
# Check names:
lsblk

# Expected Output:
# NAME          MAJ:MIN RM  SIZE RO TYPE MOUNTPOINTS
# nvme0n1       259:0    0    8G  0 disk 
# └─nvme0n1p1   259:1    0    8G  0 part /
# nvme1n1       259:2    0   10G  0 disk   <-- This is your attached EBS volume!

# Format and mount using the ACTUAL NVMe device path:
sudo mkfs -t ext4 /dev/nvme1n1
sudo mount /dev/nvme1n1 /dataofVolume
```

### 6. EBS + Kubernetes Integration (From Class Board Work)

> **From Class Board Work:** The instructor showed how Kubernetes pods use Persistent Volume Claims (PVC) backed by AWS EBS volumes — a critical real-world pattern for stateful applications on EKS.

#### How Kubernetes Uses EBS for Persistent Storage

When you run stateful applications in Kubernetes (databases, message queues, caches) on EKS, containers need persistent disk that survives pod restarts. This is where EBS integrates with Kubernetes.

```mermaid
flowchart TB
    subgraph K8S ["☸️ Kubernetes Cluster (AWS EKS)"]
        subgraph POD_GROUP ["Application Pods"]
            P1["📦 Pod 1\n(MySQL)"]
            P2["📦 Pod 2\n(App Server)"]
            P3["📦 Pod 3\n(Kafka)"]
            P4["📦 Pod 4\n(New Pod)"]
        end
        
        PVC["📋 PersistentVolumeClaim (PVC)\nRequested: 100GB, ReadWriteOnce"]
        PV["🗂️ PersistentVolume (PV)\nBound: 100GB EBS Volume"]
    end

    subgraph AWS_CLOUD ["☁️ AWS Cloud"]
        EBS_VOL[("💾 EBS Volume\n(100 GB gp3 SSD)\nAZ: ap-south-1a")]
    end

    P1 -->|"Request storage\nvia PVC"| PVC
    PVC -->|"Kubernetes binds\nPVC ↔ PV"| PV
    PV -->|"AWS EBS CSI Driver\nmounts volume"| EBS_VOL
    P4 -->|"Pod rescheduled\nReattaches to same PVC"| PVC

    classDef pod fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef k8s fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef aws fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    class P1,P2,P3,P4 pod;
    class PVC,PV k8s;
    class EBS_VOL aws;
```

#### Key K8S + EBS Concepts

| K8S Concept | Role | AWS Equivalent |
|:---|:---|:---|
| **PVC (PersistentVolumeClaim)** | Pod's request for storage (how much, what type) | Like an order form for an EBS volume |
| **PV (PersistentVolume)** | Actual storage resource provisioned | The EBS volume itself (100GB) |
| **StorageClass** | Defines the type of storage to provision | `gp2`, `gp3`, `io1` EBS types |
| **AWS EBS CSI Driver** | Kubernetes plugin that provisions EBS automatically | Manages attach/detach of EBS volumes to nodes |

#### StorageClass YAML for EBS (gp3)

```yaml
apiVersion: storage.k8s.io/v1
kind: StorageClass
metadata:
  name: ebs-gp3
provisioner: ebs.csi.aws.com
parameters:
  type: gp3
  iops: "3000"
  throughput: "125"
volumeBindingMode: WaitForFirstConsumer  # Only provision when a pod actually requests it
reclaimPolicy: Retain                    # Keep EBS volume even after PVC is deleted
```

#### PVC YAML (Pod requests 100GB of EBS storage)

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: mysql-data-pvc
spec:
  accessModes:
    - ReadWriteOnce    # EBS: only one node can mount at a time
  storageClassName: ebs-gp3
  resources:
    requests:
      storage: 100Gi
```

> [!IMPORTANT]
> EBS volumes support `ReadWriteOnce` (one node at a time). If you need multiple pods across different nodes to share storage simultaneously, use **EFS (Elastic File System)** with `ReadWriteMany` access mode.

---

### 7. Key Takeaways
* **EBS is network-attached** persistent storage, while **Instance Store is local, physical host storage** (ephemeral/non-persistent).
* **Availability Zone Locked:** Volumes must be in the same AZ as the EC2 instances they attach to. To migrate data between AZs, perform the snapshot lifecycle flow (`Volume → Snapshot → Volume`).
* **Nitro NVMe Mapping:** Modern EC2 instances rename console-attached device designations (e.g., `/dev/sdb`) to NVMe devices (`/dev/nvme1n1`).
* **Mounting Protocol:** Block devices are files, not directories. You must format them with a filesystem (`ext4`/`xfs`) and mount them to a directory to interact with their storage.
* **Kubernetes on EKS:** Use EBS CSI Driver + PVC/PV pattern for persistent storage for stateful pods. EBS = `ReadWriteOnce`; EFS = `ReadWriteMany`.

---

## TOPIC 5: S3 — SIMPLE STORAGE SERVICE

### 1. Concept Explanation

#### Beginner
Simple Storage Service (S3) is an object storage service designed to store and retrieve any amount of data via HTTP endpoints. Instead of block-level file directory structures, S3 stores files as objects inside container units called buckets.

Key Terminology:
* **Buckets:** Directory containers with a globally unique namespace across all AWS customers.
* **Objects:** Files (keys) containing data, metadata, and a version ID. Single objects range in size from 0 bytes up to 5 TB.
* **Durability:** Designed for 99.999999999% (11 nines) durability by replicating objects across a minimum of three physical AZs.

#### Intermediate
##### S3 Storage Classes
| Storage Class | Availability SLA | Minimum Duration | Use Case |
| :--- | :--- | :--- | :--- |
| **S3 Standard** | 99.99% | None | Frequently accessed files, active application files |
| **S3 Intelligent-Tiering** | 99.9% | None | Files with unknown or changing access patterns |
| **S3 Standard-IA** | 99.9% | 30 Days | Infrequently accessed files, monthly report archives |
| **S3 One Zone-IA** | 99.5% | 30 Days | Non-critical, reproducible files stored in a single AZ |
| **S3 Glacier Instant Retrieval**| 99.9% | 90 Days | Archival data requiring millisecond access times |
| **S3 Glacier Flexible Retrieval**| 99.99% (Vaults) | 90 Days | Archives with retrieval times ranging from minutes to 5 hours |
| **S3 Glacier Deep Archive** | 99.9% | 180 Days | Long-term compliance logs (retrieval time of 12 hours) |

* **Lifecycle Policies:** Automate data storage tier transitions (e.g., transition objects from Standard to Standard-IA after 30 days, then archive to Glacier after 90 days).
* **Versioning:** Keep multiple versions of an object in a bucket to protect against accidental overrides and deletions.
* **Pre-signed URLs:** Generate temporary access links valid for a custom duration (e.g., 15 minutes) to allow users to upload or download files directly from S3 securely.

#### Advanced
S3 access control utilizes multiple resource policy layers:
1. **IAM Policy:** User-level permission configurations.
2. **Bucket Policy:** Resource-level JSON policies attached directly to the bucket.
3. **Block Public Access:** Account-level block settings to prevent accidental exposure of buckets.

##### Direct S3 Upload Architecture
To prevent web servers from running out of network bandwidth and CPU when handling massive uploads (e.g., 2 GB files), the application server generates an **S3 Pre-signed URL** and returns it to the client. The client browser then uploads the file directly to S3 via HTTP PUT:

```mermaid
%%{init: {
  'theme': 'base',
  'themeVariables': {
    'primaryColor': '#4F46E5',
    'primaryTextColor': '#FFFFFF',
    'primaryBorderColor': '#C7D2FE',
    'lineColor': '#4F46E5',
    'actorBkg': '#4F46E5',
    'actorBorder': '#C7D2FE',
    'actorTextColor': '#FFFFFF',
    'actorLineColor': '#6366F1',
    'signalColor': '#4F46E5',
    'signalTextColor': '#6366F1',
    'labelBoxBkgColor': '#0F766E',
    'labelBoxBorderColor': '#99F6E4',
    'labelTextColor': '#FFFFFF',
    'loopTextColor': '#6366F1',
    'noteBkgColor': '#374151',
    'noteBorderColor': '#9CA3AF',
    'noteTextColor': '#FFFFFF',
    'activationBkgColor': '#0F766E',
    'activationBorderColor': '#99F6E4',
    'sequenceNumberColor': '#FFFFFF'
  }
} }%%
sequenceDiagram
    autonumber
    actor User as Client Browser
    participant App as Spring Boot App
    participant S3 as Amazon S3

    User->>App: Request upload token (Filename)
    App->>App: Validate user auth & scope
    App->>S3: Generate pre-signed PUT URL
    S3-->>App: Pre-signed URL (Valid for 15 min)
    App-->>User: Return Pre-signed URL
    User->>S3: HTTP PUT raw file to Pre-signed URL
    S3-->>User: 200 OK (Upload Complete)
```

### 2. Spring Boot Integration Examples

#### AWS SDK v2 (Recommended)
This snippet uses the modern AWS SDK v2, which automatically resolves credentials using the EC2 IAM Instance Profile when deployed in AWS.

```java
package com.company.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
public class S3FileService {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final String bucketName = "policy-documents";

    // Rely on Default Credentials Provider Chain (resolves IAM Role automatically)
    public S3FileService() {
        this.s3Client = S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .build();
        this.s3Presigner = S3Presigner.builder()
                .region(Region.AP_SOUTH_1)
                .build();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String key = UUID.randomUUID() + "/" + file.getOriginalFilename();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, 
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }

    public String generatePresignedUrl(String key, int expiryMinutes) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(expiryMinutes))
                .getObjectRequest(getObjectRequest)
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        return presignedRequest.url().toString();
    }
}
```

#### AWS SDK v1 (Legacy / Reference)
For legacy microservices using AWS SDK v1:
```java
package com.company.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class LegacyS3FileService {

    @Autowired 
    private AmazonS3 s3Client;
    private final String bucket = "policy-documents";

    public String uploadFile(MultipartFile file) throws IOException {
        String key = UUID.randomUUID() + "/" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        
        s3Client.putObject(bucket, key, file.getInputStream(), metadata);
        return s3Client.getUrl(bucket, key).toString();
    }

    public S3Object downloadFile(String key) {
        return s3Client.getObject(bucket, key);
    }
}
```

### 3. Interview Questions & Answers

#### Q: How do you configure a bucket for Static Website Hosting, and what permissions are required?
**A:** 
1. Enable **Static Website Hosting** under the S3 bucket properties and define the entry files (e.g., `index.html` and `error.html`).
2. Disable **Block Public Access** on the bucket.
3. Apply a public read bucket policy to allow public access:
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "PublicReadGetObject",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::your-bucket-name/*"
    }
  ]
}
```

### 4. CLI Commands

```bash
# List all buckets in the account
aws s3 ls

# Sync a local build directory to an S3 bucket (deleting removed local files in the target bucket)
aws s3 sync ./dist s3://my-static-web-bucket --delete

# Enable versioning on a bucket
aws s3api put-bucket-versioning --bucket my-app-bucket --versioning-configuration Status=Enabled
```

### 5. Key Takeaways
* S3 is a highly durable, globally-available object storage service.
* Storage Classes allow you to optimize storage costs based on file access frequency.
* Pre-signed URLs allow clients to perform secure uploads and downloads directly, reducing application server overhead.

---

## TOPIC 6: IAM — IDENTITY & ACCESS MANAGEMENT

### 1. Concept Explanation

#### Beginner
Identity & Access Management (IAM) controls authentication and authorization for users and services accessing AWS resources. IAM is a **free service** with no additional charges.

##### Two Ways to Access AWS Cloud
| Access Method | Description |
| :--- | :--- |
| **Root Account** | The most powerful AWS account (created with your email). Has unrestricted access to all AWS resources and services. **Enable MFA immediately and avoid using it for daily tasks.** |
| **IAM Account** | A user/service account created by the root user with specific, scoped permissions. Used by developers, CI/CD tools, and applications in team environments. |

> [!CAUTION]
> It is **highly recommended** to enable Multi-Factor Authentication (MFA) on the Root account and never create access keys for it. Use IAM accounts for all day-to-day operations.

Core Components:
* **Root Account:** The initial, all-powerful account created with an email address. Only use it for account-level billing tasks. Enable MFA immediately.
* **IAM Users:** Identities created within the account for developers or applications. Can access the AWS Console with username/password, and the CLI/API with Access Keys.
* **IAM Groups:** Collections of IAM Users. Assign policies to groups (e.g., `DevelopersGroup`, `DevOpsGroup`) rather than individual users for easier management.
  * *Example:* Group `Developers` with policy `AmazonEC2FullAccess` → every member of that group automatically gets EC2 access.
* **IAM Roles:** Temporary identities with no username or password. AWS services or users assume roles to gain temporary access credentials.
  * *Example:* An EC2 instance assumes a role that allows it to read data from an S3 bucket — no hardcoded keys needed.
* **Policies:** JSON documents defining what actions are allowed or denied on which AWS resources and under what conditions.

#### Intermediate
##### JSON Policy Structure Example
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "s3:GetObject",
        "s3:PutObject"
      ],
      "Resource": "arn:aws:s3:::policy-documents/*",
      "Condition": {
        "IpAddress": {
          "aws:SourceIp": "192.168.1.0/24"
        }
      }
    }
  ]
}
```

* **IAM Roles for AWS Services:** You assign an IAM Role to an EC2 instance profile. The AWS SDK running inside the instance automatically requests temporary credentials from the **EC2 Instance Metadata Service (IMDS)**, eliminating the need to store static AWS credentials on the server.

#### Advanced
##### IAM Policy Evaluation Engine
When an API request is evaluated:
1. **Explicit Deny:** If any policy statements match a `Deny` action, the request is immediately rejected.
2. **Explicit Allow:** If an `Allow` matches, the request is approved.
3. **Implicit Deny:** If there is no explicit `Allow`, the request is denied by default.

```mermaid
flowchart TD
    Root["Root User (MFA Enabled, No Access Keys)"]
    
    subgraph IAM ["IAM Directory"]
        Group["IAM Group (e.g. Developers)"]
        User["IAM User (e.g. haider)"]
        Role["IAM Role (e.g. EC2-S3-ReadOnly)"]
        Policy["IAM JSON Policy (Allows s3:GetObject)"]
    end

    User -->|Belongs to| Group
    Group -->|Attached Policy| Policy
    Role -->|Attached Policy| Policy
    Role -.->|Assumed by| EC2["EC2 / Lambda / Service"]

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef client fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    classDef mgmt fill:#581C87,stroke:#D8B4FE,color:#FFFFFF,stroke-width:2px;

    class Root client;
    class Group,User,Role,Policy mgmt;
    class EC2 layer;
```

### 2. Interview Questions & Answers

#### Q: What is the difference between IAM Users, Groups, and Roles?
**A:** 
* **Users** represent distinct users or applications with long-term credentials (passwords, access keys).
* **Groups** are logical collections of users used to manage permission structures collectively.
* **Roles** are assigned to resources or federated users to generate temporary access credentials. Roles are assumed dynamically and do not have static passwords or access keys.

#### Q (Scenario): A developer accidentally commits AWS Access Keys to a public GitHub repository. How do you respond?
**A:** 
1. Log into the AWS Console immediately and delete or deactivate the compromised Access Key.
2. Review **AWS CloudTrail** logs to audit all API actions performed using that Access Key.
3. Terminate any unauthorized resources launched during the compromise.
4. Purge the credentials from the Git history.
5. Deploy automated Git credential scanners (like git-secrets or GitHub secret scanning) to prevent future leaks.

### 3. Key Takeaways
* Follow the Principle of Least Privilege: only grant the permissions required for a specific task.
* Use IAM Roles with temporary credentials for applications and AWS services instead of permanent access keys.
* Deny statements always take precedence over Allow statements in policy evaluation.



---

## IAM COMPREHENSIVE DEEP DIVE

### IAM-1: Root Account

#### What is the Root Account?
The Root Account is the initial AWS account created when you first sign up using an email address and password. It has **complete, unrestricted access** to every AWS service and resource in the account — including billing, account closure, and IAM management.

#### Why the Root Account Should Rarely Be Used

> [!CAUTION]
> If the Root account is compromised, an attacker has **full control** over your entire AWS account — including the ability to delete all resources, run up massive bills, lock out all IAM users, and transfer billing to a different account.

**Root Account Exclusive Tasks (Only Root Can Do These):**

| Task | Why Only Root? |
| :--- | :--- |
| Change the AWS account email address | Account identity management |
| Change the AWS support plan | Billing-level operation |
| Close (delete) the AWS account | Irreversible destructive action |
| Enable IAM access to billing console | Security boundary |
| Restore a suspended account | Account administration |
| Register as a seller in AWS Marketplace | Financial commitment |
| Configure GovCloud linkage | Compliance requirement |

**Best Practices for Root Account:**
- Enable **MFA immediately** after account creation
- Use a **strong, unique password** stored in a password manager
- **Never create Access Keys** for the root account
- Store root credentials in a secure physical safe
- Set up **billing alerts** and a budget threshold alarm
- Enable **CloudTrail** to log all root account activity
- Never use root for daily administration, development, or CI/CD

**Real-world Example:**
> A startup founder creates an AWS account, enables MFA on root, then immediately creates an IAM user named `admin` with `AdministratorAccess` policy for daily use. Root credentials are stored in a physical safe and the root account is only accessed once per year to review billing settings.

---

### IAM-2: IAM Users

#### What Are IAM Users?
IAM Users are identities created **within an AWS account** for individual people, applications, or services that need AWS access. Each IAM user has its own set of credentials independent of other users.

#### Authentication Methods

| Authentication Type | Method | Used For |
| :--- | :--- | :--- |
| **Console Login** | Username + Password (+ optional MFA) | AWS Management Console access |
| **Programmatic Access** | Access Key ID + Secret Access Key | AWS CLI, SDK, API calls |
| **SSH Public Key** | Uploaded SSH public key | AWS CodeCommit Git operations |

#### IAM User Characteristics

| Property | Details |
| :--- | :--- |
| **Max Users per Account** | 5,000 IAM users per AWS account |
| **Max Groups per User** | A user can belong to up to 10 groups |
| **Credentials** | Long-term (do not expire unless rotated manually) |
| **MFA** | Optional per-user (can be enforced via IAM policy conditions) |

#### Console Access Setup
```bash
# Create IAM user
aws iam create-user --user-name john-dev

# Create login profile (console password)
aws iam create-login-profile \
  --user-name john-dev \
  --password "Str0ng!Pass#2024" \
  --password-reset-required
```

#### Access Keys for CLI/API Access
```bash
# Create access keys (save SecretAccessKey immediately - shown only once!)
aws iam create-access-key --user-name john-dev

# Deactivate a compromised access key
aws iam update-access-key --user-name john-dev \
  --access-key-id AKIAIOSFODNN7EXAMPLE \
  --status Inactive

# Delete an old access key
aws iam delete-access-key --user-name john-dev \
  --access-key-id AKIAIOSFODNN7EXAMPLE
```

> [!WARNING]
> The **Secret Access Key** is shown **only once** at creation time. If you lose it, you must delete the access key and create a new one. There is no way to retrieve it from AWS.

#### Password Policies
```bash
aws iam update-account-password-policy \
  --minimum-password-length 12 \
  --require-symbols \
  --require-numbers \
  --require-uppercase-characters \
  --require-lowercase-characters \
  --allow-users-to-change-password \
  --max-password-age 90 \
  --password-reuse-prevention 5
```

| Password Policy Option | Recommended Setting |
| :--- | :--- |
| Minimum length | 12+ characters |
| Require uppercase + lowercase + numbers + symbols | Yes |
| Password expiry | 90 days |
| Prevent password reuse | Last 5 passwords |

**Best Practices for IAM Users:**
- Create individual users per person (no shared accounts)
- Enforce MFA for all human users with console access
- Rotate access keys every 90 days
- Use IAM Roles instead of access keys wherever possible (Lambda, EC2, ECS)
- Regularly audit unused users with **IAM Credential Reports**

---

### IAM-3: IAM Groups

#### What Are IAM Groups?
IAM Groups are **collections of IAM users** that allow you to manage permissions for multiple users at once.

> [!NOTE]
> IAM Groups are **NOT** identities that can assume roles or be referenced in resource policies as principals. They are purely a management convenience for organizing users.

#### Advantages of Using Groups

| Benefit | Explanation |
| :--- | :--- |
| **Scalability** | Add a new developer to `DevGroup` — they instantly inherit all developer permissions |
| **Consistency** | All developers get identical permissions without manual per-user policy management |
| **Easy Revocation** | Remove a user from a group — their permissions are instantly revoked |
| **Separation of Duties** | `ReadOnlyGroup`, `DeveloperGroup`, `DevOpsGroup`, `AdminGroup` clearly separate access levels |

#### Real-world Group Structure Example

```
AWS Account
+-- AdminGroup               --> AdministratorAccess
+-- DevOpsGroup              --> AmazonEC2FullAccess + AmazonECRFullAccess + CloudWatchFullAccess
+-- DeveloperGroup           --> AmazonEC2ReadOnlyAccess + AWSLambdaFullAccess
+-- SecurityGroup            --> SecurityAudit + IAMReadOnlyAccess
+-- DataEngineerGroup        --> AmazonAthenaFullAccess + AmazonS3FullAccess
+-- ReadOnlyGroup            --> ReadOnlyAccess
```

```bash
# Create a group
aws iam create-group --group-name DevOpsGroup

# Attach a managed policy to the group
aws iam attach-group-policy \
  --group-name DevOpsGroup \
  --policy-arn arn:aws:iam::aws:policy/AmazonEC2FullAccess

# Add a user to the group
aws iam add-user-to-group --group-name DevOpsGroup --user-name john-dev
```

---

### IAM-4: IAM Roles — Complete Guide

#### What Are IAM Roles?

IAM Roles are **temporary identity credentials** that can be assumed by:
- AWS services (EC2, Lambda, ECS, etc.)
- IAM users in the same or different accounts
- Federated users (SAML, OIDC, corporate IdP)
- Web identity users (Google, Facebook, Amazon Cognito)

Roles have **no permanent username or password**. They issue **temporary security credentials** via **AWS Security Token Service (STS)**.

#### Why Roles Are Preferred Over Access Keys

| Criterion | Access Keys (IAM User) | IAM Role |
| :--- | :--- | :--- |
| **Credential type** | Long-term (never expire by default) | Short-term (expire in 15 min to 36 hours) |
| **Storage risk** | Must be stored somewhere | Never stored — injected dynamically by AWS |
| **Rotation** | Manual rotation required | Rotated automatically |
| **Breach impact** | Attacker has permanent access until deleted | Credentials expire automatically |
| **Suitable for** | Human users (sparingly), CI/CD bots | EC2, Lambda, ECS, EKS, cross-account |

#### How IAM Roles Work — Trust Policy + Permission Policy

Every IAM Role has two essential policy components:

**1. Trust Policy (Who Can Assume This Role?)**
```json
{
  "Version": "2012-10-17",
  "Statement": [{
    "Effect": "Allow",
    "Principal": {"Service": "ec2.amazonaws.com"},
    "Action": "sts:AssumeRole"
  }]
}
```

**2. Permission Policy (What Can the Role Do?)**
```json
{
  "Version": "2012-10-17",
  "Statement": [{
    "Effect": "Allow",
    "Action": ["s3:GetObject", "s3:PutObject", "s3:ListBucket"],
    "Resource": [
      "arn:aws:s3:::my-app-bucket",
      "arn:aws:s3:::my-app-bucket/*"
    ]
  }]
}
```

#### IAM Role AssumeRole Flow

```mermaid
sequenceDiagram
    autonumber
    participant Service as AWS Service (EC2/Lambda)
    participant STS as AWS STS (Security Token Service)
    participant IAM as AWS IAM (Trust Policy Check)
    participant Resource as Target Resource (S3/DynamoDB)

    Service->>STS: AssumeRole request (RoleArn)
    STS->>IAM: Validate Trust Policy
    IAM-->>STS: Trust Policy Evaluation Result
    alt Trust Policy Allows
        STS-->>Service: Temporary Credentials (TTL: 1hr default)
        Service->>Resource: API Call with Temporary Credentials
        Resource->>IAM: Evaluate Permission Policy for Role
        IAM-->>Resource: Allow or Deny
        Resource-->>Service: API Response (200 OK or 403 Forbidden)
    else Trust Policy Denies
        STS-->>Service: AccessDenied (403)
    end
```

#### AWS STS — Security Token Service

| STS API | Description | Max Duration |
| :--- | :--- | :--- |
| `AssumeRole` | Assume an IAM role (cross or same account) | 15 min to 12 hours |
| `AssumeRoleWithWebIdentity` | Assume role via OIDC (GitHub Actions, Cognito) | 15 min to 12 hours |
| `AssumeRoleWithSAML` | Assume role via SAML federation (AD, Okta) | 15 min to 12 hours |
| `GetSessionToken` | Get temporary credentials for MFA-protected IAM user | 15 min to 36 hours |
| `GetFederationToken` | Get credentials for federated users | 15 min to 36 hours |

**AWS STS Temporary Credential Flow:**

```mermaid
flowchart TD
    subgraph Caller["Caller (EC2 Instance / Lambda / CI-CD)"]
        App["Application Code"]
    end

    subgraph STS_Flow["AWS STS Credential Lifecycle"]
        Request["1. AssumeRole API Call\nRoleArn + optional ExternalId"]
        Validate["2. STS validates Trust Policy\nIs caller a trusted principal?"]
        Issue["3. STS Issues Temporary Credentials\nAccessKeyId + SecretKey + SessionToken\nExpires: 1 hour by default"]
        Cache["4. SDK caches credentials\nAuto-refreshes before expiry"]
    end

    subgraph IMDS["EC2 Metadata Service"]
        Meta["http://169.254.169.254/latest/meta-data/\niam/security-credentials/RoleName"]
    end

    App -->|AssumeRole| Request
    Request --> Validate
    Validate -->|Trust Policy OK| Issue
    Issue -->|Injected via| IMDS
    IMDS -->|SDK auto-fetches| Cache
    Cache -->|Signed API calls| AWS["AWS Services (S3, DynamoDB, etc.)"]

    classDef step fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef sts fill:#581C87,stroke:#A855F7,color:#FFFFFF,stroke-width:2px;
    classDef meta fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class App,Request step;
    class Validate,Issue,Cache sts;
    class Meta meta;
    class AWS step;
```

---

#### EC2 Instance Roles

The most common use of IAM Roles in DevOps. Instead of hardcoding AWS credentials into your application or EC2 instance, you attach an IAM Role to the instance.

**EC2 Instance Role Architecture:**

```mermaid
flowchart LR
    subgraph EC2["EC2 Instance"]
        App["Java/Spring Boot Application"]
        SDK["AWS SDK v2"]
        IMDS["IMDS\nhttp://169.254.169.254\n(Link-local, non-routable)"]
    end

    subgraph Role["IAM Instance Role"]
        TP["Trust Policy:\nPrincipal: ec2.amazonaws.com"]
        PP["Permission Policy:\ns3:GetObject, s3:PutObject\nsecretsmanager:GetSecretValue"]
    end

    STS["AWS STS"]
    S3["Amazon S3"]
    SM["Secrets Manager"]

    App -->|1. S3 API call| SDK
    SDK -->|2. No credentials locally| IMDS
    IMDS -->|3. Fetch temp creds via AssumeRole| STS
    STS -->|4. Temp creds - 1hr TTL| IMDS
    IMDS -->|5. Return creds to SDK| SDK
    SDK -->|6. Signed API call| S3
    SDK -->|6. Signed API call| SM

    classDef ec2 fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef role fill:#581C87,stroke:#A855F7,color:#FFFFFF,stroke-width:2px;
    classDef aws fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class App,SDK,IMDS ec2;
    class TP,PP role;
    class STS,S3,SM aws;
```

**Setting Up EC2 Instance Role:**
```bash
# Create the IAM role with EC2 trust policy
aws iam create-role \
  --role-name EC2-S3-ReadRole \
  --assume-role-policy-document '{"Version":"2012-10-17","Statement":[{"Effect":"Allow","Principal":{"Service":"ec2.amazonaws.com"},"Action":"sts:AssumeRole"}]}'

# Attach the permission policy
aws iam attach-role-policy \
  --role-name EC2-S3-ReadRole \
  --policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess

# Create instance profile and add role
aws iam create-instance-profile --instance-profile-name EC2-S3-ReadProfile
aws iam add-role-to-instance-profile \
  --instance-profile-name EC2-S3-ReadProfile \
  --role-name EC2-S3-ReadRole

# Verify credentials on the EC2 instance:
curl http://169.254.169.254/latest/meta-data/iam/security-credentials/EC2-S3-ReadRole
```

---

#### Lambda Execution Roles

Every Lambda function **must** have an IAM Role for:
1. Writing execution logs to CloudWatch Logs
2. Accessing any AWS resources the function needs (S3, DynamoDB, SQS, etc.)

**Lambda Execution Role Architecture:**

```mermaid
flowchart TD
    subgraph Trigger["Event Source"]
        SQS["SQS Queue"]
        API["API Gateway"]
        S3E["S3 Event"]
    end

    subgraph Lambda["AWS Lambda"]
        Func["Function Code\n(Java/Python/Node.js)"]
        Role["Execution Role\n(IAM Role)"]
    end

    subgraph Perms["Role Permissions"]
        CW["CloudWatch Logs\nCreateLogGroup, CreateLogStream, PutLogEvents"]
        S3P["Amazon S3\ns3:GetObject, s3:PutObject"]
        DDB["DynamoDB\ndynamodb:PutItem, dynamodb:GetItem"]
        VPC["VPC Access\nec2:CreateNetworkInterface"]
    end

    Trigger -->|Invokes| Func
    Func -->|Assumes| Role
    Role -->|Grants| CW & S3P & DDB & VPC

    classDef trigger fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef lambda fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef perm fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class SQS,API,S3E trigger;
    class Func,Role lambda;
    class CW,S3P,DDB,VPC perm;
```

> [!NOTE]
> AWS provides the `AWSLambdaBasicExecutionRole` managed policy which covers CloudWatch Logs access. For Lambda functions inside a VPC, also attach `AWSLambdaVPCAccessExecutionRole`.

---

#### ECS Task Roles & EKS Roles

**ECS (Elastic Container Service):**
- **ECS Task Execution Role** — Used by ECS agent to pull Docker images from ECR and push logs to CloudWatch
- **ECS Task Role** — Used by the application code running inside the container

**EKS (Elastic Kubernetes Service):**
EKS uses **IRSA (IAM Roles for Service Accounts)** — each Kubernetes service account maps to an IAM role via OIDC federation.

```bash
# Create OIDC provider for EKS cluster
eksctl utils associate-iam-oidc-provider --cluster my-cluster --approve

# Create IAM role for service account
eksctl create iamserviceaccount \
  --name my-service-account \
  --namespace default \
  --cluster my-cluster \
  --attach-policy-arn arn:aws:iam::aws:policy/AmazonS3ReadOnlyAccess \
  --approve
```

---

#### Cross-Account Roles

Cross-account roles allow IAM users/services in **Account A** to access resources in **Account B** without sharing credentials.

**Cross-Account Role Flow:**

```mermaid
flowchart LR
    subgraph AccountA["Account A (Dev) - 123456789012"]
        DevUser["Developer IAM User\ndev-user"]
        AssumeAction["aws sts assume-role\n--role-arn arn:aws:iam::999::role/ProdReadOnly"]
    end

    subgraph STS_Node["AWS STS"]
        STSCheck["Validates:\n1. Dev user has sts:AssumeRole permission\n2. Role Trust Policy allows Account A"]
        TempCreds["Issues Temp Credentials\n(1 hour TTL)"]
    end

    subgraph AccountB["Account B (Prod) - 999999999999"]
        CrossRole["IAM Role: ProdReadOnly\nTrust Policy: Allows Account A\nPermission: AmazonS3ReadOnlyAccess"]
        ProdS3["Production S3 Bucket"]
    end

    DevUser -->|1. AssumeRole request| STSCheck
    STSCheck -->|2. Both validations pass| TempCreds
    TempCreds -->|3. Temp creds returned| AssumeAction
    AssumeAction -->|4. Access prod resources| ProdS3

    classDef accountA fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef sts fill:#581C87,stroke:#A855F7,color:#FFFFFF,stroke-width:2px;
    classDef accountB fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class DevUser,AssumeAction accountA;
    class STSCheck,TempCreds sts;
    class CrossRole,ProdS3 accountB;
```

```bash
# In Account B: Create role with trust policy allowing Account A
aws iam create-role \
  --role-name ProdReadOnly \
  --assume-role-policy-document '{
    "Version": "2012-10-17",
    "Statement": [{
      "Effect": "Allow",
      "Principal": {"AWS": "arn:aws:iam::123456789012:root"},
      "Action": "sts:AssumeRole",
      "Condition": {
        "StringEquals": {"sts:ExternalId": "my-unique-external-id-12345"}
      }
    }]
  }'

# In Account A: Assume the role
aws sts assume-role \
  --role-arn arn:aws:iam::999999999999:role/ProdReadOnly \
  --role-session-name DevCrossAccountSession \
  --external-id my-unique-external-id-12345
```

> [!TIP]
> Always use an **ExternalId** condition in cross-account trust policies to prevent the **Confused Deputy Problem** — where a malicious third party tricks a service into using its elevated permissions on your behalf.

---

#### Service-Linked Roles

Service-Linked Roles (SLRs) are IAM roles **pre-defined by AWS services** for their own use. You cannot modify their trust policies.

| Service | Service-Linked Role | Purpose |
| :--- | :--- | :--- |
| Elastic Load Balancing | `AWSServiceRoleForElasticLoadBalancing` | Manage EC2 target registrations |
| Auto Scaling | `AWSServiceRoleForAutoScaling` | Launch/terminate EC2 instances |
| Amazon RDS | `AWSServiceRoleForRDS` | Manage RDS maintenance |
| Amazon EKS | `AWSServiceRoleForAmazonEKS` | Manage EKS cluster resources |

---

#### Role Chaining

Role Chaining is when **Assumed Role credentials are used to assume another role**:
```
User --> assumes Role A --> Role A assumes Role B --> Role B assumes Role C
```

> [!WARNING]
> Role chaining has a maximum session duration of **1 hour** regardless of individual role max duration settings.

---

#### Federation

Federation allows external identity providers to authenticate users and map them to IAM Roles.

| Federation Type | Use Case | Protocol |
| :--- | :--- | :--- |
| **SAML 2.0** | Corporate AD/Okta/ADFS login to AWS Console | SAML assertions |
| **OIDC** | GitHub Actions, Bitbucket, GitLab CI to AWS | JWT tokens |
| **Amazon Cognito** | Mobile/web app user pools to AWS resources | Cognito tokens |

**GitHub Actions OIDC Federation Example:**
```yaml
jobs:
  deploy:
    permissions:
      id-token: write
      contents: read
    steps:
      - name: Configure AWS Credentials
        uses: aws-actions/configure-aws-credentials@v4
        with:
          role-to-assume: arn:aws:iam::123456789012:role/GitHubActionsRole
          aws-region: ap-south-1
      - name: Deploy to S3
        run: aws s3 sync ./dist s3://my-deploy-bucket
```

---

### IAM-5: IAM Policies — Complete Guide

#### Policy Types

| Policy Type | Managed By | Scope | Best For |
| :--- | :--- | :--- | :--- |
| **AWS Managed Policies** | AWS | Account-wide reusable | Common permissions (ReadOnly, FullAccess) |
| **Customer Managed Policies** | You | Account-wide reusable | Custom permission sets for your org |
| **Inline Policies** | You | Embedded in single entity | Strict 1:1 permission to entity relationship |

**AWS Managed Policy Examples:**

| Policy Name | Grants Access To |
| :--- | :--- |
| `AdministratorAccess` | Full access to all AWS services |
| `ReadOnlyAccess` | Read-only access to all AWS services |
| `AmazonEC2FullAccess` | Full EC2 management |
| `AmazonS3ReadOnlyAccess` | Read-only S3 access |
| `AWSLambdaFullAccess` | Full Lambda management |
| `SecurityAudit` | Read-only access to security configurations |

#### JSON Policy Structure — Full Reference

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "AllowS3ReadOnSpecificBucket",
      "Effect": "Allow",
      "Action": [
        "s3:GetObject",
        "s3:ListBucket",
        "s3:GetBucketLocation"
      ],
      "Resource": [
        "arn:aws:s3:::my-app-bucket",
        "arn:aws:s3:::my-app-bucket/*"
      ],
      "Condition": {
        "IpAddress": {
          "aws:SourceIp": ["203.0.113.0/24"]
        },
        "Bool": {
          "aws:MultiFactorAuthPresent": "true"
        }
      }
    },
    {
      "Sid": "DenyDeleteOperations",
      "Effect": "Deny",
      "Action": "s3:DeleteObject",
      "Resource": "arn:aws:s3:::my-app-bucket/*"
    }
  ]
}
```

**Policy Field Explanations:**

| Field | Required | Description |
| :--- | :---: | :--- |
| `Version` | Yes | Always use `"2012-10-17"` |
| `Statement` | Yes | Array of one or more permission statements |
| `Sid` | No | Statement ID — descriptive label |
| `Effect` | Yes | `"Allow"` or `"Deny"` |
| `Action` | Yes | AWS API action(s). Format: `"service:Action"` |
| `Resource` | Yes | ARN(s) of the resource(s) |
| `Principal` | Yes* | Required in resource-based policies |
| `Condition` | No | Optional conditions (IP range, MFA, time, tags) |

**Common Condition Operators:**

| Condition | Example | Use Case |
| :--- | :--- | :--- |
| `IpAddress` | `aws:SourceIp: "10.0.0.0/8"` | Allow only from corporate network |
| `Bool` | `aws:MultiFactorAuthPresent: "true"` | Require MFA for sensitive actions |
| `StringEquals` | `aws:RequestedRegion: "ap-south-1"` | Restrict to specific region |
| `DateLessThan` | `aws:CurrentTime: "2024-12-31T23:59:59Z"` | Time-limited access |
| `StringLike` | `s3:prefix: "home/${aws:username}/*"` | Dynamic per-user path restrictions |

#### Explicit Deny vs Implicit Deny

| Type | Definition | Example |
| :--- | :--- | :--- |
| **Explicit Deny** | A `Deny` statement exists that matches the request | `"Effect": "Deny", "Action": "s3:DeleteObject"` |
| **Implicit Deny** | No `Allow` statement matches the request — denied by default | No statement covers `s3:DeleteBucket` |

> [!IMPORTANT]
> **Explicit Deny always wins.** Even if another policy has an `Allow` for the same action and resource, an `Explicit Deny` overrides it completely.

#### IAM Policy Evaluation Flow

```mermaid
flowchart TD
    Start(["API Request Received"]) --> OrgSCP{"SCP from\nAWS Organizations Applied?"}
    OrgSCP -->|SCP Denies| DENY1(["DENY - Organization Policy"])
    OrgSCP -->|SCP Allows or No SCP| PermBound{"Permissions Boundary\nSet on Entity?"}
    PermBound -->|Boundary Denies Action| DENY2(["DENY - Permissions Boundary"])
    PermBound -->|No Boundary or Allows| SessionPol{"Session Policy Present?"}
    SessionPol -->|Session Policy Denies| DENY3(["DENY - Session Policy"])
    SessionPol -->|No Session Policy or Allows| ExplicitDeny{"Any Attached Policy\nhas Explicit DENY?"}
    ExplicitDeny -->|YES - Explicit Deny Found| DENY4(["DENY - Explicit Policy Deny"])
    ExplicitDeny -->|NO - No Explicit Deny| ExplicitAllow{"Any Attached Policy\nhas Explicit ALLOW?"}
    ExplicitAllow -->|YES - Allow Found| ALLOW(["ALLOW - Request Permitted"])
    ExplicitAllow -->|NO - No Allow Found| DENY5(["DENY - Implicit Deny\n(Default: Everything Denied)"])

    classDef start fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef decision fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef deny fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef allow fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class Start start;
    class OrgSCP,PermBound,SessionPol,ExplicitDeny,ExplicitAllow decision;
    class DENY1,DENY2,DENY3,DENY4,DENY5 deny;
    class ALLOW allow;
```

---

### IAM-6: Authentication vs Authorization

```mermaid
flowchart LR
    subgraph AuthN["AUTHENTICATION - Who Are You?"]
        UserID["Identity Claims\n(Username + Password + MFA\nOR Access Key + Secret Key\nOR Temp Credentials from STS)"]
        Verified["Identity Verified by AWS IAM"]
    end

    subgraph AuthZ["AUTHORIZATION - What Can You Do?"]
        Request["API Request\n(e.g. s3:GetObject on bucket-A)"]
        PolicyEval["IAM Policy Engine\nEvaluates all attached policies\n(SCPs, Boundaries, Identity Policies,\nResource Policies)"]
        Result{"Decision"}
    end

    UserID -->|Credentials sent| Verified
    Verified -->|Authenticated Principal| Request
    Request --> PolicyEval
    PolicyEval --> Result
    Result -->|Explicit Allow| Allow["API Call Succeeds - 200 OK"]
    Result -->|Explicit or Implicit Deny| Deny["403 AccessDenied"]

    classDef authn fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef authz fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef allow fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef deny fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;

    class UserID,Verified authn;
    class Request,PolicyEval,Result authz;
    class Allow allow;
    class Deny deny;
```

| Concept | Authentication (AuthN) | Authorization (AuthZ) |
| :--- | :--- | :--- |
| **Question** | Who are you? | What are you allowed to do? |
| **Mechanism** | Username + Password, Access Keys, MFA, Temp Credentials | IAM Policies, Resource Policies, SCPs |
| **Validates** | Identity of the caller | Permissions of the caller |
| **Failure Result** | 401 Unauthorized | 403 AccessDenied / Forbidden |

---

### IAM-7: Principle of Least Privilege

**Definition:** Grant only the **minimum permissions** required for a user or service to complete its specific task.

**Implementation Checklist:**

| Practice | Bad Example | Good Example |
| :--- | :--- | :--- |
| Scope actions | `"Action": "*"` | `"Action": ["s3:GetObject", "s3:PutObject"]` |
| Scope resources | `"Resource": "*"` | `"Resource": "arn:aws:s3:::my-bucket/*"` |
| Service permissions | Lambda with `AdministratorAccess` | Lambda with only needed permissions |

---

### IAM-8: Multi-Factor Authentication (MFA)

MFA adds a second verification factor beyond username and password.

**MFA Device Types:**

| Type | Device | Best For |
| :--- | :--- | :--- |
| **Virtual MFA** | Google Authenticator, Authy | Individual users (most common) |
| **Hardware TOTP Token** | Gemalto, SafeNet physical token | High-security environments |
| **FIDO Security Key** | YubiKey, Titan Security Key | Phishing-resistant, enterprise-grade |

**Enforce MFA via IAM Policy:**
```json
{
  "Version": "2012-10-17",
  "Statement": [{
    "Sid": "DenyAllIfNoMFA",
    "Effect": "Deny",
    "NotAction": [
      "iam:CreateVirtualMFADevice",
      "iam:EnableMFADevice",
      "iam:GetUser",
      "iam:ListMFADevices",
      "sts:GetSessionToken"
    ],
    "Resource": "*",
    "Condition": {
      "BoolIfExists": {"aws:MultiFactorAuthPresent": "false"}
    }
  }]
}
```

---

### IAM-9: Access Keys — Complete Guide

#### Rotating Access Keys (Zero-Downtime Rotation)
```bash
# Step 1: Create new access key (max 2 per user)
aws iam create-access-key --user-name john-dev

# Step 2: Update all applications/CI to use new key
# Step 3: Verify new key works
aws sts get-caller-identity

# Step 4: Deactivate old key
aws iam update-access-key \
  --user-name john-dev \
  --access-key-id AKIAIOSFODNN7OLDEXMPL \
  --status Inactive

# Step 5: Wait 24 hours, then delete old key
aws iam delete-access-key \
  --user-name john-dev \
  --access-key-id AKIAIOSFODNN7OLDEXMPL
```

**Security Risks & Best Practices:**

| Risk | Mitigation |
| :--- | :--- |
| Keys committed to Git | Use `git-secrets` or GitHub secret scanning. Rotate immediately |
| Keys in Docker images | Use IAM Roles (ECS task roles / EKS IRSA) instead |
| Keys in environment variables | Use AWS Secrets Manager + rotation Lambda |
| Long-lived keys (>90 days) | Automated rotation via Lambda + EventBridge |
| Root account access keys | **Never create root access keys** |

> [!CAUTION]
> If you accidentally push AWS Access Keys to a public repository, immediately rotate the key and audit CloudTrail for unauthorized usage in the past 24-48 hours.

---

### IAM-10: IAM Identity Center (AWS SSO)

AWS IAM Identity Center provides **centralized access management** across multiple AWS accounts and cloud applications.

| Feature | Benefit |
| :--- | :--- |
| **Single sign-on** | One login grants access to multiple AWS accounts |
| **Integration with IdPs** | Connect with Okta, Azure AD, Google Workspace, Active Directory |
| **Permission Sets** | Define permission templates applied across accounts |
| **SCIM provisioning** | Auto-provision users from your corporate IdP |

| Scenario | Recommendation |
| :--- | :--- |
| Team of 5+ engineers, multiple AWS accounts | IAM Identity Center (SSO) |
| Single AWS account, small team | IAM Users + Groups |
| Application/service accessing AWS | IAM Roles |

---

### IAM-11: IAM Credential Reports

Credential Reports provide a CSV download of all IAM users with their credential status.

```bash
# Generate and download credential report
aws iam generate-credential-report
aws iam get-credential-report --query 'Content' --output text | base64 --decode > iam-report.csv
```

| Column | Description |
| :--- | :--- |
| `password_last_used` | When user last logged into the console |
| `mfa_active` | Whether MFA is enabled (true/false) |
| `access_key_1_active` | Whether first access key is active |
| `access_key_1_last_rotated` | When first key was last rotated |

---

### IAM-12: IAM Access Analyzer

IAM Access Analyzer identifies resources shared with **external entities** outside your account.

**What It Analyzes:**
- S3 Buckets accessible externally
- IAM Roles with cross-account trust
- KMS Keys shared externally
- Lambda functions with resource policies granting external access
- SQS queues and Secrets Manager secrets with cross-account access

```bash
aws accessanalyzer create-analyzer \
  --analyzer-name my-account-analyzer \
  --type ACCOUNT
```

---

### IAM-13: Permissions Boundaries

Permissions Boundaries set the **maximum permissions** an IAM entity can have.

**Effective Permissions = Identity Policy INTERSECT Permissions Boundary**

```bash
# Create role with permissions boundary
aws iam create-role \
  --role-name app-service-role \
  --assume-role-policy-document file://trust-policy.json \
  --permissions-boundary arn:aws:iam::123456789012:policy/MaxDeveloperPermissions
```

Use when delegating IAM role creation to developers to prevent privilege escalation.

---

### IAM-14: AWS Organizations SCP Overview

Service Control Policies (SCPs) restrict what AWS services and actions are available to member accounts.

| Aspect | IAM Policy | SCP |
| :--- | :--- | :--- |
| **Scope** | Single account | Organization level (account-wide ceiling) |
| **Overrides** | Can be overridden by explicit deny | Cannot be overridden by any account-level policy |
| **Root account affected** | No | Yes |

```json
{
  "Version": "2012-10-17",
  "Statement": [{
    "Sid": "DenyDisableCloudTrail",
    "Effect": "Deny",
    "Action": ["cloudtrail:DeleteTrail", "cloudtrail:StopLogging"],
    "Resource": "*"
  }]
}
```

---

### IAM-15: Common IAM Interview Questions

**Q1: What is the difference between an IAM Role and an IAM User?**
> IAM User has **long-term credentials** tied to a permanent identity. IAM Role has **no permanent credentials** — it issues temporary credentials via STS that expire. Roles are preferred for applications because credentials rotate automatically.

**Q2: A developer accidentally committed AWS Access Keys to GitHub. Response plan?**
> (1) Immediately **deactivate** the compromised access key. (2) Review **CloudTrail** logs for unauthorized API calls. (3) **Terminate** unauthorized resources. (4) **Purge** from Git history. (5) Create new access key for legitimate use. (6) Implement git-secrets. (7) Migrate to IAM Roles.

**Q3: Explicit Deny vs Implicit Deny?**
> **Explicit Deny**: A `"Effect": "Deny"` statement that overrides any Allow. **Implicit Deny**: No `Allow` statement exists for the requested action — denied by default. Explicit Deny > Explicit Allow > Implicit Deny.

**Q4: How does an EC2 instance access S3 without hardcoded credentials?**
> Attach IAM Role to the EC2 Instance Profile. AWS SDK automatically queries IMDS at `http://169.254.169.254/latest/meta-data/iam/security-credentials/<RoleName>` for temporary credentials that auto-refresh.

**Q5: What is a Permissions Boundary?**
> A managed policy set on an IAM entity that defines the **maximum permissions** (ceiling). Effective permissions = Identity Policy INTERSECT Boundary. Used to prevent privilege escalation when delegating IAM management.

**Q6: What is the Confused Deputy Problem?**
> A security vulnerability where a malicious third party tricks a legitimate service into performing actions using elevated privileges. Mitigation: always include `ExternalId` condition in cross-account trust policies.

**Q7: What is IRSA?**
> IRSA (IAM Roles for Service Accounts) allows EKS pods to assume IAM Roles via OIDC federation without access keys. Service accounts are annotated with IAM Role ARN; pods get OIDC tokens injected automatically.

**Q8: Can SCPs restrict the root user?**
> Yes. SCPs can restrict the root user of **member accounts** in an AWS Organization (but not the management/master account root).

---

### IAM-16: Common Beginner Mistakes

| Mistake | Consequence | Fix |
| :--- | :--- | :--- |
| Using Root account for daily work | Root compromise = total account loss | Create IAM admin user for daily tasks |
| Attaching `AdministratorAccess` to Lambda | Lambda can do anything in your account | Grant only the specific permissions Lambda needs |
| Hardcoding access keys in application code | Keys exposed in code repositories | Use IAM Roles |
| Creating access keys for Root account | Unrestricted API access forever | Never create root access keys |
| Sharing IAM users between team members | No audit trail | One IAM user per person |
| Not enabling MFA | Password-only = easily compromised | Enable Virtual MFA for all console users |
| Using `"Resource": "*"` everywhere | Grants permission on ALL resources | Scope to specific ARNs |
| Not rotating access keys | Stale keys remain valid indefinitely | Rotate every 90 days |

---

### IAM-17: Real-World Production Examples

**Example 1: Multi-Account DevOps Setup**
```
AWS Organization
+-- Management Account (Root org management only)
+-- Security Account (CloudTrail, Config, GuardDuty logging)
+-- Dev Account
|   +-- Developer IAM Users (via IAM Identity Center)
|   +-- Application IAM Roles (EC2, Lambda, ECS)
+-- Staging Account
|   +-- CI/CD Cross-Account Role (assumed by Jenkins in Dev)
+-- Production Account
    +-- Read-Only Role (on-call engineers during incidents)
    +-- Application IAM Roles (ECS Task Roles, Lambda Execution Roles)
```

**Example 2: Zero-Trust CI/CD Pipeline**
```
GitHub Actions --> OIDC Federation --> STS AssumeRoleWithWebIdentity
--> Deploys to S3 (staging) with path-restricted role
--> After approval gate, assumes prod-deploy role
--> All actions logged in CloudTrail
```

**Example 3: Microservices Permission Isolation**
```
OrderService Lambda --> IAM Role: orders-lambda-role
  --> DynamoDB: orders-table (PutItem, GetItem, Query only)
  --> SQS: payment-queue (SendMessage only)
  --> CloudWatch Logs

PaymentService Lambda --> IAM Role: payment-lambda-role
  --> DynamoDB: payments-table (PutItem, GetItem only)
  --> Secrets Manager: payment-api-key (GetSecretValue only)
  --> SNS: notifications-topic (Publish only)
```

---

### IAM-18: Enterprise Best Practices

| Practice | Implementation |
| :--- | :--- |
| **No root access keys** | Enforce via SCP: deny `iam:CreateAccessKey` for root |
| **MFA everywhere** | IAM Identity Center with MFA enforcement |
| **Least privilege** | Use IAM Access Advisor to remove unused permissions quarterly |
| **No long-term keys for services** | All services use IAM Roles |
| **Key rotation** | Automated via Lambda + EventBridge on 90-day schedule |
| **Centralized logging** | CloudTrail in all accounts, aggregated to security account S3 |
| **Access Analyzer** | Enabled in every account; findings trigger PagerDuty |
| **Permission boundaries** | Applied to all developer-created roles |
| **SCPs as guardrails** | Deny region restrictions, deny disable CloudTrail |
| **Regular audits** | Weekly credential report, monthly Access Advisor review |

---

## TOPIC 7: VPC — VIRTUAL PRIVATE CLOUD

> **Real-World Analogy:** Think of a VPC as your own private building (office/apartment complex) within AWS's massive city. You decide how many floors (subnets), which floors are public-facing (reception/lobby = public subnet), which floors are private (server room = private subnet), who can enter or leave each floor (Security Groups & NACL), and how the building connects to the outside city (Internet Gateway & NAT Gateway).

---

### 7.1 — What Is VPC and Why Does It Exist?

#### Definition
**Virtual Private Cloud (VPC)** is a logically isolated virtual network that you define within AWS. It is your own private section of the AWS cloud where you have complete control over your network environment.

#### Why AWS Created VPC
Before VPC, all EC2 instances were launched in a flat, shared public network (EC2-Classic), making security segmentation difficult. AWS created VPC to let enterprises:
- **Isolate workloads** from other AWS customers
- **Define custom IP address ranges** just like their on-premises network
- **Apply layered security** using Security Groups and NACLs
- **Control routing** precisely — what can go where
- **Simulate on-premises networks** in the cloud for hybrid architectures

#### VPC Core Components

```mermaid
flowchart TD
    VPC["🏢 VPC\n(Virtual Private Cloud)"]
    VPC --> CIDR["📏 CIDR Block\n(IP Address Range)"]
    VPC --> Subnets["🏗️ Subnets\n(Network Segments)"]
    VPC --> RT["🗺️ Route Tables\n(Traffic Direction)"]
    VPC --> IGW["🌐 Internet Gateway\n(Public Internet Access)"]
    VPC --> NATGW["🔄 NAT Gateway\n(Outbound-Only from Private)"]
    VPC --> SG["🛡️ Security Groups\n(Instance-Level Firewall)"]
    VPC --> NACL["🚧 NACL\n(Subnet-Level Firewall)"]
    VPC --> PEER["🤝 VPC Peering\n(VPC-to-VPC Connection)"]
    VPC --> EIP["📌 Elastic IP\n(Fixed Public IP)"]

    Subnets --> PubSN["✅ Public Subnet\n(Has IGW Route)"]
    Subnets --> PrivSN["🔒 Private Subnet\n(No IGW Route)"]

    classDef vpc fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef comp fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef sub fill:#7C3AED,stroke:#DDD6FE,color:#FFFFFF,stroke-width:2px;
    class VPC vpc;
    class CIDR,RT,IGW,NATGW,SG,NACL,PEER,EIP comp;
    class Subnets,PubSN,PrivSN sub;
```

---

### 7.2 — IP Addressing Fundamentals

#### IPv4 vs IPv6

| Feature | IPv4 | IPv6 |
|:---|:---|:---|
| **Bit Length** | 32 bits | 128 bits |
| **Notation** | Dotted Decimal (`192.168.1.0`) | Hexadecimal with colons (`2001:0db8:85a3::8a2e:0370:7334`) |
| **Address Sets** | 4 groups of numbers | 8 groups of 4 hex digits |
| **Total Addresses** | ~4.3 billion | 340 undecillion (virtually unlimited) |
| **AWS VPC Support** | Primary, always used | Optional (dual-stack) |
| **Example** | `10.0.0.0/16` | `2001:db8::/32` |

> [!NOTE]
> IPv4 addresses are exhausted globally. IPv6 was designed to solve this. AWS VPCs use IPv4 as the primary addressing scheme. IPv6 can be optionally enabled for dual-stack architectures.

#### Three Types of IPs in AWS EC2

| Type | Behavior | Use Case | Example |
|:---|:---|:---|:---|
| **Private IP** | Fixed — never changes even on restart | Internal VPC communication between instances | `172.31.7.164` |
| **Public IP** | Dynamic — changes every time you stop/start | Internet access from outside | `3.109.213.248` → `13.235.79.233` (after restart) |
| **Elastic IP** | Static Public IP — never changes (paid service) | Production servers needing fixed DNS entries | `65.0.78.209` always stays the same |

> [!WARNING]
> **Elastic IPs are charged** even when not in use (if allocated but not associated to a running instance). Always release unused Elastic IPs to avoid unexpected billing.

---

### 7.3 — CIDR Block — IP Range Allocation

#### What Is CIDR?
**CIDR (Classless Inter-Domain Routing)** defines the IP address range for your VPC. The `/` notation (prefix length) determines how many IPs are available.

#### CIDR Calculation Formula
```
Total IPs = 2^(32 - prefix_length)
```

#### CIDR Quick Reference Table

| CIDR Block | Prefix Length | Total IPs | AWS Usable IPs* | Use Case |
|:---|:---:|:---:|:---:|:---|
| `10.0.0.0/16` | /16 | 65,536 | 65,531 | Large enterprise VPC |
| `10.0.0.0/20` | /20 | 4,096 | 4,091 | Medium-sized subnet |
| `10.0.0.0/24` | /24 | 256 | 251 | Typical subnet (class C) |
| `10.0.0.0/28` | /28 | 16 | 11 | Minimum AWS-supported |
| `10.0.0.0/31` | /31 | 2 | ❌ Not supported | Below minimum |
| `10.0.0.0/32` | /32 | 1 | ❌ Not supported | Single host (not for VPC) |

> [!IMPORTANT]
> **AWS VPC CIDR constraints:**
> - Minimum supported: **/28** (16 IPs)
> - Maximum supported: **/16** (65,536 IPs)
> - AWS **reserves 5 IPs** from every subnet (1st = network, 2nd = VPC router, 3rd = DNS, 4th = future, last = broadcast)

#### Worked Examples from Class
```
172.31.0.0/20   → 2^(32-20) = 2^12 = 4,096 IPs
172.31.16.0/20  → 2^(32-20) = 2^12 = 4,096 IPs
10.0.0.0/16     → 2^(32-16) = 2^16 = 65,536 IPs
10.0.2.0/28     → 2^(32-28) = 2^4  = 16 IPs
192.4.50/16     → 2^(32-16) = 2^16 = 65,536 IPs  (max IPs in VPC)
10.0.0.1/28     → 2^(32-28) = 2^4  = 16 IPs      (min AWS supported)
```

---

### 7.4 — Subnets — Dividing Your VPC

#### What Is a Subnet?
A subnet is a range of IP addresses within your VPC. Think of it as individual floors within your building.

#### Public Subnet vs Private Subnet

| Feature | Public Subnet | Private Subnet |
|:---|:---|:---|
| **Internet Access** | Direct (via Internet Gateway) | No direct access |
| **Route Table Entry** | `0.0.0.0/0 → Internet Gateway` | `0.0.0.0/0 → NAT Gateway` or no route |
| **Resource Examples** | Load Balancer, Bastion Host, NAT Gateway | EC2 App Servers, RDS Databases |
| **Public IP Assignment** | Auto-assigned (optional) | No public IP |
| **Security** | Accessible from internet | Isolated from internet |

> [!NOTE]
> **What makes a subnet "public"?** A subnet is called public NOT because of its name. It is public because its **Route Table has a route pointing to an Internet Gateway (IGW)**. Without this route, even if named "public," the subnet is effectively private.

#### Architecture: Public vs Private Subnet Flow

```mermaid
flowchart TB
    Internet((🌐 Internet))
    
    subgraph VPC ["VPC — telusko-vpc (10.0.0.0/16)"]
        subgraph PubSN ["✅ Public Subnet (10.0.0.0/24)"]
            IGW["🌐 Internet Gateway"]
            NATGW["🔄 NAT Gateway\n+ Elastic IP"]
            ALB["⚖️ Application Load Balancer"]
            BASTION["🖥️ Bastion Host"]
        end
        
        subgraph PrivSN ["🔒 Private Subnet (10.0.1.0/24)"]
            APP1["🖥️ EC2 App Server 1"]
            APP2["🖥️ EC2 App Server 2"]
        end
        
        subgraph DBSubnet ["🗄️ DB Subnet (10.0.2.0/24)"]
            RDS[("🗄️ RDS Database")]
        end
        
        PubRT["📋 Public Route Table\n0.0.0.0/0 → IGW"]
        PrivRT["📋 Private Route Table\n0.0.0.0/0 → NAT GW"]
    end
    
    Internet <--> IGW
    IGW <--> ALB
    ALB --> APP1
    ALB --> APP2
    APP1 & APP2 --> RDS
    APP1 & APP2 -.->|"Outbound Only\n(patches, updates)"| NATGW
    NATGW --> IGW
    BASTION -.->|"SSH Jump"| APP1

    classDef pub fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef priv fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#7C3AED,stroke:#DDD6FE,color:#FFFFFF,stroke-width:2px;
    classDef net fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    class IGW,NATGW,ALB,BASTION pub;
    class APP1,APP2 priv;
    class RDS db;
    class Internet net;
```

---

### 7.5 — Route Tables — The GPS of Your VPC

#### What Is a Route Table?
A Route Table is a set of rules (called routes) that determine where network traffic is directed. Every subnet must be associated with exactly one Route Table.

#### How Route Tables Work

```mermaid
flowchart LR
    subgraph PubRT ["Public Route Table (telusko-public-rt)"]
        PubR1["Destination: 10.0.0.0/16\nTarget: local"]
        PubR2["Destination: 0.0.0.0/0\nTarget: igw-0abc123"]
    end
    
    subgraph PrivRT ["Private Route Table (telusko-private-rt)"]
        PrivR1["Destination: 10.0.0.0/16\nTarget: local"]
        PrivR2["Destination: 0.0.0.0/0\nTarget: nat-0xyz789"]
    end
    
    PubSN["Public Subnet"] --> PubRT
    PrivSN["Private Subnet"] --> PrivRT

    classDef rt fill:#1E40AF,stroke:#93C5FD,color:#FFFFFF,stroke-width:2px;
    classDef sn fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    class PubRT,PrivRT rt;
    class PubSN,PrivSN sn;
```

#### Default VPC Route Table Behavior
- Every VPC automatically gets a **Main Route Table** with a single `local` route
- The `local` route allows all resources within the VPC to communicate with each other freely
- You create additional routes to connect to the internet (via IGW or NAT GW)

> [!IMPORTANT]
> **What happens without a Route Table?** Without the proper Route Table routes configured, instances cannot communicate with the internet even if a Security Group allows it. Route Tables are the **mandatory prerequisite** for internet connectivity.

---

### 7.6 — Internet Gateway (IGW) — The Door to the Internet

#### What Is an Internet Gateway?
An Internet Gateway (IGW) is a horizontally scaled, redundant, and highly available VPC component that allows communication between your VPC and the internet.

#### IGW Key Facts
- **One IGW per VPC** — You can only attach one IGW to a VPC at a time
- **No bandwidth limits** — Scales automatically to handle traffic
- **No single point of failure** — AWS manages HA internally
- **Free service** — You pay for data transfer, not the gateway itself
- **Bidirectional** — Allows both inbound (internet → EC2) and outbound (EC2 → internet) traffic

#### How IGW Enables Internet Access

```mermaid
sequenceDiagram
    participant User as 🌐 Internet User
    participant IGW as 🚪 Internet Gateway
    participant RT as 📋 Route Table
    participant SG as 🛡️ Security Group
    participant EC2 as 🖥️ EC2 Instance (Public Subnet)

    User->>IGW: HTTP Request to 52.0.0.1 (Elastic IP)
    IGW->>RT: Check route for destination IP
    RT->>RT: Match: 0.0.0.0/0 → IGW (local subnet)
    RT->>SG: Forward to Security Group check
    SG->>SG: Inbound rule: Allow port 80 from 0.0.0.0/0?
    SG->>EC2: ✅ Allowed — forward request
    EC2-->>User: HTTP Response 200 OK
```

---

### 7.7 — NAT Gateway — Private Subnet's One-Way Door

#### What Is NAT Gateway?
Network Address Translation (NAT) Gateway allows EC2 instances in **private subnets** to initiate **outbound** connections to the internet (e.g., downloading OS patches, calling external APIs), while **blocking all inbound** internet connections.

#### Why NAT Gateway Is Needed
Private subnet instances have no public IP. They cannot directly reach the internet. NAT Gateway acts as a **secure middleman** — it translates the private IP to its own public Elastic IP, sends the request to the internet, and returns the response to the private instance.

#### NAT Gateway vs NAT Instance

| Feature | NAT Gateway (Managed) | NAT Instance (Self-managed EC2) |
|:---|:---|:---|
| **Management** | Fully managed by AWS | You manage the EC2 instance |
| **Availability** | Built-in HA within AZ | Single point of failure (unless you configure) |
| **Bandwidth** | Up to 100 Gbps | Limited by EC2 instance type |
| **Cost** | Pay per hour + data | Pay for EC2 + data transfer |
| **Security Groups** | Cannot apply SG | Can apply SG |
| **Recommendation** | ✅ Always use for production | Legacy — avoid in production |

#### NAT Gateway Traffic Flow

```mermaid
flowchart LR
    subgraph PrivSN ["🔒 Private Subnet"]
        EC2P["🖥️ Private EC2\nIP: 10.0.1.50"]
    end

    subgraph PubSN ["✅ Public Subnet"]
        NATGW["🔄 NAT Gateway\nElastic IP: 52.0.1.10"]
        IGW["🌐 Internet Gateway"]
    end

    Internet(("🌐 Internet\nyum.amazonaws.com"))

    EC2P -->|"1. Request to 0.0.0.0/0\nSrc: 10.0.1.50"| NATGW
    NATGW -->|"2. SNAT: Replace src IP\nSrc: 52.0.1.10"| IGW
    IGW -->|"3. Forward to internet"| Internet
    Internet -->|"4. Response to 52.0.1.10"| IGW
    IGW -->|"5. Forward to NAT GW"| NATGW
    NATGW -->|"6. DNAT: Restore\nDst: 10.0.1.50"| EC2P

    classDef pub fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef priv fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef net fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    class NATGW,IGW pub;
    class EC2P priv;
    class Internet net;
```

> [!IMPORTANT]
> **NAT Gateway is AZ-specific.** For high availability in production, deploy one NAT Gateway per Availability Zone. Each private subnet's Route Table should point to the NAT Gateway in its own AZ to avoid cross-AZ data transfer costs and single AZ dependency.

> [!WARNING]
> **NAT Gateway is a paid service.** It charges per hour (~$0.045/hr) plus per GB of data processed. After practice sessions, **always delete the NAT Gateway and release its associated Elastic IP** to avoid unexpected bills.

---

### 7.8 — Security Groups — Instance-Level Firewall

#### What Is a Security Group?
A Security Group (SG) acts as a virtual firewall that controls the inbound and outbound traffic for **individual AWS resources** (EC2 instances, RDS databases, Lambda functions, etc.).

#### Security Group Key Characteristics

| Property | Details |
|:---|:---|
| **Level** | Resource (instance) level — applied per resource |
| **State** | **Stateful** — if you allow inbound port 80, return traffic is automatically allowed |
| **Default Behavior** | All inbound **DENIED** by default, all outbound **ALLOWED** by default |
| **Rule Types** | Allow rules **only** — cannot explicitly deny |
| **Max Rules** | Up to **50 inbound + 50 outbound** rules per SG |
| **Reusability** | One SG can be attached to **multiple resources** |
| **Multiple SGs** | One resource can have **multiple SGs** applied |
| **Association** | Must be **manually attached** to resources |

#### Common Security Group Configurations

```
Web Server Security Group (public-facing):
  Inbound:
    - Port 80 (HTTP)  from 0.0.0.0/0 (internet)
    - Port 443 (HTTPS) from 0.0.0.0/0 (internet)
    - Port 22 (SSH)   from your-office-ip/32 (restricted)
  Outbound:
    - All traffic to 0.0.0.0/0 (default — allow all)

App Server Security Group (private subnet):
  Inbound:
    - Port 8080 from Web Server SG (SG-to-SG reference!)
  Outbound:
    - All traffic to 0.0.0.0/0

Database Security Group (private subnet):
  Inbound:
    - Port 3306 from App Server SG (SG-to-SG reference!)
  Outbound:
    - All traffic within VPC
```

> [!TIP]
> **Best Practice — Security Group Chaining:** Instead of allowing traffic from a CIDR block, reference another Security Group as the source. This ensures only resources with that SG can reach your resource, regardless of their IP address. This is the most secure approach for internal service-to-service communication.

---

### 7.9 — NACL — Network Access Control List (Subnet-Level Firewall)

#### What Is NACL?
Network Access Control List (NACL) acts as a firewall at the **subnet level**. Every resource inside that subnet is governed by the NACL rules, regardless of its individual Security Group settings.

#### NACL Key Characteristics

| Property | Details |
|:---|:---|
| **Level** | Subnet level — applies to **all resources** in the subnet |
| **State** | **Stateless** — return traffic must be explicitly allowed separately |
| **Rule Types** | Supports both **Allow AND Deny** rules |
| **Rule Evaluation** | Rules are evaluated in **ascending order by rule number** (lowest first) |
| **Default NACL** | AWS creates a default NACL allowing all traffic in/out |
| **Custom NACL** | Starts with all traffic **DENIED** — you add allow rules |
| **One Subnet per NACL** | A subnet can be associated with **only one NACL** |
| **Reusability** | One NACL can be applied to **multiple subnets** |

#### Security Group vs NACL — Complete Comparison

| Feature | Security Group | NACL |
|:---|:---|:---|
| **Operates At** | Instance / Resource level | Subnet level |
| **State** | Stateful | Stateless |
| **Inbound Rules** | Allow only | Allow and Deny |
| **Outbound Rules** | Allow only | Allow and Deny |
| **Return Traffic** | Automatically allowed | Must be explicitly allowed |
| **Rule Processing** | All rules evaluated together | Lowest rule number wins |
| **Association** | Manually attached to resource | Automatically applied to all subnet resources |
| **Defense Role** | First line for **outgoing** traffic | First line for **incoming** traffic |
| **Max Rules** | 50 per direction | 20 per direction (default) |

#### Security Layering Model

```mermaid
flowchart LR
    Internet(("🌐 Internet"))
    
    subgraph VPC ["VPC"]
        subgraph Subnet ["Subnet"]
            NACL["🚧 NACL\n(Subnet Firewall)\nFirst line for INBOUND"]
            subgraph Resource ["Resource"]
                SG["🛡️ Security Group\n(Instance Firewall)\nFirst line for OUTBOUND"]
                EC2["🖥️ EC2 Instance"]
            end
        end
    end

    Internet -->|"Inbound Request"| NACL
    NACL -->|"If NACL allows →"| SG
    SG -->|"If SG allows →"| EC2
    EC2 -->|"Outbound Response"| SG
    SG -->|"SG allows return →"| NACL
    NACL -->|"NACL must explicitly\nallow return →"| Internet

    classDef nacl fill:#7C3AED,stroke:#DDD6FE,color:#FFFFFF,stroke-width:2px;
    classDef sg fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef ec2 fill:#1E40AF,stroke:#93C5FD,color:#FFFFFF,stroke-width:2px;
    classDef internet fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    class NACL nacl;
    class SG sg;
    class EC2 ec2;
    class Internet internet;
```

> [!NOTE]
> **Memory Aid:** Think of NACL as the **building security guard at the entrance** (subnet boundary) — checks everyone entering AND leaving. Security Group is the **door lock on your specific apartment** (instance) — controls who can knock on your specific door.

---

### 7.10 — VPC Peering — Connecting Two VPCs

#### What Is VPC Peering?
VPC Peering establishes a private network connection between two VPCs, allowing resources in both VPCs to communicate using private IP addresses as if they were in the same network.

#### VPC Peering Key Facts
- Works for VPCs in the **same account** or **different AWS accounts**
- Works across **same region** or **different regions** (inter-region peering)
- Traffic stays within the **AWS backbone network** — never touches the public internet
- **Non-transitive:** If VPC-A peers with VPC-B, and VPC-B peers with VPC-C, VPC-A **cannot** communicate with VPC-C without a direct peering connection
- **No overlapping CIDR blocks** — VPCs being peered cannot have the same IP range

#### VPC Peering Architecture

```mermaid
flowchart LR
    subgraph TVPC ["telusko-vpc (10.0.0.0/16)"]
        EC2T["🖥️ EC2 Instance\nIP: 10.0.1.50"]
    end

    PEER["🤝 VPC Peering\nConnection"]

    subgraph DVPC ["default-vpc (172.31.0.0/16)"]
        EC2D["🖥️ EC2 Instance\nIP: 172.31.7.50"]
    end

    EC2T <-->|"Private IP Communication"| PEER
    PEER <-->|"Private IP Communication"| EC2D

    classDef vpc1 fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef vpc2 fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef peer fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    class EC2T vpc1;
    class EC2D vpc2;
    class PEER peer;
```

#### VPC Peering Setup Steps (Console)
1. **Create Peering Connection:**
   - Go to VPC → **Peering Connections** → Create Peering Connection
   - Set **Requester VPC** = `telusko-vpc`
   - Set **Accepter VPC** = `default-vpc`
2. **Accept the Peering Request:**
   - Status shows `pending-acceptance`
   - Go to Peering Connections → **Actions** → **Accept Request**
3. **Update Route Tables (BOTH VPCs):**
   - In `telusko-vpc` Route Table: Add route `172.31.0.0/16 → pcx-xxxxx` (peering connection)
   - In `default-vpc` Route Table: Add route `10.0.0.0/16 → pcx-xxxxx` (peering connection)
4. **Update Security Groups (BOTH VPCs):**
   - Allow inbound traffic from the peer VPC's CIDR range

---

### 7.11 — Complete VPC Architecture (Production Standard)

```mermaid
flowchart TB
    Internet(("🌐 Internet"))

    subgraph Region ["AWS Region: ap-south-1 (Mumbai)"]
        subgraph VPC ["🏢 VPC: telusko-vpc (10.0.0.0/16)"]
            IGW["🌐 Internet Gateway\n(IGW)"]

            subgraph AZ1 ["Availability Zone: ap-south-1a"]
                subgraph PubSN1 ["✅ Public Subnet\n10.0.0.0/24"]
                    NAT1["🔄 NAT Gateway\n+ Elastic IP"]
                    ALB1["⚖️ Load Balancer"]
                end
                subgraph PrivSN1 ["🔒 Private Subnet\n10.0.1.0/24"]
                    APP1["🖥️ EC2 App\n10.0.1.10"]
                end
                subgraph DBSN1 ["🗄️ DB Subnet\n10.0.2.0/24"]
                    RDS1[("🗄️ RDS Primary\n10.0.2.10")]
                end
            end

            subgraph AZ2 ["Availability Zone: ap-south-1b"]
                subgraph PubSN2 ["✅ Public Subnet\n10.0.3.0/24"]
                    NAT2["🔄 NAT Gateway\n+ Elastic IP"]
                end
                subgraph PrivSN2 ["🔒 Private Subnet\n10.0.4.0/24"]
                    APP2["🖥️ EC2 App\n10.0.4.10"]
                end
                subgraph DBSN2 ["🗄️ DB Subnet\n10.0.5.0/24"]
                    RDS2[("🗄️ RDS Standby\n10.0.5.10")]
                end
            end

            PubRT["📋 Public RT\n0.0.0.0/0 → IGW"]
            PrivRT1["📋 Private RT (AZ-1a)\n0.0.0.0/0 → NAT1"]
            PrivRT2["📋 Private RT (AZ-1b)\n0.0.0.0/0 → NAT2"]
        end
    end

    Internet <--> IGW
    IGW <--> ALB1
    ALB1 --> APP1
    ALB1 --> APP2
    APP1 -.->|"Outbound via NAT1"| NAT1
    APP2 -.->|"Outbound via NAT2"| NAT2
    NAT1 & NAT2 --> IGW
    APP1 & APP2 --> RDS1
    RDS1 ==>|"Sync Replication"| RDS2

    classDef pub fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef priv fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#7C3AED,stroke:#DDD6FE,color:#FFFFFF,stroke-width:2px;
    classDef net fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    classDef rt fill:#1E40AF,stroke:#93C5FD,color:#FFFFFF,stroke-width:2px;
    class NAT1,NAT2,ALB1,IGW pub;
    class APP1,APP2 priv;
    class RDS1,RDS2 db;
    class Internet net;
    class PubRT,PrivRT1,PrivRT2 rt;
```

---

### 7.12 — Step-by-Step VPC Practical (Console Walkthrough)

This walkthrough replicates the class practical session:

#### Step 1: Create VPC
```
AWS Console → VPC → Create VPC
  Name: telusko-vpc
  IPv4 CIDR: 10.0.0.0/16
  IPv6 CIDR: No IPv6
  Tenancy: Default
→ Click "Create VPC"
```
✅ **Expected:** VPC `telusko-vpc` created. AWS automatically creates a **Main Route Table** — rename it to `telusko-private-rt`.

---

#### Step 2: Create Subnets (2 Subnets)
```
VPC → Subnets → Create Subnet

Subnet 1 (Public):
  VPC: telusko-vpc
  Subnet Name: telusko-public-sn
  Availability Zone: ap-south-1a
  IPv4 CIDR: 10.0.0.0/24   ← 256 IPs (251 usable)

Subnet 2 (Private):
  VPC: telusko-vpc
  Subnet Name: telusko-private-sn
  Availability Zone: ap-south-1a
  IPv4 CIDR: 10.0.1.0/24   ← 256 IPs (251 usable)
```
✅ **Expected:** Two subnets created — both currently private (no IGW route yet).

---

#### Step 3: Create Internet Gateway and Attach to VPC
```
VPC → Internet Gateways → Create Internet Gateway
  Name: telusko-igw
→ Click "Create"
→ Actions → Attach to VPC → Select: telusko-vpc
```
✅ **Expected:** IGW attached to VPC. Status shows `Attached`.

---

#### Step 4: Create Public Route Table and Configure Routes
```
VPC → Route Tables → Create Route Table
  Name: telusko-public-rt
  VPC: telusko-vpc
→ Click "Create"

Edit Routes (telusko-public-rt):
  Add Route:
    Destination: 0.0.0.0/0
    Target: Internet Gateway → telusko-igw
→ Save Routes

Subnet Associations (telusko-public-rt):
  Edit Subnet Associations → Add: telusko-public-sn

Subnet Associations (telusko-private-rt):
  Edit Subnet Associations → Add: telusko-private-sn
```
✅ **Expected:** Public subnet now routes internet traffic via IGW. Private subnet routes locally only.

---

#### Step 5: Launch EC2 Instances in Each Subnet
```
EC2 → Launch Instance

Instance 1 (Public):
  Name: public-server
  AMI: Amazon Linux 2
  VPC: telusko-vpc
  Subnet: telusko-public-sn
  Auto-assign Public IP: ENABLE
  Security Group: Allow SSH (22) + HTTP (80)

Instance 2 (Private):
  Name: private-server
  AMI: Amazon Linux 2
  VPC: telusko-vpc
  Subnet: telusko-private-sn
  Auto-assign Public IP: DISABLE
  Security Group: Allow SSH (22) from public-server SG
```

---

#### Step 6: Test Connectivity
```bash
# Test 1: SSH to Public EC2 from your local machine
ssh -i your-key.pem ec2-user@<public-ec2-public-ip>

# Test 2: Once on public EC2, verify outbound internet works
ping www.google.com    # Should succeed ✅

# Test 3: SSH from public EC2 to private EC2 (bastion host pattern)
# First, upload PEM file to public EC2
scp -i your-key.pem your-key.pem ec2-user@<public-ip>:~/
# Then SSH to public EC2 and connect to private EC2
ssh -i ~/your-key.pem ec2-user@<private-ec2-private-ip>

# Test 4: From private EC2, try to reach internet (should FAIL)
ping www.google.com    # Should FAIL ❌ — no NAT Gateway yet
```

---

#### Step 7: Add NAT Gateway for Private Subnet Internet Access
```
VPC → NAT Gateways → Create NAT Gateway
  Name: telusko-nat-gw
  Subnet: telusko-public-sn    ← MUST be in PUBLIC subnet
  Connectivity: Public
  Elastic IP: Allocate Elastic IP → Allocate
→ Click "Create NAT Gateway"

Edit Private Route Table (telusko-private-rt):
  Add Route:
    Destination: 0.0.0.0/0
    Target: NAT Gateway → telusko-nat-gw
→ Save Routes
```
✅ **Expected:** After ~1 minute, NAT Gateway becomes Available.

---

#### Step 8: Test Private Instance Internet Access
```bash
# SSH to public EC2, then jump to private EC2
# From private EC2:
ping www.google.com    # Now SUCCEEDS ✅ — NAT GW routes traffic
sudo yum update -y    # Can download updates ✅
```

---

#### Cleanup (IMPORTANT — Avoid Bills!)
```
1. Delete NAT Gateway (takes a few minutes)
2. Release Elastic IP associated with NAT Gateway
3. Terminate EC2 Instances
4. Delete Subnets
5. Detach and Delete Internet Gateway
6. Delete VPC
```

---

### 7.13 — AWS CLI Commands for VPC

```bash
# Create VPC
aws ec2 create-vpc \
  --cidr-block 10.0.0.0/16 \
  --tag-specifications 'ResourceType=vpc,Tags=[{Key=Name,Value=telusko-vpc}]'

# Create Public Subnet
aws ec2 create-subnet \
  --vpc-id vpc-0abc123 \
  --cidr-block 10.0.0.0/24 \
  --availability-zone ap-south-1a \
  --tag-specifications 'ResourceType=subnet,Tags=[{Key=Name,Value=telusko-public-sn}]'

# Create Private Subnet
aws ec2 create-subnet \
  --vpc-id vpc-0abc123 \
  --cidr-block 10.0.1.0/24 \
  --availability-zone ap-south-1a \
  --tag-specifications 'ResourceType=subnet,Tags=[{Key=Name,Value=telusko-private-sn}]'

# Create and Attach Internet Gateway
aws ec2 create-internet-gateway \
  --tag-specifications 'ResourceType=internet-gateway,Tags=[{Key=Name,Value=telusko-igw}]'

aws ec2 attach-internet-gateway \
  --internet-gateway-id igw-0abc123 \
  --vpc-id vpc-0abc123

# Create Route Table for Public Subnet
aws ec2 create-route-table \
  --vpc-id vpc-0abc123 \
  --tag-specifications 'ResourceType=route-table,Tags=[{Key=Name,Value=telusko-public-rt}]'

# Add Route to Internet Gateway
aws ec2 create-route \
  --route-table-id rtb-0abc123 \
  --destination-cidr-block 0.0.0.0/0 \
  --gateway-id igw-0abc123

# Associate Route Table with Public Subnet
aws ec2 associate-route-table \
  --route-table-id rtb-0abc123 \
  --subnet-id subnet-0abc123

# Create NAT Gateway (requires Elastic IP first)
aws ec2 allocate-address --domain vpc
aws ec2 create-nat-gateway \
  --subnet-id subnet-0abc123 \
  --allocation-id eipalloc-0abc123

# Create VPC Peering Connection
aws ec2 create-vpc-peering-connection \
  --vpc-id vpc-0abc123 \
  --peer-vpc-id vpc-0xyz789

# Accept VPC Peering Connection
aws ec2 accept-vpc-peering-connection \
  --vpc-peering-connection-id pcx-0abc123

# Describe VPCs
aws ec2 describe-vpcs --query 'Vpcs[*].{ID:VpcId,CIDR:CidrBlock,Name:Tags[?Key==`Name`].Value|[0]}'
```

---

### 7.14 — Security Group vs NACL — Side-by-Side Deep Dive

```mermaid
flowchart TB
    subgraph SG_Box ["🛡️ SECURITY GROUP (Stateful — Instance Level)"]
        direction LR
        SG_In["Inbound Rules:\n✅ Allow Port 80\n✅ Allow Port 443\n✅ Allow Port 22\n❌ No Deny rules"]
        SG_Auto["Auto Return\nTraffic Allowed"]
        SG_Out["Outbound Rules:\n✅ All Traffic (default)"]
        SG_In --> SG_Auto --> SG_Out
    end

    subgraph NACL_Box ["🚧 NACL (Stateless — Subnet Level)"]
        direction LR
        NACL_In["Inbound Rules:\nRule 100: Allow Port 80\nRule 200: Allow Port 443\nRule 300: Allow Port 22\nRule *: Deny All"]
        NACL_Manual["Manual Return\nTraffic Required"]
        NACL_Out["Outbound Rules:\nRule 100: Allow 1024-65535\nRule *: Deny All"]
        NACL_In --> NACL_Manual --> NACL_Out
    end

    classDef sg fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef nacl fill:#7C3AED,stroke:#DDD6FE,color:#FFFFFF,stroke-width:2px;
```

> [!IMPORTANT]
> **Why does NACL need ephemeral ports in outbound rules?** When a client sends an HTTP request to port 80, the server responds to the client's **ephemeral port** (randomly chosen between 1024-65535). Since NACL is stateless, you must explicitly allow outbound traffic on the ephemeral port range for return traffic to reach the client.

---

### 7.15 — VPC Interview Questions & Detailed Answers

#### Q1: Why is a Public Subnet called "Public"?
**A:** A subnet is called "public" not because of its name, but because its **Route Table contains a route pointing to an Internet Gateway (IGW)** with destination `0.0.0.0/0`. This route allows resources within that subnet to send and receive traffic directly from the internet. Without this IGW route, even a subnet named "public" would be effectively private.

---

#### Q2: Why is NAT Gateway required?
**A:** Private subnet instances have no public IP address. When they need to reach the internet (to download OS patches, call external APIs, etc.), they cannot do so directly. NAT Gateway acts as an intermediary:
1. Private EC2 sends traffic to NAT Gateway (via Route Table)
2. NAT Gateway replaces the private source IP with its own public Elastic IP (SNAT)
3. NAT Gateway forwards the request through the Internet Gateway
4. Internet responds to NAT Gateway's Elastic IP
5. NAT Gateway translates the destination back to the private EC2's private IP (DNAT)

The key benefit: **internet cannot initiate connections to private instances** — only outbound connections are permitted.

---

#### Q3: Difference between Security Group and NACL?
**A:** 
| | Security Group | NACL |
|:---|:---|:---|
| Level | Instance/Resource | Subnet |
| State | Stateful | Stateless |
| Rules | Allow only | Allow + Deny |
| Return traffic | Auto-allowed | Must explicitly allow |
| Defense role | First line for outgoing | First line for incoming |

---

#### Q4: Can a Private EC2 access the internet?
**A:** **Yes, but only for outbound connections** — through a NAT Gateway. 
- **Without NAT Gateway:** Private EC2 has NO internet access whatsoever.
- **With NAT Gateway (in public subnet):** Private EC2 can make outbound requests (download packages, call APIs), but **the internet CANNOT initiate inbound connections** to the private EC2. This is the secure, recommended pattern.

---

#### Q5: What happens without Route Tables?
**A:** Without properly configured Route Tables:
- The default `local` route still exists, allowing VPC-internal communication
- **No internet connectivity** — without `0.0.0.0/0 → IGW` route, public subnet EC2s cannot reach the internet
- **No NAT connectivity** — without `0.0.0.0/0 → NAT GW` route, private subnet EC2s cannot reach the internet
- **No VPC Peering** — without peering routes, VPC-peered resources cannot communicate

Route Tables are the **central nervous system** of VPC networking. Without correct routes, everything is isolated.

---

#### Q6: What is the difference between VPC Endpoint and VPC Peering?
**A:**
- **VPC Peering:** Connects two VPCs together so resources in both can communicate via private IPs. Used for VPC-to-VPC communication.
- **VPC Endpoint:** Connects your VPC to AWS services (like S3, DynamoDB, SQS) **privately**, without routing traffic through the internet or NAT Gateway. Used for VPC-to-AWS service communication.

| | VPC Peering | VPC Endpoint |
|:---|:---|:---|
| Connects | VPC ↔ VPC | VPC ↔ AWS Service |
| Cost | Free (data transfer charged) | Free (Gateway) or hourly fee (Interface) |
| Types | Single type | Gateway Endpoint (S3/DynamoDB) or Interface Endpoint (others) |

---

#### Q7: Why can't two peered VPCs have overlapping CIDR blocks?
**A:** If both VPCs use the same IP range (e.g., both use `10.0.0.0/16`), the Route Table cannot determine which VPC a packet is destined for — the routes would be ambiguous and conflict. AWS enforces non-overlapping CIDRs for peering to ensure deterministic routing.

---

#### Q8: What is a Default VPC?
**A:** AWS automatically creates a **Default VPC** in each Region for every AWS account. The Default VPC:
- Has CIDR `172.31.0.0/16`
- Has a public subnet in each AZ with auto-assigned public IPs
- Has a pre-configured Internet Gateway, Route Table, NACL, and Security Group
- Is ready to use immediately — ideal for learning and quick experiments
- **Should NOT be used for production** — create a custom VPC with explicit private subnets and security controls

---

#### Q9: Real-World VPC Scenario — Banking Application
**A:** A banking application requires strict network isolation:

```
VPC: 10.0.0.0/16

Public Tier (Public Subnet):
  - Application Load Balancer (HTTPS only)
  - NAT Gateway (for patch updates)
  - Bastion Host (restricted SSH access)

Application Tier (Private Subnet):
  - Spring Boot API servers (EC2 in Auto Scaling Group)
  - Security Group: Allow port 8443 from ALB SG only

Database Tier (Private Subnet - isolated):
  - RDS MySQL Multi-AZ (primary + standby)
  - Security Group: Allow port 3306 from App Tier SG only
  
Additional Controls:
  - NACL: Deny all traffic from known malicious IPs
  - VPC Flow Logs: Capture all traffic for compliance
  - VPC Endpoints: Private access to S3 for document storage
  - AWS PrivateLink: Private connection to payment gateway
```

---

### 7.16 — Best Practices & Common Mistakes

#### ✅ Best Practices
1. **Never use the Default VPC for production** — create a dedicated custom VPC
2. **Plan CIDR blocks before creation** — you cannot change the VPC CIDR after creation without re-creating
3. **Use at least 2 AZs** for every tier (public, private, database) for high availability
4. **Deploy one NAT Gateway per AZ** to avoid cross-AZ dependency and data transfer costs
5. **Use Security Group chaining** — reference SGs as sources rather than CIDR blocks
6. **Enable VPC Flow Logs** for all production VPCs for security monitoring and compliance
7. **Use VPC Endpoints** for S3 and DynamoDB to avoid data transfer costs through NAT Gateway
8. **Apply principle of least privilege** — never open `0.0.0.0/0` for SSH in production

#### ❌ Common Mistakes
1. **Forgetting to update Route Tables** after creating IGW or NAT Gateway — resources cannot communicate
2. **Placing NAT Gateway in a private subnet** — NAT Gateway MUST be in a public subnet to work
3. **Not releasing Elastic IPs** after deleting NAT Gateways — causes ongoing billing charges
4. **Overlapping CIDR blocks** when planning VPC Peering
5. **Confusing NACL stateless behavior** — forgetting to add outbound ephemeral port rules breaks connections
6. **Using a single NAT Gateway** across all AZs — creates a single point of failure
7. **Not enabling auto-assign public IP** for public subnet instances — instances won't be reachable

---

### 7.17 — Real-World Production VPC Use Cases

#### Healthcare (HIPAA Compliance)
- All patient data (RDS) in **private DB subnet** with no internet access
- Applications in **private app subnet** — only ALB in public subnet is internet-facing
- **VPC Flow Logs** enabled for compliance auditing
- **AWS PrivateLink** for connections to third-party healthcare APIs
- **NACL deny rules** to block specific IP ranges (known threat actors)

#### E-Commerce (High Traffic Scaling)
- **Multi-AZ public subnets** with ALB for geographic load distribution
- **Auto Scaling Groups** in private subnets launching new EC2s during flash sales
- **NAT Gateways per AZ** to prevent NAT bottleneck during scale-out events
- **VPC Endpoints for S3** — product images and static assets accessed privately at scale

#### SaaS Multi-Tenant
- **Separate VPCs per customer** for complete network isolation
- **VPC Peering** to shared services VPC (logging, monitoring, billing)
- **AWS Transit Gateway** (advanced) to hub-and-spoke multiple VPCs efficiently

---

## TOPIC 8: LOAD BALANCER & AUTO SCALING

### 1. Concept Explanation

#### Beginner
* **Load Balancer:** Distributes incoming application traffic across a fleet of target servers (like EC2 instances) to prevent overload and ensure high availability.
* **Auto Scaling Group (ASG):** Monitors your EC2 instances and automatically adjusts the instance count to maintain target capacities based on traffic demand.

#### Intermediate
AWS Elastic Load Balancing (ELB) supports four types of load balancers:

| Load Balancer Type | OSI Layer | Protocol Support | Primary Use Case |
| :--- | :---: | :--- | :--- |
| **Application LB (ALB)** | Layer 7 | HTTP, HTTPS | Path/host-based routing, Spring Boot APIs, microservices |
| **Network LB (NLB)** | Layer 4 | TCP, UDP, TLS | High-performance real-time apps, gaming, low-latency systems |
| **Gateway LB (GWLB)** | Layer 3 + 4 | All IP traffic | Routes traffic through security appliances (firewalls, IDS/IPS) |
| **Classic LB** | Layer 4 & 7 | HTTP, HTTPS, TCP | Legacy applications (deprecated, avoid for new deployments) |

* **Application Load Balancer (ALB):** Operates at Layer 7 (HTTP/HTTPS). Supports host-based routing, path-based routing, SSL termination, and sticky sessions. Most commonly used for Java/Spring Boot backends.
* **Network Load Balancer (NLB):** Operates at Layer 4 (TCP/UDP). Optimized for ultra-high performance, low latency, and static IP allocations. Ideal for real-time applications and gaming.
* **Gateway Load Balancer (GWLB):** Routes all IP traffic to security appliances (e.g., firewalls) for packet inspection before forwarding to the application:
  1. Traffic arrives from the Internet.
  2. GWLB sends traffic to firewall appliances.
  3. Firewall inspects packets — if safe, traffic is forwarded to the application; if malicious, it is blocked.

##### Auto Scaling — Why It Matters
During unpredictable traffic events (e.g., Big Billion Day, flash sales), manual scaling is too slow. **Auto Scaling** automatically adjusts your EC2 fleet:

| ASG Benefit | Description |
| :--- | :--- |
| **Fault Tolerance** | Automatically replaces failed instances. A new instance launches if any instance in the group crashes. |
| **Cost Management** | Scales down during low-traffic periods, running only the minimum needed servers, saving compute costs. |
| **High Availability** | Maintains application performance and uptime under variable and heavy load conditions. |

##### Path-Based Routing Example
An ALB can inspect the HTTP request path and route the request to different target groups:

```mermaid
flowchart LR
    Client((Client Request)) --> ALB["ALB Listener"]
    ALB -->|"/api/*"| TG1["Target Group: Spring Boot Backend"]
    ALB -->|"/admin/*"| TG2["Target Group: Admin Portal"]
    ALB -->|"/static/*"| S3["S3 Static Bucket"]

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef client fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;

    class Client client;
    class ALB,TG1,TG2 layer;
    class S3 db;
```

#### Advanced
* **Connection Draining (Deregistration Delay):** When an instance is removed from the load balancer, the ALB keeps the connection open for a period (e.g., 300 seconds) to allow in-flight requests to complete before terminating the instance.
* **ASG Lifecycle Hooks:** Pause instance state changes (e.g., during scale-out or scale-in) to run initialization scripts or retrieve diagnostic logs before the instance is destroyed.

### 2. Interview Questions & Answers

#### Q: How do you achieve zero-downtime deployments using an ALB and an ASG?
**A:** 
1. Define a **Readiness Probe** (`/actuator/health`) so the ALB only routes traffic to healthy instances.
2. Configure a rolling update deployment policy (e.g. `maxSurge = 1`, `maxUnavailable = 0` in Kubernetes, or a rolling update policy in an ASG).
3. The ASG launches a new instance running the updated code.
4. The ALB routes health check requests to the new instance. Once it passes, the ALB registers it into the target group.
5. The ALB deregisters an old instance and begins the connection draining timeout (deregistration delay) to allow active sessions to close.
6. Once drained, the old instance is terminated safely.

#### Q: What are sticky sessions and when would you use them?
**A:** Sticky sessions configure the ALB to route a user's consecutive requests to the same target EC2 instance. This is achieved by generating a cookie on the load balancer. It is used for legacy stateful applications that store user session data locally in the server's memory rather than in a distributed cache like Redis.

### 3. Key Takeaways
* ALBs operate at Layer 7 (HTTP/HTTPS) and support routing rules. NLBs operate at Layer 4 (TCP/UDP) for high-performance networks.
* Connection draining allows active requests to complete gracefully before an instance is terminated during scale-in.
* Sticky sessions tie a user to a specific instance, which can cause uneven load distribution.



---

## LOAD BALANCER & AUTO SCALING — COMPREHENSIVE DEEP DIVE

### Why Do We Need a Load Balancer?

Without a load balancer, your entire application runs on a **single server**. This creates serious problems in production:

```mermaid
flowchart TD
    subgraph SingleServer["Single Server Architecture (NO Load Balancer)"]
        Users["1000 Concurrent Users"] -->|All requests hit one server| Server["Single EC2 Instance\nhandling ALL traffic"]
        Server --> Problems["Problems:\n1. Overloaded CPU & Memory\n2. Slow/delayed responses\n3. Server crash possible\n4. Single Point of Failure\n5. Zero High Availability"]
    end

    classDef problem fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef server fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef user fill:#581C87,stroke:#A855F7,color:#FFFFFF,stroke-width:2px;

    class Problems problem;
    class Server server;
    class Users user;
```

**Problems with a Single Server:**

| Problem | Impact |
| :--- | :--- |
| **One server handles all requests** | CPU, memory, and network bandwidth exhausted quickly |
| **Increased burden on the server** | Response times degrade as requests queue up |
| **Server slows down** | Client requests time out, leading to poor user experience |
| **Server can crash** | Application becomes completely unavailable |
| **Single Point of Failure (SPOF)** | One hardware/software failure brings the entire app down |

**Solution: Deploy on Multiple Servers + Load Balancer**

```mermaid
flowchart TD
    subgraph Solved["Multi-Server Architecture WITH Load Balancer"]
        Users2["1000 Concurrent Users"] --> LB["Load Balancer\n(Traffic Distributor)"]
        LB -->|Round Robin| S1["EC2 Instance 1\n~333 requests"]
        LB -->|Round Robin| S2["EC2 Instance 2\n~333 requests"]
        LB -->|Round Robin| S3["EC2 Instance 3\n~334 requests"]
        S1 & S2 & S3 --> Benefits["Benefits:\n1. Distributed load\n2. Fast performance\n3. High Availability\n4. No SPOF\n5. Auto failover"]
    end

    classDef benefit fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef lb fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef server fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef user fill:#581C87,stroke:#A855F7,color:#FFFFFF,stroke-width:2px;

    class Benefits benefit;
    class LB lb;
    class S1,S2,S3 server;
    class Users2 user;
```

**Benefits of Load Balancer:**

| Benefit | Explanation |
| :--- | :--- |
| **Distributed Load** | Traffic is spread evenly across all servers (Round Robin by default) |
| **Reduced Burden** | Each server handles only a fraction of total requests |
| **Fast Performance** | No single server is overwhelmed — faster response times |
| **High Availability** | If one server fails, the LB routes traffic to remaining healthy servers |
| **No Single Point of Failure** | Multiple servers ensure the app stays up even if instances crash |
| **Health Checks** | LB continuously monitors servers and removes unhealthy ones automatically |
| **SSL Termination** | LB handles HTTPS decryption, reducing CPU load on app servers |

---

### Round Robin Distribution — How It Works

```mermaid
sequenceDiagram
    autonumber
    actor U1 as User Request 1
    actor U2 as User Request 2
    actor U3 as User Request 3
    actor U4 as User Request 4
    participant LB as Load Balancer
    participant S1 as Server 1
    participant S2 as Server 2
    participant S3 as Server 3

    U1->>LB: HTTP GET /
    LB->>S1: Forward to Server 1 (Round 1)
    S1-->>U1: 200 OK

    U2->>LB: HTTP GET /
    LB->>S2: Forward to Server 2 (Round 2)
    S2-->>U2: 200 OK

    U3->>LB: HTTP GET /
    LB->>S3: Forward to Server 3 (Round 3)
    S3-->>U3: 200 OK

    U4->>LB: HTTP GET /
    LB->>S1: Forward to Server 1 (Round 4 - cycles back)
    S1-->>U4: 200 OK
```

> [!NOTE]
> **Round Robin** means requests are distributed to servers in order: Server 1 → Server 2 → Server 3 → Server 1 → ... ALB also supports **Least Outstanding Requests (LOR)** which routes to the server with fewest active connections.

---

## LB-1: Application Load Balancer (ALB)

### What is ALB?

Application Load Balancer (ALB) operates at **OSI Layer 7 (Application Layer)** and makes routing decisions based on the **content of the HTTP/HTTPS request** — including URL path, hostname, headers, query parameters, and HTTP methods.

```mermaid
flowchart TD
    Internet["Internet Users"] --> ALB

    subgraph ALB_Node["Application Load Balancer (Layer 7)"]
        Listener["HTTPS Listener :443\n(SSL Termination here)"]
        Rules["Routing Rules Engine\n(Inspects URL, Host, Headers)"]
    end

    subgraph TargetGroups["Target Groups"]
        TG1["Target Group 1\nSpring Boot API Servers\n/api/*"]
        TG2["Target Group 2\nAdmin Portal Servers\n/admin/*"]
        TG3["Target Group 3\nStatic Assets\n/static/*"]
        TG4["Target Group 4\nv2 API (Canary 10%)\n/api/v2/*"]
    end

    subgraph Servers["EC2 Instances / ECS / Lambda"]
        EC2_1["EC2: api-server-1"]
        EC2_2["EC2: api-server-2"]
        EC2_3["EC2: admin-portal"]
        S3_Node["S3 Bucket / CloudFront"]
        EC2_4["EC2: api-v2-server"]
    end

    Internet --> Listener
    Listener --> Rules
    Rules -->|"/api/*"| TG1
    Rules -->|"/admin/*"| TG2
    Rules -->|"/static/*"| TG3
    Rules -->|"10% canary"| TG4
    TG1 --> EC2_1 & EC2_2
    TG2 --> EC2_3
    TG3 --> S3_Node
    TG4 --> EC2_4

    classDef lb fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef tg fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef server fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef internet fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;

    class Listener,Rules lb;
    class TG1,TG2,TG3,TG4 tg;
    class EC2_1,EC2_2,EC2_3,S3_Node,EC2_4 server;
    class Internet internet;
```

### ALB Key Features

| Feature | Description |
| :--- | :--- |
| **OSI Layer** | Layer 7 (Application Layer) |
| **Protocols** | HTTP, HTTPS, gRPC, WebSocket |
| **Routing Types** | Path-based, Host-based, Header-based, Method-based, Query string |
| **SSL Termination** | Decrypts HTTPS at the ALB — app servers receive plain HTTP |
| **Sticky Sessions** | Routes same user to same server via cookie |
| **Health Checks** | HTTP/HTTPS health check endpoint (e.g., `/actuator/health`) |
| **Authentication** | Native OIDC/Cognito integration for user authentication |
| **WAF Integration** | Attach AWS WAF to filter malicious HTTP traffic |
| **Lambda Target** | Can route HTTP requests directly to Lambda functions |

### ALB Routing Rules — Deep Dive

```mermaid
flowchart LR
    Client["Client Request\nGET /api/users\nHost: app.example.com"] --> Listener["ALB Listener :443"]

    Listener --> Rule1{"Rule 1:\nHost = app.example.com\nAND Path = /api/*?"}
    Rule1 -->|YES| TG_API["Target Group: API Servers\n(EC2 t3.medium x3)"]

    Listener --> Rule2{"Rule 2:\nHost = admin.example.com?"}
    Rule2 -->|YES| TG_Admin["Target Group: Admin Portal\n(EC2 t3.small x1)"]

    Listener --> Rule3{"Rule 3:\nPath = /static/*?"}
    Rule3 -->|YES| TG_S3["Target Group: S3/CloudFront"]

    Listener --> Default["Default Rule:\n(No match)"]
    Default --> TG_404["Target Group: Error Page\n(returns 404)"]

    classDef rule fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef tg fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef lb fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;

    class Client,Listener lb;
    class Rule1,Rule2,Rule3,Default rule;
    class TG_API,TG_Admin,TG_S3,TG_404 tg;
```

### ALB Hands-On Lab (from Class Reference)

**Step 1: Create EC2 VM-1 with User Data**
```bash
#!/bin/bash
sudo yum install httpd -y
cd /var/www/html
echo "<html><h1> Telusko Learning App - 1 </h1></html>" > index.html
service httpd start
```

**Step 2: Create EC2 VM-2 with User Data**
```bash
#!/bin/bash
sudo yum install httpd -y
cd /var/www/html
echo "<html><h1> Telusko Learning App - 2 </h1></html>" > index.html
service httpd start
```

**Step 3: Create Target Group**
- Navigate: EC2 Console → Target Groups → Create Target Group
- Target type: **Instances**
- Protocol: **HTTP**, Port: **80**
- Health check path: `/` (or `/index.html`)
- Register both EC2 instances as targets

**Step 4: Create Application Load Balancer**
- Navigate: EC2 Console → Load Balancers → Create Load Balancer → **Application Load Balancer**
- Scheme: **Internet-facing**
- Listeners: **HTTP:80**
- Availability Zones: Select at least **2 AZs**
- Security Group: Allow inbound port 80
- Target Group: Select the one created in Step 3

**Step 5: Verify Round Robin**

Access the ALB DNS name in your browser multiple times (or use curl in a loop):
```bash
# Loop to see round-robin in action
for i in {1..6}; do
    curl http://<ALB-DNS-Name>/
    echo ""
done

# Expected output (alternates between servers):
# <html><h1> Telusko Learning App - 1 </h1></html>
# <html><h1> Telusko Learning App - 2 </h1></html>
# <html><h1> Telusko Learning App - 1 </h1></html>
# <html><h1> Telusko Learning App - 2 </h1></html>
```

> [!TIP]
> The ALB DNS name looks like: `my-alb-1234567890.ap-south-1.elb.amazonaws.com`. You can find it in the EC2 Console → Load Balancers → Description tab.

### ALB Real-World Use Cases

| Use Case | How ALB Helps |
| :--- | :--- |
| **Microservices routing** | `/orders/*` → Order Service, `/payments/*` → Payment Service, `/users/*` → User Service |
| **Blue-Green deployment** | Route 100% traffic to Blue TG, switch to Green TG after successful deploy |
| **Canary release** | Route 95% to v1 TG, 5% to v2 TG using weighted routing |
| **A/B testing** | Route users with `X-Experiment: B` header to variant target group |
| **Multi-tenant SaaS** | `company1.saas.com` → Company1 TG, `company2.saas.com` → Company2 TG |
| **API Gateway alternative** | Route `/api/*` to Spring Boot ECS, `/admin/*` to React admin on EC2 |

### ALB Interview Questions

**Q1: What is the difference between ALB and NLB?**
> **A:** ALB operates at Layer 7 (HTTP/HTTPS) and can make routing decisions based on URL path, hostname, headers, and query parameters. NLB operates at Layer 4 (TCP/UDP) and routes based on IP and port only — it cannot inspect HTTP content. ALB is for web applications; NLB is for high-performance, low-latency, non-HTTP workloads.

**Q2: What is path-based routing in ALB?**
> **A:** ALB inspects the URL path of every incoming HTTP request and routes it to a different target group based on defined rules. Example: `/api/*` → API servers (EC2), `/admin/*` → Admin portal (EC2), `/static/*` → S3/CloudFront. This allows a single ALB to front-end an entire microservices architecture.

**Q3: What is SSL termination and why is it done at the ALB?**
> **A:** SSL termination means the ALB decrypts the incoming HTTPS connection and communicates with backend servers over plain HTTP. This offloads the CPU-intensive encryption/decryption work from application servers to the ALB, which is optimized for this task. The ACM (AWS Certificate Manager) certificate is attached to the ALB listener.

**Q4: What is a Target Group?**
> **A:** A Target Group is a logical group of targets (EC2 instances, IP addresses, Lambda functions, or ECS tasks) that the ALB routes traffic to. Each target group has a health check configuration. An ALB listener rule routes requests to a specific target group. One target group can be shared across multiple ALB rules.

---

## LB-2: Network Load Balancer (NLB)

### What is NLB?

Network Load Balancer operates at **OSI Layer 4 (Transport Layer)** and routes traffic based on **TCP/UDP port and IP address** — without inspecting the application-layer content (HTTP headers, URLs, etc.).

```mermaid
flowchart TD
    subgraph NLB_Arch["Network Load Balancer Architecture"]
        Clients["Clients\n(Gaming, IoT, Streaming)"] --> NLB_Node

        subgraph NLB_Node["NLB (Layer 4 - Transport)"]
            EIP["Static Elastic IP\n(one per AZ - never changes)"]
            Routing["Routes by: IP + Port only\nNo HTTP header inspection\nNo URL path routing"]
        end

        NLB_Node -->|TCP :8080| TG_A["Target Group A\nJava WebSocket Servers"]
        NLB_Node -->|UDP :5000| TG_B["Target Group B\nGame State Servers"]
        NLB_Node -->|TCP :9090| TG_C["Target Group C\nMetrics/Telemetry Servers"]
    end

    classDef nlb fill:#0369A1,stroke:#0EA5E9,color:#FFFFFF,stroke-width:2px;
    classDef tg fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef client fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;

    class EIP,Routing nlb;
    class TG_A,TG_B,TG_C tg;
    class Clients client;
```

### NLB Key Features

| Feature | Description |
| :--- | :--- |
| **OSI Layer** | Layer 4 (Transport Layer) |
| **Protocols** | TCP, UDP, TLS |
| **Static IP** | Each NLB gets one **static Elastic IP per AZ** — IP never changes |
| **Latency** | Ultra-low latency (microseconds vs milliseconds for ALB) |
| **Throughput** | Handles **millions of requests per second** |
| **Preserves Source IP** | Passes client's real IP to backend servers (ALB uses X-Forwarded-For header) |
| **No HTTP Inspection** | Cannot route based on URL, host, or headers |
| **Cross-Zone LB** | Optional (free with NLB; has cost with ALB disabled) |

### NLB vs ALB — When to Use Which?

| Scenario | Use ALB | Use NLB |
| :--- | :---: | :---: |
| Spring Boot REST API | YES | |
| Real-time multiplayer gaming | | YES |
| Video streaming (UDP) | | YES |
| Microservices routing by URL | YES | |
| IoT device data ingestion (MQTT over TCP) | | YES |
| WebSocket for chat app (HTTP Upgrade) | YES | |
| Financial trading systems (ultra low latency) | | YES |
| Static IP requirement (for whitelisting) | | YES |
| AWS WAF integration needed | YES | |

### NLB Real-World Use Cases

| Use Case | Why NLB? |
| :--- | :--- |
| **Online Gaming (PUBG/Fortnite)** | Millions of UDP packets per second, sub-millisecond latency required |
| **Stock Trading Platform** | TCP-based FIX protocol, ultra-low latency is critical for order execution |
| **Video Streaming Server** | UDP media streams (RTP/RTSP) cannot be routed by ALB |
| **VoIP / SIP Traffic** | UDP-based voice protocol, NLB preserves packet timing |
| **Industrial IoT (MQTT)** | TCP-based MQTT protocol on port 1883 |
| **Kubernetes Ingress** | NLB as a Kubernetes Service type=LoadBalancer for high-throughput APIs |
| **Partner IP Whitelisting** | NLB provides a static IP — partners whitelist this IP in their firewalls |

---

## LB-3: Gateway Load Balancer (GWLB)

### What is GWLB?

Gateway Load Balancer is a specialized AWS load balancer designed to **route traffic through third-party security appliances** (like firewalls, Intrusion Detection Systems, and Deep Packet Inspection tools) before the traffic reaches your application.

### How GWLB Works (Step-by-Step)

```mermaid
flowchart TD
    Internet["Internet Traffic\n(Potentially malicious packets)"] -->|Step 1: Traffic enters VPC| GWLB

    subgraph GWLB["Gateway Load Balancer (GWLB)"]
        GE["GWLB Endpoint\n(in your App VPC)"]
        GLB["GWLB Service\n(in Security VPC)"]
    end

    subgraph SecurityVPC["Security VPC (Firewall Tier)"]
        FW1["Firewall Appliance 1\n(e.g., Palo Alto, Fortinet, Check Point)"]
        FW2["Firewall Appliance 2\n(HA failover)"]
        FW3["Firewall Appliance 3\n(scale out)"]
    end

    subgraph AppVPC["Application VPC (Your App)"]
        App1["App Server 1"]
        App2["App Server 2"]
    end

    Internet --> GE
    GE -->|Step 2: Send to firewall for inspection| GLB
    GLB --> FW1 & FW2 & FW3
    FW1 -->|Step 3: Inspect packets| Decision{"Packet Safe?"}
    Decision -->|YES - Step 4: Forward to app| App1 & App2
    Decision -->|NO - Step 5: Drop packet| Blocked["BLOCKED\nMalicious Traffic"]

    classDef gwlb fill:#0369A1,stroke:#0EA5E9,color:#FFFFFF,stroke-width:2px;
    classDef fw fill:#581C87,stroke:#A855F7,color:#FFFFFF,stroke-width:2px;
    classDef app fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef internet fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef blocked fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;

    class GE,GLB gwlb;
    class FW1,FW2,FW3 fw;
    class App1,App2 app;
    class Internet internet;
    class Blocked blocked;
    class Decision gwlb;
```

### GWLB Real-World Use Cases

| Use Case | Why GWLB? |
| :--- | :--- |
| **Enterprise Security Compliance** | PCI-DSS / HIPAA mandates all traffic passes through an approved firewall appliance |
| **Financial Institutions** | Route banking app traffic through Palo Alto firewall for DPI before reaching app servers |
| **Government Portals** | GWLB + Check Point firewall cluster for all inbound citizen service traffic |
| **Telecom Networks** | Deep packet inspection (DPI) for lawful intercept compliance |
| **Multi-tenant SaaS** | Centralized security inspection for traffic from all tenants |

---

## LB-4: Classic Load Balancer (CLB) — Legacy

### What is CLB?

Classic Load Balancer is the **original AWS load balancer** (launched in 2009). It supports both Layer 4 (TCP) and Layer 7 (HTTP/HTTPS) but in a limited, basic manner compared to ALB and NLB.

> [!WARNING]
> **Classic Load Balancer is deprecated and should NOT be used for new deployments.** AWS recommends migrating all CLB workloads to ALB (for HTTP/HTTPS) or NLB (for TCP/UDP). CLB is shown here for reference only — it may appear in legacy system interview discussions.

| Feature | Classic LB | Application LB | Network LB |
| :--- | :--- | :--- | :--- |
| **OSI Layer** | Layer 4 + 7 (basic) | Layer 7 only | Layer 4 only |
| **Path routing** | NO | YES | NO |
| **Host routing** | NO | YES | NO |
| **WebSocket** | NO | YES | YES |
| **Static IP** | NO | NO | YES |
| **gRPC** | NO | YES | NO |
| **Status** | DEPRECATED | CURRENT | CURRENT |

---

## LB-5: Load Balancer Comparison Summary

```mermaid
flowchart LR
    Request["Incoming Request"] --> Q1{"What type of\ntraffic is it?"}

    Q1 -->|"HTTP/HTTPS\nREST API\nWebSocket"| ALB_Box["Application Load Balancer\nOSI Layer 7\nUse for: Web apps, APIs,\nMicroservices, Spring Boot"]

    Q1 -->|"TCP/UDP\nHigh throughput\nLow latency\nStatic IP needed"| NLB_Box["Network Load Balancer\nOSI Layer 4\nUse for: Gaming, IoT,\nStreaming, Trading"]

    Q1 -->|"All IP traffic\nNeeds security\nappliance inspection"| GWLB_Box["Gateway Load Balancer\nOSI Layer 3+4\nUse for: Firewalls, IDS/IPS,\nCompliance security"]

    Q1 -->|"Legacy app\non CLB already"| CLB_Box["Classic Load Balancer\nDEPRECATED\nMigrate to ALB or NLB"]

    classDef alb fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef nlb fill:#0369A1,stroke:#0EA5E9,color:#FFFFFF,stroke-width:2px;
    classDef gwlb fill:#581C87,stroke:#A855F7,color:#FFFFFF,stroke-width:2px;
    classDef clb fill:#374151,stroke:#9CA3AF,color:#FFFFFF,stroke-width:2px;
    classDef question fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;

    class ALB_Box alb;
    class NLB_Box nlb;
    class GWLB_Box gwlb;
    class CLB_Box clb;
    class Request,Q1 question;
```

| Property | ALB | NLB | GWLB | CLB |
| :--- | :---: | :---: | :---: | :---: |
| **OSI Layer** | 7 | 4 | 3+4 | 4+7 |
| **Protocols** | HTTP, HTTPS, gRPC | TCP, UDP, TLS | All IP | HTTP, TCP |
| **Path Routing** | YES | NO | NO | NO |
| **Host Routing** | YES | NO | NO | NO |
| **Static IP** | NO | YES | NO | NO |
| **Latency** | Low | Ultra-low | Medium | Low |
| **Use Case** | Web Apps, APIs | High-perf, Gaming | Security Inspection | Legacy (avoid) |
| **WAF Support** | YES | NO | NO | NO |
| **Lambda Target** | YES | NO | NO | NO |
| **SSL Termination** | YES | YES (TLS) | NO | YES |

---

## LB-6: Target Groups — Deep Dive

### What is a Target Group?

A Target Group is a **logical collection of servers/endpoints** that the Load Balancer routes traffic to. The Load Balancer does NOT talk to servers directly — it always goes through a Target Group.

```mermaid
flowchart TD
    ALB_LB["Application Load Balancer"]

    subgraph TG_Concept["Target Group Concept"]
        TG["Target Group\n(logical grouping)"]
        HC["Health Check Config\nPath: /actuator/health\nInterval: 30s\nThreshold: 2 successes"]
        LBAlgo["Load Balancing Algorithm\nRound Robin or\nLeast Outstanding Requests"]
    end

    subgraph Targets["Registered Targets"]
        EC2_A["EC2 i-abc123\n(Healthy)"]
        EC2_B["EC2 i-def456\n(Healthy)"]
        EC2_C["EC2 i-ghi789\n(Unhealthy - excluded)"]
    end

    ALB_LB -->|Routes to| TG
    TG --> HC & LBAlgo
    TG -->|Sends traffic only to healthy| EC2_A & EC2_B
    TG -.->|EXCLUDED - failed health check| EC2_C

    classDef lb fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef tg fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef healthy fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef unhealthy fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;

    class ALB_LB lb;
    class TG,HC,LBAlgo tg;
    class EC2_A,EC2_B healthy;
    class EC2_C unhealthy;
```

### Target Types

| Target Type | What It Routes To | Use Case |
| :--- | :--- | :--- |
| **Instance** | EC2 instances by instance ID | Traditional EC2 deployments |
| **IP** | Any IP address (private) | Containers with dynamic IPs, on-premises servers |
| **Lambda** | AWS Lambda function | Serverless HTTP endpoints (ALB only) |
| **ALB** | Another Application Load Balancer | GWLB chaining, nested routing |

### Health Checks — How They Work

```mermaid
sequenceDiagram
    autonumber
    participant LB as Load Balancer
    participant TG as Target Group
    participant S1 as Server 1 (Healthy)
    participant S2 as Server 2 (Unhealthy)

    loop Every 30 seconds (Health Check Interval)
        LB->>S1: GET /actuator/health HTTP/1.1
        S1-->>LB: 200 OK {"status":"UP"}
        LB->>TG: Mark Server 1: HEALTHY

        LB->>S2: GET /actuator/health HTTP/1.1
        S2-->>LB: 503 Service Unavailable (or timeout)
        LB->>TG: Mark Server 2: UNHEALTHY (1/2 failures)

        LB->>S2: GET /actuator/health HTTP/1.1
        S2-->>LB: 503 Service Unavailable
        LB->>TG: Mark Server 2: UNHEALTHY (2/2 - DEREGISTERED)
    end

    Note over LB,TG: Server 2 removed from rotation
    Note over LB,S1: ALL traffic now goes to Server 1 only
```

---

## LB-7: Auto Scaling Group (ASG) — Complete Guide

### Why Auto Scaling?

During events like **Big Billion Day, IPL ticket sales, or flash sales**, traffic is completely unpredictable. Manual scaling is too slow — by the time you manually launch instances, users are already experiencing failures.

```mermaid
flowchart TD
    subgraph NoASG["WITHOUT Auto Scaling"]
        NormalTraffic["Normal: 100 users → 2 instances OK"]
        SpikTraffic["Spike: 10,000 users → 2 instances CRASH"]
        ManualAction["Manual intervention:\n30 mins to launch new instances\nApp already down!"]

        NormalTraffic --> SpikTraffic --> ManualAction
    end

    subgraph WithASG["WITH Auto Scaling Group"]
        LowTraffic["Low Traffic: 2 instances running\n(saves cost)"]
        HighTraffic["Traffic spike detected\n(CPU > 70%)"]
        AutoScale["ASG Auto-launches\n5 more instances\nin 2-3 minutes!"]
        ScaleDown["Traffic drops\nASG terminates\nextra instances\n(saves cost)"]

        LowTraffic --> HighTraffic --> AutoScale --> ScaleDown --> LowTraffic
    end

    classDef bad fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef good fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class NormalTraffic,SpikTraffic,ManualAction bad;
    class LowTraffic,HighTraffic,AutoScale,ScaleDown good;
```

### ASG Core Concepts

| Concept | Description |
| :--- | :--- |
| **Minimum Capacity** | Minimum number of instances to always keep running (e.g., `Min = 2`) |
| **Desired Capacity** | The target number of instances ASG tries to maintain (e.g., `Desired = 3`) |
| **Maximum Capacity** | The maximum number of instances ASG can ever scale to (e.g., `Max = 10`) |
| **Launch Template** | Blueprint for new EC2 instances (AMI, instance type, key pair, SG, user data) |
| **Scaling Policy** | Rules that trigger scale-out or scale-in (CPU %, request count, schedule) |
| **Health Check** | ASG replaces any instance that fails EC2 or ELB health checks |
| **Cooldown Period** | Time ASG waits after a scaling activity before triggering another (default: 300s) |

### ASG with ALB — Complete Architecture

```mermaid
flowchart TB
    Internet["Internet Users"] --> ALB_ASG["Application Load Balancer\n(internet-facing, public subnets)"]

    subgraph ASG_Group["Auto Scaling Group (Min:2, Desired:3, Max:10)"]
        subgraph AZ1["AZ ap-south-1a (Private Subnet)"]
            EC2_A1["EC2 Instance\n(running App v1)"]
            EC2_A2["EC2 Instance\n(running App v1)"]
        end
        subgraph AZ2["AZ ap-south-1b (Private Subnet)"]
            EC2_B1["EC2 Instance\n(running App v1)"]
        end
        subgraph AZ3["AZ ap-south-1c (Private Subnet)"]
            EC2_C1["EC2 Instance\n(auto-launched on spike)"]
        end
    end

    CloudWatch["CloudWatch Alarm\nCPU > 70% for 2 periods"]
    ScalingPolicy["ASG Scaling Policy\nTarget Tracking: CPU at 60%"]

    ALB_ASG -->|Health check + Route| EC2_A1 & EC2_A2 & EC2_B1
    CloudWatch -->|Trigger| ScalingPolicy
    ScalingPolicy -->|Scale Out: Launch new instance| EC2_C1
    EC2_A1 & EC2_A2 & EC2_B1 & EC2_C1 -->|DB queries| RDS["RDS (Multi-AZ)"]

    classDef alb fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef asg fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef cw fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef rds fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef internet fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;

    class ALB_ASG alb;
    class EC2_A1,EC2_A2,EC2_B1,EC2_C1 asg;
    class CloudWatch,ScalingPolicy cw;
    class RDS rds;
    class Internet internet;
```

### Scaling Policy Types

| Policy Type | How It Works | Best For |
| :--- | :--- | :--- |
| **Target Tracking** | Maintains a target metric (e.g., CPU at 60%) — ASG automatically adds/removes instances | Most scenarios — simplest and smartest |
| **Step Scaling** | Adds/removes N instances based on metric threshold brackets | Custom scaling curves |
| **Simple Scaling** | Adds/removes N instances on a single alarm, then waits for cooldown | Basic setups |
| **Scheduled Scaling** | Scale to specific counts at specific times | Predictable patterns (e.g., scale up every Friday 9 AM) |
| **Predictive Scaling** | Uses ML to forecast traffic and pre-scales | When historical patterns exist |

### Scaling Lifecycle

```mermaid
sequenceDiagram
    autonumber
    participant CW as CloudWatch
    participant ASG as Auto Scaling Group
    participant LT as Launch Template
    participant EC2_New as New EC2 Instance
    participant TG as Target Group (ALB)

    Note over CW,ASG: Scale-Out Event (Traffic Spike)
    CW->>ASG: Alarm: CPU > 70% for 2 consecutive periods
    ASG->>LT: Read Launch Template (AMI, type, SG, user data)
    ASG->>EC2_New: Launch new EC2 instance
    EC2_New->>EC2_New: Run User Data script (install app, start service)
    EC2_New->>TG: Register with Target Group
    TG->>EC2_New: Health check: GET /actuator/health
    EC2_New-->>TG: 200 OK (instance healthy)
    TG->>EC2_New: Begin routing production traffic

    Note over CW,ASG: Scale-In Event (Traffic Drops)
    CW->>ASG: Alarm: CPU < 30% for 10 consecutive periods
    ASG->>TG: Deregister instance (connection draining begins)
    TG->>EC2_New: Stop sending NEW requests (finish in-flight)
    Note over TG,EC2_New: Wait deregistration delay (300s default)
    ASG->>EC2_New: Terminate instance
```

### ASG Benefits in Detail

| Benefit | Explanation | Real-world Example |
| :--- | :--- | :--- |
| **Fault Tolerance** | If an instance crashes or fails a health check, ASG automatically launches a replacement | Server OOM crash at 3 AM → ASG launches replacement in 2 mins without human intervention |
| **Cost Management** | Scale down at night / weekends → run only 2 instances instead of 10 → save ~80% compute cost | Staging environment scales to 0 instances overnight (scheduled scaling) |
| **High Availability** | Distributes instances across multiple AZs — if one AZ fails, others serve traffic | `ap-south-1a` goes down → ALB routes to `ap-south-1b` instances automatically |
| **Elasticity** | Handles unpredictable traffic spikes automatically | Big Billion Day: scales from 3 to 47 instances in minutes |

---

## LB-8: ALB + ASG + Target Group — Complete Production Setup

```mermaid
flowchart TD
    subgraph Public["Public Tier (Internet-Facing)"]
        Internet_Users["Internet Users\n(Browsers, Mobile Apps)"]
        ALB_Prod["Application Load Balancer\nHTTPS :443\nPath rules + WAF"]
    end

    subgraph Private["Private Tier (App Servers)"]
        ASG_Prod["Auto Scaling Group\nMin:2 | Desired:4 | Max:20\nLaunch Template: app-lt-v3"]

        subgraph AZ_A["AZ ap-south-1a"]
            I1["EC2 i-aaa1\nApp v3.2.1"]
            I2["EC2 i-aaa2\nApp v3.2.1"]
        end
        subgraph AZ_B["AZ ap-south-1b"]
            I3["EC2 i-bbb1\nApp v3.2.1"]
            I4["EC2 i-bbb2\nApp v3.2.1"]
        end
    end

    subgraph Data["Data Tier (Databases)"]
        RDS_Primary["RDS MySQL Primary\n(AZ ap-south-1a)"]
        RDS_Standby["RDS MySQL Standby\n(AZ ap-south-1b)"]
        Elasticache["ElastiCache Redis\n(Session Store)"]
    end

    subgraph Monitoring["Monitoring & Automation"]
        CW_ASG["CloudWatch\nAlarm: CPU > 65%"]
        Policy["ASG Scaling Policy\n(Target Tracking)"]
    end

    Internet_Users --> ALB_Prod
    ALB_Prod -->|Health check pass| I1 & I2 & I3 & I4
    I1 & I2 & I3 & I4 --> RDS_Primary & Elasticache
    RDS_Primary -->|Sync replication| RDS_Standby
    CW_ASG -->|Trigger| Policy
    Policy -->|Scale out/in| ASG_Prod

    classDef public fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef private fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef data fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef monitor fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;

    class Internet_Users,ALB_Prod public;
    class I1,I2,I3,I4,ASG_Prod private;
    class RDS_Primary,RDS_Standby,Elasticache data;
    class CW_ASG,Policy monitor;
```

---

## LB-9: Common Load Balancer & ASG Interview Questions

**Q1: Why do we need a Load Balancer?**
> **A:** Without a load balancer, all traffic hits a single server causing overload, slow responses, crashes, and zero high availability. A load balancer distributes incoming requests across multiple servers in round-robin (or other algorithms), reducing burden per server, improving response times, and ensuring if one server crashes, traffic automatically routes to healthy servers.

**Q2: What is the difference between ALB, NLB, and GWLB?**
> **A:** ALB (Layer 7) routes based on HTTP content (URL path, hostname, headers) — ideal for web apps and microservices. NLB (Layer 4) routes by IP/port only with ultra-low latency — ideal for gaming, IoT, trading. GWLB routes all IP traffic through third-party security appliances (firewalls) for inspection before reaching the app — used in compliance-heavy environments.

**Q3: What is a Target Group and how does it relate to ALB?**
> **A:** A Target Group is a logical collection of servers (EC2 instances, IPs, Lambda) that ALB routes traffic to. ALB listener rules map URL patterns to target groups. Each target group independently tracks health checks. One ALB can route to multiple target groups (one per microservice). Auto Scaling Groups register/deregister instances in target groups automatically.

**Q4: How does Auto Scaling work during a Big Billion Day sale?**
> **A:** Before the sale, ASG is pre-configured with Min=5, Desired=5, Max=50. CloudWatch alarms are set to trigger scale-out when CPU > 60%. As traffic grows, CloudWatch triggers the ASG scaling policy, which launches new EC2 instances from the Launch Template. Instances run the startup script (install app, start service), pass health checks, and the ALB starts routing traffic to them — all automatically within 2-3 minutes. After the sale, scale-in removes excess instances saving cost.

**Q5: What is Connection Draining (Deregistration Delay)?**
> **A:** When ASG removes an instance (scale-in or replacement), the ALB doesn't immediately terminate it. Instead, it stops routing NEW requests to that instance but allows existing in-flight requests to complete within the deregistration delay window (default: 300 seconds). After that, the instance is safely terminated. This prevents active user sessions from being abruptly dropped.

**Q6: What is the difference between Horizontal and Vertical Scaling?**
> **A:** Vertical Scaling means upgrading the same server to a bigger instance type (e.g., t3.micro → m5.xlarge) — limited by maximum instance size and requires downtime. Horizontal Scaling means adding more servers of the same size — unlimited scale, no downtime, and is what ASG implements. AWS best practice is always horizontal scaling + ALB.

**Q7: How does ALB health check work with Spring Boot?**
> **A:** Configure the ALB target group health check to hit `/actuator/health` (Spring Boot Actuator endpoint). The endpoint returns `{"status":"UP"}` with HTTP 200. ALB checks this endpoint every 30 seconds (configurable). If an instance fails the check 2 consecutive times, ALB marks it unhealthy and stops sending traffic. Once it passes 2 consecutive checks, it's marked healthy again.

**Q8: What happens if all instances in an ASG fail health checks?**
> **A:** If all instances are unhealthy, the ASG will continuously attempt to replace them by launching new instances from the Launch Template. The ALB will return 503 (No healthy targets) to users during this period. You should investigate the root cause (bad AMI, app crash, DB connection failure) via CloudWatch logs and EC2 system logs.

---

## LB-10: Best Practices

| Area | Best Practice |
| :--- | :--- |
| **Security** | Always use HTTPS (port 443) on ALB; terminate SSL at ALB; redirect HTTP to HTTPS |
| **Health Checks** | Use application-level health checks (`/actuator/health`) not just TCP ping |
| **Stickiness** | Avoid sticky sessions; use Redis/ElastiCache for session state instead |
| **Availability** | Always configure ALB across **minimum 2 AZs** for high availability |
| **ASG Sizing** | Set Min ≥ 2 (ensures no SPOF), Desired = current normal load, Max = peak capacity |
| **Launch Template** | Always use Launch Templates (not Launch Configurations — deprecated) |
| **Scaling Policy** | Use Target Tracking Scaling (CPU at 60-70%) as the primary policy |
| **Cooldown Period** | Set cooldown to at least equal to instance boot + app startup time |
| **Cross-Zone LB** | Enable cross-zone load balancing to prevent uneven distribution |
| **Access Logs** | Enable ALB access logs to S3 for audit, debugging, and analytics |
| **WAF** | Attach AWS WAF to ALB to protect against SQL injection, XSS, and DDoS |

---
---

## TOPIC 9: RDS — RELATIONAL DATABASE SERVICE

### 1. Concept Explanation

#### Beginner
Relational Database Service (RDS) is a managed database service. AWS handles database provisioning, operating system patching, automated backups, and storage scaling, allowing you to focus on schema optimization and queries.

Supported Database Engines:
* Amazon Aurora, PostgreSQL, MySQL, MariaDB, Oracle, Microsoft SQL Server.

#### Intermediate
##### High Availability vs. Scale Comparisons
| Feature | RDS Multi-AZ | RDS Read Replicas |
| :--- | :--- | :--- |
| **Primary Goal** | Disaster Recovery / High Availability | Scalability for read-heavy workloads |
| **Replication Type** | Synchronous | Asynchronous |
| **Active Connections** | Only the primary instance accepts queries. The standby is inactive. | Read replicas accept read-only queries (e.g., reports, searches). |
| **Failover Mechanism** | Automated failover (DNS updates automatically) | Manual promotion to a standalone primary database |
| **AZ Scope** | Spans 2 Availability Zones | Spans multiple AZs or cross-region |

##### Spring Boot Configuration Example (`application.yml`)
```yaml
spring:
  datasource:
    url: jdbc:mysql://database-prod-endpoint.ap-south-1.rds.amazonaws.com:3306/policydb?useSSL=true
    username: ${DB_USER}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      idle-timeout: 300000
      connection-timeout: 20000
      max-lifetime: 1800000
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
```

#### Advanced
* **Amazon Aurora:** A cloud-native database engine. It replicates data 6 ways across 3 availability zones and auto-scales storage up to 128 TB.
* **RDS Proxy:** An intermediate database proxy that pools database connections. This is useful for serverless applications (like Lambda) that open and close connections frequently, preventing connection exhaustion.

##### RDS Hands-On Lab Task
Step-by-step to create and validate a MySQL RDS instance:

1. **Create MySQL database using RDS:**
   * Creation method: `Standard create`
   * Engine type: `MySQL`
   * Template: `Free tier`
   * DB instance identifier: `teluskodb`
   * Public access: `Yes` *(for learning purposes only — not for production)*
   * Initial database name: `teluskodatabase`

2. **Connect via MySQL Workbench:**
   * Endpoint: `<RDS Endpoint from Console>`
   * Username, Password, Port: `3306`
   * Enable port `3306` in the RDS Security Group inbound rules.

3. **Validate with Sample SQL:**
```sql
USE teluskodatabase;

CREATE TABLE employee (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2)
);

INSERT INTO employee (id, name, salary)
VALUES (1, 'Alice', 55000.00),
       (2, 'Bob', 60000.50),
       (3, 'Carol', 48000.25);

SELECT * FROM employee;
```

> [!WARNING]
> Always disable **Public Access** for production RDS instances. Use private subnets and VPC peering or a bastion host for secure database connectivity.

### 2. Interview Questions & Answers

#### Q: How do you optimize and debug slow database queries on RDS?
**A:** 
1. Enable **RDS Performance Insights** to identify SQL queries with high CPU load or I/O wait times.
2. Enable the **Slow Query Log** in the RDS parameter group and export it to CloudWatch Logs.
3. Use `EXPLAIN` on slow queries to identify missing indexes or full table scans.
4. Scale reads horizontally by adding **Read Replicas** and routing read-only queries to them.
5. Upgrade storage from `gp3` to `io2` if the database is throttled by IOPS limits.

### 3. Key Takeaways
* RDS Multi-AZ provides synchronous replication and automated failover for disaster recovery. Read Replicas provide asynchronous replication for read scalability.
* RDS Proxy manages connection pooling, preventing serverless functions from overwhelming the database connection limit.
* Enable Performance Insights and slow query logging to identify database bottlenecks.



---

## RDS COMPREHENSIVE DEEP DIVE

### Why Do We Need a Database?

A database is software that **stores data permanently** — even after the application restarts or the server shuts down.

**Types of Databases:**

| Type | Structure | Examples | Use Case |
| :--- | :--- | :--- | :--- |
| **Relational (RDBMS)** | Tables → Rows & Columns (structured) | MySQL, PostgreSQL, Oracle, SQL Server, Aurora | Banking, e-commerce, ERP, HR systems |
| **NoSQL** | Documents, Key-Value, Graph (flexible schema) | MongoDB, Cassandra, DynamoDB, Redis | Social media, real-time analytics, IoT |

Every application needs a database — whether relational or NoSQL — to persist user data, transactions, logs, and configuration.

---

### Problems with On-Premises (On-Prem) Databases

Before cloud databases, every company managed their own database servers. This was painful:

```mermaid
flowchart TD
    subgraph OnPrem["On-Premises Database Challenges"]
        License["Purchase DB Server License\n(Oracle = $25,000+ per core/year)"]
        Install["Install DB Server Software\n(DBA expertise required)"]
        Network["Configure Network\n(VLANs, firewalls, latency tuning)"]
        Security["Manage Security\n(patches, encryption, audits)"]
        Backup["Handle Backups\n(manual scripts, tape drives)"]
        Admin["24/7 Administration\n(monitoring, tuning, on-call)"]
        Hardware["Buy Physical Hardware\n(servers, SAN storage, rack space)"]
        HA["Build High Availability\n(clustering, replication, failover)"]
    end

    Hardware --> Install --> License --> Network --> Security --> Backup --> Admin --> HA
    HA --> Problem["Result: High Cost + High Complexity\n+ Slow Setup + 24/7 DBA Team Required"]

    classDef prob fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef step fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;

    class Problem prob;
    class License,Install,Network,Security,Backup,Admin,Hardware,HA step;
```

**On-Prem Database Limitations:**

| Challenge | Impact |
| :--- | :--- |
| **Purchase DB License** | Oracle DB license can cost $25,000+ per CPU core per year |
| **Install Database Software** | Requires certified DBAs — days of setup work |
| **Network Configuration** | Complex VLAN, firewall, and latency tuning |
| **Security Management** | Patches, encryption setup, audit logging — constant overhead |
| **Backup Management** | Manual backup scripts, tape drives, offsite storage |
| **Administration** | 24/7 monitoring, query tuning, on-call DBA teams |
| **Hardware Procurement** | Buying servers takes weeks — cannot scale quickly |
| **High Availability** | Building HA clustering requires duplicate hardware investment |

---

### What is AWS RDS?

**Amazon RDS (Relational Database Service)** is a **fully managed** cloud database service that handles all the operational burden above so you can focus on your application.

```mermaid
flowchart LR
    subgraph Without["Without RDS (On-Premises)"]
        Dev1["Developer spends time on:\nDB installation\nPatching\nBackups\nHA setup\nMonitoring"]
    end

    subgraph With["With AWS RDS (Fully Managed)"]
        Dev2["Developer focuses on:\nSchema design\nQuery optimization\nApplication logic"]
        AWS_RDS["AWS RDS Handles:\nProvisioning\nOS patching\nAutomated backups\nMulti-AZ HA\nMonitoring\nStorage scaling"]
    end

    classDef bad fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef good fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef aws fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;

    class Dev1 bad;
    class Dev2 good;
    class AWS_RDS aws;
```

**RDS Key Characteristics:**

| Property | Details |
| :--- | :--- |
| **Fully Managed** | AWS handles provisioning, patching, backups, monitoring |
| **Pay As You Go** | Pay only for instance hours + storage used |
| **Multi-Engine** | Supports MySQL, PostgreSQL, Oracle, SQL Server, MariaDB, Aurora |
| **High Availability** | Multi-AZ with automatic failover |
| **Read Scalability** | Read Replicas for horizontal read scaling |
| **Automated Backups** | Daily snapshots + transaction logs (point-in-time recovery) |
| **Security** | VPC isolation, security groups, KMS encryption, SSL/TLS |
| **Monitoring** | CloudWatch metrics + Performance Insights built-in |

---

## RDS-1: Supported Database Engines

```mermaid
flowchart TD
    RDS_Engine["Amazon RDS\nSupported Engines"]

    RDS_Engine --> Aurora["Amazon Aurora\n(AWS Cloud-native)\nMySQL + PostgreSQL compatible\n5x faster than MySQL\n3x faster than PostgreSQL\nStorage auto-scales to 128 TB\n6-way replication across 3 AZs"]

    RDS_Engine --> MySQL_DB["MySQL\nMost popular open-source RDBMS\nFree tier eligible\nVersion: 8.0, 5.7"]

    RDS_Engine --> Postgres["PostgreSQL\nAdvanced open-source RDBMS\nBest for complex queries\nVersion: 15, 14, 13"]

    RDS_Engine --> Oracle_DB["Oracle Database\nEnterprise-grade\nBYOL or License Included\nVersion: 19c, 21c"]

    RDS_Engine --> MSSQL["Microsoft SQL Server\nWindows-native enterprise DB\nExpress, Web, Standard, Enterprise editions"]

    RDS_Engine --> MariaDB["MariaDB\nMySQL fork - open source\nDrop-in MySQL replacement"]

    classDef rds fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef aurora fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef engine fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;

    class RDS_Engine rds;
    class Aurora aurora;
    class MySQL_DB,Postgres,Oracle_DB,MSSQL,MariaDB engine;
```

| Engine | Best For | Free Tier? | Max Storage |
| :--- | :--- | :---: | :--- |
| **Amazon Aurora MySQL** | High-performance cloud-native apps | No | 128 TB (auto-scales) |
| **Amazon Aurora PostgreSQL** | Complex analytics + cloud-native | No | 128 TB (auto-scales) |
| **MySQL 8.0** | Web apps, Spring Boot, WordPress | YES (db.t3.micro) | 64 TB |
| **PostgreSQL 15** | Advanced queries, GIS, JSON | YES (db.t3.micro) | 64 TB |
| **Oracle 19c** | Enterprise legacy apps, SAP | No | 64 TB |
| **SQL Server** | .NET apps, Windows workloads | No | 16 TB |
| **MariaDB** | MySQL-compatible open-source | YES (db.t3.micro) | 64 TB |

---

## RDS-2: RDS Architecture Overview

```mermaid
flowchart TB
    subgraph Internet_Clients["Client Applications"]
        App["Spring Boot App\n(EC2 / ECS / Lambda)"]
        DBA["DBA Tools\n(MySQL Workbench, DBeaver)"]
        Reports["Reporting Tool\n(Tableau, Grafana)"]
    end

    subgraph VPC["VPC (Private Network)"]
        subgraph PublicSubnet["Public Subnet"]
            Bastion["Bastion Host\n(SSH jump server for DBA access)"]
        end

        subgraph PrivateSubnet_A["Private Subnet - AZ ap-south-1a"]
            RDS_Primary["RDS Primary Instance\n(Accepts all reads & writes)\ndb.m5.large - MySQL 8.0"]
        end

        subgraph PrivateSubnet_B["Private Subnet - AZ ap-south-1b"]
            RDS_Standby["RDS Standby Instance\n(Multi-AZ passive standby)\nSynchronous replication"]
        end

        subgraph PrivateSubnet_C["Private Subnet - AZ ap-south-1a/b"]
            ReadReplica["RDS Read Replica\n(Read-only queries)\nAsynchronous replication"]
        end

        SG_RDS["Security Group (RDS)\nInbound: TCP 3306 from App SG only"]
        RDS_Proxy_Node["RDS Proxy\n(Connection Pooling)\nfor Lambda & high-concurrency apps"]
    end

    S3_Backup["S3 (Automated Backups)\nPoint-in-time recovery\nRetention: 7 days"]
    CW_RDS["CloudWatch\nPerformance Insights\nSlow Query Logs"]

    App --> RDS_Proxy_Node --> SG_RDS --> RDS_Primary
    DBA --> Bastion --> SG_RDS --> RDS_Primary
    Reports --> ReadReplica
    RDS_Primary -->|Sync replication| RDS_Standby
    RDS_Primary -->|Async replication| ReadReplica
    RDS_Primary -->|Automated backups| S3_Backup
    RDS_Primary --> CW_RDS

    classDef client fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef primary fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef standby fill:#0369A1,stroke:#0EA5E9,color:#FFFFFF,stroke-width:2px;
    classDef replica fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef infra fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef monitor fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;

    class App,DBA,Reports client;
    class RDS_Primary,RDS_Proxy_Node primary;
    class RDS_Standby standby;
    class ReadReplica replica;
    class Bastion,SG_RDS infra;
    class S3_Backup,CW_RDS monitor;
```

---

## RDS-3: Multi-AZ — High Availability

### What is RDS Multi-AZ?

Multi-AZ deploys a **synchronous standby replica** of your database in a different Availability Zone. This provides automatic failover — if the primary instance fails, AWS automatically switches to the standby.

```mermaid
sequenceDiagram
    autonumber
    participant App as Spring Boot Application
    participant DNS as RDS Endpoint (DNS)
    participant Primary as RDS Primary (AZ-1a)
    participant Standby as RDS Standby (AZ-1b)
    participant CW as CloudWatch

    Note over App,Standby: Normal Operation
    App->>DNS: Resolve DB endpoint
    DNS-->>App: Points to Primary (AZ-1a)
    App->>Primary: INSERT / UPDATE / SELECT
    Primary->>Standby: Synchronous replication (every write)
    Standby-->>Primary: ACK (write confirmed)
    Primary-->>App: Write confirmed

    Note over Primary,Standby: Failure Event!
    Primary--xApp: Primary instance fails (hardware/AZ outage)
    CW->>DNS: Detect failure via health check
    DNS->>Standby: Update DNS to point to Standby (AZ-1b)
    Note over DNS: Failover takes 60-120 seconds
    App->>DNS: Resolve DB endpoint again
    DNS-->>App: Now points to Standby (promoted to Primary)
    App->>Standby: Normal operations resume
    Note over App: Application reconnects - NO code changes needed
```

**Multi-AZ Key Points:**

| Property | Details |
| :--- | :--- |
| **Replication Type** | Synchronous — every write to primary is instantly replicated to standby |
| **Standby Status** | PASSIVE — cannot accept reads or writes (not a read replica) |
| **Failover Time** | 60–120 seconds (DNS TTL update) |
| **DNS Change** | Same endpoint URL — application reconnects automatically |
| **Use Case** | Disaster Recovery / High Availability (not for scaling reads) |
| **Cost** | ~2x the cost of single-AZ (you pay for two instances) |
| **Zero Data Loss** | Synchronous replication means no data loss on failover |

> [!IMPORTANT]
> Multi-AZ is for **availability**, not **performance**. The standby instance does NOT serve any traffic during normal operation. To scale reads, use **Read Replicas** instead.

---

## RDS-4: Read Replicas — Scalability

### What are Read Replicas?

Read Replicas are **asynchronous copies** of the primary database that can serve **read-only** SQL queries (SELECT statements). They scale read throughput horizontally.

```mermaid
flowchart TD
    subgraph Writes["Write Traffic (INSERT/UPDATE/DELETE)"]
        AppWrite["Spring Boot App\n(Write Requests)"]
    end

    subgraph PrimaryDB["Primary RDS Instance"]
        Primary["RDS Primary\n(All writes go here)"]
    end

    subgraph ReadLayer["Read Scaling Layer (Asynchronous Replicas)"]
        RR1["Read Replica 1\n(ap-south-1b)\nReporting queries"]
        RR2["Read Replica 2\n(ap-south-1c)\nSearch queries"]
        RR3["Read Replica 3\n(us-east-1)\nCross-region DR"]
    end

    subgraph ReadApps["Read-Only Consumers"]
        Dashboard["Analytics Dashboard"]
        Search["Search Service"]
        Reports["Report Generator"]
    end

    AppWrite -->|"Write: INSERT/UPDATE"| Primary
    Primary -->|"Async replication\n(slight lag)"| RR1 & RR2 & RR3
    RR1 --> Dashboard
    RR2 --> Search
    RR3 --> Reports

    classDef write fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef primary fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef replica fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef reader fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;

    class AppWrite write;
    class Primary primary;
    class RR1,RR2,RR3 replica;
    class Dashboard,Search,Reports reader;
```

**Read Replica Key Points:**

| Property | Details |
| :--- | :--- |
| **Replication Type** | Asynchronous — slight replication lag possible (milliseconds to seconds) |
| **Status** | ACTIVE — accepts SELECT queries |
| **Max Replicas** | Up to 15 read replicas (MySQL/PostgreSQL) |
| **Promotion** | A read replica can be manually promoted to a standalone primary |
| **Cross-Region** | Read replicas can be in a different AWS region |
| **Use Case** | Offload read-heavy queries, analytics, reporting, search |
| **Endpoint** | Separate read endpoint — app must explicitly route reads there |

---

## RDS-5: Multi-AZ vs Read Replicas — Side-by-Side

```mermaid
flowchart LR
    subgraph MultiAZ["Multi-AZ Deployment"]
        P1["Primary (AZ-a)\nAll reads + writes"]
        S1["Standby (AZ-b)\nNO traffic (passive)"]
        P1 -->|"Synchronous\n(zero lag)"| S1
    end

    subgraph ReadReplica["Read Replica Setup"]
        P2["Primary (AZ-a)\nAll writes"]
        RR_A["Read Replica (AZ-b)\nRead-only traffic"]
        RR_B["Read Replica (AZ-c)\nRead-only traffic"]
        P2 -->|"Asynchronous\n(slight lag)"| RR_A & RR_B
    end

    classDef primary fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef standby fill:#374151,stroke:#9CA3AF,color:#FFFFFF,stroke-width:2px;
    classDef replica fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class P1,P2 primary;
    class S1 standby;
    class RR_A,RR_B replica;
```

| Feature | Multi-AZ | Read Replica |
| :--- | :--- | :--- |
| **Primary Goal** | High Availability / Disaster Recovery | Read Scalability |
| **Replication Type** | Synchronous (zero lag) | Asynchronous (slight lag) |
| **Standby/Replica accepts queries?** | NO (passive standby) | YES (SELECT only) |
| **Failover** | Automatic (60-120s DNS switch) | Manual promotion required |
| **Number of copies** | 1 standby only | Up to 15 read replicas |
| **Cross-region?** | NO (same region, different AZ) | YES (cross-region replicas) |
| **Use for performance?** | NO | YES |
| **Use for DR?** | YES | YES (if promoted) |
| **Extra cost** | ~2x (always running standby) | Pay per replica instance |

> [!TIP]
> **Interview Answer:** Multi-AZ = High Availability (automatic failover). Read Replica = Read Scalability (manual promotion needed). Production systems use **both together** — Multi-AZ for HA, Read Replicas for performance.

---

## RDS-6: RDS Lab Setup — Step by Step (From Class Reference)

### Creating a MySQL RDS Instance

```mermaid
flowchart TD
    A["Step 1: Open AWS Console\nNavigate: RDS → Databases → Create database"] --> B["Step 2: Choose Creation Method\nStandard Create (full options visible)"]
    B --> C["Step 3: Select Engine\nMySQL 8.0"]
    C --> D["Step 4: Choose Template\nFree Tier (for learning)"]
    D --> E["Step 5: Configure Instance\nDB Instance: teluskodb\nMaster Username: admin\nMaster Password: yourpassword"]
    E --> F["Step 6: Instance Config\nClass: db.t3.micro (Free Tier)\nStorage: 20 GB gp2"]
    F --> G["Step 7: Connectivity\nVPC: Default VPC\nPublic Access: YES (learning only!)\nSecurity Group: Create new"]
    G --> H["Step 8: Database Options\nInitial DB Name: teluskodatabase"]
    H --> I["Step 9: Create Database\nWait 5-10 mins for provisioning"]
    I --> J["Step 10: Get Connection Details\nEndpoint: teluskodb.xxxxx.ap-south-1.rds.amazonaws.com\nPort: 3306"]

    classDef step fill:#1E293B,stroke:#38BDF8,color:#F8FAFC,stroke-width:2px;
    classDef final fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;

    class A,B,C,D,E,F,G,H,I step;
    class J final;
```

### RDS Configuration Parameters Explained

| Parameter | Value Used | Explanation |
| :--- | :--- | :--- |
| **Creation Method** | Standard Create | Exposes all configuration options (vs Easy Create which uses defaults) |
| **Engine Type** | MySQL | Most widely used open-source RDBMS, free tier eligible |
| **Template** | Free Tier | Locks to `db.t3.micro`, disables Multi-AZ, enables 20 GB free storage |
| **DB Instance Identifier** | `teluskodb` | The name of the RDS instance (NOT the database name) |
| **Master Username** | `admin` | The root-level database user AWS creates |
| **Public Access** | YES | Allows connection from outside the VPC (use ONLY for learning, NEVER in production) |
| **Initial DB Name** | `teluskodatabase` | The first schema/database created inside the MySQL server |

> [!CAUTION]
> **Public Access: YES** is only for learning in isolation. In production, ALWAYS set Public Access to **NO** and connect to RDS from within the same VPC using private subnets only.

---

### Connecting with MySQL Workbench

**Prerequisites:**
1. Get **RDS Endpoint** from AWS Console → RDS → Databases → `teluskodb` → Connectivity & security tab
2. Enable port `3306` in the **Security Group** inbound rules for the RDS instance

```mermaid
flowchart LR
    Workbench["MySQL Workbench\n(Your local machine)"]
    Internet["Internet"]
    SG["Security Group\nInbound: TCP 3306\nfrom 0.0.0.0/0 (learning only)"]
    RDS_Mysql["RDS MySQL Instance\nteluskodb.xxxxx.rds.amazonaws.com:3306"]

    Workbench -->|TCP 3306| Internet --> SG --> RDS_Mysql

    classDef client fill:#581C87,stroke:#A855F7,color:#FFFFFF,stroke-width:2px;
    classDef sg fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;
    classDef rds fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;

    class Workbench,Internet client;
    class SG sg;
    class RDS_Mysql rds;
```

**MySQL Workbench Connection Details:**
```
Hostname  : teluskodb.xxxxxxxxxxxxxxx.ap-south-1.rds.amazonaws.com
Port      : 3306
Username  : admin
Password  : <your RDS master password>
Schema    : teluskodatabase (optional at connection time)
```

---

### Validate RDS with Sample SQL (From Class)

```sql
-- Switch to the database
USE teluskodatabase;

-- Create employee table
CREATE TABLE employee (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    salary DECIMAL(10,2)
);

-- Insert sample data (Navin, Sana, Amit from class)
INSERT INTO employee (id, name, salary)
VALUES (1, 'Navin', 55000.00),
       (2, 'Sana', 60000.50),
       (3, 'Amit', 48000.25);

-- Verify the data
SELECT * FROM employee;

-- Additional sample queries
SELECT name, salary FROM employee WHERE salary > 50000;
SELECT COUNT(*) AS total_employees FROM employee;
SELECT AVG(salary) AS average_salary FROM employee;
```

**Expected Output of `SELECT * FROM employee`:**
```
+----+-------+----------+
| id | name  | salary   |
+----+-------+----------+
|  1 | Navin | 55000.00 |
|  2 | Sana  | 60000.50 |
|  3 | Amit  | 48000.25 |
+----+-------+----------+
3 rows in set (0.05 sec)
```

---

## RDS-7: Security Group Setup for RDS

```mermaid
flowchart TD
    subgraph Learning["Learning Environment (Public Access)"]
        LocalPC["Local Developer PC\nMySQL Workbench"]
        SG_Learn["RDS Security Group\nInbound: TCP 3306 from 0.0.0.0/0\n(ONLY for learning - not production!)"]
        RDS_Learn["RDS MySQL Instance\nPublic Access: YES"]

        LocalPC --> SG_Learn --> RDS_Learn
    end

    subgraph Production["Production Environment (Private Access)"]
        AppServer["EC2 / ECS App Server\nSecurity Group: app-sg"]
        SG_Prod["RDS Security Group\nInbound: TCP 3306 from app-sg ONLY\n(No public internet access)"]
        RDS_Prod["RDS MySQL Instance\nPublic Access: NO\nPrivate Subnet Only"]
        Bastion_Prod["Bastion Host\n(for DBA access only via SSH tunnel)"]

        AppServer --> SG_Prod --> RDS_Prod
        Bastion_Prod -.->|SSH tunnel :3306| RDS_Prod
    end

    classDef learning fill:#0369A1,stroke:#0EA5E9,color:#FFFFFF,stroke-width:2px;
    classDef prod fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef danger fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;

    class LocalPC,SG_Learn,RDS_Learn learning;
    class AppServer,SG_Prod,RDS_Prod,Bastion_Prod prod;
```

**Setting Up Security Group for RDS:**

```bash
# CLI: Allow your IP to connect to RDS on port 3306 (learning only)
aws ec2 authorize-security-group-ingress \
  --group-id sg-xxxxxxxxxx \
  --protocol tcp \
  --port 3306 \
  --cidr <YOUR-PUBLIC-IP>/32

# Production: Allow only the app server security group
aws ec2 authorize-security-group-ingress \
  --group-id sg-rds-xxxx \
  --protocol tcp \
  --port 3306 \
  --source-group sg-app-xxxx
```

---

## RDS-8: RDS Automated Backups & Snapshots

```mermaid
flowchart TD
    RDS_Primary_BK["RDS Primary Instance"]

    subgraph AutoBackup["Automated Backups (AWS Managed)"]
        Daily["Daily Snapshot\n(during backup window: 3-4 AM)"]
        TransLog["Transaction Logs\n(every 5 minutes)"]
        PITR["Point-in-Time Recovery\nRestore to any second within\nretention period (1-35 days)"]
    end

    subgraph Manual["Manual Snapshots (User Managed)"]
        ManualSnap["Manual DB Snapshot\n(taken before major changes)"]
        ManualRetain["Kept INDEFINITELY\n(until manually deleted)"]
        CrossRegion["Can be copied\nto another region"]
    end

    RDS_Primary_BK --> Daily & TransLog --> PITR
    RDS_Primary_BK --> ManualSnap --> ManualRetain & CrossRegion

    classDef rds fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef auto fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef manual fill:#0369A1,stroke:#0EA5E9,color:#FFFFFF,stroke-width:2px;

    class RDS_Primary_BK rds;
    class Daily,TransLog,PITR auto;
    class ManualSnap,ManualRetain,CrossRegion manual;
```

| Backup Type | Triggered By | Retention | Use Case |
| :--- | :--- | :--- | :--- |
| **Automated Backup** | AWS (daily during backup window) | 1–35 days (configurable) | Point-in-time recovery |
| **Manual Snapshot** | You (on-demand) | Indefinitely (until deleted) | Before major deploys, compliance |
| **Transaction Logs** | Continuous (every 5 min) | Same as backup retention | PITR to exact second |

```bash
# Create a manual snapshot before a major deployment
aws rds create-db-snapshot \
  --db-instance-identifier teluskodb \
  --db-snapshot-identifier teluskodb-before-v2-deploy

# Restore from snapshot (creates new RDS instance)
aws rds restore-db-instance-from-db-snapshot \
  --db-instance-identifier teluskodb-restored \
  --db-snapshot-identifier teluskodb-before-v2-deploy

# List all snapshots
aws rds describe-db-snapshots \
  --db-instance-identifier teluskodb
```

> [!TIP]
> Always take a **manual snapshot before any major deployment** or schema migration. Automated backups exist, but a manual snapshot before a specific event gives you a clean restore point tied to that exact moment.

---

## RDS-9: Amazon Aurora — Cloud-Native Database

Amazon Aurora is AWS's own cloud-native database engine, compatible with MySQL and PostgreSQL but significantly more powerful.

```mermaid
flowchart TD
    subgraph AuroraArch["Amazon Aurora Architecture"]
        subgraph Compute["Compute Layer (Instances)"]
            AuroraPrimary["Aurora Primary Writer\n(reads + writes)"]
            AuroraR1["Aurora Reader 1\n(reads only)"]
            AuroraR2["Aurora Reader 2\n(reads only)"]
        end

        subgraph Storage["Aurora Storage Layer (Auto-scales)"]
            Seg1["Storage Segment\nAZ-1a Copy 1"]
            Seg2["Storage Segment\nAZ-1a Copy 2"]
            Seg3["Storage Segment\nAZ-1b Copy 1"]
            Seg4["Storage Segment\nAZ-1b Copy 2"]
            Seg5["Storage Segment\nAZ-1c Copy 1"]
            Seg6["Storage Segment\nAZ-1c Copy 2"]
            Note1["6 copies across 3 AZs\nAuto-scales: 10 GB to 128 TB\n11 nines durability"]
        end
    end

    AuroraPrimary & AuroraR1 & AuroraR2 --> Seg1 & Seg2 & Seg3 & Seg4 & Seg5 & Seg6

    classDef writer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef reader fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef storage fill:#0369A1,stroke:#0EA5E9,color:#FFFFFF,stroke-width:2px;
    classDef note fill:#374151,stroke:#9CA3AF,color:#FFFFFF,stroke-width:2px;

    class AuroraPrimary writer;
    class AuroraR1,AuroraR2 reader;
    class Seg1,Seg2,Seg3,Seg4,Seg5,Seg6 storage;
    class Note1 note;
```

**Aurora vs Standard RDS MySQL:**

| Feature | RDS MySQL | Amazon Aurora MySQL |
| :--- | :--- | :--- |
| **Performance** | 1x baseline | **5x faster** than standard MySQL |
| **Storage** | Manual sizing up to 64 TB | Auto-scales 10 GB → **128 TB** |
| **Replication** | 1 standby (Multi-AZ) | **6 copies** across 3 AZs automatically |
| **Read Replicas** | Up to 5 | Up to **15 Aurora Read Replicas** |
| **Failover Time** | 60–120 seconds | **Under 30 seconds** |
| **Durability** | 99.99% (Multi-AZ) | **99.999999999%** (11 nines) |
| **Serverless** | Not available | **Aurora Serverless v2** (auto-scales compute) |
| **Cost** | Lower | ~20% higher (worth it for production) |

---

## RDS-10: RDS Proxy — Connection Pooling

### The Problem: Lambda + RDS Connection Exhaustion

```mermaid
sequenceDiagram
    autonumber
    participant Lambda1 as Lambda Instance 1
    participant Lambda2 as Lambda Instance 2
    participant LambdaN as Lambda Instance N (1000s)
    participant RDS_Proxy_Sq as RDS Proxy
    participant RDS_DB as RDS MySQL (max 1000 connections)

    Note over Lambda1,RDS_DB: WITHOUT RDS Proxy (Problem)
    Lambda1->>RDS_DB: Open DB connection
    Lambda2->>RDS_DB: Open DB connection
    LambdaN->>RDS_DB: Open DB connection
    RDS_DB-->>LambdaN: Too many connections! ERROR

    Note over Lambda1,RDS_DB: WITH RDS Proxy (Solution)
    Lambda1->>RDS_Proxy_Sq: Open connection to Proxy
    Lambda2->>RDS_Proxy_Sq: Open connection to Proxy
    LambdaN->>RDS_Proxy_Sq: Open connection to Proxy
    Note over RDS_Proxy_Sq: Proxy maintains a small pool\nof real DB connections (e.g., 50)
    RDS_Proxy_Sq->>RDS_DB: Reuse pooled connections (50 max)
    RDS_DB-->>RDS_Proxy_Sq: Responses (no connection exhaustion)
    RDS_Proxy_Sq-->>Lambda1: Response
    RDS_Proxy_Sq-->>Lambda2: Response
```

**When to Use RDS Proxy:**

| Scenario | Without Proxy | With Proxy |
| :--- | :--- | :--- |
| 1000 Lambda concurrent invocations | 1000 DB connections (exhaustion!) | ~50 pooled connections (safe) |
| ECS service with 200 containers | 200 connections | ~20 pooled connections |
| Connection spike after auto-scale | Connection storm crashes DB | Proxy absorbs the spike |
| Failover during Multi-AZ event | App must reconnect (60-120s) | Proxy handles reconnection (~30s) |

---

## RDS-11: Spring Boot Integration with RDS

### application.yml — Production Configuration

```yaml
spring:
  datasource:
    # RDS endpoint from AWS Console → RDS → Databases → Connectivity tab
    url: jdbc:mysql://teluskodb.xxxxxxxx.ap-south-1.rds.amazonaws.com:3306/teluskodatabase?useSSL=true&requireSSL=true&serverTimezone=UTC
    username: ${DB_USERNAME}       # From AWS Secrets Manager or environment variable
    password: ${DB_PASSWORD}       # From AWS Secrets Manager or environment variable
    driver-class-name: com.mysql.cj.jdbc.Driver
    hikari:
      maximum-pool-size: 10        # Max connections per instance
      minimum-idle: 2              # Keep 2 connections warm
      idle-timeout: 300000         # 5 minutes idle before closing
      connection-timeout: 20000    # 20s timeout to get a connection from pool
      max-lifetime: 1800000        # 30 min max connection lifetime

  jpa:
    hibernate:
      ddl-auto: validate           # Production: NEVER use create or create-drop
    show-sql: false                # Performance: disable SQL logging in production
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
        jdbc:
          batch_size: 25           # Batch inserts for performance
        order_inserts: true
        order_updates: true

```

### pom.xml Dependencies

```xml
<!-- MySQL JDBC Driver -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Spring Boot Actuator for health checks -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### Entity + Repository + Service (Full Example)

```java
// 1. Entity (maps to RDS table)
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    // constructors, getters, setters
}

// 2. Repository (Spring Data JPA)
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findBySalaryGreaterThan(BigDecimal salary);

    @Query("SELECT e FROM Employee e WHERE e.name = :name")
    Optional<Employee> findByName(@Param("name") String name);
}

// 3. Service (business logic)
@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);    // INSERT INTO employee
    }

    @Transactional(readOnly = true)  // Routes to Read Replica if configured
    public List<Employee> getHighEarners(BigDecimal threshold) {
        return employeeRepository.findBySalaryGreaterThan(threshold);
    }
}
```

---

## RDS-12: RDS CLI Commands

```bash
# ─────────────────────────────────────
# INSTANCE MANAGEMENT
# ─────────────────────────────────────

# List all RDS instances
aws rds describe-db-instances \
  --query 'DBInstances[*].[DBInstanceIdentifier,DBInstanceStatus,Endpoint.Address]' \
  --output table

# Create MySQL RDS instance (CLI equivalent of console lab)
aws rds create-db-instance \
  --db-instance-identifier teluskodb \
  --db-instance-class db.t3.micro \
  --engine mysql \
  --engine-version 8.0 \
  --master-username admin \
  --master-user-password YourPassword123! \
  --allocated-storage 20 \
  --db-name teluskodatabase \
  --publicly-accessible \
  --no-multi-az \
  --backup-retention-period 7

# Get RDS endpoint (to connect from app or workbench)
aws rds describe-db-instances \
  --db-instance-identifier teluskodb \
  --query 'DBInstances[0].Endpoint.Address' \
  --output text

# ─────────────────────────────────────
# SCALING
# ─────────────────────────────────────

# Vertically scale (upgrade instance type)
aws rds modify-db-instance \
  --db-instance-identifier teluskodb \
  --db-instance-class db.m5.large \
  --apply-immediately

# Create a read replica
aws rds create-db-instance-read-replica \
  --db-instance-identifier teluskodb-read-replica-1 \
  --source-db-instance-identifier teluskodb \
  --db-instance-class db.t3.micro

# ─────────────────────────────────────
# BACKUPS & SNAPSHOTS
# ─────────────────────────────────────

# Create manual snapshot
aws rds create-db-snapshot \
  --db-instance-identifier teluskodb \
  --db-snapshot-identifier teluskodb-snapshot-before-migration

# Restore from snapshot
aws rds restore-db-instance-from-db-snapshot \
  --db-instance-identifier teluskodb-restored \
  --db-snapshot-identifier teluskodb-snapshot-before-migration

# ─────────────────────────────────────
# ENABLE MULTI-AZ
# ─────────────────────────────────────

# Enable Multi-AZ on existing instance
aws rds modify-db-instance \
  --db-instance-identifier teluskodb \
  --multi-az \
  --apply-immediately

# ─────────────────────────────────────
# MONITORING
# ─────────────────────────────────────

# Get CPU utilization from CloudWatch
aws cloudwatch get-metric-statistics \
  --namespace AWS/RDS \
  --metric-name CPUUtilization \
  --dimensions Name=DBInstanceIdentifier,Value=teluskodb \
  --start-time 2024-01-01T00:00:00Z \
  --end-time 2024-01-01T23:59:59Z \
  --period 3600 \
  --statistics Average \
  --output table
```

---

## RDS-13: RDS Monitoring & Performance

```mermaid
flowchart TD
    RDS_Mon["RDS Instance"] --> CW_Metrics

    subgraph CW_Metrics["CloudWatch Metrics (Key Ones to Watch)"]
        CPU_RDS["CPUUtilization\nAlert if > 80% for 5 mins"]
        FreeStorage["FreeStorageSpace\nAlert if < 2 GB"]
        DBConns["DatabaseConnections\nAlert if near max_connections limit"]
        ReadLatency["ReadLatency\nAlert if > 100ms"]
        WriteLatency["WriteLatency\nAlert if > 100ms"]
        ReplicaLag["ReplicaLag\nAlert if Read Replica lag > 60s"]
    end

    subgraph PI["Performance Insights"]
        TopSQL["Top SQL Queries\n(by CPU / wait time)"]
        TopWaits["Top Wait Events\n(io/table/lock waits)"]
        TopUsers["Top Users\n(who is using the most DB resources)"]
    end

    subgraph SlowQuery["Slow Query Log (via Parameter Group)"]
        SlowLog["slow_query_log = 1\nlong_query_time = 1\n(log queries > 1 second)"]
        CW_Logs["Export to CloudWatch Logs\nfor querying and alerting"]
    end

    CW_Metrics --> Alarms["CloudWatch Alarms\n→ SNS notification\n→ PagerDuty alert"]
    PI --> Optimize["Query Optimization\n(add index, rewrite query)"]
    SlowQuery --> CW_Logs --> Optimize

    classDef rds fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef monitor fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef action fill:#065F46,stroke:#10B981,color:#FFFFFF,stroke-width:2px;
    classDef alert fill:#7C2D12,stroke:#EA580C,color:#FFFFFF,stroke-width:2px;

    class RDS_Mon rds;
    class CPU_RDS,FreeStorage,DBConns,ReadLatency,WriteLatency,ReplicaLag,TopSQL,TopWaits,TopUsers,SlowLog,CW_Logs monitor;
    class Optimize action;
    class Alarms alert;
```

---

## RDS-14: RDS Security Best Practices

| Layer | Practice | Implementation |
| :--- | :--- | :--- |
| **Network** | Never expose RDS publicly in production | `PubliclyAccessible: false`, private subnets only |
| **Network** | Restrict Security Group to app server SG only | Allow TCP 3306 from app-sg, deny 0.0.0.0/0 |
| **Encryption** | Encrypt database at rest | Enable KMS encryption at creation (cannot enable later) |
| **Encryption** | Encrypt in transit | `useSSL=true` in JDBC URL |
| **Credentials** | Never hardcode DB password in code | Use AWS Secrets Manager with automatic rotation |
| **Access** | Use IAM DB Authentication | Passwordless login using IAM token (MySQL/PostgreSQL) |
| **Audit** | Enable CloudTrail for RDS API calls | Track who created/deleted/modified RDS instances |
| **Backup** | Enable automated backups | Retention 7+ days, backup window during off-peak hours |
| **Updates** | Enable auto minor version upgrade | `AutoMinorVersionUpgrade: true` |

### Secrets Manager for RDS Credentials

```java
// Retrieve RDS credentials from Secrets Manager (no hardcoded passwords)
@Configuration
public class DatabaseConfig {

    @Value("${aws.secretsmanager.secret-name}")
    private String secretName;

    @Bean
    public DataSource dataSource() {
        SecretsManagerClient client = SecretsManagerClient.builder()
            .region(Region.AP_SOUTH_1)
            .build();

        GetSecretValueRequest request = GetSecretValueRequest.builder()
            .secretId(secretName)
            .build();

        String secretJson = client.getSecretValue(request).secretString();
        // Parse JSON: {"username":"admin","password":"xxx","host":"...","port":3306}
        // Build HikariDataSource from parsed values
        return buildDataSource(secretJson);
    }
}
```

---

## RDS-15: RDS Interview Questions

**Q1: What is the difference between RDS and installing MySQL on an EC2 instance?**
> **A:** On EC2 MySQL, you manage everything — OS patches, MySQL upgrades, backups, HA setup, storage scaling, security patches — requiring dedicated DBA expertise. AWS RDS is **fully managed** — AWS handles all of that automatically. You only manage: schema design, query optimization, and application configuration. RDS provides Multi-AZ HA, automated backups, Performance Insights, and Read Replicas out of the box.

**Q2: What is Multi-AZ in RDS and what problem does it solve?**
> **A:** Multi-AZ deploys a **synchronous standby replica** in a different AZ. Every write to the primary is instantly replicated to the standby. If the primary fails (hardware fault, AZ outage, OS crash), AWS automatically fails over to the standby by updating the DNS record — same endpoint, no code changes. Failover takes 60-120 seconds. Solves: **Single Point of Failure** and provides **near-zero RPO (Recovery Point Objective)** since replication is synchronous.

**Q3: What is the difference between Multi-AZ and Read Replicas?**
> **A:** Multi-AZ = **High Availability** — standby is passive, synchronous replication, automatic failover. Read Replicas = **Read Scalability** — active, asynchronous replication, accepts SELECT queries, manual promotion. Production systems use both: Multi-AZ on the primary for HA, plus Read Replicas to offload report/analytics queries.

**Q4: Why is `PubliclyAccessible: YES` dangerous for production RDS?**
> **A:** Public access means the RDS instance gets a public IP and is reachable from the internet. Even with a security group, this increases the attack surface (brute-force, credential stuffing, port scanning). Production RDS should be in a **private subnet** with `PubliclyAccessible: false` — accessible only from within the VPC. DBAs connect via bastion host or AWS Systems Manager Session Manager.

**Q5: What is RDS Proxy and when do you use it?**
> **A:** RDS Proxy is a connection pooler that sits between your application and RDS. It maintains a small pool of real DB connections and multiplexes thousands of application connections through them. Use it when: Lambda functions create too many short-lived DB connections (connection exhaustion), or when you need faster failover (Proxy handles reconnection in ~30s vs 60-120s for Multi-AZ).

**Q6: How do you optimize a slow database query on RDS?**
> **A:** (1) Enable **RDS Performance Insights** to identify the top SQL statements by CPU/wait time. (2) Enable **Slow Query Log** (via parameter group: `slow_query_log=1, long_query_time=1`). (3) Run `EXPLAIN` on the slow query to identify missing indexes or full table scans. (4) Add appropriate indexes. (5) If read-heavy, route to **Read Replicas**. (6) Upgrade storage to `io2` if the bottleneck is IOPS.

**Q7: What is Amazon Aurora and how is it different from RDS MySQL?**
> **A:** Aurora is AWS's cloud-native MySQL/PostgreSQL-compatible engine. Key differences: 5x faster than MySQL, stores 6 copies across 3 AZs automatically (vs 2 for Multi-AZ), auto-scales storage from 10 GB to 128 TB without manual intervention, supports up to 15 read replicas (vs 5 for MySQL), failover in under 30 seconds (vs 60-120s for standard RDS). Best for high-throughput production workloads.

**Q8: How do you connect a Spring Boot application to RDS securely?**
> **A:** (1) RDS in a **private subnet**, `PubliclyAccessible: false`. (2) Security Group allows TCP 3306 from EC2/ECS security group only. (3) JDBC URL with `useSSL=true`. (4) Credentials from **AWS Secrets Manager** (not hardcoded). (5) EC2/ECS has an **IAM Role** allowing `secretsmanager:GetSecretValue`. (6) HikariCP connection pool configured in `application.yml` with appropriate pool size and timeouts.

---

## RDS-16: Common Beginner Mistakes

| Mistake | Consequence | Fix |
| :--- | :--- | :--- |
| `PubliclyAccessible: true` in production | DB exposed to internet attacks | Private subnet + Security Group restriction |
| Hardcoded DB password in `application.yml` | Password leaked in Git | Use AWS Secrets Manager or env vars |
| `ddl-auto: create` in production | Drops and recreates all tables on startup! | Use `validate` or `none` in production |
| No automated backups | Data loss on failure with no recovery | Enable backups with 7-35 day retention |
| Single AZ for production DB | SPOF — DB down = app down | Enable Multi-AZ for all production databases |
| No connection pool tuning | Connection exhaustion under load | Configure HikariCP pool size appropriately |
| Not monitoring Free Storage | DB storage fills up → crash | CloudWatch alarm: FreeStorageSpace < 2 GB |
| Opening port 3306 to `0.0.0.0/0` | Internet can attempt to connect to DB | Allow only from specific security groups |
| Using root/admin user in app | Full DB privileges if credentials leaked | Create app-specific DB user with minimal privileges |
| No Read Replica for analytics queries | Analytics queries slow down production DB | Route report/dashboard queries to Read Replicas |

---

## RDS-17: Real-World Production Example

**E-Commerce Platform — RDS Architecture:**
```
Production Setup:
├── RDS Aurora MySQL (Writer)
│   ├── Multi-AZ: Enabled (auto-failover)
│   ├── Instance: db.r5.2xlarge (8 vCPU, 64 GB RAM)
│   ├── Storage: Auto-scaling 100 GB → 128 TB
│   └── Backup: 35-day retention, daily 3-5 AM
│
├── Aurora Read Replicas (2x db.r5.large)
│   ├── Replica-1: Product catalog reads, Search
│   └── Replica-2: Order history reports, Analytics
│
├── RDS Proxy (for Lambda integration)
│   ├── Manages 5000 Lambda → 100 pooled connections
│   └── Handles failover reconnection automatically
│
├── Security
│   ├── Private subnet (no public access)
│   ├── Credentials in Secrets Manager (auto-rotated every 30 days)
│   ├── KMS encryption at rest
│   └── SSL/TLS in transit
│
└── Monitoring
    ├── Performance Insights: ON
    ├── Slow Query Log: CloudWatch Logs
    └── CloudWatch Alarms: CPU > 80%, Free Storage < 5 GB, Replica Lag > 30s
```

---
---

## TOPIC 10: AWS LAMBDA — SERVERLESS COMPUTING

### 1. Concept Explanation

#### Beginner
AWS Lambda is a serverless, event-driven compute service. You upload your application code as a function, and AWS runs and scales it automatically in response to triggers. You pay only for the compute time consumed per millisecond of execution.

Key Characteristics:
* No servers to configure, patch, or maintain.
* Automatically scales from 0 to thousands of concurrent executions.
* Maximum runtime duration is 15 minutes per execution.

#### Intermediate
##### Common Lambda Triggers
* **API Gateway / ALB:** Routes HTTP requests to run serverless REST APIs.
* **Amazon S3:** Triggers processing functions when files are uploaded (e.g., generating image thumbnails).
* **Amazon SQS / Kinesis:** Polls queues and processes incoming messages asynchronously.
* **Amazon EventBridge (CloudWatch Events):** Runs functions on a cron-like schedule.

##### Java Cold Start Problem
When a Lambda function is triggered after being idle, AWS must provision a container, initialize the JVM runtime, and load your code. For Java applications, this "cold start" can take 2–10 seconds.
* **Mitigations:**
  1. **Provisioned Concurrency:** Pre-warms a set number of containers to eliminate cold start latency.
  2. **AWS Lambda SnapStart:** Takes a snapshot of the initialized JVM memory cache and resumes from it on subsequent triggers, reducing cold starts to sub-second levels.
  3. **GraalVM Native Compilation:** Compile Java code into a native binary to reduce memory footprint and startup times.

#### Advanced
##### Lambda Concurrency Models
* **Account Limit:** AWS enforces a default soft limit of 1,000 concurrent executions per region.
* **Reserved Concurrency:** Restricts the maximum concurrency of a specific function, preventing it from consuming the entire account limit and throttling other functions.

##### Serverless Microservice Design
A standard serverless API architecture using API Gateway, Lambda, and database layers:

```mermaid
flowchart LR
    Client((Client)) --> APIGW["API Gateway"]
    APIGW -->|"GET /claims"| Lambda["AWS Lambda (ClaimService)"]
    Lambda -->|"Pool Connections"| Proxy["RDS Proxy"]
    Proxy --> DB[("RDS Database")]

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef client fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;

    class Client client;
    class APIGW,Lambda,Proxy layer;
    class DB db;
```

### 2. Spring Boot Lambda Handler Code Example

To run a Spring Boot application within a Lambda container, use the **AWS Serverless Java Container** library to bridge HTTP requests:

```java
package com.company.handler;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.company.Application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamLambdaHandler implements RequestStreamHandler {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

    static {
        try {
            // Load Spring Boot application context during container initialization
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(Application.class);
        } catch (ContainerInitializationException e) {
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
            throws IOException {
        // Route request and response streams through the proxy handler
        handler.proxyStream(inputStream, outputStream, context);
    }
}
```

### 3. Interview Questions & Answers

#### Q: When would you choose Lambda over EC2?
**A:** 
| Feature | AWS Lambda | AWS EC2 |
| :--- | :--- | :--- |
| **Scaling** | Instantly scales based on event frequency | Scales via ASG policies (minutes) |
| **State** | Stateless only | Stateful or Stateless |
| **Cost Model** | Pay per millisecond of execution | Pay per hour for running instances |
| **Runtime Limit** | Maximum 15 minutes per request | No limit |
| **Operational Effort** | None (serverless) | High (patching, OS maintenance) |

Use **Lambda** for short-lived tasks, event processing, S3 triggers, and APIs with variable traffic. Use **EC2** for long-running processes, web sockets, and applications requiring local state.

### 4. Key Takeaways
* AWS Lambda is stateless, event-driven, and scales automatically.
* Use Provisioned Concurrency or SnapStart to mitigate JVM cold start latency in production Java applications.
* Set Reserved Concurrency to prevent a single function from exhausting the account's regional concurrency limit.

---

## TOPIC 11: ECS & EKS — CONTAINER ORCHESTRATION

### 1. Concept Explanation

#### Beginner
When managing dozens or hundreds of containerized applications, container orchestrators handle container scheduling, deployments, scaling, and load balancing.

AWS provides two primary orchestration options:
* **Elastic Container Service (ECS):** An AWS-native container orchestrator. It is simple to configure and integrates deeply with other AWS services.
* **Elastic Kubernetes Service (EKS):** A managed Kubernetes service. It conforms to open-source Kubernetes standards, offering high portability across multiple cloud environments.

#### Intermediate
##### Launch Modes
* **EC2 Launch Type:** You provision and manage the underlying EC2 instances that host the containers.
* **Fargate Launch Type:** A serverless compute engine for containers. AWS manages the underlying servers; you only define CPU and memory limits.

##### ECS Core Concepts
* **Task Definition:** A JSON blueprint configuring the container image, environment variables, storage mounts, and resource allocations.
* **Task:** A running container instance instantiated from a Task Definition.
* **Service:** Manages a specified number of running tasks, handles scaling, and registers tasks with a load balancer.

##### Task Definition Secrets Injection Example
```json
{
  "family": "policy-service-task",
  "networkMode": "awsvpc",
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "512",
  "memory": "1024",
  "containerDefinitions": [
    {
      "name": "policy-service",
      "image": "123456789012.dkr.ecr.ap-south-1.amazonaws.com/policy-service:v2",
      "portMappings": [
        {
          "containerPort": 8080
        }
      ],
      "secrets": [
        {
          "name": "SPRING_DATASOURCE_PASSWORD",
          "valueFrom": "arn:aws:secretsmanager:ap-south-1:123456789012:secret:prod/db-password-xyz:password::"
        }
      ],
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "/ecs/policy-service",
          "awslogs-region": "ap-south-1",
          "awslogs-stream-prefix": "ecs"
        }
      }
    }
  ]
}
```

#### Advanced
* **EKS IAM Roles for Service Accounts (IRSA):** Maps IAM Roles directly to Kubernetes Service Accounts. This allows pods to call AWS resources (like S3 or DynamoDB) using temporary credentials, adhering to the principle of least privilege at the pod level rather than the node level.

### 2. Interview Questions & Answers

#### Q: How do ECS and EKS compare, and how do you choose between them?
**A:** 
| Feature | Elastic Container Service (ECS) | Elastic Kubernetes Service (EKS) |
| :--- | :--- | :--- |
| **Complexity** | Simple, native AWS integrations | High, requires Kubernetes expertise |
| **Portability** | AWS-specific | Open-source Kubernetes (run on any cloud) |
| **Cost** | No charge for the control plane | Control plane costs $0.10 per hour |
| **Resource Model** | Tasks and Services | Pods, Deployments, and Services |

Choose **ECS** for AWS-centric applications that benefit from simple configuration. Choose **EKS** if your organization requires Kubernetes compatibility, multi-cloud portability, or advanced custom configurations.

### 3. Key Takeaways
* Fargate provides serverless compute for both ECS and EKS, eliminating the need to manage EC2 host nodes.
* Reference ARNs inside Task Definitions to inject secrets from AWS Secrets Manager securely at runtime.
* EKS IRSA provides fine-grained IAM security controls at the pod level.

---

## TOPIC 12: CLOUDWATCH — MONITORING & LOGGING

> **Real-World Analogy:** CloudWatch is like the **hospital monitoring system** for your AWS infrastructure. Just like ICU monitors track a patient's heart rate, blood pressure, and oxygen levels — CloudWatch tracks CPU, memory, disk, network, and application metrics. When a metric crosses a dangerous threshold (like CPU > 80%), the alarm system alerts the doctor (you) via SNS notification.

---

### 12.1 — What Is CloudWatch and Why Does It Exist?

#### Definition
**Amazon CloudWatch** is a monitoring and observability service that collects metrics, logs, events, and traces from AWS resources, applications, and on-premises servers. It enables you to observe what is happening in your AWS Cloud in real-time.

#### Why CloudWatch Exists
Without CloudWatch, you would have no visibility into:
- **Is my EC2 instance running at 100% CPU?** → Application will slow down or crash
- **Did my server throw 500 errors?** → Users facing issues
- **Is my RDS storage running out?** → Database will fail writes
- **Did my Auto Scaling trigger?** → Unexpected billing

CloudWatch solves these problems by providing centralized visibility, alerting, and automated responses.

---

### 12.2 — CloudWatch Core Components

```mermaid
flowchart TD
    CW["☁️ Amazon CloudWatch\n(Monitoring & Observability)"]
    
    CW --> Metrics["📊 Metrics\n(Numeric data points)\nCPU, Memory, Network, etc."]
    CW --> Logs["📝 CloudWatch Logs\n(Text logs from\napps, OS, AWS services)"]
    CW --> Alarms["🔔 CloudWatch Alarms\n(Trigger on metric threshold\n→ notify or auto-act)"]
    CW --> Events["⚡ CloudWatch Events\n/ EventBridge\n(React to AWS changes)"]
    CW --> Dashboards["📈 CloudWatch Dashboards\n(Visual real-time\nmonitoring screens)"]
    CW --> Insights["🔍 Logs Insights\n(Query & analyze\nlog data at scale)"]

    Alarms --> Actions["⚙️ Alarm Actions"]
    Actions --> SNS["📧 SNS Notification\n(Email/SMS/Slack)"]
    Actions --> ASG["⚖️ Auto Scaling\n(Scale In / Scale Out)"]
    Actions --> EC2A["🖥️ EC2 Action\n(Stop/Reboot/Terminate)"]

    classDef cw fill:#1E40AF,stroke:#93C5FD,color:#FFFFFF,stroke-width:2px;
    classDef comp fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef action fill:#7C3AED,stroke:#DDD6FE,color:#FFFFFF,stroke-width:2px;
    class CW cw;
    class Metrics,Logs,Alarms,Events,Dashboards,Insights comp;
    class Actions,SNS,ASG,EC2A action;
```

---

### 12.3 — CloudWatch Metrics

#### What Are Metrics?
Metrics are time-series data points representing the health and behavior of your AWS resources. CloudWatch automatically collects default metrics from most AWS services.

#### Default Metrics by Service

| Service | Key Metrics | Recommended Alarm Threshold |
|:---|:---|:---|
| **EC2** | `CPUUtilization`, `NetworkIn`, `NetworkOut`, `StatusCheckFailed` | CPU > 80%, StatusCheck = 1 |
| **RDS** | `CPUUtilization`, `DatabaseConnections`, `FreeStorageSpace`, `ReadLatency` | CPU > 75%, FreeStorage < 10% |
| **ALB** | `RequestCount`, `TargetResponseTime`, `HTTPCode_Target_5XX_Count` | ResponseTime > 2s, 5XX > 10 |
| **Lambda** | `Duration`, `Errors`, `Throttles`, `ConcurrentExecutions` | Errors > 0, Throttles > 10 |
| **SQS** | `ApproximateNumberOfMessagesVisible`, `NumberOfMessagesSent` | Queue depth > 1000 |
| **S3** | `BucketSizeBytes`, `NumberOfObjects`, `AllRequests` | Size approaching limit |

> [!NOTE]
> **Custom Metrics:** You can publish your own application metrics to CloudWatch (e.g., `OrdersPerMinute`, `ActiveUsers`) using the AWS SDK or AWS CLI. Custom metrics are not free — you pay per metric per month.

#### Metric Namespace
AWS organizes metrics into **namespaces** to prevent name collisions:
- `AWS/EC2` — EC2 metrics
- `AWS/RDS` — RDS metrics
- `AWS/Lambda` — Lambda metrics
- `YourApp/CustomMetrics` — Your custom application metrics

---

### 12.4 — CloudWatch Alarms

#### What Are Alarms?
A CloudWatch Alarm watches a **single metric** over a specified time period and performs one or more actions based on the value of the metric relative to a threshold.

#### Alarm States

| State | Meaning |
|:---|:---|
| **OK** | Metric is within the defined threshold — all is well |
| **ALARM** | Metric has crossed the threshold — action triggered |
| **INSUFFICIENT_DATA** | Not enough data points yet to determine state |

#### Alarm Components
```
Alarm Configuration Example:
  Metric:          EC2 CPUUtilization
  Threshold:       >= 80%
  Evaluation:      Average over last 5 minutes
  DataPoints:      3 out of 3 consecutive periods
  Action on ALARM: Notify SNS Topic → Send Email
  Action on OK:    Notify SNS Topic → Send "Recovered" Email
```

#### CloudWatch + SNS + EC2 Alarm Flow (from Board Work)

```mermaid
flowchart LR
    subgraph AWS ["☁️ AWS Cloud"]
        subgraph SERVER ["🖥️ EC2 Instance"]
            APP["📦 Application\n(High CPU load)"]
        end
        
        CW_ALARM["🔔 CloudWatch Alarm\n(Threshold: CPU >= 2%)\n(for testing — low threshold)"]
        
        SNS_TOPIC["📢 SNS Topic\n(Standard — Public)"]
        
        EMAIL["📧 Email Subscriber\nyour@email.com"]
        
        STATUS_LOG["📋 Check Logs\nTracking status:\nerror, warning..."]
    end

    APP -->|"Metrics pushed\n(CPUUtilization)"| CW_ALARM
    CW_ALARM -->|"State: IN ALARM\nTriggers notification"| SNS_TOPIC
    SNS_TOPIC -->|"Subscribe → Push"| EMAIL
    SERVER -->|"CloudWatch\nobserves"| STATUS_LOG

    classDef ec2 fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef alarm fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    classDef sns fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef email fill:#1E40AF,stroke:#93C5FD,color:#FFFFFF,stroke-width:2px;
    class SERVER,APP ec2;
    class CW_ALARM alarm;
    class SNS_TOPIC sns;
    class EMAIL,STATUS_LOG email;
```

---

### 12.5 — CloudWatch Logs

#### What Are CloudWatch Logs?
CloudWatch Logs is a **centralized log aggregation service** where you can collect, store, monitor, and query log data from:
- EC2 instances (via CloudWatch Agent)
- Lambda functions (automatically)
- AWS services (API Gateway, ECS, RDS, etc.)
- On-premises servers

#### Key Log Concepts

| Concept | Description | Example |
|:---|:---|:---|
| **Log Group** | Container for log streams (organized by application/service) | `/aws/app/order-service` |
| **Log Stream** | Sequence of log events from a single source | `i-0abc123-2024-07-16` |
| **Log Event** | A single log entry with timestamp and message | `2024-07-16 10:00:00 ERROR NullPointerException` |
| **Retention Policy** | How long logs are kept (1 day to Never expire) | 90 days for production |
| **Metric Filter** | Converts log patterns to CloudWatch metrics | Count ERROR occurrences |

#### CloudWatch Logs Insights — Query Examples

```sql
-- Find top 10 endpoints causing HTTP 500 errors
fields @timestamp, @message, status, requestPath
| filter status = 500
| stats count(*) as errorCount by requestPath
| sort errorCount desc
| limit 10

-- Find slowest API responses (> 2 seconds)
fields @timestamp, duration, requestId
| filter duration > 2000
| sort duration desc
| limit 20

-- Count errors per hour
fields @timestamp
| filter @message like /ERROR/
| stats count() as errorCount by bin(1h)
| sort @timestamp asc
```

---

### 12.6 — CloudWatch Dashboards

#### Creating Production Dashboards
A production CloudWatch Dashboard provides at-a-glance visibility into your application health:

```
Dashboard: "Production App Health"
┌─────────────────┬──────────────────┬──────────────────┐
│  EC2 CPU %      │  ALB Request/sec │  5XX Error Rate  │
│  [Line Chart]   │  [Number Widget] │  [Alarm Widget]  │
├─────────────────┼──────────────────┼──────────────────┤
│  RDS Connections│  Lambda Errors   │  SQS Queue Depth │
│  [Line Chart]   │  [Bar Chart]     │  [Line Chart]    │
└─────────────────┴──────────────────┴──────────────────┘
```

---

### 12.7 — CloudWatch + SNS Practical (from Class)

This is the exact practical demonstrated in class with the stress test:

#### Step 1: Create SNS Topic with Email Notification
```
AWS Console → SNS → Topics → Create Topic
  Type: Standard
  Name: telusko-cloudwatch-alerts
→ Click "Create Topic"

Create Subscription:
  Topic: telusko-cloudwatch-alerts
  Protocol: Email
  Endpoint: your-email@gmail.com
→ Click "Create Subscription"

⚠️ Check your email → Click "Confirm subscription" link
```

#### Step 2: Create CloudWatch Alarm on EC2
```
EC2 → Select Instance → Actions → Monitor and Troubleshoot
  → Manage CloudWatch Alarms → Create Alarm

Alarm Configuration:
  Alarm Notification: telusko-cloudwatch-alerts (SNS Topic)
  Alarm Threshold:    Average CPU >= 2% (low threshold for testing)
  Evaluation Period:  5 minutes
→ Create Alarm
```

#### Step 3: Stress Test the EC2 Instance
```bash
# SSH into your EC2 instance
ssh -i your-key.pem ec2-user@<ec2-public-ip>

# Install the stress testing tool
sudo yum install stress -y

# Generate heavy CPU load
sudo stress --cpu 8 -v --timeout 60s

# Run stress 3-4 times to trigger the alarm
sudo stress --cpu 8 -v --timeout 60s
sudo stress --cpu 8 -v --timeout 60s
sudo stress --cpu 8 -v --timeout 60s
```

#### Step 4: Observe CloudWatch Behavior
```
Expected Results:
✅ CloudWatch Alarm state changes: OK → ALARM
✅ Email received: "CloudWatch Alarm triggered for EC2 CPU..."
✅ Alarm History shows trigger events
✅ After stress stops → State changes back to OK
✅ Recovery email received
```

#### Step 5: Monitor Alarm History
```
CloudWatch → Alarms → Select your alarm → History tab
Shows:
  - When alarm transitioned: OK → ALARM (timestamp)
  - When alarm recovered: ALARM → OK (timestamp)
  - How many times triggered
```

---

### 12.8 — Spring Boot CloudWatch Integration

#### Logback Appender for CloudWatch

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/defaults.xml" />
    <include resource="org/springframework/boot/logging/logback/console-appender.xml" />

    <!-- CloudWatch Log Appender Configuration -->
    <appender name="CLOUDWATCH" class="ca.pjer.logback.AwsLogsAppender">
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>WARN</level>
        </filter>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </layout>
        <logGroupName>/aws/app/order-service-prod</logGroupName>
        <logStreamName>order-api-${HOSTNAME}</logStreamName>
        <logRegion>ap-south-1</logRegion>
        <maxBatchLogEvents>50</maxBatchLogEvents>
        <maxFlushTimeMillis>30000</maxFlushTimeMillis>
    </appender>

    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="CLOUDWATCH" />
    </root>
</configuration>
```

#### Custom Metric Publication (Java SDK)

```java
import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.*;

@Component
public class CloudWatchMetricPublisher {

    private final CloudWatchClient cloudWatchClient;

    public CloudWatchMetricPublisher() {
        this.cloudWatchClient = CloudWatchClient.builder()
            .region(Region.AP_SOUTH_1)
            .build();
    }

    /**
     * Publish custom business metric to CloudWatch
     * Used to trigger alarms based on application-level thresholds
     */
    public void publishOrdersProcessedMetric(double ordersPerMinute) {
        cloudWatchClient.putMetricData(PutMetricDataRequest.builder()
            .namespace("OrderServiceApp")
            .metricData(MetricDatum.builder()
                .metricName("OrdersProcessedPerMinute")
                .value(ordersPerMinute)
                .unit(StandardUnit.COUNT_SECOND)
                .timestamp(Instant.now())
                .build())
            .build());
    }
}
```

---

### 12.9 — CloudWatch Interview Questions & Answers

#### Q1: What is the difference between a CloudWatch Metric and a CloudWatch Log?
**A:**
- **Metric:** Numeric time-series data point representing resource behavior (e.g., CPU = 85.2%). Used to create alarms and graphs.
- **Log:** Text-based event record from an application or service (e.g., `ERROR: Database connection timeout`). Used for debugging and analysis.

---

#### Q2: How does CloudWatch integrate with Auto Scaling?
**A:** CloudWatch Alarms can trigger **Auto Scaling Group policies**:
1. Create a CloudWatch Alarm: `CPUUtilization >= 70% for 2 consecutive periods`
2. Attach alarm to ASG **Scale-Out Policy**: Launch 2 more EC2 instances
3. Create a second alarm: `CPUUtilization <= 30% for 5 consecutive periods`
4. Attach to ASG **Scale-In Policy**: Terminate 1 instance

This creates fully automatic elastic scaling based on real-time load.

---

#### Q3: What is the difference between CloudWatch Events and CloudWatch Alarms?
**A:**
- **CloudWatch Alarms:** Watch a specific **metric** over time and react when it crosses a threshold. Example: CPU > 80% → Send email.
- **CloudWatch Events (EventBridge):** React to **state changes** in AWS services. Example: EC2 instance state changes from `running` → `stopped` → trigger Lambda function.

---

#### Q4: How do you configure scaling based on custom application metrics?
**A:**
1. Publish custom metric to CloudWatch using AWS SDK (e.g., `ActiveOrdersInQueue`)
2. Create CloudWatch Alarm on this metric (e.g., `ActiveOrdersInQueue > 500`)
3. Create ASG Scaling Policy triggered by this alarm
4. When queue depth exceeds 500, Auto Scaling launches new instances to process orders faster

---

#### Q5: Explain CloudWatch Log Groups vs Log Streams
**A:**
- **Log Group:** Logical container for all log streams of a specific application/component (e.g., `/aws/app/order-service`)
- **Log Stream:** Individual source within the group (e.g., a specific EC2 instance `i-0abc123-2024-07-16`)
- **Relationship:** One Log Group → Many Log Streams → Many Log Events per stream

---

### 12.10 — Production CloudWatch Best Practices

#### ✅ Best Practices
1. **Use Composite Alarms** — combine multiple alarms for complex conditions (avoid alert fatigue)
2. **Set appropriate retention policies** — balance cost vs. debugging needs (30-90 days typical)
3. **Use Metric Filters** — convert log patterns to metrics for alerting on application-level errors
4. **Create dashboards per team** — separate operational dashboards for Dev, Ops, and Business
5. **Use CloudWatch Agent** for EC2 memory and disk metrics (not collected by default)
6. **Configure alarm actions for both ALARM and OK states** — get recovery notifications
7. **Use Log Insights** for ad-hoc investigations — avoid expensive custom queries

#### ❌ Common Mistakes
1. **Not installing CloudWatch Agent** — EC2 memory and disk utilization are NOT collected by default
2. **Setting thresholds too tight** — causes alarm fatigue (too many false positives)
3. **Missing Log Retention policy** — logs accumulate indefinitely causing storage costs
4. **Ignoring INSUFFICIENT_DATA state** — indicates monitoring gaps

---

### 12.11 — Real-World CloudWatch Architecture

```mermaid
flowchart TB
    subgraph APP ["Production Application"]
        EC2["🖥️ EC2 App Servers"]
        RDS[("🗄️ RDS Database")]
        ALB["⚖️ Load Balancer"]
    end

    subgraph CW ["☁️ CloudWatch"]
        METRICS["📊 Metrics\n(CPU, Memory, Disk, Network)"]
        LOGS["📝 Log Groups\n(/aws/app/order-service)"]
        ALARMS["🔔 Alarms\nCPU > 80% → ALARM"]
        DASH["📈 Dashboard\nReal-time visibility"]
        INSIGHTS["🔍 Logs Insights\nSQL-like queries"]
    end

    subgraph ACTIONS ["⚙️ Actions"]
        SNS_T["📢 SNS Topic\n→ Email / Slack / PagerDuty"]
        ASG_A["⚖️ Auto Scaling\nScale Out / Scale In"]
        LAMBDA["⚡ Lambda\nAuto-remediation"]
    end

    EC2 --> METRICS
    RDS --> METRICS
    ALB --> METRICS
    EC2 --> LOGS

    METRICS --> ALARMS
    METRICS --> DASH
    LOGS --> INSIGHTS
    
    ALARMS --> SNS_T
    ALARMS --> ASG_A
    ALARMS --> LAMBDA

    classDef app fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef cw fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef action fill:#7C3AED,stroke:#DDD6FE,color:#FFFFFF,stroke-width:2px;
    class EC2,RDS,ALB app;
    class METRICS,LOGS,ALARMS,DASH,INSIGHTS cw;
    class SNS_T,ASG_A,LAMBDA action;
```

---

### 12.12 — Key Takeaways

- CloudWatch provides end-to-end observability for your AWS infrastructure
- **Metrics** are numbers; **Logs** are text events; **Alarms** react to metric thresholds
- EC2 **memory and disk metrics require CloudWatch Agent** installation — not collected by default
- CloudWatch Alarms integrate with SNS (notifications), Auto Scaling (capacity), and EC2 Actions
- Use **Logs Insights** for powerful SQL-like queries across large log datasets at scale
- Set retention policies on Log Groups to control storage costs

---

## TOPIC 13: SNS & SQS — MESSAGING SERVICES

> **Real-World Analogy:** 
> - **SNS** is like a **newspaper publisher**. You subscribe, and whenever a story is published, ALL subscribers get a copy simultaneously (push-based fan-out).
> - **SQS** is like a **post office mailbox**. Messages sit in the mailbox until YOU go and pick them up (pull-based queue). Great for decoupling services.

---

### 13.1 — Simple Notification Service (SNS)

#### What Is SNS?
**Amazon SNS** is a fully managed **push-based Pub/Sub messaging service**. When you publish a message to an SNS **Topic**, it is **immediately pushed to all subscribed endpoints** — email, SMS, Lambda, SQS, HTTP, etc.

#### SNS Topic Types

| Topic Type | Throughput | Message Ordering | Use Case |
|:---|:---|:---|:---|
| **Standard** | Nearly unlimited | No guaranteed order | High-volume notifications, CloudWatch alerts |
| **FIFO** | 300 msg/sec | Strict ordering guaranteed | Ordered event sequences |

#### SNS Protocol / Subscription Endpoints

| Endpoint | Use Case |
|:---|:---|
| **Email** | Alerts to team inbox (CloudWatch → SNS → Email) |
| **SMS** | Mobile text alerts |
| **SQS** | Fan-out: SNS pushes to multiple SQS queues |
| **Lambda** | Trigger serverless processing |
| **HTTPS** | Push to any webhook/external system |
| **Mobile Push** | FCM/APNS mobile app notifications |

#### SNS Architecture: CloudWatch + EC2 + Email (From Board Work)

```mermaid
flowchart LR
    subgraph AWS ["☁️ AWS Cloud"]
        EC2["🖥️ EC2 Instance\n(Running App)"]
        CW["📊 CloudWatch\n(Monitoring CPU metrics)"]
        ALARM["🔔 CloudWatch Alarm\n(CPU >= threshold)"]
        
        subgraph SNS_BOX ["📢 SNS Topic (Standard)"]
            TOPIC["Topic: telusko-alerts"]
        end
        
        EMAIL["📧 Email Subscriber\nyou@gmail.com\n(CONFIRMED subscription)"]
        SMS["📱 SMS Subscriber\n+91 9xxxxx"]
        LAMBDA_FN["⚡ Lambda Subscriber\nAuto-remediation"]
    end

    EC2 -->|"Pushes CPU metrics"| CW
    CW --> ALARM
    ALARM -->|"State: IN ALARM\nPublish message"| TOPIC
    TOPIC -->|"Push notification"| EMAIL
    TOPIC -->|"Push SMS"| SMS
    TOPIC -->|"Invoke function"| LAMBDA_FN

    classDef ec2 fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef cw fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef sns fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    classDef sub fill:#1E40AF,stroke:#93C5FD,color:#FFFFFF,stroke-width:2px;
    class EC2 ec2;
    class CW,ALARM cw;
    class TOPIC sns;
    class EMAIL,SMS,LAMBDA_FN sub;
```

#### SNS Practical — Step by Step (From Class)

```
Step 1: Create SNS Topic
  AWS Console → SNS → Topics → Create Topic
    Type: Standard (Public)
    Name: telusko-cloudwatch-alerts
  → Click "Create Topic"
  ✅ Topic ARN created: arn:aws:sns:ap-south-1:123456789:telusko-cloudwatch-alerts

Step 2: Create Subscription
  Select Topic → Create subscription
    Protocol: Email
    Endpoint: your-email@gmail.com
  → Click "Create Subscription"

Step 3: Confirm Subscription (IMPORTANT!)
  ⚠️ Check your email inbox
  Subject: "AWS Notification - Subscription Confirmation"
  → Click "Confirm subscription" link
  ✅ Status changes from "Pending confirmation" → "Confirmed"

Step 4: Test - Publish a message manually
  SNS → Topics → telusko-cloudwatch-alerts → Publish message
    Subject: "Test Alert"
    Message: "This is a test notification from SNS"
  → Click "Publish"
  ✅ You receive email within seconds

Step 5: Wire CloudWatch Alarm to SNS
  (See Topic 12 - CloudWatch Practical)
```

---

### 13.2 — Simple Queue Service (SQS)

#### What Is SQS?
**Amazon SQS** is a fully managed **pull-based message queue service** for decoupling microservices. Producers send messages to the queue; consumers poll the queue and process messages at their own pace.

#### SQS Queue Types

| Feature | Standard Queue | FIFO Queue |
|:---|:---|:---|
| **Ordering** | Best-effort (may be out of order) | Strict FIFO (guaranteed order) |
| **Delivery** | At-least-once (may be duplicated) | Exactly-once |
| **Throughput** | Nearly unlimited | 300 msg/sec (3,000 with batching) |
| **Use Case** | High-volume, order-independent work | Transactions, financial operations |
| **Name Suffix** | None | Must end in `.fifo` |

#### Key SQS Concepts

| Concept | Description | Typical Value |
|:---|:---|:---|
| **Visibility Timeout** | Time message is hidden from others after one consumer picks it up | 30s–12 hours |
| **Message Retention Period** | How long SQS keeps unprocessed messages | Default: 4 days (max: 14 days) |
| **Dead Letter Queue (DLQ)** | Secondary queue for messages that fail after N retries | `maxReceiveCount: 3` |
| **Long Polling** | Consumer waits up to 20s for messages (reduces empty polls) | `WaitTimeSeconds: 20` |
| **Batch Size** | Number of messages processed per poll | 1–10 (or up to 10,000 for Lambda) |

#### SQS Visibility Timeout Explained

```
Timeline: Message Processing with Visibility Timeout

T=0s    Consumer polls queue → picks up "Order-123" message
        ↓ Message hidden from other consumers for 30 seconds
T=15s   Consumer processes order successfully → Deletes message from queue
        ✅ Message deleted = SUCCESS

--- Alternative failure scenario ---
T=0s    Consumer polls queue → picks up "Order-123" message
        ↓ Message hidden for 30 seconds
T=30s   Consumer crashes (timeout expires!)
        ↓ Message becomes VISIBLE AGAIN to other consumers
T=31s   Different consumer picks up "Order-123" and processes it
        ✅ No data loss — message retried automatically
```

---

### 13.3 — SNS + SQS Fan-Out Pattern

```mermaid
flowchart TD
    subgraph PUBLISHERS ["📤 Producers"]
        ORDER_SVC["🛒 Order Service\n(Spring Boot)"]
    end
    
    SNS_FANOUT["📢 SNS Topic\nOrderEvents"]
    
    subgraph SUBSCRIBERS ["📥 Subscribers (Fan-Out)"]
        SQS1["📮 SQS: Inventory Queue"]
        SQS2["📮 SQS: Email Notification Queue"]
        SQS3["📮 SQS: Analytics Queue"]
        EMAIL_FAN["📧 Email: ops-team@company.com"]
        LAMBDA_F["⚡ Lambda: Fraud Detection"]
    end

    subgraph PROCESSORS ["⚙️ Consumers"]
        INV["Inventory Service"]
        NOTIF["Email Notification Service"]
        ANALYTICS["Analytics Service"]
        FRAUD["Fraud Check Function"]
    end

    ORDER_SVC -->|"Publish 'Order Placed'"| SNS_FANOUT
    SNS_FANOUT -->|"Push instantly"| SQS1
    SNS_FANOUT -->|"Push instantly"| SQS2
    SNS_FANOUT -->|"Push instantly"| SQS3
    SNS_FANOUT -->|"Push alert"| EMAIL_FAN
    SNS_FANOUT -->|"Invoke directly"| LAMBDA_F

    SQS1 -->|"Pull & process"| INV
    SQS2 -->|"Pull & process"| NOTIF
    SQS3 -->|"Pull & process"| ANALYTICS
    LAMBDA_F --> FRAUD

    classDef pub fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    classDef sns fill:#4338CA,stroke:#A5B4FC,color:#FFFFFF,stroke-width:2px;
    classDef sqs fill:#065F46,stroke:#6EE7B7,color:#FFFFFF,stroke-width:2px;
    classDef proc fill:#1E40AF,stroke:#93C5FD,color:#FFFFFF,stroke-width:2px;
    class ORDER_SVC pub;
    class SNS_FANOUT sns;
    class SQS1,SQS2,SQS3,EMAIL_FAN,LAMBDA_F sqs;
    class INV,NOTIF,ANALYTICS,FRAUD proc;
```

**Why Fan-Out?** Without SNS fan-out, the Order Service would need to call Inventory, Email, and Analytics services sequentially. If any fails, the whole chain breaks. With fan-out, each service is independently decoupled.

---

### 13.4 — Spring Boot Integration Examples

#### SQS Message Listener (Spring Cloud AWS)

```java
@Component
public class OrderEventListener {
    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    /**
     * Automatically polls SQS queue and deserializes JSON to OrderEventDto.
     * If exception is thrown, SQS does NOT delete the message → retry happens.
     * After maxReceiveCount retries, message goes to Dead Letter Queue.
     */
    @SqsListener("prod-order-processing-queue")
    public void receiveMessage(OrderEventDto orderEvent) {
        log.info("Received order event: orderId={}", orderEvent.getOrderId());
        try {
            processOrder(orderEvent);
        } catch (Exception e) {
            log.error("Failed to process order: {}", orderEvent.getOrderId(), e);
            throw e; // Re-throw: prevents SQS from deleting → triggers retry
        }
    }

    private void processOrder(OrderEventDto order) { /* Business logic */ }
}
```

#### SNS Publisher (Java SDK v2)

```java
@Component
public class SnsNotificationPublisher {
    
    private final SnsClient snsClient;
    private final String topicArn = "arn:aws:sns:ap-south-1:123456789:OrderEvents";

    public void publishOrderEvent(String orderId, String eventType) {
        String message = String.format(
            "{\"orderId\": \"%s\", \"event\": \"%s\", \"timestamp\": \"%s\"}",
            orderId, eventType, Instant.now()
        );

        snsClient.publish(PublishRequest.builder()
            .topicArn(topicArn)
            .message(message)
            .subject("Order Event: " + eventType)
            .build());
    }
}
```

---

### 13.5 — SNS vs SQS Comparison

| Feature | SNS | SQS |
|:---|:---|:---|
| **Type** | Pub/Sub messaging | Message queue |
| **Delivery model** | Push (immediate fan-out) | Pull (consumer polls) |
| **Consumers** | Multiple simultaneously | Typically one consumer group |
| **Message persistence** | No (not stored after push) | Yes (up to 14 days) |
| **Use case** | Notifications, fan-out, alerts | Decoupling, async processing |
| **With each other** | SNS can push to SQS (fan-out) | SQS can trigger Lambda |

---

### 13.6 — Interview Questions & Answers

#### Q1: What is the difference between SNS and SQS?
**A:**
- **SNS** is a **push-based pub/sub** service. One message is immediately delivered to ALL subscribers (email, Lambda, SQS, etc.)
- **SQS** is a **pull-based queue**. Messages sit in the queue until consumers poll and process them
- They complement each other: SNS fan-out → multiple SQS queues for independent processing

#### Q2: What is the SQS Visibility Timeout and why is it important?
**A:** When a consumer fetches a message, SQS hides it from other consumers for the visibility timeout duration (default 30s). If the consumer processes successfully and deletes the message, it's gone. If the consumer crashes before deletion, the timeout expires, and the message becomes visible again for retry. This ensures **at-least-once delivery** without data loss.

#### Q3: What is the SQS Dead Letter Queue (DLQ)?
**A:** A DLQ is a separate SQS queue that receives messages that fail processing after `maxReceiveCount` retries. Instead of losing failed messages or blocking the main queue, they're moved to the DLQ for investigation. Every production SQS queue should have a DLQ configured.

#### Q4: How does the SNS + SQS Fan-Out pattern work?
**A:**
1. A producer (Order Service) publishes ONE message to an SNS Topic
2. SNS immediately fans out to multiple subscribed SQS queues (Inventory, Email, Analytics)
3. Each SQS queue is processed independently by its own microservice
4. Services are fully decoupled — if Email Service is slow, Inventory Service is unaffected

#### Q5: What is the difference between SNS Standard and FIFO topics?
**A:**
- **Standard**: Near-unlimited throughput, best-effort ordering, at-least-once delivery. Use for high-volume alerts.
- **FIFO**: 300 msg/sec limit, strict ordering, exactly-once delivery. Use for ordered financial events.

---

### 13.7 — Key Takeaways

- **SNS** = Push-based, fan-out, pub/sub. Immediate delivery to all subscribers.
- **SQS** = Pull-based, queue, decoupling. Messages persist until consumed.
- **SNS → SQS** Fan-out pattern is the fundamental microservices decoupling architecture.
- Always configure a **Dead Letter Queue (DLQ)** for production SQS queues.
- **Visibility Timeout** is SQS's mechanism for retrying failed messages without data loss.
- **SNS Standard** for CloudWatch alarms; **SNS FIFO** for ordered transactional events.

---


## TOPIC 14: ROUTE 53 — DNS & DOMAIN MANAGEMENT

### 1. Concept Explanation

#### Beginner
Amazon Route 53 is a scalable, highly available Domain Name System (DNS) web service. It translates human-readable domain names (e.g. `api.company.com`) into numeric IP addresses (e.g. `10.0.1.50`).

Common DNS Record Types:
* **A Record:** Maps a domain name directly to an IPv4 address.
* **AAAA Record:** Maps a domain name to an IPv6 address.
* **CNAME Record:** Alias pointing one domain name to another domain name.
* **Alias Record:** An AWS-specific record that routes traffic directly to AWS resources (like ALBs, CloudFront distributions, or S3 websites) without incurring additional DNS query charges.

#### Intermediate
##### Routing Policies
* **Simple Routing:** Directs all traffic for a domain to a single target resource.
* **Weighted Routing:** Distributes traffic across multiple endpoints based on assigned weights (useful for canary testing).
* **Latency Routing:** Routes users to the AWS region that provides the lowest network latency.
* **Failover Routing:** Configures active-passive failover, routing traffic to a backup environment if the primary health check fails.
* **Geolocation Routing:** Routes traffic based on the user's geographic location (e.g., routing European users to an EU-based load balancer).

### 2. Interview Questions & Answers

#### Q: How do CNAME records differ from Alias records in Route 53?
**A:** A CNAME record points a domain name to another domain name, requires an extra DNS lookup step, and cannot be created for the zone apex (root domain, e.g. `company.com`). An Alias record is an AWS-specific extension that points a domain directly to an AWS resource, works for the zone apex, and is resolved natively by Route 53 for free.

#### Q: How do you configure active-passive Disaster Recovery at the DNS level?
**A:** Create a **Failover Routing Policy** in Route 53:
1. Define the Primary record pointing to your active region's ALB, and configure a Route 53 health check.
2. Define the Secondary record pointing to your backup region's static recovery page or secondary ALB.
3. Route 53 regularly polls the primary ALB's health check. If it fails, Route 53 automatically updates DNS records to route user requests to the secondary endpoint.

### 3. Key Takeaways
* Route 53 manages public and private DNS records.
* Use Alias records instead of CNAMEs to map root domains to AWS resources.
* Use failover and latency-based routing policies to build resilient, global architectures.

---

## TOPIC 15: CLOUDFORMATION & TERRAFORM — INFRASTRUCTURE AS CODE

### 1. Concept Explanation

#### Beginner
Infrastructure as Code (IaC) allows you to define, provision, and version your cloud resources using configuration files, eliminating manual configuration in the AWS Console.

Core Benefits:
* **Reproducibility:** Recreate dev, test, and prod environments consistently.
* **Audit Trail:** Track infrastructure changes using Git version control history.
* **Automation:** Integrate infrastructure provisioning directly into your CI/CD pipelines.

#### Intermediate
##### CloudFormation vs. Terraform
* **AWS CloudFormation:** An AWS-native service that uses YAML or JSON templates. State is managed automatically by AWS.
* **HashiCorp Terraform:** An open-source, cloud-agnostic tool using HashiCorp Configuration Language (HCL). State is managed via a state file (`.tfstate`) that you must store and lock securely.

##### Configuration Comparison Example
Here is how you define an EC2 security group in both CloudFormation and Terraform:

###### CloudFormation (YAML)
```yaml
AWSTemplateFormatVersion: '2010-09-09'
Description: App Security Group Stack
Resources:
  AppSecurityGroup:
    Type: AWS::EC2::SecurityGroup
    Properties:
      GroupDescription: Allow inbound application traffic
      SecurityGroupIngress:
        - IpProtocol: tcp
          FromPort: 8080
          ToPort: 8080
          CidrIp: 0.0.0.0/0
```

###### Terraform (HCL)
```hcl
resource "aws_security_group" "app_sg" {
  name        = "app-security-group"
  description = "Allow inbound application traffic"

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}
```

#### Advanced
##### Terraform State Management
In a team environment, you must store your Terraform state file in a central backend (like an S3 bucket) and enable state locking using a DynamoDB table to prevent concurrent executions from corrupting the state file:
```hcl
terraform {
  backend "s3" {
    bucket         = "company-terraform-states"
    key            = "prod/policy-service/terraform.tfstate"
    region         = "ap-south-1"
    dynamodb_table = "terraform-state-lock"
    encrypt        = true
  }
}
```

### 2. Interview Questions & Answers

#### Q: How does CloudFormation handle failures during stack updates?
**A:** If a stack update fails halfway, CloudFormation automatically rolls back all changes, destroying any newly created resources and restoring the infrastructure to its last-known stable configuration (rolling back to `ROLLBACK_COMPLETE`).

#### Q: What is configuration drift, and how do you resolve it?
**A:** Configuration drift occurs when resources are modified manually (e.g. in the console) outside of the IaC code. In CloudFormation, you can detect drift using the Drift Detection feature. In Terraform, running `terraform plan` compares the code against the actual cloud state and highlights discrepancies. To resolve drift, update the IaC code to match the manual changes, or apply the IaC template to overwrite them.

### 3. Key Takeaways
* CloudFormation is AWS-native; Terraform is cloud-agnostic and uses HCL.
* Use a remote backend (like S3) with locking (DynamoDB) to run Terraform safely in a team environment.
* IaC prevents configuration drift and ensures consistent environment deployments.

---

## TOPIC 16: CI/CD PIPELINE — JENKINS, GITHUB ACTIONS, HARNESS

### 1. Concept Explanation

#### Beginner
* **Continuous Integration (CI):** Automates compiling, building, and testing code every time a developer commits changes to the repository, catching bugs early.
* **Continuous Delivery/Deployment (CD):** Automatically deploys the built artifacts to staging or production environments after passing validation checks.

#### Intermediate
##### Production Pipeline Stages
A standard DevSecOps pipeline consists of the following steps:
1. **Source:** Developer pushes code to a branch, opening a Pull Request (PR).
2. **Build:** Maven compiles and packages the code (`mvn clean package`).
3. **Unit Tests:** Executes tests and generates code coverage reports (e.g., JaCoCo target > 80%).
4. **Static Code Analysis:** SonarQube scans code for quality gates, security vulnerabilities, and code smells.
5. **Interactive Security Testing:** Contrast Security agent scans the running JVM during tests.
6. **Containerization:** Docker builds a multi-stage image.
7. **Container Vulnerability Scan:** Twistlock/Trivy scans the image layers for CVEs.
8. **Publish:** Push the Docker image to a private registry (like Harbor or Amazon ECR).
9. **Deployment:** Harness or ArgoCD deploys the image to a Kubernetes cluster.
10. **Validation:** Executes smoke tests and monitors application logs for errors.

### 2. Spring Boot Pipeline & Dockerfile Examples

#### GitHub Actions Workflow (`ci-cd.yml`)
```yaml
name: CI/CD Pipeline - Policy Service

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: maven

      - name: Build and Test with Maven
        run: mvn clean verify -B

      - name: Run SonarQube Scan
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: |
          mvn sonar:sonar \
            -Dsonar.projectKey=policy-service \
            -Dsonar.host.url=https://sonarqube.company.com \
            -Dsonar.login=$SONAR_TOKEN

  docker-build-push:
    needs: build-and-test
    if: github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Code
        uses: actions/checkout@v4

      - name: Set up Docker Buildx
        uses: docker/setup-buildx-action@v3

      - name: Log in to Harbor
        uses: docker/login-action@v3
        with:
          registry: harbor.company.com
          username: ${{ secrets.HARBOR_USER }}
          password: ${{ secrets.HARBOR_PASSWORD }}

      - name: Build Docker Image
        run: |
          docker build -t harbor.company.com/apps/policy-service:${{ github.sha }} .

      - name: Scan Image with Trivy
        uses: aquasecurity/trivy-action@master
        with:
          image-ref: 'harbor.company.com/apps/policy-service:${{ github.sha }}'
          exit-code: '1'
          severity: 'CRITICAL'

      - name: Push to Harbor Registry
        run: |
          docker push harbor.company.com/apps/policy-service:${{ github.sha }}
```

#### Multi-Stage Dockerfile (Production Optimized)
This Dockerfile splits the build and run stages to minimize the final container image size and optimize dependency caching:
```dockerfile
# Stage 1: Build & Compile
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Layer Extraction
FROM eclipse-temurin:17-jre AS extractor
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# Stage 3: Final Production Image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Copy Spring Boot layers sequentially to optimize layer caching
COPY --from=extractor /app/dependencies/ ./
COPY --from=extractor /app/snapshot-dependencies/ ./
COPY --from=extractor /app/spring-boot-loader/ ./
COPY --from=extractor /app/application/ ./

EXPOSE 8080
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
```

### 3. Architecture Flow Diagram

```mermaid
flowchart TD
    Dev["Developer Git Push"] -->|"Trigger Pipeline"| GH["GitHub Actions Runner"]
    
    subgraph CI ["Continuous Integration (CI)"]
        GH --> Maven["Maven Build & Test"]
        Maven --> Sonar["SonarQube Quality Scan"]
        Sonar --> Contrast["Contrast JVM IAST Security"]
    end
    
    subgraph CD ["Continuous Deployment (CD)"]
        Contrast --> DockerBuild["Multi-Stage Docker Build"]
        DockerBuild --> Twistlock["Twistlock Image Vulnerability Scan"]
        Twistlock -->|Passes Scan| PushHarbor["Push Image to Harbor Registry"]
        PushHarbor --> Harness["Harness Deploy Engine"]
        Harness --> EKS["Deploy to EKS (K8s)"]
        EKS --> SmokeTest["Execute Smoke & Health Checks"]
    end

    Twistlock -->|Vulnerabilities Found| Fail["Block Deployment & Notify Dev"]

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef client fill:#7C2D12,stroke:#FDBA74,color:#FFFFFF,stroke-width:2px;
    classDef mgmt fill:#581C87,stroke:#D8B4FE,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;

    class Dev client;
    class GH,Maven,Sonar,Contrast,DockerBuild,Twistlock,PushHarbor,Harness,EKS,SmokeTest mgmt;
    class Fail db;
```

### 4. Interview Questions & Answers

#### Q: How do you handle failed security scans in the deployment pipeline?
**A:** If a vulnerability scanner (like Twistlock or Trivy) detects critical vulnerabilities exceeding the defined threshold (e.g., CVSS score >= 8), the pipeline is configured to fail immediately with exit code 1. This blocks the push to the container registry and prevents deployment to production. Developers are notified of the failed step with a report listing the vulnerable dependency, allowing them to patch it and retrigger the pipeline.

### 5. Key Takeaways
* Use multi-stage Docker builds to reduce image size and improve security.
* Integrate static code analysis (SonarQube) and security scanners (Trivy/Twistlock) to catch vulnerabilities early.
* Set up automated rollbacks in your CD tool (like Harness) triggered by health check failures.

---

## TOPIC 17: DOCKER — CONTAINERIZATION FOR JAVA DEVELOPERS

### 1. Concept Explanation

#### Beginner
Docker packages your application, along with its specific dependencies, libraries, and configurations, into a lightweight, portable container. This ensures that the application runs consistently across local development, staging, and production environments, eliminating the "works on my machine" issue.

Core Concepts:
* **Image:** A read-only template containing the application code and its dependencies (similar to a Java class blueprint).
* **Container:** A running instance of an image (similar to a Java object instance).
* **Registry:** A repository where Docker images are stored and shared (e.g., Docker Hub, Harbor, Amazon ECR).

#### Intermediate
##### Docker Compose
Docker Compose allows you to define and manage multi-container applications (like an API server running alongside database and caching containers) using a single YAML file:

```yaml
version: '3.8'

services:
  policy-service:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/policydb
    depends_on:
      postgres-db:
        condition: service_healthy
    networks:
      - app-network

  postgres-db:
    image: postgres:15-alpine
    environment:
      - POSTGRES_DB=policydb
      - POSTGRES_PASSWORD=secret
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 5
    networks:
      - app-network

networks:
  app-network:
    driver: bridge

volumes:
  pgdata:
```

#### Advanced
##### Image Optimization Strategies
* **Use JRE instead of JDK:** Run production containers using a lightweight JRE image (like `eclipse-temurin:17-jre-alpine` at ~100 MB) instead of a full JDK image (which can exceed 400 MB).
* **Leverage Layer Caching:** Order the commands in your `Dockerfile` so that rarely changed elements (like dependency downloads) are run before frequently changed elements (like application code compilation).
* **Minimize Layers:** Combine multiple `RUN` commands using `&&` and backslashes to reduce the total number of layers in the final image.

### 2. Java Developer Dockerfile Reference

#### Basic Dockerfile (Legacy/Reference)
```dockerfile
FROM openjdk:17-slim
WORKDIR /app
COPY target/policy-service-1.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "-Xms512m", "-Xmx1024m", "--spring.profiles.active=prod"]
```

### 3. Architecture Diagram

```mermaid
flowchart LR
    subgraph DevMachine ["Developer Machine"]
        DF["Dockerfile"] -->|"docker build"| Img["Docker Image"]
    end
    
    Img -->|"docker push"| Reg["Registry (ECR / Harbor)"]
    Reg -->|"docker pull"| Target["Production Server"]
    
    subgraph Prod ["Docker Host Runtime"]
        Target -->|"docker run"| C1["policy-service Container"]
        Target -->|"docker run"| C2["postgres Container"]
        C1 <-->|"Bridge Network"| C2
    end

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;

    class DF,Img,Reg,Target,C1 layer;
    class C2 db;
```

### 4. Interview Questions & Answers

#### Q: How do you pass database credentials to a container securely?
**A:** Do not hardcode credentials in the `Dockerfile` or the image. Instead, inject them at runtime using environment variables (e.g. `docker run -e DB_PASSWORD=secret my-image`), or integrate them with a secrets manager (such as AWS Secrets Manager or Kubernetes Secrets) to inject them directly into the container's memory environment.

#### Q: How do you troubleshoot a container that crashes immediately after starting?
**A:** 
1. Run `docker ps -a` to view the exit code of the stopped container.
2. Run `docker logs <container-id>` to check the application's startup logs and stack traces.
3. Use `docker inspect <container-id>` to check environment variables and command configurations.
4. If needed, run the container overriding the entrypoint to launch a shell for debugging: `docker run -it --entrypoint sh <image-name>`.

### 5. Key Takeaways
* Containers isolate application runtimes, ensuring consistent execution across environments.
* Use multi-stage builds and JRE-specific base images to keep production images small and secure.
* Use Docker Compose to orchestrate multi-container development and testing environments locally.

---

## TOPIC 18: KUBERNETES — CONTAINER ORCHESTRATION

### 1. Concept Explanation

#### Beginner
Kubernetes (K8s) is an open-source platform designed to automate deploying, scaling, and managing containerized applications across a cluster of host nodes.

Core Objects:
* **Pod:** The smallest deployable unit in Kubernetes, hosting one or more containers sharing a network and storage configuration. Pods are ephemeral.
* **Deployment:** Defines the desired state for your application fleet, managing pod replication, rolling updates, and rollbacks.
* **Service:** Provides a stable network IP address and DNS endpoint to route traffic to a dynamic group of pods.
* **ConfigMap / Secret:** Externalizes configuration parameters and sensitive credentials, injecting them into pods without rebuilding container images.

#### Intermediate
##### Spring Boot Kubernetes Deployment Configuration (`deployment.yaml`)
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: policy-service-deployment
  namespace: production
  labels:
    app: policy-service
spec:
  replicas: 3
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 0
  selector:
    matchLabels:
      app: policy-service
  template:
    metadata:
      labels:
        app: policy-service
    spec:
      containers:
        - name: policy-service
          image: harbor.company.com/apps/policy-service:v2
          ports:
            - containerPort: 8080
          resources:
            requests:
              cpu: "250m"
              memory: "512Mi"
            limits:
              cpu: "500m"
              memory: "1Gi"
          readinessProbe:
            httpGet:
              path: /actuator/health/readiness
              port: 8080
            initialDelaySeconds: 20
            periodSeconds: 10
          livenessProbe:
            httpGet:
              path: /actuator/health/liveness
              port: 8080
            initialDelaySeconds: 40
            periodSeconds: 20
```

#### Advanced
##### Kubernetes Architecture
Kubernetes splits responsibilities between the **Control Plane** (orchestration management) and **Worker Nodes** (hosting execution workloads):

```mermaid
flowchart TD
    subgraph ControlPlane ["Control Plane (AWS EKS Managed)"]
        API["API Server"]
        etcd[(etcd State Store)]
        Sched["Scheduler"]
        CM["Controller Manager"]
        
        API <--> etcd
        API <--> Sched
        API <--> CM
    end

    subgraph Workers ["Worker Nodes"]
        subgraph Node1 ["EC2 Node 1"]
            Kubelet1["kubelet"]
            Proxy1["kube-proxy"]
            PodA["Pod A: policy-service"]
        end
        subgraph Node2 ["EC2 Node 2"]
            Kubelet2["kubelet"]
            Proxy2["kube-proxy"]
            PodB["Pod B: policy-service"]
        end
    end

    API <--> Kubelet1 & Kubelet2
    ALB["AWS Load Balancer"] --> Proxy1 & Proxy2
    Proxy1 --> PodA
    Proxy2 --> PodB

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;
    classDef mgmt fill:#581C87,stroke:#D8B4FE,color:#FFFFFF,stroke-width:2px;

    class API,Sched,CM,Kubelet1,Kubelet2,Proxy1,Proxy2,ALB mgmt;
    class etcd db;
    class PodA,PodB layer;
```

### 2. Interview Questions & Answers

#### Q: What is the difference between a Liveness Probe and a Readiness Probe?
**A:** 
* **Readiness Probes** check if a pod is ready to accept incoming network traffic. If the readiness probe fails, Kubernetes stops routing traffic to the pod via the Service, but leaves the pod running.
* **Liveness Probes** check if the application inside the pod is still running healthily. If the liveness probe fails (indicating a deadlock or crash), Kubernetes terminates the pod and launches a new one.

#### Q: How do you troubleshoot a pod stuck in `CrashLoopBackOff`?
**A:** 
1. Run `kubectl get pods` to identify the crashing pod.
2. Run `kubectl describe pod <pod-name>` to check the events history for OOM (Out Of Memory) flags or exit codes.
3. Check the application logs using `kubectl logs <pod-name>`.
4. If the pod crashed immediately, inspect the logs of the previous crashed instance: `kubectl logs <pod-name> --previous`.
5. Check if dependency configurations, database URLs, or secret values are missing or misconfigured in the deployment file.

### 3. Key Takeaways
* Pods are ephemeral; use Services to provide stable network entry points.
* Set resource requests and limits to ensure fair sharing of compute capacity across the cluster.
* Use readiness and liveness probes to monitor application health and prevent routing traffic to unhealthy containers.

---

## TOPIC 19: SECURITY — SONARQUBE, TWISTLOCK, CONTRAST

### 1. Concept Explanation

#### Beginner
Integrating security tooling directly into the CI/CD pipeline (known as **Shift Left Security**) allows you to detect code quality issues and vulnerabilities before the code is deployed.

Core Tooling:
* **SonarQube (SAST):** Performs static application security testing, scanning source code without executing it to detect bugs, code smells, duplicate code, and potential vulnerabilities.
* **Twistlock / Prisma Cloud (SCA & Container Security):** Scans built container images for known vulnerabilities in OS packages, libraries, and runtime dependencies.
* **Contrast Security (IAST/RASP):** Performs interactive application security testing using a security agent running inside the JVM. It monitors execution flows during tests to detect runtime vulnerabilities (like SQL injection or data exposure).

#### Intermediate
##### SonarQube Quality Gates
To prevent bad code from merging, define a quality gate that must pass in the CI pipeline:
* Code coverage must be greater than or equal to 80%.
* Zero new critical or blocker bugs allowed.
* Code duplication rate must be below 3%.
* Security rating must be 'A'.

#### Advanced
##### SAST vs. DAST vs. IAST
| Feature | SAST (SonarQube) | DAST (OWASP ZAP) | IAST (Contrast Security) |
| :--- | :--- | :--- | :--- |
| **Method** | Scans source code | Attacks running application externally | Monitors runtime JVM execution internally |
| **Pipeline Stage** | Build time | Post-deployment testing | During integration tests |
| **Accuracy** | High false positives (cannot verify if code is reachable) | Medium false positives (black box testing) | High accuracy (traces actual execution paths) |
| **Required State**| Source code files | Running application | Running application with test suites |

```mermaid
flowchart LR
    subgraph Pipeline ["CI/CD Pipeline Flow"]
        Code["Source Code"] -->|SAST Scan| Build["Build JAR"]
        Build -->|IAST Agent active| Test["Run Integration Tests"]
        Test -->|DAST Scan| Deploy["Production Deploy"]
    end

    classDef blueNode fill:#1E88E5,stroke:#93C5FD,color:#FFFFFF,stroke-width:2px;
    classDef greenNode fill:#43A047,stroke:#A7F3D0,color:#FFFFFF,stroke-width:2px;
    classDef redNode fill:#E53935,stroke:#FCA5A5,color:#FFFFFF,stroke-width:2px;

    class Code,Build blueNode;
    class Test greenNode;
    class Deploy redNode;
```

### 2. Interview Questions & Answers

#### Q: How does Contrast Security (IAST) differ from traditional static analysis (SAST)?
**A:** Static analysis (SAST) scans code line-by-line without executing it, checking for syntax patterns that suggest vulnerabilities, which can result in false positives. Contrast Security (IAST) runs inside the JVM using bytecode instrumentation. It monitors data flows in real-time during integration tests, confirming if a vulnerable code path is actually executed and exploitable, resulting in much higher accuracy.

### 3. Key Takeaways
* Static analysis (SAST) runs early on source code files; Dynamic analysis (DAST) tests running applications; Interactive analysis (IAST) uses agent instrumentation inside the runtime.
* Enforce automated quality gates in the build pipeline to reject code that fails quality or coverage targets.
* Twistlock image scanning blocks deployments of container images containing critical CVEs.

---

## TOPIC 20: DEPLOYMENT STRATEGIES

### 1. Concept Explanation

#### Beginner
Deployment strategies define how new versions of an application are rolled out to production while minimizing downtime and testing changes safely.

Common Strategies:
* **Blue-Green:** Two identical production environments. Traffic is switched instantly from one to the other.
* **Canary:** A new version is deployed to a small subset of servers (e.g. 5% of traffic) to test stability before rolling it out to the entire fleet.
* **Rolling Update:** Instances are replaced incrementally one by one, keeping overall capacity stable during the rollout.

#### Intermediate
##### Comparison of Deployment Strategies
| Feature | Blue-Green | Canary | Rolling Update |
| :--- | :--- | :--- | :--- |
| **Infrastructure Cost** | 2x (requires running two full environments during rollout) | 1.1x (requires minor extra capacity) | 1x (uses existing capacity) |
| **Rollback Speed** | Instant (switch load balancer back) | Fast (terminate canary instances) | Slow (incrementally rollback instances) |
| **Real User Testing** | No (pre-testing is done in isolation) | Yes (canary receives real production traffic) | Yes (general users receive traffic) |
| **Database Migrations**| Difficult (both versions must share or sync database schemas) | Difficult (database must support both application versions) | Medium |

#### Advanced
##### Deployment Strategy Routing Diagrams

```mermaid
flowchart TD
    subgraph BlueGreen ["Blue-Green (Instant Switch)"]
        ALB1["ALB Router"]
        ALB1 -->|Active 100%| Blue["Blue Environment (v1.0)"]
        ALB1 -.->|Inactive 0%| Green["Green Environment (v2.0)"]
    end
    
    subgraph Canary ["Canary (Gradual Traffic Shift)"]
        ALB2["ALB Splitter"]
        ALB2 -->|90% Traffic| Stable["Production Fleet (v1.0)"]
        ALB2 -->|10% Traffic| Can["Canary Instance (v2.0)"]
    end

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;

    class ALB1,ALB2,Blue,Stable layer;
    class Green,Can db;
```

### 2. Interview Questions & Answers

#### Q: How do you implement Canary deployments using Route 53 or an ALB?
**A:** 
* **Using Route 53:** Create two DNS records pointing to your active load balancer (v1.0) and new load balancer (v2.0). Configure a **Weighted Routing Policy**, assigning weight 90 to v1.0 and weight 10 to v2.0. Route 53 will resolve requests accordingly, sending 10% of users to the new version.
* **Using an ALB:** Define a target group for each application version. Configure the listener rule to route traffic across the target groups using weights (e.g. 90% to Target Group A, 10% to Target Group B).

### 3. Key Takeaways
* Blue-Green deployments provide safe, instant rollbacks, but double your compute costs during the rollout.
* Canary deployments allow you to test new features on a small percentage of real production traffic, minimizing the blast radius of errors.
* Database changes must be backward compatible to support both old and new application versions running simultaneously during a deployment.

---

## TOPIC 21: MONITORING & OBSERVABILITY

### 1. Concept Explanation

#### Beginner
Observability allows you to monitor application health and troubleshoot issues using three primary signals:
* **Metrics:** Numeric aggregates showing system resource usage (e.g., memory usage, requests per second).
* **Logs:** Text records of application events and errors.
* **Traces:** Traces following a single request as it travels through a distributed microservices network.

#### Intermediate
##### ELK Stack Logging Pipeline
Logs flow from applications through parsing and indexing engines before becoming queryable:
1. **Spring Boot App:** Writes structured log events (preferably in JSON format).
2. **Logstash:** Gathers, filters, parses, and enriches the log entries.
3. **Elasticsearch:** A search index database that stores and indexes log messages.
4. **Kibana:** A web interface used to search logs, build dashboards, and set alerts.

```mermaid
flowchart LR
    App["Spring Boot App (JSON Logs)"] --> Logstash["Logstash (Parse & Filter)"]
    Logstash --> ES["Elasticsearch (Index & Store)"]
    ES --> Kibana["Kibana Dashboard (UI)"]

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;

    class App,Logstash,Kibana layer;
    class ES db;
```

##### Spring Boot Logback JSON Encoder Configuration (`logback-spring.xml`)
Configure your application to write structured JSON logs to standard output, making ingestion by Elasticsearch or Splunk straightforward:
```xml
<configuration>
    <appender name="JSON_CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
            <providers>
                <timestamp/>
                <loggerName/>
                <logLevel/>
                <threadName/>
                <message/>
                <stackTrace/>
                <mdc/> <!-- Injects MDC keys like traceId automatically -->
            </providers>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="JSON_CONSOLE"/>
    </root>
</configuration>
```

#### Advanced
##### Custom Micrometer Prometheus Registry
Configure Spring Boot Actuator to expose metrics in a Prometheus-compatible format:
```yaml
# application.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,prometheus
  metrics:
    export:
      prometheus:
        enabled: true
```

Use Micrometer's registry to record custom business metrics (such as completed orders or processing times) in your Java code:
```java
package com.company.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

@Component
public class BusinessMetrics {

    private final Counter claimCounter;
    private final Timer processingTimer;

    public BusinessMetrics(MeterRegistry registry) {
        this.claimCounter = Counter.builder("policy.claims.processed")
                .description("Total policy claims processed successfully")
                .tag("tier", "premium")
                .register(registry);

        this.processingTimer = Timer.builder("policy.claims.processing.time")
                .description("Time taken to evaluate policy claims")
                .register(registry);
    }

    public void incrementClaims() {
        this.claimCounter.increment();
    }

    public Timer getTimer() {
        return this.processingTimer;
    }
}
```

### 2. Interview Questions & Answers

#### Q: How do you correlate log messages across a distributed microservices architecture?
**A:** Use a **Correlation ID (Trace ID)** pattern:
1. The API Gateway intercepts incoming requests. If no `X-Trace-Id` header is found, it generates a unique UUID.
2. The gateway passes this UUID as an HTTP header to all downstream microservices.
3. Each service intercepts the header and injects it into its logging **Mapped Diagnostic Context (MDC)**.
4. When logging events occur, the log appender prints the `traceId` alongside the log message.
5. In Kibana or Splunk, querying `traceId = "abc-123"` displays a chronological timeline of logs generated by all services involved in processing that request.

### 3. Key Takeaways
* Structured JSON logging allows centralized log engines (ELK/Splunk) to parse and index log fields automatically.
* Spring Boot Actuator exposes system and custom metrics to Prometheus, which are visualized using Grafana dashboards.
* Use Trace IDs and MDC to track and correlate requests across distributed microservices.

---

## TOPIC 22: COST OPTIMIZATION

### 1. Cost Optimization Strategies

#### EC2 Cost Optimization
* **Purchase RIs or Savings Plans:** Commit to a 1 or 3-year term for constant, baseline production workloads to save up to 72% compared to on-demand pricing.
* **Scale-in During Idle Times:** Set scheduled Auto Scaling policies to shut down development instances outside of business hours (saving up to 50%).
* **Right-Size Instances:** Monitor CPU and memory usage using CloudWatch. If utilization is consistently below 20%, downsize the instance family.
* **Adopt Graviton (ARM) Instances:** Move workloads to AWS Graviton processors (`m6g` families) which offer up to 40% better price-performance than Intel processors.

#### S3 Cost Optimization
* **Enable Intelligent-Tiering:** Automatically transitions files with unknown access patterns to cheaper storage tiers, saving money without retrieval penalties.
* **Set Lifecycle Rules:** Archive cold files to Glacier or delete temporary files automatically after a defined period.
* **Clean Up Incomplete Multipart Uploads:** Configure lifecycle rules to delete incomplete uploads, which otherwise incur storage charges indefinitely.

#### RDS Cost Optimization
* **Reserved DB Instances:** Purchase RIs for production database instances.
* **Avoid Multi-AZ for Non-Production:** Disable Multi-AZ for development and testing environments, reducing database instance costs by 50%.
* **Aurora Serverless v2:** Use Serverless v2 for test environments with sporadic usage, allowing the compute capacity to scale down to 0.5 ACUs when idle.

#### General Optimization
* **Tag All Resources:** Enforce a resource tagging policy (e.g. `Owner`, `Environment`, `CostCenter`) to identify and clean up unallocated or orphaned resources.
* **Run Compute Optimizer:** Use AWS Compute Optimizer's machine learning recommendations to identify over-provisioned resources.
* **Clean Up Idle Resources:** Delete unattached EBS volumes, unused Elastic Load Balancers, and idle Elastic IP addresses.

### 2. Key Takeaways
* Match workloads to the appropriate billing model: Reserved for steady baselines, Spot for flexible tasks, and On-Demand for variable workloads.
* Enable S3 Intelligent-Tiering and lifecycle rules to automate cold data archival.
* Disable Multi-AZ in non-production environments and clean up unattached EBS volumes to reduce waste.

---

## TOPIC 23: REAL-WORLD PRODUCTION SCENARIOS

### SCENARIO 1: Database Connection Pool Exhaustion

#### Symptom
A production Spring Boot application deployed on ECS starts throwing `ConnectionPoolTimeoutException` errors. Users receive HTTP 503 Service Unavailable errors.

#### Diagnosis
1. Check RDS CloudWatch metrics: `DatabaseConnections` shows a vertical line hitting the database engine's maximum allowed connections limit.
2. Check Spring Boot log files: Logs show HikariCP threads waiting to acquire a connection: `connection is not available, request timed out after 30000ms`.
3. Check code changes: A recently deployed feature contains a data export endpoint that opens a connection but fails to release it.

#### Buggy Code (Leaking Connections)
```java
@GetMapping("/export")
public ResponseEntity<List<Policy>> exportData() throws SQLException {
    // Manually opens connection bypassing connection pool lifecycle management
    Connection conn = dataSource.getConnection();
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM policy");
    List<Policy> results = mapResults(rs);
    // Connection is not closed! It leaks and remains open indefinitely.
    return ResponseEntity.ok(results);
}
```

#### Refactored Code (Fixed)
Use Java's **try-with-resources** statement or Spring's `@Transactional` annotation to ensure connections are closed automatically when the execution scope exits:
```java
@GetMapping("/export")
@Transactional(readOnly = true) // Spring manages connection opening and closing automatically
public ResponseEntity<List<Policy>> exportData() {
    List<Policy> results = policyRepository.findAll();
    return ResponseEntity.ok(results);
}
```

#### Recovery Steps
1. Restart the application instances on ECS to force-close leaked connections.
2. Scale up the application containers incrementally while monitoring the database connection metrics.

---

### SCENARIO 2: Out of Memory (OOM) Crash in a Kubernetes Pod

#### Symptom
A Spring Boot application running inside a Kubernetes cluster keeps crashing and restarting. Running `kubectl get pods` shows a status of `CrashLoopBackOff`.

#### Diagnosis
1. Run `kubectl describe pod <pod-name>` to check the termination history:
```text
Last State: Terminated
Reason: OOMKilled
Exit Code: 137
```
This indicates the container process was terminated by the Linux Out-Of-Memory (OOM) killer because it exceeded its container memory limit.
2. Monitor memory usage trends in Grafana: memory usage climbs steadily until it hits the container limit of 1 GB, triggering the crash.
3. Root cause: The application code caches data indefinitely using a static map without an eviction policy, causing a memory leak.

#### Buggy Cache Configuration
```java
@Component
public class CacheService {
    // Static map grows indefinitely without limits, causing a memory leak
    private final Map<String, UserProfile> userCache = new ConcurrentHashMap<>();

    public void cacheUser(UserProfile user) {
        userCache.put(user.getId(), user);
    }
}
```

#### Refactored Code (Fixed)
Configure a cache manager (like Caffeine) with maximum size and time-to-live (TTL) limits:
```java
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(5000) // Caps total cache objects to 5,000 entries
                .expireAfterWrite(15, TimeUnit.MINUTES) // Sets 15-minute TTL eviction
                .recordStats());
        return cacheManager;
    }
}
```

#### Kubernetes Resource Definition Alignment
Configure JVM parameters to respect container memory limits, preventing the heap memory from exceeding the container's physical limit:
```yaml
# deployment.yaml
resources:
  requests:
    memory: "512Mi"
  limits:
    memory: "1Gi" # Container is hard-capped at 1 GB
env:
  - name: JAVA_TOOL_OPTIONS
    value: "-XX:MaxRAMPercentage=75.0 -XX:+UseContainerSupport"
    # Max Heap = 75% of container limit (768 MB), leaving 256 MB for JVM metaspace and system threads.
```

---

### SCENARIO 3: Slow CI/CD Pipeline Builds

#### Symptom
Software delivery is slowed down because the GitHub Actions build pipeline takes 25 minutes to complete.

#### Diagnosis
* The runner downloads all Maven dependencies from Maven Central on every execution (no caching).
* Single-threaded test executions.
* Container builds do not leverage Docker layer caching.

#### Optimizations Applied
1. **Enable GitHub Actions Caching:** Cache the local Maven repository:
```yaml
- name: Cache Maven packages
  uses: actions/cache@v4
  with:
    path: ~/.m2/repository
    key: ${{ runner.os }}-maven-${{ hashFiles('**/pom.xml') }}
    restore-keys: |
      ${{ runner.os }}-maven-
```
2. **Run Tests in Parallel:** Configure Maven Surefire to run tests concurrently:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <parallel>methods</parallel>
        <threadCount>4</threadCount>
    </configuration>
</plugin>
```
3. **Multi-Stage Docker caching:** Structure the Dockerfile layers so that dependencies are cached unless `pom.xml` changes.

#### Results
Build execution time reduced from 25 minutes to under 5 minutes.

---

### SCENARIO 4: Auto Scaling Fails During Traffic Spikes

#### Symptom
During a marketing promotion, application response times increase to 10 seconds, but the Auto Scaling Group does not scale out.

#### Diagnosis
* The ASG scale-out policy is configured to trigger when average CPU utilization exceeds 70%.
* The bottleneck is memory leak pressure and network I/O blockages, while CPU utilization remains below 20%.

#### Optimization Applied
Configure scaling based on **ALB Request Count Per Target** or **SQS Queue Depth**, which act as better indicators of traffic pressure for I/O-bound applications:
```bash
aws autoscaling put-scaling-policy \
  --policy-name request-count-scaling \
  --auto-scaling-group-name prod-asg \
  --policy-type TargetTrackingScaling \
  --target-tracking-configuration '{
    "TargetValue": 1000.0,
    "PredefinedMetricSpecification": {
      "PredefinedMetricType": "ALBRequestCountPerTarget",
      "ResourceLabel": "app/prod-alb/12345/targetgroup/prod-targets/54321"
    }
  }'
```

---

### 5. Key Takeaways
* Clean up database connections and resource leaks inside a `try-with-resources` block or use transactional annotations to prevent pool exhaustion.
* Always configure JVM heap limits (`MaxRAMPercentage`) to fit within container limits to prevent OS termination crashes (Exit Code 137).
* Align your scaling policies with your application's actual resource bottlenecks (e.g., Request Count or Queue Depth instead of just CPU).

---

## TOPIC 24: COMPARISON TABLES

### TABLE 1: Compute Options Comparison
| Feature | AWS EC2 | AWS Lambda | AWS ECS (Fargate) |
| :--- | :--- | :--- | :--- |
| **Virtualization Level** | Machine Level (VM) | Function Level | Container Level |
| **Management Effort** | High (you manage OS patches) | Zero (fully managed serverless) | Low (managed container runtime) |
| **Scaling Delay** | Minutes (boots new OS instance) | Milliseconds | Seconds |
| **Execution Limit** | None | 15 Minutes | None |
| **Cost Model** | Hourly rate (billed for idle time) | Billed per execution duration | Billed per container size and run duration |
| **State Support** | Stateful or Stateless | Stateless only | Stateless (can mount EFS for state) |

### TABLE 2: Relational Databases vs. NoSQL
| Feature | Amazon RDS | Amazon Aurora | Amazon DynamoDB |
| :--- | :--- | :--- | :--- |
| **Storage Type** | SQL Relational | SQL Relational | NoSQL Key-Value / Document |
| **Scaling** | Vertical scale-up | Auto-scaling storage up to 128 TB | Infinite horizontal auto-scaling |
| **Replication** | Multi-AZ (1 standby copy) | Replicates 6 ways across 3 AZs | Replicates across multiple AZs (supports Global Tables) |
| **Throughput limits** | Constrained by instance size | Capped by cluster memory/CPU | Infinite (scales read/write capacity units) |
| **Failover time** | 60–120 seconds | Under 30 seconds | Milliseconds (handled transparently) |

### TABLE 3: Messaging Services Comparison
| Feature | Amazon SQS | Amazon SNS | Amazon EventBridge |
| :--- | :--- | :--- | :--- |
| **Messaging Pattern** | Queue (Point-to-Point) | Pub/Sub (Publish/Subscribe) | Event Bus (Router) |
| **Delivery Model** | Pull (consumers poll the queue) | Push (SNS sends message to sub) | Push (routes events to targets) |
| **Message Ordering** | FIFO queues support strict order | Best-effort order | No order guarantees |
| **Data Retention** | Up to 14 days | No retention (pushed instantly) | Up to 24 hours (using archive features) |
| **Rule Filtering** | None | Simple message attributes | Advanced JSON schema filtering |

### TABLE 4: Infrastructure as Code Tooling
| Feature | AWS CloudFormation | HashiCorp Terraform | AWS CDK |
| :--- | :--- | :--- | :--- |
| **Language** | YAML / JSON | HCL | TypeScript, Java, Python, Go |
| **Scope** | AWS resources only | Multi-Cloud | AWS resources only |
| **State Location** | Managed by AWS | State file stored by user | Synthesized to CloudFormation templates |
| **Rollback Capability** | Automatically rolls back on failure | Leaves state partially applied | Automatically rolls back via CloudFormation |
| **Code Completion** | Basic | Good | Native IDE validation and code compilation |

---

## TOPIC 25: INTERVIEW QUICK REFERENCE

### Q1: Explain the AWS Shared Responsibility Model.
**A:** AWS manages security **of** the cloud (guarding hardware, hypervisors, and data centers). The customer manages security **in** the cloud (securing application code, IAM configuration, database schema files, operating systems, and network paths).

### Q2: What is the difference between EBS, S3, and EFS?
**A:**
* **EBS** is a persistent block storage volume attached to a single EC2 instance, restricted to a single Availability Zone.
* **S3** is an object storage service accessible globally via HTTP APIs.
* **EFS** is a shared network file system that can be mounted by multiple EC2 instances concurrently across availability zones.

### Q3: How do you secure an S3 bucket in production?
**A:** Enable Block Public Access, apply a bucket policy allowing access only to specific IAM roles, enable default KMS encryption, enable versioning to prevent accidental deletions, and use VPC Endpoints for private data transfers.

### Q4: Explain the difference between Security Groups and Network ACLs (NACLs).
**A:** Security groups are stateful and act at the instance level. NACLs are stateless, act at the subnet level, and evaluate rules sequentially.

### Q5: How does an EC2 Auto Scaling Group (ASG) work?
**A:** The ASG monitors CPU, memory, or request metrics via CloudWatch. When a metric exceeds the defined threshold, the ASG launches additional instances from a launch template and registers them with the ALB. When traffic decreases, it terminates idle instances.

### Q6: What is an IAM Role, and when should you use it?
**A:** An IAM Role grants temporary security credentials to trusted services or users. Use it to allow applications running on EC2 or Lambda to access AWS resources securely without hardcoding credential keys.

### Q7: How does RDS Multi-AZ replication work?
**A:** Writes to the primary database instance are replicated synchronously to a standby instance in a different AZ. If the primary AZ suffers an outage, RDS automatically updates the DNS record to failover to the standby instance.

### Q8: What is the difference between Horizontal and Vertical Scaling?
**A:** Horizontal scaling (scaling out) adds more instances to your fleet. Vertical scaling (scaling up) upgrades an instance to a larger size (more CPU/memory). Horizontal scaling is preferred because it eliminates single points of failure.

### Q9: Explain the difference between Blue-Green and Canary deployments.
**A:** Blue-Green deploys the new version to an identical environment and switches 100% of traffic over instantly. Canary deploys the new version to a small subset of servers (e.g. 10% of traffic) to test stability before rolling it out to the rest of the fleet.

### Q10: How do you manage database credentials securely in a Spring Boot application on AWS?
**A:** Store credentials in **AWS Secrets Manager**. Use the Spring Cloud AWS Secrets Manager Starter to import the database properties at application startup:
`spring.config.import=aws-secretsmanager:/secrets/prod-db-credentials`
Do not store credentials in your code repository or environment files.

### Q11: What is VPC Peering, and what is its main limitation?
**A:** VPC Peering connects two VPCs using private IPs. Its main limitation is that routing is non-transitive: if VPC A is peered with VPC B, and B is peered with C, VPC A cannot communicate with VPC C without a direct peer link.

### Q12: How do you troubleshoot a Lambda function that times out?
**A:** Check CloudWatch Logs to find the last statement executed before the timeout. Check connection pool limits and verify if the function is blocked waiting for database connections or external API responses. Adjust the execution timeout or allocate more memory if necessary.

### Q13: What is Amazon CloudFront, and how does it optimize web requests?
**A:** CloudFront is a Content Delivery Network (CDN) that caches static assets (images, CSS, JS) at edge locations globally, reducing latency by serving requests closer to the user.

### Q14: How do you implement caching in a Spring Boot application on AWS?
**A:** Deploy **Amazon ElastiCache (Redis)** and integrate it using Spring Boot Starter Data Redis:
```yaml
spring:
  cache:
    type: redis
  redis:
    host: prod-cache.xyz.cache.amazonaws.com
```
Use the `@Cacheable` annotation on service methods to cache database query results.

### Q15: How do you deploy microservices on AWS EKS?
**A:** Package your microservice into a Docker container and push the image to Amazon ECR. Write Kubernetes manifest files (`deployment.yaml` and `service.yaml`), configure liveness and readiness probes, and deploy them using `kubectl`. Route external traffic to the pods using an AWS Load Balancer Controller.

### Q16: How do you scale an EBS volume without downtime?
**A:** Modify the volume size directly using the AWS Console or CLI:
`aws ec2 modify-volume --volume-id vol-xxx --size 100`
Once updated, run OS commands (like `growpart` and `resize2fs` on Linux) to extend the file system partition without restarting the instance.

### Q17: Why should you avoid logging to files inside a container?
**A:** Containers are ephemeral; any files written inside a container are lost when the container is stopped or restarted. Instead, write logs to standard output (`stdout`) and standard error (`stderr`), allowing log collectors (like FluentBit or CloudWatch agents) to capture and forward them to a centralized store.

### Q18: What is AWS X-Ray?
**A:** AWS X-Ray is a distributed tracing service that maps request flows across microservices, databases, and queues, helping you identify performance bottlenecks and track down failures in distributed architectures.

### Q19: When should you use Spot Instances?
**A:** Use Spot Instances for stateless, fault-tolerant, or batch-processing workloads (such as CI/CD runners or background workers) that can handle interruptions with a 2-minute warning.

### Q20: Your Spring Boot application's response time suddenly spikes from 200ms to 5s. How do you diagnose it?
**A:** 
1. Check ALB metrics in CloudWatch: verify if `TargetResponseTime` spiked and check for a corresponding spike in HTTP 5XX errors.
2. Check database metrics: check RDS CPU utilization, active connections, and read/write latency.
3. Check application logs in Kibana or Splunk to search for error spikes or long-running database queries.
4. Run thread dumps (`jstack`) on container instances to identify thread contention or blocked states.

---

## TOPIC 26: EFS — ELASTIC FILE SYSTEM

### 1. Concept Explanation

#### Beginner
Elastic File System (EFS) provides a shared network file system (NFSv4) that can be mounted concurrently by multiple EC2 instances, containers, or on-premises servers across multiple availability zones.

EFS vs. EBS Storage Model:
* **EFS:** Mounted concurrently by multiple EC2 instances across AZs (shared file system).
* **EBS:** Attached to a single EC2 instance within a single AZ (dedicated block storage).

```mermaid
flowchart TD
    subgraph MultiAZ ["Shared EFS Volume (Region-wide)"]
        EFS["Elastic File System (EFS)"]
    end
    
    subgraph Compute ["EC2 instances in different AZs"]
        EC1["EC2 Instance 1 (AZ-1a)"]
        EC2["EC2 Instance 2 (AZ-1b)"]
        EC3["EC2 Instance 3 (AZ-1c)"]
    end

    EC1 -->|Mount NFS port 2049| EFS
    EC2 -->|Mount NFS port 2049| EFS
    EC3 -->|Mount NFS port 2049| EFS

    classDef layer fill:#4F46E5,stroke:#C7D2FE,color:#FFFFFF,stroke-width:2px;
    classDef db fill:#0F766E,stroke:#99F6E4,color:#FFFFFF,stroke-width:2px;

    class EC1,EC2,EC3 layer;
    class EFS db;
```

#### Intermediate
##### EFS vs. EBS vs. S3 Comparison
| Feature | EBS | EFS | S3 |
| :--- | :--- | :--- | :--- |
| **Access Pattern** | Single instance (per AZ) | Multiple instances (Multi-AZ) | Globally accessible via HTTP APIs |
| **Protocol** | Block Storage | NFSv4 File System | REST Object Storage |
| **Capacity Scaling** | Fixed size (manual resizing) | Elastic auto-scaling | Infinite auto-scaling |
| **Average Cost** | ~$0.10 per GB-month | ~$0.30 per GB-month | ~$0.023 per GB-month |
| **Primary Use Case** | OS root disks, relational database files | Shared media directories, config folders | Data backups, static assets, data lakes |

* **Performance Modes:**
  * *General Purpose:* Optimized for low latency (best for web servers, content management).
  * *Max I/O:* Optimized for high scale and aggregate throughput (best for big data analysis, media processing).
* **Throughput Modes:**
  * *Elastic:* Automatically scales throughput to match current traffic workloads (default).
  * *Provisioned:* Sets a fixed throughput capacity regardless of storage size.

#### Advanced
* **EFS Lifecycle Management:** Automatically moves infrequently accessed files to the EFS Infrequent Access (EFS-IA) tier after a defined period (e.g. 30 days), reducing storage costs by up to 92%.
* **Security Rules:** Configure Security Groups to restrict traffic to the EFS mount target interfaces, allowing inbound connections on TCP port 2049 (NFS) only from your application servers' security groups.

### 2. EFS Operations Reference

#### Mount EFS on EC2 Linux
```bash
# Install EFS helper utilities
sudo yum install -y amazon-efs-utils

# Create the target mount folder
sudo mkdir -p /mnt/shared-storage

# Mount EFS securely using TLS encryption
sudo mount -t efs -o tls fs-0abc123456789def0:/ /mnt/shared-storage

# Configure /etc/fstab to persist the mount across system reboots
echo 'fs-0abc123456789def0:/ /mnt/shared-storage efs defaults,tls,_netdev 0 0' | sudo tee -a /etc/fstab
```

### 3. Interview Questions & Answers

#### Q: When would you choose EFS over EBS?
**A:** Choose **EFS** when you have multiple compute nodes (EC2 or Fargate containers) that need concurrent read/write access to a shared directory (such as a shared directory for uploads, user home directories, or shared application configurations). Choose **EBS** for single-instance, high-performance database workloads (such as hosting the physical database files of a MySQL or PostgreSQL server).

### 4. Best Practices
* Enable EFS Lifecycle Management to transition old files to the EFS-IA tier and save costs.
* Restrict inbound traffic to the EFS security group to port 2049, allowing access only from the application security group.
* Enable encryption at rest and in transit when mounting the file system.

---

## TOPIC 27: ELASTIC BEANSTALK — PLATFORM AS A SERVICE

### 1. Concept Explanation

#### Beginner
Elastic Beanstalk is a Platform as a Service (PaaS) offering. You upload your application code (JAR, WAR, or ZIP files), and Beanstalk automatically provisions and manages the load balancers, auto scaling groups, EC2 instances, and databases, allowing you to deploy applications without managing infrastructure.

Supported Runtimes:
* Java (Corretto configurations for Spring Boot), Node.js, Python, PHP, Ruby, Go, Docker, and .NET.

#### Intermediate
##### Beanstalk Environment Configuration (`.ebextensions/jvm.config`)
You can customize the environment configuration by adding configuration files in the `.ebextensions` directory at the root of your application package:
```yaml
option_settings:
  aws:elasticbeanstalk:application:environment:
    SPRING_PROFILES_ACTIVE: production
    SERVER_PORT: 5000
  aws:autoscaling:asg:
    MinSize: 2
    MaxSize: 8
  aws:autoscaling:launchconfiguration:
    InstanceType: t3.medium
```

* **Default Port for Java Platform:** Beanstalk's built-in Nginx reverse proxy routes incoming public requests to port 5000 by default. Set `server.port=5000` in your Spring Boot application properties, or set the `SERVER_PORT` environment variable to `5000`.

#### Advanced
##### Deployment Policies
* **All at Once:** Deploys the new version to all instances simultaneously. This is the fastest method but incurs application downtime.
* **Rolling:** Deploys the new version to instances in batches, keeping the remaining instances active to prevent downtime.
* **Rolling with Additional Batch:** Launches a new batch of instances to deploy the update before stopping old instances, maintaining full application capacity during the rollout.
* **Immutable:** Launches a completely new auto-scaling group with new instances, tests their health, and switches traffic over, terminating the old group if successful.
* **Blue-Green:** Deploys the new version to a separate environment, allows you to verify it, and then swaps the environment URLs (CNAMEs) to route traffic to the new version with zero downtime.

### 2. Interview Questions & Answers

#### Q: How do you choose between deploying on EC2 manually, using Elastic Beanstalk, or using ECS/EKS?
**A:** 
| Option | Control Level | Setup Time | Management Effort | Best For |
| :--- | :--- | :--- | :--- | :--- |
| **EC2 Manual** | Full control | Hours/Days | High | Custom OS configurations, legacy systems |
| **Elastic Beanstalk**| Low (managed) | Minutes | Low | Quick prototypes, standard web apps |
| **ECS / EKS** | High control | Hours | Medium | Microservices, containerized architectures |

Deploy on **Elastic Beanstalk** for simple web applications to minimize infrastructure management. Deploy on **ECS/EKS** for complex microservices architectures requiring container orchestration.

### 3. Key Takeaways
* Elastic Beanstalk is a PaaS service that provisions and manages infrastructure automatically based on your uploaded code.
* Use `.ebextensions` configuration files to customize environment parameters and scaling properties.
* Set your Spring Boot application port to 5000 to match Beanstalk's Nginx proxy configuration.

---

## TOPIC 28: AWS CLI — COMMAND LINE INTERFACE

### 1. Concept Explanation

#### Beginner
The AWS Command Line Interface (CLI) is an open-source tool that allows you to manage and automate AWS services directly from your terminal using commands, bypassing the AWS Console.

Configuring the CLI:
`aws configure`
This command prompts you to input your Access Key ID, Secret Access Key, Default Region (e.g. `ap-south-1`), and default output format (`json`).

#### Intermediate
##### Essential CLI Commands
* **EC2:**
```bash
# List all running EC2 instances
aws ec2 describe-instances --filters "Name=instance-state-name,Values=running"

# Start a stopped instance
aws ec2 start-instances --instance-ids i-0123456789abcdef0
```
* **S3:**
```bash
# Upload a file to a bucket
aws s3 cp document.pdf s3://company-reports-bucket/reports/

# Sync a local directory to a bucket
aws s3 sync ./build s3://static-assets-bucket/ --delete
```
* **IAM:**
```bash
# List all IAM users
aws iam list-users
```
* **RDS:**
```bash
# Create a manual database snapshot
aws rds create-db-snapshot --db-instance-identifier prod-db --db-snapshot-identifier prod-db-backup-2026
```

#### Advanced
##### Named Profiles
To manage multiple AWS accounts (e.g., development and production), configure **Named Profiles**:
```bash
# Configure profiles
aws configure --profile dev-account
aws configure --profile prod-account

# Execute commands using a specific profile
aws s3 ls --profile prod-account

# Or set the profile for the current terminal session
export AWS_PROFILE=prod-account
```

##### Output Querying (JMESPath)
Filter JSON output directly in the CLI using the `--query` parameter:
```bash
# Retrieve only the InstanceId, State, and Public IP of EC2 instances in a table format
aws ec2 describe-instances \
  --query 'Reservations[*].Instances[*].[InstanceId, State.Name, PublicIpAddress]' \
  --output table
```

### 2. Interview Questions & Answers

#### Q: How do you authorize the AWS CLI inside a CI/CD runner securely?
**A:** Avoid using permanent IAM User access keys in your CI/CD runner. Instead, configure **OIDC (OpenID Connect)** federation. The runner requests a temporary token from AWS IAM by assuming a designated role (e.g., `GitHubActionsWorkflowRole`) for the duration of the deployment step.

#### Q: What is the `--dry-run` flag in the AWS CLI?
**A:** The `--dry-run` flag checks whether you have the necessary permissions to execute a command without actually performing the action. It is useful for validating IAM policies before running potentially disruptive operations. If you have the required permissions, the command returns a `DryRunOperation` error.

### 3. Key Takeaways
* AWS CLI enables command-line management and scripting of AWS resources.
* Use Named Profiles to switch between different AWS accounts and environments.
* Use the `--query` parameter to parse and filter JSON outputs from AWS CLI commands.

---

## TOPIC 29: STATIC WEBSITE HOSTING ON EC2

### 1. Concept Explanation

#### Beginner
A static website consists of pre-built files (HTML, CSS, JavaScript, and images) served directly to the browser without server-side processing (no database connections or dynamic rendering). You can host a static website on an EC2 instance running a web server like Apache (`httpd`).

Core Web Servers:
* **httpd (Apache):** A widely-used web server.
* **Nginx:** A high-performance web server and reverse proxy.
* **Tomcat:** An application server used to execute Java Servlets and render Java Server Pages (JSP). It is not recommended for serving pure static content.

#### Intermediate
##### Hosting a Static Website on EC2 (Step-by-Step)
1. Launch an EC2 instance running Amazon Linux 2.
2. Configure the **Security Group** to allow inbound traffic on port 80 (HTTP) from anywhere (`0.0.0.0/0`).
3. SSH into the instance and install the web server:
```bash
sudo yum update -y
sudo yum install httpd -y
```
4. Start the Apache service and configure it to launch automatically on reboot:
```bash
sudo systemctl start httpd
sudo systemctl enable httpd
```
5. Deploy your static website files to the Apache document root directory (`/var/www/html`):
```bash
cd /var/www/html
echo "<html><body><h1>Welcome to My EC2 Hosted Website</h1></body></html>" | sudo tee index.html
```
6. Access the website in your browser using the EC2 instance's public IP address: `http://<EC2-Public-IP>`.

#### Advanced
##### Production Web Hosting Architectures
While you can host a static website on EC2, modern production architectures prefer serverless setups to optimize cost and performance:

| Hosting Option | Compute Management | Scalability | Cost Model |
| :--- | :--- | :--- | :--- |
| **EC2 + httpd** | Manual OS maintenance and patching | Requires configuring ALBs and ASGs | Billed hourly based on instance size |
| **Amazon S3 + CloudFront**| Serverless (no OS to maintain) | Auto-scales to handle global traffic | Billed per GB stored and transferred (very cheap) |

For static sites, hosting on **S3 and CloudFront** is the recommended best practice, providing global CDN caching, automatic SSL termination, and low storage costs. For dynamic Java applications, deploy on **EC2, ECS, or EKS**.

### 2. Interview Questions & Answers

#### Q: You deployed a website on EC2, but typing the public IP in a browser returns a connection timeout. What do you troubleshoot?
**A:** 
1. Check the EC2 instance's **Security Group** to verify that inbound traffic on TCP port 80 (HTTP) is allowed from `0.0.0.0/0`.
2. Verify that the web server service is running on the instance: `sudo systemctl status httpd`.
3. Check the VPC's **Network ACL (NACL)** to verify that inbound and outbound traffic on port 80 is not blocked.
4. Verify that you are accessing the IP using `http://` instead of `https://`, as SSL certificates (port 443) are not configured by default.

### 3. Key Takeaways
* Serve static website files from the default web root directory `/var/www/html` when using Apache (`httpd`).
* For production static websites, use S3 static hosting combined with CloudFront instead of hosting on EC2 to reduce costs and management overhead.
* Always check security group rules first if you experience connection timeouts when accessing public endpoints.

---

## TROUBLESHOOTING QUICK REFERENCE

| Problem | Likely Cause | Resolution |
| :--- | :--- | :--- |
| **HTTP 502 Bad Gateway** | The ALB cannot connect to the backend application, or the container health check is failing. | Inspect the application logs inside the EC2 instance or Kubernetes pod to diagnose startup crashes. |
| **HTTP 503 Service Unavailable**| The ALB target group has no healthy instances registered, or the database connection pool is exhausted. | Verify that instances are passing health checks, and check active database connection limits. |
| **HTTP 504 Gateway Timeout** | The backend application took too long to respond, indicating a slow database query or downstream API timeout. | Optimize slow-running database queries, add read replicas, or configure appropriate timeouts for external API calls. |
| **Pod stuck in `CrashLoopBackOff`**| The application crashed during startup (due to database connection failures, missing environment variables, or OOM errors). | Run `kubectl logs <pod-name> --previous` to inspect the logs of the crashed container. |
| **EC2 instance SSH Connection Timeout**| Inbound SSH traffic on port 22 is blocked by the security group or network ACL. | Update the security group rules to allow inbound SSH access from your IP address. |
| **S3 Upload returns 403 Forbidden** | The IAM role, policy, or bucket policy does not grant the required `s3:PutObject` permission. | Verify and update the IAM policies attached to the service role. |
| **Database connection refused** | The database security group does not allow traffic on port 3306 (MySQL) or 5432 (Postgres) from the application server. | Update the database security group to allow inbound traffic from the application server's security group. |
| **Lambda execution times out** | The function runtime exceeded the configured execution timeout, often due to slow queries or cold start delays. | Increase the timeout setting, implement RDS Proxy for connection pooling, or enable SnapStart to mitigate JVM cold starts. |
| **ASG fails to scale during traffic spikes**| The scaling policy is based on CPU utilization, while the application is blocked by memory or I/O bottlenecks. | Configure target tracking scaling policies using metrics like ALB request count or SQS queue depth. |
| **State file locked error in Terraform**| A previous Terraform execution crashed without releasing the lock on the DynamoDB state table. | Force-unlock the state using the command `terraform force-unlock <lock-id>` after verifying no other runs are active. |
| **Slow Maven builds in CI pipeline** | The pipeline runner downloads all dependencies from Maven Central on every execution. | Enable cache steps in your CI runner (e.g. `actions/cache` in GitHub Actions) to cache the local `.m2` repository. |
| **High monthly cloud bill** | Orphaned, idle, or over-provisioned resources are active in the account. | Use AWS Trusted Advisor and AWS Compute Optimizer to identify idle EBS volumes, unused Elastic IPs, and over-provisioned instances. |
