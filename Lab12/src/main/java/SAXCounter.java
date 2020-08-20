package main.java;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXCounter extends DefaultHandler {
    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

        lakeCount = 0;
        riverCount = 0;
        waterfallCount = 0;
        seaCount = 0;
        oceanCount = 0;
        maxSurfaceArea = 0;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("waterbody")) {

            switch (attributes.getValue("type")) {
                case "Lake":
                    lakeCount++;
                    maxSurfaceArea = Math.max(maxSurfaceArea, Integer.valueOf(attributes.getValue("value")));
                    break;
                case "River":
                    riverCount++;
                    break;
                case "Waterfall":
                    waterfallCount++;
                    break;
                case "Sea":
                    seaCount++;
                    maxSurfaceArea = Math.max(maxSurfaceArea, Integer.valueOf(attributes.getValue("value")));
                    break;
                case "Ocean":
                    oceanCount++;
                    maxSurfaceArea = Math.max(maxSurfaceArea, Integer.valueOf(attributes.getValue("value")));
                    break;
            }
        }
    }

    public int getLakeCount() {
        return lakeCount;
    }

    public int getRiverCount() {
        return riverCount;
    }

    public int getWaterfallCount() {
        return waterfallCount;
    }

    public int getSeaCount() {
        return seaCount;
    }

    public int getOceanCount() {
        return oceanCount;
    }

    public int getMaxSurfaceArea() {
        return maxSurfaceArea;
    }

    private int lakeCount;
    private int riverCount;
    private int waterfallCount;
    private int seaCount;
    private int oceanCount;

    private int maxSurfaceArea;
}

