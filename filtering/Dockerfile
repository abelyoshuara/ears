# Use the official Python base image
FROM python:3.8

# Set the working directory in the container
WORKDIR /app

# Copy the requirements file and install dependencies
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the code files to the container
COPY filtering.ipynb .
COPY serviceaccountkey.json .

# Run the notebook using nbconvert
CMD ["jupyter", "nbconvert", "--execute", "--to", "notebook", "--output", "output.ipynb", "filtering.ipynb"]
