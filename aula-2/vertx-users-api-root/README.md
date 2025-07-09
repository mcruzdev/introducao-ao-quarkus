# Aula 2

Nessa aula aprenderemos sobre Maven, Jackson e Lombok.

## Fazendo tudo na mão

Vamos voltar um pouco no tempo, e vamos ver mais ou menos como eram as coisas sem o Maven.

Uma coisa é certa a gente não vai reescrever todas as coisas do zero, a gente sempre usa código de outros programadores. Na pasta `lib` a gente tem uma série de arquivos [JAR](https://docs.oracle.com/javase/8/docs/technotes/guides/jar/jarGuide.html)  

## Java

Vamos criar nosso primeiro programa Java.

1. Vamos criar nossa classe Java:

```java

public class Main {

    public static void main(String ...args) {
        System.out.println("Hello friend");
    }

}
```

2. Vamos compilar ela:

```bash
javac Main.java
```

3. E executar:

```bash
java Main
```

## E em uma aplicação mais complexa?

> [!NOTE]
> Eu utilizei https://get-coursier.io para baixar todas as dependências do `io.vertx:vertx-web:4.5.16`.
> Observação: Eu baixei a versão `vertx-web` ao invés do `vertx-core`, por que o `vertx-web` contém o `vertx-core` e eu dependo das classes que estão dentro do `vertx-web`.
>
> O comando para baixar está aqui embaixo: 
> ```shell
>  cs fetch io.vertx:vertx-web:4.5.16 --classpath | tr ':' '\n' | while read jar; do cp "$jar" lib/; done    
>

1. Vamos buildar 

```shell
javac -cp "lib/*" -d out tech/ada/Main.java
```

2. Vamos executar

```shell
java -cp "lib/*:out" tech.ada.Main
```

## Conclusão

Nós vimos o quão difícil e complicado é gerenciar as dependências e buildar nossa aplicação, e olha que a gente nem adicionou testes e outras funcionalidades que um gerenciador de dependências e build faz pra gente.