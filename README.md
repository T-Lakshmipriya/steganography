# 🔐 Steganography Project

This project enables users to **hide (encode)** and **reveal (decode)** secret messages within image files using basic **LSB (Least Significant Bit)** steganography techniques. It's developed to explore digital privacy and secure communication using visual data.

---

## 📌 Features

- Hide secret text inside image files (PNG, JPG, etc.)
- Extract hidden messages from encoded images
- Simple command-line or GUI-based interface
- Optional password protection
- Easy-to-use and beginner-friendly codebase
- Future support for audio steganography

---

## 🛠️ Tech Stack

- **Language**: Java / Python  
- **Image Processing**:
  - Java: `BufferedImage`, `ImageIO`
  - Python: `PIL` (Pillow)
- **Interface**:
  - Java: `Swing`
  - Python: `Tkinter` or CLI
- **Optional**: Audio handling via `javax.sound.sampled` (Java) or `pydub` (Python)

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 17+ or Python 3.8+
- VS Code / IntelliJ / Eclipse (Java) or any Python IDE
- Pillow (for Python image support):  
  `pip install pillow`

### Run Instructions

**For Java:**

```bash
javac SteganographyApp.java
java SteganographyApp
