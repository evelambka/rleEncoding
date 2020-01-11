import java.util.Scanner;

public class RleProgram {
        public static String toHexString(byte[] data) {
            // ONE Translates data (RLE or raw) to a hexadecimal string (without delimiters).
            // Ex:toHexString(new byte[] {3, 15, 6, 4}) yields string "3f64".
            String desiredString = "";
            for (int i = 0; i < data.length; i++) {
                //this switch statement will look at each number in the array and assign it the correct hexidecimal value
                //it will take that value and add it to a string until the array is empty.
                switch (data[i]) {
                    case 0:
                        desiredString = desiredString + "0";
                        break;
                    case 1:
                        desiredString = desiredString + "1";
                        break;
                    case 2:
                        desiredString = desiredString + "2";
                        break;
                    case 3:
                        desiredString = desiredString + "3";
                        break;
                    case 4:
                        desiredString = desiredString + "4";
                        break;
                    case 5:
                        desiredString = desiredString + "5";
                        break;
                    case 6:
                        desiredString = desiredString + "6";
                        break;
                    case 7:
                        desiredString = desiredString + "7";
                        break;
                    case 8:
                        desiredString = desiredString + "8";
                        break;
                    case 9:
                        desiredString = desiredString + "9";
                        break;
                    case 10:
                        desiredString = desiredString + "a";
                        break;
                    case 11:
                        desiredString = desiredString + "b";
                        break;
                    case 12:
                        desiredString = desiredString + "c";
                        break;
                    case 13:
                        desiredString = desiredString + "d";
                        break;
                    case 14:
                        desiredString = desiredString + "e";
                        break;
                    case 15:
                        desiredString = desiredString + "f";
                        break;
                }
            }
            return desiredString;
        }

        public static int countRuns(byte[] flatData) {
            //TWO Returns number of runs of data in an image data set; double this result for length of encoded (RLE) byte array.
            // Ex:countRuns(new byte[] {15, 15, 15, 4, 4, 4, 4, 4, 4}) yields integer 2.
            int count = 0;
            int runs = 0;
            int runs2 = 0;
            int lengthCurrent = flatData.length;
            int lengthInto = 0;

            //this loop will continue to run for the entire length of the array.
            while (lengthCurrent <= flatData.length) {
                //this introduces the integer that will be counted until a new integer comes up in the array
                int lookingFor = flatData[lengthInto];
                if (lookingFor == flatData[lengthInto]) {
                    count = 0;
                }
                //i used this fragment of code from the encodeRle method so that is why the count is inside of the while loop
                while (lookingFor == flatData[lengthInto]) {
                    lengthCurrent --;
                    count++;
                    lengthInto++;
                    if (count == 15) {
                        break;
                    }
                    if (lengthCurrent == 0) {
                        break;
                    }
                }
                runs++;
                runs2++;

                //this will break from the loop once the
                if (lengthCurrent == 0) {
                    break;
                }

            }
            return runs2;
        }

        public static byte[] encodeRle(byte[] flatData) {
            //THREE Returns encoding (in RLE) of the raw data passed in; used to generate RLE representation of a data.
            // Ex: encodeRle(new byte[] {  15, 15, 15, 4, 4, 4, 4,  4, 4}) yields byte array {  3,  15,   6, 4   }.
            //this sends the array to the countRuns method to determine the length of the encoded array.
            int numRuns = countRuns(flatData);
            byte[] desiredArray = new byte[numRuns*2];
            byte count = 0;
            int i = 0;
            int lengthInto = 0;
            int lengthCurrent = flatData.length;

            //this loop will continue to run for the entire length of the array.
            while (lengthCurrent <= flatData.length) {
                //this introduces the integer that will be counted until a new integer comes up in the array
                byte lookingFor = flatData[lengthInto];
                //if the next integer is not the same as the one I'm looking for, the count resets to 0
                if (lookingFor == flatData[lengthInto]) {
                    count = 0;
                }
                //while the next integer is still the same as the one I'm looking for, I will increase the count
                //and continue looking through the array
                while (lookingFor == flatData[lengthInto]) {
                    lengthCurrent --;
                    count++;
                    lengthInto++;
                    if (count == 15) {
                        break;
                    }
                    if (lengthCurrent == 0) {
                        break;
                    }
                }

                //this if statement just breaks the loop when the end of the array has been reached
                if (lengthInto == (flatData.length)) {
                    desiredArray[i] = count;
                    desiredArray[i + 1] = lookingFor;
                    i = i + 2;
                    break;
                }

                //this code adds the values to the new array. First the count of the number I was looking for, then
                //the value of the number I was looking for.
                desiredArray[i] = count;
                desiredArray[i + 1] = lookingFor;
                i = i + 2;

                //when all of the array has been encoded, the loop will break
                if (lengthCurrent == 0) {
                    break;
                }
            }
            return desiredArray;
        }

