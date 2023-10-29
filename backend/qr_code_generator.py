import qrcode
import os
import sys

from PIL import Image

if len(sys.argv) != 2:
    print("Usage: python qr_code_generator.py <data>")
    sys.exit(1)

data = sys.argv[1]

# Generate QR Code
qr = qrcode.QRCode(version=1, box_size=10, border=4)
qr.add_data(data)
qr.make(fit=True)

# Generate image from QR Code
image = qr.make_image(fill="black", back_color="white")

# Save image to download path
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

# Generate a unique filename by checking for existing files
base_filename = "qr_code.png"
counter = 0
while True:
    filename = base_filename if counter == 0 else f"qr_code_{counter}.png"
    full_path = os.path.join(download_folder, filename)
    if not os.path.exists(full_path):
        break
    counter += 1

image.save(full_path)

print(f"QR Code has been downloaded to {full_path}")
