package main.java;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.table.AbstractTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WaterbodyTableModel extends AbstractTableModel {
    public WaterbodyTableModel() {
        waterObjects = new ArrayList<>();
    }

    public WaterbodyTableModel(File xmlFile) {
        this();

        String name;
        String type;
        String propertyName;
        String value;
        List<String> continents;

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("waterbody");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Element element = (Element) nodeList.item(i);

                name = element.getAttribute("name");
                type = element.getAttribute("type");
                propertyName = element.getAttribute("property");
                value = element.getAttribute("value");

                NodeList childrenNodeList = element.getElementsByTagName("continent");
                continents = new ArrayList<>();
                for (int j = 0; j < childrenNodeList.getLength(); ++j) {
                    continents.add(childrenNodeList.item(j).getTextContent());
                }

                waterObjects.add(new Waterbody(name, type, propertyName, Integer.valueOf(value), continents));
            }
        } catch (NumberFormatException | SAXException | ParserConfigurationException | IOException e) {
            System.out.println(e.getStackTrace());
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ColumnName.values()[columnIndex].getColumnClass();
    }

    @Override
    public int getRowCount() {
        return waterObjects.size();
    }

    @Override
    public int getColumnCount() {
        return ColumnName.values().length;
    }

    @Override
    public String getColumnName(int column) {
        return ColumnName.values()[column].toString();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column) {
            case 0:
                return waterObjects.get(row).getName();
            case 1:
                return waterObjects.get(row).getType();
            case 2:
                return waterObjects.get(row).getProperty();
            case 3:
                return waterObjects.get(row).getValue();
            default:
                return waterObjects.get(row).getContinents();
        }
    }

    @Override
    public void setValueAt(Object val, int row, int column) {
        switch (column) {
            case 0:
                waterObjects.get(row).setName((String) val);
                break;
            case 1:
                waterObjects.get(row).setType((Waterbody.Type) val);
                break;
            case 2:
                waterObjects.get(row).setProperty((Waterbody.Property) val);
                break;
            case 3:
                waterObjects.get(row).setValue((Integer) val);
                break;
            case 4:
                waterObjects.get(row).setContinents((Waterbody.Continents) val);
                break;
            default:
                throw new IllegalArgumentException("column is greater than column size");
        }
    }

    public void setWaterObjects(List<Waterbody> waterObjects) {
        this.waterObjects = waterObjects;
    }

    public List<Waterbody> getWaterObjects() {
        return waterObjects;
    }

    private enum ColumnName {
        Name(String.class),
        Type(String.class),
        Property(String.class),
        Value(Integer.class),
        Continents(Waterbody.Continents.class);

        ColumnName(Class<?> columnClass) {
            this.columnClass = columnClass;
        }

        public Class<?> getColumnClass() {
            return columnClass;
        }

        private Class<?> columnClass;
    }

    public void deleteRows(int[] rows) {
        for (int i = rows.length - 1; i >= 0; --i) {
            waterObjects.remove(rows[i]);
        }
    }

    public void saveXml(File selectedFile) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("root");
            document.appendChild(root);

            waterObjects.forEach((waterObject) -> {
                Element waterbody = document.createElement("waterbody");

                waterbody.setAttribute("name", waterObject.getName());
                waterbody.setAttribute("type", waterObject.getType().toString());
                waterbody.setAttribute("property", waterObject.getProperty().toString());
                waterbody.setAttribute("value", Integer.toString(waterObject.getValue()));

                for (Waterbody.Continent continent : waterObject.getContinents().getContinentList()) {
                    Element continentElement = document.createElement("continent");
                    continentElement.setTextContent(continent.toString());

                    waterbody.appendChild(continentElement);
                }

                root.appendChild(waterbody);
            });

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(selectedFile);
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void saveBinary(File selectedFile) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(selectedFile));
        stream.writeObject(waterObjects);
        stream.close();
    }

    public static WaterbodyTableModel getBinary(File selectedFile) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(new FileInputStream(selectedFile));
        WaterbodyTableModel model = new WaterbodyTableModel();
        model.setWaterObjects((List<Waterbody>) stream.readObject());
        stream.close();
        return model;
    }

    private List<Waterbody> waterObjects;
}
