package com.boyarskycompany.mcis.controllers;


import com.boyarskycompany.mcis.persistence.GenericDAOImpl;
import com.boyarskycompany.mcis.run.MainApplicationEntry;
import com.boyarskycompany.mcis.util.DoubleTuple;
import com.boyarskycompany.mcis.util.EntityClassUtil;
import com.boyarskycompany.mcis.util.StageRegister;
import com.boyarskycompany.mcis.util.alerts.ConfirmationAlert;
import com.boyarskycompany.mcis.util.alerts.ErrorParsingAlert;
import com.boyarskycompany.mcis.util.alerts.WarningAlert;
import com.boyarskycompany.mcis.util.converters.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import jfxtras.scene.control.CalendarTextField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.prefs.Preferences;

import static com.boyarskycompany.mcis.util.EntityClassUtil.getSimpleClassName;
import static com.boyarskycompany.mcis.util.EntityClassUtil.getTableAndEntityNames;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by zandr on 10.10.2016.
 */
public class EntityController<T> implements Initializable {
    private ResourceBundle res = MainApplicationEntry.getLanguageResourceBundle();
    private Class<T> cl;
    private ArrayList<Field> fields;
    private ArrayList<Class> typeList = new ArrayList();
    private TextField searchField;
    private ArrayList<GenericDAOImpl> baseDAUs = new ArrayList<>();
    private Preferences preferences;
    private GenericDAOImpl<T> dauImpl;
    private TableView<T> table = new TableView<T>();
    private ArrayList<TableColumn<T, ?>> columns = new ArrayList<TableColumn<T, ?>>();
    private BorderPane root = new BorderPane();
    private BorderPane additionalBorderPane = new BorderPane();
    private HBox fieldContainer = new HBox();

    private Button addRecordButton;
    private Button findButton;
    private MenuBar mainMenu = new MenuBar();
    private ContextMenu tableContextMenu;
    private BorderPane toolsPane = new BorderPane();
    private HashMap<Class, ComboBox<Long>> idClassChoosersMap = new HashMap<>();
    private Stage stage = new Stage();

    public EntityController(Class<T> cl) {
        this.cl = cl;
        fields = new ArrayList<Field>(Arrays.asList(cl.getDeclaredFields()));
        Iterator<Field> iter = fields.iterator();
        while (iter.hasNext()) {
            Field field = iter.next();
            if (field.getType().getSimpleName().contains("Collection") || field.getType().getName().contains("entities")) {
                iter.remove();
            }
        }
        dauImpl = new GenericDAOImpl<T>(cl);
        preferences = Preferences.userNodeForPackage(cl);
        if (getLongEntityCounter() <= 1) {
            Long maxId = getMaxId(true);
            if (maxId != null) {
                createEntityCounter(maxId + 1);
            }
        }
        searchField = new TextField();
        searchField.setPromptText(MainApplicationEntry.getLanguageResourceBundle().getString("findButton"));
        searchField.setMinHeight(1d);
        searchField.setPrefHeight(1d);
        construct();
        showEntity();
    }

