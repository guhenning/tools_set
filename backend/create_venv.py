import subprocess


def create_venv():
    subprocess.run("python -m venv venv", shell=True)


if __name__ == "__main__":
    create_venv()
