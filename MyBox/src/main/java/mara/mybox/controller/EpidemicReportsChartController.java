package mara.mybox.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;
import javafx.stage.Modality;
import javafx.stage.Screen;
import static mara.mybox.controller.BaseController.openImageViewer;
import mara.mybox.data.EpidemicReport;
import mara.mybox.data.GeographyCode;
import mara.mybox.data.QueryCondition;
import static mara.mybox.db.DerbyBase.dbHome;
import static mara.mybox.db.DerbyBase.login;
import static mara.mybox.db.DerbyBase.protocol;
import mara.mybox.db.TableStringValue;
import mara.mybox.fxml.ControlStyle;
import mara.mybox.fxml.FxmlColor;
import mara.mybox.fxml.FxmlControl;
import mara.mybox.fxml.FxmlControl.ChartCoordinate;
import mara.mybox.fxml.FxmlControl.LabelType;
import static mara.mybox.fxml.FxmlControl.badStyle;
import mara.mybox.fxml.Logarithmic10Coordinate;
import mara.mybox.fxml.LogarithmicECoordinate;
import mara.mybox.fxml.SquareRootCoordinate;
import mara.mybox.image.ImageManufacture;
import mara.mybox.image.file.ImageFileWriters;
import mara.mybox.image.file.ImageGifFile;
import mara.mybox.tools.DoubleTools;
import mara.mybox.tools.FileTools;
import mara.mybox.tools.LocationTools;
import mara.mybox.tools.StringTools;
import mara.mybox.value.AppVariables;
import static mara.mybox.value.AppVariables.logger;
import static mara.mybox.value.AppVariables.message;
import mara.mybox.value.CommonValues;
import thridparty.LabeledBarChart;
import thridparty.LabeledHorizontalBarChart;

/**
 * @Author Mara
 * @CreateDate 2020-2-3
 * @License Apache License Version 2.0
 */
public class EpidemicReportsChartController extends LocationsMapController {

    protected EpidemicReportsController reportsController;
    protected EpidemicReportsSettingsController settingsController;
    protected QueryCondition queryCondition;
    protected String chartQuerySQL, chartName;
    protected List<String> orderNames, valuesNames;
    protected int interval, topNumber, snapWidth, chartLoadTime, mapLoadTime,
            totalSize, chartIndex;
    protected List<String> datasets, chartTimes;
    protected Map<String, List<EpidemicReport>> timesReports, locationsReports;
    protected List<GeographyCode> chartLocations;
    protected boolean multipleDatasets, mapCentered;
    protected double maxValue;
    protected Side legendSide;
    protected float lenUnit;
    protected LabelType labelType;
    protected ChartCoordinate chartCoordinate;
    protected LoadingController loading;

    @FXML
    protected StackPane chartPane;
    @FXML
    protected ComboBox<String> intervalSelector, labelSizeSelector, frameSelector;
    @FXML
    protected ToggleGroup chartGroup, labelGroup, legendGroup, coordinateGroup;
    @FXML
    protected HBox chartTypeBox, playBox;
    @FXML
    protected VBox valueTypeBox, optionsVBox, legendBox, labelBox, chartSnapBox;
    @FXML
    protected CheckBox categoryAxisCheck, confirmedCheck, healedCheck, deadCheck,
            increasedConfirmedCheck, increasedHealedCheck, increasedDeadCheck,
            HealedConfirmedPermillageCheck, DeadConfirmedPermillageCheck,
            ConfirmedPopulationPermillageCheck, DeadPopulationPermillageCheck, HealedPopulationPermillageCheck,
            ConfirmedAreaPermillageCheck, HealedAreaPermillageCheck, DeadAreaPermillageCheck,
            hlinesCheck, vlinesCheck;
    @FXML
    protected RadioButton numbersRadio, ratiosRadio, increasedRadio, confirmedRatio,
            increasedConfirmedRadio, healedRadio, healedRatioRadio, increasedHealedRadio,
            deadRadio, deadRatioRadio, increasedDeadRadio,
            horizontalBarsChartRadio, verticalBarsChartRadio, linesChartRadio, linesChartHRadio, pieRadio, mapRadio,
            cartesianRadio, logarithmicERadio, logarithmic10Radio, squareRootRadio;
    @FXML
    protected VBox chartBox, mapBox;
    @FXML
    protected Button pauseButton;

    public EpidemicReportsChartController() {
        baseTitle = AppVariables.message("EpidemicReport");

    }

    @Override
    public void initializeNext() {
        try {
            super.initializeNext();
            valuesNames = new ArrayList();
            initOptions();

        } catch (Exception e) {
            logger.error(e.toString());
        }

    }

    protected void initOptions() {
        try {
            chartGroup.selectedToggleProperty().addListener(
                    (ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
                        if (isSettingValues || newValue == null) {
                            return;
                        }
                        drawChart();
                    });

            chartCoordinate = ChartCoordinate.Cartesian;
            coordinateGroup.selectedToggleProperty().addListener(
                    (ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
                        if (isSettingValues || newValue == null) {
                            return;
                        }
                        if (logarithmicERadio.isSelected()) {
                            chartCoordinate = ChartCoordinate.LogarithmicE;
                        } else if (logarithmic10Radio.isSelected()) {
                            chartCoordinate = ChartCoordinate.Logarithmic10;
                        } else if (squareRootRadio.isSelected()) {
                            chartCoordinate = ChartCoordinate.SquareRoot;
                        } else {
                            chartCoordinate = ChartCoordinate.Cartesian;
                        }
                        drawChart();
                    });

            frameSelector.getSelectionModel().selectedItemProperty().addListener(
                    (ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
                        if (isSettingValues) {
                            return;
                        }
                        if (timer != null) {
                            timer.cancel();
                            timer = null;
                        }
                        drawFrame(newValue);
                    });

            labelType = LabelType.NameAndValue;
            labelGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
                if (isSettingValues || newValue == null) {
                    return;
                }
                String value = ((RadioButton) newValue).getText();
                if (message("NameAndValue").equals(value)) {
                    labelType = LabelType.NameAndValue;
                } else if (message("Value").equals(value)) {
                    labelType = LabelType.Value;
                } else if (message("Name").equals(value)) {
                    labelType = LabelType.Name;
                } else if (message("NotDisplay").equals(value)) {
                    labelType = LabelType.NotDisplay;
                } else if (message("Pop").equals(value)) {
                    labelType = LabelType.Pop;
                } else {
                    labelType = LabelType.NameAndValue;
                }
                drawChart();
            });

