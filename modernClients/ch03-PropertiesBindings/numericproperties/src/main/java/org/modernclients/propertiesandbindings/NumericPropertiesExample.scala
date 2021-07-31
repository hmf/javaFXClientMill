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
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.beans.binding.DoubleBinding

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

    def BidirectionalBindingExample: Unit = {
        println("\n\nConstructing two StringProperty objects.")

        val prop1: StringProperty = SimpleStringProperty("")
        val prop2: StringProperty = SimpleStringProperty("")

        println("Calling bindBidirectional.")
        prop2.bindBidirectional(prop1)
        println("prop1.isBound() = " + prop1.isBound())
        println("prop2.isBound() = " + prop2.isBound())

        println("\nCalling prop1.set(\"prop1" + " says: Hi!\")")
        prop1.set("prop1 says: Hi!")
        println("prop2.get() returned: " + prop2.get())

        println("Calling prop2.set(prop2.get()" + " + \"\\nprop2 says: Bye!\")")
        prop2.set(prop2.get() + "\nprop2 says: Bye!")
        println("prop1.get() returned:")
        println(prop1.get())
    }

    def DirectExtensionExample: Unit = {
        println("\n\nConstructing x with value 2.0.")
        val x: DoubleProperty= SimpleDoubleProperty(null, "x", 2.0)

        println("Constructing y with value 3.0.")
        val y: DoubleProperty = SimpleDoubleProperty(null, "y", 3.0)

        println("Creating binding area" +
                " with dependencies x and y.")

        val area: DoubleBinding = 
                new DoubleBinding() {
                        super.bind(x, y)

                        override def computeValue(): Double = {
                                println("computeValue()" +
                                           " is called.")
                                x.get() * y.get()
                        }
                }

        println("area.get() = " + area.get())
        println("area.get() = " + area.get())
        println("Setting x to 5")
        x.set(5)
        println("Setting y to 7")
        y.set(7)
        println("area.get() = " + area.get())
    }


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

        // This lazy value evaluation is what makes the JavaFX properties and 
        // bindings framework efficient. Attaching a ChangeListener forces eager
        // evaluation
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

        BidirectionalBindingExample
        DirectExtensionExample
    }
}

