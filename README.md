# EDCrypt
This is a Java program that is very much probably the least cryptographically secure example of symmetric key cryptography, in theory.  It uses a deterministic random. 

It compiles and runs using the OpenJDK sdk/jre, but originally I wrote and compiled this probably in spring of 2012 or Fall/Winter 2011 using the java sdk that was current at the time with Eclipse.

The methods/algorithms could be implemented in other ways, or modified to work on certain blocks of Unicode. This only really works in the code block that is the same as ASCII, and it is otherwise broken. For example, using 😀 in your message.txt will have unintended or intended results. 

Other things that could be done to see what happens:
*Modifying the algorithm to swap carriage returns with line feeds (currently they are left unchanged)
*Modifying the algorithm to include control characters, or add a few lines to recognize control characters in a separate file before proceeding with the method that secures the output from including them (what happens when if prints to terminal? What happens when you open the text file?)
*Making it asymetric while maintaining insecurity
*import java.security.SecureRandom
*have fun
