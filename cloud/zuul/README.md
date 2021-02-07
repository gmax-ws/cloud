# **Zuul - Service Gateway** #

* This project is a Services Gateway Server with load balancing capabilities.
* The implementation is based on **Netflix Zuul** reverse proxy server and **Netflix Ribbon** for load balancing. 
* Because the service gateway is the interface between the public, and the private network using a secured protocol is a **MUST**.
Just for **POC** a self signed certificate is enough but in production a certificate signed by a trusted authority is also a **MUST**.
* Sensitive information like passwords are symmetrical encrypted. 