    public <S> EntityController(Class<T> childEntityClass, S parentRecord, Class<S> parentEntityClass) {
        this(childEntityClass);
        table.getItems().clear();
        DoubleTuple<ArrayList<T>, HashMap<Field, Long>> tuple = getBoundedRecords(parentEntityClass, parentRecord);
        table.getItems().addAll(tuple.getFirstField());
        ObservableList<Node> nodes = getFieldContainer().getChildren();
        for (int i = 0; i < nodes.size(); i++) {
            if (ComboBox.class.isInstance(nodes.get(i))) {
                Iterator<Map.Entry<Field, Long>> iter =
                        tuple.getSecondField().entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = iter.next();
                    String fieldName = ((Field) entry.getKey()).getName();
                    if (getFields().get(i).getName().equalsIgnoreCase(fieldName)) {
                        ComboBox<Long> idComboBox = ((ComboBox<Long>) nodes.get(i));
                        idComboBox.getItems().clear();
                        idComboBox.setValue((Long) entry.getValue());
                        idComboBox.setEditable(false);
                    }
                }
            }
        }
    }

    public Long getMaxId(boolean isLast) {return dauImpl.getMaxId(isLast);}

    public <S> DoubleTuple<ArrayList<T>, HashMap<Field, Long>> getBoundedRecords(Class<S> relatedClass, S record) {
        return dauImpl.getBoundedRecordsWithParentRecord(relatedClass, record);
    }

    protected MenuBar getMainMenu() {
        return mainMenu;
    }

    protected void setMainMenu(MenuBar mainMenu) {
        this.mainMenu = mainMenu;
    }


    protected Button getAddRecordButton() {
        return addRecordButton;
    }

    protected void setAddRecordButton(Button addRecordButton) {
        this.addRecordButton = addRecordButton;
    }

    protected Button getFindButton() {
        return findButton;
    }

    protected void setFindButton(Button findButton) {
        this.findButton = findButton;
    }

    protected ContextMenu getTableContextMenu() {
        return tableContextMenu;
    }

    protected void setTableContextMenu(ContextMenu tableContextMenu) {
        this.tableContextMenu = tableContextMenu;
    }

    protected Class<T> getCl() {
        return cl;
    }

    protected void setCl(Class<T> cl) {
        this.cl = cl;
    }

    protected ArrayList<Field> getFields() {
        return fields;
    }

    protected void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }

    protected ArrayList<Class> getTypeList() {
        return typeList;
    }

    protected void setTypeList(ArrayList<Class> typeList) {
        this.typeList = typeList;
    }

    protected TextField getSearchField() {
        return searchField;
    }

    protected void setSearchField(TextField searchField) {
        this.searchField = searchField;
    }

    protected ArrayList<GenericDAOImpl> getBaseDAUs() {
        return baseDAUs;
    }

    protected void setBaseDAUs(ArrayList<GenericDAOImpl> baseDAUs) {
        this.baseDAUs = baseDAUs;
    }

    protected Preferences getPreferences() {
        return preferences;
    }

    protected void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    protected GenericDAOImpl<T> getDauImpl() {
        return dauImpl;
    }

    protected void setDauImpl(GenericDAOImpl<T> dauImpl) {
        this.dauImpl = dauImpl;
    }

    protected TableView<T> getTable() {
        return table;
    }

    protected void setTable(TableView<T> table) {
        this.table = table;
    }

    protected ArrayList<TableColumn<T, ?>> getColumns() {
        return columns;
    }

    protected void setColumns(ArrayList<TableColumn<T, ?>> columns) {
        this.columns = columns;
    }

    protected BorderPane getRoot() {
        return root;
    }

    protected void setRoot(BorderPane root) {
        this.root = root;
    }

    protected BorderPane getAdditionalBorderPane() {
        return additionalBorderPane;
    }

    protected void setAdditionalBorderPane(BorderPane additionalBorderPane) {
        this.additionalBorderPane = additionalBorderPane;
    }

    protected HBox getFieldContainer() {
        return fieldContainer;
    }

    protected void setFieldContainer(HBox fieldContainer) {
        this.fieldContainer = fieldContainer;
    }

    protected BorderPane getToolsPane() {
        return toolsPane;
    }

    protected void setToolsPane(BorderPane toolsPane) {
        this.toolsPane = toolsPane;
    }

    protected void createEntityCounter(long i) {
        preferences.putLong(cl.getSimpleName(), i);
    }

    protected void createEntityCounter() {
        createEntityCounter(1);
    }

    protected long getLongEntityCounter() {
        return preferences.getLong(cl.getSimpleName(), -1);
    }

    protected String getStringEntityCounter() {
        return Long.toString(preferences.getLong(cl.getSimpleName(), -1));
    }

    protected long incrementEntityCounter() {
        long counter =
                preferences.getLong(cl.getSimpleName(), -1);
        if (counter == -1) {
            throw new RuntimeException("Preferences for" + cl.getName() + "are not found");
        } else {
            counter++;
            preferences.putLong(cl.getSimpleName(), counter);
        }
        return counter;
    }

    protected List<T> getListRecordsFromDatabase() {
        return dauImpl.getRecordList();
    }

    protected ObservableList<T> getObservableListRecordsFromDatabase() {
        return dauImpl.getObservableListRecords();
    }

    protected void pushRecordIntoDatabase(T record) {
        dauImpl.add(record);
    }

    protected T getRecordByIDFromDatabase(long id) {
        return dauImpl.getRecordByID(id);
    }

    protected void updateRecordInDatabase(T record) {
        dauImpl.update(record);
    }

    protected void deleteRecordFromDatabase(T record) {
        dauImpl.deleteRecord(record);
    }

    protected void resetFields(ObservableList<Node> nodes) {
        nodes.forEach(node -> {
            if (TextField.class.isInstance(node)) {
                TextField textField = ((TextField) node);
                if (textField.isEditable() == false) {
                    textField.setText(getStringEntityCounter());
                } else {
                    textField.clear();
                }
            } else if (CalendarTextField.class.isInstance(node)) {
                ((CalendarTextField) node).setCalendar(Calendar.getInstance());
            }
        });
    }

    private void removeRecord() {
        T record = table.getSelectionModel().getSelectedItem();
        if (record != null) {
            WarningAlert alert = new WarningAlert("warningDeletingRecordContentText");
            if (alert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                if (haveChilds(record)) {
                    WarningAlert haveRelatedRecordsAlert = new WarningAlert("warningHaveRelatedRecordsContentText");
                    if (haveRelatedRecordsAlert.showAndWait().get().getButtonData() == ButtonBar.ButtonData.YES) {
                        deleteRecordFromDatabase(record);
                        table.getItems().remove(record);
                        table.refresh();
                    }
                } else {
                    deleteRecordFromDatabase(record);
                    table.getItems().remove(record);
                    table.refresh();
                }
            }
        }
    }

    private void construct() {
        String simpleEntityName = EntityClassUtil.getLowerCaseVariableName(cl);
        createTableColumns(simpleEntityName);
        table.setEditable(true);
        table.getColumns().addAll(columns);
        table.getItems().addAll(getObservableListRecordsFromDatabase());
        table.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                removeRecord();
            }
        });
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefViewportHeight(27);
        scrollPane.setPrefViewportWidth(table.getWidth());
        scrollPane.setContent(fieldContainer);
        Text entityListLabel = new Text(res.getString(simpleEntityName + "ListLabel"));
        entityListLabel.setFont(Font.font("Arial", 24));
        entityListLabel.setTextAlignment(TextAlignment.CENTER);
        entityListLabel.setTextOrigin(VPos.CENTER);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(entityListLabel, table, scrollPane);
        root.setCenter(vBox);
        findButton = new Button(res.getString("findButton"));
        findButton.setOnAction(e -> configureSearching());
        addRecordButton = new Button(res.getString("add" + EntityClassUtil.getSimpleClassName(cl) + "Button"));
        addRecordButton.setOnAction(e -> addRecord());
        attachContextMenu();
        configureMainMenu(mainMenu);
        configureNavigationBar(root);
        toolsPane.setTop(mainMenu);
        root.setTop(toolsPane);
        additionalBorderPane.setLeft(findButton);
        additionalBorderPane.setCenter(addRecordButton);
        additionalBorderPane.setBottom(searchField);
        root.setBottom(additionalBorderPane);
    }

    private void addRecord() {
        T record = createNewRecord();
        if (record != null) {
            ConfirmationAlert confirmation = new ConfirmationAlert("confirmationAddingRecordContentText");
            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                pushRecordIntoDatabase(record);
                resetStyles(fieldContainer.getChildren());
                incrementEntityCounter();
                resetFields(fieldContainer.getChildren());
                table.getItems().add(record);
            }
        }
    }

    private boolean haveChilds(T record) {
        Field[] fieldArray = cl.getDeclaredFields();
        for (int i = 0; i < fieldArray.length; i++) {
            if (fieldArray[i].getType().getSimpleName().contains("collection")) {
                Field field =
                        fieldArray[i];
                Class clazz = EntityClassUtil.loadClassFromCollectionField(field.getName());
                DoubleTuple<ArrayList<T>, HashMap<Field, Long>> recordTuple = getBoundedRecords(clazz, record);
                if (recordTuple.getFirstField() == null || recordTuple.getFirstField().size() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void createTableColumns(String simpleEntityName) {
        fields.forEach(field -> {
            field.setAccessible(true);
            String typeName = field.getType().getSimpleName().toLowerCase();
            String fieldName = field.getName();
            TableColumn tableColumn = null;
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Class<?> fieldClass = null;
            Control control = null;
            if (typeName.equals("long")) {
                tableColumn = new TableColumn<T, Long>(res.getString(fieldName + "Column"));
                tableColumn.setCellValueFactory(new PropertyValueFactory<T, Long>(fieldName));
                typeList.add(Long.class);
                fieldClass = Long.class;
                if (fieldName.equalsIgnoreCase("id" + simpleEntityName)) {
                    tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringToLongConverter()));
                    tableColumn.setEditable(false);
                    tableColumn.setPrefWidth(100);
                    TextField idField = new TextField();
                    System.out.println(fieldName);
                    idField.setEditable(false);
                    idField.setPrefSize(100, 25);
                    idField.setText(getStringEntityCounter());
                    control = idField;
                } else {
                    Class<?> entityClass = null;
                    try {
                        entityClass = EntityClassUtil.loadEntityClassFromIdFieldName(fieldName);
                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    Method gett = null;
                    try {
                        gett = cl.getMethod(getMethodName);
                    }
                    catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    Annotation[] annotations = gett.getDeclaredAnnotations();
                    for (int i = 0; i < annotations.length; i++) {
                        if (annotations[i].toString().contains("ID")) {

                        }
                    }
                    ObservableList<Long> idList = getListEntityID(entityClass);
                    ComboBox<Long> idComboBox = new ComboBox<Long>(idList);
                    idComboBox.setPromptText(tableColumn.getText());
                    idComboBox.setEditable(false);
                    idComboBox.setPrefWidth(100);
                    idComboBox.setEditable(true);
                    control = idComboBox;
                    idClassChoosersMap.put(entityClass, idComboBox);
                    tableColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new StringToLongConverter(), idList));
                    tableColumn.setEditable(true);
                    tableColumn.setPrefWidth(100);
                }
            } else if (typeName.startsWith("int")) {
                fieldClass = Integer.class;
                typeList.add(Integer.class);
                tableColumn = new TableColumn<T, Integer>(res.getString(fieldName + "Column"));
                tableColumn.setCellValueFactory(new PropertyValueFactory<T, Integer>(fieldName));
                tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringToIntegerConverter()));
                tableColumn.setEditable(true);
                tableColumn.setPrefWidth(100);
                control = (createStandardTextField(tableColumn.getText()));
            } else {
                if (typeName.equals("float")) {
                    fieldClass = Float.class;
                    typeList.add(Float.class);
                    tableColumn = new TableColumn<T, Float>(res.getString(fieldName + "Column"));
                    tableColumn.setCellValueFactory(new PropertyValueFactory<T, Float>(fieldName));
                    tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringToFloatConverter()));
                    tableColumn.setEditable(true);
                    tableColumn.setPrefWidth(100);
                    control = (createStandardTextField(tableColumn.getText()));
                } else if (typeName.equalsIgnoreCase("double")) {
                    fieldClass = Double.class;
                    typeList.add(Double.class);
                    tableColumn = new TableColumn<T, Double>(res.getString(fieldName + "Column"));
                    tableColumn.setCellValueFactory(new PropertyValueFactory<T, Double>(fieldName));
                    tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDoubleConverter()));
                    tableColumn.setEditable(true);
                    tableColumn.setPrefWidth(100);
                    control = (createStandardTextField(tableColumn.getText()));
                } else {
                    if (typeName.equalsIgnoreCase("string")) {
                        fieldClass = String.class;
                        typeList.add(String.class);
                        tableColumn = new TableColumn<T,
                                String>(res.getString(fieldName + "Column"));
                        tableColumn.setCellValueFactory(new PropertyValueFactory<T, String>(fieldName));
                        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
                        tableColumn.setEditable(true);
                        tableColumn.setPrefWidth(100);
                        control = (createStandardTextField(tableColumn.getText()));
                    } else if (typeName.equalsIgnoreCase("timestamp")) {
                        fieldClass = Timestamp.class;
                        typeList.add(Timestamp.class);
                        tableColumn = new TableColumn<T, Timestamp>(res.getString(fieldName + "Column"));
                        Callback<TableColumn<T, Timestamp>, TableCell<T, Timestamp>> dateTimeCellFactory = (TableColumn<T, Timestamp> param) -> new DateTimeEditingCell();
                        tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures, ObservableValue>) celldata -> {
                            try {
                                Method getter = celldata.getValue().getClass().getDeclaredMethod(getMethodName);
                                getter.setAccessible(true);
                                return new SimpleObjectProperty<Timestamp>((Timestamp) getter.invoke(celldata.getValue()));
                            }
                            catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                            return null;
                        });
                        tableColumn.setCellFactory(dateTimeCellFactory);
                        tableColumn.setEditable(true);
                        tableColumn.setPrefWidth(250);
                        CalendarTextField calendarTextField = new CalendarTextField();
                        calendarTextField.setAllowNull(false);
                        calendarTextField.setShowTime(true);
                        calendarTextField.setPrefWidth(250);
                        calendarTextField.setDateFormat(new SimpleDateFormat("dd.MM.yyyy HH:mm"));
                        control = (calendarTextField);
                    } else if (typeName.equalsIgnoreCase("date")) {
                        fieldClass = java.sql.Date.class;
                        typeList.add(java.sql.Date.class);
                        tableColumn = new TableColumn<T, java.sql.Date>(res.getString(fieldName + "Column"));
                        Callback<TableColumn<T, java.sql.Date>, TableCell<T, java.sql.Date>> dateCellFactory = (TableColumn<T, java.sql.Date> param) -> new DateEditingCell();
                        tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures, ObservableValue>) celldata -> {
                            try {
                                Method getter = celldata.getValue().getClass().getDeclaredMethod(getMethodName);
                                getter.setAccessible(true);
                                return new SimpleObjectProperty<java.sql.Date>((java.sql.Date) getter.invoke(celldata.getValue()));
                            }
                            catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }
                            return null;
                        });
                        tableColumn.setCellFactory(dateCellFactory);
                        tableColumn.setEditable(true);
                        tableColumn.setPrefWidth(150);
                        CalendarTextField calendarTextField = new CalendarTextField();
                        calendarTextField.setAllowNull(false);
                        calendarTextField.setShowTime(false);
                        calendarTextField.setPrefWidth(150);
                        calendarTextField.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
                        control = (calendarTextField);
                    } else {
                        if (typeName.equals("boolean")) {
                            fieldClass = Boolean.class;
                            typeList.add(Boolean.class);
                            tableColumn = new TableColumn<T, Boolean>(res.getString(fieldName + "Column"));
                            tableColumn.setCellValueFactory(new PropertyValueFactory<T, Boolean>(fieldName));
                            tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringToBooleanConverter()));
                            tableColumn.setEditable(true);
                            tableColumn.setPrefWidth(80);
                            CheckBox booleanField = new CheckBox(tableColumn.getText());
                            booleanField.setSelected(true);
                            booleanField.setPrefWidth(80);
                            control = (booleanField);
                        }
                    }
                }
            }
            Class<?> finalFieldClass = fieldClass;
            tableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
                @Override
                public void handle(TableColumn.CellEditEvent event) {
                    Method setter = null;
                    String setMethodName = "s" + getMethodName.substring(1);
                    try {
                        setter = cl.getDeclaredMethod(setMethodName, finalFieldClass);
                    }
                    catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                    setter.setAccessible(true);
                    T record = (T) event.getRowValue();
                    if (event.getNewValue() == null) {
                        table.refresh();
                        return;
                    }
                    ConfirmationAlert alert = new
                            ConfirmationAlert("confirmationUpdateRecordContentText");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                        try {
                            setter.invoke(record, finalFieldClass.cast(event.getNewValue()));
                        }
                        catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        updateRecordInDatabase(record);
                        getTable().refresh();
                    }
                }
            });
            control.setMinWidth(5);
            TableColumn finalTableColumn = tableColumn;
            Control finalControl = control;
            tableColumn.widthProperty().addListener(e -> {
                finalControl.prefWidthProperty().setValue(finalTableColumn.getWidth());
            });
            fieldContainer.getChildren().add(control);
            columns.add(tableColumn);
        });

    }

    private T createNewRecord() {
        ObservableList<Node> list = fieldContainer.getChildren();
        T newRecord = null;
        try {
            newRecord = cl.newInstance();
        }
        catch (InstantiationException e1) {
            e1.printStackTrace();
        }
        catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }

        for (int i = 0; i < list.size(); i++) {
            Node node = list.get(i);
            Field field = fields.get(i);
            String fieldName = field.getName();
            Method setter = null;
            fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Character.toUpperCase(fieldName.charAt(0));
            Class fieldClass = typeList.get(i);
            if (TextField.class.isInstance(node)) {
                TextField textField = (TextField) node;
                String text = textField.getText();
                try {
                    if (fieldClass.equals(Long.class)) {
                        setter = cl.getDeclaredMethod("set" + fieldName, Long.class);
                        setter.setAccessible(true);
                        Long l = Long.parseLong(text);
                        if (l > 0) {
                            setter.invoke(newRecord, l);
                        }
                    } else if (fieldClass.equals(Integer.class)) {
                        setter = cl.getDeclaredMethod("set" + fieldName, Integer.class);
                        setter.setAccessible(true);
                        Integer integer = Integer.parseInt(text);
                        if (integer > 0) {
                            setter.invoke(newRecord, integer);
                        }
                    } else if (fieldClass.equals(Double.class)) {
                        setter = cl.getDeclaredMethod("set" + fieldName, Double.class);
                        setter.setAccessible(true);
                        Double d = Double.parseDouble(text);
                        if (d > 0) {
                            setter.invoke(newRecord, d);
                        }
                    } else if (fieldClass.equals(Float.class)) {
                        setter = cl.getDeclaredMethod("set" + fieldName, Float.class);
                        setter.setAccessible(true);
                        Float f = Float.parseFloat(text);
                        if (f > 0) {
                            setter.invoke(newRecord, f);
                        }
                    } else if (fieldClass.equals(String.class)) {
                        setter = cl.getDeclaredMethod("set" + fieldName, String.class);
                        setter.setAccessible(true);
                        if (text.isEmpty() || text == null || text.length() == 0) {
                            throw new Exception("Text is emptty");
                        } else setter.invoke(newRecord, text);
                    } else if (fieldClass.equals(Boolean.class)) {
                        setter = cl.getDeclaredMethod("set" + fieldName, Boolean.class);
                        setter.setAccessible(true);
                        Boolean b = Boolean.parseBoolean(text);
                        if (b != null) {
                            setter.invoke(newRecord, b);
                        } else throw new Exception();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    for (int j = i + 1; j < list.size(); j++) {
                        resetStyles(list.get(j));
                    }
                    shutDownParsing(textField, fieldClass);
                    return null;
                }
            } else if (CalendarTextField.class.isInstance(node)) {
                CalendarTextField ctf = (CalendarTextField) node;
                Long time = ctf.getCalendar().getTime().getTime();
                try {
                    if (fieldClass.equals(java.sql.Date.class)) {
                        setter = cl.getDeclaredMethod("set" + fieldName, java.sql.Date.class);
                        setter.setAccessible(true);
                        setter.invoke(newRecord, new java.sql.Date(time));
                    } else if (fieldClass.equals(Timestamp.class)) {
                        setter = cl.getDeclaredMethod("set" + fieldName, Timestamp.class);
                        setter.setAccessible(true);
                        setter.invoke(newRecord, new Timestamp(time));
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    for (int j = i + 1; j < list.size(); j++) {
                        resetStyles(list.get(j));
                    }
                    return null;
                }
            } else if (ComboBox.class.isInstance(node)) {
                ComboBox comboBox = (ComboBox) node;
                try {
                    Object o = comboBox.getValue();
                    if (o != null) {
                        if (Long.class.equals(fieldClass)) {
                            setter = cl.getDeclaredMethod("set" + fieldName, Long.class);
                            setter.setAccessible(true);
                            if (Long.class.isInstance(o)) {
                                setter.invoke(newRecord, o);
                            } else {
                                setter.invoke(newRecord, (Long.parseLong(((String) o))));
                            }

                        } else if (Integer.class.equals(fieldClass)) {

                            setter = cl.getDeclaredMethod("set" + fieldName, Integer.class);
                            setter.setAccessible(true);
                            setter.invoke(newRecord, ((Integer.parseInt(((String) o)))));
                        } else if (String.class.equals(fieldClass)) {
                            setter = cl.getDeclaredMethod("set" + fieldName, String.class);
                            setter.setAccessible(true);
                            setter.invoke(newRecord, (String) o);
                        }
                    } else {
                        Method gett = null;
                        try {
                            gett = cl.getMethod("get" + fieldName);
                        }
                        catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        gett.setAccessible(true);
                        Annotation[] annotations = gett.getDeclaredAnnotations();
                        for (int j = 0; j < annotations.length; j++) {
                            System.out.println(annotations[j].toString());
                            if (annotations[j].toString().contains("nullable=false")) {
                                throw new Exception();
                            } else if (j == annotations.length - 1) {
                                setter = cl.getDeclaredMethod("set" + fieldName, Long.class);
                                setter.setAccessible(true);
                                Long l = null;
                                try {
                                    setter.invoke(newRecord, l);
                                }
                                catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                                catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                }
                                catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    for (int j = i + 1; j < list.size(); j++) {
                        resetStyles(list.get(j));
                    }
                    shutDownParsing(comboBox, fieldClass);
                    return null;
                }
            } else if (CheckBox.class.isInstance(node)) {
                try {
                    setter = cl.getDeclaredMethod("set" + fieldName, Boolean.class);
                }
                catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                setter.setAccessible(true);
                try {
                    setter.invoke(newRecord, ((CheckBox) node).selectedProperty().getValue());
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            node.setStyle("-fx-border-color: limegreen");
        }
        return newRecord;
    }

    private void showEntity() {
        Scene scene = new Scene(root);
        stage.setTitle(MainApplicationEntry.getLanguageResourceBundle().getString(EntityClassUtil.getLowerCaseVariableName(cl)));
        stage.getIcons().add(new Image("images/documentIcon.png"));
        stage.setScene(scene);
        StageRegister.register(stage);
        stage.show();
    }

    protected void resetStyles(ObservableList<Node> nodes) {
        nodes.forEach(field -> {
            field.setStyle("-fx-border-color: aliceblue");
        });
    }

    protected void resetStyles(Node node) {
        ObservableList<Node> list = observableArrayList();
        list.add(node);
        resetStyles(list);
    }

    protected <S> void shutDownParsing(Node field, Class fieldClass) {
        field.setStyle("-fx-border-color: red");
        field.requestFocus();
        ErrorParsingAlert errorParsingAlert = new ErrorParsingAlert(fieldClass.getSimpleName() + "ErrorParsingText", fieldClass.getSimpleName() + "ErrorParsingHeader");
    }

    protected <V> ObservableList<Long> getListEntityID(Class<V> entityClass) {
        GenericDAOImpl<V> baseDAU = new GenericDAOImpl<V>(entityClass);
        ObservableList<V> allRecords = baseDAU.getObservableListRecords();
        ObservableList<Long> idList = observableArrayList();
        allRecords.forEach(record -> {
            Field idField = null;
            try {
                idField = entityClass.getDeclaredField("id" + getSimpleClassName(entityClass));
                idField.setAccessible(true);
            }
            catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            Long l = null;
            try {
                l = (Long) idField.get(record);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            idList.add(l);
        });
        baseDAUs.add(baseDAU);
        return idList;
    }

    private TextField createStandardTextField(String promptText) {
        TextField textField = new TextField();
        textField.setEditable(true);
        textField.setPromptText(promptText);
        textField.setPrefWidth(100);
        return textField;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        construct();
    }

    private void configureSearching() {
        searchField.setPrefHeight(25);
        FilteredList<T> filteredList = new FilteredList<T>(table.getItems(), e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener(((observable, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super T>) entity -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String newValueFilter = newValue.toLowerCase();
                    ArrayList<Field> fields = new
                            ArrayList<Field>(Arrays.asList(entity.getClass().getDeclaredFields()));
                    ListIterator<Field> iter = fields.listIterator();
                    while (iter.hasNext()) {
                        Field field = iter.next();
                        if (field.getType().getSimpleName().contains("Collection")) {
                            iter.remove();
                        }
                    }
                    while (iter.hasPrevious()) {
                        Field field = iter.previous();
                        field.setAccessible(true);
                        String type = field.getType().getSimpleName().toLowerCase();

                        if (type.startsWith("long")) {
                            Long l = -1l;
                            try {
                                l = (Long) field.get(entity);
                                if (l != null && Long.toString(l).contains(newValue)) {
                                    return true;
                                }
                            }
                            catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                        }
                        if (type.startsWith("int")) {
                            Integer i = -1;
                            try {
                                i = (Integer) field.get(entity);
                                if (i != null && Integer.toString(i).contains(newValue)) {
                                    return true;
                                }
                            }
                            catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                        }
                        if (type.equals("double")) {
                            Double d = -1d;
                            try {
                                d = (Double) field.get(entity);
                                if (d != null && Double.toString(d).contains(newValue)) {
                                    return true;
                                }
                            }
                            catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                        }
                        if (type.equals("string")) {
                            String s = "";
                            try {
                                s = (String) field.get(entity);
                            }
                            catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                            if (!s.isEmpty() && s.toLowerCase().contains(newValueFilter)) {
                                return true;
                            }
                        }
                        if (type.equals("timestamp")) {
                            Timestamp timestamp = null;
                            try {
                                timestamp = (Timestamp) field.get(entity);
                            }
                            catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                            if (new SimpleDateFormat("dd.MM.yyyy hh:mm", MainApplicationEntry.getLanguageResourceBundle().getLocale()).
                                    format(new Date(timestamp.getTime())).toString().contains(newValueFilter)) {
                                return true;
                            }
                        }
                        if (type.equals("date")) {
                            java.sql.Date date = null;
                            try {
                                date = (java.sql.Date) field.get(entity);
                            }
                            catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                            if (new SimpleDateFormat("dd.MM.yyyy", MainApplicationEntry.getLanguageResourceBundle().getLocale()).
                                    format(new Date(date.getTime())).toString().contains(newValueFilter)) {
                                return true;
                            }
                        }
                        if (type.equals("boolean")) {
                            Boolean b = false;
                            try {
                                b = ((Boolean) field.get(entity));
                            }
                            catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                            if (Boolean.toString(b).contains(newValueFilter)) {
                                return true;
                            }
                        }
                    }
                    return false;
                });
            }));
            SortedList<T> sortedList = new SortedList<T>(filteredList);
            sortedList.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedList);
            table.refresh();
        });
    }

    private void synchonize() {
        table.setItems(getObservableListRecordsFromDatabase());
        table.refresh();
        idClassChoosersMap.entrySet().forEach(pair -> {
            pair.getValue().getItems().clear();
            pair.getValue().getItems().setAll(getListEntityID(pair.getKey()));
        });

    }

    private void attachContextMenu() {
        tableContextMenu = new ContextMenu();
        MenuItem updateItem = new MenuItem(MainApplicationEntry.getLanguageResourceBundle().getString("updateItem"));
        updateItem.setOnAction(e -> {
            synchonize();
        });
        tableContextMenu.getItems().add(updateItem);
        table.setContextMenu(tableContextMenu);
    }

    private <E> void configureNavigationBar(BorderPane pane) {
        Text accessableTablesText = new Text(res.getString("accessableTablesText"));
        ListView<String> tableList = new ListView();
        DoubleTuple<List<String>, List<String>> items = getTableAndEntityNames();
        tableList.getItems().addAll(items.getFirstField());
        tableList.setEditable(false);
        tableList.setOnMousePressed(e -> {
            if (tableList.getSelectionModel().getSelectedItem() != null) {
                try {
                    new EntityController<E>(EntityClassUtil.loadClassFromSimpleEntityClassName(items.getSecondField().get(tableList.getSelectionModel().getSelectedIndex())));
                }
                catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        BorderPane navigationBar = new BorderPane();
        VBox vbox = new VBox(accessableTablesText, tableList);
        String showNavigationBarText = (res.getString("showNavigationBarText"));
        Menu toolsMenu = new Menu(res.getString("menuView"));
        RadioMenuItem hideOption = new RadioMenuItem(showNavigationBarText);
        hideOption.setSelected(true);
        hideOption.setOnAction(e -> {
            if (hideOption.isSelected()) {
                navigationBar.setCenter(vbox);
                navigationBar.setPrefWidth(200);
            } else {
                navigationBar.getChildren().remove(vbox);
                navigationBar.setPrefWidth(0);
            }
        });
        navigationBar.setCenter(vbox);
        pane.setLeft(navigationBar);
        toolsMenu.getItems().add(hideOption);
        getMainMenu().getMenus().add(toolsMenu);
    }

    private void configureMainMenu(MenuBar menuBar) {
        ResourceBundle res = MainApplicationEntry.getLanguageResourceBundle();
        Menu menuFile = new Menu(res.getString("menuFile"));
        MenuItem addItem = new MenuItem(res.getString("add" + getSimpleClassName(cl) + "Button"));
        addItem.setOnAction(getAddRecordButton().getOnAction());
        MenuItem updateItem = new MenuItem(res.getString("updateItem"));
        updateItem.setOnAction(getTableContextMenu().getItems().get(0).getOnAction());
        ObservableList<MenuItem> menuItems = FXCollections.observableArrayList();
        ArrayList<Field> collectionFields = new ArrayList<>(Arrays.asList(cl.getDeclaredFields()));
        collectionFields.removeAll(fields);
        collectionFields.forEach(collectionField -> {
            String collectionFieldName = collectionField.getName();
            if (collectionField.getType().getName().startsWith("com.boyarskycompany.mcis.entities.")) {
                collectionFieldName = collectionFieldName.substring(0, 1).toUpperCase() + collectionFieldName.substring(1, collectionFieldName.length());

            } else {
                collectionFieldName = collectionFieldName.substring(0, 1).toUpperCase() + collectionFieldName.substring(1, collectionFieldName.length() - 1);
            }
            MenuItem item = new MenuItem(res.getString("add" + collectionFieldName + "Button"));
            String finalCollectionFieldName = collectionFieldName;
            item.setOnAction(handler -> {
                Class clazz = null;
                try {
                    clazz = Class.forName("com.boyarskycompany.mcis.entities." + finalCollectionFieldName + "Entity");
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                T record = table.getSelectionModel().getSelectedItem();
                if (record != null) {
                    new EntityController(clazz, record, cl);
                } else {
                    new EntityController(clazz);
                }
            });
            menuItems.add(item);
        });
        menuFile.getItems().addAll(addItem, updateItem);
        menuFile.getItems().addAll(menuItems);
        menuBar.getMenus().add(menuFile);
    }

}