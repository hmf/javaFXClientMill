package org.modernclients.propertiesandbindings

// cSpell:ignore javafx, ints, stackoverflow, nullpointerexception


import javafx.collections.FXCollections
import javafx.collections.ObservableIntegerArray

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch03-PropertiesBindings.arraychangeevent.run
 * ./mill -i modernClients.ch03-PropertiesBindings.arraychangeevent.runMain org.modernclients.propertiesandbindings.ArrayChangeEventExample
 * ./mill -i --watch modernClients.ch03-PropertiesBindings.arraychangeevent.runMain org.modernclients.propertiesandbindings.ArrayChangeEventExample
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
object ArrayChangeEventExample {

    def main(args: Array[String]): Unit = {

        val ints: ObservableIntegerArray =
                FXCollections.observableIntegerArray(10, 20)

        ints.addListener(
              (array, sizeChanged, from, to) => {
                 val sb = StringBuilder("\tObservable Array = ")
                                 .append(array)
                                 .append("\n")
                                 .append("\t\tsizeChanged = ")
                                 .append(sizeChanged).append("\n")
                                 .append("\t\tfrom = ")
                                 .append(from).append("\n")
                                 .append("\t\tto = ")
                                 .append(to)
                                 .append("\n")
                 println(sb.toString())
             } )

        ints.ensureCapacity(20)

        println("Calling addAll(30, 40):")
        ints.addAll(30, 40)

        val src = Array(50, 60, 70)
        println("Calling addAll(src, 1, 2):")
        ints.addAll(src, 1, 2)

        println("Calling set(0, src, 0, 1):")
        ints.set(0, src, 0, 1)

        println("Calling setAll(src):")
        ints.setAll(src:_*)

        ints.trimToSize()

        val ints2: ObservableIntegerArray =
                FXCollections.observableIntegerArray()
        ints2.resize(ints.size())

        println("Calling copyTo(0, ints2," +
                " 0, ints.size()):")
        ints.copyTo(0, ints2, 0, ints.size())

        println("\tDestination = " + ints2)
    }
}

