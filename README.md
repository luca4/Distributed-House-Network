# Intelligent Energy Management System for Residential Complex

This project implements an intelligent energy management system for a group of houses (residential complex) using a distributed network architecture. The system is composed of three main components:

### 1. **AdminServer**:
A gRPC server built using **Jersey**, responsible for managing the entire complex. It exposes endpoints that allow:
- Adding or removing houses from the network.
- Collecting energy consumption measurements.
- Providing various services to monitor the overall status of the complex.

### 2. **AdminClient**:
A terminal-based user interface (TUI) client that interacts with the AdminServer. It allows users to:
- View the current number of houses in the network.
- Retrieve the last `x` energy measurements for a specific house or the entire complex.
- Display statistical data on the complex's energy consumption.

### 3. **House**:
Represents a single house in the system, connected to other houses via sockets, forming a Peer-to-Peer (P2P) network. Each house:
- Periodically generates energy consumption data.
- Shares this data with the network. The network coordinator, which is the house with the highest ID, is responsible for sending the aggregated data to the AdminServer, ensuring data uniqueness and efficiency.


All the JSON exchanged between nodes has been serialized and deserialized using the Google **Gson** library.

#### **Energy Boost Management**:
A house can request a temporary energy boost for a short period. The **Ricart-Agrawala** algorithm is implemented in the P2P network to ensure that only one house can use the energy boost at a time, preventing conflicts and ensuring fairness.

### Development Context:
This project was developed as part of the "Distributed Systems" course at the University of Milan. As per educational requirements, concurrency is handled using low-level Java constructs like `wait` and `notify`. In a production environment, it would be advisable to utilize classes and methods from the **java.util.concurrent** package for better scalability and maintainability.

## Sequence Diagrams

Here's some sequence diagrams that explain interaction between network components:
- ### Compute consumption
![CalcoloConsumi](https://github.com/user-attachments/assets/7f980eb7-743d-4b34-b91f-1b56158c122c)

- ### Add house to the network
![IngressoCasa](https://github.com/user-attachments/assets/03a79f99-7d2f-42b9-93e6-4a82b8a459db)

- ### Boost request
![RichiestaBoost](https://github.com/user-attachments/assets/af4b59fc-d9f4-4617-b739-c122650c1b00)

- ### House's list request
![RichiestaElencoCase](https://github.com/user-attachments/assets/c572a22f-9784-481a-b933-48b7c7c9e8b7)

- ### Complex energy measures request
![RichiestaMisureCondominiali](https://github.com/user-attachments/assets/29677d4f-666f-4459-862b-2a07d77c9ea0)

- ### Remove house from network
![UscitaCasa](https://github.com/user-attachments/assets/386b268b-7e87-47e1-a4d9-534a4c5af9a9)
