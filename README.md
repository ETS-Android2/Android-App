# Service Novigrad
![Image of Service Novigrad logo](https://github.com/SEG2105-uottawa/seg2105f20-project-project_gr-4/blob/master/assets/logo_lightmode.png?raw=true)

**Repository Link:**\
https://github.com/SEG2105-uottawa/seg2105f20-project-project_gr-4/ \
https://github.com/SEG2105-uottawa/seg2105f20-project-project_gr-4.git


## :clipboard: Description
The app has three different types of users in mind: the administrator, the Service Novigrad branch employee, and the customers. The administrator manages all the possible services that can be offered to customers by different Service Novigrad branches. The branch employee creates a profile for the branch and selects the services offered by that branch, in addition to the working hours. The customers are able to search for a Service Novigrad branchby address/type of service provided/working hours. The features that are available to each type of user are given below.
> **Note:** These are the minimum required features, and you are free to add more features you think might enrich your application.

## :arrow_double_down: Installation


## :phone: Usage
### Registration
Create an account by selecting the 'Create Account' button.
Toggle the switch in the direction of the account you wish to create - employee or customer - and enter the input fields as required. 


![Image of UI](https://github.com/SEG2105-uottawa/seg2105f20-project-project_gr-4/blob/master/assets/loggingInNovigrad.gif?raw=true)

### Sign-In
Sign-in with email and password.

**Admin Credentials**\
Username: admin\
Password: admin

### Authentication
#### Registration
First & Last name:
- Uppercase letters.
- Lowercase letters
- Can only have one hyphen.

Email:
- Uppercase letters.
- Lowercase letters
- Can contain any of the following special characters: (?=._!#$%&'*+=?`{|}~^.-).
- Must contain exactly one instance of "@". 

Password:
- A digit must occur at least once
- A lower case letter must occur at least once.
- An uppercase letter must occur at least once.
- A special character must occur at least once: (?=.*[@#$%^&!+=]).
- No whitespace allowed in the entire string.
- At least 6 characters.

#### Sign-In
Email & Password:
- Firebase authentication.

#### Dummy Request Testing Account
Email: employee.employee.ca
Password: @Novi2Grad

## :bug: Support
Compatible with Android OS 25+.

CircleCI Badge:\
[![CircleCI](https://circleci.com/gh/SEG2105-uottawa/seg2105f20-project-project_gr-4.svg?style=svg&circle-token=b64096758b45f12f18e1e0b1209a978419d17c30)](https://app.circleci.com/pipelines/github/SEG2105-uottawa)

## :clap: Authors and acknowledgement
:dolphin: Vivienne Cruz\
:penguin: Dharitri Dixit\
:tropical_fish: Vivianne Yee\
:turtle: Rachel Jamer\
:octopus: Billy Bolton

## :construction: Project Status
![Image of UML Diagram](https://github.com/SEG2105-uottawa/seg2105f20-project-project_gr-4/blob/master/assets/UML_Dec5_2020.png)



### :clock11: Deliverable Due Dates:
|Deliverable Description                        |Due Date                 |Status                          |
|-----------------------------------------------|-------------------------|--------------------------------|
|1) Github repository and user accounts(3%)     |October 7th              |`Complete`                      |
|2) Admin functionaity (3%)                     |November 1st             |`Complete`                      |
|3) Service Novigrad user functionality (3%)    |November 22nd            |`Complete`                      |
|4) Customer and application functionality (9%) |December 6th             |`Complete`                      |
|5) Dragon's Den Demo (2%)                      |TBA: Last week of classes|`Complete`                      |

## :car: Roadmap
- Additional email authentication using two-factor email authentications.

## :information_source: References
### Regex for Input Authentication
- https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
- http://www.java2s.com/Tutorial/Java/0120__Development/Validatethefirstnameandlastname.htm
- https://howtodoinjava.com/java/regex/java-regex-validate-email-address/
### Firebase
- https://firebase.google.com
