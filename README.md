# EDCrypt
Very bad example of symmetric key cryptography with not much security. An ascii algorithm in a utf8 world. 

Compiled and ran with OpenJDK 21.

The methods/algorithms could be implemented in other ways, or modified to work on certain blocks of Unicode. This only really works in the code block that is the same as ASCII, and it is otherwise pretty much broken. For example, using 😀 in your message.txt will have unintended or intended results. Having tabs in your message.txt may have unexpected results. Using CRLF (Windows) line breaks can be funny, or CR (old Macintosh) can appear disastrous. Tabs can result in other control characters to be displayed and printed in the "encrypted" text, or break the thing by becoming a CR or LF. 
