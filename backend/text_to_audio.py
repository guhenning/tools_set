from gtts import gTTS
import json
import os
import sys

# Parse input arguments
input_text = sys.argv[1]
selected_language = sys.argv[2]

# Load language info from the JSON file
with open(
    "C:\\Users\\gusta\\Documents\\IntelliJ Projects\\codingtime\\backend\\languages.json",
    "r",
    encoding="utf-8",
) as file:
    language_info = json.load(file)

# Find the language information based on the selected language
language_code = ""
domain = ""
for key, info in language_info.items():
    if info["language_name"] == selected_language:
        language_code = info["language_code"]
        domain = info["domain"]
        break

if language_code == "":
    print(f"Invalid language selection: {selected_language}")
    sys.exit(1)

# Create the audio file
try:
    text_to_convert = gTTS(text=input_text, lang=language_code, tld=domain, slow=False)

    # Determine the download folder based on the operating system
    download_folder = os.path.expanduser("~")  # Default home directory

    if os.name == "posix":  # macOS or Linux
        download_folder = os.path.join(download_folder, "Downloads")
    elif os.name == "nt":  # Windows
        download_folder = os.path.join(download_folder, "Downloads")
    else:
        print("Unsupported operating system. Download folder not set.")
        sys.exit(1)

    # Create the download folder if it doesn't exist
    os.makedirs(download_folder, exist_ok=True)

    audio_output = "speech.mp3"

    # Save Audio in the Download folder
    download_path = os.path.join(download_folder, audio_output)
    text_to_convert.save(download_path)

    print(f"Speech downloaded to: {download_path}")
except Exception as e:
    print(f"Error while generating speech: {e}")
    sys.exit(1)
