// cSpell:ignore javafx, hansolo

package hansolo.charts

import eu.hansolo.fx.charts.forcedirectedgraph.GraphEdge
import eu.hansolo.fx.charts.forcedirectedgraph.GraphNode
import eu.hansolo.fx.charts.forcedirectedgraph.GraphPanel
import eu.hansolo.fx.charts.forcedirectedgraph.NodeEdgeModel
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.ColorPicker
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.control.RadioButton
import javafx.scene.control.Slider
import javafx.scene.control.ToggleGroup
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage


import javafx.beans.Observable
import javafx.beans.value.ObservableObjectValue
import javafx.beans.value.ObservableValue

import java.util.ArrayList
import java.util.HashMap
import java.util.List


import scala.compiletime.uninitialized
import scala.jdk.CollectionConverters.*
import java.{util => ju}

/**
 * 
 * authors: Michael L\u00E4uchli, MLaeuchli (github)
 *          Stefan Mettler, orizion (github)
 * 
 * ./mill mill.scalalib.GenIdea/idea
 *
 * ./mill -i hansolo.graph.run
 * ./mill -i hansolo.graph.runMain hansolo.charts.ForceDirectedGraphTest
 * ./mill -i --watch hansolo.graph.runMain hansolo.charts.ForceDirectedGraphTest
 * 
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
 * 
 * @see https://stackoverflow.com/questions/22000423/javafx-and-maven-nullpointerexception-location-is-required
 * @see https://stackoverflow.com/questions/12124657/getting-started-on-scala-javafx-desktop-application-development
 * @see https://github.com/HanSolo/charts/blob/master/src/test/java/eu/hansolo/fx/charts/LineChartTest.java
 */
class ForceDirectedGraphTest extends Application {

    private var  graph: GraphPanel = uninitialized
    private var  presentationPanel: PresentationPanel = uninitialized

    override def init() = {
        graph = new GraphPanel()
        presentationPanel = new PresentationPanel()
    }

    override def start(stage: Stage) = {
        val pane = new StackPane(presentationPanel)
        //StackPane pane = new StackPane(graph)
        val scene = new Scene(pane)

        stage.setTitle("Force Directed Graph")
        stage.setScene(scene)
        stage.show()
    }

    override def stop() = {
        System.exit(0)
    }

    // public static void main(String[] args) {
    //     launch(args)
    // }


    class PresentationPanel extends BorderPane {
        private var graphPanel: GraphPanel = uninitialized

        private var sliderBox: VBox = uninitialized

        private var nodeSizeFactor: Slider = uninitialized
        private var edgeWidthFactor: Slider = uninitialized

        private var dataChanger: ComboBox[String] = uninitialized

        private var groupComboBox: ComboBox[String] = uninitialized
        private var colorSchemeComboBox: ComboBox[String] = uninitialized
        private var nodeValueComboBox: ComboBox[String] = uninitialized

        private var edgeForceValueComboBox: ComboBox[String] = uninitialized
        private var edgeWidthValueComboBox: ComboBox[String] = uninitialized

        private var borderColorPicker: ColorPicker = uninitialized
        private var edgeColorPicker: ColorPicker = uninitialized

        private var alwaysNormalize: RadioButton = uninitialized
        private var neverNormalize: RadioButton = uninitialized
        private var normalizeIfBetweenZeroAndOne: RadioButton = uninitialized

        private var normalizeGroup: ToggleGroup = uninitialized

        private var physic: CheckBox = uninitialized
        private var invertedForce: CheckBox = uninitialized

        private var calculateDegreeCentrality: Button = uninitialized
        private var calculateClosenessCentrality: Button = uninitialized
        private var calculateBetweennessCentrality: Button = uninitialized
        private var restartAnimation: Button = uninitialized

        private var nodeEdgeModel: NodeEdgeModel = uninitialized
        private var dataGenerator: DataGenerator = uninitialized

        private var colors: VBox = uninitialized

        initializeParts()
        layoutParts()
        setupBindings()
        setupListener()
        layoutSelf()

