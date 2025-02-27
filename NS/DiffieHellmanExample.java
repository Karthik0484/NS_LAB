import java.security.*;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public class DiffieHellmanExample {
    public static void main(String[] args) throws Exception {
        // Step 1: Generate Key Pair for Alice
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DH");
        keyGen.initialize(512); // Using 512-bit Diffie-Hellman key for simplicity
        KeyPair alicePair = keyGen.generateKeyPair();
        PublicKey alicePublicKey = alicePair.getPublic();
        PrivateKey alicePrivateKey = alicePair.getPrivate();

        // Step 2: Extract DH Parameters from Alice's Public Key
        DHParameterSpec dhSpec = ((DHPublicKey) alicePublicKey).getParams();

        // Step 3: Generate Key Pair for Bob using Alice's DH Parameters
        KeyPairGenerator bobKeyGen = KeyPairGenerator.getInstance("DH");
        bobKeyGen.initialize(dhSpec); // Bob uses the same parameters as Alice
        KeyPair bobPair = bobKeyGen.generateKeyPair();
        PublicKey bobPublicKey = bobPair.getPublic();
        PrivateKey bobPrivateKey = bobPair.getPrivate();

        // Step 4: Compute Shared Secret for Alice
        KeyAgreement aliceKeyAgreement = KeyAgreement.getInstance("DH");
        aliceKeyAgreement.init(alicePrivateKey);
        aliceKeyAgreement.doPhase(bobPublicKey, true);
        byte[] aliceSharedSecret = aliceKeyAgreement.generateSecret();

        // Step 5: Compute Shared Secret for Bob
        KeyAgreement bobKeyAgreement = KeyAgreement.getInstance("DH");
        bobKeyAgreement.init(bobPrivateKey);
        bobKeyAgreement.doPhase(alicePublicKey, true);
        byte[] bobSharedSecret = bobKeyAgreement.generateSecret();

        // Convert public keys and shared secrets to integer representation for simplicity
        int alicePubKeyInt = alicePublicKey.hashCode() & 0xFF;  // Simulated small integer
        int bobPubKeyInt = bobPublicKey.hashCode() & 0xFF;      // Simulated small integer
        int aliceSecretInt = aliceSharedSecret[0] & 0xFF;
        int bobSecretInt = bobSharedSecret[0] & 0xFF;

        // Step 6: Display Results
        System.out.println("Public Key of Alice: " + alicePubKeyInt);
        System.out.println("Public Key of Bob: " + bobPubKeyInt);
        System.out.println("Shared Secret calculated by Alice: " + aliceSecretInt);
        System.out.println("Shared Secret calculated by Bob: " + bobSecretInt);

        // Verify if both shared secrets are the same
        if (aliceSecretInt == bobSecretInt) {
            System.out.println("The shared secret is successfully established!");
        } else {
            System.out.println("Shared secret mismatch! Key exchange failed.");
        }
    }
}
