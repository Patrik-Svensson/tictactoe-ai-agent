# AI Agent for Tic-Tac-Toe

This repository contains a Tic-Tac-Toe game implemented in Java, utilizing the **Minimax algorithm** to create an AI opponent. 

## Features
- Play Tic-Tac-Toe against an AI opponent powered by the Minimax algorithm.
- Two types of Tic-Tac-Toe:
  - 2D Tic-Tac-Toe
  - 3D Tic-Tac-Toe 
- Two game modes:
  - **Player vs AI**
  - **AI vs AI**
- Command-line interface for simplicity.
- Well-structured and documented Java code.

## Compile
```bash
javac *.java
```

## Run
The players use standard input and output to communicate. The moves made are shown as unicode-art on standard error if the parameter `verbose` is given.

### Play against self in the same terminal
```bash
mkfifo pipe
java Main init verbose < pipe | java Main > pipe
```

### Play against self in two different terminals
**Terminal 1:**
```bash
mkfifo pipe1 pipe2
java Main init verbose < pipe1 > pipe2
```

**Terminal 2:**
```bash
java Main verbose > pipe1 < pipe2
```

### Play against your friend (in the same terminal)
If you want two algorithms to play against each other, create two folders, `folder1` and `folder2`. Copy the skeleton to both of these folders and compile the code. Then run:
```bash
mkfifo pipe
java -cp folder1 Main init verbose < pipe | java -cp folder2 Main > pipe
```

## Future Improvements
- Implement a graphical user interface (GUI).

## Contributing
Contributions are welcome! Feel free to open issues and submit pull requests.

---

Enjoy coding and good luck beating the AI! 😃

