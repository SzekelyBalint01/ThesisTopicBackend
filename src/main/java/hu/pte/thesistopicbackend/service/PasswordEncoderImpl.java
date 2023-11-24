package hu.pte.thesistopicbackend.service;

import hu.pte.thesistopicbackend.model.User;
import hu.pte.thesistopicbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;


@Service
public class PasswordEncoderImpl implements PasswordEncoder {

    private final UserRepository userRepository;


    public PasswordEncoderImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean auth(String password, String email) throws FileNotFoundException {
        // Check if company exists or not
        User user = userRepository.findByEmail(email).orElseThrow(() -> new FileNotFoundException());

        // Retrieve the salt and hashed password from the user record
        byte[] salt = user.getPasswordSalt();
        byte[] hashedPassword = user.getPasswordHash();

        // Generate a new hash of the entered password with the retrieved salt
        byte[] enteredPasswordHash = hashPassword(password, salt);

        // Compare the two hashes
        return Arrays.equals(enteredPasswordHash, hashedPassword);
    }

    protected Map<String, byte[]> hashPassword(String password) {
        byte[] salt = generateSalt();
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] saltedPassword = Arrays.copyOf(passwordBytes, passwordBytes.length + salt.length);
        System.arraycopy(salt, 0, saltedPassword, passwordBytes.length, salt.length);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(saltedPassword);

        Map<String, byte[]> result = new HashMap<>();
        result.put("salt", salt);
        result.put("hash", hash);
        return result;
    }

    protected byte[] hashPassword(String password, byte[] loginSalt) {
        byte[] salt = loginSalt;
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] saltedPassword = Arrays.copyOf(passwordBytes, passwordBytes.length + salt.length);
        System.arraycopy(salt, 0, saltedPassword, passwordBytes.length, salt.length);

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(saltedPassword);


        return hash;
    }


    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return false;
    }

}