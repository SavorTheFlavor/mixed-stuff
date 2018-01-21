import os


def create_dir(directory):
    if not os.path.exists(directory):
        os.mkdir(directory)

def write_file(path, data):
    with open(path,'w') as f:
        f.write(data)