# DAI-2022-SMTP
## Description
The mission is to develop a client application that automatically plays pranks on a list of victims:

* The user should be able to **define a list of victims** (concretely,
you should be able to create a file containing a list of e-mail addresses).
* The user should be able to **define how many groups of victims should
be formed** in a given campaign. In every group of victims, there should
be 1 sender and at least 2 recipients (i.e. the minimum size for a group is 3).
* The user should be able to **define a list of e-mail messages**. When a prank
is played on a group of victims, then one of these messages should be selected.
**The mail should be sent to all group recipients, from the address of the group
sender**. In other words, the recipient victims should be lead to believe that the
sender victim has sent them.

## Project structure
    .
    ├── MockSMTPServer          # SMTP Server with all docker files
    ├── resources               # Configuration files for the project
    │   ├── config.properties   # Configuration of the SMTP server and the
    │   │                         the number of pranked groups 
    │   ├── email.utf8          # List of all emails
    │   └── messaages.utf8      # List of possible messages
    ├── src                     # Source files of prank project
    ├── pom.xml                 # Maven configuration file
    └── README.md
## MockMock 
_"MockMock is a cross-platform SMTP server built on Java. It allows you to test if
outgoing emails are sent (without actually sending them) and to see what they look
like. It provides a web interface that displays which emails were sent and shows you
what the contents of those emails are. If you use MockMock you can be sure that your
outgoing emails will not reach customers or users by accident. It really just is a
mock SMTP server and has no email sending functionality."_

The git link to the MockMock project is [here](https://github.com/tweakers/MockMock)

### start mockmock with docker
**_to complet_**
### start mockmock without docker
**_to complet_**
## How to start and configure the prank project
In the resources folder at the root of the repository, multiple files are used to
configure the project. The 3 files must be in the resources folder for it to be able to run.

### start the prank project
**_to complet_**
### config.properties
The config.properties file is used to configure how to contact the SMTP
server and specify the number of group. The number of group must be greater the 0.

Default config (and example to edit):

    smtpServerAddress=localhost
    smtpServerPort=25
    numberGroups=1
### email.utf8
The email.utf8 file is used to list all emails separated by **\n** or **\r\n**.
The minimum number of emails in the file must be >= 3 * numberGroups in the config.properties
file. The minimum of 3 emails per group comes from 1 for the sender and all the other 
for the receivers (min. 2).

Example of 3 emails in the file:

    rupak@sbcglobal.net
    ideguy@hotmail.com
    atmarks@yahoo.com
### messages.utf8
The messages.utf8 file is used to get a different mail body and subject for all groups.
The format of the message in the message.uft8 file:
    
    first line  # subject of the email
    all         # content of the email
    the 
    folowing 
    ///         # end the mail with 3 slash

Example of one message in the file:

    test1
    Now is the winter of our discontent
    Made glorious summer by this sun of York;
    And all the clouds that lour'd upon our house
    In the deep bosom of the ocean buried.
    Now are our brows bound with victorious wreaths;
    Our bruised arms hung up for monuments;
    Our stern alarums changed to merry meetings,
    Our dreadful marches to delightful measures.
    ///

## Description of the implementation
**_to complet_**