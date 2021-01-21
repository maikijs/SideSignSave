package lv.side.Utils;

import org.bukkit.Bukkit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import lv.side.Main;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class FileUtils {
    public static void saveSignToXML(SignUtils list) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element main = doc.createElement("SignList");
            for (LocationUtils e : list.getSigns()) {
                Element SignRoot = doc.createElement("Sign");
                main.appendChild(SignRoot);

                Element SignID = doc.createElement("itemID");
                SignID.appendChild(doc.createTextNode(e.getText()[2]));
                SignRoot.appendChild(SignID);

                //        Element SignPrice = doc.createElement("price");
                //        SignPrice.appendChild(doc.createTextNode(e.getText()[3]));
                //        SignRoot.appendChild(SignPrice);

                Element SignAmount = doc.createElement("amount");
                SignAmount.appendChild(doc.createTextNode(e.getText()[1]));
                SignRoot.appendChild(SignAmount);

                Element Location = doc.createElement("Location");

                Element X = doc.createElement("X");
                X.appendChild(doc.createTextNode(String.valueOf(e.getX())));
                Location.appendChild(X);

                Element Y = doc.createElement("Y");
                Y.appendChild(doc.createTextNode(String.valueOf(e.getY())));
                Location.appendChild(Y);

                Element Z = doc.createElement("Z");
                Z.appendChild(doc.createTextNode(String.valueOf(e.getZ())));
                Location.appendChild(Z);

                Element world = doc.createElement("world");
                world.appendChild(doc.createTextNode(String.valueOf(e.getWorld())));
                Location.appendChild(world);

                SignRoot.appendChild(Location);
                main.appendChild(SignRoot);
            }
            doc.appendChild(main);
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            File file = new File(Main.getInstance().getDataFolder() + "/" + list.getFileName() + ".xml");
            transformer.transform(new DOMSource(doc), new StreamResult(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SignUtils readSigns(String FileName) {
        SignUtils list = new SignUtils(FileName);
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(
                    Main.getInstance().getDataFolder() + "/" + FileName + ".xml");
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            NodeList rootList = doc.getElementsByTagName("SignList");
            Node rootNode = rootList.item(0);
            NodeList subNodes = rootNode.getChildNodes();
            for (int i = 0; i < subNodes.getLength(); i++) {
                if (subNodes.item(i).getNodeName().equals("Sign")) {
                    String[] strings = new String[4];
                    int x = -1;
                    int y = -1;
                    int z = -1;
                    String world = "";
                    NodeList subSubNotes = subNodes.item(i).getChildNodes();
                    for (int e = 0; e < subSubNotes.getLength(); ++e) {
                        if (subSubNotes.item(e).getNodeName().equals("itemID")) {
                            strings[2] = subSubNotes.item(e).getTextContent();
                        }
                        //            else if (subSubNotes.item(e).getNodeName().equals("price"))
                        //            {
                        //              strings[3] = subSubNotes.item(e).getTextContent();
                        //            }
                        else if (subSubNotes.item(e).getNodeName().equals("amount")) {
                            strings[1] = subSubNotes.item(e).getTextContent();
                        } else if (subSubNotes.item(e).getNodeName().equals("Location")) {
                            NodeList location = subSubNotes.item(e).getChildNodes();
                            for (int o = 0; o < location.getLength(); o++) {
                                if (location.item(o).getNodeName().equals("X")) {
                                    x = Integer.parseInt(location.item(o).getTextContent());
                                } else if (location.item(o).getNodeName().equals("Y")) {
                                    y = Integer.parseInt(location.item(o).getTextContent());
                                } else if (location.item(o).getNodeName().equals("Z")) {
                                    z = Integer.parseInt(location.item(o).getTextContent());
                                } else if (location.item(o).getNodeName().equals("world")) {
                                    world = location.item(o).getTextContent();
                                }
                            }
                        }
                    }
                    if (FileName.equals("buySigns")) {
                        strings[3] = "$" + Main.getInstance().getConfig()
                                .getString("Prices." + "Buy" + "." + strings[1] + "." + strings[2]);
                    }
                    if (FileName.equals("sellSigns")) {
                        strings[3] = "$" + Main.getInstance().getConfig()
                                .getString("Prices." + "Sell" + "." + strings[1] + "." + strings[2]);
                    }
                    list.addSign(strings, Bukkit.getWorld(world).getBlockAt(x, y, z));
                }
            }
            return list;
        } catch (Exception e) {

        }
        return list;
    }
}
