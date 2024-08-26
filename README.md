<!--cSpell:ignore JRE -->
<!--cSpell:ignore JFX, Intellij -->

# javaFXClientMill

Examples of Scala using JavaFX. It is now referred to as [OpenFX](https://openjfx.io/). 

## Installing Libraries

On order to use OpenJFX, one needs to install the libraries. The installation of the libraries depends on how they will be used. The libraries are usually [installed manually](https://gluonhq.com/products/javafx/). You can either download and use the JDK or the modules. You can the use these libraries either via a command line or using a build tool. [Using the command line](https://openjfx.io/openjfx-docs/#install-javafx) is cumbersome and error prone. It not only requires manually managing the libraries but also configuring long paths for command line use. Currently, plugins for [Maven](https://openjfx.io/openjfx-docs/#maven) and [Gradle](https://openjfx.io/openjfx-docs/#gradle) exist that allow one to configure the libraries with minimum effort. They are automatically downloaded with their dependencies and the module paths and names are transparently added to the compilation and execution command lines. Additional instructions are available that provide information on configuring several IDEs such as [IntelliJ](https://openjfx.io/openjfx-docs/#IDE-Intellij), [NetBeans](https://openjfx.io/openjfx-docs/#IDE-NetBeans), [Eclipse](https://openjfx.io/openjfx-docs/#IDE-Eclipse) and [Visual Studio Code](https://openjfx.io/openjfx-docs/#IDE-VSCode).

Note that [runtime images](https://openjfx.io/openjfx-docs/#modular) can also be sued that provide a JDK with the modules and your code all wrapped into a single custom JRE. The [Maven](https://openjfx.io/openjfx-docs/#maven) and [Gradle](https://openjfx.io/openjfx-docs/#gradle) also provide a way to create this custom JRE. The instruction to manually do this are also provided. 

[Mill](https://github.com/com-lihaoyi/mill) does not have such a plugin. Here we provide a build script that you can use to configure your project to easily setup and use the libraries. [Mill](https://mill-build.org) is flexible in that you can adapt this code to your needs. Te code provided for the [`build.sc`](build.sc) allow one to configure and use the JavaFX modules by simply extending an existing traitr 
library configuraton is done as usual.
Will be automnaticalyy loaded







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


