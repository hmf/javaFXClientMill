
// cSpell:ignore javac, xlint, pprint, fansi, sbt, agg, jfx, logback, slf, jini
// cSpell:ignore scalalib, helloworld, coursier, Deps, unmanaged, classpath, JVM's, customizer, dprism
// cSpell:ignore javafx, controlsfx, openjfx, munit, myshapes, myshapesproperties, myshapesfxml
// cSpell:ignore hansolo, personui
// cSpell:ignore libprism, libglass, libgio, libgtk, xtst, libxslt, cuda, versionless, Djavafx, Djdk, Dawt, Djava
// cSpell:ignore arraychangeevent, numericproperties

import coursier.core.Resolution
import mill.api.Loose
import mill.define.{Target, Task}
import mill._, scalalib._

import $ivy.`com.lihaoyi::pprint:0.9.0`
import $ivy.`com.lihaoyi::fansi:0.5.0`

val ScalaVersion = "3.5.1-RC2" // "3.0.1"

//val javaFXVersion = "11.0.2"
//val javaFXVersion = "11"
//val javaFXVersion = "12"
//val javaFXVersion = "13.0.2"
// val javaFXVersion = "16"
val javaFXVersion = "22.0.2"

val mUnitVersion         = "1.0.1" // "0.8.4" // "0.7.27"
val controlsFXVersion    = "11.2.1" // "11.1.0"
//val hanSoloChartsVersion = "16.0.12" JDK16
// val hanSoloChartsVersion = "11.7"
val hanSoloChartsVersion = "21.0.19"


/**
 * Mill now checks that a JavaModule does not use any scala dependencies. In 
 * general this is desired, ensuring the project model is pure Java. However
 * there are always cases were a java project may legitimately require scala 
 * libraries. In this case to use the JUnit 5 tests API, we need to use the
 * SBT jupiter interface. 
 * 
 * According to the github issue below 2 solutions exist:
 * - Define a ScalaModule and use that as a dependency
 * - Explicitly set the JAR version so that it is downloaded as a standard Java library
 * 
 * In the case of the SBT jupiter interface, this will not work. The problem is
 * that the JAR's name used in the Maven repository is not standard. It uses the 
 * extended Scala version `2.12_1.0`, where the `1.0` referes to the SBT version. 
 * This means that under the hood Coursier will grab the correct SBT jupiter 
 * interface library, but ot will also try and download a Scala library version 
 * `2.12_1.0`. which does no exist. If the version number wa standard one could  
 * simply add the `override def moduleDeps = Seq(LibsScala)` dependency to the 
 * `JavaModule` that required this library. 
 * 
 * Because of this,  the current solution explicitly sets the exact library
 * version directly in the `OpenJFX` `JavaModule`. 
 * 
 * @see https://github.com/com-lihaoyi/mill/issues/860 
 * @see https://mvnrepository.com/artifact/com.github.sbt.junit
 * @see https://mvnrepository.com/artifact/com.github.sbt.junit/sbt-jupiter-interface_2.12_1.0/0.13.0
 */ 
object LibsScala extends ScalaModule {
  // def scalaVersion = T{ "2.12.1" }
  def scalaVersion = T{ "2.12_1.0" }
  override def ivyDeps = T { Agg(ivy"com.github.sbt.junit::sbt-jupiter-interface:0.13.0") }
}

/**
 * When working with JavaFX/OpenFX in JDK 1.9 and later, the libraries are
 * not included in the JDK. They may be installed manually in the OS or
 * automatically via Mill. The latter method has the advantage of acquiring
 * the paths of the libraries automatically and also setting up build the file
 * automatically. The easiest way to do this is to to use Mill's automatic
 * library dependency management (see #775# link below). Here we have an example
 * of using of Mill's managed library dependency setup.
 *
 * Note that in the case of the JavaFX libraries we must use set the JVM's
 * parameters to include the module path and module names. Other libraries, even
 * though provided as module may not require this. Most of the JVM parameter
 * set-up is automatic. It also allows to set-up module visibility and even
 * overriding certain modules on boot-up. This allows for example the use the
 * TestFX for use in headless UI testing.
 *
 * To add other libraries as modules see `controlsFXModule` as an example. 
 * 
 * ./mill mill.scalalib.GenIdea/idea
 * 
 * TODO: https://stackoverflow.com/questions/46616520/list-modules-in-jar-file
 *
 * @see https://github.com/com-lihaoyi/mill/pull/775#issuecomment-826091576
 */
