# Data-Structures-and-Algorithms_programming-assignment
This project provides a comprehensive management system for a football tournament between various faculties at Technion. The project is implemented in Java and includes several classes for managing faculties, players, matches, and results.

### Features
#### Faculties Management:
Creation & Removal: Faculties can be created or removed, including transitioning their players into free agents.
Players Management: Players can be added to faculties, removed, and their goals tracked.
#### Match Handling:
Record Results: Matches between faculties can be recorded, including updating their scores and goals scored by players.
#### Queries:
Retrieve Data: Retrieve faculties and players sorted by various metrics, including points, goals, and more.
### Classes and Their Functions
TechnionTournament: Main class managing faculties, players, and match results.
Faculty: Represents a faculty, with methods to manage its attributes and players.
Player: Represents a player, including their goals scored and faculty affiliation.
#### Tree Structures:
FacultiesTreeById & FacultiesTreeByScore: Trees storing faculties by ID and score, respectively.
PlayersTreeById & PlayersTreeByGoals: Trees storing players by ID and goals scored, respectively.
FacultyNodeById & FacultyNodeByScore: Nodes for faculties by ID and score.
PlayerNodeById & PlayerNodeByGoals: Nodes for players by ID and goals.
Main: Provides a test for the functionalities, including how the program functions in real-world scenarios.
