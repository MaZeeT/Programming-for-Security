# Programming for security 
This is my submission of the "Programming for security" course exam project from RUC (Roskilde University).
A description of the course can be found in CourseDescription.pdf

The submission implement a simple chat service, where the clients have end-to-end encryption based on a shared password. The server that relays the message will not be able to decrypt the messages since only the clients with the corrected password will be able to do so. Implemented in Java with Bouncy Castle to facilitate the security functions.