
// cSpell:ignore javac, xlint
// cSpell:ignore scalalib, helloworld, coursier, Deps, unmanaged, classpath, JVM's, customizer, dprism
// cSpell:ignore javafx, controlsfx, openjfx, munit, myshapes, myshapesproperties, myshapesfxml
// cSpell:ignore hansolo, personui
// cSpell:ignore libprism, libglass, libgio, libgtk, xtst, libxslt, cuda, versionless, Djavafx, Djdk, Dawt, Djava

import coursier.core.Resolution
import mill._
import mill.api.Loose
import mill.define.{Target, Task}
import scalalib._

val ScalaVersion = "3.0.1"

//val javaFXVersion = "11.0.2"
//val javaFXVersion = "12"
//val javaFXVersion = "13.0.2"
val javaFXVersion = "16"

val mUnitVersion         = "0.7.27"
val controlsFXVersion    = "11.1.0"
//val hanSoloChartsVersion = "16.0.12" JDK16
val hanSoloChartsVersion = "11.7"


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
  //val HANSOLO_CHARTS_ = "eu.hansolo.fx.charts"
  val HANSOLO_CHARTS_ = "charts"


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
  val HANSOLO_CHARTS = s"eu.hansolo.fx:charts:$hanSoloChartsVersion"  // Java

  // OpenFX/JavaFX libraries
  val javaFXModuleNames = Seq(BASE_, CONTROLS_, FXML_, GRAPHICS_, MEDIA_, SWING_, WEB_)


  def jfx(s:String): String = s"javafx.$s"

  val javaFXModuleNamesX = Map(
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
Export-Package: eu.hansolo.fx.charts;uses:="eu.hansolo.fx.charts.data,
 eu.hansolo.fx.charts.event,eu.hansolo.fx.charts.series,eu.hansolo.fx.
 charts.tools,javafx.application,javafx.beans,javafx.beans.property,ja
 vafx.collections,javafx.geometry,javafx.scene,javafx.scene.input,java
 fx.scene.layout,javafx.scene.paint,javafx.stage";version="11.7.0",eu.
 hansolo.fx.charts.data;uses:="eu.hansolo.fx.charts,eu.hansolo.fx.char
 ts.event,eu.hansolo.fx.charts.tools,javafx.beans.property,javafx.scen
 e.paint";version="11.7.0",eu.hansolo.fx.charts.event;uses:="eu.hansol
 o.fx.charts.data,eu.hansolo.fx.charts.series,eu.hansolo.fx.charts.too
 ls";version="11.7.0",eu.hansolo.fx.charts.series;uses:="eu.hansolo.fx
 .charts,eu.hansolo.fx.charts.data,eu.hansolo.fx.charts.event,javafx.b
 eans.property,javafx.collections,javafx.scene.paint";version="11.7.0"
 ,eu.hansolo.fx.charts.tools;uses:="eu.hansolo.fx.charts,eu.hansolo.fx
 .charts.data,eu.hansolo.fx.charts.event,javafx.beans.property,javafx.
 event,javafx.geometry,javafx.scene,javafx.scene.canvas,javafx.scene.i
 nput,javafx.scene.paint,javafx.scene.shape,javafx.scene.text,javafx.s
 tage";version="11.7.0",eu.hansolo.fx.geometry;uses:="eu.hansolo.fx.ge
 ometry.tools,eu.hansolo.fx.geometry.transform,javafx.scene.canvas,jav
 afx.scene.paint";version="11.7.0",eu.hansolo.fx.geometry.tools;uses:=
 "eu.hansolo.fx.geometry,javafx.scene.paint";version="11.7.0",eu.hanso
 lo.fx.geometry.transform;uses:="eu.hansolo.fx.geometry,eu.hansolo.fx.
 geometry.tools";version="11.7.0"

 https://github.com/HanSolo/charts/blob/master/src/main/java/module-info.java
 exports eu.hansolo.fx.geometry;
    exports eu.hansolo.fx.geometry.tools;
    exports eu.hansolo.fx.geometry.transform;
    exports eu.hansolo.fx.charts;
    exports eu.hansolo.fx.charts.areaheatmap;
    exports eu.hansolo.fx.charts.color;
    exports eu.hansolo.fx.charts.converter;
    exports eu.hansolo.fx.charts.data;
    exports eu.hansolo.fx.charts.event;
    exports eu.hansolo.fx.charts.font;
    exports eu.hansolo.fx.charts.forcedirectedgraph;
    exports eu.hansolo.fx.charts.heatmap;
    exports eu.hansolo.fx.charts.pareto;
    exports eu.hansolo.fx.charts.series;
    exports eu.hansolo.fx.charts.tools;
    exports eu.hansolo.fx.charts.world;
*/

  val xxx = "eu.hansolo.fx.charts"

  /* TODO: we need a better way to identify modules in the JARs
  see: https://stackoverflow.com/questions/46616520/list-modules-in-jar-file
  see: https://www.daniweb.com/programming/software-development/threads/291837/best-way-executing-jar-from-java-code-then-killing-parent-java-code
  see: https://in.relation.to/2017/12/06/06-calling-jdk-tools-programmatically-on-java-9/
  see: https://www.pluralsight.com/guides/creating-opening-jar-files-java-programming-language
  see: https://stackoverflow.com/questions/320510/viewing-contents-of-a-jar-file
  see: https://www.baeldung.com/java-compress-and-uncompress
  see: https://github.com/srikanth-lingala/zip4j
  // List of modules (note that a single Jar may have ore than one module)
  val modules = javaFXModuleNames.map(n => n -> s"org.openjfx:javafx-$n:$javaFXVersion") // OpenFX
                                  .toMap 
                ++  // Other modules
                Map( "controlsfx" -> s"org.controlsfx:controlsfx:$controlsFXVersion")    // ControlsFX
  println(modules)
  */

  // Standard libraries

  // TODO: after version 0.10.0 iof Mill put test in the managed/unmanaged classes
  val ivyMunit          = ivy"org.scalameta::munit::$mUnitVersion"
  val ivyMunitInterface = "munit.Framework"


  /**
   * In order to use OS specific libraries (such as JavaFX or OpenJFX), we
   * must set-up the OS flags appropriately for Maven download via Coursier.
   * This is only available **after** version **0.9.6** of Mill.
   *
   * @see https://github.com/com-lihaoyi/mill/pull/775 (commit ab4d61a)
   * @return OS specific resolution mapping
   */
  override def resolutionCustomizer: Task[Option[Resolution => Resolution]] = T.task {
    Some((_: coursier.core.Resolution).withOsInfo(coursier.core.Activation.Os.fromProperties(sys.props.toMap)))
  }

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
    // get the managed libraries
    val allLibs: Loose.Agg[PathRef] = runClasspath()
    val strLibs = allLibs.map(_.path.toString())
    // get the OpenJFX and related managed libraries
    val s: Loose.Agg[String] = allLibs.map(_.path.toString())
                                      .filter{
                                         s =>
                                           val t = s.toLowerCase()
                                           t.contains("javafx") || t.contains("controlsfx") || t.contains("hansolo")
                                        }
    println(s.mkString("!\n"))                          
    val hasControls = strLibs.filter{ s => s.toLowerCase.contains("controlsfx") }.size > 0
    val hasCharts = strLibs.filter{ s => s.toLowerCase.contains("hansolo") }.size > 0
    println(if (hasControls) Seq(controlsFXModule) else Seq())
    println(if (hasCharts) Seq(HANSOLO_CHARTS_) else Seq())

    // Create the JavaFX module names (convention is amenable to automation)
    import scala.util.matching.Regex

    // First get the javaFX only libraries
    val javaFXLibs = raw".*javafx-(.+?)-.*".r
    val javaFXModules = s.iterator.map(m => javaFXLibs.findFirstMatchIn(m).map(_.group(1)) )
                      .toSet
                      .filter(_.isDefined)
                      .map(_.get)
    // Now generate the module names
    val modulesNames = javaFXModules.map( m => s"javafx.$m") ++
                          // no standard convention, so add it manually
                          (if (hasControls) Seq(controlsFXModule) else Seq()) ++
                          (if (hasCharts) Seq(HANSOLO_CHARTS_) else Seq())

    println(modulesNames.mkString("?\n"))                          
    // Experiment


    // Add to the modules list
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


  // TODO: after version 0.10.0 of Mill put test in the managed/unmanaged classes
  object test extends Tests {

    // TODO: after version 0.10.0 of Mill remove this
    // sse https://github.com/com-lihaoyi/mill/issues/1406
    override def resolutionCustomizer: Task[Option[Resolution => Resolution]] = T.task {
      Some((_: coursier.core.Resolution).withOsInfo(coursier.core.Activation.Os.fromProperties(sys.props.toMap)))
    }

    // https://github.com/com-lihaoyi/mill#097---2021-05-14
    //def testFrameworks = Seq(ivyMunitInterface)
    def testFramework = ivyMunitInterface
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
                              ivy"$CONTROLSFX"
                             )
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

      //val t = Seq("-Dprism.verbose=true", "-Djavafx.verbose=true", "-ea") ++ // JavaFX
      val t = Seq("-Djavafx.verbose=true") ++ // JavaFX
        super[OpenJFX].forkArgs() //  OpenFX
      println(t.mkString("\n"))
      // we do not have here the hansolo module, loading s not the same
      t
    }


    override def mainClass: T[Option[String]] = Some("hansolo.charts.LineChartTest")

    override def ivyDeps = Agg(
                                ivy"$CONTROLS",
                                //ivy"$CONTROLSFX",      // TODO: bug - we should not need this
                                ivy"$HANSOLO_CHARTS" // ivyHanSoloCharts 
                              )

  }


object modernClients extends ScalaModule {
    def scalaVersion = T{ ScalaVersion }

  object HelloWorldScala extends OpenJFX with ScalaModule {
    def scalaVersion = T{ ScalaVersion }

    override def mainClass: T[Option[String]] = Some("helloworld.HelloWorld")

    override def ivyDeps = Agg(
                                ivy"$CONTROLS"
                                //ivy"$CONTROLSFX"
                              )

  }

  object HelloModernWorld extends OpenJFX with ScalaModule {
    def scalaVersion = T{ ScalaVersion }

    override def mainClass: T[Option[String]] = Some("helloworld.HelloWorld")

    override def ivyDeps = Agg(
                                ivy"$CONTROLS",
                                //ivy"$CONTROLSFX",
                                ivy"$FXML"
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
                                  ivy"$HANSOLO_CHARTS",
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

      override def mainClass: T[Option[String]] = Some("org.modernclient.PersonUI")

      override def ivyDeps = Agg(
                                  ivy"$CONTROLS",
                                  //ivy"$CONTROLSFX",
                                  ivy"$FXML"
                                )

    }

  }


}
