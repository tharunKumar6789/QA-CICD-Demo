# Task Manager

A very small Flask web app for practicing CI/CD. You can add tasks and delete them.
Tasks are kept in memory, so restarting the server clears them.

## Project structure

```
task-manager/
    app.py              Flask app (routes + validation)
    templates/
        index.html      The single page
    static/
        style.css       Styles
    requirements.txt
    README.md
```

## Installation

Requires Python 3.9 or newer.

**Windows (PowerShell)**

```powershell
cd task-manager
python -m venv .venv
.venv\Scripts\Activate.ps1
pip install -r requirements.txt
```

**Linux / macOS**

```bash
cd task-manager
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
```

## Run the application

```
python app.py
```

Open http://127.0.0.1:5000 in your browser. Stop the server with `Ctrl+C`.

To change the host or port, set environment variables first:

- Windows: `$env:APP_HOST="0.0.0.0"; $env:APP_PORT="8080"; python app.py`
- Linux: `APP_HOST=0.0.0.0 APP_PORT=8080 python app.py`

## Verify Add and Delete

1. Open the page. You should see **"No tasks yet. Add your first task above."**
2. Type `Buy milk` in the input box and click **Add Task**. The task appears in the list
   and the empty message disappears.
3. Add a second task, for example `Write tests`. Both tasks are listed.
4. Click **Add Task** with the box empty (or only spaces). The error
   **"Task cannot be empty."** is shown and nothing is added.
5. Add `Buy milk` again. The error **"This task already exists."** is shown.
6. Click **Delete** next to `Buy milk`. It is removed; `Write tests` remains.
7. Delete the last task. The empty-list message is shown again.

## Validation rules

- Task cannot be empty or only spaces (surrounding spaces are trimmed).
- Task can be at most 100 characters.
- The same task cannot be added twice (case-insensitive).

## Element IDs for Selenium

| Element | ID |
|---|---|
| Page heading | `page-heading` |
| Add form | `task-form` |
| Task input | `task-input` |
| Add Task button | `add-task-button` |
| Error message | `error-message` |
| Task list | `task-list` |
| Task row | `task-<id>` |
| Task text | `task-text-<id>` |
| Delete button | `delete-task-<id>` |
| Empty-list message | `empty-message` |

`<id>` is a number assigned when the task is added: 1, 2, 3, and so on. Numbers are never
reused while the server is running.
