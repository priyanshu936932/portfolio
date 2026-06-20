package com.priyanshu.portfolio.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private static final String TEST_SECRET =
            "test-secret-key-used-only-in-unit-tests-must-be-long-enough-for-hs256-1234567890";

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Inject the secret that @Value would normally provide from application properties
        ReflectionTestUtils.setField(jwtService, "secretKey", TEST_SECRET);
    }

    // --- token generation + extraction round-trip ---

    @Test
    void generateToken_thenExtractEmail_returnsOriginalEmail() {
        String email = "user@example.com";

        String token = jwtService.generateToken(email);
        String extracted = jwtService.extractEmail(token);

        assertThat(extracted).isEqualTo(email);
    }

    @Test
    void generateToken_producesNonEmptyString() {
        String token = jwtService.generateToken("test@example.com");
        assertThat(token).isNotBlank();
    }

    // --- isTokenValid ---

    @Test
    void isTokenValid_returnsTrueForFreshTokenWithMatchingEmail() {
        String email = "valid@example.com";
        String token = jwtService.generateToken(email);

        assertThat(jwtService.isTokenValid(token, email)).isTrue();
    }

    @Test
    void isTokenValid_returnsFalseWhenEmailDoesNotMatchTokenSubject() {
        String token = jwtService.generateToken("real@example.com");

        assertThat(jwtService.isTokenValid(token, "attacker@example.com")).isFalse();
    }

    // --- malformed token ---

    @Test
    void extractEmail_throwsForCompletelyMalformedToken() {
        assertThatThrownBy(() -> jwtService.extractEmail("not.a.jwt"))
                .isInstanceOf(Exception.class);
    }

    @Test
    void extractEmail_throwsForEmptyString() {
        assertThatThrownBy(() -> jwtService.extractEmail(""))
                .isInstanceOf(Exception.class);
    }

    @Test
    void extractEmail_throwsForTokenSignedWithDifferentKey() {
        // A token signed with a different secret key should fail signature verification.
        String foreignToken = "eyJhbGciOiJIUzI1NiJ9"
                + ".eyJzdWIiOiJoYWNrZXJAZXhhbXBsZS5jb20ifQ"
                + ".FAKE_SIGNATURE_XXXXXXXXXXXXXXXXXXXXXXXXXXXX";

        assertThatThrownBy(() -> jwtService.extractEmail(foreignToken))
                .isInstanceOf(Exception.class);
    }
}
