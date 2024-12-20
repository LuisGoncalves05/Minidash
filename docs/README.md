<p align="center">
    <img align='center' src='./minidash.png' width='50%' />
</p>

## LDTS_t10g01 - Minidash

MiniDash is a simple version of [Geometry Dash](https://geometrygame.org/), implemented using Java's Lanterna. We follow SOLID principles and integrate several programming patterns such as State, Game Loop, and Visitor.

This project was developed by *Luís Barbosa* (up202303872), *Luís Gonçalves* (upXXXXXXXXX), and *Sofia Sousa* (up202303662) for LDTS 2024/25.

### IMPLEMENTED FEATURES

- **Blocks & Platforms** - Game elements the player uses to move and jump. Colliding with a block sideways kills the player;
- **Spikes** - Colliding with a spike kills the player;
- **Boosts** - Colliding with a bost makes the player jump higher and further than normal. The player is forced to jump upon collision, even without jump-button action;
- **Double jumpers** - Double jumps allow the player to jump mid-air if there is jump-button action. Otherwise, the player falls through them;
- **Physics** - The above features require realistic physics for jumping and collision detection;
- **Player rotation** - This required some non-trivial trigonometry and clever thinking;
- **Menus** - Allow the user to select a level or quit the game;
- **Music** - There is one track for each level, as per the original game.

<p align="center">
    <img align='center' src='./minidash1.png' width='50%' />
</p>
<p align="center">
    <img align='center' src='./minidash2.png' width='50%' />
</p>
<p align="center">
    <img align='center' src='./minidash3.png' width='50%' />
</p>

### PLANNED FEATURES

We'd planned these additional features, that end up not being implemented due to time restraints:
- Portals
- Gravity Inversion
- Color theme changing

### DESIGN

#### DRAWING AND INTERATING WITH GAME ELEMENTS

**Problem in Context**

Minidash follows an MVC structure. When we first created the LevelView class, we intended to check the type of each Element and render it accordingly. This, however, violated the Open-Closed Principle, since adding more game elements would require modifying the `draw()` method in LevelView. The same problem emerged again while creating the LevelController and the menu logic.

**The Pattern**

To solve this, the **Visitor** pattern was incorporated. This pattern consists of a **visitor** class accessing a generic **element** through its **accept** method. Each of the subclasses of the element is responsible for choosing their appropriate handler in the visitor. For each new element, another method needs to be added in the visitor, but none of the existing ones are changed.

**Implementation**

The following figure shows how the pattern’s roles were mapped to the application classes.

![img](https://www.fe.up.pt/~arestivo/page/img/examples/lpoo/state.svg)

These classes can be found in the following files (only a few are provided for brevity, all the others follow a similar structure):

- [Element](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/model/Element.java)
- [Block](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/model/Block.java)
- [ElementVisitor](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/model/ElementVisitor.java)
- [LevelView](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/view/LevelView.java)
- [LevelController](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/controller/LevelController.java)

**Consequences**

The use of the Visitor Pattern in the current design allows:
- A clear separation between view, model, and controller;
- Following the Open-Closed Principle.

However, since new interfaces were added, and the visitor is not perceived at first glance, using this pattern makes it slightly more difficult to track the elements' rendering and collision-checking. The whole pattern can be easily grasped if supported by a UML diagram.

#### CREATING MENUS

**Problem in Context**

Menus were implemented incrementally in Minidash. Firstly, only the main menu existed, implemented in a MenuModel class with a list of available options. As we added more menus, different menu models with their own sets of options were needed. We modified the constructor to require a list of available options. However, classes that had nothing whatsoever to do with menus now had to decide which options to make available in a menu.

**The Pattern**

We chose to apply the **Factory Method**, where an abstract class implements most of the logic but delegates the creation of one or more objects to its subclasses.

**Implementation**

We transformed the existing **MenuState** into an abstract class, containing the abstract method **createModel**, and created three other classes to extend it: **MainMenuState**, **LevelMenuState**, and **LevelCompleteState**.

![img](https://www.fe.up.pt/~arestivo/page/img/examples/lpoo/state.svg)

These classes can be found in the following files:

- [MenuState](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/state/MenuState.java)
- [MainMenuState](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/state/MainMenuState.java)
- [LevelMenuState](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/state/LevelMenuState.java)
- [LevelCompleteState](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/state/LevelCompleteState.java)

**Consequences**

Using the Factory Method in the current design means that there is:
- Better separation between different classes: the LevelController doesn't have to decide which options a menu should have - it suffices to choose a menu type and call its empty constructor;
- Some additional complexity, far outweighed by the benefits.

#### I/O

**Problem in Context**

Minidash uses Lanterna for its I/O operations. Lanterna has a rather complicated interface with more complexity and funcionality than what was needed for Minidash. Besides adding unnecessary I/O-related clutter where it didn't belong, it forced us to repeat code in many places and to tightly couple our implementation with Lanterna's.

**The Pattern**

We solved this problem with the **Facade Pattern**. This pattern requires creating a new class that provides only the features needed in another more complex class and abstracts away the repeated code.

**Implementation**

While the original implementation of this pattern relied on a single interface, **IOAdapter**, which was implemented by the **LanternaIOAdapter** class, we latter realized that this violated the Interface Segregation Principle. As such, we decided to create two new interfaces, **InputAdapter** and **OutputAdapter**, which are both implemented by LanternaIOAdapter:

![img](https://www.fe.up.pt/~arestivo/page/img/examples/lpoo/state.svg)

These classes can be found in the following files:

- [InputAdapter](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/ioadapter/InputAdapter.java)
- [OutputAdapter](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/ioadapter/OutputAdapter.java)
- [LanternaIOAdapter](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/ioadapter/LanternaIOAdapter.java)

**Consequences**

Using the Facade Pattern resulted in:

- A simplified interface for I/O.
- Less repeated code
- Not violating the Interface Segregation Principle

#### LEVELS AND MENUS

**Problem in Context**

Minidash has a menu mode and a level mode. As the game becomes more complex, it's likely that other modes will be added. This raises the problem of managing mode switching and the interactions between different modes.

**The Pattern**

We have applied the **State Pattern**. This pattern enables the same object to act differently at distinct moments (i.e., it has different **states**) and provides a way to manage interactions between them. A class **State** defines a public interface which should be followed by all of its subclasses.

**Implementation**

We defined a **State** class which is subclassed by **MenuState** and **LevelState**.

![img](https://www.fe.up.pt/~arestivo/page/img/examples/lpoo/state.svg)

These classes can be found in the following files:

- [State](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/state/State.java)
- [MenuState](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/state/MenuState.java)
- [LevelState](https://github.com/FEUP-LDTS-2024/project-t10g01/blob/master/src/main/java/com/t10g01/minidash/state/LevelState.java)

**Consequences**

The use of the Factory Method in the current design has the following consequences:

- Better separation between different classes: the LevelController doesn't have to decide which options a menu should have - it suffices to choose a menu type and call its empty constructor.
- Some additional complexity, far outweighed by the benefits.

### TESTING

- Screenshot of coverage report.
- Link to mutation testing report.

### SELF-EVALUATION

- Luís Barbosa: 33%
- Luís Gonçalves: 33%
- Sofia Sousa: 33%
- Rubber Duck: 1%
