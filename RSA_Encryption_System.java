import java.util.Arrays;
import java.util.Random;
//import java.util.Scanner;

public class RSA_Encryption_System {
   static int seed = 5 ;
   static  int quantity = 100;
 public static void main (String [] args){

    // int seed = 5 ;
    // int quantity = 100;


    System.out.println(" welcom to my main class we will test all my method here :) ");
        System.out.println(" first we will test RSAKeyGenerator it's will generate random numbers using LCG method ");
        Linear_Congruenal_Generator LinearCongruenalGenerator = new Linear_Congruenal_Generator();
       // LinearCongruenalGenerator.LCG(5, 100);
        System.out.println(" Hi there! This is LCG method, I am called with (seed=5  quantity=100) ");
        System.out.println(" and I have those initialized local variables(A = 1664525  C = 1013904223  M = 32767) ");
        System.out.println (" I generated 100 random numbers");

        // Generate pseudo-random numbers using the LCG method
        int[] randomNumbers = LinearCongruenalGenerator.LCG(seed, quantity);

        // Print the generated random numbers
        System.out.println("Generated Random Numbers: " + Arrays.toString(randomNumbers));
// done here 
System.out.println("bye now! --LCG method");

// ******************************************************************************************************************************

System.out.println("now I will examin the generateKeys method  (millerRabinTest) ");

int rand =randomNumbers[0];
MillerRabin millerRabin = new MillerRabin();
if (millerRabin.isPrime(rand, 5))
            System.out.println("\n" + rand + " is probably prime");
        else
            System.out.println("\n" + rand + " is composite");
            System.out.println("  assign p to the first number that passes millerRabinTest q to the second number ");

 

 // ******************************************************************************************************************************
 System.out.println("finally, I am creating an instance of KeyPair class as:");
 //RSAKeyGenerator mykey = new RSAKeyGenerator();
 long[] mykeys = RSA_Encryption_System.RSAKeyGenerator.generateKeys();
 //Return public key (n, e) and private key (n, d) {n, e, d}
 long privateKey = mykeys[2];
 long publicteKey = mykeys[1];
 long n = mykeys[0];
 System.out.printf(" my privatekey is ( %d , %d )\n" , n,privateKey );
 System.out.printf(" my publicteKey is ( %d , %d )\n" , n,publicteKey );
 System.out.println(" bye now :) generateKeys method");

 // ******************************************************************************************************************************
 //Encryption encryption = new Encryption();
 String Massage = "hello";
 System.out.printf(" setting plaintext to: hello \ncalling encrypt...\n");
 long[] encryptedMassuge =RSA_Encryption_System.Encryption.encrypt(Massage, publicteKey, n);
 System.out.printf(" Hi there! This is encrypt method \n converting my string to int: \n " );
 int[] stringtoInt = RSA_Encryption_System.Encryption.String_to_intArray(Massage);
 System.out.println(stringtoInt);
 System.out.println(" encryptedValues:");
 System.out.println(encryptedMassuge);
 System.out.println(" bye now! --encrypt method "); 
 // ******************************************************************************************************************************
 //Decryption decryption = new Decryption();
  String decr =RSA_Encryption_System.Decryption.decrypt(encryptedMassuge, privateKey,n );
  
 System.out.println(" calling decrypt on encrypt output... ");
 System.out.printf(" Hi there! This is decrypt method \n decryptedValues:\n " );
 System.out.println(encryptedMassuge);
 System.out.print(" ArrayToString::");
 System.out.println(RSA_Encryption_System.Decryption.intArray_to_String(stringtoInt));


if  (Massage.equalsIgnoreCase(Decryption.intArray_to_String(Encryption.String_to_intArray(Massage))))
System.out.println("Yes! they are");
 // ******************************************************************************************************************************
 System.out.println("bye now! --main method");

 }
 
// ************** Linear Congruen/al Generator (LCG) **************
 
public static class Linear_Congruenal_Generator {
    // Constants for the Linear Congruential Generator (LCG)
    private   int A = 1664525;
    private   int C = 1013904223;
    private   int M = 32767 ;//(int) Math.pow(2, 32);