            textSize = 12;
            labelSizeSelector.getItems().addAll(Arrays.asList(
                    "12", "14", "10", "15", "16", "18", "9", "8", "18", "20", "24"
            ));
            labelSizeSelector.getSelectionModel().selectedItemProperty().addListener(
                    (ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
                        try {
                            int v = Integer.parseInt(newValue);
                            if (v > 0) {
                                textSize = v;
                                labelSizeSelector.getEditor().setStyle(null);
                                AppVariables.setUserConfigInt("EpidemicReportChartTextSize", textSize);
                                if (!isSettingValues) {
                                    drawChart();
                                }
                            } else {
                                labelSizeSelector.getEditor().setStyle(badStyle);
                            }
                        } catch (Exception e) {
                            labelSizeSelector.getEditor().setStyle(badStyle);
                        }
                    });

            legendSide = null;
            legendGroup.selectedToggleProperty().addListener(
                    (ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
                        if (isSettingValues || newValue == null) {
                            return;
                        }
                        String value = ((RadioButton) newValue).getText();
                        if (message("NotDisplay").equals(value)) {
                            legendSide = null;
                        } else if (message("Left").equals(value)) {
                            legendSide = Side.LEFT;
                        } else if (message("Top").equals(value)) {
                            legendSide = Side.TOP;
                        } else if (message("Bottom").equals(value)) {
                            legendSide = Side.BOTTOM;
                        } else {
                            legendSide = Side.RIGHT;
                        }
                        drawChart();
                    });