        private def initializeParts(): Unit = {
            dataGenerator = new DataGenerator()
            dataGenerator.generateGraphWithMinimalInformation()

            nodeEdgeModel = new NodeEdgeModel(dataGenerator.getNodes(), dataGenerator.getEdges())
            nodeEdgeModel.addColorTheme(createExampleColorTheme(),"Example_1")
            nodeEdgeModel.addColorTheme(createExampleColorTheme2(), "Example_2")
            nodeEdgeModel.setCurrentColorThemeKey("Kelly Color")
            graphPanel = new GraphPanel(nodeEdgeModel)


            sliderBox = new VBox()

            nodeSizeFactor  = new Slider(5,30,10)
            edgeWidthFactor = new Slider(0.5,10,5)

            groupComboBox          = new ComboBox[String]()
            nodeValueComboBox      = new ComboBox[String]()
            edgeForceValueComboBox = new ComboBox[String]()
            edgeWidthValueComboBox = new ComboBox[String]()

            colorSchemeComboBox = new ComboBox[String]()
            colorSchemeComboBox.getItems().addAll(graphPanel.getNodeEdgeModel().getColorThemeKeys())

            normalizeGroup = new ToggleGroup()
            alwaysNormalize = new RadioButton("Always Normalize")
            neverNormalize = new RadioButton("Never Normalize")
            normalizeIfBetweenZeroAndOne = new RadioButton("Normalize if Between 0 and 1")
            alwaysNormalize.setToggleGroup(normalizeGroup)
            neverNormalize.setToggleGroup(normalizeGroup)
            normalizeIfBetweenZeroAndOne.setToggleGroup(normalizeGroup)
            alwaysNormalize.setSelected(true)
            physic = new CheckBox("Disable Physics")
            physic.selectedProperty().setValue(true)
            invertedForce = new CheckBox("inverted")

            calculateDegreeCentrality = new Button("Calculate Degreecentrality")
            calculateClosenessCentrality = new Button("Calculate ClossenessCentrality")
            calculateBetweennessCentrality = new Button("Calculate BetweennessCentrality")
            restartAnimation = new Button("Restart Animation")

            borderColorPicker = new ColorPicker()
            edgeColorPicker = new ColorPicker()
            edgeColorPicker.setValue(Color.LIGHTGRAY)

            groupComboBox.getItems().addAll(graphPanel.getNodeEdgeModel().getStringAttributeKeysOfNodes())
            nodeValueComboBox.getItems().addAll(graphPanel.getNodeEdgeModel().getNumericAttributeKeysOfNodes())
            edgeForceValueComboBox.getItems().addAll(graphPanel.getNodeEdgeModel().getNumericAttributeKeysOfEdges())
            edgeWidthValueComboBox.getItems().addAll(graphPanel.getNodeEdgeModel().getNumericAttributeKeysOfEdges())

            //edgeForceValueComboBox.getItems().addAll(nodeEdgeModel)

            dataChanger = new ComboBox[String]()
            dataChanger.getItems().addAll("D3Example","FoodExport")

            colors = new VBox()




            refreshColorPickers()


        }

        private def layoutParts(): Unit = {
            sliderBox.getChildren().clear()
            sliderBox.getChildren().addAll(new Label("Size Factor"), nodeSizeFactor,
                                           new Label("Edge With Factor:"), edgeWidthFactor,
                                           new Label("Grouped By"), groupComboBox,
                                           new Label("NodeBorderColor"), borderColorPicker,
                                           new Label("Group Colors"), colors,
                                           new Label("ColorTheme"), colorSchemeComboBox,
                                           new Label("Edge Color"), edgeColorPicker,
                                           new Label("Node Size Attribute"), nodeValueComboBox,
                                           new Label("Edge Width Attribute"), edgeWidthValueComboBox,
                                           new Label("Edge Force Attribute"), edgeForceValueComboBox,
                                           new Label("Force inverted"), invertedForce,
                                           new Label("Normalization Behavior:"), alwaysNormalize, neverNormalize, normalizeIfBetweenZeroAndOne,
                                           new Label("Enable Phisics"), physic,
                                           new Label("Calculations"), calculateDegreeCentrality, calculateClosenessCentrality, calculateBetweennessCentrality, restartAnimation,
                                           new Label("Select Data"), dataChanger)

        }

