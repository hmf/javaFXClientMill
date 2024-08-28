<!--cSpell:ignore JRE, SDK, jmods, jlink -->
<!--cSpell:ignore JFX, Intellij -->

# javaFXClientMill

Examples of Scala using JavaFX as modules. It is now referred to as [OpenFX](https://openjfx.io/). 

## Installing Libraries

On order to use OpenJFX, one needs to install the libraries. The installation of the libraries depends on how they will be used. The libraries are usually [installed manually](https://gluonhq.com/products/javafx/). You can either download and use the JDK or the modules. You can the use these libraries either via a command line or using a build tool. [Using the command line](https://openjfx.io/openjfx-docs/#install-javafx) is cumbersome and error prone. It not only requires manually managing the libraries but also configuring long paths for command line use. Currently, plugins for [Maven](https://openjfx.io/openjfx-docs/#maven) and [Gradle](https://openjfx.io/openjfx-docs/#gradle) exist that allow one to configure the libraries with minimum effort. They are automatically downloaded with their dependencies and the module paths and names are transparently added to the compilation and execution command lines. Additional instructions are available that provide information on configuring several IDEs such as [IntelliJ](https://openjfx.io/openjfx-docs/#IDE-Intellij), [NetBeans](https://openjfx.io/openjfx-docs/#IDE-NetBeans), [Eclipse](https://openjfx.io/openjfx-docs/#IDE-Eclipse) and [Visual Studio Code](https://openjfx.io/openjfx-docs/#IDE-VSCode).

Note that [runtime images](https://openjfx.io/openjfx-docs/#modular) can also be used that provide a JDK with the modules and your code all wrapped into a single custom JRE. The [Maven](https://openjfx.io/openjfx-docs/#maven) and [Gradle](https://openjfx.io/openjfx-docs/#gradle) also provide a way to create this custom JRE. The instruction to manually do this are also provided. 

To execute a Java application that uses modules one can also use the [command lines directly](https://openjfx.io/openjfx-docs/#install-javafx). To do this it is necessary to either [manually download and install the SDK or `jmods`](https://gluonhq.com/products/javafx/). The SDK contains the java libraries (JAR) and the specific OS libraries. To compile and execute the application, one has to point the `--module-path` to the SDK `lib` folder. The build tool plugins essentially automate the download the correct libraries (version for the required OS) and configure the `--module-path` and `--add-modules` command line arguments that are also used during manual operations.

The [runtime images](https://openjfx.io/openjfx-docs/#modular) are created by using the previously compiled application using the SDK. The [jlink](https://docs.oracle.com/en/java/javase/22/docs/specs/man/jlink.html) command is then used to create a JRE that includes the required modules and can execute the application. To do this it is necessary to have the `jmods` libraries installed locally and the `--module-path $PATH_TO_FX_MODS:mods --add-modules application` arguments used, where `$PATH_TO_FX_MODS` points to the `jmods` folder. Note that this **custom JRE is platform-specific**. With `jlink` one can also prepare a custom JDK that includes all the required modules. This JDK can be used to compile and run the Java application with no need to add the `--module-path` (either via command line or in an IDE). 

[Mill](https://github.com/com-lihaoyi/mill) does not have plugins for importing and using Java modules of any library. Here we provide a build script that you can use to configure your project to easily setup and use the libraries. [Mill](https://mill-build.org) is flexible in that you can adapt this code to your needs. The code provided in the [`build.sc`](build.sc) script allows one to configure and use the JavaFX modules by simply extending an existing trait `OpenJFX`, which has code that encodes the required modules, automatically creates the module path and module names of the OpenJFX and [ControlsFX](https://github.com/controlsfx/controlsfx) modules that are loaded as libraries for the given Scala/Java module and overrides the Mill `forkArgs` tasks so that you can launch the application using these libraries. To extend the build script to new modules, one has to add the module names and respective filters so that only those libraries modules will be added to the command line arguments. 

It should also be possible to use [non-modular applications](https://openjfx.io/openjfx-docs/#modular), but initial tests failed, so this mode os use was ot made available.  

> NOTE: the [installation instructions](https://openjfx.io/openjfx-docs/#install-java) indicate that: *"If your system has multiple versions of JDK installed, you need to make sure that the JAVA_HOME environment variable points to correct JDK. JavaFX 21 needs at least JDK 17."* In ubuntu, the `update-java-alternatives` or `update-alternatives` commands take care of this automatically (No variable is created (`user@gnode:~$ env | grep -i time`)). 
Here are some examples of usage (we only need the second one):
> * update-java-alternatives --list
> * sudo update-java-alternatives -s java-1.21.0-openjdk-amd64
> * sudo update-alternatives --config java
> * sudo update-alternatives --config javac
> * sudo update-alternatives --config javadoc
> * sudo update-alternatives --config jarsigner
<!-- https://askubuntu.com/questions/315646/update-java-alternatives-vs-update-alternatives-config-java -->

## CAching the OS libraries

When executing the application, the following warning will appear:

> WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/user/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/22.0.2/libglass.so
> Loaded library /libglass.so from resource

When the application starts, it will attempt to extract the OS libraries as resource from the library original JAR. However, the OS libraries are found in a separate JAR that ahs the same base name extended with the OS name. For example the archive `javafx-graphics-22.0.2.jar` has a companion `javafx-graphics-22.0.2-linux.jar` that contains the OS libraries. When this happens, the OpenJFX library looks for and extracts the required libraries to a cache folder. For example, the `libjavafx_font_pango.so` is cached as follows:

```bash
user@node:~$ locate -i libjavafx_font_pango.so
/home/user/.openjfx/cache/13/libjavafx_font_pango.so
/home/user/.openjfx/cache/13.0.1/libjavafx_font_pango.so
/home/user/.openjfx/cache/13.0.2/libjavafx_font_pango.so
/home/user/.openjfx/cache/16/libjavafx_font_pango.so
/home/user/.openjfx/cache/17.0.2-ea/libjavafx_font_pango.so
/home/user/.openjfx/cache/22.0.2+4/amd64/libjavafx_font_pango.so
```

Notice how these libraries are cached according to the library's version. 

## Compiling and running examples

Clean the project to start over:

> ./mill -i clean



> ./mill -i hanSoloCharts.runMain hansolo.charts.LineChartTest



  // TODO: missing javafx-sdk
  // https://stackoverflow.com/questions/76683529/error-javafx-runtime-components-are-missing-and-are-required-to-run-this-appl
  // /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/22.0.2/


WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/user/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/22.0.2/libglass.so
Loaded library /libglass.so from resource



