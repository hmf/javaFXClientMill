<!--cSpell:ignore JRE, SDK, jmods, jlink -->
<!--cSpell:ignore JFX, Intellij -->

# javaFXClientMill

Examples of Scala using JavaFX as modules. It is now referred to as [OpenFX](https://openjfx.io/). 

## Installing Libraries

On order to use OpenJFX, one needs to install the libraries. The installation of the libraries depends on how they will be used. The libraries are usually [installed manually](https://gluonhq.com/products/javafx/). You can either download and use the JDK or the modules. You can the use these libraries either via a command line or using a build tool. [Using the command line](https://openjfx.io/openjfx-docs/#install-javafx) is cumbersome and error prone. It not only requires manually managing the libraries but also configuring long paths for command line use. Currently, plugins for [Maven](https://openjfx.io/openjfx-docs/#maven) and [Gradle](https://openjfx.io/openjfx-docs/#gradle) exist that allow one to configure the libraries with minimum effort. They are automatically downloaded with their dependencies and the module paths and names are transparently added to the compilation and execution command lines. Additional instructions are available that provide information on configuring several IDEs such as [IntelliJ](https://openjfx.io/openjfx-docs/#IDE-Intellij), [NetBeans](https://openjfx.io/openjfx-docs/#IDE-NetBeans), [Eclipse](https://openjfx.io/openjfx-docs/#IDE-Eclipse) and [Visual Studio Code](https://openjfx.io/openjfx-docs/#IDE-VSCode).

Note that [runtime images](https://openjfx.io/openjfx-docs/#modular) can also be used that provide a JDK with the modules and your code all wrapped into a single custom JRE. The [Maven](https://openjfx.io/openjfx-docs/#maven) and [Gradle](https://openjfx.io/openjfx-docs/#gradle) also provide a way to create this custom JRE. The instruction to manually do this are also provided. 

To execute a Java application that uses modules one can also use the [command lines directly](https://openjfx.io/openjfx-docs/#install-javafx). To do this it is necessary to either [manually download and install the SDK or `jmods`](https://gluonhq.com/products/javafx/). The SDK contains the java libraries (JAR) and the specific OS libraries. To compile and execute the application, one has to point the `--module-path` to the SDK `lib` folder. The build tool plugins essentially automate the download the correct libraries (version for the required OS) and configure the `--module-path` and `--add-modules` command line arguments that are also used during manual operations.

The [runtime images](https://openjfx.io/openjfx-docs/#modular) are created by using the previously compiled application using the SDK. The [jlink](https://docs.oracle.com/en/java/javase/22/docs/specs/man/jlink.html) command is then used to create a JRE that includes the required modules and can execute the application. To do this it is necessary to have the `jmods` libraries installed locally and the `--module-path $PATH_TO_FX_MODS:mods --add-modules application` arguments used, where `$PATH_TO_FX_MODS` points to the `jmods` folder. Note that this **custom JRE is platform-specific**. With `jlink` one can also prepare a custom JDK that includes all the required modules. This JDK can be used to compile and run the Java application with no need to add the `--module-path` (either via command line or in an IDE). 

[Mill](https://github.com/com-lihaoyi/mill) does not have plugins for importing and using Java modules of any library. Here we provide a build script that you can use to configure your project to easily setup and use the libraries. [Mill](https://mill-build.org) is flexible in that you can adapt this code to your needs. Te code provided for the [`build.sc`](build.sc) allow one to configure and use the JavaFX modules by simply extending an existing trait `OpenJFX` that has code that encodes the required modules, automatically creates the module path and module names of the OpenJFX and [ControlsFX](https://github.com/controlsfx/controlsfx) modules that are loaded as libraries for the given Scala/Java module and overrides the Mill `forkArgs` tasks so that you can launch the application using these libraries.



library configuraton is done as usual.
Will be automnaticalyy loaded


Non-modular application 






wsetahe JDK or the mnodu


https://openjfx.io/openjfx-docs/#install-java

> Note: If your system has multiple versions of JDK installed, you need to make sure that the JAVA_HOME environment variable points to correct JDK. JavaFX 21 needs at least JDK 17. 


https://openjfx.io/openjfx-docs/#install-javafx




./mill -i hanSoloCharts.runMain hansolo.charts.LineChartTest



  // TODO: missing javafx-sdk
  // https://stackoverflow.com/questions/76683529/error-javafx-runtime-components-are-missing-and-are-required-to-run-this-appl
  // /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/22.0.2/


WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/22.0.2/libglass.so
Loaded library /libglass.so from resource

hmf@gandalf:~$ locate -i libjavafx_font_pango.so
/home/hmf/.openjfx/cache/13/libjavafx_font_pango.so
/home/hmf/.openjfx/cache/13.0.1/libjavafx_font_pango.so
/home/hmf/.openjfx/cache/13.0.2/libjavafx_font_pango.so
/home/hmf/.openjfx/cache/16/libjavafx_font_pango.so
/home/hmf/.openjfx/cache/17.0.2-ea/libjavafx_font_pango.so
/home/hmf/.openjfx/cache/22.0.2+4/amd64/libjavafx_font_pango.so


