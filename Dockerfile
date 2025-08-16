FROM ubuntu:latest
LABEL authors="kakje"

ENTRYPOINT ["top", "-b"]