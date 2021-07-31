package org.modernclients.propertiesandbindings

// cSpell:ignore javafx, stackoverflow, nullpointerexception

import javafx.beans.property.DoubleProperty
import javafx.beans.property.FloatProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.LongProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleFloatProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.InvalidationListener
import javafx.beans.value.ChangeListener

/**
 *
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i modernClients.ch03-PropertiesBindings.numericproperties.run
 * ./mill -i modernClients.ch03-PropertiesBindings.numericproperties.runMain org.modernclients.propertiesandbindings.NumericPropertiesExample
 * ./mill -i --watch modernClients.ch03-PropertiesBindings.numericproperties.runMain org.modernclients.propertiesandbindings.NumericPropertiesExample
 * 
 * Note on resources (see StackOverflow link below) Mill's convention is to 
 * place a resources directory on the lowest level Mill module. To access 
 * these resources one must use the path relative to the application (Mill 
 * module) and not the class (because resources are not copied to the compiled 
 * class directory).
 * 
 * If you want to keep the resources next to the classes, these would require
 * you change Mill behavior to copy them, or do it yourself. 
 * 
 * @see https//stackoverflow.com/questions/22000423/javafx-and-maven-nullpointerexception-location-is-required
 * @see https//stackoverflow.com/questions/12124657/getting-started-on-scala-javafx-desktop-application-development
 */
object NumericPropertiesExample {

    def main(args: Array[String]) = {
        val i: IntegerProperty = SimpleIntegerProperty(null, "i", 1024)
        val l: LongProperty    = SimpleLongProperty(null, "l", 0L)
        val f: FloatProperty   = SimpleFloatProperty(null, "f", 0.0F)
        val d: DoubleProperty  = SimpleDoubleProperty(null, "d", 0.0)

        // Extras
        val invalidationListener: InvalidationListener = observable => {
                println( "The observable has been " + 
                         "invalidated: " +
                         observable + ".")
                } 

        val changeListener: ChangeListener[Number] = 
                (observableValue, oldValue, newValue) => {
                        println( "The observableValue has " +
                                 "changed: oldValue = " + oldValue +
                                 ", newValue = " + newValue )
        }

        println("Constructed numerical" +
                " properties i, l, f, d.")

        println("i.get() = " + i.get())
        println("l.get() = " + l.get())
        println("f.get() = " + f.get())
        println("d.get() = " + d.get())

        d.addListener(invalidationListener)
        d.addListener(changeListener)

        l.bind(i)
        f.bind(l)
        d.bind(f)
        println("Bound l to i, f to l, d to f.")

        println("i.get() = " + i.get())
        println("l.get() = " + l.get())
        println("f.get() = " + f.get())
        println("d.get() = " + d.get())

        println("Calling i.set(2048).")
        i.set(2048)

        println("i.get() = " + i.get())
        println("l.get() = " + l.get())
        println("f.get() = " + f.get())
        println("d.get() = " + d.get())

        d.unbind()
        f.unbind()
        l.unbind()
        println("Unbound l to i, f to l, d to f.")

        f.bind(d)
        l.bind(f)
        i.bind(l)
        println("Bound f to d, l to f, i to l.")

        println("Calling d.set(10000000000L).")
        d.set(10000000000L)

        println("d.get() = " + d.get())
        println("f.get() = " + f.get())
        println("l.get() = " + l.get())
        println("i.get() = " + i.get())
    }
}

