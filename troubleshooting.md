<!--- cSpell:ignore dprism, javafx, hansolo, djavafx, openjfx, coursier, controlsfx --->
<!--- cSpell:ignore libprism, cuda, clinit, libglass, libglassgtk, libjavafx, pango, println --->
<!--- cSpell:ignore inputmap, rasterizer, vsync, vpipe, freetype, libraryname --->
<!--- cSpell:ignore myshapesproperties, modernclient --->




# Introduction

Some notes on issues that were identified. Here we try and include information 
on how to diagnose and solve these problems. 

## JavaFX boot: Graphics Device initialization failed for:  es2, sw

When executing:

```bash
 ./mill -i hanSoloCharts.runMain hansolo.charts.LineChartTest
```

and ensuring that the following command-line arguments are used: 

```bash
-Dprism.verbose=true
-Djavafx.verbose=true
```

so that we get the all the error messages, we get this result: 

```bash
user@machine:~/VSCodeProjects/javaFXClientMill$ ./mill -i hanSoloCharts.runMain hansolo.charts.LineChartTest
Compiling /home/hmf/VSCodeProjects/javaFXClientMill/build.sc
[33/37] hanSoloCharts.forkArgs 
-Djavafx.verbose=true
-ea
--module-path
/home/hmf/VSCodeProjects/javaFXClientMill/hanSoloCharts/resources:/home/hmf/VSCodeProjects/javaFXClientMill/out/hanSoloCharts/compile/dest/classes:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-controls/16/javafx-controls-16.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/controlsfx/controlsfx/11.1.0/controlsfx-11.1.0.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-controls/16/javafx-controls-16-linux.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/javafx-graphics-16.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-base/16/javafx-base-16-mac.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/javafx-graphics-16-mac.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-controls/16/javafx-controls-16-mac.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-swing/11/javafx-swing-11-mac.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/javafx-graphics-16-linux.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-base/16/javafx-base-16.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-base/16/javafx-base-16-linux.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-swing/11/javafx-swing-11-linux.jar
--add-modules
javafx.graphics,javafx.base,javafx.controls,org.controlsfx.controls,javafx.swing
--add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=org.controlsfx.controls
--add-exports=javafx.controls/com.sun.javafx.scene.control.inputmap=org.controlsfx.controls
--add-exports=javafx.graphics/com.sun.javafx.scene.traversal=org.controlsfx.controls
-Dprism.verbose=true
-ea
[37/37] hanSoloCharts.runMain 
JavaFX launchApplication method: launchMode=LM_CLASS
Prism pipeline init order: es2 sw 
Using Double Precision Marlin Rasterizer
Using dirty region optimizations
Not using texture mask for primitives
Not forcing power of 2 sizes for textures
Using hardware CLAMP_TO_ZERO mode
Opting in for HiDPI pixel scaling
Prism pipeline name = com.sun.prism.es2.ES2Pipeline
Loading ES2 native library ... prism_es2
WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/libprism_es2.so
GraphicsPipeline.createPipeline failed for com.sun.prism.es2.ES2Pipeline
java.lang.UnsatisfiedLinkError: no prism_es2 in java.library.path: [., /usr/local/cuda-11.2/lib64, /usr/java/packages/lib, /usr/lib/x86_64-linux-gnu/jni, /lib/x86_64-linux-gnu, /usr/lib/x86_64-linux-gnu, /usr/lib/jni, /lib, /usr/lib]
	at java.base/java.lang.ClassLoader.loadLibrary(ClassLoader.java:2670)
	at java.base/java.lang.Runtime.loadLibrary0(Runtime.java:830)
	at java.base/java.lang.System.loadLibrary(System.java:1873)
	at javafx.graphics/com.sun.glass.utils.NativeLibLoader.loadLibraryInternal(NativeLibLoader.java:163)
	at javafx.graphics/com.sun.glass.utils.NativeLibLoader.loadLibrary(NativeLibLoader.java:53)
	at javafx.graphics/com.sun.prism.es2.ES2Pipeline.lambda$static$0(ES2Pipeline.java:69)
	at java.base/java.security.AccessController.doPrivileged(Native Method)
	at javafx.graphics/com.sun.prism.es2.ES2Pipeline.<clinit>(ES2Pipeline.java:51)
	at java.base/java.lang.Class.forName0(Native Method)
	at java.base/java.lang.Class.forName(Class.java:315)
	at javafx.graphics/com.sun.prism.GraphicsPipeline.createPipeline(GraphicsPipeline.java:218)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.init(QuantumRenderer.java:91)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.run(QuantumRenderer.java:124)
	at java.base/java.lang.Thread.run(Thread.java:829)
*** Fallback to Prism SW pipeline
Prism pipeline name = com.sun.prism.sw.SWPipeline
WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/libprism_sw.so
GraphicsPipeline.createPipeline failed for com.sun.prism.sw.SWPipeline
java.lang.UnsatisfiedLinkError: no prism_sw in java.library.path: [., /usr/local/cuda-11.2/lib64, /usr/java/packages/lib, /usr/lib/x86_64-linux-gnu/jni, /lib/x86_64-linux-gnu, /usr/lib/x86_64-linux-gnu, /usr/lib/jni, /lib, /usr/lib]
	at java.base/java.lang.ClassLoader.loadLibrary(ClassLoader.java:2670)
	at java.base/java.lang.Runtime.loadLibrary0(Runtime.java:830)
	at java.base/java.lang.System.loadLibrary(System.java:1873)
	at javafx.graphics/com.sun.glass.utils.NativeLibLoader.loadLibraryInternal(NativeLibLoader.java:163)
	at javafx.graphics/com.sun.glass.utils.NativeLibLoader.loadLibrary(NativeLibLoader.java:53)
	at javafx.graphics/com.sun.prism.sw.SWPipeline.lambda$static$0(SWPipeline.java:42)
	at java.base/java.security.AccessController.doPrivileged(Native Method)
	at javafx.graphics/com.sun.prism.sw.SWPipeline.<clinit>(SWPipeline.java:41)
	at java.base/java.lang.Class.forName0(Native Method)
	at java.base/java.lang.Class.forName(Class.java:315)
	at javafx.graphics/com.sun.prism.GraphicsPipeline.createPipeline(GraphicsPipeline.java:218)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.init(QuantumRenderer.java:91)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.run(QuantumRenderer.java:124)
	at java.base/java.lang.Thread.run(Thread.java:829)
Graphics Device initialization failed for :  es2, sw
Error initializing QuantumRenderer: no suitable pipeline found
java.lang.RuntimeException: java.lang.RuntimeException: Error initializing QuantumRenderer: no suitable pipeline found
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer.getInstance(QuantumRenderer.java:280)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumToolkit.init(QuantumToolkit.java:244)
	at javafx.graphics/com.sun.javafx.tk.Toolkit.getToolkit(Toolkit.java:261)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:286)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:160)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.startToolkit(LauncherImpl.java:658)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplicationWithArgs(LauncherImpl.java:409)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplication(LauncherImpl.java:363)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at java.base/sun.launcher.LauncherHelper$FXHelper.main(LauncherHelper.java:1051)
Caused by: java.lang.RuntimeException: Error initializing QuantumRenderer: no suitable pipeline found
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.init(QuantumRenderer.java:94)
	at javafx.graphics/com.sun.javafx.tk.quantum.QuantumRenderer$PipelineRunnable.run(QuantumRenderer.java:124)
	at java.base/java.lang.Thread.run(Thread.java:829)
Exception in thread "main" java.lang.reflect.InvocationTargetException
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:566)
	at java.base/sun.launcher.LauncherHelper$FXHelper.main(LauncherHelper.java:1051)
Caused by: java.lang.RuntimeException: No toolkit found
	at javafx.graphics/com.sun.javafx.tk.Toolkit.getToolkit(Toolkit.java:273)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:286)
	at javafx.graphics/com.sun.javafx.application.PlatformImpl.startup(PlatformImpl.java:160)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.startToolkit(LauncherImpl.java:658)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplicationWithArgs(LauncherImpl.java:409)
	at javafx.graphics/com.sun.javafx.application.LauncherImpl.launchApplication(LauncherImpl.java:363)
	... 5 more
1 targets failed
hanSoloCharts.runMain subprocess failed
```

