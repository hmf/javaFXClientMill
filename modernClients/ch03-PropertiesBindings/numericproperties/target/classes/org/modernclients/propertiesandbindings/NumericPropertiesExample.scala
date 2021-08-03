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
import javafx.beans.binding.NumberBinding
import javafx.beans.binding.Bindings
import javafx.beans.binding.IntegerBinding
import javafx.beans.binding.StringExpression

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

    def NumericPropertiesExample: Unit = {
        println("\n\nNumericPropertiesExample")

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
    }

    def BidirectionalBindingExample: Unit = {
        println("\n\nBidirectionalBindingExample")
        println("Constructing two StringProperty objects.")

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

        println("\n\nDirectExtensionExample")
        println("Constructing x with value 2.0.")
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

    def TriangleAreaExample: Unit = {
         
        val x1: IntegerProperty = new SimpleIntegerProperty(0)
        val y1: IntegerProperty = new SimpleIntegerProperty(0)
        val x2: IntegerProperty = new SimpleIntegerProperty(0)
        val y2: IntegerProperty = new SimpleIntegerProperty(0)
        val x3: IntegerProperty = new SimpleIntegerProperty(0)
        val y3: IntegerProperty = new SimpleIntegerProperty(0)

        val x1y2: NumberBinding = Bindings.multiply(x1, y2)
        val x2y3: NumberBinding = Bindings.multiply(x2, y3)
        val x3y1: NumberBinding = Bindings.multiply(x3, y1)
        val x1y3: NumberBinding = Bindings.multiply(x1, y3)
        val x2y1: NumberBinding = Bindings.multiply(x2, y1)
        val x3y2: NumberBinding = Bindings.multiply(x3, y2)

        val sum1       : NumberBinding  = Bindings.add(x1y2, x2y3)
        val sum2       : NumberBinding  = Bindings.add(sum1, x3y1)
        val sum3       : NumberBinding  = Bindings.add(sum2, x3y1)
        val diff1      : NumberBinding  = Bindings.subtract(sum3, x1y3)
        val diff2      : NumberBinding  = Bindings.subtract(diff1, x2y1)
        val determinant: NumberBinding  = Bindings.subtract(diff2, x3y2)
        val area       : NumberBinding  = Bindings.divide(determinant, 2.0D)

        println("\n\nTriangleAreaExample")
        x1.set(0) ; y1.set(0)
        x2.set(6) ; y2.set(0)
        x3.set(4) ; y3.set(3)
        printResult(x1, y1, x2, y2, x3, y3, area)
        println(s"Area = $area")

        x1.set(1) ; y1.set(0)
        x2.set(2) ; y2.set(2)
        x3.set(0) ; y3.set(1)
        printResult(x1, y1, x2, y2, x3, y3, area)

        x1.set(2) ; y1.set(3)
        val area1: DoubleBinding = Bindings.createDoubleBinding( () => { x1.get() * y1.get() }, x1, y1)
        //val area1: IntegerBinding = Bindings.createIntegerBinding( () => { x1.get() * y1.get() }, x1, y1)
        //val area1: IntegerBinding = Bindings.createIntegerBinding( () => { x1.get() * y1.get() })
        println(s"Area1 = ${area1.get}")

    }

    def TriangleAreaFluentExample: Unit = {
        println("\n\nTriangleAreaFluentExample")

        val x1: IntegerProperty = new SimpleIntegerProperty(0)
        val y1: IntegerProperty = new SimpleIntegerProperty(0)
        val x2: IntegerProperty = new SimpleIntegerProperty(0)
        val y2: IntegerProperty = new SimpleIntegerProperty(0)
        val x3: IntegerProperty = new SimpleIntegerProperty(0)
        val y3: IntegerProperty = new SimpleIntegerProperty(0)

        val area: NumberBinding = x1.multiply(y2)
                                    .add(x2.multiply(y3))
                                    .add(x3.multiply(y1))
                                    .subtract(x1.multiply(y3))
                                    .subtract(x2.multiply(y1))
                                    .subtract(x3.multiply(y2))
                                    .divide(2.0D)

        val output: StringExpression = Bindings.format(
                                                  "For A(%d,%d), B(%d,%d), C(%d,%d)," +
                                                      " the area of triangle ABC is %3.1f",
                                                   x1, y1, x2, y2, x3, y3, area)
        x1.set(0) ; y1.set(0)
        x2.set(6) ; y2.set(0)
        x3.set(4) ; y3.set(3)
        println(output.get())

        x1.set(1) ; y1.set(0)
        x2.set(2) ; y2.set(2)
        x3.set(0) ; y3.set(1)
        println(output.get())
    }

    def HeronsFormulaExample: Unit = {
        DoubleProperty a = new SimpleDoubleProperty(0);
        DoubleProperty b = new SimpleDoubleProperty(0);
        DoubleProperty c = new SimpleDoubleProperty(0);
        DoubleBinding s = a.add(b).add(c).divide(2.0d);
        final DoubleBinding areaSquared = new When(
                a.add(b).greaterThan(c)
                        .and(b.add(c).greaterThan(a))
                        .and(c.add(a).greaterThan(b)))
                .then(s.multiply(s.subtract(a))
                        .multiply(s.subtract(b))
                        .multiply(s.subtract(c)))
                .otherwise(0.0D);
        a.set(3);
        b.set(4);
        c.set(5);
        System.out.printf("Given sides a = %1.0f," +
                        " b = %1.0f, and c = %1.0f," +
                        " the area of the triangle is" +
                        " %3.2f\n", a.get(), b.get(), c.get(),
                Math.sqrt(areaSquared.get()));
        a.set(2);
        b.set(2);
        c.set(2);
        System.out.printf("Given sides a = %1.0f," +
                        " b = %1.0f, and c = %1.0f," +
                        " the area of the triangle is" +
                        " %3.2f\n", a.get(), b.get(), c.get(),
                Math.sqrt(areaSquared.get()));
    }


public class HeronsFormulaDirectExtensionExample {
    public static void main(String[] args) {
        final DoubleProperty a = new SimpleDoubleProperty(0);
        final DoubleProperty b = new SimpleDoubleProperty(0);
        final DoubleProperty c = new SimpleDoubleProperty(0);
        DoubleBinding area = new DoubleBinding() {
            {
                super.bind(a, b, c);
            }


            @Override
            protected double computeValue() {
                double a0 = a.get();
                double b0 = b.get();
                double c0 = c.get();
                if ((a0 + b0 > c0) && (b0 + c0 > a0) &&
                        (c0 + a0 > b0)) {
                    double s = (a0 + b0 + c0) / 2.0D;
                    return Math.sqrt(s * (s - a0) *
                            (s - b0) * (s - c0));
                } else {
                    return 0.0D;
                }
            }
        };
        a.set(3);
        b.set(4);
        c.set(5);
        System.out.printf("Given sides a = %1.0f," +
                        " b = %1.0f, and c = %1.0f," +
                        " the area of the triangle" +
                        " is %3.2f\n", a.get(), b.get(),
                c.get(), area.get());
        a.set(2);
        b.set(2);
        c.set(2);
        System.out.printf("Given sides a = %1.0f," +
                        " b = %1.0f, and c = %1.0f," +
                        " the area of the triangle" +
                        " is %3.2f\n", a.get(), b.get(),
                c.get(), area.get());
    }

    def printResult( x1:   IntegerProperty,
                     y1:   IntegerProperty,
                     x2:   IntegerProperty,
                     y2:   IntegerProperty,
                     x3:   IntegerProperty,
                     y3:   IntegerProperty,
                     area: NumberBinding) = {
        println( "For A(" +
                  x1.get() + "," + y1.get() + "), B(" +
                  x2.get() + "," + y2.get() + "), C(" +
                  x3.get() + "," + y3.get() +
                  "), the area of triangle ABC is " +
                  area.getValue()
                  )
     }

    def main(args: Array[String]) = {

        NumericPropertiesExample
        BidirectionalBindingExample
        DirectExtensionExample
        TriangleAreaExample
        TriangleAreaFluentExample
    }
}

