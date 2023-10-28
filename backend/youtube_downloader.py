import argparse
from pytube import YouTube
import os


def download_video(yt_url):
    # Get video title
    video_to_download = YouTube(yt_url)
    title = video_to_download.title
    print(f"Video title: {title}")

    # Get the stream with the highest resolution
    stream = video_to_download.streams.get_highest_resolution()

    # Determine the download folder based on the operating system
    download_folder = os.path.expanduser("~")  # Default home directory

    if os.name == "posix":  # macOS or Linux
        download_folder = os.path.join(download_folder, "Downloads")
    elif os.name == "nt":  # Windows
        download_folder = os.path.join(download_folder, "Downloads")
    else:
        print("Unsupported operating system. Download folder not set.")

    print(f"Download folder: {download_folder}")

    # Create the download folder if it doesn't exist
    os.makedirs(download_folder, exist_ok=True)

    # Download the video to the specified path
    stream.download(output_path=download_folder)

    print(f"Video '{title}' has been downloaded to {download_folder}")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Download YouTube video.")
    parser.add_argument("url", help="YouTube video URL")

    args = parser.parse_args()
    download_video(args.url)
