# Publishing BanglaGuru v2.0 to an Existing GitHub Repository

This guide assumes the GitHub repository has already been created.

## Step 1: Prepare the source

Extract `BanglaGuru-GitHub-Ready-v2.0.zip`. The extracted `BanglaGuru` directory contains the Android source, `README.md`, `.gitignore`, release notes, and the Mermaid ER diagram.

**Do not upload:** the original ZIP, private `.jks` key, `local.properties`, `.gradle/`, `.idea/`, generated `build/` directories, or the `.aab` bundle as a regular source file.

## Step 2: Push to the existing GitHub repository

The most reliable method is Git on your computer (Git Bash, PowerShell, or Terminal). Replace `YOUR_USERNAME` and `YOUR_REPO` with the existing GitHub repository information:

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPO.git
cd YOUR_REPO
```

Copy **the contents inside** the extracted `BanglaGuru` folder into this cloned repository (so that `app/`, `gradle/`, `README.md`, and `settings.gradle` are at the repository root). Then run:

```bash
git add .
git status
git commit -m "Add BanglaGuru v2.0 Android source and documentation"
git push origin HEAD
```

Check `git status` and the staging list to ensure no private key, credentials, compiled bundles, or generated files are staged.

If Git reports no changes to commit, the contents may already be present. If the remote repository already contains files, cloning first avoids overwriting its existing history.

## Step 3: Set repository details

On GitHub, open your repository and edit the **About** section:

**Description:**

BanglaGuru is an offline Bengali grammar and literature learning app for Android, featuring lessons, MCQ practice, mock tests, favorites, progress tracking, and a local Room database.

**Topics (one per entry):**

```text
android, android-app, java, bengali, bangla, education, exam-preparation, bengali-grammar, bengali-literature, mcq, android-jetpack, room-database, mvvm, offline-first
```

## Step 4: Publish the GitHub Release

1. In the repository, open **Releases > Draft a new release**.
2. Create the tag `v2.0` from the commit containing your v2.0 source.
3. Enter the title `BanglaGuru v2.0 - Offline Bengali Exam Preparation`.
4. Paste the contents of `RELEASE_NOTES_v2.0.md` into the release description.
5. Attach the supplied `BanglaGuru-v2.0-release.aab` from the separate release package.
6. Review and publish the release.

GitHub automatically provides source-code ZIP/TAR files for tagged releases. The AAB belongs in **Release assets**, not in the Git-tracked project files.

## Step 5: Important signing safeguards

Keep your original app signing `.jks` file and credentials in a secure private backup. Do not upload them to GitHub, including private repositories unless explicitly required and securely managed. A future Play Store update must follow your existing app-signing arrangement; a new key cannot always replace the original upload/signing key.

For the next release, increase `versionCode` above `2`, confirm the UI version label, and use Android Studio's **Generate Signed Bundle / APK** workflow.