trait OpenJFX extends JavaModule {

  // override def moduleDeps = Seq(LibsScala)

  // Start of example of manual setup. Note used
  // Modules 

  // OpenFX
  val BASE_       = "base"
  val CONTROLS_   = "controls"
  val FXML_       = "fxml"
  val GRAPHICS_   = "graphics"
  val MEDIA_      = "media"
  val SWING_      = "swing"
  val WEB_        = "web"

  // ControlsFX
  val CONTROLSFX_ = "controlsfx"
  val CONTROLSFX_1 = "org.controlsfx.controls"

  // Charts
  val HANSOLO_CHARTS_ = "eu.hansolo.fx.charts"
  val HANSOLO_CHARTS_1 = "charts"


  // Extra modules
  // Note that the module name and the library name are not the same
  val controlsFXModule = CONTROLSFX_1

  // Module libraries 
  val BASE           = s"org.openjfx:javafx-$BASE_:$javaFXVersion"
  val CONTROLS       = s"org.openjfx:javafx-$CONTROLS_:$javaFXVersion"
  val FXML           = s"org.openjfx:javafx-$FXML_:$javaFXVersion"
  val GRAPHICS       = s"org.openjfx:javafx-$GRAPHICS_:$javaFXVersion"
  val MEDIA          = s"org.openjfx:javafx-$MEDIA_:$javaFXVersion"
  val SWING          = s"org.openjfx:javafx-$SWING_:$javaFXVersion"
  val WEB            = s"org.openjfx:javafx-$WEB_:$javaFXVersion"
  val CONTROLSFX     = s"org.controlsfx:$CONTROLSFX_:$controlsFXVersion"
  val HANSOLO_CHARTS = s"eu.hansolo.fx:charts:$hanSoloChartsVersion"  
  
  // OpenFX/JavaFX libraries
  val javaFXLibraryNames = Seq(BASE_, CONTROLS_, FXML_, GRAPHICS_, MEDIA_, SWING_, WEB_)


  def jfx(s:String): String = s"javafx.$s"

  val javaFXModuleNames = Map(
                             jfx(BASE_)      -> BASE, 
                             jfx(CONTROLS_)  -> CONTROLS, 
                             jfx(FXML_)      -> FXML, 
                             jfx(GRAPHICS_)  -> GRAPHICS, 
                             jfx(MEDIA_)     -> MEDIA, 
                             jfx(SWING_)     -> SWING, 
                             jfx(WEB_)       -> WEB,
                             CONTROLSFX_1    -> CONTROLSFX,
                             HANSOLO_CHARTS_ -> HANSOLO_CHARTS
                             )


  /* 
  // This is an example snippet of how to use the library and  module names listed above.
  // This not required because we now get this information from semi-automatically from
  // the managed libraries. 
  // List of modules (note that a single Jar may have more than one module)
  val modules = javaFXModuleNames.map(n => n -> s"org.openjfx:javafx-$n:$javaFXVersion") // OpenFX
                                  .toMap 
                ++  // Other modules
                Map( "controlsfx" -> s"org.controlsfx:controlsfx:$controlsFXVersion")    // ControlsFX
  println(modules)
  */
  // End of example of manual setup. Note used


  // Standard libraries
  //val ivyMunit          = ivy"org.scalameta::munit::$mUnitVersion"
  val ivyMunit          = ivy"org.scalameta::munit:$mUnitVersion"
  val ivyMunitInterface = "munit.Framework"


  /**
   * In order to use OS specific libraries (such as JavaFX or OpenJFX), we
   * must set-up the OS flags appropriately for Maven download via Coursier.
   * This is only available **after** version **0.9.6** of Mill.
   * 
   * @see https://github.com/com-lihaoyi/mill/pull/775 (commit ab4d61a)
   * 
   * We had to add this same override explicitly to the test module. After version 
   * 0.10.0 of Mill this is not required anymore because it is automatically inherited.
   * @see https://github.com/com-lihaoyi/mill/issues/1406
   *
   * @return OS specific resolution mapping
  */ 
  override def resolutionCustomizer: Task[Option[Resolution => Resolution]] = T.task {
    Some((_: coursier.core.Resolution).withOsInfo(coursier.core.Activation.Os.fromProperties(sys.props.toMap)))
  }
  