    /**
     * Generates a sequence of pseudo-random numbers using a Linear Congruential Generator (LCG).
     *  formula: X_{n+1} = (A * X_n + C) % M
     * seed     The initial seed for the generator - the fist value to start -.
     *  quantity The number of pseudo-random numbers to generate.
     * the result of the method is to return An array containing the generated pseudo-random numbers.
     */
    public int[] LCG(int seed, int quantity) {
        // Array to store the generated pseudo-random numbers
        int[] randomNumbers = new int[quantity];

        // Initialize the current seed with the provided seed value
        int currentSeed = seed;

        // Generate pseudo-random numbers using the LCG formula
        for (int i = 0; i < quantity; i++) {
            currentSeed = (A * currentSeed + C) % M; // the formela 
            randomNumbers[i] = currentSeed; // important because all valuse dapande in previos value 
        }

        // Return the array of generated pseudo-random numbers
        return randomNumbers;
    }
    



}

// ************** Miller-Rabin Primality Test **************

public static class MillerRabin {

    // Function to check if a number is prime using the Miller-Rabin primality test
    public boolean isPrime(long n, int iteration) {
        // Base cases
        if (n == 0 || n == 1)
            return false;
        if (n == 2)
            return true;
        if (n % 2 == 0)
            return false;

        // Factor out powers of 2 from n-1
        long s = n - 1;
        while (s % 2 == 0)
            s /= 2;

        Random rand = new Random();
        for (int i = 0; i < iteration; i++) {
            // Generate a random witness 'a' in the range [1, n-1]
            long r = Math.abs(rand.nextLong());
            long a = r % (n - 1) + 1;

            // Initialize temporary variables
            long temp = s;
            long mod = modularExponentiation(a, temp, n);

            // Check if a^(s*2^k) is congruent to 1 or n-1 for some k
            while (temp != n - 1 && mod != 1 && mod != n - 1) {
                mod = multiplyMod(mod, mod, n);
                temp *= 2;
            }

            // If the conditions are not met, n is composite
            if (mod != n - 1 && temp % 2 == 0)
                return false;
        }
        // If no witness was found, n is probably prime
        return true;
    }

    // Function to calculate (a ^ b) % c using modular exponentiation
    public long modularExponentiation(long a, long b, long c) {
        long result = 1;
        a %= c;

        // Use binary exponentiation to efficiently calculate (a ^ b) % c
        while (b > 0) {
            if ((b & 1) == 1) {
                result = multiplyMod(result, a, c);
            }
            a = multiplyMod(a, a, c);
            b >>= 1;
        }
        return result % c;
    }

    // Function to calculate (a * b) % c using modular multiplication
    public long multiplyMod(long a, long b, long mod) {
        long result = 0;
        a %= mod;

        // Use long multiplication with modular reduction
        while (b > 0) {
            if ((b & 1) == 1) {
                result = (result + a) % mod;
            }
            a = (a * 2) % mod;
            b >>= 1;
        }
        return result;
    }
}

// ************** RSA Key Generaton **************

public static class RSAKeyGenerator {
    static Linear_Congruenal_Generator random = new Linear_Congruenal_Generator();
    static int[] randomNumbers = random.LCG(seed, quantity);
    static long rand =randomNumbers[0];
    static long rand2 =randomNumbers[2];

    // Method to generate RSA public and private keys using longs 
    public static long[] generateKeys() {
        // Choose two distinct prime numbers
        long p = rand;
        long q = rand2;

        // Calculate n = p * q
        long n = p * q;

        // Calculate totient = (p-1) * (q-1)
        long totient = (p - 1) * (q - 1);

        // Choose public exponent e such that 1 < e < totient and gcd(e, totient) = 1
        long e = choosePublicExponent(totient);

        // Calculate private exponent d such that d * e ≡ 1 (mod totient)
        long d = extendedEuclideanAlgorithm(e, totient);

        // Return public key (n, e) and private key (n, d)
        return new long[]{n, e, d};
    }

    // // Method to generate a random prime number
    // private static long generatePrime() {
    //     return getRandomPrime();
    // }

    // // Method to generate a random prime number (simplified)
    // private static long getRandomPrime() {
    //     Random random = new Random();
    //     long number;
    //     do {
    //         number = random.nextInt(1000) + 1000; // Choose a random number
    //     } while (!isPrime(number));
    //     return number;
    // }

