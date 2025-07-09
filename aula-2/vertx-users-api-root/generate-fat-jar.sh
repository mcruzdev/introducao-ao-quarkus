#!/bin/bash

mkdir -p tmp

cp -r out/* tmp/

for jar in lib/*.jar; do
  unzip -oq "$jar" -d tmp/
done

# o: overwrite
# q: quiet

jar cfe simple-api.jar tech.ada.Main -C tmp .

# c: estou dizendo que quero criar um arquivo jar
# f: especifica o nome do arquivo que vai ser criado
# e: define a classe principal, ou seja, a classe que contém o método psvm (public static void main (String ...args))

rm -rf tmp

