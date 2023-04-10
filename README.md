# My Personal Project

## Card Collector

This application will be designed for people with an interest in collecting and keeping track of their trading cards.
Personally, I collect cards for the sake of using them in games with my friend's but the program will be more general.
For the sake of sticking to what I'm more knowledgeable about, I'll loosely base the cards off of *Magic: The Gathering*
since this is what interests me. I enjoy playing card games like *MTG* and as a result, I find this project would be
helpful and interesting to me. **The application will allow users to enter cards into collections as they collect them,
and sort them into decks at any point to help with deck planning. It should help the user with finding their desired
cards and make sure their deck is standard according the ruleset they're currently applying to the deck. A collection
should be all the cards owned by the user, whereas a deck will be a certain subset of those cards of arbitrary size
that fits selectable restrictions. (Most rulesets have limits on the card count in a deck, but I'll add one with no
additional rules to allow for an arbitrary deck size with no restrictions)**


User stories:
- As a user, I want to be able to sort my collection for the card I want by name
- As a user, I want to be view my current Decks
- As a user, I want to be able to add/remove cards from a deck and/or a collection
- As a user, I want to see which cards in my collection are the most used in my decks and which are the least used
- As a user, I want to mark/edit a preexisting card with its current condition or location
- As a user, I want a mechanism to ensure my deck follows my chosen ruleset

- As a user, I want to have the option to save the entire state of the application to file
- As a user, I want the option to reload that state from file and resume exactly where I left off at some earlier time

# Instructions for Grader

- You can reload the state of my application by clicking the load button at the bottom of the viewport.
- You can save the state of my application by clicking the save button at the bottom of the viewport.
- You can locate my visual component by clicking on a card and looking at its details. The visual component is
  the two squares displayed next to the name. They change color based on rarity and condition respectively.
- You can generate the first required action related to adding Xs to a Y by opening a deck and clicking either
  of the sort buttons (labelled A->Z and Z->A respectively). This sorts the deck by name.
- You can generate the second required action related to adding Xs to a Y by opening a deck and clicking on a
  card within (From the upper right). Then click the remove card from deck button. It should remove the card from
  the deck.

## For more clear instructions on navigating the UI, here's an example you could follow to get a feel for it

- First, you'll want to load the data stored in file by clicking the large "Load" button at the bottom of the frame
- Next, click on the deck named "Beans" and then the card labelled as "Big Bean" in the upper right corner of the
  viewport (not to be confused with the bottom middle instance).
- This should display information and actions in the bottom right corner of the view port relating to the card,
  including two visual indicators for the condition and rarity. Scrolling through the other cards in the deck,
  you can see how they change colors between cards. The first shows rarity and will be one of four colors. The second
  shows condition and will appear as some color between pink and black with pink representing better condition.
- Now, with the big bean selected once more (or any other card, but for the sake of detail), you can now click the
  "remove from deck" button and watch as it disappears.
- Now, save the deck in this state.
- Click on Python in the bottom middle section of the page without deselecting the deck.
- In the bottom right corner again, click the "add to deck" button and watch as it appears. Now, if you click the
  load button once more, it should remove the python card from the deck.
- This functionality can be verified by closing and reopening the app before loading once more.
- Now, open the snake deck by clicking on the button in the upper middle labelled as snake.
- Try out both of the sorting buttons by clicking either and watching as the deck reorients itself (or not,
  if it's already sorted)
- If you want to create a new deck or card to test something, simply fill in all the fields in the specified 
  menu and click the button. Deck creator wants a string. Card creator wants an arbitrary string for name, one of
  "Common", "Uncommon", "Rare", "Mythic" for rarity, a double between 0 and 10 for condition, a positive integer or 0
  mana cost, and another arbitrary string for card type. (None of which are case-sensitive).

# Phase 4: Task 2

- Mon Apr 10 15:09:24 PDT 2023
- Testdeck was created
- Mon Apr 10 15:09:53 PDT 2023
- Testcard was created
- Mon Apr 10 15:09:53 PDT 2023
- Added Testcard to all
- Mon Apr 10 15:09:58 PDT 2023
- Added Testcard to Testdeck
- Mon Apr 10 15:10:00 PDT 2023
- Testdeck was sorted according to name
- Mon Apr 10 15:10:03 PDT 2023
- Removed Testcard from Testdeck

# Phase 4: Task 3

After seeing the design reflected in my UML diagram, I believe it would have been worthwhile to spend my time
adding in more abstract classes and interfaces to save time on the repetitive GUI elements since it has become
more apparent to me just how much those classes truly had in common in terms of their functionality.
More importantly though, I think it would have been better for me to work those classes into the CardAppGraphical
class itself. It was a poor design decision on my part to have them all need to inherit the class that should supposedly
have been parenting them. It definitely works, but it makes the references a lot harder to follow than was necessary.
If I had more time to work on the project I would focus on reworking the definition of the CardCollection to eliminate
duplicate instances of the list of decks and cards, and I would also refactor the GUI classes into the CardAppGraphical
class as there is not a real valid reason I can think of to have placed them outside in that way. If I still had extra
time I would then rework CardAppGraphical to a singleton design since I know there should never be extra instances and I
believe it would improve the code's legibility.