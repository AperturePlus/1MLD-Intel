package xenosoft.imldintelligence.module.license.internal.service;

import java.nio.file.Path;
import java.security.PublicKey;

/**
 * Provides the cryptographic primitives required by license verification and upgrade authorization.
 */
public interface CryptoService {
    /**
     * Loads an RSA public key from the supplied file path.
     *
     * @param publicKeyPath path to the public key file used for signature verification
     * @return parsed RSA public key
     * @throws IllegalStateException if the file does not exist or cannot be parsed as an RSA public key
     */
    PublicKey loadRsaPublicKey(Path publicKeyPath);

    /**
     * Verifies a SHA-256 with RSA signature for a canonicalized payload.
     *
     * @param payload payload text that was originally signed
     * @param signatureBase64 Base64-encoded signature value
     * @param publicKey RSA public key used to verify the signature
     * @return {@code true} when the signature is valid; otherwise {@code false}
     */
    boolean verifySha256WithRsa(String payload, String signatureBase64, PublicKey publicKey);

    /**
     * Computes a lowercase hexadecimal SHA-256 digest for the provided input.
     *
     * @param input source text to hash
     * @return SHA-256 digest encoded as hexadecimal text
     */
    String sha256Hex(String input);
}