        public static int getDecodedLength(byte[] rleData) {
            //FOUR Returns decompressed size RLE data; used to generate flat data from RLE encoding. (Counterpart to #2)
            // Ex: getDecodedLength(new byte[] {  3,  15,   6, 4}) yields integer9.
            int i = 0;
            int decodedLength = 0;

            //this while loop basically adds every other number in the array to get the length of the decompressed data
            while (i < rleData.length) {
                int firstNum = 0;
                firstNum = rleData [i];
                decodedLength = decodedLength + firstNum;
                i= i +2;
            }
            return decodedLength;
        }

        public static byte[] decodeRle(byte[] rleData) {
            //FIVE Returns the decoded data set from RLE encoded data. This decompresses RLE data for use. (Inverse of #3)
            // Ex:decodeRle(new byte[] {3, 15, 6, 4}) yields byte array{  15, 15, 15,  4, 4, 4, 4,  4, 4   }
            int lengthOfarray = getDecodedLength(rleData);
            byte []outarray = new byte[lengthOfarray];
            int counter = 0;
            int f = 1;
            int j = 0;
            int specificNum = 0;
            int checkIfDone = 0;
            //this while loop will continue to run until every number in the array has been read
            while (checkIfDone == 0) {
                //this for loop will read the first 2 numbers in the array: the first number is the amount of times
                //that the second number will occur in decompressed data
                for (int i = 0; i < rleData[j]; i++) {
                    outarray [counter] = rleData[f];
                    counter ++;
                }
                j = j+2;
                f = f+2;
                specificNum ++;
                //once the entire array has been read, this if statement will break the loop
                if (specificNum == (rleData.length / 2)) {
                    checkIfDone = 1;
                }
            }
            return outarray;
        }

        public static byte[] stringToData(String dataString) {
            //SIX Translates a string in hexadecimal format into byte data (can be raw or RLE). (Inverse of #1)
            // Ex:stringToData ("3f64") yields byte array{  3, 15, 6, 4}.
            byte []desiredArray = new byte[(dataString.length())];
            int count = 0;
            while (count < dataString.length()) {
                //this switch statement looks at each element in the hexidecimal string and converts it to the correct
                //decimal value in the return array.
                switch (dataString.charAt(count)) {
                    case '0':
                        desiredArray[count] = 0;
                        break;
                    case '1':
                        desiredArray[count] = 1;
                        break;
                    case '2':
                        desiredArray[count] = 2;
                        break;
                    case '3':
                        desiredArray[count] = 3;
                        break;
                    case '4':
                        desiredArray[count] = 4;
                        break;
                    case '5':
                        desiredArray[count] = 5;
                        break;
                    case '6':
                        desiredArray[count] = 6;
                        break;
                    case '7':
                        desiredArray[count] = 7;
                        break;
                    case '8':
                        desiredArray[count] = 8;
                        break;
                    case '9':
                        desiredArray[count] = 9;
                        break;
                    case 'a':
                    case 'A':
                        desiredArray[count] = 10;
                        break;
                    case 'b':
                    case 'B':
                        desiredArray[count] = 11;
                        break;
                    case 'c':
                    case 'C':
                        desiredArray[count] = 12;
                        break;
                    case 'd':
                    case 'D':
                        desiredArray[count] = 13;
                        break;
                    case 'e':
                    case 'E':
                        desiredArray[count] = 14;
                        break;
                    case 'f':
                    case 'F':
                        desiredArray[count] = 15;
                        break;
                }
                count++;
            }
            return desiredArray;
        }

