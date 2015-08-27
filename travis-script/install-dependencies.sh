#!/bin/bash

set -ev

# Install Opaml
wget https://github.com/ocaml/opam/releases/download/1.2.2/opam-1.2.2-x86_64-Linux -O opam
chmod ugo+x opam
./opam init -y --comp=4.01.0
eval `./opam config env`
./opam install -y extlib.1.5.4 atdgen.1.6.0 javalib.2.3.1 sawja.1.5.1

# Checkout Infer
git clone https://github.com/facebook/infer.git
cd infer/
git submodule update --init --recursive
# Compile Infer
make -C infer
export PATH=`pwd`/infer/bin:$PATH

cd ../

# Add more dependencies here
# ...