        private def setupBindings(): Unit = {
            graphPanel.nodeSizeFactorProperty().bindBidirectional(nodeSizeFactor.valueProperty())

            graphPanel.edgeWidthFactorProperty().bindBidirectional(edgeWidthFactor.valueProperty())
            graphPanel.forceInvertedProperty().bindBidirectional(invertedForce.selectedProperty())
            graphPanel.physicsActiveProperty().bindBidirectional(physic.selectedProperty())
            graphPanel.getNodeEdgeModel().alwaysNormalizeProperty().bindBidirectional(alwaysNormalize.selectedProperty())
            graphPanel.getNodeEdgeModel().neverNormalizeProperty().bindBidirectional(neverNormalize.selectedProperty())
            graphPanel.getNodeEdgeModel().normalizeIfNotBetweenZeroAndOneProperty().bindBidirectional(normalizeIfBetweenZeroAndOne.selectedProperty())
            graphPanel.getNodeEdgeModel().nodeBorderColorProperty().bindBidirectional(borderColorPicker.valueProperty())
            graphPanel.edgeColorProperty().bindBidirectional(edgeColorPicker.valueProperty())

        }

        private def setupListener(): Unit = {
            dataChanger.valueProperty().addListener((observable, oldValue, newValue) => {
                newValue match {
                    case "D3Example" =>
                        dataGenerator.generateGraphWithDiffrentNodeSizes()
                        nodeEdgeModel = new NodeEdgeModel(dataGenerator.getNodes(), dataGenerator.getEdges())
                        graphPanel.setNodeEdgeModel(nodeEdgeModel)
                        graphPanel.getNodeEdgeModel().addColorTheme(createExampleColorTheme(), "Example1")
                        graphPanel.getNodeEdgeModel().addColorTheme(createExampleColorTheme2(), "Example2")
                        graphPanel.getNodeEdgeModel().setCurrentColorThemeKey("Kelly Color")
                        setDefault(graphPanel.getNodeEdgeModel())

                    case "FoodExport" =>
                        dataGenerator.generateGraphWithMinimalInformation()
                        nodeEdgeModel = new NodeEdgeModel(dataGenerator.getNodes(), dataGenerator.getEdges())
                        nodeEdgeModel.setCurrentColorThemeKey("Kelly Color")
                        setDefault(nodeEdgeModel)
                        graphPanel.setNodeEdgeModel(nodeEdgeModel)

                    case _ =>
                        System.err.println("DataKey invalid")
                }
                setupBindings()
                refreshGui()
            })

            groupComboBox.valueProperty().addListener((observable, oldValue, newValue) => {
                graphPanel.getNodeEdgeModel().setGroupColors(newValue)
                refreshColorPickers()
            })

            colorSchemeComboBox.valueProperty().addListener((observable, oldValue, newValue) => {
                graphPanel.getNodeEdgeModel().instantiateColorScheme(newValue)
                refreshColorPickers()
            })

            nodeValueComboBox.valueProperty().addListener((observable, oldValue, newValue) => graphPanel.getNodeEdgeModel().setNodeSizeKey(newValue))
            edgeWidthValueComboBox.valueProperty().addListener((observable, oldValue, newValue) => graphPanel.getNodeEdgeModel().setEdgeWidthFromAttributeNormalized(newValue))
            edgeForceValueComboBox.valueProperty().addListener((observable, oldValue, newValue) => graphPanel.getNodeEdgeModel().setEdgeForceFromAttributeNormalized(newValue))

            calculateDegreeCentrality.setOnAction(event => {
                graphPanel.calculateDegreeCentrality()
                refreshGui()
            })
            calculateBetweennessCentrality.setOnAction(event => {
                graphPanel.calculateBetweennessCentrality()
                refreshGui()
            })
            calculateClosenessCentrality.setOnAction(event => {
                graphPanel.calculateClosenessCentrality()
                refreshGui()
            })

            restartAnimation.setOnAction(event => graphPanel.restart())
        }

        private def layoutSelf(): Unit = {
            this.setCenter(graphPanel)
            this.setRight(sliderBox)
            //super.getChildren().addAll(graphPanel, sliderBox)

        }

