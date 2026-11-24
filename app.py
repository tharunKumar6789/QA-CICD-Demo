"""A very small Task Manager built with Flask.

Tasks are stored in memory, so they are cleared when the server restarts.
"""
import os

from flask import Flask, redirect, render_template, request, url_for

app = Flask(__name__)

MAX_TASK_LENGTH = 100

# In-memory storage: a list of {"id": int, "title": str}
tasks = []
next_id = 1


def validate_task(title):
    """Return an error message if the task title is invalid, otherwise None."""
    if not title:
        return "Task cannot be empty."
    if len(title) > MAX_TASK_LENGTH:
        return f"Task cannot be longer than {MAX_TASK_LENGTH} characters."
    if any(task["title"].lower() == title.lower() for task in tasks):
        return "This task already exists."
    return None


@app.route("/")
def index():
    return render_template("index.html", tasks=tasks, max_length=MAX_TASK_LENGTH)


@app.route("/add", methods=["POST"])
def add_task():
    global next_id
    title = request.form.get("task", "").strip()

    error = validate_task(title)
    if error:
        # Re-show the page with the error and keep what the user typed.
        return render_template(
            "index.html", tasks=tasks, max_length=MAX_TASK_LENGTH,
            error=error, task_text=title,
        ), 400

    tasks.append({"id": next_id, "title": title})
    next_id += 1
    return redirect(url_for("index"))


@app.route("/delete/<int:task_id>", methods=["POST"])
def delete_task(task_id):
    for task in tasks:
        if task["id"] == task_id:
            tasks.remove(task)
            break
    return redirect(url_for("index"))


if __name__ == "__main__":
    host = os.environ.get("APP_HOST", "127.0.0.1")
    port = int(os.environ.get("APP_PORT", "5000"))
    app.run(host=host, port=port)
