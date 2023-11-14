import os
import platform
import subprocess


def activate_virtual_environment():
    system_platform = platform.system()

    if system_platform == "Windows":
        activate_script = os.path.join("venv", "Scripts", "activate")
    elif system_platform == "Linux":
        activate_script = os.path.join("venv", "bin", "activate")
    else:
        print("Unsupported operating system")
        return

    if system_platform == "Windows":
        subprocess.run(["cmd", "/k", activate_script], shell=True)
    elif system_platform == "Linux":
        subprocess.run(["bash", "-c", f"source {activate_script}"], shell=True)


if __name__ == "__main__":
    activate_virtual_environment()