Notice that the system attempted to load the native libraries using the [NativeLibLoader](https://github.com/openjdk/jfx/blob/jfx16/modules/javafx.graphics/src/main/java/com/sun/glass/utils/NativeLibLoader.java). It looks like it first tries the [`private static void loadLibraryInternal(String libraryName, List<String> dependencies, Class caller)`](https://github.com/openjdk/jfx/blob/jfx16/modules/javafx.graphics/src/main/java/com/sun/glass/utils/NativeLibLoader.java#L114) method. Here it will do the following (comments from source code):

```scala
        // The search order for native library loading is:
        // - try to load the native library from the same folder as this jar
        //   (only on non-modular builds)
        // - if the native library comes bundled as a resource it is extracted
        //   and loaded
        // - the java.library.path is searched for the library in definition
        //   order
        // - the library is loaded via System#loadLibrary
        // - on iOS native library is statically linked and detected from the
        //   existence of a JNI_OnLoad_libraryname function
```

Note that in this example the call to [`private static boolean loadLibraryFromResource(String libraryName, List<String> dependencies, Class caller)`](https://github.com/openjdk/jfx/blob/jfx16/modules/javafx.graphics/src/main/java/com/sun/glass/utils/NativeLibLoader.java#L193) fails. In fact a search for the libraries confirms this is correct:

```bash
locate -i libprism_es2.so
/home/hmf/.openjfx/cache/13.0.2/libprism_es2.so
/home/hmf/.openjfx/cache/13/libprism_es2.so
/home/hmf/.openjfx/cache/16/libprism_es2.so

locate -i libglass.so
/home/hmf/.openjfx/cache/13.0.2/libglass.so
/home/hmf/.openjfx/cache/13/libglass.so
/home/hmf/.openjfx/cache/16/libglass.so

locate -i prism_sw.so
/home/hmf/.openjfx/cache/13.0.2/libprism_sw.so
```

The issue here is that we have use the OpenJFX libraries via the Maven/Ivy repository. The native libraries are therefore included in the JAR archives (libraries). In these cases the native libraries are extracted from the JARs and linked to dynamically. The command shows an example where this happens. We execute:

```
./mill -i modernClients.ch02-javafx_fundamentals.myshapesproperties.runMain org.modernclient.MyShapesProperties 
```

we get:

```bash
-Djavafx.verbose=true
-ea
--module-path
/home/hmf/VSCodeProjects/javaFXClientMill/modernClients/ch02-javafx_fundamentals/myshapesproperties/resources:/home/hmf/VSCodeProjects/javaFXClientMill/out/modernClients/ch02-javafx_fundamentals/myshapesproperties/compile/dest/classes:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-controls/16/javafx-controls-16.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/controlsfx/controlsfx/11.1.0/controlsfx-11.1.0.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-fxml/16/javafx-fxml-16.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-controls/16/javafx-controls-16-linux.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/javafx-graphics-16.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-fxml/16/javafx-fxml-16-linux.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/javafx-graphics-16-linux.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-base/16/javafx-base-16.jar:/home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-base/16/javafx-base-16-linux.jar
--add-modules
javafx.graphics,javafx.base,javafx.controls,org.controlsfx.controls,javafx.fxml
--add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=org.controlsfx.controls
--add-exports=javafx.controls/com.sun.javafx.scene.control.inputmap=org.controlsfx.controls
--add-exports=javafx.graphics/com.sun.javafx.scene.traversal=org.controlsfx.controls
-Dprism.verbose=true
-ea
[37/37] modernClients.ch02-javafx_fundamentals.myshapesproperties.runMain 
JavaFX launchApplication method: launchMode=LM_CLASS
Prism pipeline init order: es2 sw 
Using Double Precision Marlin Rasterizer
Using dirty region optimizations
Not using texture mask for primitives
Not forcing power of 2 sizes for textures
Using hardware CLAMP_TO_ZERO mode
Opting in for HiDPI pixel scaling
Prism pipeline name = com.sun.prism.es2.ES2Pipeline
Loading ES2 native library ... prism_es2
WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/libprism_es2.so
Loaded library /libprism_es2.so from resource
	succeeded.
GLFactory using com.sun.prism.es2.X11GLFactory
(X) Got class = class com.sun.prism.es2.ES2Pipeline
Initialized prism pipeline: com.sun.prism.es2.ES2Pipeline
JavaFX: using com.sun.javafx.tk.quantum.QuantumToolkit
WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/libglass.so
Loaded library /libglass.so from resource
WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/libglassgtk3.so
Loaded library /libglassgtk3.so from resource
Maximum supported texture size: 16384
Maximum texture size clamped to 4096
Non power of two texture support = true
Maximum number of vertex attributes = 16
Maximum number of uniform vertex components = 16384
Maximum number of uniform fragment components = 16384
Maximum number of varying components = 128
Maximum number of texture units usable in a vertex shader = 32
Maximum number of texture units usable in a fragment shader = 32
Graphics Vendor: Intel
       Renderer: Mesa Intel(R) UHD Graphics 630 (CFL GT2)
        Version: 4.6 (Compatibility Profile) Mesa 20.2.6
 vsync: true vpipe: true
Calling main(String[]) method
WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/libjavafx_font.so
Loaded library /libjavafx_font.so from resource
WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/libjavafx_font_freetype.so
Loaded library /libjavafx_font_freetype.so from resource
WARNING: java.lang.UnsatisfiedLinkError: Can't load library: /home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-graphics/16/libjavafx_font_pango.so
Loaded library /libjavafx_font_pango.so from resource
ES2ResourceFactory: Prism - createStockShader: FillEllipse_LinearGradient_PAD.frag
PPSRenderer: scenario.effect - createShader: LinearConvolveShadow_64
ES2ResourceFactory: Prism - createStockShader: Solid_TextureRGB.frag
ES2ResourceFactory: Prism - createStockShader: Texture_Color.frag
new alphas with length = 4096
QuantumRenderer: shutdown

```

Note that we use the following code to show the command line parameters used for th JVM:

```scala
     object myshapesproperties extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclient.MyShapesProperties")

      override def forkArgs: Target[Seq[String]] = T {
        val t = Seq("-Djavafx.verbose=true", "-ea") ++ // JavaFX
          super[OpenJFX].forkArgs() //  OpenFX
        println(t.mkString("\n"))
        t
      }

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  ivy"$CONTROLSFX",
                                  ivy"$FXML"
                                )

    }

```

In the above example [previously unpacked library is loaded](https://github.com/openjdk/jfx/blob/jfx16/modules/javafx.graphics/src/main/java/com/sun/glass/utils/NativeLibLoader.java#L216). Note that in both cases an attempt is first made to load the libraries from the system and only then from the modules. The difference is that the call to [`loadLibraryFromResource`](https://github.com/openjdk/jfx/blob/jfx16/modules/javafx.graphics/src/main/java/com/sun/glass/utils/NativeLibLoader.java#L193) in the first case it fails and the second not. 

The culprit seems to be the `ivy"eu.hansolo.fx:charts::$hanSoloChartsVersion"` library. This library has **not** been added to the modules list. It seems like loading native libraries differs when using modules and when using standard JARs. If it is a standard JAR the JAR is not used as a resource to cache and load the native library stored within it. Whereas in the modules (JDK 9+) this happens. I assume that this is because when a JavaFX (OpenFX) library is a dependency of a module and a standard library, they will be loaded twice, each with it own class loader. 

Could thr native library be shared? If so, how can we ensure that a common dependency of a standard JAR and a module are the same? Another issue is, does thr OS allow one to load the same dynamically linked library more than once per process (*I don't this tis is possible*).

```
 TODO
```

home/hmf/.cache/coursier/v1/https/repo1.maven.org/maven2/eu/hansolo/fx/charts/11.7
