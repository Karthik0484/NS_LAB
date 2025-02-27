import java.security.*;
import java.util.Base64;
import java.util.Scanner;

public class DigitalSignatureDemo {

    public static void main(String[] args) {
        try {
            // Step 1: Accept text input from the user.
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter text to sign:");
            String data = scanner.nextLine();
            scanner.close();

            // Step 2: Create a KeyPairGenerator object and initialize it.
            // Here we use DSA (Digital Signature Algorithm) with a key size of 2048 bits.
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");
            keyPairGen.initialize(2048);

            // Step 3: Generate the pair of keys and get the private key.
            KeyPair keyPair = keyPairGen.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();

            // Step 4: Create a Signature object and initialize it with the private key.
            // We use the SHA256withDSA algorithm.
            Signature signature = Signature.getInstance("SHA256withDSA");
            signature.initSign(privateKey);

            // Step 5: Add data into the signature object and calculate the signature.
            signature.update(data.getBytes("UTF-8"));
            byte[] digitalSignature = signature.sign();

            // Step 6: Print the digital signature value (encoded in Base64 for readability).
            String signatureBase64 = Base64.getEncoder().encodeToString(digitalSignature);
            System.out.println("Digital Signature: " + signatureBase64);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