  // Logging utilities
  def underline(a: fansi.Attr) = fansi.Underlined.On ++ a
  def lightYellow_ : fansi.Attr = fansi.Color.LightYellow
  def lightYellow(s:java.lang.String): String = lightYellow_(s).render
  //def underlineYellow(s:java.lang.String): String = fansi.Underlined.On(fansi.Color.LightYellow(s)).render
  def underlineYellow(s:java.lang.String): String = underline(fansi.Color.Yellow)(s).render
  def underlineLYellow(s:java.lang.String): String = underline(lightYellow_)(s).render

  // https://www.rapidtables.com/web/color/orange-color.html
  def orange_ : fansi.Attr = fansi.Color.True(255,165,0)
  def orange(s: String): String = orange_(s).render
  def underlineOrange(s: String): String = fansi.Underlined.On(orange_(s)).render

  def darkOrange_ : fansi.Attr = fansi.Color.True(255,140,0)
  def darkOrange(s: String): String = darkOrange_(s).render
  def underlineDarkOrange(s: String): String = fansi.Underlined.On(darkOrange_(s)).render
  
  def showManagedLibs(libs: Loose.Agg[String]) = {
    println(underlineOrange("Manged modules found from dependencies:"))
    println(lightYellow(libs.mkString("!\n")))
  }

  def showModuleChecks(
      hasJavaFX:   Boolean,
      hasControls: Boolean,
      hasCharts:   Boolean,
      hasLogback:  Boolean,
      hasSLF4j:    Boolean ) = {

    println(underlineOrange("Module checks:"))
    println(lightYellow(s"hasJavaFX:   $hasJavaFX"))
    println(lightYellow(s"hasControls: $hasControls"))
    println(lightYellow(s"hasCharts:   $hasCharts"))
    println(lightYellow(s"hasLogback:  $hasLogback"))
    println(lightYellow(s"hasSLF4j:    $hasSLF4j"))
  }

  def mkStringOrEmpty(s: Seq[String], delimiter: String = ", ") : String = {
    if (s.size > 0) s.mkString(delimiter) else "-"
  }

  def showModuleNames(
      javafx:   Seq[String],
      controls: Seq[String],
      charts:   Seq[String],
      logBack:  Seq[String],
      sl4J:     Seq[String] ) = {

    println(underlineOrange("Module names:"))
    println(lightYellow(s"javafx:   ${mkStringOrEmpty(javafx)}"))
    println(lightYellow(s"controls: ${mkStringOrEmpty(controls)}"))
    println(lightYellow(s"charts:   ${mkStringOrEmpty(charts)}"))
    println(lightYellow(s"logBack:  ${mkStringOrEmpty(logBack)}"))
    println(lightYellow(s"sl4J:     ${mkStringOrEmpty(sl4J)}"))
  }

  val JAVAFX_  = "javafx"
  val HANSOLO_ = "hansolo"
  val LOGBACK_ = "logback"
  val SLF4J_   = "slf4j"

