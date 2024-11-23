<!-- cSpell:ignore JRE, SDK, jmods, jlink -->
<!-- cSpell:ignore JFX, Intellij -->
<!-- cSpell:ignore numericproperties, modernclients, propertiesandbindings, arraychangeevent, personui -->
<!-- cSpell:ignore myshapesfxml -->

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

After cleaning, all caches are also cleared, so when we execute the application, the `forkArgs` method, which is overridden to construct the command line arguments that include the libraries and modules, is also executed. This methods will list all of the managed class paths that are used to identify the module names. It also lists which module libraries are detected and the corresponding module names. Note that the JavaFX/OpenFX library consists of several JARs and contains multiple modules. The `forkArgs` method of your module class that extends the `OpenJFX` can be overridden according to your requirements. In several examples we do this to change and print the full command line arguments that are used. The following executes an example that shows a simple line chart. 

> `./mill -i hanSoloCharts.runMain hansolo.charts.LineChartTest`

## Samples

### Java Examples

The [HelloWorldJava/src/helloworld/HelloWorld.java](HelloWorldJava/src/helloworld/HelloWorld.java) shows how to start a OpenFX Java application using the [Mill HelloWorldJava module](build.sc#L355). Here is a list of possible commands:

```bash
$ ./mill -i HelloWorldJava.run
$ ./mill -i HelloWorldJava.runMain helloworld.HelloWorld
$ ./mill -i --watch HelloWorldJava.run
```

Press the button to print a hello message to the console. Note that the first command executes the default applications. A module may have several applications. To execute one of these applications, explicitly indicate the class name using the `runMain` command.  

Another option is to use to extend the `javafx.application.Application` class and use it indirectly. The [HelloWorldJava/src/button/Main.java](HelloWorldJava/src/button/Main.java) shows how to start a OpenFX Java application using the [Mill HelloWorldJava module](build.sc#L355). Here is a list of possible commands:

```bash
$ ./mill -i HelloWorldJava.runMain button.Main
$ ./mill -i --watch HelloWorldJava.runMain button.Main
```

> Note: although we use the same Mill module, we are not running the default application, so we must explicitly name the class. 

We can also [setup Java test modules](https://mill-build.org/mill/Java_Build_Examples.html#_java_module_with_test_suite). Mill documentation include [examples](https://mill-build.org/mill/Java_Build_Examples.html). Here we have an example that uses the base [`TestModule.Junit5`](build.sc#L336). To provide a means to execute [Junit5](https://junit.org/junit5) test suites, an implementation of sbt's test interface for [JUnit Jupiter](https://github.com/sbt/sbt-jupiter-interface) must be included in the project. The library is added in the build script as shown next:

[`def ivyDeps = Agg(ivy"org.junit.jupiter:junit-jupiter-engine:5.11.0")`](build.sc#L348)

For JUnit 4, the [SBT Junit interface](https://github.com/sbt/junit-interface) can be used instead. The test example[HelloWorldJava/jtest/src/helloworld/PlotSpec.java](HelloWorldJava/jtest/src/helloworld/PlotSpec.java) can be executed with the following commands:

```shell
./mill -i HelloWorldJava.jtest
./mill -i HelloWorldJava.jtest.testLocal
./mill -i HelloWorldJava.jtest --tests=hello.*
./mill -i HelloWorldJava.jtest --tests=he.*o.* 
```

Unlike equivalent scala test frameworks, when using Mill we can only match test on names and not the class path. SBT already has support for selecting test suites via class paths, which we don't have im Mill. One can however use the Scala test frameworks to test Java code and reap benefits from their class path selection capabilities. The test is designed to fail. Here is an example of the result:

```shell
[72/72] HelloWorldJava.jtest.test 
Test run started (JUnit Jupiter)
Test #hello() started
Test helloworld.PlotSpec.hello failed: org.opentest4j.AssertionFailedError: expected: <2> but was: <1>, took 0.034s
    at helloworld.PlotSpec.hello(PlotSpec.java:30)
    at java.lang.reflect.Method.invoke(Method.java:580)
    at java.util.ArrayList.forEach(ArrayList.java:1596)
    at java.util.ArrayList.forEach(ArrayList.java:1596)
Test  finished, took 0.058s
Test  finished, took 0.088s
Test run finished: 1 failed, 0 ignored, 1 total, 0.123s
1 targets failed
HelloWorldJava.jtest.test 1 tests failed: 
  helloworld.PlotSpec hello()
```

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

An example of using an [MUnit](https://github.com/scalameta/munit) test is shown. In this case we need to define a `test` module that is a mixin with [TestModule.Munit](build.sc#L380). The list of commands below show how one can execute all test suites or select a test suite or a single test using MUnit:

```bash
 $ ./mill -i HelloWorldScala.test
 $ ./mill -i HelloWorldScala.test.testLocal
 $ ./mill -i HelloWorldScala.test HelloWorld.PlotSpec.*
 $ ./mill -i HelloWorldScala.test HelloWorld.PlotSpec.hello
```

### OpenFX Scala Examples

At this point, you should be able to code your own OpenFX/JavaFX applications in Java or Scala using Mill. Th next set of examples focuses on the use of OpenFX (previously JavaFX). I found the [github examples](https://github.com/Apress/definitive-guide-modern-java-clients-javafx17) of the book [The Definitive Guide to Modern Java Clients with JavaFX 17: Cross-Platform Mobile and Cloud Development](https://link.springer.com/book/10.1007/978-1-4842-7268-8). An [alternative Github repo](https://github.com/Apress/definitive-guide-modern-java-clients-javafx) was used to get the code. This seems to be an older unmaintained version. 

We start off with a very simple Scala application [`HelloModernWorld`](modernClients/HelloModernWorld/src/sample/Main.scala). The application module is in fact a **submodule** with the path [modernClients.HelloModernWorld](build.sc#L391). Mill allows us to have modules within modules, which helps us more easily organize our code. 

In this example the application creates a window that shows an image, which is loaded via a Java resource file. In this case, the resources is the single [`sample.fxml`](modernClients/HelloModernWorld/resources/sample.fxml) file that encodes a GUI, which is created with the [Gluon interactive scene builder](https://github.com/gluonhq/scenebuilder). When loaded using the JavaFX loader, the resource file is interpreted and the GUI is automatically generated. In this case, an [image](modernClients/HelloModernWorld/resources/1024px-ISS-RapidScat_nadir_adapter_removed_from_CRS-4_Dragon_trunk_(ISS041E049097).jpg) is shown. Here are the commands to execute the simple application:

```bash
 $ ./mill -i modernClients.HelloModernWorld.run
 $ ./mill -i modernClients.HelloModernWorld.runMain sample.Main
 $ ./mill -i --watch modernClients.HelloModernWorld.runMain sample.Main
```

Note that we have only one application class, so we have set the default runtime class to `sample.Main`. No tests are provided for this example. 

The [modernClients/HelloWorldScala/src/helloworld/HelloWorld.scala](modernClients/HelloWorldScala/src/helloworld/HelloWorld.scala) example is just used for checking the setup (module [modernClients.HelloWorldScala](build.sc#404)). You can execute this application with the commands:

```bash
 $ ./mill -i modernClients.HelloWorldScala.run
 $ ./mill -i modernClients.HelloWorldScala.runMain helloworld.HelloWorld
 $ ./mill -i --watch modernClients.HelloWorldScala.run
```

The next set of examples were found in the [`modernClients.ch02-javafx_fundamentals` module](build.sc#417). Under this 
module we have a set of modules that show the basic functionality generating elements or loading them from resources 
to be shown visually. We will succinctly describe these below.

The first example is [`myshapes`](build.sc#418) module that has the class [MyShapes](modernClients/ch02-javafx_fundamentals/myshapes/src/modernclient/MyShapes.scala) that creates an ellipse with text inside it. The following commands can be used to run the application:

```bash
$ ./mill -i modernClients.ch02-javafx_fundamentals.myshapes.run
$ ./mill -i modernClients.ch02-javafx_fundamentals.myshapes.runMain org.modernclient.MyShapes
$ ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapes.runMain org.modernclient.MyShapes
```

The [`MyShapes2`](modernClients/ch02-javafx_fundamentals/myshapes2/src/modernclient/MyShapes2.scala) extends the previous 
example with special visual effects (reflection) and animation (rotation). To make the ellipse rotate, simply
click on the pane. The application is configured in the [`myshapes2`](build.sc#430) module and can be executed
with the following commands:

```bash
$ ./mill -i modernClients.ch02-javafx_fundamentals.myshapes2.run
$ ./mill -i modernClients.ch02-javafx_fundamentals.myshapes2.runMain org.modernclient.MyShapes2
$ ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapes2.runMain org.modernclient.MyShapes2
```

The [`myshapesproperties`](build.sc#442) configures the [`MyShapesProperties`](modernClients/ch02-javafx_fundamentals/myshapesproperties/src/modernclient/MyShapesProperties.scala) application. The `MyShapesProperties` class
extends the previous class with property listeners that change according the the property values. When
rotating the text and its color is changed. The application can be executed with the following commands:

```bash
$ ./mill -i modernClients.ch02-javafx_fundamentals.myshapesproperties.run
$ ./mill -i modernClients.ch02-javafx_fundamentals.myshapesproperties.runMain org.modernclient.MyShapesProperties
$ ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapesproperties.runMain org.modernclient.MyShapesProperties
```

> Note: the commented sections of the code show alternate coding styles for the property changes.

The [myshapesfxml](build.sc#462) module configures the [`MyShapesFXML`](modernClients/ch02-javafx_fundamentals/myshapesfxml/src/modernclient/MyShapesFXML.scala) application. This application has the same functionality as the application
above. However, the GUI description is stored in the [`Scene.fxml`](modernClients/ch02-javafx_fundamentals/myshapesfxml/resources/fxml/Scene.fxml) file, which is loaded parsed and used to create the GUI on the fly. Note that the variables
that hold state and are used in the XML scene description, are declared in the code. They are linked to the
GUI via the [`@FXML` annotation](https://dev.java/learn/javafx/fxml/). The application can be executed
with the following commands:

```bash
$ ./mill -i modernClients.ch02-javafx_fundamentals.myshapesfxml.run
$ ./mill -i modernClients.ch02-javafx_fundamentals.myshapesfxml.runMain org.modernclient.MyShapesFXML
$ ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapesfxml.runMain org.modernclient.MyShapesFXML
```

The [personui](build.sc#474) module configures the [`PersonUI`](modernClients/ch02-javafx_fundamentals/personui/src/modernclient/PersonUI.scala) application. The application shows how to use te Model-View-Controller (MVC) architecture using
the GUI techniques of the previous application. The code simulates DAO (data access objects) to emulate a
database of people. The application can be executed with the following commands:

```bash
$ ./mill -i modernClients.ch02-javafx_fundamentals.personui.run
$ ./mill -i modernClients.ch02-javafx_fundamentals.personui.runMain com.modernclient.PersonUI
$ ./mill -i --watch modernClients.ch02-javafx_fundamentals.personui.runMain com.modernclient.PersonUI
```

> Note: the *"create"* does not seem to work because their is no way to have the listbox deselect any element. 

The next set of applications in the `ch03-PropertiesBindings` module provide more examples on the use
of events and bindings. The first examples is coded in the [arraychangeevent](build.sc#491) module and it
builds the [`ArrayChangeEventExample`](modernClients/ch03-PropertiesBindings/arraychangeevent/src/main/java/org/modernclients/propertiesandbindings/ArrayChangeEventExample.scala) application. This application does **not** create  and 
show a GUI. It consists of the following tests:
* `ArrayChangeEventExample`
* `FXCollectionsExample`
* `ObservableListExample`
* `ListChangeEventExample`
* `MapChangeEventExample`
* `SetChangeEventExample`
* `ArrayChangeEventExampleOriginal`
* `bindingsUtilityUnidirectional`
* `SelectBindingExample`
* `JavaBeanPropertiesExample`

Each of the examples above creates a certain type of observable value, assigns an observable to an update event, 
and then updates the event. During execution, you show see a message of setting up and updating a value, and then
observe the event being detected with another message. You can use these to select and test a value update using
the MVC model. These examples assume we use containers, and update specific elements of those containers. To run
these examples you can execute one of the following commands:

```bash
$ ./mill -i modernClients.ch03-PropertiesBindings.arraychangeevent.run
$ ./mill -i modernClients.ch03-PropertiesBindings.arraychangeevent.runMain org.modernclients.propertiesandbindings.ArrayChangeEventExample
$ ./mill -i --watch modernClients.ch03-PropertiesBindings.arraychangeevent.runMain org.modernclients.propertiesandbindings.ArrayChangeEventExample
```

The [numericproperties](build.sc#502) module configures the [`NumericPropertiesExample`](modernClients/ch03-PropertiesBindings/numericproperties/src/main/java/org/modernclients/propertiesandbindings/NumericPropertiesExample.scala) that performs essentially the same type of tests as the above examples, but for numeric values. The non-GUI application contais the 
following tests:
* `NumericPropertiesExample`
* `BidirectionalBindingExample`
* `DirectExtensionExample`
* `TriangleAreaExample`
* `TriangleAreaFluentExample`
* `HeronsFormulaExample`
* `HeronsFormulaDirectExtensionExample`

To run these examples you can execute one of the following commands:

```bash
$ ./mill -i modernClients.ch03-PropertiesBindings.numericproperties.run
$ ./mill -i modernClients.ch03-PropertiesBindings.numericproperties.runMain org.modernclients.propertiesandbindings.NumericPropertiesExample
$ ./mill -i --watch modernClients.ch03-PropertiesBindings.numericproperties.runMain org.modernclients.propertiesandbindings.NumericPropertiesExample
```

The `ch04-javafx_controls` module contains a set of submodules that exemplify the use of the GUI controls that 
are available in JavaFX. The submodules and the exemplified elements are:
* [`basic`](build.sc#518): 
  * Standard buttons 
  * Hyperlink
  * Single check box
  * Toggle buttons
  * Radio buttons
  * Single line text input boxes
  * Single line password input boxes
  * Multi-line text input boxes
  * Progress bar 
  * Slide ruler
* [`container`](build.sc#530):
  * Title pane
  * Scroll pane
  * Stack Pane
  * Split Pane
  * Tab Pane
  * Tool bar
  * Accordion
  * Horizontal box
  * Vertical box
* [`others`](build.sc#544)
  * HTMLEditor
  * Pagination
  * BorderPane
  * ScrollBar
  * Spinner
  * Tooltip
* [`popup`](build.sc#557)
* [`dialog`](build.sc#569)
* [`selectionAndFocus`](build.sc#593)
* [`advanced`](build.sc#581)


[Basic](/home/hmf/VSCodeProjects/javaFXClientMill/modernClients/ch04-javafx_controls/basic/src/Basic.scala):

```bash
$ ./mill -i modernClients.ch04-javafx_controls.basic.run
$ ./mill -i modernClients.ch04-javafx_controls.basic.runMain org.modernclients.controls.Basic
$ ./mill -i --watch modernClients.ch04-javafx_controls.basic.runMain org.modernclients.controls.Basic
```

[Container](modernClients/ch04-javafx_controls/container/src/Container.scala):

```bash
$ ./mill -i modernClients.ch04-javafx_controls.container.run
$ ./mill -i modernClients.ch04-javafx_controls.container.runMain org.modernclients.container.Container
$ ./mill -i --watch modernClients.ch04-javafx_controls.container.runMain org.modernclients.container.Container
```

[Others](modernClients/ch04-javafx_controls/others/src/Others.scala):

```bash
$ ./mill -i modernClients.ch04-javafx_controls.others.run
$ ./mill -i modernClients.ch04-javafx_controls.others.runMain org.modernclients.others.Others
$ ./mill -i --watch modernClients.ch04-javafx_controls.others.runMain org.modernclients.others.Others
```

[Popup](modernClients/ch04-javafx_controls/popup/src/Popup.scala):

```bash
$ ./mill -i modernClients.ch04-javafx_controls.popup.run
$ ./mill -i modernClients.ch04-javafx_controls.popup.runMain org.modernclients.popup.Popup
$ ./mill -i --watch modernClients.ch04-javafx_controls.popup.runMain org.modernclients.popup.Popup
```

[Dialog](modernClients/ch04-javafx_controls/dialog/src/Dialog.scala)

```bash
$ ./mill -i modernClients.ch04-javafx_controls.dialog.run
$ ./mill -i modernClients.ch04-javafx_controls.dialog.runMain org.modernclients.dialog.Dialog
$ ./mill -i --watch modernClients.ch04-javafx_controls.dialog.runMain org.modernclients.dialog.Dialog
```


TODO 

[SelectionAndFocus](modernClients/ch04-javafx_controls/selectionAndFocus/src/SelectionAndFocus.scala):

```bash
$ ./mill -i modernClients.ch04-javafx_controls.selectionAndFocus.run
$ ./mill -i modernClients.ch04-javafx_controls.selectionAndFocus.runMain org.modernclients.selectionAndFocus.SelectionAndFocus
$ ./mill -i --watch modernClients.ch04-javafx_controls.selectionAndFocus.runMain org.modernclients.selectionAndFocus.SelectionAndFocus
```

TODO: 

[Advanced](modernClients/ch04-javafx_controls/advanced/src/Advanced.scala):

```bash
$ ./mill -i modernClients.ch04-javafx_controls.advanced.run
$ ./mill -i modernClients.ch04-javafx_controls.advanced.runMain org.modernclients.advanced.Advanced
$ ./mill -i --watch modernClients.ch04-javafx_controls.advanced.runMain org.modernclients.advanced.Advanced
```


The `chapter5-mastering Visuals+CSS Design` module contains a set of submodules that exemplify the CSS API that 
are available in JavaFX. The submodules and the exemplified elements are:
* [`introduction`](build.sc#608): 
* [`cssapi`](build.sc#620): 
* [`applying`](build.sc#633): 


[HelloCSS](modernClients/chapter5-mastering Visuals+CSS Design/introduction/src/HelloCSS.scala):

```bash
$ ./mill -i modernClients.ch04-javafx_controls.advanced.run
$ ./mill -i modernClients.ch04-javafx_controls.advanced.runMain org.modernclients.advanced.Advanced
$ ./mill -i --watch modernClients.ch04-javafx_controls.advanced.runMain org.modernclients.advanced.Advanced
```

[WeatherApp](modernClients/chapter5-mastering_Visuals-CSS_Design/cssapi/src/WeatherApp.scala)

```bash
$ ./mill -i modernClients.chapter5-mastering_Visuals-CSS_Design.cssapi.run
$ ./mill -i modernClients.chapter5-mastering_Visuals-CSS_Design.cssapi.runMain chapter5.cssapi.WeatherApp
$ ./mill -i --watch modernClients.chapter5-mastering_Visuals-CSS_Design.cssapi.runMain chapter5.cssapi.WeatherApp
```

[ApplyingStyles](modernClients/chapter5-mastering_Visuals-CSS_Design/applying/src/ApplyingStyles.scala)

```bash
$ ./mill -i modernClients.chapter5-mastering_Visuals-CSS_Design.applying.run
$ ./mill -i modernClients.chapter5-mastering_Visuals-CSS_Design.applying.runMain chapter5.applying.ApplyingStyles
$ ./mill -i --watch modernClients.chapter5-mastering_Visuals-CSS_Design.applying.runMain chapter5.applying.ApplyingStyles
```

```bash
```

```bash
```

### HanSolo Charts Scala Examples

https://github.com/HanSolo/charts


hanSoloCharts/src/LineChartTest.scala

 * ./mill -i hanSoloChartsStd.run
 * ./mill -i hanSoloChartsStd.runMain hansolo.charts.LineChartTest
 * ./mill -i --watch hanSoloChartsStd.runMain hansolo.charts.LineChartTest

hanSoloChartsStd/src/LineChartTest.scala

 * ./mill -i hanSoloCharts.run
 * ./mill -i hanSoloCharts.runMain hansolo.charts.LineChartTest
 * ./mill -i --watch hanSoloCharts.runMain hansolo.charts.LineChartTest


hansolo/lineChart/src/LineChartTest.scala

 * ./mill -i hansolo.lineChart.run
 * ./mill -i hansolo.lineChart.runMain hansolo.charts.LineChartTest
 * ./mill -i --watch hansolo.lineChart.runMain hansolo.charts.LineChartTest

hansolo/arcChart/src/ArcChartTest.scala

 * ./mill -i hansolo.arcChart.run
 * ./mill -i hansolo.arcChart.runMain hansolo.charts.ArcChartTest
 * ./mill -i --watch hansolo.arcChart.runMain hansolo.charts.ArcChartTest


hansolo/heatMaps/src/AreaHeatMapTest.scala

 * ./mill -i hansolo.heatMaps.run
 * ./mill -i hansolo.heatMaps.runMain hansolo.charts.AreaHeatMapTest
 * ./mill -i --watch hansolo.heatMaps.runMain hansolo.charts.AreaHeatMapTest


hansolo/heatMaps/src/HeatMapTest.scala

 * X ./mill -i hansolo.heatMaps.run
 * ./mill -i hansolo.heatMaps.runMain hansolo.charts.HeatMapTest
 * ./mill -i --watch hansolo.heatMaps.runMain hansolo.charts.HeatMapTest


hansolo/heatMaps/src/MatrixHeatMapTest.scala


 * ./mill -i hansolo.heatMaps.runMain hansolo.charts.MatrixHeatmapTest
 * ./mill -i --watch hansolo.heatMaps.runMain hansolo.charts.MatrixHeatmapTest

hansolo/heatMaps/src/WorldHeatMapTest.scala


 * ./mill -i hansolo.heatMaps.runMain hansolo.charts.WorldHeatMapTest
 * ./mill -i --watch hansolo.heatMaps.runMain hansolo.charts.WorldHeatMapTest


Axis

hansolo/axis/src/AxisTest.scala

 * ./mill -i hansolo.axis.run
 * ./mill -i hansolo.axis.runMain hansolo.charts.AxisTest
 * ./mill -i --watch hansolo.axis.runMain hansolo.charts.AxisTest

hansolo/axis/src/DateAxisTest.scala

 * X ./mill -i hansolo.axis.run 
 * ./mill -i hansolo.axis.runMain hansolo.charts.DateAxisTest
 * ./mill -i --watch hansolo.axis.runMain hansolo.charts.DateAxisTest

hansolo/axis/src/TimeAxisTest.scala

 * X ./mill -i hansolo.axis.run
 * ./mill -i hansolo.axis.runMain hansolo.charts.TimeAxisTest
 * ./mill -i --watch hansolo.axis.runMain hansolo.charts.TimeAxisTest


bar

hansolo/bar/src/BarChartTest.scala

 * ./mill -i hansolo.bar.run
 * ./mill -i hansolo.bar.runMain hansolo.charts.BarChartTest
 * ./mill -i --watch hansolo.bar.runMain hansolo.charts.BarChartTest


hansolo/bar/src/ComparisonBarChartTest.scala

 * X ./mill -i hansolo.bar.run
 * ./mill -i hansolo.bar.runMain hansolo.charts.ComparisonBarChartTest
 * ./mill -i --watch hansolo.bar.runMain hansolo.charts.ComparisonBarChartTest

hansolo/bar/src/NestedBarChartTest.scala

 * X ./mill -i hansolo.bar.run
 * ./mill -i hansolo.bar.runMain hansolo.charts.NestedBarChartTest
 * ./mill -i --watch hansolo.bar.runMain hansolo.charts.NestedBarChartTest

hansolo/bar/src/PanelBarChartTest.scala

 * X ./mill -i hansolo.bar.run
 * ./mill -i hansolo.bar.runMain hansolo.charts.PanelBarChartTest
 * ./mill -i --watch hansolo.bar.runMain hansolo.charts.PanelBarChartTest

hansolo/bar/src/RadialBarChartTest.scala

 * X ./mill -i hansolo.bar.run
 * ./mill -i hansolo.bar.runMain hansolo.charts.RadialBarChartTest
 * ./mill -i --watch hansolo.bar.runMain hansolo.charts.RadialBarChartTest

box

hansolo/box/src/BoxPlotTest.scala

 * ./mill -i hansolo.box.run
 * ./mill -i hansolo.box.runMain hansolo.charts.BoxPlotTest
 * ./mill -i --watch hansolo.box.runMain hansolo.charts.BoxPlotTest

hansolo/box/src/BoxPlotsTest.scala

 * X ./mill -i hansolo.box.run
 * ./mill -i hansolo.box.runMain hansolo.charts.BoxPlotsTest
 * ./mill -i --watch hansolo.box.runMain hansolo.charts.BoxPlotsTest


bubble

hansolo/bubble/src/BubbleChartTest.scala

 * ./mill -i hansolo.bubble.run
 * ./mill -i hansolo.bubble.runMain hansolo.charts.BubbleChartTest
 * ./mill -i --watch hansolo.bubble.runMain hansolo.charts.BubbleChartTest

hansolo/bubble/src/BubbleGridChartTest.scala

 * X ./mill -i hansolo.bubble.run
 * ./mill -i hansolo.bubble.runMain hansolo.charts.BubbleGridChartTest
 * ./mill -i --watch hansolo.bubble.runMain hansolo.charts.BubbleGridChartTest

candle

hansolo/candle/src/CandleChartTest.scala

 * ./mill -i hansolo.candle.run
 * ./mill -i hansolo.candle.runMain hansolo.charts.CandleChartTest
 * ./mill -i --watch hansolo.candle.runMain hansolo.charts.CandleChartTest

charts

/home/hmf/VSCodeProjects/javaFXClientMill/hansolo/charts/src/ChartTest.scala

 * ./mill -i hansolo.charts.run
 * ./mill -i hansolo.charts.runMain hansolo.charts.ChartTest
 * ./mill -i --watch hansolo.charts.runMain hansolo.charts.ChartTest

ring

/home/hmf/VSCodeProjects/javaFXClientMill/hansolo/ring/src/ConcentricRingChartTest.scala

 * ./mill -i hansolo.ring.run
 * ./mill -i hansolo.ring.runMain hansolo.charts.ConcentricRingChartTest
 * ./mill -i --watch hansolo.ring.runMain hansolo.charts.ConcentricRingChartTest




<!-- 

HelloWorldScala/src/helloworld/HelloWorld.scala

 * ./mill -i HelloWorldScala.run
 * ./mill -i HelloWorldScala.runMain helloworld.HelloWorld
 * ./mill -i --watch HelloWorldScala.run
 

/home/hmf/VSCodeProjects/javaFXClientMill/HelloWorldJava/src/button/Main.java

 * ./mill -i HelloWorldJava.runMain button.Main
 * ./mill -i --watch HelloWorldJava.runMain button.Main

HelloWorldScala/src/button/Main.scala

 * ./mill -i HelloWorldScala.runMain button.Main
 * ./mill -i --watch HelloWorldScala.runMain button.Main


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
 
 modernClients/ch02-javafx_fundamentals/myshapes/src/main/java/org/modernclient/MyShapes.scala

 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapes.run
 * ./mill -i modernClients.ch02-javafx_fundamentals.myshapes.runMain org.modernclient.MyShapes
 * ./mill -i --watch modernClients.ch02-javafx_fundamentals.myshapes.runMain org.modernclient.MyShapes


modernClients/HelloWorldScala/test/src/PlotSpec.scala

 * ./mill -i HelloWorld.test
 * ./mill -i HelloWorld.test.testLocal
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.*
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.hello // test("hello")

HelloWorldJava/test/src/PlotSpec.scala

 * ./mill -i HelloWorld.test
 * ./mill -i HelloWorld.test.testLocal
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.*
 * ./mill -i HelloWorld.test HelloWorld.PlotSpec.hello // test("hello")

 -->