        private def refreshGui(): Unit = {
            val groupValue: String = groupComboBox.getValue()
            val nodeValue: String = nodeValueComboBox.getValue()
            val edgeForceValue: String = edgeForceValueComboBox.getValue()
            val edgeWidthValue: String = edgeWidthValueComboBox.getValue()
            val colorShemeValue: String = colorSchemeComboBox.getValue()

            groupComboBox.setItems(FXCollections.observableArrayList(graphPanel.getNodeEdgeModel().getStringAttributeKeysOfNodes()))
            nodeValueComboBox.setItems(FXCollections.observableArrayList(graphPanel.getNodeEdgeModel().getNumericAttributeKeysOfNodes()))
            val list: ObservableList[String] = FXCollections.observableArrayList(graphPanel.getNodeEdgeModel().getNumericAttributeKeysOfEdges())
            edgeForceValueComboBox.setItems(list)
            edgeWidthValueComboBox.setItems(list)
            colorSchemeComboBox.setItems(FXCollections.observableArrayList(graphPanel.getNodeEdgeModel().getColorThemeKeys()))
            refreshColorPickers()

            groupComboBox.setValue(groupValue)
            nodeValueComboBox.setValue(nodeValue)
            edgeForceValueComboBox.setValue(edgeForceValue)
            edgeWidthValueComboBox.setValue(edgeWidthValue)
            colorSchemeComboBox.setValue(colorShemeValue)
        }

        private def refreshColorPickers(): Unit = {
            colors.getChildren().clear()
            val t = graphPanel.getNodeEdgeModel().getDistinctValuesPerGroupKey(graphPanel.getNodeEdgeModel().getCurrentGroupKey()).asScala.toList
            for(s: String <- graphPanel.getNodeEdgeModel().getDistinctValuesPerGroupKey(graphPanel.getNodeEdgeModel().getCurrentGroupKey()).asScala.toList) {
                val temp = new HBox()
                val cp   = new ColorPickerWithString(s)
                cp.setValue(graphPanel.getNodeEdgeModel().getGroupValueColor(s, graphPanel.getNodeEdgeModel().getCurrentGroupKey()))
                temp.getChildren().addAll(cp, new Label(s))
                addChangeListenerToColorPicker(cp)
                colors.getChildren().add(temp)
            }
        }

        private def addChangeListenerToColorPicker(cp: ColorPickerWithString): Unit = {
            cp.valueProperty().addListener(
                // First type not required, inferred from second or third
                (observable: ObservableValue[? <: Color], oldValue:Color, newValue:Color) => 
                    graphPanel.getNodeEdgeModel()
                              .setGroupValueColor(
                                cp.getKey(),
                                graphPanel.getNodeEdgeModel().getCurrentGroupKey(), // string
                                newValue
                            )
                )

        }

        private def setDefault(nodeEdgeModel: NodeEdgeModel): Unit = {
            nodeSizeFactor.setValue(graphPanel.getNodeSizeFactor())
            edgeWidthFactor.setValue(graphPanel.getEdgeWidthFactor())
            alwaysNormalize.setSelected(nodeEdgeModel.isAlwaysNormalize())
            neverNormalize.setSelected(nodeEdgeModel.isNeverNormalize())
            normalizeIfBetweenZeroAndOne.setSelected(nodeEdgeModel.isNormalizeIfNotBetweenZeroAndOne())
        }

        private def createExampleColorTheme(): ArrayList[Color] = {
            val colorTheme: ArrayList[Color] = new ArrayList()
            for(i <- 0 until 20){
                colorTheme.add(Color.color(0.5, 0.5, 1-0.05*i))
            }
            return colorTheme
        }

        private def createExampleColorTheme2(): ArrayList[Color] = {
            val colorTheme: ArrayList[Color] = new ArrayList()
            for(i <- 0 until 20){
                colorTheme.add(Color.color(1-0.05*i, 0.05*i, 1))
            }
            return colorTheme
        }

    }

    class ColorPickerWithString(private var key:String) extends ColorPicker {

        def getKey(): String = { key }
        def setKey(KEY: String): Unit = { key = KEY }
    }

    class DataGenerator {

        private val nodes: ArrayList[GraphNode] = new ArrayList[GraphNode]()
        private val edges: ArrayList[GraphEdge] = new ArrayList[GraphEdge]()
        private val colorsheme: HashMap[String, Color] = new HashMap[String, Color]()
        private var index: Int = uninitialized
        private var indexMap: HashMap[String, Integer] = uninitialized