  /**
   * Here we setup the Java modules so that they can be loaded prior to
   * application boot. We can indicate which modules are visible and even opt
   * to substitute some of those. For example using TestFX to allow for headless
   * testing.
   *
   * Note that with managed libraries, we may pull in additional modules. So we
   * attempt here to identify (via naming convention), which libraries are modules.
   * These corresponding modules are then added to the JVM command line. 
   * 
   * @return the list of parameters for the JVM
   */
  override def forkArgs: Target[Seq[String]] = T {
    println(orange("build.sc.forkArgs"))

    // get the managed libraries
    val allLibs: Loose.Agg[PathRef] = runClasspath()
    val strLibs = allLibs.map(_.path.toString())
    // get the OpenJFX and related managed libraries that have/are modules
    val s: Loose.Agg[String] = strLibs.filter{
                                         s =>
                                           val t = s.toLowerCase()
                                           t.contains(JAVAFX_) || t.contains(CONTROLSFX_) || 
                                           t.contains(HANSOLO_) || t.contains(LOGBACK_) || t.contains(SLF4J_)
                                        }
    showManagedLibs(s)

    // Check for each module by name
    val hasControls = s.filter{ s => s.toLowerCase.contains(CONTROLSFX_) }.size > 0
    val hasCharts   = s.filter{ s => s.toLowerCase.contains(HANSOLO_) }.size > 0
    val hasLogback  = s.filter{ s => s.toLowerCase.contains(LOGBACK_) }.size > 0
    val hasSLF4j    = s.filter{ s => s.toLowerCase.contains(SLF4J_) }.size > 0
    // Match is a little more complicated
    // Create the JavaFX module names (convention is amenable to automation)
    // import scala.util.matching.Regex
    val javaFXLibs = raw".*javafx-(.+?)-.*".r
    val javaFXModules = s.iterator.map(m => javaFXLibs.findFirstMatchIn(m).map(_.group(1)) )
                         .toSet
                         .filter(_.isDefined)
                         .map(_.get)
                         .toSeq
    val hasJavaFX   = javaFXModules.size > 0

    showModuleChecks( hasJavaFX, hasControls, hasCharts,  hasLogback, hasSLF4j )

    // Now collect the module names based on the modules found
    // First get the javaFX only libraries
    val javafx   = if (hasJavaFX)   javaFXModules.map( m => s"$JAVAFX_.$m") else Seq()
    val controls = if (hasControls) Seq(controlsFXModule)                 else Seq()
    val charts   = if (hasCharts)   Seq(HANSOLO_CHARTS_)                  else Seq()
    val logBack  = if (hasLogback)  Seq()                                 else Seq() // only path required
    val sl4J     = if (hasSLF4j)    Seq()                                 else Seq() // only path required

    showModuleNames( javafx, controls, charts,  logBack, sl4J )

    // Now combine all the module names
    val modulesNames = javafx ++ controls ++ charts ++ logBack ++ sl4J

    // Add to the modules list and paths for the command line arguments
    Seq(
        "--module-path", s.iterator.mkString(":"),
        "--add-modules", modulesNames.iterator.mkString(",")
        ) ++
    (
      if (hasControls)
        Seq("--add-exports=javafx.controls/com.sun.javafx.scene.control.behavior=org.controlsfx.controls",
            "--add-exports=javafx.controls/com.sun.javafx.scene.control.inputmap=org.controlsfx.controls",
            "--add-exports=javafx.graphics/com.sun.javafx.scene.traversal=org.controlsfx.controls") 
      else Seq()
    ) ++ 
      // add standard parameters
      Seq("-Dprism.verbose=true", "-ea")
  }

  /**
   * We test the use of a pure Java module that only uses Java libraries. 
   * Accordingly, `JavaTests` now checks for and fails if any Scala dependencies 
   * are used. Two workarounds exist. See the comments in the [[LibsScala]] 
   * module. Here we add the JAR version using the Java ivy directives as second
   * workaround solution.
   * 
   * Note: the use of requestion a specific version for the SBT jupiter interface
   * is not necessary. Simply adding `TestModule.Junit5` is enough as it already 
   * pulls in the required Scala API library. This servers only as an example.
   * 
   * @see https://github.com/com-lihaoyi/mill/issues/860
   * @see https://mvnrepository.com/artifact/com.github.sbt.junit
   */
  object jtest extends JavaTests with TestModule.Junit5 {

    //  Not required after version 0.10.0 of Mill 
    // see https://github.com/com-lihaoyi/mill/issues/1406
    // override def resolutionCustomizer: Task[Option[Resolution => Resolution]] = T.task {
    //   Some((_: coursier.core.Resolution).withOsInfo(coursier.core.Activation.Os.fromProperties(sys.props.toMap)))
    // }

    // Cannot be sued because IvyDeps checks: transitiveIvyDeps java.lang.AssertionError
    def ivyDeps = Agg(
    ivy"org.junit.jupiter:junit-jupiter-engine:5.11.0",
    //ivy"com.github.sbt.junit::sbt-jupiter-interface:0.13.0",
    ivy"com.github.sbt.junit:sbt-jupiter-interface_2.12_1.0:0.13.0",
    
    )
  }

}

object HelloWorldJava extends OpenJFX {
  
  override def mainClass: T[Option[String]] = Some("helloworld.HelloWorld")

  override def ivyDeps = Agg(
                              ivy"$CONTROLS",
                              ivy"$CONTROLSFX"
                             )
}

object HelloWorldScala extends OpenJFX with ScalaModule {
  def scalaVersion = T{ ScalaVersion }

  override def mainClass: T[Option[String]] = Some("helloworld.HelloWorld")

