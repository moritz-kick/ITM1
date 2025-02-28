import os


def list_files(directory, output_file):
    with open(output_file, "w", encoding="utf-8") as f:
        for root, dirs, files in os.walk(directory):
            if "node_modules" in root.split(os.sep):
                continue

            rel_path = os.path.relpath(root, directory)
            f.write(f"[{rel_path if rel_path != '.' else 'root'}]\n")
            for file in files:
                if file == "package-lock.json":
                    continue
                file_path = os.path.join(root, file)
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
