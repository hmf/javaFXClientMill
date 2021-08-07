package org.modernclients.propertiesandbindings

// cSpell:ignore javafx, ints, stackoverflow, nullpointerexception


import javafx.collections.FXCollections
import javafx.collections.ObservableIntegerArray
import javafx.collections.ObservableList
import javafx.collections.ObservableMap
import javafx.collections.ObservableSet
import javafx.collections.ObservableFloatArray
import javafx.collections.ListChangeListener
import javafx.collections.MapChangeListener
import javafx.collections.SetChangeListener
import javafx.collections.ArrayChangeListener
import java.util.Comparator
import java.util.Random
import java.util.Arrays
import javafx.beans.Observable
import javafx.collections.ListChangeListener.Change

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

    def ArrayChangeEventExample: Unit = {
        println("\n\nArrayChangeEventExample")

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

    def FXCollectionsExample: Unit = {
        println("\n\nFXCollectionsExample")

        val list : ObservableList[String]         = FXCollections.observableArrayList()
        val map  : ObservableMap[String, String]  = FXCollections.observableHashMap()
        val set  : ObservableSet[Integer]         = FXCollections.observableSet()
        val array: ObservableFloatArray           = FXCollections.observableFloatArray()

        list.addListener( (c => println("\tlist = " + c.getList() )): ListChangeListener[String] )
        //list.addListener( c => println("\tlist = " + c.getList() ))

        map.addListener( (c => println("\tmap = " + c.getMap())): MapChangeListener[String, String] )

        set.addListener( (c => println("\tset = " + c.getSet())): SetChangeListener[Integer] )

        //array.addListener((observableArray, sizeChanged, from, to) => println("\tarray = " + observableArray) )
        array.addListener( ( (observableArray, sizeChanged, from, to) => println("\tarray = " + observableArray)): ArrayChangeListener[ObservableFloatArray] )

        manipulateList(list)
        manipulateMap(map)
        manipulateSet(set)
        manipulateArray(array)
    }

    def manipulateList(list: ObservableList[String]): Unit = {
        println("Calling list.addAll(\"Zero\"," +" \"One\", \"Two\", \"Three\"):")
        list.addAll("Zero", "One", "Two", "Three")

        println("Calling copy(list," +" Arrays.asList(\"Four\", \"Five\")):")
        FXCollections.copy(list, Arrays.asList("Four", "Five"))

        println("Calling replaceAll(list," + " \"Two\", \"Two_1\"):")
        FXCollections.replaceAll(list, "Two", "Two_1")

        println("Calling reverse(list):")
        FXCollections.reverse(list)

        println("Calling rotate(list, 2):")
        FXCollections.rotate(list, 2)

        println("Calling shuffle(list):");
        FXCollections.shuffle(list)

        println("Calling shuffle(list," + " new Random(0L)):")
        FXCollections.shuffle(list, new Random(0L))

        println("Calling sort(list):")
        FXCollections.sort(list)

        println("Calling sort(list, c)" + " with custom comparator: ")
        FXCollections.sort(list, new Comparator[String]() {
                                       override def compare(lhs: String, rhs: String) = {
                                                        // Reverse the order
                                                        rhs.compareTo(lhs) 
                                                    }
                                                }
                            )

        println("Calling fill(list," + " \"Ten\"): ")
        FXCollections.fill(list, "Ten")
    }

    def manipulateMap(map: ObservableMap[String, String]) = {
        println("Calling map.put(\"Key\"," + " \"Value\"):")
        map.put("Key", "Value");
    }

    def manipulateSet(set: ObservableSet[Integer]) = {
        println("Calling set.add(1024):")
        set.add(1024)
    }

    def manipulateArray(array: ObservableFloatArray) = {
        println("Calling  array.addAll(3.14159f," + " 2.71828f):")
        array.addAll(3.14159f, 2.71828f)
    }

    def ObservableListExample: Unit = {
        println("\n\nFXCollectionsExample")

        val strings: ObservableList[String] = FXCollections.observableArrayList()

        strings.addListener((observable: Observable) => println("\tlist invalidated") )
        strings.addListener((change: Change[_ <: String]) => println("\tstrings = " + change.getList()) )

        println("Calling add(\"First\"): ")
        strings.add("First")
        println("Calling add(0, \"Zeroth\"): ")
        strings.add(0, "Zeroth")

        println("Calling addAll(\"Second\"," + " \"Third\"): ")
        strings.addAll("Second", "Third")

        println("Calling set(1," + " \"New First\"): ")
        strings.set(1, "New First")

        val list: java.util.List[String] = Arrays.asList("Second_1", "Second_2")
        println("Calling addAll(3, list): ")
        strings.addAll(3, list)

        println("Calling remove(2, 4): ")
        strings.remove(2, 4)

        val iterator: java.util.Iterator[String] = strings.iterator()
        while (iterator.hasNext()) {
            val next: String = iterator.next()
            if (next.contains("t")) {
                println("Calling remove()" + " on iterator: ")
                iterator.remove()
            }
        }
        println("Calling removeAll(" + "\"Third\", \"Fourth\"): ")
        strings.removeAll("Third", "Fourth")
    }

    def main(args: Array[String]): Unit = {
        ArrayChangeEventExample
        FXCollectionsExample
        ObservableListExample
    }
}

