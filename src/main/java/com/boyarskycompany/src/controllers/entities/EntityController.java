package com.boyarskycompany.src.controllers.entities;


import com.boyarskycompany.src.controllers.converters.*;
import com.boyarskycompany.src.controllers.database.dauimpl.BaseDAUImpl;
import com.boyarskycompany.src.controllers.entities.util.ClassUtil;
import com.boyarskycompany.src.controllers.entities.util.RecordsIdsTuple;
import com.boyarskycompany.src.controllers.entities.util.StageRegister;
import com.boyarskycompany.src.controllers.entities.util.alerts.ConfirmationAlert;
import com.boyarskycompany.src.controllers.entities.util.alerts.ErrorParsingAlert;
import com.boyarskycompany.src.controllers.entities.util.alerts.WarningAlert;
import com.boyarskycompany.src.run.Main;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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

import static com.boyarskycompany.src.controllers.entities.util.ClassUtil.getSimpleClassName;
import static javafx.collections.FXCollections.observableArrayList;

/**
 * Created by zandr on 10.10.2016.
 */
public class EntityController<T> implements Initializable, Configurable {
    private ResourceBundle res = Main.getResLan();
    private Class<T> cl;
    private ArrayList<Field> fields;
    private ArrayList<Class> typeList = new ArrayList();
    private TextField searchField;
    private ArrayList<BaseDAUImpl> baseDAUs = new ArrayList<>();
    private Preferences preferences;
    private BaseDAUImpl<T> dauImpl;
    private TableView<T> table = new TableView<T>();
    private ArrayList<TableColumn<T, ?>> columns = new ArrayList<TableColumn<T, ?>>();
    private BorderPane root = new BorderPane();
    private BorderPane additionalBorderPane = new BorderPane();
    private FlowPane fieldsPane = new FlowPane();
    private Button nextEntityButton;
    private Button addRecordButton;
    private Button findButton;
    private MenuBar mainMenu = new MenuBar();
    private ContextMenu tableContextMenu;
    private BorderPane toolsPane = new BorderPane();
    private HashMap<Class, ComboBox<Long>> idClassChoosersMap = new HashMap<>();

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
        dauImpl = new BaseDAUImpl<T>(cl);
        preferences = Preferences.userNodeForPackage(cl);
        if (getLongEntityCounter() <= 1) {
            Long maxId = getMaxId(true);
            if (maxId != null) {
                createEntityCounter(maxId + 1);
            }
        }
        searchField = new TextField();
        searchField.setPromptText(Main.getResLan().getString("findButton"));
        searchField.setMinHeight(1d);
        searchField.setPrefHeight(1d);
        construct();
        showEntity();
    }

    public <S> EntityController(Class<T> childEntityClass, S parentRecord, Class<S> parentEntityClass) {
        this(childEntityClass);
        table.getItems().clear();
        RecordsIdsTuple<T> tuple = getBoundedRecords(parentEntityClass, parentRecord);
        table.getItems().addAll(tuple.getRecordList());
        ObservableList<Node> nodes = getFieldsPane().getChildren();
        for (int i = 0; i < nodes.size(); i++) {
            if (ComboBox.class.isInstance(nodes.get(i))) {
                Iterator<Map.Entry<Field, Long>> iter =
                        tuple.getIdMap().entrySet().iterator();
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

    public <S> RecordsIdsTuple<T> getBoundedRecords(Class<S> relatedClass, S record) {
        return dauImpl.getBoundedRecords(relatedClass, record);
    }

    protected MenuBar getMainMenu() {
        return mainMenu;
    }

    protected void setMainMenu(MenuBar mainMenu) {
        this.mainMenu = mainMenu;
    }

    protected Button getNextEntityButton() {
        return nextEntityButton;
    }

    protected void setNextEntityButton(Button nextEntityButton) {
        this.nextEntityButton = nextEntityButton;
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

    protected ArrayList<BaseDAUImpl> getBaseDAUs() {
        return baseDAUs;
    }

    protected void setBaseDAUs(ArrayList<BaseDAUImpl> baseDAUs) {
        this.baseDAUs = baseDAUs;
    }

    protected Preferences getPreferences() {
        return preferences;
    }

    protected void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    protected BaseDAUImpl<T> getDauImpl() {
        return dauImpl;
    }

    protected void setDauImpl(BaseDAUImpl<T> dauImpl) {
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

    protected FlowPane getFieldsPane() {
        return fieldsPane;
    }

    protected void setFieldsPane(FlowPane fieldsPane) {
        this.fieldsPane = fieldsPane;
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
        return dauImpl.getListRecords();
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

    private void construct() {
        String simpleEntityName = ClassUtil.getSimpleLowerCaseClassName(cl);
        table.setEditable(true);
        createTableColumns(simpleEntityName);
        table.getColumns().addAll(columns);
        table.getItems().addAll(getObservableListRecordsFromDatabase());
        table.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
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
        });
        root.setCenter(table);
        additionalBorderPane.setTop(fieldsPane);
        findButton = new Button(res.getString("findButton"));
        findButton.setOnAction(e -> performSearching());
        addRecordButton = new Button(res.getString("add" + cl.getSimpleName().substring(0, cl.getSimpleName().indexOf("Entity")) + "Button"));
        addRecordButton.setOnAction(e -> {
            T record = createNewRecord();
            if (record != null) {
                ConfirmationAlert confirmation = new ConfirmationAlert("confirmationAddingRecordContentText");
                Optional<ButtonType> result = confirmation.showAndWait();
                if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                    pushRecordIntoDatabase(record);
                    resetStyles(fieldsPane.getChildren());
                    incrementEntityCounter();
                    resetFields(fieldsPane.getChildren());
                    table.getItems().add(record);
                }
            }
        });
        nextEntityButton = new Button(res.getString("viewNextTable"));
        Text entityListLabel = new Text(res.getString(simpleEntityName + "ListLabel"));
        entityListLabel.setStyle("-fx-alignment: baseline-center");
        entityListLabel.setFont(Font.font("Tahoma", 24));
        toolsPane.setCenter(entityListLabel);
        attachContextMenu();
        configureMainMenu(mainMenu);
        toolsPane.setTop(mainMenu);
        root.setTop(toolsPane);
        additionalBorderPane.setLeft(findButton);
        additionalBorderPane.setCenter(addRecordButton);
        additionalBorderPane.setRight(nextEntityButton);
        additionalBorderPane.setBottom(searchField);
        root.setBottom(additionalBorderPane);
    }

    private boolean haveChilds(T record) {
        Field[] fieldArray = cl.getDeclaredFields();
        for (int i = 0; i < fieldArray.length; i++) {
            if (fieldArray[i].getType().getSimpleName().contains("collection")) {
                Field field =
                        fieldArray[i];
                Class clazz = ClassUtil.loadClassFromCollectionField(field.getName());
                RecordsIdsTuple recordTuple = getBoundedRecords(clazz, record);
                if (recordTuple.getRecordList() == null || recordTuple.getRecordList().size() == 0) {
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
                    fieldsPane.getChildren().add(idField);
                } else {
                    Class<?> entityClass = ClassUtil.loadClassFromIdField(fieldName);
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
                    fieldsPane.getChildren().add(idComboBox);
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
                fieldsPane.getChildren().add(createStandardTextField(tableColumn.getText()));
            } else {
                if (typeName.equals("float")) {
                    fieldClass = Float.class;
                    typeList.add(Float.class);
                    tableColumn = new TableColumn<T, Float>(res.getString(fieldName + "Column"));
                    tableColumn.setCellValueFactory(new PropertyValueFactory<T, Float>(fieldName));
                    tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringToFloatConverter()));
                    tableColumn.setEditable(true);
                    tableColumn.setPrefWidth(100);
                    fieldsPane.getChildren().add(createStandardTextField(tableColumn.getText()));
                } else if (typeName.equalsIgnoreCase("double")) {
                    fieldClass = Double.class;
                    typeList.add(Double.class);
                    tableColumn = new TableColumn<T, Double>(res.getString(fieldName + "Column"));
                    tableColumn.setCellValueFactory(new PropertyValueFactory<T, Double>(fieldName));
                    tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringToDoubleConverter()));
                    tableColumn.setEditable(true);
                    tableColumn.setPrefWidth(100);
                    fieldsPane.getChildren().add(createStandardTextField(tableColumn.getText()));
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
                        fieldsPane.getChildren().add(createStandardTextField(tableColumn.getText()));
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
                        calendarTextField.setDateFormat(new SimpleDateFormat("dd.MM.yyyy hh:mm"));
                        fieldsPane.getChildren().add(calendarTextField);
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
                        fieldsPane.getChildren().add(calendarTextField);
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
                            fieldsPane.getChildren().add(booleanField);
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
                    ConfirmationAlert alert = new
                            ConfirmationAlert("confirmationUpdateRecordContentText");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get().getButtonData() == ButtonBar.ButtonData.YES) {
                        T record = (T) event.getRowValue();
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
            columns.add(tableColumn);
        });

    }

    private T createNewRecord() {
        ObservableList<Node> list = fieldsPane.getChildren();
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
        Stage stage = new Stage();
        stage.setTitle(Main.getResLan().getString(ClassUtil.getSimpleLowerCaseClassName(cl)));
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
        ErrorParsingAlert errorParsingAlert = new ErrorParsingAlert(fieldClass.getSimpleName() + "ErrorParsingText", fieldClass.getSimpleName() + "ErrorParsingHeader");
    }

    protected <V> ObservableList<Long> getListEntityID(Class<V> entityClass) {
        BaseDAUImpl<V> baseDAU = new BaseDAUImpl<V>(entityClass);
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

    private void performSearching() {
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
                            }
                            catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                            if (l != null && Long.toString(l).contains(newValue)) {
                                return true;
                            }
                        }
                        if (type.startsWith("int")) {
                            Integer i = -1;
                            try {
                                i = (Integer) field.get(entity);
                            }
                            catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                            if (i != -1 && Integer.toString(i).contains(newValue)) {
                                return true;
                            }
                        }
                        if (type.equals("double")) {
                            Double d = -1d;
                            try {
                                d = (Double) field.get(entity);
                            }
                            catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
                            if (d != -1d && Double.toString(d).contains(newValue)) {
                                return true;
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
                            if (new SimpleDateFormat("dd.MM.yyyy hh:mm", Main.getResLan().getLocale()).
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
                            if (new SimpleDateFormat("dd.MM.yyyy", Main.getResLan().getLocale()).
                                    format(new Date(date.getTime())).toString().contains(newValueFilter)) {
                                return true;
                            }
                        }
                        if (type.equals("boolean")) {
                            boolean b = false;
                            try {
                                b = field.getBoolean(entity);
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
        MenuItem updateItem = new MenuItem(Main.getResLan().getString("updateItem"));
        updateItem.setOnAction(e -> {
            synchonize();
        });
        tableContextMenu.getItems().add(updateItem);
        table.setContextMenu(tableContextMenu);
    }

    @Override
    public void configureNavigationBar(BorderPane pane) {
        BorderPane navigationBar = new BorderPane();

    }

    @Override
    public void configureMainMenu(MenuBar menuBar) {
        ResourceBundle res = Main.getResLan();
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
            if (collectionField.getType().getName().startsWith("com.boyarskycompany.src.entities.")) {
                collectionFieldName = collectionFieldName.substring(0, 1).toUpperCase() + collectionFieldName.substring(1, collectionFieldName.length());

            } else {
                collectionFieldName = collectionFieldName.substring(0, 1).toUpperCase() + collectionFieldName.substring(1, collectionFieldName.length() - 1);
            }
            MenuItem item = new MenuItem(res.getString("add" + collectionFieldName + "Button"));
            String finalCollectionFieldName = collectionFieldName;
            item.setOnAction(handler -> {
                Class clazz = null;
                try {
                    clazz = Class.forName("com.boyarskycompany.src.entities." + finalCollectionFieldName + "Entity");
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