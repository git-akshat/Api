package com.example.skilltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView pname;
    TextView pdesc;
    ImageView pimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pname = (TextView) findViewById(R.id.product_name);
        pdesc = (TextView) findViewById(R.id.product_description);
        pimage = (ImageView) findViewById(R.id.image_view);
        try {
            parseXml();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseXml() throws XmlPullParserException, IOException {
        XmlPullParserFactory parserFactory;
        parserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = parserFactory.newPullParser();
        InputStream istream = getAssets().open("http://anecdot.in/api/product.xml");
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
        parser.setInput(istream,null);

        processParsing(parser);
    }

    private void processParsing(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Product> products = new ArrayList<>();
        int eventType = parser.getEventType();
        Product currentProduct = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();

                    if ("URL".equals(eltName))
                    {
                        currentProduct = new Product();
                        products.add(currentProduct);
                    }
                    else if (currentProduct != null)
                    {
                        if ("product_id".equals(eltName)) {
                            currentProduct.product_id = parser.nextText();
                        }
                        else if ("product_url".equals(eltName)) {
                            currentProduct.product_url = parser.nextText();
                        }
                        else if ("product_name".equals(eltName)) {
                            currentProduct.product_name = parser.nextText();
                        }
                        else if ("about_product".equals(eltName)) {
                            currentProduct.about_product = parser.nextText();
                        }
                        else if ("product_image1".equals(eltName)) {
                            currentProduct.product_image1 = parser.nextText();
                        }
                        else if ("product_image2".equals(eltName)) {
                            currentProduct.product_image2 = parser.nextText();
                        }
                        else if ("product_image3".equals(eltName)) {
                            currentProduct.product_image3 = parser.nextText();
                        }
                        else if ("product_image4".equals(eltName)) {
                            currentProduct.product_image4 = parser.nextText();
                        }
                        else if ("product_image5".equals(eltName)) {
                            currentProduct.product_image5 = parser.nextText();
                        }
                        else if ("price".equals(eltName)) {
                            currentProduct.price = parser.nextText();
                        }
                        else if ("coupon_code".equals(eltName)) {
                            currentProduct.coupon_code = parser.nextText();
                        }
                        else if ("product_desc".equals(eltName)) {
                            currentProduct.product_desc = parser.nextText();
                        }
                        else if ("color".equals(eltName)) {
                            currentProduct.color = parser.nextText();
                        }
                        else if ("RFID".equals(eltName)) {
                            currentProduct.RFID = parser.nextText();
                        }
                        else if ("size_for_trail".equals(eltName)) {
                            currentProduct.size_for_trail = parser.nextText();
                        }
                    }
                    break;
            }

            eventType = parser.next();
        }

        printProducts(products);
    }

    private void printProducts(ArrayList<Product> products) {
        StringBuilder builder = new StringBuilder();

        for (Product product : products) {
            builder.append(product.product_id).append("\n").
                    append(product.product_url).append("\n").
                    append(product.product_name).append("\n\n");
        }

        pname.setText(builder.toString());
    }
}