        def generateGraphWithMinimalInformation(): Unit = {
            setColorshemeToMinimum()
            nodes.clear()
            nodes.add(new GraphNode(new HashMap[String, java.lang.Double](), new HashMap[String, String]()))
            nodes.add(new GraphNode(new HashMap[String, java.lang.Double](), new HashMap[String, String]()))
            nodes.add(new GraphNode(new HashMap[String, java.lang.Double](), new HashMap[String, String]()))
            nodes.add(new GraphNode(new HashMap[String, java.lang.Double](), new HashMap[String, String]()))
            nodes.add(new GraphNode(new HashMap[String, java.lang.Double](), new HashMap[String, String]()))
            nodes.add(new GraphNode(new HashMap[String, java.lang.Double](), new HashMap[String, String]()))
            nodes.add(new GraphNode(new HashMap[String, java.lang.Double](), new HashMap[String, String]()))
            nodes.add(new GraphNode(new HashMap[String, java.lang.Double](), new HashMap[String, String]()))
            nodes.add(new GraphNode(new HashMap[String, java.lang.Double](), new HashMap[String, String]()))

            edges.clear()
            edges.add(new GraphEdge(nodes.get(0), nodes.get(1), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(1), nodes.get(2), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(2), nodes.get(3), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(3), nodes.get(4), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(4), nodes.get(5), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(5), nodes.get(6), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(6), nodes.get(7), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(7), nodes.get(8), new HashMap[String, java.lang.Double]()))
        }

        def generateGraphWithDiffrentNodeSizes(): Unit = {
            setColorshemeToMinimum()
            nodes.clear()
            edges.clear()

            val sizeKey: String = "size"
            var temp: HashMap[String, java.lang.Double] = new HashMap[String, java.lang.Double]()

            temp.put(sizeKey, 200.0)
            nodes.add(new GraphNode(temp, new HashMap[String, String]()))
            temp = new HashMap[String, java.lang.Double]()
            temp.put(sizeKey, 400.0)
            nodes.add(new GraphNode(temp, new HashMap[String, String]()))
            temp = new HashMap[String, java.lang.Double]()
            temp.put(sizeKey, 150.0)
            nodes.add(new GraphNode(temp, new HashMap[String, String]()))
            temp = new HashMap[String, java.lang.Double]()
            temp.put(sizeKey, 600.0)
            nodes.add(new GraphNode(temp, new HashMap[String, String]()))
            temp = new HashMap[String, java.lang.Double]()
            temp.put(sizeKey, 250.0)
            nodes.add(new GraphNode(temp, new HashMap[String, String]()))
            temp = new HashMap[String, java.lang.Double]()
            temp.put(sizeKey, 300.0)
            nodes.add(new GraphNode(temp, new HashMap[String, String]()))

            edges.add(new GraphEdge(nodes.get(0), nodes.get(1), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(1), nodes.get(2), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(2), nodes.get(3), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(3), nodes.get(4), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(4), nodes.get(5), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(5), nodes.get(2), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(4), nodes.get(2), new HashMap[String, java.lang.Double]()))

        }