    // // Method to check if a number is prime (simplified)
    // private static boolean isPrime(long number) {
    //     if (number < 2) {
    //         return false;
    //     }
    //     for (long i = 2; i * i <= number; i++) {
    //         if (number % i == 0) {
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    // Method to choose public exponent e
    private static long choosePublicExponent(long totient) {
        long e = 65537; // A common choice for e
        while (e >= totient || gcd(e, totient) != 1) {
            e += 2; // Increment by 2 to ensure an odd value
        }
        return e;
    }

    // Method to find the modular multiplicative inverse using Extended Euclidean Algorithm
    private static long extendedEuclideanAlgorithm(long a, long b) {
        // Helper method returns an array [gcd, x, y]
        long[] result = extendedEuclideanAlgorithmHelper(a, b);
        long gcd = result[0];
        long x = result[1];
        long y = result[2];

        // Ensure x is positive
        return (x % b + b) % b;
    }

    // Helper method for Extended Euclidean Algorithm
    private static long[] extendedEuclideanAlgorithmHelper(long a, long b) {
        if (b == 0) {
            return new long[]{a, 1, 0};
        } else {
            // Recursively calculate [gcd, x, y]
            long[] result = extendedEuclideanAlgorithmHelper(b, a % b);
            long gcd = result[0];
            long x1 = result[1];
            long y1 = result[2];

            // Update x and y using the recursive results
            long x = y1;
            long y = x1 - (a / b) * y1;

            return new long[]{gcd, x, y};
        }
    }

    // Method to calculate the greatest common divisor (gcd)
    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    
}

// ************** Encryption and decryption and modular arithmetic operations **************

// ******************************************************************************************************************************



public class Decryption {

    

    // Helper method to convert a string to an array of integers
    public static int[] String_to_intArray(String message) {
        int[] intArray = new int[message.length()];

        for (int i = 0; i < message.length(); i++) {
            intArray[i] = (int) message.charAt(i);
        }

        return intArray;
    }

    // Helper method to convert an array of integers to a string
    public static String intArray_to_String(int[] intArray) {
        char[] charArray = new char[intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            charArray[i] = (char) intArray[i];
        }

        return new String(charArray);
    }

    // Method to decrypt a ciphertext message using the private key
    public static String decrypt(long[] ciphertext, long d, long n) {
        String decryptedMessage = "";

        for (long cipher : ciphertext) {
            // Decrypt using modular exponentiation
            long result = modularExponentiation(cipher, d, n);
            decryptedMessage += (char) result;
        }

        return decryptedMessage;
    }

    // Method to encrypt a message using the public key
    public static long[] encrypt(String message, long e, long n) {
        int[] intArray = String_to_intArray(message);
        long[] ciphertext = new long[intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            // Encrypt using modular exponentiation
            long encrypted = modularExponentiation(intArray[i], e, n);
            ciphertext[i] = encrypted;
        }

        return ciphertext;
    }

    // Helper method for modular exponentiation
    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        base = base % modulus;

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }

            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }

        return result;
    }
}


// ******************************************************************************************************************************

public  static class Encryption {

    // Helper method to convert a string to an array of integers
    private static int[] String_to_intArray(String message) { 
        int[] intArray = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            intArray[i] = (int) message.charAt(i);
        }
        return intArray;
    }

    /**
     * Encrypts a plaintext message using a public key (e, n).
     *
     message The plaintext message to be encrypted.
      e The public exponent.
      n The modulus.
     this method will return An array of long representing the encrypted message.
     */
    public static long[] encrypt(String message, long e, long n) {
        int[] intArray = String_to_intArray(message);
        long[] encryptedArray = new long[intArray.length];

        // Encryption: c ≡ m^e (mod n)
        for (int i = 0; i < intArray.length; i++) {
            long messageValue = intArray[i];
            long encryptedValue = modularExponentiation(messageValue, e, n);
            encryptedArray[i] = encryptedValue;
        }

        return encryptedArray;
    }

    /**
     * Calculates (base^exponent) % modulus using the modular exponentiation algorithm.
     *
      base The base value.
     exponent The exponent value.
     modulus  The modulus value.
     this method will return The result of (base^exponent) % modulus.
     */
    private static long modularExponentiation(long base, long exponent, long modulus) { // i implement this method before 
        long result = 1;
        base = base % modulus;

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }

            exponent = exponent >> 1; // Right shift exponent by 1 (equivalent to dividing by 2)
            base = (base * base) % modulus;
        }

        return result;
    }
}

}//