  override def ivyDeps = Agg(
                              ivy"$CONTROLS",
                              ivy"$CONTROLSFX",
                              ivyMunit
                             )

  /** 
   * Add support for Scala MUnit testing because it is not available from the 
   * Java `OpenJFX` 
   * */                           
  object test extends ScalaTests with TestModule.Munit  {
    def ivyDeps = Agg(ivyMunit) // not required
    //def testFramework = ivyMunitInterface 
  }
}

// JavaFX

object modernClients extends ScalaModule {
    def scalaVersion = T{ ScalaVersion }

  object HelloModernWorld extends OpenJFX with ScalaModule {
    def scalaVersion = T{ ScalaVersion }

    override def mainClass: T[Option[String]] = Some("sample.Main")

    override def ivyDeps = Agg(
                                ivy"$CONTROLS",
                                //ivy"$CONTROLSFX",
                                ivy"$FXML"
                              )

  }

  object HelloWorldScala extends OpenJFX with ScalaModule {
    def scalaVersion = T{ ScalaVersion }

    override def mainClass: T[Option[String]] = Some("helloworld.HelloWorld")

    override def ivyDeps = Agg(
                                ivy"$CONTROLS"
                                //ivy"$CONTROLSFX"
                              )

  }


  object `ch02-javafx_fundamentals` extends OpenJFX {
    object myshapes extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclient.MyShapes")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  //ivy"$CONTROLSFX",
                                  ivy"$FXML"
                                )

    }
    object myshapes2 extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclient.MyShapes2")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  //ivy"$CONTROLSFX",
                                  ivy"$FXML"
                                )

    }
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
                                  //ivy"$CONTROLSFX",
                                  //ivy"$HANSOLO_CHARTS",
                                  ivy"$FXML"
                                )

    }
    object myshapesfxml extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclient.MyShapesFXML")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  //ivy"$CONTROLSFX",
                                  ivy"$FXML"
                                )

    }
    object personui extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("com.modernclient.PersonUI")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  //ivy"$CONTROLSFX",
                                  ivy"$FXML"
                                )

    }

  }

  // https://stackoverflow.com/questions/21185156/javafx-on-linux-is-showing-a-graphics-device-initialization-failed-for-es2-s
  // locate -i libprism_es2.so
  // locate -i libglass.so
  // locate -i prism_sw.so   - this only appears in one of the versions 13.02
  // locate -i locate -i libgio-2.0.so.0
  // sudo apt-get install libgtk2.0-bin libXtst6 libxslt1.1
  /*
    GraphicsPipeline.createPipeline failed for com.sun.prism.es2.ES2Pipeline
java.lang.UnsatisfiedLinkError: no prism_es2 in java.library.path: [., /usr/local/cuda-11.2/lib64, /usr/java/packages/lib, /usr/lib/x86_64-linux-gnu/jni, /lib/x86_64-linux-gnu, /usr/lib/x86_64-linux-gnu, /usr/lib/jni, /lib, /usr/lib]

Prism pipeline name = com.sun.prism.sw.SWPipeline
GraphicsPipeline.createPipeline failed for com.sun.prism.sw.SWPipeline
java.lang.UnsatisfiedLinkError: no prism_sw in java.library.path: [., /usr/local/cuda-11.2/lib64, /usr/java/packages/lib, /usr/lib/x86_64-linux-gnu/jni, /lib/x86_64-linux-gnu, /usr/lib/x86_64-linux-gnu, /usr/lib/jni, /lib, /usr/lib]

/home/hmf/.openjfx/cache/13.0.2/libprism_es2.so
/home/hmf/.openjfx/cache/13/libprism_es2.so
/home/hmf/.openjfx/cache/16/libprism_es2.so
    
https://stackoverflow.com/questions/53382810/configure-openjfx-11-to-extract-its-dlls-into-a-different-user-specified-directo
// String jfxVersion = System.getProperty("javafx.version", "versionless")
// -Djavafx.cachedir=/tmp/foo 

https://www.gitmemory.com/issue/update4j/update4j/80/587583388


https://stackoverflow.com/questions/661320/how-to-add-native-library-to-java-library-path-with-eclipse-launch-instead-of
  */
  object hanSoloCharts extends OpenJFX with ScalaModule {
    def scalaVersion = T{ ScalaVersion }

    //override def javacOptions = Seq("-source", "1.8", "-target", "1.8", "-Xlint")
    //override def javacOptions = T{ Seq("-source", "11", "-target", "11", "-Xlint") }
    //override def scalacOptions = T{ Seq("-deprecation", "-feature") }

    // -Djdk.gtk.verbose=true -Djavafx.embed.singleThread=true -Dawt.useSystemAAFontSettings=on
    // -Djava.library.path
    override def forkArgs: Target[Seq[String]] = T {
      println(darkOrange(s"build.sc.${getClass.getSimpleName}.forkArgs"))

      //val t = Seq("-Dprism.verbose=true", "-Djavafx.verbose=true", "-ea") ++ // JavaFX
      val t = Seq("-Djavafx.verbose=true") ++  super[OpenJFX].forkArgs() //  OpenFX
      println(orange(t.mkString("\n")))
      // we do not have here the hansolo module, loading s not the same
      t
    }


    override def mainClass: T[Option[String]] = Some("hansolo.charts.LineChartTest")

    override def ivyDeps = Agg(
                                // TODO: required by charts only
                                ivy"org.slf4j:slf4j-api:2.0.16",
                                // TODO: for tests only?
                                // https://stackoverflow.com/questions/54777923/logback-in-a-java-9-modular-application-not-working
                                // https://logback.qos.ch/
                                // ivy"ch.qos.logback:logback-classic:1.3.0-alpha4",
                                ivy"ch.qos.logback:logback-classic:1.5.7",
                                ivy"$CONTROLS",
                                //ivy"$CONTROLSFX",      // TODO: bug - we should not need this
                                ivy"$HANSOLO_CHARTS" // ivyHanSoloCharts 
                              )

  }

  object hanSoloChartsStd extends OpenJFX with ScalaModule {
    def scalaVersion = T{ ScalaVersion }

    //override def javacOptions = Seq("-source", "1.8", "-target", "1.8", "-Xlint")
    //override def javacOptions = T{ Seq("-source", "11", "-target", "11", "-Xlint") }
    //override def scalacOptions = T{ Seq("-deprecation", "-feature") }

    // -Djdk.gtk.verbose=true -Djavafx.embed.singleThread=true -Dawt.useSystemAAFontSettings=on
    // -Djava.library.path
    override def forkArgs: Target[Seq[String]] = T {

      //val t = Seq("-Dprism.verbose=true", "-Djavafx.verbose=true", "-ea") ++ // JavaFX
      val t = Seq("-Djavafx.verbose=true") ++ // JavaFX
        super[OpenJFX].forkArgs() //  OpenFX
      println(t.mkString("\n"))
      // we do not have here the hansolo module, loading s not the same
      t
      Seq("-Dprism.verbose=true", "-Djavafx.verbose=true", "-ea")// JavaFX
    }


    override def mainClass: T[Option[String]] = Some("hansolo.charts.LineChartTest")

    override def ivyDeps = Agg(
                                ivy"$CONTROLS",
                                //ivy"$CONTROLSFX",      // TODO: bug - we should not need this
                                ivy"$HANSOLO_CHARTS" // ivyHanSoloCharts 
                              )

  }


  object `ch03-PropertiesBindings` extends OpenJFX {
    object arraychangeevent extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclients.propertiesandbindings.ArrayChangeEventExample")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  ivy"$FXML"
                                )

    }
    object numericproperties extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclients.propertiesandbindings.NumericPropertiesExample")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  ivy"$FXML"
                                )

    }

  }


  object `ch04-javafx_controls` extends OpenJFX {
    object basic extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclients.controls.Basic")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  ivy"$FXML"
                                )

    }

    object container extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclients.container.Container")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  ivy"$FXML"
                                )

    }

    // others, popup, dialog, advanced, selectionAndFocus

    object others extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclients.others.Others")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  ivy"$FXML",
                                  ivy"$WEB"
                                )

    }

    object popup extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclients.popup.Popup")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  ivy"$FXML"
                                )

    }

    object dialog extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclients.dialog.Dialog")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  ivy"$FXML"
                                )

    }

    object advanced extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclients.advanced.Advanced")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  ivy"$FXML"
                                )

    }

    object selectionAndFocus extends OpenJFX with ScalaModule {
      def scalaVersion = T{ ScalaVersion }

      override def mainClass: T[Option[String]] = Some("org.modernclients.selectionAndFocus.SelectionAndFocus")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  ivy"$FXML"
                                )

    }

  }


}