        def generateGraphWithGroupingAndDifferentNodeSizes(): Unit = {
            setColorshemeToMinimum()
            nodes.clear()
            edges.clear()

            val sizeKey:String = "size"
            var temp: HashMap[String, java.lang.Double] = new HashMap[String, java.lang.Double]()
            val colorKey:String = "color"
            var temp2: HashMap[String, String] = new HashMap[String, String]()

            temp.put(sizeKey, 200.0)
            temp2.put(colorKey, "Group1")
            nodes.add(new GraphNode(temp, temp2))
            temp = new HashMap[String, java.lang.Double]()
            temp.put(sizeKey, 400.0)
            temp2 = new HashMap[String, String]()
            temp2.put(colorKey, "Group1")
            nodes.add(new GraphNode(temp, temp2))
            temp = new HashMap[String, java.lang.Double]()
            temp.put(sizeKey, 150.0)
            temp2 = new HashMap[String, String]()
            temp2.put(colorKey, "Group2")
            nodes.add(new GraphNode(temp, temp2))
            temp = new HashMap[String, java.lang.Double]()
            temp.put(sizeKey, 600.0)
            temp2 = new HashMap[String, String]()
            temp2.put(colorKey, "Group2")
            nodes.add(new GraphNode(temp, temp2))
            temp = new HashMap[String, java.lang.Double]()
            temp.put(sizeKey, 250.0)
            temp2 = new HashMap[String, String]()
            temp2.put(colorKey, "Group1")
            nodes.add(new GraphNode(temp, temp2))
            temp = new HashMap[String, java.lang.Double]()
            temp.put(sizeKey, 300.0)
            temp2 = new HashMap[String, String]()
            temp2.put(colorKey, "Group3")
            nodes.add(new GraphNode(temp, temp2))

            edges.add(new GraphEdge(nodes.get(0), nodes.get(1), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(1), nodes.get(2), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(2), nodes.get(3), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(3), nodes.get(4), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(4), nodes.get(5), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(5), nodes.get(2), new HashMap[String, java.lang.Double]()))
            edges.add(new GraphEdge(nodes.get(4), nodes.get(2), new HashMap[String, java.lang.Double]()))

            colorsheme.put("Group1", Color.GREEN)
            colorsheme.put("Group2", Color.BLUE)
            colorsheme.put("Group3", Color.YELLOW)

        }

        def generateRandomGraph(): Unit = {
            val amountOfNodes = (Math.random()*29).toInt + 2
            val amountOfEdges = (amountOfNodes -1) + ((Math.random() * (((amountOfNodes * (amountOfNodes-1))/2)) - amountOfNodes -1).toInt)
            generateGraphWithSetAmountOfNodesAndEdges(amountOfNodes, amountOfEdges)
        }

        def generateGraphWithSetAmountOfNodes(amountOfNodes: Int): Unit = {
            val amountOfEdges = (amountOfNodes -1) + ((Math.random() * (((amountOfNodes * (amountOfNodes-1))/2)) - amountOfNodes -1).toInt)
            generateGraphWithSetAmountOfNodesAndEdges(amountOfNodes, amountOfEdges)
        }

        def generateGrapheWitNodeRange(min_ :Int , max_ :Int): Unit = {
            var min = min_
            var max = max_
            if(min>max){
                var temp = max
                max = min
                min = temp
            }
            val amountOfNodes = (Math.random()*(max-min) ).toInt + min
            val amountOfEdges = (amountOfNodes -1) + ((Math.random() * (((amountOfNodes * (amountOfNodes-1))/2)) - amountOfNodes -1).toInt)
            generateGraphWithSetAmountOfNodesAndEdges(amountOfNodes, amountOfEdges)
        }

        def generateGraphWithSetAmountOfNodesAndEdges(amountOfNodes: Int, amountOfEdges: Int): Unit = {
            val amountOfGroups = (Math.random() * (amountOfNodes/2)).toInt +1
            generateGraphWithSetAmountOfNodesEdgesAndGroups(amountOfNodes, amountOfEdges, amountOfGroups)
        }

