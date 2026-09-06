import os
import html
import time
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler

PROJECT_DIR = "."
OBSIDIAN_FILE = r"E:\Program Files\Obsidian\mods\Mods\All Code.md"
EXTENSIONS = {'.java', '.json', '.gradle', '.kt', '.toml', '.mcmeta'}
IGNORE_DIRS = {'build', '.gradle', 'run', '.git', '.idea', '.vscode'}

def get_lang(ext):
    return {'java': 'java', 'json': 'json', 'gradle': 'groovy', 'kt': 'kotlin', 'toml': 'toml'}.get(ext[1:], '')

def export_code():
    with open(OBSIDIAN_FILE, 'w', encoding='utf-8') as md:
        md.write("# Код моего Minecraft мода\n\n---\n\n")
        
        for root, dirs, files in os.walk(PROJECT_DIR):
            dirs[:] = [d for d in dirs if d not in IGNORE_DIRS]
            
            for file in files:
                if os.path.splitext(file)[1] in EXTENSIONS:
                    filepath = os.path.join(root, file)
                    rel_path = os.path.relpath(filepath, PROJECT_DIR).replace('\\', '/')
                    lang = get_lang(os.path.splitext(file)[1])
                    
                    md.write(f"## {rel_path}\n\n")
                    md.write(f'<pre><code class="language-{lang}">\n')
                    try:
                        with open(filepath, 'r', encoding='utf-8') as f:
                            content = f.read()
                            content = html.escape(content)
                            md.write(content)
                    except Exception:
                        md.write("/* Ошибка чтения файла */")
                    md.write("\n</code></pre>\n\n---\n\n")
    
    print(f"✅ Обновлено: {time.strftime('%H:%M:%S')}")

class CodeChangeHandler(FileSystemEventHandler):
    def on_modified(self, event):
        if event.is_directory:
            return
        if os.path.splitext(event.src_path)[1] in EXTENSIONS:
            time.sleep(0.5)  # Ждем завершения сохранения
            export_code()
    
    def on_created(self, event):
        if not event.is_directory and os.path.splitext(event.src_path)[1] in EXTENSIONS:
            time.sleep(0.5)
            export_code()
    
    def on_deleted(self, event):
        if not event.is_directory and os.path.splitext(event.src_path)[1] in EXTENSIONS:
            time.sleep(0.5)
            export_code()

if __name__ == "__main__":
    print("👀 Слежение за изменениями кода...")
    print(f"📁 Папка: {os.path.abspath(PROJECT_DIR)}")
    print(f"📄 Файл Obsidian: {OBSIDIAN_FILE}")
    print("Нажмите Ctrl+C для остановки\n")
    
    export_code()  # Первоначальный экспорт
    
    event_handler = CodeChangeHandler()
    observer = Observer()
    observer.schedule(event_handler, PROJECT_DIR, recursive=True)
    observer.start()
    
    try:
        while True:
            time.sleep(1)
    except KeyboardInterrupt:
        observer.stop()
    observer.join()