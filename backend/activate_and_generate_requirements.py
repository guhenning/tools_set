import subprocess


def activate_and_generate_requirements():
    subprocess.run(
        "python activate_venv.py && pip freeze > requirements.txt", shell=True
    )


if __name__ == "__main__":
    activate_and_generate_requirements()