        public static String toRleString(byte[] rleData) {
            //SEVEN Translates  RLE  data  into  a  human-readable  representation.  For  each  run,  in  order,  it  should
            // display  the  run  length in decimal (1-2 digits); the run value in hexadecimal (1 digit); and a delimiter,
            // ‘:’, between runs. (See examples in standalone section.)
            // Ex:toRleString(new byte[] { 15, 15, 6, 4}) yields string "15 f:64".
            int i = 0;
            int lengthInto = 0;
            String desiredString = "";
            while (lengthInto < rleData.length) {
                int firstNum = 0;
                firstNum = rleData[i];
                desiredString = desiredString + firstNum;
                //this switch statement will turn every other number in the array into a hexidecimal if it is 10 or over
                switch (rleData[i+1]) {
                    case 0:
                        desiredString = desiredString + "0";
                        break;
                    case 1:
                        desiredString = desiredString + "1";
                        break;
                    case 2:
                        desiredString = desiredString + "2";
                        break;
                    case 3:
                        desiredString = desiredString + "3";
                        break;
                    case 4:
                        desiredString = desiredString + "4";
                        break;
                    case 5:
                        desiredString = desiredString + "5";
                        break;
                    case 6:
                        desiredString = desiredString + "6";
                        break;
                    case 7:
                        desiredString = desiredString + "7";
                        break;
                    case 8:
                        desiredString = desiredString + "8";
                        break;
                    case 9:
                        desiredString = desiredString + "9";
                        break;
                    case 10:
                        desiredString = desiredString + "a";
                        break;
                    case 11:
                        desiredString = desiredString + "b";
                        break;
                    case 12:
                        desiredString = desiredString + "c";
                        break;
                    case 13:
                        desiredString = desiredString + "d";
                        break;
                    case 14:
                        desiredString = desiredString + "e";
                        break;
                    case 15:
                        desiredString = desiredString + "f";
                        break;
                }
                lengthInto = lengthInto + 2;
                if (lengthInto == rleData.length) {
                    break;
                }
                //this inserts the delimiter into the string after the first two values in the array have been inserted into the string
                desiredString = desiredString + ":";
                i = i + 2;
            }
            return desiredString;
        }

