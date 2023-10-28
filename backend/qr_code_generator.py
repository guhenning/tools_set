import qrcode
import os
import sys

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

# Save the image to a temporary location (you can adjust this)
image.save("qr_code.png")
