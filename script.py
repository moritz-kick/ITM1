import os


def is_binary_file(file_path):
    """Prüft, ob eine Datei binär ist."""
    try:
        with open(file_path, "rb") as f:
            chunk = f.read(1024)
            return (
                b"\x00" in chunk
            )  # Prüft auf Nullbytes, ein Indikator für Binärdateien
    except Exception:
        return True  # Falls die Datei nicht gelesen werden kann, als binär betrachten


def list_files(directory, output_file):
    with open(output_file, "w", encoding="utf-8") as f:
        for root, dirs, files in os.walk(directory):
            if "node_modules" in root.split(os.sep) or ".git" in root.split(os.sep):
                continue

            rel_path = os.path.relpath(root, directory)
            f.write(f"[{rel_path if rel_path != '.' else 'root'}]\n")

            for file in files:
                if file.endswith(".class") or file in {
                    "package-lock.json",
                    "java_jni_digital_input",
                    ".gitignore",
                }:
                    continue

                file_path = os.path.join(root, file)

                if is_binary_file(file_path):
                    f.write(f"- {file} (Binärdatei, übersprungen)\n")
                    continue

                try:
                    with open(
                        file_path, "r", encoding="utf-8", errors="ignore"
                    ) as file_content:
                        content = file_content.read()
                    f.write(f"- {file}\n")
                    f.write(f"  Inhalt:\n{content}\n\n")
                except Exception as e:
                    f.write(f"- {file} (Fehler beim Lesen: {e})\n")
            f.write("\n")


if __name__ == "__main__":
    current_directory = os.getcwd()  # Aktuelles Verzeichnis
    output_file = os.path.join(current_directory, "file_list.txt")
    list_files(current_directory, output_file)
    print(f"Dateiliste wurde in {output_file} gespeichert.")