            categoryAxisCheck.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                        if (isSettingValues) {
                            return;
                        }
                        AppVariables.setUserConfigValue("EpidemicReportDisplayCategoryAxis", newValue);
                        drawChart();
                    });

            hlinesCheck.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                        if (isSettingValues) {
                            return;
                        }
                        AppVariables.setUserConfigValue("EpidemicReportDisplayHlines", newValue);
                        drawChart();
                    });

            vlinesCheck.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                        if (isSettingValues) {
                            return;
                        }
                        AppVariables.setUserConfigValue("EpidemicReportDisplayVlines", newValue);
                        drawChart();
                    });

            interval = 200;
            intervalSelector.getItems().addAll(Arrays.asList(
                    "200", "500", "1000", "50", "100", "300", "800", "1500", "2000", "3000", "5000", "10000"
            ));
            intervalSelector.getSelectionModel().selectedItemProperty().addListener(
                    (ObservableValue<? extends String> ov, String oldValue, String newValue) -> {
                        try {
                            int v = Integer.valueOf(intervalSelector.getValue());
                            if (v > 0) {
                                interval = v;
                                AppVariables.setUserConfigValue("EpidemicReportInterval", interval + "");
                                FxmlControl.setEditorNormal(intervalSelector);
                                if (isSettingValues) {
                                    return;
                                }
                                drawChart();
                            } else {
                                FxmlControl.setEditorBadStyle(intervalSelector);
                            }
                        } catch (Exception e) {
                            logger.error(e.toString());
                        }
                    });

            isSettingValues = true;
            labelSizeSelector.getSelectionModel().select(AppVariables.getUserConfigValue("EpidemicReportChartTextSize", "12"));
            categoryAxisCheck.setSelected(AppVariables.getUserConfigBoolean("EpidemicReportDisplayCategoryAxis", false));
            hlinesCheck.setSelected(AppVariables.getUserConfigBoolean("EpidemicReportDisplayHlines", true));
            vlinesCheck.setSelected(AppVariables.getUserConfigBoolean("EpidemicReportDisplayVlines", true));
            intervalSelector.getSelectionModel().select(AppVariables.getUserConfigValue("EpidemicReportInterval", "200"));
            isSettingValues = false;

        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    protected void loadCharts() {
        synchronized (this) {
            if (task != null) {
                return;
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            if (!initCharts()) {
                return;
            }
            task = new SingletonTask<Void>() {

                @Override
                protected boolean handle() {
                    try ( Connection conn = DriverManager.getConnection(protocol + dbHome() + login)) {
                        conn.setReadOnly(true);
                        Map<String, String> saved = TableStringValue.readWithPrefix(conn, "EpidemicReportsLocationColor");
                        Collection<String> savedColors = saved.values();
                        Map<String, String> locationColors = new HashMap();
                        for (GeographyCode location : chartLocations) {
                            String name = location.getFullName();
                            String color = saved.get("EpidemicReportsLocationColor" + name);
                            if (color == null) {
                                color = FxmlColor.randomColorExcept(savedColors);
                            }
                            locationColors.put(name, color);
                        }
                        TableStringValue.writeWithPrefix("EpidemicReportsLocationColor", locationColors);
                        settingsController.locationColors = locationColors;
                        if (loading != null) {
                            loading.setInfo(message("DateNumber") + ": " + chartTimes.size()
                                    + " " + message("TotalSize") + ": " + totalSize);
                        }
                        return true;

                    } catch (Exception e) {
                        if (loading != null) {
                            loading.setInfo(e.toString());
                        }
                        error = e.toString();
                        return false;
                    }
                }

                @Override
                protected void whenSucceeded() {
                    settingsController.initColors();
                    drawChart();
                }

            };
            loading = reportsController.openHandlingStage(task, Modality.WINDOW_MODAL, message("LoadingChartData"));
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        }

    }

    protected boolean initCharts() {
        clearChart();
        if (reportsController == null
                || reportsController.topNumber <= 0
                || reportsController.dataTimes.isEmpty()
                || reportsController.orderNames.isEmpty()
                || reportsController.timesReports.isEmpty()) {
            return false;
        }
        topNumber = reportsController.topNumber;
        queryCondition = reportsController.queryCondition;
        chartQuerySQL = reportsController.dataQuerySQL;
        datasets = reportsController.datasets;
        timesReports = reportsController.timesReports;
        locationsReports = reportsController.locationsReports;
        chartLocations = reportsController.dataLocations;
        multipleDatasets = reportsController.datasets.size() > 1;
        maxValue = reportsController.maxValue;
        totalSize = reportsController.totalSize;
        chartTimes = new ArrayList<>();
        chartTimes.addAll(reportsController.dataTimes);
        Collections.reverse(chartTimes);
        mapSize = 3;
        chartIndex = 0;

        orderNames = new ArrayList<>();
        orderNames.addAll(reportsController.orderNames);
        orderNames.remove("time");
        if (orderNames.isEmpty()) {
            return false;
        }
        setValuesChecks();

        if (chartTimes.size() > 1) {
            playBox.setVisible(true);
            isSettingValues = true;
            setPause(false);
            List<String> frames = new ArrayList<>();
            for (int i = chartTimes.size() - 1; i >= 0; i--) {
                String date = chartTimes.get(i);
                frames.add(date);
            }
            frameSelector.getItems().clear();
            frameSelector.getItems().addAll(frames);
            isSettingValues = false;
        } else {
            playBox.setVisible(false);
        }
        return true;
    }

    public void clearChart() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        chartBox.getChildren().clear();
        optionsVBox.getChildren().clear();
        chartBox.setVisible(false);
        mapBox.setVisible(false);
        valuesNames.clear();
        titleLabel.setText("");
        mapCentered = false;
    }

    protected void setValuesChecks() {
        for (Node node : valueTypeBox.getChildren()) {
            if (node instanceof CheckBox) {
                ((CheckBox) node).setSelected(false);
                ((CheckBox) node).setDisable(false);
            }
        }
        if (orderNames == null) {
            return;
        }
        boolean first = false;
        for (String name : orderNames) {
            if ("confirmed".equals(name)) {
                confirmedCheck.setSelected(true);
                if (!first) {
                    confirmedCheck.setDisable(true);
                    first = true;
                }
            } else if ("healed".equals(name)) {
                healedCheck.setSelected(true);
                if (!first) {
                    healedCheck.setDisable(true);
                    first = true;
                }
            } else if ("dead".equals(name)) {
                deadCheck.setSelected(true);
                if (!first) {
                    deadCheck.setDisable(true);
                    first = true;
                }
            } else if ("increased_confirmed".equals(name)) {
                increasedConfirmedCheck.setSelected(true);
                if (!first) {
                    increasedConfirmedCheck.setDisable(true);
                    first = true;
                }
            } else if ("increased_healed".equals(name)) {
                increasedHealedCheck.setSelected(true);
                if (!first) {
                    increasedHealedCheck.setDisable(true);
                    first = true;
                }
            } else if ("increased_dead".equals(name)) {
                increasedDeadCheck.setSelected(true);
                if (!first) {
                    increasedDeadCheck.setDisable(true);
                    first = true;
                }
            } else if ("healed_confirmed_permillage".equals(name)) {
                HealedConfirmedPermillageCheck.setSelected(true);
                if (!first) {
                    HealedConfirmedPermillageCheck.setDisable(true);
                    first = true;
                }
            } else if ("dead_confirmed_permillage".equals(name)) {
                DeadConfirmedPermillageCheck.setSelected(true);
                if (!first) {
                    DeadConfirmedPermillageCheck.setDisable(true);
                    first = true;
                }
            } else if ("confirmed_population_permillage".equals(name)) {
                ConfirmedPopulationPermillageCheck.setSelected(true);
                if (!first) {
                    ConfirmedPopulationPermillageCheck.setDisable(true);
                    first = true;
                }
            } else if ("healed_population_permillage".equals(name)) {
                HealedPopulationPermillageCheck.setSelected(true);
                if (!first) {
                    HealedPopulationPermillageCheck.setDisable(true);
                    first = true;
                }
            } else if ("dead_population_permillage".equals(name)) {
                DeadPopulationPermillageCheck.setSelected(true);
                if (!first) {
                    DeadPopulationPermillageCheck.setDisable(true);
                    first = true;
                }
            } else if ("confirmed_area_permillage".equals(name)) {
                ConfirmedAreaPermillageCheck.setSelected(true);
                if (!first) {
                    ConfirmedAreaPermillageCheck.setDisable(true);
                    first = true;
                }
            } else if ("healed_area_permillage".equals(name)) {
                HealedAreaPermillageCheck.setSelected(true);
                if (!first) {
                    HealedAreaPermillageCheck.setDisable(true);
                    first = true;
                }
            } else if ("dead_area_permillage".equals(name)) {
                DeadAreaPermillageCheck.setSelected(true);
                if (!first) {
                    DeadAreaPermillageCheck.setDisable(true);
                    first = true;
                }
            }
        }
    }

    protected void checkValues() {
        valuesNames.clear();
        if (confirmedCheck.isSelected()) {
            valuesNames.add(message("Confirmed"));
        }
        if (healedCheck.isSelected()) {
            valuesNames.add(message("Healed"));
        }
        if (deadCheck.isSelected()) {
            valuesNames.add(message("Dead"));
        }
        if (increasedConfirmedCheck.isSelected()) {
            valuesNames.add(message("IncreasedConfirmed"));
        }
        if (increasedHealedCheck.isSelected()) {
            valuesNames.add(message("IncreasedHealed"));
        }
        if (increasedDeadCheck.isSelected()) {
            valuesNames.add(message("IncreasedDead"));
        }
        if (HealedConfirmedPermillageCheck.isSelected()) {
            valuesNames.add(message("HealedConfirmedPermillage"));
        }
        if (DeadConfirmedPermillageCheck.isSelected()) {
            valuesNames.add(message("DeadConfirmedPermillage"));
        }
        if (ConfirmedPopulationPermillageCheck.isSelected()) {
            valuesNames.add(message("ConfirmedPopulationPermillage"));
        }
        if (HealedPopulationPermillageCheck.isSelected()) {
            valuesNames.add(message("HealedPopulationPermillage"));
        }
        if (DeadPopulationPermillageCheck.isSelected()) {
            valuesNames.add(message("DeadPopulationPermillage"));
        }
        if (ConfirmedAreaPermillageCheck.isSelected()) {
            valuesNames.add(message("ConfirmedAreaPermillage"));
        }
        if (HealedAreaPermillageCheck.isSelected()) {
            valuesNames.add(message("HealedAreaPermillage"));
        }
        if (DeadAreaPermillageCheck.isSelected()) {
            valuesNames.add(message("DeadAreaPermillage"));
        }
    }

    @FXML
    public void drawChart() {
        if (isSettingValues) {
            return;
        }
        try {
            clearChart();
            checkValues();
            if (chartTimes.isEmpty() || valuesNames.isEmpty()
                    || chartLocations.isEmpty() || timesReports.isEmpty()) {
                return;
            }
            setPause(false);

            if (mapRadio.isSelected()) {
                optionsVBox.getChildren().addAll(labelBox, mapOptionsBox);
                chartName = message("Map");
                chartBox.getChildren().clear();
                chartBox.setVisible(false);
                mapBox.setVisible(true);
                mapBox.toFront();
                webEngine.executeScript("setZoom(" + mapSize + ");");
            } else {
                optionsVBox.getChildren().addAll(legendBox, labelBox);
                mapBox.setVisible(false);
                chartBox.setVisible(true);
                chartBox.toFront();
                if (horizontalBarsChartRadio.isSelected()) {
                    chartName = message("HorizontalBarsChart");
                } else if (verticalBarsChartRadio.isSelected()) {
                    chartName = message("VerticalBarsChart");
                } else if (linesChartRadio.isSelected()) {
                    if (locationsReports == null) {
                        return;
                    }
                    chartName = message("VerticalLinesChart");
                } else if (linesChartHRadio.isSelected()) {
                    if (locationsReports == null) {
                        return;
                    }
                    chartName = message("HorizontalLinesChart");
                } else if (pieRadio.isSelected()) {
                    chartName = message("PieChart");
                }
            }

            try {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                if (interval <= 0) {
                    interval = 1000;
                }

                byte[] lock = new byte[0];
                timer = new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        Platform.runLater(() -> {
                            if (chartTimes == null || chartTimes.isEmpty()
                                    || timesReports == null || locationsReports.isEmpty()) {
                                if (timer != null) {
                                    timer.cancel();
                                    timer = null;
                                }
                                return;
                            }
                            synchronized (lock) {
                                fixChartIndex();
                                String time = chartTimes.get(chartIndex);
                                List<EpidemicReport> timeReports = timesReports.get(time);
                                drawChart(time, timeReports);

                                if (chartTimes.size() == 1) {
                                    if (timer != null) {
                                        timer.cancel();
                                        timer = null;
                                    }
                                    return;
                                }
                                ++chartIndex;
                            }
                        });
                    }

                }, 0, interval);

            } catch (Exception e) {
                logger.debug(e.toString());
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    protected void drawChart(String time, List<EpidemicReport> timeReports) {
        try {
            if (valuesNames.isEmpty() || time == null
                    || timeReports == null || timeReports.isEmpty()) {
                return;
            }
            titleLabel.setText(queryCondition.getTitle().replaceAll("\n", " ") + " - " + time);
            if (mapRadio.isSelected()) {
                drawMap(timeReports);
            } else {
                if (horizontalBarsChartRadio.isSelected()) {
                    if (valuesNames.size() == 1) {
                        drawValueBarsHorizontal(timeReports);
                    } else {
                        drawValuesBarsHorizontal(timeReports);
                    }
                } else if (verticalBarsChartRadio.isSelected()) {
                    if (valuesNames.size() == 1) {
                        drawValueBarsVertical(timeReports);
                    } else {
                        drawValuesBarsVertical(timeReports);
                    }
                } else if (linesChartRadio.isSelected()) {
                    drawValuesLines(time, timeReports, true);
                } else if (linesChartHRadio.isSelected()) {
                    drawValuesLines(time, timeReports, false);
                } else if (pieRadio.isSelected()) {
                    drawPies(time, timeReports);
                }
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    protected void drawFrame(int index) {
        chartIndex = index;
        drawFrame();
    }

    protected void drawFrame(String time) {
        chartIndex = chartTimes.indexOf(time);
        drawFrame();
    }

    protected void drawFrame() {
        try {
            setPause(true);
            fixChartIndex();
            String time = chartTimes.get(chartIndex);
            List<EpidemicReport> timeReports = timesReports.get(time);
            drawChart(time, timeReports);
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    protected void fixChartIndex() {
        if (chartIndex > chartTimes.size() - 1) {
            chartIndex -= chartTimes.size();
        } else if (chartIndex < 0) {
            chartIndex += chartTimes.size();
        }
    }

    protected void drawPies(String time, List<EpidemicReport> timeReports) {
        try {
            if (time == null || timeReports == null || timeReports.isEmpty()) {
                return;
            }
            chartBox.getChildren().clear();
            HBox line = new HBox();
            int colsNum = (int) Math.sqrt(valuesNames.size());
            colsNum = Math.max(colsNum, valuesNames.size() / colsNum);
            for (int i = 0; i < valuesNames.size(); i++) {
                String valueName = valuesNames.get(i);
                if (i % colsNum == 0) {
                    line = new HBox();
                    line.setAlignment(Pos.TOP_CENTER);
                    line.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    line.setSpacing(5);
                    VBox.setVgrow(line, Priority.ALWAYS);
                    HBox.setHgrow(line, Priority.ALWAYS);
                    chartBox.getChildren().add(line);
                }
                PieChart pie = new PieChart();
                pie.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                VBox.setVgrow(pie, Priority.ALWAYS);
                HBox.setHgrow(pie, Priority.ALWAYS);
                pie.setAnimated(false);
                if (legendSide == null) {
                    pie.setLegendVisible(false);
                } else {
                    pie.setLegendVisible(true);
                    pie.setLegendSide(legendSide);
                }
                pie.setTitle(valueName + " - " + time);
//                pie.setClockwise(true);
                pie.setLabelLineLength(0d);
                line.getChildren().add(pie);
                drawPie(valueName, pie, time, timeReports);
            }

        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    protected void drawPie(String valueName, PieChart pie,
            String time, List<EpidemicReport> timeReports) {
        try {
            if (valueName == null || time == null || pie == null
                    || timeReports == null || timeReports.isEmpty()) {
                return;
            }
            float total = 0;
            for (EpidemicReport report : timeReports) {
                Number value = report.value(valueName);
                if (value == null) {
                    continue;
                }
                total += value.floatValue();
            }
            if (total == 0) {
                return;
            }

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
            pie.setData(pieData);

            String label;
            List<String> palette = new ArrayList();
            for (EpidemicReport report : timeReports) {
                String name = (multipleDatasets ? report.getDataSet() + " - " : "") + report.getLocationFullName();
                Number value = report.value(valueName);
                if (value == null) {
                    continue;
                }
                double d = value.doubleValue();
                double percent = DoubleTools.scale(d * 100 / total, 1);
                String labelValue = StringTools.format(d);
                switch (labelType) {
                    case Name:
                        label = name;
                        break;
                    case Value:
                        label = percent + "% " + labelValue;
                        break;
                    case NameAndValue:
                        label = name + " " + percent + "% " + labelValue;
                        break;
                    case NotDisplay:
                    default:
                        label = "";
                        break;
                }
                PieChart.Data item = new PieChart.Data(label, d);
                pieData.add(item);
                if (labelType == LabelType.Pop) {
                    FxmlControl.setTooltip(item.getNode(), name + " " + percent + "% " + labelValue);
                }
                palette.add(settingsController.locationColors.get(report.getLocationFullName()));
            }

            FxmlControl.setPieColors(pie, palette, legendSide != null);
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    protected LineChart addLinesChart(boolean vertical) {
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.setSide(Side.BOTTOM);
        categoryAxis.setTickLabelsVisible(categoryAxisCheck.isSelected());
        categoryAxis.setGapStartAndEnd(true);

        categoryAxis.setTickLabelRotation(90);
        categoryAxis.setAnimated(false);
        NumberAxis numberAxis = new NumberAxis();
        numberAxis.setSide(Side.LEFT);
        numberAxis.setAnimated(false);
        LineChart lineChart;
        if (vertical) {
            categoryAxis.setEndMargin(100);
            lineChart = new LineChart(categoryAxis, numberAxis);
        } else {
            categoryAxis.setEndMargin(20);
            lineChart = new LineChart(numberAxis, categoryAxis);
        }
        lineChart.setAlternativeRowFillVisible(false);
        lineChart.setAlternativeColumnFillVisible(false);
        lineChart.setAnimated(false);
        lineChart.setCreateSymbols(true);
        lineChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        lineChart.setVerticalGridLinesVisible(vlinesCheck.isSelected());
        lineChart.setHorizontalGridLinesVisible(hlinesCheck.isSelected());
        VBox.setVgrow(lineChart, Priority.ALWAYS);
        HBox.setHgrow(lineChart, Priority.ALWAYS);
        if (legendSide == null) {
            lineChart.setLegendVisible(false);
        } else {
            lineChart.setLegendVisible(true);
            lineChart.setLegendSide(legendSide);
        }
        switch (chartCoordinate) {
            case LogarithmicE:
                numberAxis.setTickLabelFormatter(new LogarithmicECoordinate());
                break;
            case Logarithmic10:
                numberAxis.setTickLabelFormatter(new Logarithmic10Coordinate());
                break;
            case SquareRoot:
                numberAxis.setTickLabelFormatter(new SquareRootCoordinate());
                break;
        }
        return lineChart;
    }

    protected XYChart.Data lineDataNode(String label, String prefix, double value, boolean vertical, boolean last) {
        double coordinateValue = FxmlControl.coordinateValue(chartCoordinate, value);
        XYChart.Data data;
        if (vertical) {
            data = new XYChart.Data(label, coordinateValue);
        } else {
            data = new XYChart.Data(coordinateValue, label);
        }
        String finalLabel = prefix == null ? label : prefix + "\n" + label;
        String finalValue = StringTools.format(value);
        if (last) {
            String valueLabel = finalLabel + " " + finalValue;
            Label text = new Label(valueLabel);
            text.setStyle("-fx-background-color: transparent;  -fx-font-size: " + textSize + "px; -fx-font-weight: bolder;");
            data.setNode(text);
        } else if (labelType == LabelType.Pop) {
            Label text = new Label("");
            text.setStyle("-fx-background-color: transparent;  -fx-font-size: " + textSize + "px; -fx-font-weight: bolder;");
            data.setNode(text);
            FxmlControl.setTooltip(text, finalLabel + " " + finalValue);
        } else {
            String valueLabel;
            switch (labelType) {
                case Name:
                    valueLabel = finalLabel;
                    break;
                case NameAndValue:
                    valueLabel = finalLabel + " " + finalValue;
                    break;
                case Value:
                    valueLabel = finalValue;
                    break;
                default:
                    valueLabel = "";
                    break;
            }
            Label text = new Label(valueLabel);
            text.setStyle("-fx-background-color: transparent;  -fx-font-size: " + textSize + "px;");
            data.setNode(text);
        }
        return data;
    }

    protected void drawValuesLines(String time, List<EpidemicReport> timeReports, boolean vertical) {
        try {
            if (locationsReports == null
                    || time == null || timeReports == null || timeReports.isEmpty()) {
                return;
            }
            chartBox.getChildren().clear();
            HBox line = new HBox();
            int colsNum = (int) Math.sqrt(valuesNames.size());
            colsNum = Math.max(colsNum, valuesNames.size() / colsNum);
            for (int i = 0; i < valuesNames.size(); i++) {
                String valueName = valuesNames.get(i);
                if (i % colsNum == 0) {
                    line = new HBox();
                    line.setAlignment(Pos.TOP_CENTER);
                    line.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    line.setSpacing(5);
                    VBox.setVgrow(line, Priority.ALWAYS);
                    HBox.setHgrow(line, Priority.ALWAYS);
                    chartBox.getChildren().add(line);
                }
                LineChart lineChart = addLinesChart(vertical);
                lineChart.setTitle(valueName + " - " + time);
                lineChart.setAlternativeRowFillVisible(false);
                lineChart.setAlternativeColumnFillVisible(false);
                lineChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                VBox.setVgrow(lineChart, Priority.ALWAYS);
                HBox.setHgrow(lineChart, Priority.ALWAYS);
                line.getChildren().add(lineChart);
                drawValueLines(valueName, lineChart, time, vertical);
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    protected void drawValueLines(String valueName, LineChart lineChart,
            String time, boolean vertical) {
        try {
            if (valueName == null || lineChart == null
                    || time == null || locationsReports == null) {
                return;
            }
            Map<String, String> palette = new HashMap();
            Map<String, XYChart.Series> seriesMap = new HashMap();
            for (int i = 0; i < chartTimes.size(); i++) {
                String date = chartTimes.get(i);
                int dateCompare = date.compareTo(time);
                if (dateCompare > 0) {
                    break;
                }
                List<EpidemicReport> reports = locationsReports.get(date);
                if (reports == null || reports.isEmpty()) {
                    continue;
                }
                for (int j = 0; j < reports.size(); j++) {
                    EpidemicReport report = reports.get(j);
                    Number value = report.value(valueName);
                    if (value == null) {
                        continue;
                    }
                    GeographyCode location = report.getLocation();
                    String locationName = location.getFullName();
                    String lineName = (multipleDatasets ? report.getDataSet() + " - " : "") + locationName;
                    XYChart.Series series = seriesMap.get(lineName);
                    if (series == null) {
                        series = new XYChart.Series();
                        series.setName(lineName);
                        lineChart.getData().add(series);
                        seriesMap.put(lineName, series);
                        palette.put(lineName, settingsController.locationColors.get(locationName));
                    }
                    if (dateCompare == 0) {
                        series.getData().add(lineDataNode(date, lineName, value.doubleValue(), vertical, true));
                    } else if (multipleDatasets) {
                        series.getData().add(lineDataNode(date, report.getDataSet(), value.doubleValue(), vertical, false));
                    } else {
                        series.getData().add(lineDataNode(date, null, value.doubleValue(), vertical, false));
                    }
                }
            }

            FxmlControl.setLineChartColors(lineChart, palette, legendSide != null);
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    protected LabeledBarChart addVerticalBarChart() {
        chartBox.getChildren().clear();
//        boolean intValue = !message("HealedRatio").equals(valueName)
//                && !message("DeadRatio").equals(valueName);
        LabeledBarChart barChart
                = LabeledBarChart.create(categoryAxisCheck.isSelected(), chartCoordinate)
                        .setIntValue(false)
                        .setLabelType(labelType)
                        .setTextSize(textSize);
        barChart.setAlternativeRowFillVisible(false);
        barChart.setAlternativeColumnFillVisible(false);
        barChart.setBarGap(0.0);
        barChart.setCategoryGap(0.0);
        barChart.setAnimated(false);
        barChart.getXAxis().setAnimated(false);
        barChart.getYAxis().setAnimated(false);
        barChart.getXAxis().setTickLabelRotation(90);
        barChart.setVerticalGridLinesVisible(vlinesCheck.isSelected());
        barChart.setHorizontalGridLinesVisible(hlinesCheck.isSelected());
        barChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(barChart, Priority.ALWAYS);
        HBox.setHgrow(barChart, Priority.ALWAYS);
        if (legendSide == null) {
            barChart.setLegendVisible(false);
        } else {
            barChart.setLegendVisible(true);
            barChart.setLegendSide(legendSide);
        }
        chartBox.getChildren().add(barChart);
        return barChart;
    }

    protected LabeledHorizontalBarChart addHorizontalBarChart() {
        chartBox.getChildren().clear();
        LabeledHorizontalBarChart barChart
                = LabeledHorizontalBarChart.create(categoryAxisCheck.isSelected(), chartCoordinate)
                        .setIntValue(false)
                        .setLabelType(labelType)
                        .setTextSize(textSize);
        barChart.setAlternativeRowFillVisible(false);
        barChart.setAlternativeColumnFillVisible(false);
        barChart.setBarGap(0.0);
        barChart.setCategoryGap(0.0);
        barChart.setAnimated(false);
        barChart.getXAxis().setAnimated(false);
        barChart.getYAxis().setAnimated(false);
        barChart.setVerticalGridLinesVisible(vlinesCheck.isSelected());
        barChart.setHorizontalGridLinesVisible(hlinesCheck.isSelected());
        barChart.getXAxis().setTickLabelRotation(90);
        barChart.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(barChart, Priority.ALWAYS);
        HBox.setHgrow(barChart, Priority.ALWAYS);
        if (legendSide == null) {
            barChart.setLegendVisible(false);
        } else {
            barChart.setLegendVisible(true);
            barChart.setLegendSide(legendSide);
        }
        chartBox.getChildren().add(barChart);
        return barChart;
    }

    protected void drawValuesBarsVertical(List<EpidemicReport> timeReports) {
        try {
            if (valuesNames.size() <= 1 || timeReports == null || timeReports.isEmpty()) {
                return;
            }
            LabeledBarChart barChart = addVerticalBarChart();

            Map<String, String> palette = new HashMap();
            List<XYChart.Series> seriesList = new ArrayList<>();
            for (int i = 0; i < valuesNames.size(); i++) {
                String valueName = valuesNames.get(i);
                XYChart.Series series = new XYChart.Series();
                series.setName(valueName);
                seriesList.add(series);
                barChart.getData().add(i, series);
                palette.put(valueName, settingsController.color(valueName));
            }
            for (EpidemicReport report : timeReports) {
                String label = (multipleDatasets ? report.getDataSet() + " - " : "") + report.getLocationFullName();
                for (int i = 0; i < valuesNames.size(); i++) {
                    XYChart.Series series = seriesList.get(i);
                    String valueName = valuesNames.get(i);
                    Number value = report.value(valueName);
                    if (value == null) {
                        continue;
                    }
                    double coordinateValue = FxmlControl.coordinateValue(chartCoordinate, value.doubleValue());
                    XYChart.Data item = new XYChart.Data(label, coordinateValue);
                    series.getData().add(item);
                    if (labelType == LabelType.Pop) {
                        FxmlControl.setTooltip(item.getNode(), label + " " + value);
                    }
                }
            }
            FxmlControl.setBarChartColors(barChart, palette, legendSide != null);
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    protected void drawValuesBarsHorizontal(List<EpidemicReport> timeReports) {
        try {
            if (valuesNames.size() <= 1 || timeReports == null || timeReports.isEmpty()) {
                return;
            }
            LabeledHorizontalBarChart barChart = addHorizontalBarChart();

            Map<String, String> palette = new HashMap();
            List<XYChart.Series> seriesList = new ArrayList<>();
            for (int i = 0; i < valuesNames.size(); i++) {
                String valueName = valuesNames.get(i);
                XYChart.Series series = new XYChart.Series();
                series.setName(valueName);
                seriesList.add(series);
                barChart.getData().add(i, series);
                palette.put(valueName, settingsController.color(valueName));
            }
            for (int i = timeReports.size() - 1; i >= 0; i--) {
                EpidemicReport report = timeReports.get(i);
                String label = (multipleDatasets ? report.getDataSet() + " - " : "") + report.getLocationFullName();
                for (int j = 0; j < valuesNames.size(); j++) {
                    XYChart.Series series = seriesList.get(j);
                    String valueName = valuesNames.get(j);
                    Number value = report.value(valueName);
                    if (value == null) {
                        continue;
                    }
                    double coordinateValue = FxmlControl.coordinateValue(chartCoordinate, value.doubleValue());
                    XYChart.Data item = new XYChart.Data(coordinateValue, label);
                    series.getData().add(item);
                    if (labelType == LabelType.Pop) {
                        FxmlControl.setTooltip(item.getNode(), label + " " + value);
                    }
                }
            }

            FxmlControl.setBarChartColors(barChart, palette, legendSide != null);
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    protected void drawValueBarsVertical(List<EpidemicReport> timeReports) {
        if (valuesNames.size() != 1 || timeReports == null || timeReports.isEmpty()) {
            return;
        }
        String valueName = orderNames.get(0);
        LabeledBarChart barChart = addVerticalBarChart();

        Map<String, String> palette = new HashMap();
        for (int i = 0; i < timeReports.size(); i++) {
            EpidemicReport report = timeReports.get(i);
            String location = report.getLocationFullName();
            String label = (multipleDatasets ? report.getDataSet() + " - " : "") + location;

            Number value = report.value(valueName);
            if (value == null) {
                continue;
            }
            double coordinateValue = FxmlControl.coordinateValue(chartCoordinate, value.doubleValue());
            XYChart.Series series = new XYChart.Series();
            series.setName(label);
            palette.put(label, settingsController.locationColors.get(location));
            XYChart.Data item = new XYChart.Data(label, coordinateValue);
            series.getData().add(item);
            barChart.getData().add(series);
            if (labelType == LabelType.Pop) {
                FxmlControl.setTooltip(item.getNode(), label + " " + value);
            }
        }
        FxmlControl.setBarChartColors(barChart, palette, legendSide != null);
    }

    protected void drawValueBarsHorizontal(List<EpidemicReport> timeReports) {
        if (valuesNames.size() != 1 || timeReports == null || timeReports.isEmpty()) {
            return;
        }
        String valueName = orderNames.get(0);
        LabeledHorizontalBarChart barChart = addHorizontalBarChart();

        Map<String, String> palette = new HashMap();
        for (int i = timeReports.size() - 1; i >= 0; i--) {
            EpidemicReport report = timeReports.get(i);
            String location = report.getLocationFullName();
            String label = (multipleDatasets ? report.getDataSet() + " - " : "") + location;
            palette.put(label, settingsController.locationColors.get(location));
            Number value = report.value(valueName);
            if (value == null) {
                continue;
            }
            double coordinateValue = FxmlControl.coordinateValue(chartCoordinate, value.doubleValue());
            XYChart.Series series = new XYChart.Series();
            series.setName(label);
            XYChart.Data item = new XYChart.Data(coordinateValue, label);
            series.getData().add(item);
            barChart.getData().add(series);
            if (labelType == LabelType.Pop) {
                FxmlControl.setTooltip(item.getNode(), label + " " + value);
            }
        }
        FxmlControl.setBarChartColors(barChart, palette, legendSide != null);
    }

    protected void drawMap(List<EpidemicReport> timeReports) {
        try {
            if (timeReports == null || timeReports.isEmpty()) {
                return;
            }
            webEngine.executeScript("clearMap();");
            for (EpidemicReport report : timeReports) {
                GeographyCode location = report.getLocation();
                if (!location.validCoordinate()) {
                    continue;
                }
                if (!mapCentered) {
                    webEngine.executeScript("setCenter("
                            + location.getLongitude() + "," + location.getLatitude() + ");");
                    mapCentered = true;
                }
                String name = "<font color=\"black\">" + (multipleDatasets ? report.getDataSet() + " - " : "") + location.getFullName() + "</font>";
                String value = "";
                for (String valueName : valuesNames) {
                    value += " <font color=\"" + settingsController.color(valueName) + "\">" + report.value(valueName) + "</font> ";
                }
                String label;
                switch (labelType) {
                    case Name:
                        label = "<div>" + name + "</div>";
                        break;
                    case NameAndValue:
                        label = "<div>" + name + value + "</div>";
                        break;
                    case NotDisplay:
                        label = "";
                        break;
                    case Value:
                    default:
                        label = value;
                        break;
                }
                String info = popInfoCheck.isSelected() || labelType == LabelType.Pop
                        ? "<div>" + name + value + "</div></br>" + location.info("</br>") : "";
                markerSize = markSize(report.value(orderNames.get(0)).doubleValue());
                LocationTools.addMarkerInGaoDeMap(webEngine,
                        location.getLongitude(), location.getLatitude(),
                        label, info,
                        locationImage(), true, -99, markerSize, textSize);
            }
        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    // maximum marker size of GaoDe Map is 64
    protected int markSize(double value) {
        if (maxValue == 0) {
            markerSize = 20;
            return markerSize;
        }
        double d, m;
        switch (chartCoordinate) {
            case LogarithmicE:
                d = Math.log(value);
                m = Math.log(maxValue);
                break;
            case Logarithmic10:
                d = Math.log10(value);
                m = Math.log10(maxValue);
                break;
            case SquareRoot:
                d = Math.sqrt(value);
                m = Math.sqrt(maxValue);
                break;
            default:
                d = value;
                m = maxValue;
        }
        markerSize = Math.min(60, Math.max(6, (int) (d * 60 / m)));
        return markerSize;
    }

    @FXML
    protected void snapAction() {
        try {
            snapWidth = settingsController.snapWidth;
            chartLoadTime = settingsController.chartLoadTime;
            dpi = settingsController.dpi;

            final SnapshotParameters snapPara;
            final double scale;
            double scalev = dpi / Screen.getPrimary().getDpi();
            scale = scalev > 1 ? scalev : 1;
            snapPara = new SnapshotParameters();
            snapPara.setFill(Color.WHITE);
            snapPara.setTransform(Transform.scale(scale, scale));

            if (timer == null) {
                openImageViewer(snapWidth(snapPara, scale, Integer.MAX_VALUE, chartSnapBox));
                return;
            }
            timer.cancel();
            timer = null;
            List<File> snapshots = new ArrayList<>();
            loading = openHandlingStage(Modality.WINDOW_MODAL);
            byte[] lock = new byte[0];
            int speed = chartLoadTime + 100;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                private int index = 0;

                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (loading == null || loading.isIsCanceled()) {
                            timer.cancel();
                            timer = null;
                            drawChart();
                            return;
                        }
                        if (index > chartTimes.size() - 1) {
                            timer.cancel();
                            timer = null;
                            if (snapshots.isEmpty()) {
                                return;
                            }
                            if (snapshots.size() == 1) {
                                openImageViewer(snapshots.get(0));
                            } else {
                                File file = FileTools.getTempFile(".gif");
                                if (loading != null) {
                                    loading.setInfo(message("Saving") + ": " + file);
                                }
                                ImageGifFile.writeImageFiles(snapshots, file, interval, true);
                                if (file.exists()) {
                                    ImageGifViewerController controller
                                            = (ImageGifViewerController) openStage(CommonValues.ImageGifViewerFxml);
                                    controller.sourceFileChanged(file);
                                }
                            }
                            if (loading != null) {
                                loading.closeStage();
                                loading = null;
                            }
                            drawChart();
                            return;
                        }
                        if (loading != null) {
                            loading.setInfo(message("Snapping") + ": " + (index + 1) + "/" + chartTimes.size());
                        }
                        synchronized (lock) {
                            drawFrame(index);
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                            }
                            snapshots.add(snapWidth(snapPara, scale, snapWidth, chartSnapBox));
                            ++index;
                        }
                    });
                }

            }, 0, speed + 100);

        } catch (Exception e) {
            logger.debug(e.toString());
        }
    }

    protected File snapWidth(SnapshotParameters snapPara, double scale, int width, Region chart) {
        try {
            Bounds bounds = chart.getLayoutBounds();
            int imageWidth = (int) Math.round(bounds.getWidth() * scale);
            int imageHeight = (int) Math.round(bounds.getHeight() * scale);
            Image snap = chart.snapshot(snapPara, new WritableImage(imageWidth, imageHeight));
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snap, null);
            if (bufferedImage.getWidth() > width) {
                bufferedImage = ImageManufacture.scaleImageWidthKeep(bufferedImage, snapWidth);
            }
            File tmpfile = FileTools.getTempFile(".png");
            ImageFileWriters.writeImageFile(bufferedImage, "png", tmpfile.getAbsolutePath());
            return tmpfile;
        } catch (Exception e) {
            logger.debug(e.toString());
            return null;
        }
    }

    @FXML
    @Override
    public void clearAction() {
        webEngine.executeScript("clearMap();");
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public String locationImage() {
        String path = "/" + ControlStyle.getIconPath();
        return FxmlControl.getInternalFile(path + "iconCircle.png", "map",
                AppVariables.ControlColor.name() + "Circle.png").getAbsolutePath();
    }

    protected void setPause(boolean setAsPaused) {
        if (setAsPaused) {
            ControlStyle.setIcon(pauseButton, ControlStyle.getIcon("iconPlay.png"));
            FxmlControl.setTooltip(pauseButton, new Tooltip(message("Continue")));
            previousButton.setDisable(false);
            nextButton.setDisable(false);
            pauseButton.setUserData("paused");
        } else {
            ControlStyle.setIcon(pauseButton, ControlStyle.getIcon("iconPause.png"));
            FxmlControl.setTooltip(pauseButton, new Tooltip(message("Pause")));
            previousButton.setDisable(true);
            nextButton.setDisable(true);
            pauseButton.setUserData("playing");
        }
        pauseButton.applyCss();
    }

    @FXML
    public void pauseAction() {
        Platform.runLater(() -> {
            if (pauseButton.getUserData() != null && "paused".equals(pauseButton.getUserData())) {
                drawChart();

            } else {
                setPause(true);
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                chartIndex--;
            }
        });
    }

    @FXML
    @Override
    public void previousAction() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        drawFrame(chartIndex - 1);
        setPause(true);
    }

    @FXML
    @Override
    public void nextAction() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        drawFrame(chartIndex + 1);
        setPause(true);
    }

    /*
        get/set
     */
    public EpidemicReportsController getReportsController() {
        return reportsController;
    }

    public void setReportsController(EpidemicReportsController reportsController) {
        this.reportsController = reportsController;
    }

    public EpidemicReportsSettingsController getSettingsController() {
        return settingsController;
    }

    public void setSettingsController(EpidemicReportsSettingsController settingsController) {
        this.settingsController = settingsController;
    }

}
