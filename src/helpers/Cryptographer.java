/** Cryptographer
*  Encodes and decodes customer information for a banking system
*  @author Aryan Soni
*/

public class Cryptographer {
  
  /** Encodes a customer's information 
    * @prompt mssg The message to encode
    * @return The encoded information
    */
    public static String encode(String mssg) {
      
      String encrypted = "";

        // parse through reversed string ( reverse string to tighten encryption)
        for (int i = mssg.length() - 1; i >= 0; i--) {
          
            char selected = mssg.charAt(i);

            if (isValid(selected)) {
                encrypted += encrypt((int) selected);

            }
            else {
                encrypted += selected;
            }
        }

        return encrypted;
    }


  /** Decodes a customer's information 
    * @prompt mssg The message to decode
    * @return The decoded information
    */
    public static String decode(String mssg) {
      
        String decrypted = "";

        // reverse string when parsing (already reversed to encrypt, so essentially reverting to original)
        for (int i = mssg.length() - 1; i >= 0; i--) {
          
            char selected = mssg.charAt(i);

            if (isValid(selected)) {
                decrypted += decrypt((int) selected);

            }
            else {
                decrypted += selected;
            }
        }

        return decrypted; // returned decrypted message
    }


    /** Checks if a character is alphanumeric
      * @prompt selected The character to examine
      * @return Whether the character is valid to encrypt (i.e. whether it is alphanumeric)
      */
    public static boolean isValid(char selected) {
      
        int ascii = (int) selected;

        // if ascii is in range of A-Z or a-z
        if ((ascii >= 65 && ascii <= 90) || (ascii >= 97 && ascii <= 122)) {
            return true;
        }
        // else if ascii is a number
        else if (ascii >= 48 && ascii <= 57) {
            return true;
        }
        // else if ascii is a symbol that is utilized by the encryption key
        else if (ascii >= 33 && ascii <= 42) {
            return true;
        }

        return false;

    }

    /** Encrypts a character using the ascii representation of the character
      * @prompt ascii The ascii of the char
      * @return The encrypted char
      */
    public static String encrypt(int ascii) {
      
        int newAscii; // ascii value of encrypted char
        newAscii = ascii; 

        String newChar; // ascii of the original char converted to a letter/number/symbol

        // if letter is upper case
        if (ascii >= 65 && ascii <= 90) {
            // if ascii is in the first half of the alphabet
            if (ascii >= 65 && ascii <= 77) {
                // new ascii is opposite letter in the alphabet (ex. a becomes z)
                newAscii = 155 - ascii;
            }
            // else if ascii is in the second half of the alphabet
            else if (ascii >= 78 && ascii <= 90) {
                // new ascii is pos. of letter in second half of alphabet converted to the first half
                // for example, n is pos. 1 in the second half of the alphabet, hence n turns into a (pos. 1 in the first half of the alphabet)
                newAscii = ascii - 13;
            }

            // shift newAscii 7 right
            newAscii += 7;

            // if newAscii is over limit for ascii
            if (newAscii > 90) {
                // loop back to beginning of alphabet and add remaining amount
                newAscii = (newAscii - 90) + 64;
            }

        }
        // else if letter is lower case
        else if (ascii >= 97 && ascii <= 122) {

            // if ascii is in the first half of the alphabet
            if (ascii >= 97 && ascii <= 109) {
                // new ascii is opposite letter in the alphabet (ex. a becomes z)
                newAscii = 219 - ascii;
            }
            // else if ascii is in the second half of the alphabet
            else if (ascii >= 110 && ascii <= 122) {
                // new ascii is pos. of letter in second half of alphabet converted to the first half
                // for example, n is pos. 1 in the second half of the alphabet, hence n turns into a (pos. 1 in the first half of the alphabet)
                newAscii = ascii - 13;
            }

            // shift newAscii 7 right
            newAscii += 7;

            // if newAscii is over limit for ascii
            if (newAscii > 122) {
                // loop back to beginning of alphabet and add remaining amount
                newAscii = (newAscii - 122) + 96;
            }
        }
        // else if ascii is a number
        else if (ascii >= 48 && ascii <= 57) {
            // the ascii of 0 is 48, so to convert ascii to a number, simply subtract 48 from it
            // subtract the original number from 9 to get new encrypted number
            newAscii = 48 + 9 - (ascii - 48); // add 48 to convert it back to ascii

            // taking this number, convert it to a symbol
            newAscii -= 15;
        }

        newChar = Character.toString((char)(newAscii));

        return newChar;
    }


    /** Decrypts a character using the ascii representation of the character
      * @prompt ascii The ascii of the char
      * @return The decrypted char
      */
    public static String decrypt(int ascii) {
        int newAscii; // will store ascii value of decrypted letter/number
        newAscii = ascii; // default to ascii value of the encrypted char

        String newChar; // ascii converted to char

        // shift newAscii 7 left (will only apply to letters)
        newAscii -= 7;

        // if letter is lower case and below range
        if (newAscii < 97 && newAscii > 89) {
            // loop to end of alphabet and make up for remaining amount
            newAscii = 123 - (97 - newAscii);
        }
        // else if letter is upper case and below range
        else if (newAscii < 65) {
            // loop to end of alphabet and make up for remaining amount
            newAscii = 91 - (65 - newAscii);
        }

        // if newAscii is upper case
        if (newAscii >= 65 && newAscii <= 90) {
            // if newAscii is in the first half of the alphabet
            if (newAscii >= 65 && newAscii <= 77) {
                // new newAscii is pos. of letter in first half of alphabet converted to the second half
                // for example, a is pos. 1 in the first half of the alphabet, hence a turns into n (pos. 1 in the second half of the alphabet)
                newAscii = newAscii + 13;
            }
            // else if newAscii is in the second half of the alphabet
            else if (newAscii >= 78 && newAscii <= 90) {
                // newAscii is opposite letter in the alphabet (ex. z becomes a)
                newAscii = 65 + (90 - newAscii);
            }

        }
        // else if letter is lower case
        else if (newAscii >= 97 && newAscii <= 122) {
            // if newAscii is in the first half of the alphabet
            if (newAscii >= 97 && newAscii <= 109) {
                // newAscii is pos. of letter in first half of alphabet converted to the second half
                // for example, a is pos. 1 in the first half of the alphabet, hence a turns into n (pos. 1 in the second half of the alphabet)
                newAscii = newAscii + 13;
            }
            // else if newAscii is in the second half of the alphabet
            else if (newAscii >= 110 && newAscii <= 122) {
                // newAscii is opposite letter in the alphabet (ex. z becomes a)
                newAscii = 97 + (122 - newAscii);
            }

        }
        // else if encrypted ascii is a symbol (meaning it was a number that was decrypted)
        else if (ascii >= 33 && ascii <= 42) {
            ascii += 15; // convert ascii from symbol to number

            // the ascii of 0 is 48, so to convert ascii to a number, simply subtract 48 from it
            // subtract the encrypted number from 9 to get original number
            newAscii = 48 + 9 - (ascii - 48); // add 48 to convert it back to an ascii
        }

        newChar = Character.toString((char)(newAscii));

        return newChar;

    }
} // Cryptographer class