CREATE TABLE profile (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    linkedin_url VARCHAR(500),
    github_url VARCHAR(500),
    bio TEXT,
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO profile (full_name, email, phone, linkedin_url, github_url, bio)
VALUES (
    'Priyanshu Mishra',
    'priyanshumisra3141@gmail.com',
    '+91-9369324363',
    'https://www.linkedin.com/in/priyanshu-mishra-6a1011220/',
    'https://github.com/priyanshu936932',
    'Full-stack developer building with Java, Spring Boot, and Vue.js'
);