        public static byte[] stringToRle(String rleString) {
            //EIGHT Translates a string in human-readable RLE format (with delimiters) into RLE byte data.
            // (Inverse of #7)Ex:stringToRle("15 f:64") yields byte array{  15,  15,   6, 4   }
            String duplicate = rleString;
            int x = 0;
            int length = duplicate.length();

            //this while loop determined the length of the array that will be output from this method
            while (length >= 1) {
                //this if statement accounts for the parts of the string with 3 values (xxx:)
                if (duplicate.charAt(2) == ':') {
                    x = x + 2;
                    duplicate = duplicate.substring(3);
                    length = length - 3;
                    if (length == 2 || length == 3) {
                        duplicate = duplicate + ":";
                        length++;
                    }
                    continue;
                }

                //this if statement accounts for the parts of the string with 2 values (xx:)
                if (duplicate.charAt(3) == ':') {
                    x = x + 2;
                    duplicate = duplicate.substring(4);
                    length = length - 4;

                    if (length == 2 || length == 3) {
                        duplicate = duplicate + ":";
                        length++;
                    }
                    continue;
                }
            }

            //then i create the array that will be output from this method with the length determined from the code above
            byte []desiredArray = new byte[x];
            char numOfTerms;
            char theTerm;

            int i = 0;
            int l = rleString.length();
            int j= 0;
            int k = 0;

            while (l >= 1) {
                //this if statement accounts for the parts of the string with 2 values (xx:)
                if (rleString.charAt(2) == ':') {
                    numOfTerms = rleString.charAt(0);
                    // this switch statement reads the first value from the string
                    switch (numOfTerms) {
                        case '0':
                            j = 0;
                            break;
                        case '1':
                            j = 1;
                            break;
                        case '2':
                            j = 2;
                            break;
                        case '3':
                            j = 3;
                            break;
                        case '4':
                            j = 4;
                            break;
                        case '5':
                            j = 5;
                            break;
                        case '6':
                            j = 6;
                            break;
                        case '7':
                            j = 7;
                            break;
                        case '8':
                            j = 8;
                            break;
                        case '9':
                            j = 9;
                            break;

                    }
                    //this switch statement reads the second value in the string
                    theTerm = rleString.charAt(1);
                    switch (theTerm) {
                        case '0':
                            k = 0;
                            break;
                        case '1':
                            k = 1;
                            break;
                        case '2':
                            k = 2;
                            break;
                        case '3':
                            k = 3;
                            break;
                        case '4':
                            k = 4;
                            break;
                        case '5':
                            k = 5;
                            break;
                        case '6':
                            k = 6;
                            break;
                        case '7':
                            k = 7;
                            break;
                        case '8':
                            k = 8;
                            break;
                        case '9':
                            k = 9;
                            break;
                        case 'a':
                        case 'A':
                            k = 10;
                            break;
                        case 'b':
                        case 'B':
                            k = 11;
                            break;
                        case 'c':
                        case 'C':
                            k = 12;
                            break;
                        case 'd':
                        case 'D':
                            k = 13;
                            break;
                        case 'e':
                        case 'E':
                            k = 14;
                            break;
                        case 'f':
                        case 'F':
                            k = 15;
                            break;
                    }
                    //this assigns the values found from above into the proper place in the array.
                    desiredArray[i] = (byte) j;
                    desiredArray[i + 1] = (byte) k;
                    //this cuts out the part of the string that I just put into the array and evaluates the next values
                    rleString = rleString.substring(3);

                    i = i + 2;
                    l = l - 3;

                    //if I'm at the last values in the string it adds a delimiter so my if statements will work
                    if (l == 2 || l==3) {
                        rleString = rleString + ":";
                        l++;
                    }
                    continue;
                }

                if (rleString.charAt(3) == ':') {
                    numOfTerms = rleString.charAt(1);
                    // this switch statement reads the ones place from double digit value in the string
                    switch (numOfTerms) {
                        case '0':
                            j = 10;
                            break;
                        case '1':
                            j = 11;
                            break;
                        case '2':
                            j = 12;
                            break;
                        case '3':
                            j = 13;
                            break;
                        case '4':
                            j = 14;
                            break;
                        case '5':
                            j = 15;
                            break;
                        case '6':
                            j = 16;
                            break;
                        case '7':
                            j = 17;
                            break;
                        case '8':
                            j = 18;
                            break;
                    }
                    theTerm = rleString.charAt(2);

                    // this switch statement reads the second value from the string
                    switch (theTerm) {
                        case '0':
                            k = 0;
                            break;
                        case '1':
                            k = 1;
                            break;
                        case '2':
                            k = 2;
                            break;
                        case '3':
                            k = 3;
                            break;
                        case '4':
                            k = 4;
                            break;
                        case '5':
                            k = 5;
                            break;
                        case '6':
                            k = 6;
                            break;
                        case '7':
                            k = 7;
                            break;
                        case '8':
                            k = 8;
                            break;
                        case '9':
                            k = 9;
                            break;
                        case 'a':
                        case 'A':
                            k = 10;
                            break;
                        case 'b':
                        case 'B':
                            k = 11;
                            break;
                        case 'c':
                        case 'C':
                            k = 12;
                            break;
                        case 'd':
                        case 'D':
                            k = 13;
                            break;
                        case 'e':
                        case 'E':
                            k = 14;
                            break;
                        case 'f':
                        case 'F':
                            k = 15;
                            break;
                    }

                    //this assigns the values found from above into the proper place in the array.
                    desiredArray[i] = (byte) j;
                    desiredArray[i + 1] = (byte) k;

                    //this cuts out the part of the string that I just put into the array and evaluates the next values
                    rleString = rleString.substring(4);
                    i = i + 2;
                    l= l - 4;

                    //if I'm at the last values in the string it adds a delimiter so my if statements will work
                    if (l == 2 || l == 3) {
                        rleString = rleString + ":";
                        l++;
                    }
                    continue;
                }
            }
            return desiredArray;
        }

