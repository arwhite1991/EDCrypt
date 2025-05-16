/*EDcrypt.java
 * This program encrypts and decrypts messages stored in the specified files by using a pseudo random
 * number generator in the encryption and decryption process. A string is read with the Scanner
 * tool and this string is turned into a character array and each character is individually
 * encrypted. Because the same list of numbers is generated for the decryption proceess
 * it is possible to reverse the encryption using simple algebra.The encrypt() and decrypt() methods
 * both write the encrypted/decrypted message onto a new text file.  To start, create a plain text file named message.text
 * in the directory you run the java file from and type something to be encrypted.
 *
 */

 import java.lang.Math.*;
 import java.util.*;
 import java.io.*;

 //in this class message.txt is the unencrypted message, messageEnc.txt is the encrypted, and messageDec.txt is decrypted
 public class EDCrypt
 {
	public static final Random r_ = new Random();
	public static final long seed_ = r_.nextLong(); //generates the seed number for the random number generator used for encryption/decryption

	public static final String sSeed_ = "EDSeed.txt";
	public static final String message_ = "message.txt";
	public static final String messageEnc_ = "messageEnc.txt";
	public static final String messageDec_ = "messageDec.txt"; // keeping the file names consistant (unencrypted, encrypted, decrypted)

	//**IN THIS PROGRAM: variables labeled s or with an s prefix are input variables, labeled t or t prefix are output variables ( s -> t)**

	//main method
	public static void main(String[] args) throws FileNotFoundException, IOException{
         System.out.println("The seed for this number generator is(" + sSeed_ + "): " + seed_); //important to know seed of your psudo random number generator
        System.out.println("\nInitial/Unencrypted message is stored in (" + message_ + ")");
        System.out.println("Encrypted message is then stored in (" + messageEnc_ + ")");
        System.out.println("Decrypted message is then stored in (" + messageDec_+ ") \n");

        encrypt(getString(message_), seedRandom(seed_)); //encrypts message.txt with new random num gen from seedRandom()
        decrypt(getString(messageEnc_), seedRandom(seed_)); //decrypts messageEnc.txt with new random num gen from seedRandom()

        }

    /*  I used this method for aid in troubleshooting
    *
    public static void listDir(){
        String dirProp = System.getProperty("user.dir");
        System.out.println(dirProp + "\n");
        File dir = new File(dirProp);
        String[] files = dir.list();
        for(int j = 0; j < files.length; j++){
			System.out.println(files[j]);
        System.out.println();
    }
		*/
	//encrypts string s with specified number generator by individually encrypting each character
	public static String encrypt(String s, Random r)throws IOException{

		System.out.println(" ===========\n |ENCRYPTION|\n ===========");

		char[] sArray = new char[s.length()]; //sArray will be the unencrypted character array
		char[] tArray = new char[s.length()];//tArray will be the encrypted character array (same length as unencrypted array)
		sArray = s.toCharArray(); //to break the string down in to individual characters

		int i, j;
		for(i = 0; i < sArray.length; i++){ //loops until all characters in array are encrypted

					if ((int)sArray[i] == 13 || (int)sArray[i] == 10) //special case for linebreaks (they don't change)
						tArray[i] = sArray[i];
					else{
						j = getRandom(r); //the random number to be added to the character value at current spot in array
						tArray[i] = encryptChar(sArray[i],j); //tArray[i] is now the encrypted character
							}
			}


		String t = new String(tArray); //creates a new string using values from tArray
		putString(t, messageEnc_); //writes encrypted message to messageEnc.txt
		t = getString(messageEnc_); //getting the String value that was written to the file messageEnc.txt

		//next five lines for monitoring and double checking program is functional
		System.out.println("Length of unencrypted message: " + s.length() );
		System.out.println("Unencrypted message (" + message_ + "):\n\n" + s);
		System.out.println("\nLength of encrypted message: " + t.length());
		System.out.println("Encrypted message (" + messageEnc_ + "):\n");
		System.out.println(t + "\n");

		return t;
		}

	//very similar to the encrypt() method, uses decryptChar() which is the inverse of encryptChar()
	public static String decrypt(String s, Random r)throws IOException{

		System.out.println(" ==========\n |DECRYPTION|\n ==========");

		char[] sArray = new char[s.length()]; //encrypted character array
		char [] tArray = new char[s.length()]; //decrypted character array
		sArray = s.toCharArray();

		int i, j;
		for(i = 0; i < sArray.length; i++){
					if((int)sArray[i] == 13 || (int)sArray[i] == 10) //no need to decrypt these, their values did not change
						tArray[i] = sArray[i];
					else{
					j = getRandom(r); //generates the same #s generated in encrypt() method
					tArray[i] = decryptChar(sArray[i], j);
					}
			}

		String t = new String(tArray); //creates a new string using values from tArray
		putString(t, messageDec_); //writes decrypted message to messageDec.txt
		t = getString(messageDec_); //gets the string that was just written to the file

		//next five lines for monitoring accuracy
		System.out.println("Length of encrypted message: " + s.length() );
		System.out.println("Encrypted message (" + messageEnc_ +"):\n\n" + s);
		System.out.println("\nLength of decrypted message: " + t.length());
		System.out.println("Decrypted message (" + messageDec_ + "):\n");
		System.out.println(t + "\n");

		return t;
		}

	public static char encryptChar(char c, int v){ //v is the sudo random # used for the encryption

		int cv = (int)(c + v); //cv is the value of the character after the addition of a random number v

		//Algorithm: 128 = 32 [128 - 96], 129 = 33, etc... to keep the character values between 32 and 127
		if(cv > 127)
			return (char)(cv - 96); //if cv is greater than 27 we need to subtract 96 to keep it in the range of 32 and 127
		else
			return (char)cv; //cv <= 127
		}

	//inverted process of encryptChar
	public static char decryptChar(char c, int v){

		int cv = (int)(c - v); //when decrypting we subtract what was added during encryption

		//31 = 127, 30 = 126, 29 = 125, etc...
		if(cv < 32)
			return (char)(cv + 96);
		else
			return (char)cv;

	}

	//fileName is a plain text file, such as message.txt or messageEnc.txt , getString takes a text file and returns it as a string
	public static String getString(String fileName) throws FileNotFoundException, IOException{

		File inputFile = new File(fileName); //location of the file containing the String(s)

		/* **THIS IS UNNECCESSARY CODE USED FOR TROUBLESHOOTING**
		 *
		if (inputFile.exists())
			System.out.println(fName + " exists! \n");
		else
			System.out.println(" does not exist...\n");
		*/

		Scanner inScan = new Scanner(inputFile); //this Scanner reads from the file inputFile (ex: messageEnc.txt)
		inScan.useDelimiter("line.separator");  //using line separators for delimiter
		String t = ""; //t must have some value before adding to it

		while ( inScan.hasNext() ) { //will go on as long as there are more lines to be read
			t +=  inScan.next(); //next string/line to be read gets appended to String t
		}

		return t;
	}

	//writes a string (message) to a file(fileName) example:
	// 	putString("blah", "blah.txt") creates or overwrites file blah.txt and consists only of "blah"
	public static void putString(String message, String fileName)throws IOException{
		PrintWriter out = new PrintWriter(fileName);
		out.print(message);
		out.close();
	}

	/*
	 *Using seed_ for the input paramater will always yield the same list of random numbers (seed_ was defined earlier)
	 * However, seed_ does not need to be used incase user needs to encrypt/decrypt using a past seed value
	 */
	public static Random seedRandom(long seed)throws IOException{ //all Random generators created with same seed will generate the same list of numbers
		String t = "" + seed;
		putString(t, sSeed_); //saves the seed to a text file (EDSeed.txt)
		Random n = new Random(seed); //gets a Random number generator using designated seed
		return n; //returns the number generator
	}

	public static int getRandom(Random rand){ //uses a Random # gen to return the next integer value in the list
		int nxtInt = rand.nextInt(96); //gets an int >= 0 and < 96 ( 127 - 32 = 95 total characters)
		return nxtInt;
	}
 }
