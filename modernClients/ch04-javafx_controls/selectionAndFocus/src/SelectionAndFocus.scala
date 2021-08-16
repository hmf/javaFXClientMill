package org.modernclients.selectionAndFocus

// cSpell:ignore javafx

import collection.JavaConverters._

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch04-javafx_controls.selectionAndFocus.run
 * ./mill -i modernClients.ch04-javafx_controls.selectionAndFocus.runMain org.modernclients.selectionAndFocus.SelectionAndFocus
 * ./mill -i --watch modernClients.ch04-javafx_controls.selectionAndFocus.runMain org.modernclients.selectionAndFocus.SelectionAndFocus
 * 
 * Note on resources (see StackOverflow link below): Mill's convention is to 
 * place a resources directory on the lowest level Mill module. To access 
 * these resources one must use the path relative to the application (Mill 
 * module) and not the class (because resources are not copied to the compiled 
 * class directory).
 * 
 * If you want to keep the resources next to the classes, these would require
 * you change Mill behavior to copy them, or do it yourself. 
 * 
 * @see https://stackoverflow.com/questions/22000423/javafx-and-maven-nullpointerexception-location-is-required
 * @see https://stackoverflow.com/questions/12124657/getting-started-on-scala-javafx-desktop-application-development
 */
object SelectionAndFocus {

    def main(args: Array[String]): Unit = {
      println("Hello selection & focus")
    }
}