        public static void main(String[] args) {
            // this block of code prints the welcome message and the spectrum image
            System.out.println("Welcome to the RLE image encoder!");
            System.out.println(" ");
            System.out.println("Displaying Spectrum Image: ");
            ConsoleGfx ConsoleGfx = new ConsoleGfx();
            ConsoleGfx.displayImage(ConsoleGfx.testRainbow);

            //this initiates the imageData array that will be updated when the user chooses options 1 or 2.
            //it also initiates the personFile that will be updated when the user enters the file they wish to load
            byte [] imageData = null;
            String personFile = "";

            int checkIfDone = 0;
            //this while loop will continue until the user chooses to exit with option 0
            while (checkIfDone == 0) {
                System.out.println(" ");
                System.out.println("RLE Menu");
                System.out.println("--------");
                System.out.println("0. Exit");
                System.out.println("1. Load File");
                System.out.println("2. Load Test Image");
                System.out.println("3. Read RLE String");
                System.out.println("4. Read RLE Hex String");
                System.out.println("5. Read Data Hex String");
                System.out.println("6. Display Image");
                System.out.println("7. Display RLE String");
                System.out.println("8. Display Hex RLE Data");
                System.out.println("9. Display Hex Flat Data");
                System.out.println(" ");
                System.out.print("Select a Menu Option: ");

                //this reads the user's input on what option they wish to choose
                Scanner keyboard = new Scanner(System.in);
                int personChoice = keyboard.nextInt();

                //if the user inputs a value that is not 1-9 it will return an error message
                if (personChoice < 0 || personChoice > 9) {
                    System.out.println("Error! Invalid input.");
                }

                //option 0: user can exit
                if (personChoice == 0) {
                    checkIfDone = 1;
                }

                //option 1: user enters the file they wish to load
                if (personChoice == 1) {
                    System.out.print("Enter name of file to load: ");
                    personFile = keyboard.next();
                    imageData = ConsoleGfx.loadFile(personFile);
                }

                //option 2: the program loads in the test image data
                if (personChoice == 2) {
                    imageData = ConsoleGfx.testImage;
                    System.out.println("Test image data loaded.");
                }

                //option 3: takes the user's rle string that they want decoded
                if (personChoice == 3) {
                    System.out.print("Enter an RLE string to be decoded: ");
                    String response = keyboard.next();
                    byte[] nextStep = stringToRle(response);
                    imageData = decodeRle(nextStep);
                }

                //option 4: takes the user's hex string that they wish to convert
                if (personChoice == 4) {
                    System.out.print("Enter the hex string holding RLE data: ");
                    String response = keyboard.next();
                    byte [] nextStep = stringToData(response);
                    imageData = decodeRle(nextStep);
                }

                //option 5: takes the user's hex string that they wish to convert
                if (personChoice == 5) {
                    System.out.print("Enter the hex string holding flat data: ");
                    String response = keyboard.next();
                }

                //option 6: prints out the image that is currently loaded into the system
                if (personChoice == 6) {
                    System.out.println("Displaying image... ");
                    if (imageData == null){
                        System.out.println("(no data)");
                        continue;
                    }
                    ConsoleGfx.displayImage(imageData);
                }

                //option 7: prints out the RLE representation of the data that is currently in the system
                if (personChoice == 7) {
                    if (imageData == null) {
                        System.out.println("RLE representation: (no data)");
                        continue;
                    }
                    byte[] nextStep = encodeRle(imageData);
                    String desiredString = toRleString(nextStep);
                    System.out.println("RLE representation: " + desiredString);
                }

                //option 8: prints out the RLE hex values of the data that is currently in the system
                if (personChoice == 8) {
                    if (imageData == null) {
                        System.out.println("RLE hex values: (no data)");
                        continue;
                    }
                    byte[] nextStep = encodeRle(imageData);
                    String desiredString = toHexString(nextStep);
                    System.out.println("RLE hex values: " + desiredString);
                }

                //option 9: prints out the flat data of the data that is currently in the system
                if (personChoice == 9) {
                    if (imageData == null) {
                        System.out.println("Flat hex values: (no data)");
                        continue;
                    }
                    String nextStep = toHexString(imageData);
                    System.out.println("Flat hex values: " + nextStep);
                }
            }
        }
    }
