PROJECT TITLE:
Rental Checker App

TEAM MEMBERS:
- Francis Neo Achilles S. Simeon - Neodymium2006
- Alex James W. Chan - alexchan00

PROBLEM STATEMENT & GOALS:
- Easy managment of the admin user of the app.
- To check the availability of the building space.

TARGET USER:
- Admin
- Building manager
- Renter

BRIEF DESCRIPTION:

The purpose of this project is to make a software application where admins or owners of the business can oversee the status of his or her business with
ease. It is also to keep track of who are the managers of the buildings and the renters. In addition, it is also to check the availability of the building space.
With just a click of the button, the manager and the renter can contact each other through the app to make lives convenient. It is also convenient for renters
to book a unit of a specific building.

CORE OOP CONCEPTS:
- Encapsulation:
- Where: In the User, Building, and Unit classes by hiding their internal data states.
- How: These attributes will be set to private and can only be accessed or modified through public getter and setter methods, like bookUnit() or getUnitDetails().
  
- Inheritance:
- Where: In the creation of the user hierarchy to avoid code duplication.
- How: We will create a base/parent class called User which contains common attributes (name, password, userType) and methods (login(), contact()). The specific roles—Admin, BuildingManager, and Renter will be child classes that inherit from User. Each child class will then add its own specific attributes.
  
- Polymorphism:
- Where: In the user interface behaviors and the contact functionalities across different user types.
- How: We will define a common method in the User class, such as viewDashboard() or getAvailableContacts().
- When viewDashboard() is called, it behaves differently depending on the object: an Admin sees the status of all 3 buildings; a Manager sees specific details of their assigned building and limited views of others; a Renter sees the 4 buttons:Building A, B, C, Current Unit. Then add a contact() for the Renter to contact with the Maneger.

  
- Abstraction:
- Where: In the user interface's "Contact" system and the backend property management structure, a generalized Space or Property concept.
- How: When a Renter or Manager clicks the "Contact" button on the GUI to send a message, or clicks "Book Unit", they are only interacting with a simple interface. The complex background processes, like validating user permissions, routing the message to the correct building manager, or updating database availability.

INITIAL CLASS IDEAS:
- Neo: Create the user gui, the admin's interface, the three buildings and their respective units. Assign also building managers for the app.
- Alex: Develop the user interfaces and specific functionalities for the Building Managers and Renters, and implement the unified cross-role messaging system.

USER STORIES (Recommended):
- As a future businessman, I want to create an application for business owners that oversees the whole business so that he or she can easily track the details
of his or her employee (building managers) and renters.
- As a Renter, I want to find a way so that I can found a vacant place of resident for rent easier in one app.

CORE FEATURES (Recommended):
- The app's general user interface (GUI) will let the user input his or her user type, name, and password.
- For the admin's perspective (if the user is an ADMIN):
  1.   The admin can oversee the status of the three buildings. He or she can also see the details of the dimensions of the units per building. Each building has three floors and each floor has two units.
  2. The admin can see the details of the manager for each building the renters. In addition, the admin's view can also see the number of units available and unavailable.
  3. The admin's interface also has the functionality to contact the manager of each building.

- For the building manager's perspective:
  1. The manager can see the available units and unavailable units in their respective buildings. For example, admin instructed manager A to handle building A, in for building manager A's perspective, he or she can only see the available and unavailable units for building A. This also adds the contect functionality for
building A's renters. This principle also applies for manager B and manager C for building's B and C respectively.
  2. The manager can also see the details of their renters details such as name, profession etc.
  3. If the manager is from building A, he or she can see the units for other buildings as well. However, he or she CANNOT SEE the details of the renters not
  from his or her building.
  4. The three building mangers have the function to contact each other through the contact button.

- For the renter's perspective:
  1. The interface of the renter has four (4) buttons, building A, B, C, and current unit. If will book, the next page upon clicking the "current unit" button is empty.
  2. The renter can choose a unit among the three buildings depending on the availability of the unit.
  3. If the renter already has booked a unit, the renter will now unlock the purpose of the "current unit" button. Upon clicking, the renter can see the details of his or her unit - such as size, price etc.
  4. The renter can also contact the building manager depending on where the renter booked. For example, if renter booked a unit for building A, the renter can ONLY contact the manager for building A. The same applies for renters in building's B and C.
