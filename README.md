<!--cSpell:ignore JRE, SDK, jmods, jlink -->
<!--cSpell:ignore JFX, Intellij -->

# javaFXClientMill

Examples of Scala using JavaFX as modules. It is now referred to as [OpenFX](https://openjfx.io/). Other examples include:

* TODO: https://www.tutorialspoint.com/javafx/javafx_charts.htm
* The [Hansolo charts library](https://github.com/HanSolo/charts)
* [Chart-Fx](https://github.com/fair-acc/chart-fx) TODO
* [TestFX](https://github.com/TestFX/TestFX) TODO
* TODO: https://github.com/brunomnsilva/JavaFXSmartGraph 

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

## Caching the OS libraries

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

After cleaning, all caches are also cleared, so when we execute the application, the `forkArgs` method is override to construct the command line arguments that include the libraries and modules. This methods will list all of the managed class paths that are used to identify the module names. It also lists which module libraries are detected and the corresponding module names. Note that the JavaFX/OpenFX library consists of several JARs and contains multiple modules. The `forkArgs` method can ne overridden. In several examples we do this to change the command line and print the full command line arguments that are used.  The following executes a simple example that shows a simple line chart. 

> `./mill -i hanSoloCharts.runMain hansolo.charts.LineChartTest`

## Samples

### Java Examples


The [HelloWorldJava/src/helloworld/HelloWorld.java](HelloWorldJava/src/helloworld/HelloWorld.java) shows how to start a OpenFX Java application using the [Mill HelloWorldJava module](build.sc#L355). Here is a list of possible commands:

```bash
$ ./mill -i HelloWorldJava.run
$ ./mill -i HelloWorldJava.runMain helloworld.HelloWorld
$ ./mill -i --watch HelloWorldJava.run
```

Note that the first command execute a default applications. A module may have several applications. To execute such a class explicitly indicate the class name sing the `runMain` command. Press the button to print a hello message to the console. 

Another option is to use to extend the `javafx.application.Application` class and use it indirectly. The [HelloWorldJava/src/button/Main.java](HelloWorldJava/src/button/Main.java) shows how to start a OpenFX Java application using the [Mill HelloWorldJava module](build.sc#L355). Here is a list of possible commands:

```bash
$ ./mill -i HelloWorldJava.runMain button.Main
$ ./mill -i --watch HelloWorldJava.runMain button.Main
```

> Note: although we use the same Mill mode, we are not running the default, so we must explicitly name the class. 

[`TestModule.Junit5`](build.sc#L336)
[`def ivyDeps = Agg(ivy"org.junit.jupiter:junit-jupiter-engine:5.11.0")`](build.sc#L348)


https://github.com/sbt/junit-interface
https://github.com/sbt/sbt-jupiter-interface


### Scala Examples

The [HelloWorldScala/src/helloworld/HelloWorld.scala](HelloWorldScala/src/helloworld/HelloWorld.scala) shows how to start a OpenFX Scala application using the [Mill HelloWorldScala module](build.sc#L365). Here is a list of possible commands:

```bash
 * ./mill -i HelloWorldScala.run
 * ./mill -i HelloWorldScala.runMain helloworld.HelloWorld
 * ./mill -i --watch HelloWorldScala.run
```

> **NOTE**: the application class cannot be called directly. It must be called as follows:

```scala
Application.launch(classOf[HelloWorld], args: _*)
```

Another option is to use to extend the `javafx.application.Application` class and use it indirectly. The [HelloWorldScala/src/button/Main.scala](HelloWorldScala/src/button/Main.scala) shows how to start a OpenFX Java application using the [Mill HelloWorldScala module](build.sc#L365). Here is a list of possible commands:

```bash
$ ./mill -i HelloWorldScala.runMain button.Main
$ ./mill -i --watch HelloWorldScala.runMain button.Main
```

An example of using an [MUnit](https://github.com/scalameta/munit) test is shown.  In this case we need to define a `test` module that is a mixin with [TestModule.Munit](build.sc#L380).

```bash
 $ ./mill -i HelloWorldScala.test
 $ ./mill -i HelloWorldScala.test.testLocal
 $ ./mill -i HelloWorldScala.test HelloWorld.PlotSpec.*
 $ ./mill -i HelloWorldScala.test HelloWorld.PlotSpec.hello // test("hello")
```


At this point, you should be able to code your own OpenFX/JavaFX applications ion Java or Scala using Mill. 


hanSoloCharts/src/LineChartTest.scala

 * ./mill -i hanSoloChartsStd.run
 * ./mill -i hanSoloChartsStd.runMain hansolo.charts.LineChartTest
 * ./mill -i --watch hanSoloChartsStd.runMain hansolo.charts.LineChartTest

hanSoloChartsStd/src/LineChartTest.scala

 * ./mill -i hanSoloCharts.run
 * ./mill -i hanSoloCharts.runMain hansolo.charts.LineChartTest
 * ./mill -i --watch hanSoloCharts.runMain hansolo.charts.LineChartTest

/home/hmf/VSCodeProjects/javaFXClientMill/HelloWorldJava/src/button/Main.java

 * ./mill -i HelloWorldJava.runMain button.Main
 * ./mill -i --watch HelloWorldJava.runMain button.Main

HelloWorldJava/test/src/PlotSpec.scala

 * ./mill -i HelloWorld.test
 * ./mill -i HelloWorld.test.testLocal
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.*
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.hello // test("hello")

HelloWorldScala/src/button/Main.scala

 * ./mill -i HelloWorldScala.runMain button.Main
 * ./mill -i --watch HelloWorldScala.runMain button.Main


HelloWorldScala/src/helloworld/HelloWorld.scala

 * ./mill -i HelloWorldScala.run
 * ./mill -i HelloWorldScala.runMain helloworld.HelloWorld
 * ./mill -i --watch HelloWorldScala.run

HelloWorldScala/test/src/PlotSpec.scala

 * ./mill -i HelloWorld.test
 * ./mill -i HelloWorld.test.testLocal
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.*
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.hello // test("hello")

modernClients/ch02-javafx_fundamentals/myshapes/src/main/java/org/modernclient/MyShapes.scala

 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapes.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapes.runMain org.modernclient.MyShapes
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapes.runMain org.modernclient.MyShapes

modernClients/ch02-javafx_fundamentals/myshapes2/src/main/java/org/modernclient/MyShapes2.scala
modernClients/ch02-javafx_fundamentals/myshapes2/src/main/java/org/modernclient/MyShapes2.scala

 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapes2.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapes2.runMain org.modernclient.MyShapes2
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapes2.runMain org.modernclient.MyShapes2

modernClients/ch02-javafx_fundamentals/myshapesfxml/src/main/java/org/modernclient/MyShapesFXML.scala
modernClients/ch02-javafx_fundamentals/myshapesfxml/target/classes/org/modernclient/MyShapesFXML.scala

 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapesfxml.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapesfxml.runMain org.modernclient.MyShapesFXML
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapesfxml.runMain org.modernclient.MyShapesFXML


modernClients/ch02-javafx_fundamentals/myshapesproperties/src/main/java/org/modernclient/MyShapesProperties.scala
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapesproperties.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapesproperties.runMain org.modernclient.MyShapesProperties
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapesproperties.runMain org.modernclient.MyShapesProperties


modernClients/ch02-javafx_fundamentals/myshapesproperties/src/main/java/org/modernclient/MyShapesProperties.scala
modernClients/ch02-javafx_fundamentals/myshapesproperties/target/classes/org/modernclient/MyShapesProperties.scala

 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapesproperties.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapesproperties.runMain org.modernclient.MyShapesProperties
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapesproperties.runMain org.modernclient.MyShapesProperties


modernClients/ch02-javafx_fundamentals/personui/src/main/java/com/modernclient/PersonUI.scala

 * ./mill -i modernClients.ch02-javafx_fundamentals.personui.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.personui.runMain com.modernclient.PersonUI
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.personui.runMain com.modernclient.PersonUI

modernClients/ch03-PropertiesBindings/arraychangeevent/src/main/java/org/modernclients/propertiesandbindings/ArrayChangeEventExample.scala

 * ./mill -i modernClients.ch03-PropertiesBindings.arraychangeevent.run
 * ./mill -i modernClients.ch03-PropertiesBindings.arraychangeevent.runMain org.modernclients.propertiesandbindings.ArrayChangeEventExample
 * ./mill -i --watch modernClients.ch03-PropertiesBindings.arraychangeevent.runMain org.modernclients.propertiesandbindings.ArrayChangeEventExample

modernClients/ch03-PropertiesBindings/numericproperties/src/main/java/org/modernclients/propertiesandbindings/NumericPropertiesExample.scala


 * ./mill -i modernClients.ch03-PropertiesBindings.numericproperties.run
 * ./mill -i modernClients.ch03-PropertiesBindings.numericproperties.runMain org.modernclients.propertiesandbindings.NumericPropertiesExample
 * ./mill -i --watch modernClients.ch03-PropertiesBindings.numericproperties.runMain org.modernclients.propertiesandbindings.NumericPropertiesExample

modernClients/ch04-javafx_controls/advanced/src/Advanced.scala

 * ./mill -i modernClients.ch04-javafx_controls.advanced.run
 * ./mill -i modernClients.ch04-javafx_controls.advanced.runMain org.modernclients.advanced.Advanced
 * ./mill -i --watch modernClients.ch04-javafx_controls.advanced.runMain org.modernclients.advanced.Advanced
 * 

modernClients/ch04-javafx_controls/basic/src/Basic.scala


 * ./mill -i modernClients.ch04-javafx_controls.basic.run
 * ./mill -i modernClients.ch04-javafx_controls.basic.runMain org.modernclients.controls.Basic
 * ./mill -i --watch modernClients.ch04-javafx_controls.basic.runMain org.modernclients.controls.Basic


/home/hmf/VSCodeProjects/javaFXClientMill/modernClients/ch04-javafx_controls/container/src/Container.scala

 * ./mill -i modernClients.ch04-javafx_controls.container.run
 * ./mill -i modernClients.ch04-javafx_controls.container.runMain org.modernclients.container.Container
 * ./mill -i --watch modernClients.ch04-javafx_controls.container.runMain org.modernclients.container.Container


modernClients/ch04-javafx_controls/dialog/src/Dialog.scala

 * ./mill -i modernClients.ch04-javafx_controls.dialog.run
 * ./mill -i modernClients.ch04-javafx_controls.dialog.runMain org.modernclients.dialog.Dialog
 * ./mill -i --watch modernClients.ch04-javafx_controls.dialog.runMain org.modernclients.dialog.Dialog

modernClients/ch04-javafx_controls/others/src/Others.scala


 * ./mill -i modernClients.ch04-javafx_controls.others.run
 * ./mill -i modernClients.ch04-javafx_controls.others.runMain org.modernclients.others.Others
 * ./mill -i --watch modernClients.ch04-javafx_controls.others.runMain org.modernclients.others.Others


modernClients/ch04-javafx_controls/popup/src/Popup.scala

 * ./mill -i modernClients.ch04-javafx_controls.popup.run
 * ./mill -i modernClients.ch04-javafx_controls.popup.runMain org.modernclients.popup.Popup
 * ./mill -i --watch modernClients.ch04-javafx_controls.popup.runMain org.modernclients.popup.Popup
 * 

modernClients/ch04-javafx_controls/selectionAndFocus/src/SelectionAndFocus.scala

 * ./mill -i modernClients.ch04-javafx_controls.selectionAndFocus.run
 * ./mill -i modernClients.ch04-javafx_controls.selectionAndFocus.runMain org.modernclients.selectionAndFocus.SelectionAndFocus
 * ./mill -i --watch modernClients.ch04-javafx_controls.selectionAndFocus.runMain org.modernclients.selectionAndFocus.SelectionAndFocus
 * 

modernClients/HelloModernWorld/src/sample/Main.scala

 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapes.runMain org.modernclient.MyShapes
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapes.runMain org.modernclient.MyShapes

modernClients/HelloWorldScala/src/button/Main.scala


 * ./mill -i modernClients.HelloWorldScala.runMain button.Main
 * ./mill -i --watch modernClients.HelloWorldScala.runMain button.Main

modernClients/HelloWorldScala/src/helloworld/HelloWorld.scala

 * ./mill -i modernClients.HelloWorldScala.run
 * ./mill -i modernClients.HelloWorldScala.runMain helloworld.HelloWorld
 * ./mill -i --watch modernClients.HelloWorldScala.run

modernClients/HelloWorldScala/test/src/PlotSpec.scala

 * ./mill -i HelloWorld.test
 * ./mill -i HelloWorld.test.testLocal
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.*
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.hello // test("hello")
