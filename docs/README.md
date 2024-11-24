<p align="center">
    <img align='center' src='./minidash.png' width='50%' />
</p>

MiniDash is a simple version of [Geometry Dash](https://geometrygame.org/), implemented using Java's Lanterna. We follow SOLID principles and integrate several programming patterns such as State, Game Loop and Visitor.

## Description

We intend MiniDash to be a simplified version of the original game. The player advances from left to right and is allowed to jump. The game is composed of various elements the player interacts with:

- Blocks
    - platforms where the player can land
    - colliding with a block from the side causes the game to end
- Spikes
    - colliding with a spike causes the game to end
- Boosts
    - boosts make the player jump higher than normal
    - boosts force the player to jump, even without jump button action
- Double-jumpers
    - double-jumpers allow the player to jump mid-air
    - if the player doesn't jump, it falls through the double-jumper 


<img src='./mocks.png' />

## Implementation

The game will have two different states of operation: Menu and Level. Using the State pattern, we have implemented these two modes and the logic behind switching between them.

The MVC pattern is used to separate game data, logic and rendering.

Since we represent all of the game elements as a list of Collidables inside our Model, we faced the problem of drawing those elements without violating the Open-Closed principle. This occurred again when calculating collisions, which have different outcomes depending on the Collidable. To solve that problem, we implemented the Visitor pattern.
