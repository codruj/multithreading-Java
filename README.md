# Queue Management Simulation

## Overview
This project simulates a queue management system where multiple clients arrive and are served based on different scheduling strategies. The simulation tracks client wait times, service times, and queue performance, providing insights such as peak hour and average waiting time.

## Features
- **Graphical User Interface (GUI):** A Java Swing-based interface to configure and start the simulation.
- **Queue Management:** Clients are assigned to queues based on different strategies.
- **Scheduling Strategies:** Supports two selection policies:
  - **Shortest Queue:** Assigns the client to the queue with the fewest tasks.
  - **Shortest Time:** Assigns the client to the queue with the shortest waiting time.
- **Logging & Statistics:** Outputs logs of events, including peak hour and average waiting times.

## Technologies Used
- Java (Swing for GUI, Multithreading for server management)
- Data Structures: `BlockingQueue` for task management, `ArrayList` for client storage.
- Design Patterns: Strategy pattern for queue selection.

## Components

### 1. **Graphical User Interface**
- **View.java**
  - Provides a Swing-based GUI.
  - Allows user input for simulation parameters (number of clients, queues, time limits, etc.).
  - Displays real-time output of the queue states.

### 2. **Simulation Management**
- **SimulationManager.java**
  - Runs the main simulation loop.
  - Generates random clients with arrival and service times.
  - Dispatches tasks to the appropriate queues.
  - Logs events to `logofevents.txt`.

- **Scheduler.java**
  - Manages the list of available queues.
  - Handles task dispatching based on the selected strategy.

### 3. **Queue Strategies**
- **Strategy.java (Interface)**
  - Defines the contract for task assignment strategies.

- **ConcreteStrategyQueue.java**
  - Implements the "Shortest Queue" strategy.

- **ConcreteStrategyTime.java**
  - Implements the "Shortest Time" strategy.

- **SelectionPolicy.java (Enum)**
  - Defines the available selection policies.

### 4. **Task and Server Management**
- **Task.java**
  - Represents a client with attributes such as `ID`, `arrivalTime`, `serviceTime`, and `waitingTime`.

- **Server.java**
  - Represents a queue that processes tasks.
  - Uses a `BlockingQueue` to store tasks.
  - Implements `Runnable` for multithreading support.

## Log Files
- Each log records queue states over time and final statistics like peak hour and average waiting time.

## Execution
1. Run `SimulationManager.java` to start the application.
2. Configure parameters via the GUI.
3. Choose a scheduling strategy.
4. Start the simulation and observe real-time logs.

## Metrics & Results
- **Peak Hour:** The time when the highest number of clients are in the queues.
- **Average Waiting Time:** The mean time clients spend waiting in queues.
- **Average Service Time:** The mean time required to serve clients.

## Possible Enhancements
- Implement additional scheduling strategies (e.g., priority-based scheduling).
- Extend GUI to provide graphical visualization of queue dynamics.
- Optimize thread synchronization for better performance.