        def generateGraphWithSetAmountOfNodesEdgesAndGroups(amountOfNodes: Int, amountOfEdges:Int, amountOfGroups:Int):NodeEdgeModel ={
            setColorshemeToMinimum()
            nodes.clear()
            edges.clear()


            val groups = amountOfGroups

            val sizeKey:String = "size"
            val colorKey:String = "color"
            
            for(i<-0 until groups){
                var temp:HashMap[String, java.lang.Double] = new HashMap[String, java.lang.Double]()
                temp.put(sizeKey, Math.random() * 300.0 + 100.0)
                var temp2: HashMap[String, String] = new HashMap[String, String]()
                temp2.put(colorKey, "Group" + i)
                val gn = new GraphNode(temp, temp2)
                gn.setSizeKey(sizeKey)
                nodes.add(gn)
            }
            System.out.println("Datagenerator: exit Nodes static grouping")

            for(i <- 0 until amountOfNodes-groups){
                var temp:HashMap[String, java.lang.Double] = new HashMap[String, java.lang.Double]()
                temp.put(sizeKey, Math.random()*300.0 + 100.0)
                var temp2: HashMap[String, String] = new HashMap[String, String]()
                temp2.put(colorKey, "Group" + (Math.random()*groups).toInt)
                val gn = new GraphNode(temp, temp2)
                gn.setSizeKey(sizeKey)
                nodes.add(gn)
            }
            System.out.println("Datagenerator: exit Nodes random grouping")

            val notConnected: ArrayList[Integer] = new ArrayList[Integer]()
            val connected: ArrayList[Integer] = new ArrayList[Integer]()
            for(i <-0 until amountOfNodes){
                notConnected.add(i)
            }


            var currentNode1: Int = (Math.random()*amountOfNodes).toInt
            var currentNode2: Int = (Math.random()*amountOfNodes).toInt
            while( currentNode1 == currentNode2){
                currentNode2 = (Math.random()*amountOfNodes).toInt
            }


            edges.add(new GraphEdge(nodes.get(currentNode1),nodes.get(currentNode2), new HashMap[String, java.lang.Double]()))

            connected.add(currentNode1)
            connected.add(currentNode2)
            if(currentNode1 > currentNode2){
                notConnected.remove(currentNode1)
                notConnected.remove(currentNode2)
            } else{
                notConnected.remove(currentNode2)
                notConnected.remove(currentNode1)
            }


            for(i <- 0 until amountOfNodes-2){
                currentNode1 = (Math.random()*notConnected.size()).toInt
                currentNode2 = (Math.random()*connected.size()).toInt
                edges.add(new GraphEdge(nodes.get(notConnected.get(currentNode1)),
                                        nodes.get(connected.get(currentNode2)),
                                        new HashMap[String, java.lang.Double]()))
                connected.add(notConnected.get(currentNode1))
                notConnected.remove(currentNode1)
            }

            System.out.println("Datagenerator: exit Edge controlled connection")



            for(i <-0 until amountOfEdges-(amountOfNodes-1) ){
                var newEdge: Boolean = false
                var counter: Int = 0
                while (!newEdge) {
                    counter += 1
                    currentNode1 = (Math.random() * amountOfNodes).toInt
                    currentNode2 = (Math.random() * amountOfNodes).toInt
                    while (currentNode1 == currentNode2) {
                        currentNode2 = (Math.random() * amountOfNodes).toInt
                    }
                    var counter2 = 0
                    newEdge = true
                    var j = 0
                    while(j<edges.size() && newEdge){
                        counter2 += 1
                        if((edges.get(j).getU() == nodes.get(currentNode1)&& edges.get(j).getV() == nodes.get(currentNode2))
                           || (edges.get(j).getU() ==nodes.get(currentNode2)&& edges.get(j).getV() == nodes.get(currentNode1))){
                            newEdge = false
                        }
                        j += 1
                    }
                    if(newEdge){
                        edges.add(new GraphEdge(nodes.get(currentNode1), nodes.get(currentNode2), new HashMap[String, java.lang.Double]()))
                    }
                    if(counter>30){
                        newEdge = true
                        System.out.println("max Amount of Edge connection tries reached")
                    }
                }
            }

            for(i <- 0 until groups){
                colorsheme.put("Group" + i, Color.color(Math.random(),Math.random(),Math.random()))
            }
            new NodeEdgeModel(nodes, edges)

        }

        private def createNodeWithNameAndGroup(name: String, group: HashMap[String,String]): Unit = {
            nodes.add(new GraphNode(name,new HashMap[String, java.lang.Double](),group))
            indexMap.put(name, index)
            index += 1
        }


        def getNodes(): ArrayList[GraphNode] = {
            if(nodes.size()<1){
                generateGraphWithMinimalInformation()
            }
            nodes
        }

        def getEdges(): ArrayList[GraphEdge] = {
            if(edges.size()<1){
                generateGraphWithMinimalInformation()
            }
            edges
        }

        def getColorScheme():HashMap[String, Color] = {
            colorsheme
        }

        private def setColorshemeToMinimum(): Unit = {
            colorsheme.clear()
            colorsheme.put("None", Color.GRAY)
        }
    }
}


// Not required, not needed
// object ForceDirectedGraphTest {
//   def main(args: Array[String]) =
//     val app = new ForceDirectedGraphTest
//     app.launchIt()
// 
// }

// Not required, not needed
// object ForceDirectedGraphTest {
// 
//     def main(args: Array[String]): Unit = {
//     Application.launch(classOf[ForceDirectedGraphTest], args*)
//     }
// }
