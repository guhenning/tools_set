import string
import secrets
import sys


def generate_password(length=12, max_length=25):
    symbols = "&*%$#@!"
    characters = string.ascii_letters + string.digits + symbols
    length = min(length, max_length)  # Ensure length doesn't exceed max_length
    secure_password = "".join(secrets.choice(characters) for _ in range(length))
    return secure_password


def generate_passwords(num_passwords, password_length):
    passwords = []
    for i in range(num_passwords):
        password = generate_password(password_length)
        passwords.append(password)
    return passwords


def main():
    if len(sys.argv) != 3:
        print("Usage: python password_generator.py <num_passwords> <password_length>")
        return

    num_passwords = int(sys.argv[1])
    password_length = int(sys.argv[2])

    passwords = generate_passwords(num_passwords, password_length)
    for i, password in enumerate(passwords, start=1):
        print(f"Random Password {i}: {password}")


if __name__ == "__main__":
    main()
